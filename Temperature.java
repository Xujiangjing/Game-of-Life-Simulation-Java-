import java.util.Random;

/**
 * Represents a simplified weather model focusing on temperature conditions. It
 * can simulate temperature changes as either cold, hot, or warm, changing at
 * random intervals.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class Temperature {
    private static final Random rand = Randomizer.getRandom(); // Random number generator.
    private static final int MAX_TEMPERATURE_LENGTH = 3; // Maximum duration for a temperature condition.

    private int currentTemperatureStep; // Tracks the remaining steps for the current temperature condition.
    private boolean isCold; // Flag indicating whether the current condition is cold.
    private boolean isHot; // Flag indicating whether the current condition is hot.
    private boolean isWarm; // Flag indicating whether the current condition is warm.

    /**
     * Constructor for Temperature. Initializes the temperature to a random
     * condition.
     */
    public Temperature() {
        randomiseTemperature(); // Set an initial random temperature.
    }

    /**
     * Randomly sets the temperature condition (cold, hot, or warm) and its
     * duration.
     */
    private void randomiseTemperature() {
        // Reset all temperature condition flags.
        isCold = isHot = isWarm = false;

        // Randomly select a weather condition.
        int weather = rand.nextInt(4);
        switch (weather) {
        case 1:
            isCold = true; // Set the condition to cold.
            break;
        case 2:
            isHot = true; // Set the condition to hot.
            break;
        default:
            isWarm = true; // Set the condition to warm.
            break;
        }
        // Randomly determine the duration for this temperature condition.
        currentTemperatureStep = rand.nextInt(MAX_TEMPERATURE_LENGTH) + 1;
    }

    /**
     * Updates the temperature condition, potentially changing it if the duration
     * has elapsed.
     */
    public void update() {
        currentTemperatureStep--; // Decrement the step counter.
        if (currentTemperatureStep == 0) {
            randomiseTemperature(); // Randomize temperature again if the duration has elapsed.
        }
    }

    /**
     * Provides a description of the current temperature condition.
     * 
     * @return A string describing the current temperature ("Cold", "Hot", or
     *         "Warm").
     */
    public String getDescription() {
        if (isCold) {
            return "Cold";
        } else if (isHot) {
            return "Hot";
        }
        return "Warm"; // Default to "Warm" if neither cold nor hot.
    }
}