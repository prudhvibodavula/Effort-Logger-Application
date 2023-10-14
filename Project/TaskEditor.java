import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TaskEditor {

    private Task task;
    private TextField taskNameField;
    private TextField taskDescField;
    private TextField timeField;
    private TextField actTimeField;
    private TextField taskStatus;
    private TextField assignedTo;
    
    public TaskEditor(Task task) {
        this.task = task;
    }
    
    public TaskEditor() {
     
    }

    public Task updateTask() {
        Stage stage = new Stage();
        stage.setTitle("Edit Task");

        // Create task description input
        Label taskDescLabel = new Label("Task Description:");
        taskDescField = new TextField(task.getTaskDescription());
        
        Label taskStatusLabel = new Label("Task Status:");
        taskStatus = new TextField(task.getStatus());

        // Create expected time input
        Label timeLabel = new Label("Expected Hours:");
        timeField = new TextField(task.getExpectedHours());

        // Create actual time input
        Label actTimeLabel = new Label("Actual Hours:");
        actTimeField = new TextField(task.getActualHours());

        // Create save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            task.setTaskDescription(taskDescField.getText());
            task.setStatus(taskStatus.getText());
            task.setExpectedHours(timeField.getText());
            task.setActualHours(actTimeField.getText());
            stage.close();
        });

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            stage.close();
        });

        // Create layout
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.addRow(0, taskDescLabel, taskDescField);
        grid.addRow(1, taskStatusLabel, taskStatus);
        grid.addRow(2, timeLabel, timeField);
        grid.addRow(3, actTimeLabel, actTimeField);
        Label taskNameLabel = new Label(task.getTaskName());
        HBox buttons = new HBox(20, saveButton, cancelButton);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        VBox layout = new VBox(20, taskNameLabel, grid, buttons);

        // Create scene
        Scene scene = new Scene(layout);

        // Set stage properties
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // Show stage and wait for it to close
        stage.showAndWait();

        return task;
    }
    
    
    public Task addTask() {
        Stage stage = new Stage();
        stage.setTitle("Add Task");
        task = new Task();

        // Create task description input
        
        Label taskName = new Label("Task Name:");
        taskNameField = new TextField();
        
        Label taskDescLabel = new Label("Task Description:");
        taskDescField = new TextField();
        
        Label taskStatusLabel = new Label("Task Status:");
        taskStatus = new TextField();

        // Create expected time input
        Label timeLabel = new Label("Expected Hours:");
        timeField = new TextField();

        // Create actual time input
        Label actTimeLabel = new Label("Actual Hours:");
        actTimeField = new TextField();
        
        Label assignedToLabel = new Label("Assigned To:");
        assignedTo = new TextField();

        // Create save button
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
        	task.setTaskName(taskNameField.getText());
            task.setTaskDescription(taskDescField.getText());
            task.setStatus(taskStatus.getText());
            task.setExpectedHours(timeField.getText());
            task.setActualHours(actTimeField.getText());
            task.setAssigned(assignedTo.getText());
            stage.close();
        });

        // Create cancel button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            stage.close();
        });

        // Create layout
        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 20, 20, 20));
        grid.addRow(0, taskName, taskNameField);
        grid.addRow(1, taskDescLabel, taskDescField);
        grid.addRow(2, taskStatusLabel, taskStatus);
        grid.addRow(3, timeLabel, timeField);
        grid.addRow(4, actTimeLabel, actTimeField);
        grid.addRow(5, assignedToLabel, assignedTo);
        HBox buttons = new HBox(20, saveButton, cancelButton);
        buttons.setAlignment(Pos.BOTTOM_RIGHT);
        VBox layout = new VBox(20, grid, buttons);

        // Create scene
        Scene scene = new Scene(layout);

        // Set stage properties
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);

        // Show stage and wait for it to close
        stage.showAndWait();

        return task;
    }
}
