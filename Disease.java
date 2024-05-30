import java.util.Random;
import java.util.List;

/**
 * class representing the concept of disease in cells. Diseases in
 * cells have two primary characteristics: (i) they can spread from one cell to
 * its neighbors, (ii) they change the behavior of the cell once it is diseased.
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class Disease {
    private boolean isDiseased;
    private static final double CELL_DISEASE_PROB = 0.007;

    public Disease() {
        this.isDiseased = false;
    }
    
    public boolean isDiseased() {
        return isDiseased;
    }
    
    public void setDiseased(boolean isDiseased) {
        this.isDiseased = isDiseased;
    }

    public void infectCell(boolean diseasable) {
        if (diseasable) {
            Random rand = Randomizer.getRandom();
            if (rand.nextDouble() <= CELL_DISEASE_PROB) {
                isDiseased = true;
            }
        }
    }

    /**
     * Spreads the disease to adjacent cells based on a random chance.
     * 
     * @param field           The field where the cell is located.
     * @param currentLocation The current location of the diseased cell in the
     *                        field.
     */
    public void spreadDisease(Field field, Location currentLocation) {
        List<Location> adjacentLocations = field.adjacentLocations(currentLocation); // Get neighboring locations

        // Iterate through each adjacent location
        for (Location loc : adjacentLocations) {
            Cell neighbor = field.getObjectAt(loc); // Get the neighboring cell
            if (neighbor != null && !neighbor.getDiseased()) {
                Random rand = Randomizer.getRandom();
                double infectionChance = rand.nextDouble();
                // Spread the disease to the neighbor based on the infection chance
                if (infectionChance <= CELL_DISEASE_PROB) {
                    neighbor.setDiseased(true);
                }
            }
        }
    }
}