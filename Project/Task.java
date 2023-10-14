import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

class Task {

	private String taskName;
	private String taskDescription;
	private String status;
	private String expectedHours;
	private String actualHours;
	private String assigned;

	public Task(String taskName, String status, String taskDescription, String expectedHours, String actualHours,String assigned) {
		this.taskName = taskName;
		this.status = status;
		this.taskDescription = taskDescription;
		this.expectedHours = expectedHours;
		this.actualHours = actualHours;
		this.assigned = assigned;
	}

	public Task()
	{
		
	}
	
	public String getAssigned() {
		return assigned;
	}

	public void setAssigned(String assigned) {
		this.assigned = assigned;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskDescription() {
		return taskDescription;
	}

	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getExpectedHours() {
		return expectedHours;
	}

	public void setExpectedHours(String expectedHours) {
		this.expectedHours = expectedHours;
	}

	public String getActualHours() {
		return actualHours;
	}

	public void setActualHours(String actualHours) {
		this.actualHours = actualHours;
	}
	
	public StringProperty taskDescProperty() {
		return new SimpleStringProperty(taskDescription);
	}
	
	public StringProperty taskNameProperty() {
		return new SimpleStringProperty(taskName);
	}
	
	public StringProperty statusProperty() {
		return new SimpleStringProperty(status);
	}

	public StringProperty timeProperty() {
		return new SimpleStringProperty(expectedHours);
	}

	public StringProperty actualtimeProperty() {
		return new SimpleStringProperty(actualHours);
	}
	
	public StringProperty assignedProperty() {
		return new SimpleStringProperty(assigned);
	}
}
