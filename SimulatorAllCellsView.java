import javafx.stage.Stage;

/**
 * A simulation view that runs the Life Simulator with all available cell types.
 * This class allows for observing and analyzing the behavior of multiple cell
 * types in a shared environment, facilitating the study of interactions and
 * dynamics between different life forms.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class SimulatorAllCellsView extends SimulatorView {
    
    /**
     * Initializes the simulation with all available cell types. Populates the
     * simulation field with a diverse mix of cells, representing different forms of
     * life.
     */
    protected void initializeSimulation() {
        this.simulator = new Simulator();
        simulator.populate(); // Populate the simulator with a mix of all cell types.
    }

    /**
     * Starts the JavaFX application. Sets up the main window and its components for
     * this specific simulation view.
     *
     * @param stage The primary stage for this application, onto which the
     *              application scene can be set.
     */
    public void start(Stage stage) {
        super.start(stage);
    }

    /**
     * The main method to launch the JavaFX application. This method is the entry
     * point for the application when run.
     *
     * @param args Command-line arguments passed to the application. Not used in
     *             this implementation.
     */
    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application.
    }
}