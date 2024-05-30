/**
 * Symbiosis is any long-term relationship between two different organisms.
 * Implement two forms of life that either have a mutualistic or parasitic
 * relationship. In a mutualistic relationship both life forms benefit while in
 * a parasitic relationship only one benefits.
 *
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */

public class Symbiosis {
	private Cell host; // The host cell in the symbiotic relationship.
	private Cell symbiont; // The symbiont cell in the symbiotic relationship.

	/**
	 * Constructor for creating a symbiotic relationship between two cells.
	 *
	 * @param host     The host cell of the symbiotic relationship.
	 * @param symbiont The symbiont cell of the symbiotic relationship.
	 */
	public Symbiosis(Cell host, Cell symbiont) {
		this.host = host;
		this.symbiont = symbiont;
	}

	/**
	 * Returns the host cell in the symbiotic relationship.
	 * 
	 * @return The host cell.
	 */
	public Cell getHost() {
		return host;
	}

	/**
	 * Returns the symbiont cell in the symbiotic relationship.
	 * 
	 * @return The symbiont cell.
	 */
	public Cell getSymbiont() {
		return symbiont;
	}

	/**
	 * Sets or updates the host cell in the symbiotic relationship.
	 * 
	 * @param host The new host cell.
	 */
	public void setHost(Cell host) {
		this.host = host;
	}

	/**
	 * Sets or updates the symbiont cell in the symbiotic relationship.
	 * 
	 * @param symbiont The new symbiont cell.
	 */
	public void setSymbiont(Cell symbiont) {
		this.symbiont = symbiont;
	}
}