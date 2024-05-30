import javafx.scene.paint.Color;
import java.util.List;
import java.util.Random;

/**
 * Non-deterministic cells It executes the same set of rules during in
 * generation. A life form that behaves in a non-deterministic manner. That is,
 * given a set of rules, the cell will execute a rule, rn, with probability pn.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */

public class NonDeterministicCells extends Cell {
    private Random rand = new Random(); // Random number generator for probabilistic behavior.

    private enum NFAState { // Enum to represent the state of the cell.
        ALIVE, DEAD
    }

    private NFAState currentState; // Current state of the cell.

    /**
     * Create a new NonDeterministicCells.
     *
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     * @param col        The color within the field.
     * @param diseasable Indicates if the cell can be diseased.
     */
    public NonDeterministicCells(Field field, Location location, Color col, boolean diseasable) {
        super(field, location, col, diseasable);
        setMaxAge(1999999);
        setParasitism(false, false); // This cell cannot be parasitized and cannot be a host.
        setMutualism(true, false); // This cell engages in mutualism but cannot be a host.
        currentState = NFAState.ALIVE; // Initial state is ALIVE.
    }

    /**
     * Defines the behavior of the NonDeterministicCells in each generation. The
     * cell makes decisions based on the number of neighbors and random
     * probabilities.
     */
    public void act(List<Cell> newCell) {
        checkAge(); // Check the cell's age and handle aging.

        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        // Handle behavior if the cell is diseased.
        if (getDiseased()) {
            infectedAct(); // Perform actions specific to infected cells.
            disease.spreadDisease(getField(), getLocation()); // Spread the disease if infected.
        } else {
            // Non-deterministic behavior based on the number of neighbors.
            if (isAlive()) {
                if (neighbours.size() < 2 || neighbours.size() > 3) {
                    if (rand.nextDouble() < 0.1) { // 10% chance of dying.
                        currentState = NFAState.DEAD;
                    }
                } else if (neighbours.size() == 2) {
                    if (rand.nextDouble() < 0.9) { // 90% chance of staying alive.
                        currentState = NFAState.ALIVE;
                    }
                }
            }
            setNextState(currentState == NFAState.ALIVE); // Update the next state based on current state.
        }
        // Attempt to recover the cell if it meets the recovery conditions.
        CellRecovery.attemptRecovery(this, getField());
        updataAge(); // Increase age each time act() is called.
    }
}