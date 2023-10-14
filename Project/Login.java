import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Random;
import java.util.Scanner;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// Created by Aditya Sai Nune and Prudhvi Krishna Bodavula  23-Mar-2023

public class Login extends Application {

	private static final Random random = new Random();
	private static final int OTP_LENGTH = 6;
	SecretKey secretKey = null;
	String encryptedPassword = null;
	String encryptedPhoneNumber = null;
	String decryptedPassword = null;
	String decryptedPhoneNumber = null;
	String encryptedType = null;
	EmployeeDashboard smpld;
	EmployerDashBoard empld;
	UserDetails dtls;
	Login log;
	private static int login_attempts;
	
	public void showLoginScreen(Stage primaryStage) {
		GridPane loginGridPane = new GridPane();
		loginGridPane.setAlignment(Pos.CENTER);
		loginGridPane.setHgap(10);
		loginGridPane.setVgap(10);
		loginGridPane.setPadding(new Insets(25, 25, 25, 25));
		Scene loginScene = new Scene(loginGridPane, 400, 275);

		Label loginLabel = new Label("Login");
		loginLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		Label usernameLoginLabel = new Label("Username:");
		TextField usernameLoginTextField = new TextField();

		Label passwordLoginLabel = new Label("Password:");
		PasswordField passwordLoginTextField = new PasswordField();

		Button loginButton = new Button("Login");
		loginButton.setOnAction(e -> {
			String username = usernameLoginTextField.getText();
			String password = passwordLoginTextField.getText();
			try {
				dtls = checkLogin(username, password);
				
			if(dtls==null && login_attempts<2)
			{
				login_attempts++;
				showLoginScreen(primaryStage);	
			}
			else if(login_attempts==2)
			{
				showAlert("Error", "Too many attempts. Please check back later " + username);
				login_attempts=0;
				return;
			}
			else
			{
				login_attempts=0;
			}
			} catch (Exception e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			if (dtls!=null) {
				String phoneNumber = dtls.getPhoneNumber();
				if (phoneNumber == null) {
					showAlert("Error", "Phone number not found for username " + username);
				} else {
					 showOtpVerificationScreen(phoneNumber, primaryStage);
				}
			} else {
				showAlert("Error", "Incorrect username or password");
			}
		});

		Button registerLinkButton = new Button("Register");
		registerLinkButton.setOnAction(e -> {
			// Clear text fields
			usernameLoginTextField.clear();
			passwordLoginTextField.clear();

			// Show registration screen
			showRegistrationScreen(primaryStage);
		});

		HBox loginButtonBox = new HBox(10);
		loginButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
		loginButtonBox.getChildren().add(loginButton);
		loginButtonBox.getChildren().add(registerLinkButton);

		loginGridPane.add(loginLabel, 0, 0, 2, 1);
		loginGridPane.add(usernameLoginLabel, 0, 1);
		loginGridPane.add(usernameLoginTextField, 1, 1);
		loginGridPane.add(passwordLoginLabel, 0, 2);
		loginGridPane.add(passwordLoginTextField, 1, 2);
		loginGridPane.add(loginButtonBox, 1, 3);

		primaryStage.setTitle("Login");
		primaryStage.setScene(loginScene);
		primaryStage.show();
	}

	public void showRegistrationScreen(Stage primaryStage) {
		GridPane registrationGridPane = new GridPane();
		registrationGridPane.setAlignment(Pos.CENTER);
		registrationGridPane.setHgap(10);
		registrationGridPane.setVgap(10);
		registrationGridPane.setPadding(new Insets(25, 25, 25, 25));
		Scene registrationScene = new Scene(registrationGridPane, 400, 275);

		Label registrationLabel = new Label("Registration");
		registrationLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

		Label usernameLabel = new Label("Username:");
		TextField usernameTextField = new TextField();

		Label passwordLabel = new Label("Password:");
		PasswordField passwordTextField = new PasswordField();

		Label phoneNumberLabel = new Label("Phone Number:");
		TextField phoneNumberTextField = new TextField();
		Label userType = new Label("Type:");
		ComboBox<String> statusComboBox;
		statusComboBox = new ComboBox<>();
		statusComboBox.getItems().addAll("employer", "employee");

		Button registerButton = new Button("Register");
		registerButton.setOnAction(e -> {
			String username = usernameTextField.getText();
			String password = passwordTextField.getText();
			String phoneNumber = phoneNumberTextField.getText();
			String selectedValue = statusComboBox.getSelectionModel().getSelectedItem();

			// Store username and password in memory

			if (username.length() < 6) {
				showAlert("Error", "Username must be at least 6 characters long.");
				return;
			}

			File file = new File("data/"+username + ".txt");
			if (file.exists()) {
				showAlert("Error", "Username already exists.");
				return;
			}

			if (password.length() < 8) {
				showAlert("Error", "Password must be at least 8 characters long.");
				return;
			}

			if (phoneNumber.length() < 10) {
				showAlert("Error", "Phone number should be 10 digit long.");
				return;
			}

			try {
				writeToFile(username, password, phoneNumber, selectedValue);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// Clear text fields
			usernameTextField.clear();
			passwordTextField.clear();
			phoneNumberTextField.clear();

			// Show login screen
			showLoginScreen(primaryStage);
		});

		HBox registrationButtonBox = new HBox(10);
		registrationButtonBox.setAlignment(Pos.BOTTOM_RIGHT);
		registrationButtonBox.getChildren().add(registerButton);

		registrationGridPane.add(registrationLabel, 0, 0, 2, 1);
		registrationGridPane.add(usernameLabel, 0, 1);
		registrationGridPane.add(usernameTextField, 1, 1);
		registrationGridPane.add(passwordLabel, 0, 2);
		registrationGridPane.add(passwordTextField, 1, 2);
		registrationGridPane.add(phoneNumberLabel, 0, 3);
		registrationGridPane.add(phoneNumberTextField, 1, 3);
		registrationGridPane.add(userType, 0, 4);
		registrationGridPane.add(statusComboBox, 1, 4);
		registrationGridPane.add(registrationButtonBox, 1, 5);
		primaryStage.setScene(registrationScene);
	}

	@Override
	public void start(Stage primaryStage) throws Exception, NoSuchAlgorithmException {

		// Create login screen
		showLoginScreen(primaryStage);
	}

	public void showOtpVerificationScreen(String phoneNumber, Stage primaryStage) {
		// TODO: Implement OTP verification screen

		if (phoneNumber.isEmpty()) {
			showAlert("Missing Details", "Phone Number is required.");
			return;
		}
		// Generate a random OTP
		String otp = String.format("%0" + OTP_LENGTH + "d", random.nextInt((int) Math.pow(10, OTP_LENGTH)));
		Label phoneLabel = new Label("Phone Number:");
		RadioButton lastFourRadioButton = new RadioButton(
				"Last Four Digits: " + phoneNumber.substring(phoneNumber.length() - 4));
		ToggleGroup toggleGroup = new ToggleGroup();
		lastFourRadioButton.setToggleGroup(toggleGroup);
		lastFourRadioButton.setSelected(true);
		Label otpLabel = new Label("Enter OTP:");
		TextField otpTextField = new TextField();
		otpTextField.setDisable(true);
		Button verifyButton = new Button("Verify");
		verifyButton.setOnAction(e -> {
			verifyButton.setDisable(true);
			// Verify OTP and switch to empty screen with message
			if (otpTextField.getText().equals(otp)) {
				
				//showMainAppScreen(primaryStage);
				String type = dtls.getUserType();
				if (type != null && type.equals("employee")) {
					try {
						log = new Login();
						smpld = new EmployeeDashboard(log,dtls);
						smpld.dashBoard(primaryStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					try {
						log = new Login();
						empld = new EmployerDashBoard(log,dtls);
						empld.dashBoard(primaryStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} else {
				showAlert("OTP Verification Error", "Invalid OTP entered.");
			}
		});

		Button sendOtpButton = new Button("Send OTP");
		sendOtpButton.setOnAction(e -> {
			verifyButton.setDisable(false);
			otpTextField.setDisable(false);
			// Generate and send OTP
			try {
				OTPSender.sendOTP(phoneNumber, otp);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

		});

		// Create layout
		VBox vbox = new VBox(10);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(10));

		HBox buttonBox = new HBox(10);
		buttonBox.getChildren().addAll(sendOtpButton, verifyButton);
		buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
		vbox.getChildren().addAll(phoneLabel, lastFourRadioButton, otpLabel, otpTextField, buttonBox);
		// Create scene and show stage
		primaryStage.setScene(new Scene(vbox, 400, 300));
		primaryStage.setTitle("OTP Verification");
		primaryStage.show();

	}

	private void showAlert(String title, String message) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}


	public void writeToFile(String inputusername, String inputpassword, String phoneNumber, String userType)
			throws Exception {
		try {
			// Create a FileWriter object to write to the file

			secretKey = Encryption.generateSecretKey();
			encryptedPassword = Encryption.encrypt(inputpassword, secretKey);
			encryptedPhoneNumber = Encryption.encrypt(phoneNumber, secretKey);
			encryptedType = Encryption.encrypt(userType, secretKey);
			FileWriter writer = new FileWriter("data/"+inputusername + ".txt", true); // true for appending to file
			String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

			// Write the login details to the file
		
			writer.write("skey:" + encodedKey + "\n");
			writer.write("uname:" + inputusername + "\n");
			writer.write("pswd:" + encryptedPassword + "\n");
			writer.write("phoneNum:" + encryptedPhoneNumber + "\n");
			writer.write("userType:" + encryptedType);
			// Close the FileWriter object
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// checking file for username and password
	public UserDetails checkLogin(String uname, String passw) throws Exception {

		// Create a File object with the file name
		String fileName = "data/"+uname + ".txt"; // Replace with the actual file name
		File file = new File(fileName);
		String userName = null;
		String password = null;
		String phoneNumber = null;
		String userType = null;

		// Read the contents of the file and search for the username and password
		try {
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			String key = firstLine.substring(firstLine.indexOf("skey:") + 5);
			byte[] decodedKey = Base64.getDecoder().decode(key);
			SecretKey sKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(":");
				if (parts[0].contains("uname")) {
					userName = parts[1];
					continue;
				}
				String decrypted = Encryption.decrypt(parts[1], sKey);
				if (parts[0].contains("pswd"))
					password = decrypted;
				else if (parts[0].contains("phoneNum"))
					phoneNumber = decrypted;
				else if (parts[0].contains("userType"))
					userType = decrypted;
			}
			scanner.close();
			if (userName.equals(uname) && password.equals(passw)) {
				dtls = new UserDetails();
				dtls.setUserName(userName);
				dtls.setPhoneNumber(phoneNumber);
				dtls.setUserType(userType);
				return dtls;
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

}
