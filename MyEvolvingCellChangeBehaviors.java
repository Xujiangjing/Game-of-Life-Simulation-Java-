import javafx.scene.paint.Color; 
import java.util.List;

/**
 * MyEvolvingCellChangeBehaviors represents a form of life that can change its behavior over time.
 * This cell evolves its rules of living based on its age.
 *
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class MyEvolvingCellChangeBehaviors extends Cell {
    /**
     * Constructor to create a new evolving cell.
     * 
     * @param field      The field currently occupied by the cell.
     * @param location   The location of the cell within the field.
     * @param col        The color of the cell.
     * @param diseasable Indicates if the cell can be diseased.
     */
    public MyEvolvingCellChangeBehaviors(Field field, Location location, Color col, boolean diseasable) {
        super(field, location, col, diseasable);
        setMaxAge(200000000);
        setParasitism(true, false); // This cell can be parasitized but not a host.
        setMutualism(false, false); // This cell does not engage in mutualism.
    }

    /**
     * Defines the behavior of the evolving cell in each generation.
     * The cell's behavior changes based on its age.
     */
    public void act(List<Cell> newCell) {
        checkAge();// Check the cell's age and handle aging.
       
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        
        // Handle behavior if the cell is diseased.
        if (getDiseased()) {
            infectedAct(); // Perform actions specific to infected cells.
            disease.spreadDisease(getField(), getLocation()); // Spread the disease if infected.
        } else {
            // Behavior changes based on the cell's age.
            if (getAge() <= 10) {
                // First behavior: When age <= 10, cell stays alive if it has 1 or 0 neighbors.
                setNextState(neighbours.size() < 2); 
            } else if (getAge() > 10 && getAge() < 50) {
                // Second behavior: When age is between 11 and 49, cell stays alive if it has 2 or 0 neighbors.
                setNextState(neighbours.size() ==2 && neighbours.size() == 0);         
            } else {
                // Third behavior: When age >= 50, cell stays alive only if it has 1 neighbors.
                setNextState(neighbours.size() == 1);
            }  
        }
        // Attempt to recover the cell if it meets the recovery conditions.
        CellRecovery.attemptRecovery(this, getField());
        updataAge(); //Increase age each time when act() is call.
    }
}