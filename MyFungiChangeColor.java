import javafx.scene.paint.Color;
import java.util.List;

/**
 * Fungi is one form of life. Which can change color by time when it is alive
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class MyFungiChangeColor extends Cell {
    /**
     * Create a new MyFungiChangeColor.
     * 
     * @param field      The field currently occupied by the cell.
     * @param location   The location of the cell within the field.
     * @param col        The color of the cell.
     * @param diseasable Indicates if the cell can be diseased.
     */
    public MyFungiChangeColor(Field field, Location location, Color col, boolean diseasable) {
        super(field, location, col, diseasable);
        setMaxAge(200000);
        setParasitism(true, true); // This cell can be parasitized and be a host.
        setMutualism(false, false); // This cell does not engage in mutualism.
        setDiseasable(true); // This cell can be diseased.
    }

    /**
     * Defines the behavior of the MyFungiChangeColor cell in each generation. The
     * cell changes its color based on its age and the number of live neighbors.
     */
    public void act(List<Cell> newCell) {
        checkAge(); // Check the cell's age and handle aging.

        disease.infectCell(getDiseasable()); // Check if the cell gets infected based on its disease susceptibility.

        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        // Handle behavior if the cell is diseased.
        if (getDiseased()) {
            infectedAct(); // Perform actions specific to infected cells.
            disease.spreadDisease(getField(), getLocation()); // Spread the disease if infected.
        } else {
            // Change the color of the cell based on the number of live neighbors and its
            // age.
            if (neighbours.size() == 1) {
                if (getAge() < 20) {
                    setColor(Color.BLUE); // Color changes to blue in infancy
                    setNextState(true);
                } else {
                    setColor(Color.BLACK);// Cell will die soon.

                }
            } else if (neighbours.size() == 2 && getAge() < 100) {
                setColor(Color.GREEN); // Color changes to green when mature.
                setNextState(true);
            } else {
                setColor(Color.BLACK); // Will be dead
            }
        }
        // Attempt to recover the cell if it meets the recovery conditions.
        CellRecovery.attemptRecovery(this, getField());
        updataAge(); // Increase age each time act() is called.
    }
}