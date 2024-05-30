import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

/**
 * Class for creating and displaying a selection window where users can choose a
 * type of cell. Once a selection is made, the chosen cell type is returned.
 * 
 * @author Jiangjing, Xu & Hongyuan, Zhao
 * @version 2024.02.28
 */
public class CellSelection {
    private String selectedCellType;

    /**
     * Displays a modal window for cell type selection and returns the selected cell
     * type.
     * 
     * @return String representing the selected cell type.
     */
    public String display() {
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL); // Block input events to other windows
        stage.setTitle("Cell Type Selection"); // Set the title of the window

        // Label and ChoiceBox
        Label label1 = new Label("Please choose the cell");
        ChoiceBox<String> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll("Mycoplasma", "MyFungiChangeColor", "MyEvolvingCellChangeBehaviors",
                "NonDeterministicCells");

        // Buttons
        Button button1 = new Button("OK");
        Button button2 = new Button("Cancel");

        // Event Handler for the OK Button
        button1.setOnAction(event -> {
            String selectedOption = choiceBox.getValue(); // Get the selected item from the ChoiceBox
            if (selectedOption != null) {
                // Perform the action based on the selected option
                selectedCellType = selectedOption;
                getSelectedCellType();
                stage.close(); // Close the window
            } else {
                // Handle the case when no option is selected
                System.out.println("No option selected");
            }
        });

        // Event Handler for the Cancel Button
        button2.setOnAction(event -> {
            stage.close(); // Close the window
        });

        // HBox for buttons
        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(button1, button2);
        buttonBox.setAlignment(Pos.CENTER); // Center align the buttons

        // VBox to stack ChoiceBox and buttonBox
        VBox centerBox = new VBox(10);
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
        stage.setScene(scene);
        stage.showAndWait(); // Show the window and wait for it to be closed

        return selectedCellType; // Return the selected cell type
    }

    /**
     * Return the selected cell type.
     * 
     * @return The selected cell type.
     */
    public String getSelectedCellType() {
        return selectedCellType;
    }
}