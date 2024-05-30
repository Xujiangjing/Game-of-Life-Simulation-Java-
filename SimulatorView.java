import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Group;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael & Jiangjing, Xu &
 *         Hongyuan, Zhao
 * @version 2024.02.28
 */
public abstract class SimulatorView extends Application {
	// Common fields
	protected static final int GRID_WIDTH = 100;
	protected static final int GRID_HEIGHT = 80;
	protected static final int WIN_WIDTH = 700;
	protected static final int WIN_HEIGHT = 600;
	protected static final Color EMPTY_COLOR = Color.WHITE;

	protected final String GENERATION_PREFIX = "Generation: ";
	protected final String POPULATION_PREFIX = "Population: ";
	protected Label genLabel, population, infoLabel;

	protected FieldCanvas fieldCanvas;
    protected FieldStats stats;
    protected Simulator simulator;
    protected Stage stage;
    protected boolean isSimulationRunning;

	// Components used for the side part
	private final String TEMPERATURE_PREFIX = "Temperature: ";
	private final String TIME_PREFIX = "Time: ";
	private final String DISEASENUM_PREFIX = "Number of Disease Alive Cells: ";
	private Label temperatureLabel, timeLabel, diseaseNumLabel;
	private Button simulateOneGenBtn, resetBtn, simulateLongRun;

	@Override
	public void start(Stage stage) {
		// Common initialization logic here
		stats = new FieldStats();
		fieldCanvas = new FieldCanvas(WIN_WIDTH - 200, WIN_HEIGHT - 100);
		fieldCanvas.setScale(GRID_HEIGHT, GRID_WIDTH);
		simulator = new Simulator();
		initializeSimulation();
		// Initialize labels and buttons
		Group root = new Group();

		genLabel = new Label(GENERATION_PREFIX);
		infoLabel = new Label("  ");
		population = new Label(POPULATION_PREFIX);
		temperatureLabel = new Label(TEMPERATURE_PREFIX);
		timeLabel = new Label(TIME_PREFIX);
		diseaseNumLabel = new Label(DISEASENUM_PREFIX);

		simulateOneGenBtn = new Button("Simulate One Generation");
		resetBtn = new Button("Reset");
		simulateLongRun = new Button("Simulate Long Run");

		// Event handlers for buttons
		simulateOneGenBtn.setOnAction(event -> {
			isSimulationRunning = true;
			simulate(1);
		});
		resetBtn.setOnAction(event -> {
			isSimulationRunning = false; // Stop the simulation
			reset();
		});
		simulateLongRun.setOnAction(event -> {
			isSimulationRunning = true;
			simulate(5000);
		});

		BorderPane bPane = new BorderPane();

		HBox infoPane = new HBox();
		infoPane.setSpacing(10);
		infoPane.getChildren().addAll(genLabel, infoLabel);

		// Create VBox for side part
		VBox sideBar = new VBox(10); // 10 is the spacing between elements
		sideBar.getChildren().addAll(temperatureLabel, timeLabel);

		VBox bottomBar = new VBox(10);
		bottomBar.getChildren().addAll(population, diseaseNumLabel);

		VBox leftBar = new VBox(10);
		leftBar.getChildren().addAll(simulateOneGenBtn, resetBtn, simulateLongRun);

		bPane.setTop(infoPane);
		bPane.setCenter(fieldCanvas);
		bPane.setBottom(bottomBar);
		bPane.setRight(sideBar);
		bPane.setLeft(leftBar);

		root.getChildren().add(bPane);
		Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT);

		// Set minimum window size
		stage.setMinWidth(WIN_WIDTH + 200); // Adjusted to accommodate sidebar
		stage.setMinHeight(WIN_HEIGHT);

		stage.setScene(scene);
		stage.setTitle("Life Simulation");
		updateCanvas(simulator.getGeneration(), simulator.getField(), simulator.getTemperature(),
				simulator.getTimeString(), simulator.getDiseaseAliveCells());
		stage.show();
	}

	/**
	 * Display a short information label at the top of the window.
	 */
	protected void setInfoText(String text) {
		infoLabel.setText(text);
	}

	/**
	 * Show the current status of the field.
	 * 
	 * @param generation The current generation.
	 * @param field      The field whose status is to be displayed.
	 */
	protected void updateCanvas(int generation, Field field, String temperature, String time,
			double diseaseAliveCells) {
		genLabel.setText(GENERATION_PREFIX + generation);
		temperatureLabel.setText(TEMPERATURE_PREFIX + temperature);
		timeLabel.setText(TIME_PREFIX + time);
		diseaseNumLabel.setText(DISEASENUM_PREFIX + diseaseAliveCells);
		stats.reset();

		for (int row = 0; row < field.getDepth(); row++) {
			for (int col = 0; col < field.getWidth(); col++) {
				Cell cell = field.getObjectAt(row, col);

				if (cell != null && cell.isAlive()) {
					stats.incrementCount(cell.getClass());
					fieldCanvas.drawMark(col, row, cell.getColor());
				} else {
					fieldCanvas.drawMark(col, row, EMPTY_COLOR);
				}
			}
		}

		stats.countFinished();
		population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
	}

	/**
	 * Determine whether the simulation should continue to run.
	 * 
	 * @return true If there is more than one species alive.
	 */
	protected boolean isViable(Field field) {
		return stats.isViable(field);
	}

	/**
	 * Run the simulation from its current state for the given number of
	 * generations. Stop before the given number of generations if the simulation
	 * ceases to be viable.
	 * 
	 * @param numGenerations The number of generations to run for.
	 */
	protected void simulate(int numGenerations) {
		new Thread(() -> {

			for (int gen = 1; gen <= numGenerations && isSimulationRunning; gen++) {
				simulator.simOneGeneration();
				simulator.delay(500);
				Platform.runLater(() -> {
					updateCanvas(simulator.getGeneration(), simulator.getField(), simulator.getTemperature(),
							simulator.getTimeString(), simulator.getDiseaseAliveCells());
				});
			}

		}).start();
	}

	/**
	 * Reset the simulation to a starting position.
	 */
	protected void reset() {
		isSimulationRunning = false; // Ensure the simulation is stopped
		this.simulator = new Simulator();
		stats = new FieldStats();
		initializeSimulation();
		updateCanvas(simulator.getGeneration(), simulator.getField(), simulator.getTemperature(),
				simulator.getTimeString(), simulator.getDiseaseAliveCells());
	}

	// Abstract method to be implemented by derived classes
	protected abstract void initializeSimulation();
}
