/**
 * Represents a parasitic relationship where only the symbiont benefits at the
 * expense of the host. This class extends the Symbiosis class, indicating a
 * specific type of symbiotic relationship. In parasitism, the symbiont benefits
 * while the host is harmed.
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class Parasitism extends Symbiosis {
    private Cell host; // The host cell in the parasitic relationship.
    private Cell symbiont; // The symbiont cell in the parasitic relationship.

    /**
     * Constructor for creating a Parasitism relationship. Sets up the parasitic
     * relationship between two cells - a host and a symbiont.
     *
     * @param host     The host cell that will be harmed.
     * @param symbiont The symbiont cell that will benefit.
     */
    public Parasitism(Cell host, Cell symbiont) {
        super(host, symbiont);
        this.host = host;
        this.symbiont = symbiont;
    }

    /**
     * Defines the behavior of the cells in a parasitic relationship. The host is
     * harmed and the symbiont benefits if they are neighbors. Harm to the host is
     * modeled as setting its state to false (possibly indicating poor health) and
     * increasing its age, potentially leading to its death. The symbiont benefits
     * by having its age reduced, thereby extending its lifespan.
     */
    public void parasitic() {
        Location hostLoc = host.getLocation();
        Location symbiontLoc = symbiont.getLocation();

        // Check if host and symbiont are adjacent to each other.
        if (Field.areNeighbours(hostLoc, symbiontLoc)) {
            // If they are neighbors, the symbiont benefits and the host is harmed.
            host.setNextState(false); // Update the state of the host.

            // Increase the age of the host, potentially leading to its death.
            Double currentHostAge = host.getAge();
            if (currentHostAge + 100 <= host.getMaxAge()) {
                host.setAge(currentHostAge + 100);
            } else if (currentHostAge <= host.getMaxAge()) {
                host.setDead(); // Host dies if its age exceeds the maximum age.
            }

            // Apply benefit to the symbiont by reducing its age.
            symbiont.setNextState(true); // Update the state of the symbiont.
            Double currentSymbiontAge = symbiont.getAge();
            symbiont.setAge(Math.max(0, currentSymbiontAge - 100)); // Ensure age doesn't go below 0.
        }
    }
}