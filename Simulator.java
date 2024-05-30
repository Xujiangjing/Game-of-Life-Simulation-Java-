import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author @author David J. Barnes, Michael Kölling & Jeffery Raphael &
 *         Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */

public class Simulator {
    private static final double CELL_ALIVE_PROB = 0.25;// For generality
    private static final double MYCOPLASMA_ALIVE_PROB = 0.03; // The alive probability of Mycoplasma.
    private static final double MYEVOLVING_ALIVE_PROB = 0.019; // The alive probability of MyEvolvingCellChangeBehaviors.
    private static final double MYFUNGI_ALIVE_PROB = 0.28; // The alive probability of MyFungiChangeColor.
    private static final double NFACELL_ALIVE_PROB = 0.25; // The alive probability of NonDeterministicCells.
    private List<Cell> cells;
    private Field field;
    private int generation;
    private String cellType;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * 
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation. Iterate
     * over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        field.increaseGen(); // Increment the generation count in the field.
        List<Cell> newCell = new ArrayList<>();

        for (Iterator<Cell> it = cells.iterator(); it.hasNext();) {
            Cell cell = it.next();
            cell.act(newCell);
            if (!cell.isAlive()) {
                it.remove();
            }
        }

        for (Cell cell : cells) {
            cell.updateState();
        }

        cells.addAll(newCell);
        generation++;
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        field.reset();
    }

    /**
     * Pause for a given time.
     * 
     * @param millisec The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        } catch (InterruptedException ie) {
            // wake up
        }
    }

    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }

    public String getTemperature() {
        return field.getTemperatureCondition();
    }

    public String getTimeString() {
        return field.getTimeString();
    }

    public double getDiseaseAliveCells() {
        return field.getDiseasedAliveCells();
    }

    /**
     * Initializes the simulation with a selected cell type. Populates the field
     * based on the cell type and its associated alive probability.
     * 
     * @param String cellType the name of cell
     */
    public void initializeWithSelectedCellType(String cellType) {
        reset();
        this.cellType = cellType;
        Random rand = Randomizer.getRandom();
        field.clear();
        double aliveProbability; // Variable to hold the probability for the current cell type
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Cell cell = null;

                // Determine the type of cell to create based on the specified cellType.
                switch (cellType) {
                case "Mycoplasma":
                    cell = new Mycoplasma(field, location, Color.ORANGE, true);
                    aliveProbability = MYCOPLASMA_ALIVE_PROB;
                    break;
                case "MyFungiChangeColor":
                    cell = new MyFungiChangeColor(field, location, Color.BLUE, true);
                    aliveProbability = MYFUNGI_ALIVE_PROB;
                    break;
                case "MyEvolvingCellChangeBehaviors":
                    cell = new MyEvolvingCellChangeBehaviors(field, location, Color.GREEN, false);
                    aliveProbability = MYEVOLVING_ALIVE_PROB;
                    break;
                case "NonDeterministicCells":
                    cell = new NonDeterministicCells(field, location, Color.YELLOW, false);
                    aliveProbability = NFACELL_ALIVE_PROB;
                    break;
                default:
                    aliveProbability = CELL_ALIVE_PROB; // Default probability if cell type is unknown
                    break;
                }

                if (cell != null && rand.nextDouble() <= aliveProbability) {
                    cells.add(cell);
                } else if (cell != null) {
                    cell.setDead();
                    cells.add(cell);
                }
            }
        }
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    public void populate() {
        reset();
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {

                if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                    Location location = new Location(row, col);
                    Mycoplasma myco = new Mycoplasma(field, location, Color.ORANGE, true);
                    cells.add(myco);
                } else if (rand.nextDouble() <= MYFUNGI_ALIVE_PROB) {
                    Location location = new Location(row, col);
                    MyFungiChangeColor myfu = new MyFungiChangeColor(field, location, Color.PURPLE, true);
                    cells.add(myfu);
                } else if (rand.nextDouble() <= MYEVOLVING_ALIVE_PROB) {
                    Location location = new Location(row, col);
                    MyEvolvingCellChangeBehaviors myev = new MyEvolvingCellChangeBehaviors(field, location, Color.BLUE,
                            false);
                    cells.add(myev);
                } else if (rand.nextDouble() <= NFACELL_ALIVE_PROB) {
                    Location location = new Location(row, col);
                    NonDeterministicCells nond = new NonDeterministicCells(field, location, Color.YELLOW, false);
                    cells.add(nond);
                }
            }
        }
    }

    public String getCellType() {
        return cellType;
    }
}