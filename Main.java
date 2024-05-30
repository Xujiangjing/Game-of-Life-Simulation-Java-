import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * A JavaFX application for selecting and launching different types of life simulations.
 * Users can choose to simulate all cell types or focus on a single specific cell type.
 * The Main launches the corresponding simulation view.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class Main extends Application{
    private String selectedCellType; // Stores the type of cell selected by the user.
    
    /**
     * The main method to launch the JavaFX application.
     * @param args Command-line arguments passed to the application.
     */
    public static void main(String[] args) {
        Application.launch(); // Launch the JavaFX application.
    }
    
    /**
     * Starts the JavaFX application and sets up the primary stage with its components.
     * @param primaryStage The primary stage for this application, onto which the application scene can be set.
     */
    @Override
    public void start(Stage primaryStage) { 
        primaryStage.setTitle("Life Simulation");

        // Label and ChoiceBox
        Label label1 = new Label("Please choose the simulation");

        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Simulate all cells",
                                     "Simulate Single cell");

        // Buttons
        Button button1 = new Button("OK");
        Button button2 = new Button("Cancel");
        
        // Event Handler for the OK Button
        button1.setOnAction(event -> {
             String selectedOption = choiceBox.getValue();
             if (selectedOption != null) {
                 if (selectedOption.equals("Simulate Single cell")) {
                     // Show cell selection window as modal
                     CellSelection cellSelection = new CellSelection();
                     selectedCellType = cellSelection.display(); // Display the window and get the selected type
                     if (selectedCellType != null) {
                         SimulatorSingleCellView simulatorSingleView = new SimulatorSingleCellView(selectedCellType);
                         simulatorSingleView.start(new Stage());
                     }                  
                     primaryStage.close(); // Close the selection window
                        
                } else if(selectedOption.equals("Simulate all cells")){
                    SimulatorAllCellsView simulatorAllCellsView = new SimulatorAllCellsView();
                    simulatorAllCellsView.start(new Stage());
                    primaryStage.close(); // Close the selection window
                }
            } else {
                // Handle the case when no option is selected
                System.out.println("No option selected");
            }
        });
        
        // Event Handler for the Cancel Button
        button2.setOnAction(event -> {
            primaryStage.close(); // Close the window
        });

        // HBox for buttons
        HBox buttonBox = new HBox(10); // 10 is the spacing between buttons
        buttonBox.getChildren().addAll(button1, button2);
        buttonBox.setAlignment(Pos.CENTER); // Center align the buttons

        // VBox to stack ChoiceBox and buttonBox
        VBox centerBox = new VBox(10); // 10 is the spacing between elements
        centerBox.getChildren().addAll(choiceBox, buttonBox);
        centerBox.setAlignment(Pos.CENTER); // Center align the VBox contents

        // BorderPane for overall layout
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(label1);
        borderPane.setCenter(centerBox); // Place VBox in the center

        // Aligning the label to the center (top of BorderPane)
        BorderPane.setAlignment(label1, Pos.CENTER);

        // Set scene
        Scene scene = new Scene(borderPane, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}