import javafx.scene.paint.Color;
import java.util.List;

/**
 * Abstract class representing the shared characteristics of all forms of life.
 * Includes features like aging, parasitism, mutualism, and disease
 * susceptibility.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael & Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */

public abstract class Cell {
    private boolean alive;
    private boolean nextAlive; // The state of the cell in the next iteration
    private Field field;
    private Location location;
    private Color color = Color.WHITE;

    private double age; // Current age of the cell.
    private double maxAge; // Every cell have a max age, they will die when reaching.
    private boolean diseasable; // Some cell can have disease, some can't.
    private boolean isParasitizable; // Indicates if the cell can be parasitized.
    private boolean isParasiteHost; // Indicates if the cell can be a host for a parasite.
    private boolean isMutualistic; // Indicates if the cell can engage in mutualistic relationships.
    private boolean isMutualismHost; // Indicates if the cell can be a host in a mutualistic relationship.
    protected Disease disease;
    protected CellRecovery cellRecovery;

    /**
     * Create a new cell at location in field.
     *
     * @param field      The field currently occupied.
     * @param location   The location within the field.
     * @param col        The color of the cell.
     * @param diseasable Whether the cell is susceptible to diseases.
     */
    public Cell(Field field, Location location, Color col, boolean diseasable) {
        alive = true;
        nextAlive = false;
        this.field = field;
        this.diseasable = false;
        this.disease = new Disease(); // Initialize the Disease instance
        this.cellRecovery = new CellRecovery();
        setLocation(location);
        setColor(col);
        age = 0;
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the next
     * generation.
     * 
     * @param newCells List of new cells generated in the current generation.
     */
    abstract public void act(List<Cell> newCell);

    /**
     * Check whether the cell is alive or not.
     * 
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    protected void setNextState(boolean value) {
        nextAlive = value;
    }

    /**
     * Changes the state of the cell
     */
    protected void updateState() {
        alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    protected void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     * 
     * @return color
     */
    protected Color getColor() {
        return color;
    }

    /**
     * Return the cell's location.
     * 
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * 
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * 
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }

    /**
     * Set the cell's max age
     * 
     * @param maxAge
     */
    protected void setMaxAge(double maxAge) {
        this.maxAge = maxAge;
    }

    /**
     * Set the cell's age
     * 
     * @param age
     */
    protected void setAge(double age) {
        this.age = age;
    }

    /**
     * Return the cell's age.
     * 
     * @return age
     */
    protected double getAge() {
        return age;
    }

    /**
     * Return max age
     * 
     * @return maxAge
     */
    protected double getMaxAge() {
        return maxAge;
    }

    /**
     * Update age
     */
    protected void updataAge() {
        age++;
    }

    /**
     * Check the age, if current age is larger than max age, the cell will be dead.
     */
    protected void checkAge() {
        if (age > maxAge) {
            setDead();
        }
    }

    /**
     * Set the parasitism characteristics of the cell.
     * 
     * @param isParasitizable Indicates if the cell can be parasitized.
     * @param isParasiteHost  Indicates if the cell can be a host for parasites.
     */
    protected void setParasitism(boolean isParasitizable, boolean isParasiteHost) {
        this.isParasitizable = isParasitizable;
        this.isParasiteHost = isParasiteHost;
    }

    /**
     * Get the cell whether can be parasitic
     * 
     * @return boolean isParasitizable
     */
    protected boolean getParasitism() {
        return isParasitizable;
    }

    /**
     * Set the mutualism characteristics of the cell.
     * 
     * @param isMutualistic   Indicates if the cell can engage in mutualistic
     *                        relationships.
     * @param isMutualismHost Indicates if the cell can be a host in mutualistic
     *                        relationships.
     */
    protected void setMutualism(boolean isMutualistic, boolean isMutualismHost) {
        this.isMutualistic = isMutualistic;
        this.isMutualismHost = isMutualismHost;
    }

    /**
     * Get the cell whether can be mutualistic
     * 
     * @return boolean isMutualistic
     */
    protected boolean getMutualism() {
        return isMutualistic;
    }

    /**
     * Set the cell whether can have disease
     * 
     * @param diseasable
     */
    protected void setDiseasable(boolean diseasable) {
        this.diseasable = diseasable;
    }

    /**
     * Return diseasable
     * 
     * @return boolean diseasable
     */
    protected boolean getDiseasable() {
        return diseasable;
    }

    /**
     * The action of cell which infect disease
     * 
     */
    public void infectedAct() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        if (age + 100 < maxAge) {
            setAge(age + 100); // The cell age is reduced 100
            if (neighbours.size() == 3) {
                setNextState(true);
            }
        } else {
            setDead();
        }
    }

    /**
     * Set the cell whether is diseased
     * 
     * @param boolean isDiseased
     */
    protected void setDiseased(boolean diseased) {
        disease.setDiseased(diseased);
    }

    /**
     * Get the cell whether is diseased
     * 
     * @return isDiseased
     */
    protected boolean getDiseased() {
        return disease.isDiseased();
    }
}