/**
 * Represents a mutualistic relationship between two life forms where both
 * benefit. This class is an extension of the Symbiosis class, indicating a
 * specific type of symbiotic relationship. In mutualism, both organisms
 * involved (host and symbiont) gain advantages from the association.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class Mutualism extends Symbiosis {
    private Cell host; // The host cell in the mutualistic relationship.
    private Cell symbiont; // The symbiont cell in the mutualistic relationship.

    /**
     * Constructor for creating a Mutualism relationship. This sets up the
     * mutualistic relationship between two cells - a host and a symbiont.
     *
     * @param host     The host cell in the mutualistic relationship.
     * @param symbiont The symbiont cell in the mutualistic relationship.
     */
    public Mutualism(Cell host, Cell symbiont) {
        super(host, symbiont); // Call to the superclass constructor to handle common setup.
        this.host = host;
        this.symbiont = symbiont;
    }

    /**
     * Defines the behavior of both cells in a mutualistic relationship. If the host
     * and the symbiont are neighbors, they both receive benefits. Benefits are
     * modeled as setting their state to true (possibly indicating health or
     * survival) and reducing their age, thereby extending their lifespan.
     */
    public void mutualistic() {
        Location hostLoc = host.getLocation();
        Location symbiontLoc = symbiont.getLocation();

        // Check if host and symbiont are adjacent to each other.
        if (Field.areNeighbours(hostLoc, symbiontLoc)) {
            // If they are neighbors, both benefit from the mutualistic relationship.
            host.setNextState(true);
            // Reduce the age of the host, if possible, to simulate the benefit of
            // mutualism.
            Double currentHostAge = host.getAge();
            host.setAge(Math.max(0, currentHostAge - 100));

            // Apply the same benefit to the symbiont.
            symbiont.setNextState(true);

            // Reduce the age of the symbiont, similar to the host.
            Double currentSymbiontAge = symbiont.getAge();
            symbiont.setAge(Math.max(0, currentSymbiontAge - 100));
        }
    }
}