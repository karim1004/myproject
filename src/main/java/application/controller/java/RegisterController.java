package application.controller.java;

import hotel.database.HotelDatabase;
import hotel.model.Gender;
import hotel.people.Guest;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class RegisterController {

    @FXML private TextField    txtFirstName;
    @FXML private TextField    txtLastName;
    @FXML private TextField    txtUsername;
    @FXML private TextField    txtAddress;
    @FXML private DatePicker   dpDob;
    @FXML private ComboBox<String> cmbGender;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtConfirmPass;
    @FXML private Label        lblStatus;

    @FXML
    public void initialize() {
        cmbGender.getItems().addAll("Male", "Female");
    }

    @FXML
    public void handleRegistration(ActionEvent event) {
        String firstName  = txtFirstName.getText().trim();
        String lastName   = txtLastName.getText().trim();
        String username   = txtUsername.getText().trim();
        String address    = txtAddress.getText().trim();
        String pass       = txtPassword.getText();
        String confirm    = txtConfirmPass.getText();

        // ── Validation ────────────────────────────────────────────────────────
        if (firstName.isEmpty() || lastName.isEmpty() || username.isEmpty() ||
                pass.isEmpty() || dpDob.getValue() == null || cmbGender.getValue() == null) {
            setError("All fields are required.");
            return;
        }
        if (pass.length() < 6) {
            setError("Password must be at least 6 characters.");
            return;
        }
        if (!pass.equals(confirm)) {
            setError("Passwords do not match.");
            return;
        }
        if (HotelDatabase.usernameExists(username)) {
            setError("Username already taken. Please choose another.");
            return;
        }

        Gender gender = cmbGender.getValue().equals("Male") ? Gender.MALE : Gender.FEMALE;

        Guest newGuest = new Guest(
                username, pass, dpDob.getValue(),
                0.0,
                address.isEmpty() ? "Not Provided" : address,
                gender, "Standard");

        HotelDatabase.addGuest(newGuest);

        lblStatus.setStyle("-fx-text-fill: #27ae60; -fx-font-weight: bold;");
        lblStatus.setText("Account created! Redirecting to login...");

        // Auto-navigate back to login after short delay
        javafx.animation.PauseTransition pause = new javafx.animation.PauseTransition(
                javafx.util.Duration.seconds(1.5));
        pause.setOnFinished(e -> handleBackToLogin(event));
        pause.play();
    }

    @FXML
    public void handleBackToLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("LuxStay — Login");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void setError(String msg) {
        lblStatus.setStyle("-fx-text-fill: #e74c3c; -fx-font-weight: bold;");
        lblStatus.setText(msg);
    }
}
