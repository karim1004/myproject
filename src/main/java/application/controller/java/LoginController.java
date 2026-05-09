package application.controller.java;

import hotel.database.HotelDatabase;
import hotel.model.Gender;
import hotel.people.Guest;
import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginController {

    @FXML private TextField     txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private Label         lblLoginError;

    @FXML private TextField     txtStaffId;
    @FXML private PasswordField txtStaffPass;
    @FXML private Label         lblStaffError;

    @FXML private VBox   guestPane;
    @FXML private VBox   staffPane;
    @FXML private Button btnTabGuest;
    @FXML private Button btnTabStaff;

    // ─── Tab Switching ────────────────────────────────────────────────────────
    @FXML public void showGuestTab(ActionEvent e) {
        guestPane.setVisible(true);  guestPane.setManaged(true);
        staffPane.setVisible(false); staffPane.setManaged(false);
        // Active = blue (btn-primary), inactive = outline
        btnTabGuest.getStyleClass().remove("btn-outline");
        btnTabGuest.getStyleClass().add("btn-primary");
        btnTabStaff.getStyleClass().remove("btn-primary");
        btnTabStaff.getStyleClass().add("btn-outline");
    }

    @FXML public void showStaffTab(ActionEvent e) {
        staffPane.setVisible(true);  staffPane.setManaged(true);
        guestPane.setVisible(false); guestPane.setManaged(false);
        // Active = blue (btn-primary), inactive = outline
        btnTabStaff.getStyleClass().remove("btn-outline");
        btnTabStaff.getStyleClass().add("btn-primary");
        btnTabGuest.getStyleClass().remove("btn-primary");
        btnTabGuest.getStyleClass().add("btn-outline");
    }

    // ─── Guest Login ──────────────────────────────────────────────────────────
    @FXML public void handleGuestLogin(ActionEvent event) {
        String user = txtUsername.getText().trim();
        String pass = txtPassword.getText();
        if (user.isEmpty() || pass.isEmpty()) {
            lblLoginError.setText("Please enter username and password.");
            return;
        }
        Guest found = HotelDatabase.findGuest(user, pass);
        if (found == null) {
            lblLoginError.setText("Invalid username or password.");
            return;
        }
        openDashboard(event, found);
    }

    // ─── Staff Login ──────────────────────────────────────────────────────────
    @FXML public void handleStaffLogin(ActionEvent event) {
        String user = txtStaffId.getText().trim();
        String pass = txtStaffPass.getText();
        if ("staff".equals(user) && "staff123".equals(pass)) {
            Guest staffGuest = new Guest("staff", "staff123", LocalDate.of(1985, 1, 1),
                    0, "Hotel HQ", Gender.MALE, "N/A");
            openDashboard(event, staffGuest);
        } else {
            lblStaffError.setText("Invalid staff credentials.");
        }
    }

    // ─── Register Link ────────────────────────────────────────────────────────
    @FXML public void showRegister(ActionEvent event) {
        switchScene(event, "/fxml/Register.fxml", "Hotel Reservation System");
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────
    private void openDashboard(ActionEvent event, Guest guest) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/GuestDashboard.fxml"));
            Parent root = loader.load();
            DashboardController dc = loader.getController();
            dc.setGuest(guest);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle("Hotel Reservation System");
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            lblLoginError.setText("Error loading dashboard.");
        }
    }

    private void switchScene(ActionEvent event, String fxml, String title) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.setTitle(title);
            stage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
