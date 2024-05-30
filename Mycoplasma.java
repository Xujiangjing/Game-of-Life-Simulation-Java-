import javafx.scene.paint.Color;
import java.util.List;

/**
 * Simplest form of life. Fun Fact: Mycoplasma are one of the simplest forms of
 * life. A type of bacteria, they only have 500-1000 genes! For comparison,
 * fruit flies have about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael & Jiangjing, Xu &
 *         Hongyuan, Zhao
 * @version 2024.02.28
 */

public class Mycoplasma extends Cell {
    /**
     * Create a new Mycoplasma.
     *
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     * @param col        The color within the field.
     * @param diseasable Indicates if the cell can be diseased.
     */
    public Mycoplasma(Field field, Location location, Color col, boolean diseasable) {
        super(field, location, col, diseasable);
        setMaxAge(188888);
        setParasitism(false, false); // The cell cannot be parasitized and cannot be a host.
        setMutualism(true, true); // The cell engages in mutualism both as a participant and a host.
        setDiseasable(true); // The cell can be affected by diseases.
    }

    /**
     * Defines the behavior of the Mycoplasma cell in each generation based on the
     * number of live neighbours. The rules for Mycoplasma are inspired by Conway's
     * Game of Life: - If the cell has fewer than two live neighbours, it dies due
     * to underpopulation. - If the cell has two or three live neighbours, it
     * survives to the next generation. - If the cell has more than three live
     * neighbours, it dies due to overpopulation. - Any dead cell with exactly three
     * live neighbours becomes alive, simulating reproduction. Additionally, this
     * method handles disease spread and aging.
     */
    public void act(List<Cell> newCell) {
        checkAge(); // Check and update the age of the cell, possibly leading to natural death.
        disease.infectCell(getDiseasable()); // Check if the cell gets infected based on its disease susceptibility.
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation()); // Get living neighbours.
        setNextState(false);

        if (getDiseased()) {
            infectedAct(); // Define behavior if the cell is infected.
            disease.spreadDisease(getField(), getLocation()); // Spread disease if the cell is infected.
        } else {
            if (isAlive()) {
                // Determine the cell's next state based on the number of live neighbours.
                if (neighbours.size() < 2 || neighbours.size() > 3) {
                    setNextState(false); // Cell dies if it has fewer than two or more than three neighbours.
                } else if (neighbours.size() == 2 || neighbours.size() == 3) {
                    setNextState(true); // Cell survives if it has two or three neighbours.
                }
            } else {
                if (neighbours.size() == 3) {
                    setNextState(true); // Dead cell becomes alive if it has exactly three neighbours.
                }
            }
        }
        // Attempt to recover the cell if it meets the recovery conditions.
        CellRecovery.attemptRecovery(this, getField());
        updataAge(); // Update the cell's age each generation.
    }
}