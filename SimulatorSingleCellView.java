import javafx.stage.Stage;

/**
 * A specialized view for the Life Simulator focusing on a single type of cell.
 * This class allows for initializing and running a simulation with a specific
 * cell type, which can be useful for observing and analyzing the behavior of
 * individual cell types in isolation.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class SimulatorSingleCellView extends SimulatorView {
    private String cellType; // The type of cell this simulation will focus on.

    /**
     * Constructor for creating a simulation view for a specific type of cell.
     *
     * @param cellType The type of cell to be used in the simulation.
     */
    public SimulatorSingleCellView(String cellType) {
        this.cellType = cellType;
    }

    /**
     * Initializes the simulation with the specified cell type. This method
     * overrides the abstract method in the parent class to provide specific
     * initialization logic.
     */
    @Override
    protected void initializeSimulation() {
        simulator.initializeWithSelectedCellType(cellType); // Initialize the simulator with the selected cell type.
    }

    /**
     * Starts the JavaFX application. This method overrides the start method of the
     * parent class to set up the stage for this specific simulation view.
     *
     * @param stage The primary stage for this application, onto which the
     *              application scene can be set.
     */
    public void start(Stage stage) {
        super.start(stage);
    }
}