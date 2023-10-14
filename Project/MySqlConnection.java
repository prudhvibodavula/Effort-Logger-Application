import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MySqlConnection {
	 String JDBC_DRIVER = "com.mysql.jdbc.Driver";
     String DB_URL = "jdbc:mysql://localhost:3306/sys";
     String USER = "root";
     String PASS = "root";

    public ObservableList<Task> getTasks(String userName) {
        // JDBC driver name, database URL, username and password
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ObservableList<Task> tasks = FXCollections.observableArrayList();

        try {
            // Register JDBC driver
            Class.forName(JDBC_DRIVER);
            // Open a connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            // Execute a query
            stmt = conn.createStatement();
            String sql; 
            PreparedStatement statement = null;
            if(userName.equals("all"))
            {
            	sql  = "SELECT * FROM task";
                rs = stmt.executeQuery(sql);
            }
            else
            {
            	sql = "SELECT * FROM task WHERE taskassigned = ?";
            	statement = conn.prepareStatement(sql);
                statement.setString(1, userName);
                rs = statement.executeQuery();
            }
            
            while (rs.next()) {
            	Task ts = new Task();
                String taskName = rs.getString("TaskName");
                String description = rs.getString("TaskDesc");
                String expectedHours = rs.getString("ExpectedHours");
                String actualHours = rs.getString("ActualHours");
                String taskStatus = rs.getString("TaskStatus");
                String taskAssigned = rs.getString("TaskAssigned");
                ts.setTaskName(taskName);
                ts.setTaskDescription(description);
                ts.setExpectedHours(expectedHours);
                ts.setActualHours(actualHours);
                ts.setExpectedHours(expectedHours);
                ts.setAssigned(taskAssigned);
                ts.setStatus(taskStatus);
                tasks.add(ts);
            }

        } catch (SQLException se) {
            // Handle errors for JDBC
            se.printStackTrace();
        } catch (Exception e) {
            // Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return tasks;
    }
    
    public void addingTask(Task task)
    {
  
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement with parameters
            String sql = "INSERT INTO sys.task(TaskName,TaskDesc,ExpectedHours,ActualHours,TaskAssigned,TaskStatus) VALUES (?, ?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, task.getTaskName());
            pstmt.setString(2, task.getTaskDescription());
            pstmt.setString(3, task.getExpectedHours());
            pstmt.setString(4, task.getActualHours());
            pstmt.setString(5, task.getAssigned());
            pstmt.setString(6, task.getStatus());

            // Execute the SQL statement
            int rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateTask(Task task)
    {
  
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement with parameters
            String sql = "UPDATE task SET TaskDesc = ?, ExpectedHours = ?, ActualHours = ?, TaskAssigned = ? ,TaskStatus = ?  WHERE TaskName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(6, task.getTaskName());
            pstmt.setString(1, task.getTaskDescription());
            pstmt.setString(2, task.getExpectedHours());
            pstmt.setString(3, task.getActualHours());
            pstmt.setString(4, task.getAssigned());
            pstmt.setString(5, task.getStatus());

            // Execute the SQL statement
            int rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void updateTaskbyEmp(Task task)
    {
  
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Connect to the database
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            // Prepare the SQL statement with parameters
            String sql = "UPDATE task SET TaskDesc = ?, ExpectedHours = ?, ActualHours = ? ,TaskStatus = ?  WHERE TaskName = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(5, task.getTaskName());
            pstmt.setString(1, task.getTaskDescription());
            pstmt.setString(2, task.getExpectedHours());
            pstmt.setString(3, task.getActualHours());
            pstmt.setString(4, task.getStatus());

            // Execute the SQL statement
            int rows = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    	
}
