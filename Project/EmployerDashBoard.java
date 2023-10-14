import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class EmployerDashBoard {
	private TableView<Task> taskTable;
	private Button addButton;
	private Button updateButton;
	private Button logoutButton;
	private ObservableList<Task> tasks;
	private Login mainLogin;
	private UserDetails dtls;

	public EmployerDashBoard(Login mainLogin, UserDetails dtls) {
		this.mainLogin = mainLogin;
		this.dtls = dtls;
	}

	public void dashBoard(Stage primaryStage) {

		// Create task table
		MySqlConnection conn = new MySqlConnection();
		tasks = conn.getTasks("all");
		taskTable = new TableView<>();
		taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

		// Add new columns
		TableColumn<Task, String> taskDescColumn = new TableColumn<>("Task Description");
		taskDescColumn.setCellValueFactory(data -> data.getValue().taskDescProperty());
		TableColumn<Task, String> timecol = new TableColumn<>("Expected Hours");
		timecol.setCellValueFactory(data -> data.getValue().timeProperty());
		TableColumn<Task, String> taskNameColumn = new TableColumn<>("Task Name");
		taskNameColumn.setCellValueFactory(data -> data.getValue().taskNameProperty());
		TableColumn<Task, String> statusColumn = new TableColumn<>("Status");
		statusColumn.setCellValueFactory(data -> data.getValue().statusProperty());
		TableColumn<Task, String> acttimecol = new TableColumn<>("Actual Hours");
		acttimecol.setCellValueFactory(data -> data.getValue().actualtimeProperty());
		TableColumn<Task, String> assign_col = new TableColumn<>("Assigned To");
		assign_col.setCellValueFactory(data -> data.getValue().assignedProperty());
		taskTable.getColumns().addAll(taskNameColumn, taskDescColumn, statusColumn, timecol, acttimecol,assign_col);
		taskTable.getItems().addAll(tasks);

		
		updateButton = new Button("Update");
		updateButton.setOnAction(event -> {
				Task selectedTask = taskTable.getSelectionModel().getSelectedItem();
			    if (selectedTask != null) {
			        TaskEditor taskEditor = new TaskEditor(selectedTask);
			        Task updatedTask = taskEditor.updateTask();
			        if (updatedTask != null) {
			            taskTable.refresh();
			        }
			        conn.updateTask(updatedTask);
			    }
		});
		
		addButton = new Button("Add");
		addButton.setOnAction(event -> {
			        TaskEditor taskEditor = new TaskEditor();
			        Task addTask = taskEditor.addTask();
			        conn.addingTask(addTask);
			        tasks.add(addTask);
			        taskTable.getItems().clear();
			        taskTable.getItems().addAll(tasks);
			        taskTable.refresh();
		});

		// Create logout button
		logoutButton = new Button("Logout");
		logoutButton.setOnAction(event -> {
			mainLogin.showLoginScreen(primaryStage);
		});

		Label welcomeLabel = new Label("Welcome,"+dtls.getUserName());
		// Create task dashboard layout
		VBox dashboardBox = new VBox();
		HBox buttons = new HBox(20,addButton,updateButton,logoutButton);
		buttons.setAlignment(Pos.BOTTOM_RIGHT);
		dashboardBox.setSpacing(10);
		dashboardBox.setPadding(new Insets(10, 10, 10, 10));
		dashboardBox.getChildren().addAll(welcomeLabel,
				new VBox(taskTable,buttons));

		// Create scene
		Scene scene = new Scene(new BorderPane(dashboardBox), 800, 600);

		// Set scene and show stage
		primaryStage.setScene(scene);
		primaryStage.show();
	}

}
