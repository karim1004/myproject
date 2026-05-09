package application.controller.java;

import hotel.database.HotelDatabase;
import hotel.model.ReservationStatus;
import hotel.people.Guest;
import hotel.reservations.Reservation;
import hotel.rooms.Room;
import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class DashboardController {

    @FXML private StackPane contentArea;
    @FXML private Button btnNavDashboard;
    @FXML private Button btnNavProfile;
    @FXML private Button btnNavRooms;
    @FXML private Button btnNavReservations;
    @FXML private Button btnNavPayment;

    private Guest currentGuest;

    public void setGuest(Guest guest) {
        this.currentGuest = guest;
        showDashboard(null);
    }

    // ─── Navigation ──────────────────────────────────────────────────────────
    @FXML public void showDashboard(ActionEvent e)     { setActive(btnNavDashboard);     buildDashboardPane(); }
    @FXML public void showProfile(ActionEvent e)       { setActive(btnNavProfile);       buildProfilePane(); }
    @FXML public void showRooms(ActionEvent e)         { setActive(btnNavRooms);         buildRoomsPane(); }
    @FXML public void showReservations(ActionEvent e)  { setActive(btnNavReservations);  buildReservationsPane(); }
    @FXML public void showCheckout(ActionEvent e)      { setActive(btnNavPayment);       buildCheckoutPane(null); }

    private void setActive(Button active) {
        for (Button b : new Button[]{btnNavDashboard, btnNavProfile, btnNavRooms,
                btnNavReservations, btnNavPayment}) {
            b.getStyleClass().remove("nav-btn-active");
        }
        if (active != null) active.getStyleClass().add("nav-btn-active");
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  DASHBOARD OVERVIEW
    // ═══════════════════════════════════════════════════════════════════════════
    private void buildDashboardPane() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(0));

        // Header
        Label title = new Label("Dashboard");
        title.getStyleClass().add("label-title");
        Label sub = new Label("Welcome back, " + currentGuest.getUsername() + "!");
        sub.getStyleClass().add("label-subtitle");
        VBox header = new VBox(4, title, sub);

        // Stats row
        long activeCount = HotelDatabase.getGuestReservations(currentGuest).stream()
                .filter(r -> r.getStatus() == ReservationStatus.CONFIRMED).count();
        long totalCount  = HotelDatabase.getGuestReservations(currentGuest).size();

        HBox stats = new HBox(15,
                statCard("💰 Balance",      String.format("$%.2f", currentGuest.getBalance()), "card-value-gold"),
                statCard("📅 Active Stays", String.valueOf(activeCount), "card-value"),
                statCard("🧾 Total Booked", String.valueOf(totalCount),  "card-value"),
                statCard("🛏 Available Rooms", String.valueOf(HotelDatabase.getAvailableRooms().size()), "card-value")
        );
        for (Node n : stats.getChildren()) HBox.setHgrow(n, Priority.ALWAYS);

        // Recent reservations table
        Label secLabel = new Label("Recent Reservations");
        secLabel.getStyleClass().add("label-section");

        TableView<Reservation> table = buildReservationTable(false);
        List<Reservation> guestRes = HotelDatabase.getGuestReservations(currentGuest);
        table.setItems(FXCollections.observableArrayList(guestRes));
        table.setPrefHeight(220);

        root.getChildren().addAll(header, stats, secLabel, table);
        setContent(root);
    }

    private VBox statCard(String label, String value, String valueStyle) {
        Label lbl = new Label(label);  lbl.getStyleClass().add("card-label");
        Label val = new Label(value);  val.getStyleClass().add(valueStyle);
        VBox card = new VBox(6, lbl, val);
        card.getStyleClass().add("card");
        card.setAlignment(Pos.TOP_LEFT);
        return card;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  PROFILE
    // ═══════════════════════════════════════════════════════════════════════════
    private void buildProfilePane() {
        VBox root = new VBox(20);

        Label title = new Label("My Profile");
        title.getStyleClass().add("label-title");

        VBox card = new VBox(16);
        card.getStyleClass().add("card");
        card.setMaxWidth(500);

        card.getChildren().addAll(
                profileRow("Username",     currentGuest.getUsername()),
                profileRow("Address",      currentGuest.getAddress() != null ? currentGuest.getAddress() : "—"),
                profileRow("Date of Birth",currentGuest.getDateOfBirth() != null ? currentGuest.getDateOfBirth().toString() : "—"),
                profileRow("Gender",       currentGuest.getGender() != null ? currentGuest.getGender().name() : "—"),
                profileRow("Preferences",  currentGuest.getRoomPreferences()),
                profileRow("Balance",      String.format("$%.2f", currentGuest.getBalance()))
        );

        // Top-up balance
        Label topupLabel = new Label("Add Balance");
        topupLabel.getStyleClass().add("label-section");

        TextField amtField = new TextField();
        amtField.setPromptText("Amount in USD");
        amtField.getStyleClass().add("field");
        amtField.setMaxWidth(200);

        Label feedback = new Label(); feedback.getStyleClass().add("label-success");

        Button addBtn = new Button("Add Funds");
        addBtn.getStyleClass().add("btn-gold");
        addBtn.setOnAction(e -> {
            try {
                double amt = Double.parseDouble(amtField.getText().trim());
                if (amt <= 0) throw new NumberFormatException();
                currentGuest.setBalance(currentGuest.getBalance() + amt);
                feedback.setText("✓ $" + String.format("%.2f", amt) + " added successfully!");
                amtField.clear();
            } catch (NumberFormatException ex) {
                feedback.setStyle("-fx-text-fill:#e74c3c; -fx-font-weight:bold;");
                feedback.setText("Please enter a valid positive amount.");
            }
        });

        HBox topupRow = new HBox(10, amtField, addBtn);
        topupRow.setAlignment(Pos.CENTER_LEFT);

        root.getChildren().addAll(title, card, topupLabel, topupRow, feedback);
        setContent(root);
    }

    private HBox profileRow(String label, String value) {
        Label lbl = new Label(label + ":");
        lbl.setMinWidth(130);
        lbl.getStyleClass().add("label-muted");
        Label val = new Label(value);
        val.setStyle("-fx-font-weight:bold; -fx-text-fill:#1a2744;");
        HBox row = new HBox(10, lbl, val);
        row.setAlignment(Pos.CENTER_LEFT);
        row.setPadding(new Insets(4, 0, 4, 0));
        row.setStyle("-fx-border-color: transparent transparent #f0f2f8 transparent; -fx-border-width:0 0 1 0;");
        return row;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  ROOM BROWSING
    // ═══════════════════════════════════════════════════════════════════════════
    private void buildRoomsPane() {
        VBox root = new VBox(16);

        Label title = new Label("Browse Rooms");
        title.getStyleClass().add("label-title");

        // Filter Bar
        HBox filterBar = new HBox(12);
        filterBar.getStyleClass().add("filter-bar");
        filterBar.setAlignment(Pos.CENTER_LEFT);

        ComboBox<String> typeFilter = new ComboBox<>();
        typeFilter.getItems().addAll("All Types", "Standard", "Deluxe", "Suite", "Penthouse");
        typeFilter.setValue("All Types");
        typeFilter.setStyle("-fx-pref-width:140px;");

        ComboBox<String> priceFilter = new ComboBox<>();
        priceFilter.getItems().addAll("Any Price", "Under $100", "$100-$200", "$200-$400", "Over $400");
        priceFilter.setValue("Any Price");
        priceFilter.setStyle("-fx-pref-width:140px;");

        ComboBox<String> amenityFilter = new ComboBox<>();
        amenityFilter.getItems().addAll("Any Amenity", "WiFi", "TV", "Minibar", "Breakfast", "AC");
        amenityFilter.setValue("Any Amenity");
        amenityFilter.setStyle("-fx-pref-width:140px;");

        Button filterBtn = new Button("Filter");
        filterBtn.getStyleClass().add("btn-primary");

        filterBar.getChildren().addAll(
                new Label("Type:"), typeFilter,
                new Label("Price:"), priceFilter,
                new Label("Amenity:"), amenityFilter,
                filterBtn);

        // Room Grid (FlowPane-style via TilePane)
        FlowPane grid = new FlowPane(14, 14);
        grid.setPrefWrapLength(800);

        ScrollPane scroll = new ScrollPane(grid);
        scroll.setFitToWidth(true);
        scroll.getStyleClass().add("scroll-pane");
        scroll.setPrefHeight(460);

        // Render rooms
        Runnable renderRooms = () -> {
            grid.getChildren().clear();
            String type    = typeFilter.getValue();
            String price   = priceFilter.getValue();
            String amenity = amenityFilter.getValue();

            for (Room room : HotelDatabase.getAvailableRooms()) {
                if (!type.equals("All Types") && !room.getRoomType().getType().equals(type)) continue;
                if (!price.equals("Any Price")) {
                    double p = room.getPricePerNight();
                    if (price.equals("Under $100")    && p >= 100) continue;
                    if (price.equals("$100-$200")     && (p < 100 || p > 200)) continue;
                    if (price.equals("$200-$400")     && (p < 200 || p > 400)) continue;
                    if (price.equals("Over $400")     && p <= 400) continue;
                }
                if (!amenity.equals("Any Amenity")) {
                    boolean hasAmenity = room.getAmenities().stream()
                            .anyMatch(a -> a.getType().name().equalsIgnoreCase(
                                    amenity.replace(" ", "_").replace("AC","AIR_CONDITIONING")));
                    if (!hasAmenity) continue;
                }
                grid.getChildren().add(buildRoomCard(room));
            }
            if (grid.getChildren().isEmpty()) {
                Label none = new Label("No rooms match your filters.");
                none.getStyleClass().add("label-muted");
                grid.getChildren().add(none);
            }
        };

        filterBtn.setOnAction(e -> renderRooms.run());
        renderRooms.run();

        root.getChildren().addAll(title, filterBar, scroll);
        setContent(root);
    }

    private VBox buildRoomCard(Room room) {
        Label num   = new Label("Room " + room.getRoomNumber());
        num.getStyleClass().add("room-number");

        Label type  = new Label(room.getRoomType().getType());
        type.getStyleClass().add("room-type");

        Label price = new Label(String.format("$%.0f / night", room.getPricePerNight()));
        price.getStyleClass().add("room-price");

        Label amenities = new Label("✦ " + room.getAmenitiesString().replace(", ", "  ✦ "));
        amenities.getStyleClass().add("room-amenities");
        amenities.setWrapText(true);
        amenities.setMaxWidth(200);

        Label badge = new Label("AVAILABLE");
        badge.getStyleClass().add("badge-available");

        Button bookBtn = new Button("Book Now");
        bookBtn.getStyleClass().add("btn-gold");
        bookBtn.setMaxWidth(Double.MAX_VALUE);
        bookBtn.setOnAction(e -> openBookingDialog(room));

        VBox card = new VBox(8, num, type, price, amenities, badge, bookBtn);
        card.getStyleClass().add("room-card");
        card.setPrefWidth(220);
        card.setPadding(new Insets(16));
        return card;
    }

    private void openBookingDialog(Room room) {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Book Room " + room.getRoomNumber());
        dlg.setHeaderText(room.getRoomType().getType() + " — $" +
                String.format("%.0f", room.getPricePerNight()) + "/night");

        DatePicker checkIn  = new DatePicker(LocalDate.now());
        DatePicker checkOut = new DatePicker(LocalDate.now().plusDays(1));
        Label cost = new Label();
        cost.getStyleClass().add("label-gold");

        Runnable calcCost = () -> {
            if (checkIn.getValue() != null && checkOut.getValue() != null) {
                long nights = ChronoUnit.DAYS.between(checkIn.getValue(), checkOut.getValue());
                if (nights > 0)
                    cost.setText(String.format("Total: $%.2f (%d nights)", nights * room.getPricePerNight(), nights));
                else cost.setText("Check-out must be after check-in.");
            }
        };
        checkIn.valueProperty().addListener((o,ov,nv) -> calcCost.run());
        checkOut.valueProperty().addListener((o,ov,nv) -> calcCost.run());
        calcCost.run();

        GridPane gp = new GridPane();
        gp.setHgap(12); gp.setVgap(12);
        gp.add(new Label("Check-in:"),  0, 0); gp.add(checkIn,  1, 0);
        gp.add(new Label("Check-out:"), 0, 1); gp.add(checkOut, 1, 1);
        gp.add(cost, 0, 2, 2, 1);

        dlg.getDialogPane().setContent(gp);
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        dlg.showAndWait().ifPresent(btn -> {
            if (btn == ButtonType.OK) {
                if (checkOut.getValue().isAfter(checkIn.getValue())) {
                    Reservation res = new Reservation(
                            HotelDatabase.nextReservationId(),
                            currentGuest, room,
                            checkIn.getValue(), checkOut.getValue());
                    room.occupy();
                    HotelDatabase.addReservation(res);
                    currentGuest.addReservation(res);
                    showAlert(Alert.AlertType.INFORMATION, "Booked!",
                            "Room " + room.getRoomNumber() + " reserved successfully!\n" + cost.getText());
                    buildRoomsPane(); // refresh
                } else {
                    showAlert(Alert.AlertType.ERROR, "Invalid Dates", "Check-out must be after check-in.");
                }
            }
        });
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  RESERVATIONS
    // ═══════════════════════════════════════════════════════════════════════════
    private void buildReservationsPane() {
        VBox root = new VBox(16);

        Label title = new Label("My Reservations");
        title.getStyleClass().add("label-title");

        TableView<Reservation> table = buildReservationTable(true);
        List<Reservation> guestRes = HotelDatabase.getGuestReservations(currentGuest);
        ObservableList<Reservation> data = FXCollections.observableArrayList(guestRes);
        table.setItems(data);
        VBox.setVgrow(table, Priority.ALWAYS);

        // Action buttons
        Button cancelBtn = new Button("Cancel Selected");
        cancelBtn.getStyleClass().add("btn-danger");
        cancelBtn.setOnAction(e -> {
            Reservation sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a reservation to cancel.");
                return;
            }
            if (sel.getStatus() != ReservationStatus.CONFIRMED) {
                showAlert(Alert.AlertType.WARNING, "Cannot Cancel", "Only CONFIRMED reservations can be cancelled.");
                return;
            }
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                    "Cancel reservation for Room " + sel.getRoom().getRoomNumber() + "?",
                    ButtonType.YES, ButtonType.NO);
            confirm.setTitle("Confirm Cancellation");
            confirm.showAndWait().ifPresent(bt -> {
                if (bt == ButtonType.YES) {
                    sel.cancel();
                    table.refresh();
                    showAlert(Alert.AlertType.INFORMATION, "Cancelled", "Reservation cancelled successfully.");
                }
            });
        });

        Button payBtn = new Button("Checkout Selected");
        payBtn.getStyleClass().add("btn-gold");
        payBtn.setOnAction(e -> {
            Reservation sel = table.getSelectionModel().getSelectedItem();
            if (sel == null) {
                showAlert(Alert.AlertType.WARNING, "No Selection", "Please select a reservation to checkout.");
                return;
            }
            if (sel.getStatus() != ReservationStatus.CONFIRMED) {
                showAlert(Alert.AlertType.WARNING, "Cannot Checkout", "Only CONFIRMED reservations can be checked out.");
                return;
            }
            setActive(btnNavPayment);
            buildCheckoutPane(sel);
        });

        HBox btnRow = new HBox(10, cancelBtn, payBtn);
        root.getChildren().addAll(title, table, btnRow);
        setContent(root);
    }

    private TableView<Reservation> buildReservationTable(boolean withCost) {
        TableView<Reservation> table = new TableView<>();
        table.getStyleClass().add("table-view");

        TableColumn<Reservation, String> colRoom = new TableColumn<>("Room");
        colRoom.setCellValueFactory(cd -> new SimpleStringProperty(
                "Room " + cd.getValue().getRoom().getRoomNumber() +
                " (" + cd.getValue().getRoom().getRoomType().getType() + ")"));
        colRoom.setPrefWidth(200);

        TableColumn<Reservation, String> colIn = new TableColumn<>("Check-In");
        colIn.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCheckInDate().toString()));
        colIn.setPrefWidth(120);

        TableColumn<Reservation, String> colOut = new TableColumn<>("Check-Out");
        colOut.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getCheckOutDate().toString()));
        colOut.setPrefWidth(120);

        TableColumn<Reservation, String> colStatus = new TableColumn<>("Status");
        colStatus.setCellValueFactory(cd -> new SimpleStringProperty(cd.getValue().getStatus().name()));
        colStatus.setPrefWidth(110);

        table.getColumns().addAll(colRoom, colIn, colOut, colStatus);

        if (withCost) {
            TableColumn<Reservation, String> colCost = new TableColumn<>("Total Cost");
            colCost.setCellValueFactory(cd -> new SimpleStringProperty(
                    String.format("$%.2f", cd.getValue().getTotalCost())));
            colCost.setPrefWidth(110);
            table.getColumns().add(colCost);
        }

        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return table;
    }

    // ═══════════════════════════════════════════════════════════════════════════
    //  CHECKOUT / PAYMENT
    // ═══════════════════════════════════════════════════════════════════════════
    private void buildCheckoutPane(Reservation preSelected) {
        VBox root = new VBox(20);

        Label title = new Label("Checkout & Payment");
        title.getStyleClass().add("label-title");

        // Pick reservation
        Label pickLabel = new Label("Select Reservation");
        pickLabel.getStyleClass().add("label-section");

        List<Reservation> confirmed = HotelDatabase.getGuestReservations(currentGuest)
                .stream().filter(r -> r.getStatus() == ReservationStatus.CONFIRMED)
                .collect(java.util.stream.Collectors.toList());

        ComboBox<Reservation> resCmb = new ComboBox<>();
        resCmb.getItems().addAll(confirmed);
        resCmb.setCellFactory(lv -> new ListCell<>() {
            protected void updateItem(Reservation r, boolean empty) {
                super.updateItem(r, empty);
                setText(empty || r == null ? "" :
                        "Room " + r.getRoom().getRoomNumber() + " | " +
                        r.getCheckInDate() + " → " + r.getCheckOutDate());
            }
        });
        resCmb.setButtonCell(resCmb.getCellFactory().call(null));
        if (preSelected != null && confirmed.contains(preSelected)) resCmb.setValue(preSelected);
        else if (!confirmed.isEmpty()) resCmb.setValue(confirmed.get(0));

        if (confirmed.isEmpty()) {
            root.getChildren().addAll(title,
                    new Label("No confirmed reservations to check out."));
            setContent(root);
            return;
        }

        // Invoice card
        VBox invoiceCard = new VBox(10);
        invoiceCard.getStyleClass().add("card");
        invoiceCard.setMaxWidth(500);

        Label invoiceTitle = new Label("Invoice Details");
        invoiceTitle.getStyleClass().add("card-title");
        invoiceCard.getChildren().add(invoiceTitle);

        Label[] invRoom   = {new Label()};
        Label[] invNights = {new Label()};
        Label[] invRate   = {new Label()};
        Label[] invTotal  = {new Label()};
        invTotal[0].getStyleClass().add("invoice-total");

        Runnable updateInvoice = () -> {
            Reservation r = resCmb.getValue();
            if (r == null) return;
            long nights = Math.max(1, ChronoUnit.DAYS.between(r.getCheckInDate(), r.getCheckOutDate()));
            invRoom[0].setText("Room " + r.getRoom().getRoomNumber() + " (" + r.getRoom().getRoomType().getType() + ")");
            invNights[0].setText(nights + " night(s)");
            invRate[0].setText(String.format("$%.2f / night", r.getRoom().getPricePerNight()));
            invTotal[0].setText(String.format("Total: $%.2f", r.getTotalCost()));
        };
        resCmb.valueProperty().addListener((o, ov, nv) -> updateInvoice.run());
        updateInvoice.run();

        GridPane invoiceGrid = new GridPane();
        invoiceGrid.setHgap(20); invoiceGrid.setVgap(8);
        invoiceGrid.add(new Label("Room:"),    0, 0); invoiceGrid.add(invRoom[0],   1, 0);
        invoiceGrid.add(new Label("Duration:"),0, 1); invoiceGrid.add(invNights[0], 1, 1);
        invoiceGrid.add(new Label("Rate:"),    0, 2); invoiceGrid.add(invRate[0],   1, 2);
        invoiceGrid.add(new Separator(), 0, 3, 2, 1);
        invoiceGrid.add(invTotal[0], 0, 4, 2, 1);
        for (Node n : invoiceGrid.getChildren())
            if (n instanceof Label && invoiceGrid.getColumnIndex(n) == 0)
                ((Label)n).getStyleClass().add("label-muted");
        invoiceCard.getChildren().add(invoiceGrid);

        // Payment method
        Label payLabel = new Label("Payment Method");
        payLabel.getStyleClass().add("label-section");

        ToggleGroup payGroup = new ToggleGroup();
        RadioButton rbCash = new RadioButton("💵  Cash");           rbCash.setToggleGroup(payGroup);
        RadioButton rbCard = new RadioButton("💳  Credit Card");    rbCard.setToggleGroup(payGroup);
        RadioButton rbOnline = new RadioButton("🌐  Online Transfer");rbOnline.setToggleGroup(payGroup);
        rbCard.setSelected(true);

        for (RadioButton rb : new RadioButton[]{rbCash, rbCard, rbOnline}) {
            rb.setStyle("-fx-font-size:14px; -fx-padding:10 16 10 16; " +
                    "-fx-background-color:#f0f2f8; -fx-background-radius:8; -fx-cursor:hand;");
        }
        HBox payOptions = new HBox(12, rbCash, rbCard, rbOnline);
        payOptions.setAlignment(Pos.CENTER_LEFT);

        // Balance warning
        Label balanceInfo = new Label();
        balanceInfo.getStyleClass().add("label-muted");
        balanceInfo.setText("Your balance: $" + String.format("%.2f", currentGuest.getBalance()));

        // Confirm button
        Button confirmBtn = new Button("Confirm Payment");
        confirmBtn.getStyleClass().add("btn-success");
        confirmBtn.setPrefWidth(220);
        confirmBtn.setPrefHeight(44);

        Label feedback = new Label(); feedback.setWrapText(true);

        confirmBtn.setOnAction(e -> {
            Reservation r = resCmb.getValue();
            if (r == null) { feedback.setText("Select a reservation."); return; }
            double cost = r.getTotalCost();

            hotel.model.PaymentMethod method =
                    rbCash.isSelected()   ? hotel.model.PaymentMethod.CASH :
                    rbCard.isSelected()   ? hotel.model.PaymentMethod.CREDIT_CARD :
                                            hotel.model.PaymentMethod.ONLINE;

            if (method == hotel.model.PaymentMethod.ONLINE || method == hotel.model.PaymentMethod.CASH) {
                if (currentGuest.getBalance() < cost) {
                    feedback.setStyle("-fx-text-fill:#e74c3c; -fx-font-weight:bold;");
                    feedback.setText("Insufficient balance. Please add funds in My Profile.");
                    return;
                }
                currentGuest.setBalance(currentGuest.getBalance() - cost);
            }

            hotel.reservations.Invoice inv = new hotel.reservations.Invoice(
                    HotelDatabase.nextInvoiceId(), r, cost, method);
            inv.processPayment();
            HotelDatabase.addInvoice(inv);
            r.complete();

            feedback.setStyle("-fx-text-fill:#27ae60; -fx-font-weight:bold; -fx-font-size:14px;");
            feedback.setText("✓ Payment of $" + String.format("%.2f", cost) + " confirmed via " +
                    method.name() + "!\nReservation marked as COMPLETED.");
            confirmBtn.setDisable(true);
            balanceInfo.setText("Your balance: $" + String.format("%.2f", currentGuest.getBalance()));
        });

        root.getChildren().addAll(title, pickLabel, resCmb, invoiceCard,
                payLabel, payOptions, balanceInfo, confirmBtn, feedback);
        setContent(root);
    }

    // ─── Logout ───────────────────────────────────────────────────────────────
    @FXML public void handleLogout(ActionEvent event) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Are you sure you want to logout?", ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Logout");
        confirm.showAndWait().ifPresent(bt -> {
            if (bt == ButtonType.YES) {
                try {
                    Parent root = FXMLLoader.load(
                            getClass().getResource("/fxml/Login.fxml"));
                    Stage stage = (Stage) contentArea.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Hotel Reservation System — Login");
                    stage.show();
                } catch (IOException ex) { ex.printStackTrace(); }
            }
        });
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────
    private void setContent(VBox content) {
        content.setPadding(new Insets(0));
        ScrollPane sp = new ScrollPane(content);
        sp.setFitToWidth(true);
        sp.getStyleClass().add("scroll-pane");
        sp.setStyle("-fx-background-color:transparent; -fx-background:transparent;");
        contentArea.getChildren().setAll(sp);
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert a = new Alert(type);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
