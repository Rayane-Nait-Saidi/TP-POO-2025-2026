package HelloFX;

import HelloFX.Entities.Animal;
import HelloFX.Entities.Cult;
import HelloFX.Entities.Event_Sanitaire;
import HelloFX.Entities.Prod;
import HelloFX.Entities.Type;
import HelloFX.Entities.Type_Event_Sant;
import HelloFX.Entities.Type_Prod;
import HelloFX.Ferme.Ferme;
import HelloFX.Zones.Aqua;
import HelloFX.Zones.Croissance;
import HelloFX.Zones.Culture;
import HelloFX.Zones.Elevage;
import HelloFX.Zones.Famille;
import HelloFX.Zones.STATUS;
import HelloFX.Zones.Zone;
import HelloFX.alertes.Alerte;
import HelloFX.alertes.Gravite;
import HelloFX.capteurs.Bio;
import HelloFX.capteurs.Capteur;
import HelloFX.capteurs.Env;
import HelloFX.capteurs.Eau;
import HelloFX.capteurs.GPS;
import HelloFX.capteurs.Num;
import HelloFX.capteurs.Sol;
import HelloFX.capteurs.Stat_Capt;
import HelloFX.releves.Niveau_Releve;
import HelloFX.releves.Releve;
import HelloFX.releves.Releve_GPS;
import HelloFX.releves.Releve_Generale;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class FarmDashboard extends Application {

    private static final double SIDEBAR_W  = 240;
    private static final double SNAPSHOT_W = 220;

    private final Ferme ferme = new Ferme();

    private TextArea logArea;
    private Label zonesCountValue;
    private Label sensorsCountValue;
    private Label alertsCountValue;
    private Label activeZonesValue;
    private ListView<String> snapshotList;

    private final Label topZonesValue   = new Label("0");
    private final Label topSensorsValue = new Label("0");
    private final Label topAlertsValue  = new Label("0");

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage stage) {
        initialiserDemo();

        HBox root = new HBox();
        root.setStyle("-fx-background-color: #101e15; -fx-font-family: 'Segoe UI';");

        Node sidebar  = buildSidebar();
        Node center   = buildCenterArea();
        HBox.setHgrow((Region) ((ScrollPane) center).getContent(), Priority.ALWAYS);
        HBox.setHgrow(center, Priority.ALWAYS);
        Node snapshot = buildSnapshotPanel();

        root.getChildren().addAll(sidebar, center, snapshot);

        VBox wrapper = new VBox();
        wrapper.setStyle("-fx-background-color: #101e15;");
        wrapper.getChildren().addAll(buildHeader(), root);
        VBox.setVgrow(root, Priority.ALWAYS);

        double screenH = Screen.getPrimary().getVisualBounds().getHeight();
        double screenW = Screen.getPrimary().getVisualBounds().getWidth();

        Scene scene = new Scene(wrapper, Math.min(screenW, 1540), Math.min(screenH, 920));
        stage.setTitle("Farm Control Center");
        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.setMinHeight(600);
        stage.show();

        refreshOverview();
        appendLog("Dashboard ready. Use the left menu to manage the farm.");
    }

    // ── HEADER ────────────────────────────────────────────────────────────────
    private Node buildHeader() {
        HBox header = new HBox(20);
        header.setPadding(new Insets(16, 24, 14, 24));
        header.setAlignment(Pos.CENTER_LEFT);
        header.setStyle(
            "-fx-background-color: #07120c;" +
            "-fx-border-color: rgba(255,255,255,0.07);" +
            "-fx-border-width: 0 0 1 0;"
        );

        VBox titleBox = new VBox(2);
        Label title    = new Label("Farm Control Center");
        title.setStyle("-fx-text-fill: #f7f2e8; -fx-font-size: 22px; -fx-font-weight: 700;");
        Label subtitle = new Label("JavaFX dashboard — zones · animals · sensors · alerts · production");
        subtitle.setStyle("-fx-text-fill: #9db8a2; -fx-font-size: 11px;");
        titleBox.getChildren().addAll(title, subtitle);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        applyChipValueStyle(topZonesValue);
        applyChipValueStyle(topSensorsValue);
        applyChipValueStyle(topAlertsValue);

        HBox chips = new HBox(8,
            chip("Zones",   topZonesValue),
            chip("Sensors", topSensorsValue),
            chip("Alerts",  topAlertsValue)
        );
        chips.setAlignment(Pos.CENTER_RIGHT);

        header.getChildren().addAll(titleBox, spacer, chips);
        return header;
    }

    private HBox chip(String name, Label valueLabel) {
        Label nameLabel = new Label(name + ":");
        nameLabel.setStyle("-fx-text-fill: #8fb8a0; -fx-font-size: 11px; -fx-font-weight: 600;");
        HBox chip = new HBox(5, nameLabel, valueLabel);
        chip.setAlignment(Pos.CENTER);
        chip.setPadding(new Insets(6, 14, 6, 14));
        chip.setStyle(
            "-fx-background-color: rgba(255,255,255,0.07);" +
            "-fx-background-radius: 20;" +
            "-fx-border-color: rgba(255,255,255,0.11);" +
            "-fx-border-radius: 20;"
        );
        return chip;
    }

    private void applyChipValueStyle(Label l) {
        l.setStyle("-fx-text-fill: #f0ede4; -fx-font-size: 12px; -fx-font-weight: 700;");
    }

    // ── SIDEBAR ───────────────────────────────────────────────────────────────
    private Node buildSidebar() {
        VBox inner = new VBox(7);
        inner.setPadding(new Insets(18, 12, 18, 12));
        inner.setFillWidth(true);
        inner.setStyle("-fx-background-color: #0a1910;");

        Label heading = new Label("Main Menu");
        heading.setStyle("-fx-text-fill: #e8e0d0; -fx-font-size: 14px; -fx-font-weight: 700;");
        Label hint = new Label("Each action follows the original Main2 menu.");
        hint.setStyle("-fx-text-fill: #7a9e87; -fx-font-size: 10px;");
        hint.setWrapText(true);

        inner.getChildren().addAll(heading, hint, sep());

        String[][] btns = {
            {"1.  Ajouter une zone",                     "#c97506"},
            {"2.  Modifier une zone",                    "#5e8a5a"},
            {"3.  Désactiver / réactiver une zone",      "#3d8e88"},
            {"4.  Afficher toutes les zones",            "#7a5f38"},
            {null, null},
            {"5.  Affecter une culture à une zone",      "#8f5a07"},
            {"6.  Màj le stade d'une culture",           "#a04f08"},
            {"7.  Rapport des cultures par zone",        "#c97506"},
            {"8.  Affecter un animal à une zone",        "#3d8e5a"},
            {"9.  Evénement sanitaire",                  "#b03a0b"},
            {"10. Définir programme alimentaire",        "#5e8748"},
            {"11. Afficher programmes alimentaires",     "#2d7a70"},
            {null, null},
            {"12. Ajouter / configurer un capteur",      "#2e50d0"},
            {"13. Changer le statut d'un capteur",       "#484e80"},
            {"14. Enregistrer un relevé + alerte",       "#0e6560"},
            {"16. Historique des relevés",               "#6a7280"},
            {"17. Graphique des relevés",                "#554468"},
            {null, null},
            {"18. Alertes actives triées",               "#cc3333"},
            {"19. Acquitter une alerte",                 "#d96010"},
            {"20. Supprimer une alerte",                 "#991616"},
            {null, null},
            {"21. Enregistrer une production",           "#475260"},
            {"22. Historique des productions",           "#186070"},
        };

        Runnable[] actions = {
            this::handleAddZone, this::handleRenameZone, this::handleToggleZone,
            this::handleShowZones, null,
            this::handleAddCulture, this::handleUpdateCultureStage, this::handleCultureReport,
            this::handleAddAnimal, this::handleSanitaryEvent, this::handleDefineProgram,
            this::handleShowPrograms, null,
            this::handleAddSensor, this::handleChangeSensorStatus, this::handleRecordReading,
            this::handleHistory, this::handleGraph, null,
            this::handleShowAlerts, this::handleAcknowledgeAlert, this::handleDeleteAlert,
            null,
            this::handleProduction, this::handleProductionHistory
        };

        for (int i = 0; i < btns.length; i++) {
            if (btns[i][0] == null) inner.getChildren().add(sep());
            else inner.getChildren().add(sideBtn(btns[i][0], btns[i][1], actions[i]));
        }

        ScrollPane sp = new ScrollPane(inner);
        sp.setPrefWidth(SIDEBAR_W);
        sp.setMinWidth(SIDEBAR_W);
        sp.setMaxWidth(SIDEBAR_W);
        sp.setFitToWidth(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        sp.setPannable(true);
        sp.setStyle(
            "-fx-background: #0a1910;" +
            "-fx-background-color: #0a1910;" +
            "-fx-border-color: rgba(255,255,255,0.06);" +
            "-fx-border-width: 0 1 0 0;"
        );
        return sp;
    }

    private Button sideBtn(String text, String color, Runnable action) {
        Button b = new Button(text);
        b.setMaxWidth(Double.MAX_VALUE);
        b.setMinHeight(36);
        b.setWrapText(true);
        b.setAlignment(Pos.CENTER_LEFT);
        b.setPadding(new Insets(8, 10, 8, 10));
        b.setOnAction(e -> action.run());
        String normal = sideBtnStyle(color, false);
        String hover  = sideBtnStyle(color, true);
        b.setStyle(normal);
        b.setOnMouseEntered(e -> b.setStyle(hover));
        b.setOnMouseExited(e  -> b.setStyle(normal));
        return b;
    }

    private String sideBtnStyle(String color, boolean hovered) {
        String bg = hovered ? "derive(" + color + ", 20%)" : color;
        return "-fx-background-color: " + bg + ";" +
               "-fx-text-fill: #ffffff;" +
               "-fx-font-size: 11px;" +
               "-fx-font-weight: 700;" +
               "-fx-background-radius: 8;" +
               "-fx-border-radius: 8;" +
               "-fx-cursor: hand;" +
               "-fx-alignment: center-left;";
    }

    private Separator sep() {
        Separator s = new Separator();
        s.setStyle("-fx-background-color: rgba(255,255,255,0.1);");
        return s;
    }

    // ── CENTER AREA ───────────────────────────────────────────────────────────
    private Node buildCenterArea() {
        VBox center = new VBox(14);
        center.setPadding(new Insets(18));
        center.setFillWidth(true);
        center.setStyle("-fx-background-color: linear-gradient(to bottom, #142a1c, #1e3828);");

        HBox stats = new HBox(12);
        stats.setFillHeight(true);

        VBox c1 = statCard("Zones",   "#6a9b6a", "Total zones in the farm");
        VBox c2 = statCard("Sensors", "#4a9060", "Active and inactive sensors");
        VBox c3 = statCard("Alerts",  "#c05535", "Current alert queue");
        VBox c4 = statCard("Active",  "#3d7a6a", "Zones currently active");
        for (VBox c : new VBox[]{c1, c2, c3, c4}) HBox.setHgrow(c, Priority.ALWAYS);
        stats.getChildren().addAll(c1, c2, c3, c4);

        VBox console = new VBox(10);
        console.setPadding(new Insets(16));
        console.setStyle(
            "-fx-background-color: rgba(8,18,12,0.92);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255,255,255,0.08);" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.25),20,0.2,0,6);"
        );
        VBox.setVgrow(console, Priority.ALWAYS);

        Label consoleTitle = new Label("Activity Console");
        consoleTitle.setStyle("-fx-text-fill: #ddd8cc; -fx-font-size: 14px; -fx-font-weight: 700;");

        logArea = new TextArea();
        logArea.setEditable(false);
        logArea.setWrapText(true);
        logArea.setStyle(
            "-fx-control-inner-background: #f5f1e8;" +
            "-fx-text-fill: #1a2b1e;" +
            "-fx-font-family: 'Consolas';" +
            "-fx-font-size: 12px;"
        );
        VBox.setVgrow(logArea, Priority.ALWAYS);

        console.getChildren().addAll(consoleTitle, logArea);
        center.getChildren().addAll(stats, console);

        ScrollPane sp = new ScrollPane(center);
        sp.setFitToWidth(true);
        sp.setFitToHeight(true);
        sp.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        sp.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        HBox.setHgrow(sp, Priority.ALWAYS);
        return sp;
    }

    private VBox statCard(String title, String accent, String hint) {
        VBox box = new VBox(4);
        box.setPadding(new Insets(14));
        box.setMinWidth(120);
        box.setStyle(
            "-fx-background-color: rgba(255,255,255,0.88);" +
            "-fx-background-radius: 12;" +
            "-fx-border-color: rgba(255,255,255,0.2);" +
            "-fx-border-radius: 12;" +
            "-fx-effect: dropshadow(gaussian,rgba(0,0,0,0.12),12,0.1,0,4);"
        );
        Label nameL = new Label(title);
        nameL.setStyle("-fx-text-fill: #2e4a35; -fx-font-size: 11px; -fx-font-weight: 700;");
        Label val = new Label("0");
        val.setStyle("-fx-text-fill: " + accent + "; -fx-font-size: 26px; -fx-font-weight: 800;");
        Label hintL = new Label(hint);
        hintL.setWrapText(true);
        hintL.setStyle("-fx-text-fill: #5a7060; -fx-font-size: 10px;");
        box.getChildren().addAll(nameL, val, hintL);
        switch (title) {
            case "Zones"   -> zonesCountValue   = val;
            case "Sensors" -> sensorsCountValue = val;
            case "Alerts"  -> alertsCountValue  = val;
            default        -> activeZonesValue  = val;
        }
        return box;
    }

    // ── SNAPSHOT PANEL ────────────────────────────────────────────────────────
    private Node buildSnapshotPanel() {
        VBox panel = new VBox(12);
        panel.setPrefWidth(SNAPSHOT_W);
        panel.setMinWidth(SNAPSHOT_W);
        panel.setMaxWidth(SNAPSHOT_W);
        panel.setPadding(new Insets(18, 14, 18, 14));
        panel.setStyle(
            "-fx-background-color: #f0ece0;" +
            "-fx-border-color: #d0cabb;" +
            "-fx-border-width: 0 0 0 1;"
        );
        Label title = new Label("Live Snapshot");
        title.setStyle("-fx-text-fill: #1a2e20; -fx-font-size: 15px; -fx-font-weight: 700;");

        snapshotList = new ListView<>();
        snapshotList.setMaxWidth(Double.MAX_VALUE);
        snapshotList.setStyle(
            "-fx-control-inner-background: #faf8f2;" +
            "-fx-border-color: #c8c2b2;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-font-size: 11px;"
        );
        VBox.setVgrow(snapshotList, Priority.ALWAYS);

        VBox note = new VBox(5);
        note.setPadding(new Insets(10));
        note.setStyle(
            "-fx-background-color: #ffffff;" +
            "-fx-background-radius: 8;" +
            "-fx-border-color: #ddd8cc;" +
            "-fx-border-radius: 8;"
        );
        Label noteTitle = new Label("Notes");
        noteTitle.setStyle("-fx-text-fill: #1a2e20; -fx-font-size: 12px; -fx-font-weight: 700;");
        Label noteBody  = new Label("Demo data is pre-loaded so the UI is immediately useful.");
        noteBody.setWrapText(true);
        noteBody.setStyle("-fx-text-fill: #4a5e50; -fx-font-size: 10px;");
        note.getChildren().addAll(noteTitle, noteBody);
        panel.getChildren().addAll(title, snapshotList, note);
        return panel;
    }

    // ── REFRESH ───────────────────────────────────────────────────────────────
    private void refreshOverview() {
        int zones = ferme.getAll_zones().size();
        int sensors = 0, active = 0;
        for (Zone z : ferme.getAll_zones()) {
            sensors += z.getAll_capteurs().size();
            if (z.getStatus() == STATUS.ACTIF) active++;
        }
        int alerts = ferme.getAlertes().size();
        set(zonesCountValue,   zones);
        set(sensorsCountValue, sensors);
        set(alertsCountValue,  alerts);
        set(activeZonesValue,  active);
        topZonesValue.setText(String.valueOf(zones));
        topSensorsValue.setText(String.valueOf(sensors));
        topAlertsValue.setText(String.valueOf(alerts));
        if (snapshotList != null)
            snapshotList.setItems(FXCollections.observableArrayList(snapshotLines()));
    }

    private void set(Label l, int v) { if (l != null) l.setText(String.valueOf(v)); }

    private List<String> snapshotLines() {
        List<String> lines = new ArrayList<>();
        int zones = ferme.getAll_zones().size();
        lines.add("Zones: " + zones);
        lines.add("Active: " + ferme.getAll_zones().stream().filter(z -> z.getStatus() == STATUS.ACTIF).count());
        lines.add("Sensors: " + ferme.getAll_zones().stream().mapToInt(z -> z.getAll_capteurs().size()).sum());
        lines.add("Alerts: " + ferme.getAlertes().size());
        lines.add("");
        lines.add("Current zones:");
        if (zones == 0) lines.add("  (none)");
        for (Zone z : ferme.getAll_zones()) lines.add("  " + zoneLabel(z));
        return lines;
    }

    // ── LABEL HELPERS ─────────────────────────────────────────────────────────
    private String zoneLabel(Zone z) {
        return z.getClass().getSimpleName() + " #" + z.getCode() + " – " + z.getNom() + " [" + z.getStatus() + "]";
    }
    private String capteurLabel(Capteur c) {
        return c.getClass().getSimpleName() + " #" + c.getCode() + " – " + c.getZone().getNom() + " [" + c.getStatut() + "]";
    }
    private String animalLabel(Animal a) {
        return "Animal #" + a.getId() + " – " + a.getEspece() + " – " + a.getAge() + " mois – " + a.getPoid() + " kg";
    }
    private String alertLabel(Alerte al) {
        Releve_Generale r = al.getReleve_Generale();
        return al.getGravite() + " – " + r.getCapteur().getZone().getNom() +
               " – " + r.getCapteur().getClass().getSimpleName() + " – " + r.getDate();
    }

    // ── DIALOG HELPERS ────────────────────────────────────────────────────────
    private <T> T chooseItem(String title, String header,
                             List<? extends T> items,
                             Function<? super T, String> fmt) {
        if (items.isEmpty()) { showInfo("Empty", "Nothing available."); return null; }
        Dialog<T> dlg = new Dialog<>();
        dlg.setTitle(title); dlg.setHeaderText(header);
        ButtonType ok = new ButtonType("Select", ButtonBar.ButtonData.OK_DONE);
        dlg.getDialogPane().getButtonTypes().addAll(ok, ButtonType.CANCEL);
        ComboBox<T> cb = new ComboBox<>(FXCollections.observableArrayList(new ArrayList<>(items)));
        cb.setMaxWidth(Double.MAX_VALUE);
        cb.setConverter(new StringConverter<>() {
            public String toString(T o) { return o == null ? "" : fmt.apply(o); }
            public T fromString(String s) { return null; }
        });
        cb.getSelectionModel().selectFirst();
        VBox box = new VBox(8, new Label("Choose:"), cb);
        box.setPadding(new Insets(14));
        dlg.getDialogPane().setContent(box);
        dlg.setResultConverter(b -> b == ok ? cb.getValue() : null);
        return dlg.showAndWait().orElse(null);
    }

    private String promptText(String title, String header) {
        TextInputDialog d = new TextInputDialog();
        d.setTitle(title); d.setHeaderText(header);
        return d.showAndWait().map(String::trim).filter(s -> !s.isEmpty()).orElse(null);
    }

    private Integer promptInt(String title, String header) {
        while (true) {
            TextInputDialog d = new TextInputDialog();
            d.setTitle(title); d.setHeaderText(header);
            Optional<String> r = d.showAndWait();
            if (r.isEmpty()) return null;
            try { return Integer.parseInt(r.get().trim()); }
            catch (Exception e) { showError("Invalid", "Enter a valid integer."); }
        }
    }

    private Float promptFloat(String title, String header) {
        while (true) {
            TextInputDialog d = new TextInputDialog();
            d.setTitle(title); d.setHeaderText(header);
            Optional<String> r = d.showAndWait();
            if (r.isEmpty()) return null;
            try { return Float.parseFloat(r.get().trim()); }
            catch (Exception e) { showError("Invalid", "Enter a valid decimal."); }
        }
    }

    private Float promptFloatGreaterThan(String title, String header, float minExclusive, String errorMessage) {
        while (true) {
            Float value = promptFloat(title, header);
            if (value == null) return null;
            if (value > minExclusive) return value;
            showError("Seuils", errorMessage);
        }
    }

    private Float promptFloatLessThan(String title, String header, float maxExclusive, String errorMessage) {
        while (true) {
            Float value = promptFloat(title, header);
            if (value == null) return null;
            if (value < maxExclusive) return value;
            showError("Seuils", errorMessage);
        }
    }

    private LocalDate promptDate(String title, String header) {
        while (true) {
            TextInputDialog d = new TextInputDialog();
            d.setTitle(title); d.setHeaderText(header + "  [yyyy-mm-dd]");
            Optional<String> r = d.showAndWait();
            if (r.isEmpty()) return null;
            try { return LocalDate.parse(r.get().trim()); }
            catch (Exception e) { showError("Invalid", "Use yyyy-mm-dd."); }
        }
    }

    private <E extends Enum<E>> E chooseEnum(String t, String h, E[] vals) {
        return chooseItem(t, h, Arrays.asList(vals), v -> v.name().replace('_', ' '));
    }

    private void showInfo(String t, String c)  { Alert a = new Alert(Alert.AlertType.INFORMATION); a.setTitle(t); a.setHeaderText(null); a.setContentText(c); a.showAndWait(); }
    private void showError(String t, String c) { Alert a = new Alert(Alert.AlertType.ERROR);       a.setTitle(t); a.setHeaderText(null); a.setContentText(c); a.showAndWait(); }

    private void appendLog(String msg) {
        if (logArea == null) return;
        logArea.appendText(msg + "\n\n");
        logArea.positionCaret(logArea.getText().length());
    }

    // ── DEMO INIT ─────────────────────────────────────────────────────────────
    private void initialiserDemo() {
        Culture culture = new Culture("Zone Culture");
        Elevage elevage = new Elevage("Zone Elevage");
        Aqua    aqua    = new Aqua("Zone Aquaculture", "poisson", 120);
        ferme.ajouter_zone(culture); ferme.ajouter_zone(elevage); ferme.ajouter_zone(aqua);

        Sol sol     = new Sol(0, Stat_Capt.ACTIF, culture, 10, 40, 5,  50, 60,  "%");
        Env env     = new Env(0, Stat_Capt.ACTIF, culture, 15, 30, 10, 40, 25,  "C");
        Eau eau     = new Eau(0, Stat_Capt.ACTIF, culture, 20, 60, 10, 80, 75,  "L");
        GPS gpsCult = new GPS(0, Stat_Capt.ACTIF, culture, 0, 0, -5, 5, -10, 10, -5, 5, -10, 10);
        culture.ajouter_capteur(sol); culture.ajouter_capteur(env);
        culture.ajouter_capteur(eau); culture.ajouter_capteur(gpsCult);

        culture.ajouter_culture(new Cult(LocalDate.of(2026,3,15),LocalDate.of(2026,8,30),Famille.CEREAL,  6.2f,7.4f,30f,60f));
        culture.ajouter_culture(new Cult(LocalDate.of(2026,4,1), LocalDate.of(2026,7,15),Famille.LEGUMES, 6.0f,7.0f,40f,70f));

        Animal cow = new Animal(12,Type.RUMI,150); Animal bird = new Animal(8,Type.VOL,3);
        elevage.ajouter_animal(cow); elevage.ajouter_animal(bird);
        GPS gpsElev = new GPS(0,Stat_Capt.ACTIF,elevage,12,10,-5,20,-10,25,-5,20,-10,25);
        elevage.ajouter_capteur(gpsElev); cow.setCollierGPS(gpsElev);

        aqua.definir_programme("Granulés riches en protéines",8);
        cow.definir_programme("Mélange riche en fibres",5);

        for (Releve_Generale r : new Releve_Generale[]{
                sol.generer_releve(), env.generer_releve(),
                eau.generer_releve(), gpsCult.generer_releve(), gpsElev.generer_releve()})
            registerAlert(r);

        elevage.ajouter_prod(new Prod(LocalDate.now(),LocalTime.now(),Type_Prod.LAIT,   120,"L"));
        culture.ajouter_prod(new Prod(LocalDate.now(),LocalTime.now(),Type_Prod.RECOLTE, 80,"kg"));
    }

    private void registerAlert(Releve_Generale r) {
        if      (r.getNiveau_releve()==Niveau_Releve.CRIT)  ferme.engistrer_alerte(new Alerte(r,Gravite.CRIT));
        else if (r.getNiveau_releve()==Niveau_Releve.AVERT) ferme.engistrer_alerte(new Alerte(r,Gravite.AVERT));
    }

    private boolean requireActiveZone(Zone z) {
        if (z != null && z.getStatus() == STATUS.SUSP) {
            showError("Zone suspended", "This zone is suspended and cannot do any operation.");
            return false;
        }
        return true;
    }

    // ── ACTION HANDLERS ───────────────────────────────────────────────────────
    private void handleAddZone() {
        Integer t = promptInt("Ajouter une zone","1=Culture  2=Elevage  3=Aquaculture");
        if(t==null)return;
        if (t < 1 || t > 3) { showError("Invalid","Choose 1, 2 or 3."); return; }
        String nm = promptText("Nom","Zone name"); if(nm==null)return;
        if      (t==1){ferme.ajouter_zone(new Culture(nm)); appendLog("Culture zone added: "+nm);}
        else if (t==2){ferme.ajouter_zone(new Elevage(nm)); appendLog("Elevage zone added: "+nm);}
        else {
            String sp=promptText("Espèce","Species"); if(sp==null)return;
            Integer nb=promptInt("Nombre","Count");   if(nb==null)return;
            ferme.ajouter_zone(new Aqua(nm,sp,nb));   appendLog("Aqua zone added: "+nm);
        }
        refreshOverview();
    }

    private void handleRenameZone() {
        Zone z=chooseItem("Renommer","Zone to rename",zoneList(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        String n=promptText("Nouveau nom","New name"); if(n==null)return;
        z.setNom(n); refreshOverview(); appendLog("Zone renamed: "+zoneLabel(z));
    }

    private void handleToggleZone() {
        Zone z=chooseItem("Toggle zone","Zone to toggle",zoneList(),this::zoneLabel); if(z==null)return;
        Integer c=promptInt("État","1=Activer  2=Suspendre"); if(c==null)return;
        if      (c==1){ferme.activer(z);    appendLog("Activated: "+z.getNom());}
        else if (c==2){ferme.suspendue(z);  appendLog("Suspended: "+z.getNom());}
        else          {showError("Invalid","Choose 1 or 2.");return;}
        refreshOverview();
    }

    private void handleShowZones() { appendLog(ferme.afficher_all_zones()); }

    private void handleAddCulture() {
        Culture z=chooseItem("Culture","Culture zone",cultureZones(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        LocalDate p=promptDate("Plantation","Planting date"); if(p==null)return;
        LocalDate r=promptDate("Récolte",   "Harvest date");  if(r==null)return;
        if(!r.isAfter(p)){showError("Dates","Harvest must be after planting.");return;}
        Famille f=chooseEnum("Famille","Family",Famille.values()); if(f==null)return;
        Float mn=promptFloat("pH min",""),mx=promptFloat("pH max",""),
              mnh=promptFloat("Hum min",""),mxh=promptFloat("Hum max","");
        if(mn==null||mx==null||mnh==null||mxh==null)return;
        if(!(mn<mx&&mnh<mxh)){showError("Seuils","Min must be < max.");return;}
        z.ajouter_culture(new Cult(p,r,f,mn,mx,mnh,mxh));
        refreshOverview(); appendLog("Culture added to "+z.getNom());
    }

    private void handleUpdateCultureStage() {
        Culture z=chooseItem("Stade culture","Culture zone",cultureZones(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        Cult c=chooseItem("Culture","Choose culture",new ArrayList<>(z.getList_cultures()),Cult::get_etat_cult); if(c==null)return;
        Croissance s=chooseEnum("Stade","Growth stage",Croissance.values()); if(s==null)return;
        c.mettre_a_jour_stade_croissance(s); refreshOverview(); appendLog("Stage updated: "+s);
    }

    private void handleCultureReport() {
        StringBuilder sb=new StringBuilder();
        for(Zone z:ferme.getAll_zones()) if(z instanceof Culture c) sb.append(c.rapport_etat_cult()).append("\n");
        appendLog(sb.length()==0?"No culture zone.":sb.toString());
    }

    private void handleAddAnimal() {
        Elevage z=chooseItem("Animal","Elevage zone",elevageZones(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        Integer age=promptInt("Age","Months"); if(age==null)return;
        Type sp=chooseEnum("Espèce","Species",Type.values()); if(sp==null)return;
        Float w=promptFloat("Poids","kg"); if(w==null)return;
        z.ajouter_animal(new Animal(age,sp,w)); refreshOverview(); appendLog("Animal added to "+z.getNom());
    }

    private void handleSanitaryEvent() {
        Elevage z=chooseItem("Sanitaire","Elevage zone",elevageZones(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        Animal a=chooseItem("Animal","Choose animal",new ArrayList<>(z.getList_animaux()),this::animalLabel); if(a==null)return;
        Type_Event_Sant t=chooseEnum("Event","Type",Type_Event_Sant.values()); if(t==null)return;
        String d=promptText("Description","Describe"); if(d==null)return;
        a.consigne_event_sanitaire(new Event_Sanitaire(t,d));
        if(t==Type_Event_Sant.EVOL_POID){Float nw=promptFloat("Poids","New weight kg"); if(nw!=null)a.setPoid(nw);}
        refreshOverview(); appendLog("Sanitary event for "+animalLabel(a));
    }

    private void handleDefineProgram() {
        List<Elevage> elevages = elevageZones();
        List<Aqua> aquas = ferme.getAll_zones().stream()
                .filter(z -> z instanceof Aqua).map(z -> (Aqua) z).collect(Collectors.toList());
        Object selected = chooseItem("Programme", "Choose an elevage or aquaculture zone",
                concatProgrammableZones(elevages, aquas),
                zone -> zone instanceof Elevage ? zoneLabel((Elevage) zone) : zoneLabel((Aqua) zone));
        if(selected==null)return;
        if(selected instanceof Zone zone && !requireActiveZone(zone))return;
        if(selected instanceof Elevage el){
            Animal a = chooseItem("Animal","Choose animal first", new ArrayList<>(el.getList_animaux()), this::animalLabel);
            if(a==null)return;
            String ft=promptText("Aliment","Food type"); if(ft==null)return;
            Integer q=promptInt("Quantité","Quantity"); if(q==null)return;
            a.definir_programme(ft,q); appendLog("Programme for #"+a.getId());
        } else if(selected instanceof Aqua aq){
            String ft=promptText("Aliment","Food type"); if(ft==null)return;
            Integer q=promptInt("Quantité","Quantity"); if(q==null)return;
            aq.definir_programme(ft,q); appendLog("Programme for "+aq.getNom());
        } else{showError("Unsupported","Zone not programmable.");return;}
        refreshOverview();
    }

    private void handleShowPrograms() {
        StringBuilder sb=new StringBuilder();
        for(Zone z:ferme.getAll_zones()){
            if(z instanceof Elevage el){
                sb.append("=== ").append(el.getNom()).append(" ===\n");
                for(Animal a:el.getList_animaux()) sb.append(animalLabel(a)).append("\n").append(a.afficher_prog()).append("\n\n");
            } else if(z instanceof Aqua aq){
                sb.append("=== ").append(aq.getNom()).append(" ===\n").append(aq.afficher_prog()).append("\n\n");
            }
        }
        appendLog(sb.length()==0?"No programmable zones.":sb.toString());
    }

    private void handleAddSensor() {
        Integer t=promptInt("Capteur","1=Numérique  2=GPS"); if(t==null)return;
        Zone z=chooseItem("Zone","Target zone",zoneList(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        if(t==1){
            Integer st=promptInt("Sous-type","1=Sol 2=Env 3=Eau 4=Bio"); if(st==null)return;
            Float wn=promptFloat("Warn min","Warning minimum threshold"); if(wn==null)return;
            Float wx=promptFloatGreaterThan("Warn max","Warning maximum threshold", wn, "Warning max must be greater than warning min."); if(wx==null)return;
            Float cn=promptFloatLessThan("Crit min","Critical minimum threshold", wn, "Critical min must be lower than warning min."); if(cn==null)return;
            Float cx=promptFloatGreaterThan("Crit max","Critical maximum threshold", wx, "Critical max must be greater than warning max."); if(cx==null)return;
            while (cx <= cn) {
                showError("Seuils", "Critical max must also be greater than critical min.");
                cx=promptFloatGreaterThan("Crit max","Critical maximum threshold", wx, "Critical max must be greater than warning max."); if(cx==null)return;
            }
            Float v=promptFloat("Valeur","Current value"); if(v==null)return;
            String u=promptText("Unité",""); if(u==null)return;
            Num s;
            if     (st==1)s=new Sol(0,Stat_Capt.ACTIF,z,wn,wx,cn,cx,v,u);
            else if(st==2)s=new Env(0,Stat_Capt.ACTIF,z,wn,wx,cn,cx,v,u);
            else if(st==3)s=new Eau(0,Stat_Capt.ACTIF,z,wn,wx,cn,cx,v,u);
            else if(st==4)s=new Bio(0,Stat_Capt.ACTIF,z,wn,wx,cn,cx,v,u);
            else{showError("Invalid","1-4 only");return;}
            z.ajouter_capteur(s); refreshOverview(); appendLog("Sensor added to "+z.getNom());
        } else if(t==2){
            if(!(z instanceof Elevage el)){showError("GPS","GPS only for elevage.");return;}
            Animal a=chooseItem("Animal","Choose",new ArrayList<>(el.getList_animaux()),this::animalLabel); if(a==null)return;
            Float la=promptFloat("Lat","Current latitude"); if(la==null)return;
            Float lo=promptFloat("Lon","Current longitude"); if(lo==null)return;
            Float lwn=promptFloat("Lat warn min","Latitude warning minimum threshold"); if(lwn==null)return;
            Float lwx=promptFloatGreaterThan("Lat warn max","Latitude warning maximum threshold", lwn, "Latitude warning max must be greater than latitude warning min."); if(lwx==null)return;
            Float lcn=promptFloatLessThan("Lat crit min","Latitude critical minimum threshold", lwn, "Latitude critical min must be lower than latitude warning min."); if(lcn==null)return;
            Float lcx=promptFloatGreaterThan("Lat crit max","Latitude critical maximum threshold", lwx, "Latitude critical max must be greater than latitude warning max."); if(lcx==null)return;
            while (lcx <= lcn) {
                showError("Seuils", "Latitude critical max must also be greater than latitude critical min.");
                lcx=promptFloatGreaterThan("Lat crit max","Latitude critical maximum threshold", lwx, "..."); if(lcx==null)return;
            }
            Float own=promptFloat("Lon warn min","Longitude warning minimum threshold"); if(own==null)return;
            Float owx=promptFloatGreaterThan("Lon warn max","Longitude warning maximum threshold", own, "Longitude warning max must be greater than longitude warning min."); if(owx==null)return;
            Float ocn=promptFloatLessThan("Lon crit min","Longitude critical minimum threshold", own, "Longitude critical min must be lower than longitude warning min."); if(ocn==null)return;
            Float ocx=promptFloatGreaterThan("Lon crit max","Longitude critical maximum threshold", owx, "Longitude critical max must be greater than longitude warning max."); if(ocx==null)return;
            while (ocx <= ocn) {
                showError("Seuils", "Longitude critical max must also be greater than longitude critical min.");
                ocx=promptFloatGreaterThan("Lon crit max","Longitude critical maximum threshold", owx, "..."); if(ocx==null)return;
            }
            GPS gps=new GPS(0,Stat_Capt.ACTIF,z,la,lo,lwn,lwx,lcn,lcx,own,owx,ocn,ocx);
            z.ajouter_capteur(gps); a.setCollierGPS(gps); refreshOverview(); appendLog("GPS added to "+animalLabel(a));
        } else showError("Invalid","1 or 2");
    }

    private void handleChangeSensorStatus() {
        Capteur c=chooseSensor(); if(c==null)return;
        if(!requireActiveZone(c.getZone()))return;
        Integer ch=promptInt("Statut","1=Actif  2=Suspendu  3=Défaillant"); if(ch==null)return;
        if     (ch==1)c.changeStatus(Stat_Capt.ACTIF);
        else if(ch==2)c.changeStatus(Stat_Capt.SUSP);
        else if(ch==3)c.changeStatus(Stat_Capt.DEF);
        else{showError("Invalid","1-3 only");return;}
        refreshOverview(); appendLog("Status updated: "+capteurLabel(c));
    }

    private void handleRecordReading() {
        Capteur c=chooseSensor(); if(c==null)return;
        if(!requireActiveZone(c.getZone()))return;
        if(c.getStatut()!=Stat_Capt.ACTIF){showError("Inactive","Sensor not active.");return;}
        Releve_Generale r=c.generer_releve(); registerAlert(r); refreshOverview(); appendLog(r.display_releve());
    }

    private void handleHistory() {
        Capteur c=chooseSensor(); if(c==null)return;
        if(!requireActiveZone(c.getZone()))return;
        LocalDate s=promptDate("Début","Start"),e=promptDate("Fin","End");
        if(s==null||e==null)return;
        if(!e.isAfter(s)){showError("Dates","End must be after start.");return;}
        appendLog(c.getHistorique_releve().display_releves(s,e));
    }

    // ── GRAPHIQUE DES RELEVÉS (option 17) ─────────────────────────────────────
    private void handleGraph() {
        Integer mode = promptInt("Graphique des relevés", "1 = Par zone   2 = Par capteur");
        if (mode == null) return;

        if (mode == 1) {
            // -- by zone
            Zone zone = chooseItem("Graphique par zone", "Choisir une zone", zoneList(), this::zoneLabel);
            if (zone == null) return;
            if (!requireActiveZone(zone)) return;

            // Collect all readings from every sensor in the zone
            List<Releve_Generale> allReleves = new ArrayList<>();
            for (Capteur c : zone.getAll_capteurs()) {
                allReleves.addAll(c.getHistorique_releve().getContent());
            }
            if (allReleves.isEmpty()) {
                showInfo("Aucun relevé", "Aucun relevé disponible pour cette zone.");
                return;
            }
            showBarChart("Graphique des relevés — " + zone.getNom(), allReleves);

        } else if (mode == 2) {
            // -- by sensor
            Capteur capteur = chooseSensor();
            if (capteur == null) return;
            if (!requireActiveZone(capteur.getZone())) return;

            List<Releve_Generale> releves = capteur.getHistorique_releve().getContent();
            if (releves.isEmpty()) {
                showInfo("Aucun relevé", "Aucun relevé disponible pour ce capteur.");
                return;
            }
            showBarChart("Graphique des relevés — " + capteurLabel(capteur), releves);

        } else {
            showError("Invalid", "Choose 1 or 2.");
        }
    }

    /**
     * Opens a new window showing a bar chart.
     * Y-axis: 3 levels — NORMAL (bottom), WARNING (middle), CRITIC (top).
     * Each bar is colored green / orange / red according to the reading's Niveau_Releve.
     * The X-axis labels show "Sensor#N R#M" so the user can identify every reading.
     */
    private void showBarChart(String chartTitle, List<Releve_Generale> releves) {
        // ── Canvas dimensions ─────────────────────────────────────────────────
        final double PAD_LEFT   = 90;
        final double PAD_RIGHT  = 30;
        final double PAD_TOP    = 50;
        final double PAD_BOTTOM = 80;
        final double BAR_W      = 36;
        final double BAR_GAP    = 14;

        int n = releves.size();
        double canvasW = PAD_LEFT + n * (BAR_W + BAR_GAP) + PAD_RIGHT;
        double canvasH = 420;
        double chartH  = canvasH - PAD_TOP - PAD_BOTTOM;
        double chartW  = canvasW - PAD_LEFT - PAD_RIGHT;

        Canvas canvas = new Canvas(canvasW, canvasH);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // ── Background ────────────────────────────────────────────────────────
        gc.setFill(Color.web("#1a2e20"));
        gc.fillRect(0, 0, canvasW, canvasH);

        // ── Grid lines + Y-axis labels ────────────────────────────────────────
        // 3 bands: NORMAL = 0..1/3, WARNING = 1/3..2/3, CRITIC = 2/3..1
        String[] yLabels  = {"NORMAL", "WARNING", "CRITIC"};
        double[] yFracs   = {1.0/6.0, 3.0/6.0, 5.0/6.0}; // midpoints of each band
        Color[]  bandBg   = {
            Color.web("#0d2010"), Color.web("#2a1a08"), Color.web("#2a0808")
        };

        // Draw alternating band backgrounds
        for (int i = 0; i < 3; i++) {
            double bandTop    = PAD_TOP + i * (chartH / 3.0);
            double bandHeight = chartH / 3.0;
            gc.setFill(bandBg[2 - i]); // CRITIC at top → index 0 in bandBg is normal bottom
            gc.fillRect(PAD_LEFT, bandTop, chartW, bandHeight);
        }

        // Horizontal dividers
        gc.setStroke(Color.web("#3a5040"));
        gc.setLineWidth(1);
        for (int i = 0; i <= 3; i++) {
            double y = PAD_TOP + i * (chartH / 3.0);
            gc.strokeLine(PAD_LEFT, y, PAD_LEFT + chartW, y);
        }

        // Y-axis labels
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 11));
        gc.setTextAlign(TextAlignment.RIGHT);
        Color[] yLabelColors = {Color.web("#4caf50"), Color.web("#ff9800"), Color.web("#f44336")};
        String[] yLabelsOrdered = {"CRITIC", "WARNING", "NORMAL"};
        for (int i = 0; i < 3; i++) {
            double y = PAD_TOP + (i + 0.5) * (chartH / 3.0);
            gc.setFill(yLabelColors[2 - i]);
            gc.fillText(yLabelsOrdered[i], PAD_LEFT - 8, y + 4);
        }

        // ── Y-axis line ───────────────────────────────────────────────────────
        gc.setStroke(Color.web("#5a8060"));
        gc.setLineWidth(1.5);
        gc.strokeLine(PAD_LEFT, PAD_TOP, PAD_LEFT, PAD_TOP + chartH);

        // ── Bars ──────────────────────────────────────────────────────────────
        gc.setFont(Font.font("Segoe UI", 9));
        gc.setTextAlign(TextAlignment.CENTER);

        for (int i = 0; i < n; i++) {
            Releve_Generale r   = releves.get(i);
            Niveau_Releve   niv = r.getNiveau_releve();

            // Bar Y position: which of the 3 bands?
            int    bandIndex; // 0=normal(bottom), 1=warning(mid), 2=critic(top)
            Color  barColor;
            Color  labelColor;
            switch (niv) {
                case CRIT  -> { bandIndex = 2; barColor = Color.web("#f44336"); labelColor = Color.web("#ff8a80"); }
                case AVERT -> { bandIndex = 1; barColor = Color.web("#ff9800"); labelColor = Color.web("#ffcc80"); }
                default    -> { bandIndex = 0; barColor = Color.web("#4caf50"); labelColor = Color.web("#a5d6a7"); }
            }

            // Bar always starts from the X-axis (bottom) and reaches up to the TOP of its band.
            // bandIndex: 0=NORMAL → top at 2/3 height, 1=WARNING → top at 1/3 height, 2=CRITIC → top at 0
            double bandTopY  = PAD_TOP + (2 - bandIndex) * (chartH / 3.0); // top edge of the level's band
            double chartBase = PAD_TOP + chartH;                            // bottom of the chart (X-axis)
            double barHeight = chartBase - bandTopY;                        // full height from bottom to band top
            double barX      = PAD_LEFT + i * (BAR_W + BAR_GAP) + BAR_GAP / 2.0;
            double barY      = bandTopY;                                    // bar starts at top of its band

            // Shadow
            gc.setFill(barColor.deriveColor(0, 1, 0.5, 0.25));
            gc.fillRoundRect(barX + 2, barY + 2, BAR_W, barHeight, 5, 5);

            // Main bar — rounded top corners only (bottom is flush with X-axis)
            gc.setFill(barColor);
            gc.fillRoundRect(barX, barY, BAR_W, barHeight, 5, 5);

            // Reading index label below X-axis
            gc.setFill(Color.web("#8fb8a0"));
            gc.fillText("R" + (i + 1), barX + BAR_W / 2.0, PAD_TOP + chartH + 16);

            // Sensor name below index
            String sensorShort = r.getCapteur().getClass().getSimpleName().substring(0, Math.min(3, r.getCapteur().getClass().getSimpleName().length()))
                                 + "#" + r.getCapteur().getCode();
            gc.setFill(Color.web("#607060"));
            gc.fillText(sensorShort, barX + BAR_W / 2.0, PAD_TOP + chartH + 28);

            // Level badge on top of bar
            gc.setFill(labelColor);
            gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 8));
            String lvlShort = niv == Niveau_Releve.CRIT ? "CRIT" : niv == Niveau_Releve.AVERT ? "WARN" : "OK";
            gc.fillText(lvlShort, barX + BAR_W / 2.0, barY - 4);
            gc.setFont(Font.font("Segoe UI", 9));
        }

        // ── X-axis line ───────────────────────────────────────────────────────
        gc.setStroke(Color.web("#5a8060"));
        gc.setLineWidth(1.5);
        gc.strokeLine(PAD_LEFT, PAD_TOP + chartH, PAD_LEFT + chartW, PAD_TOP + chartH);

        // ── Chart title ───────────────────────────────────────────────────────
        gc.setFill(Color.web("#e8e0d0"));
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(chartTitle, canvasW / 2.0, 30);

        // ── Legend ────────────────────────────────────────────────────────────
        double legendX = PAD_LEFT + chartW - 180;
        double legendY = PAD_TOP + 10;
        gc.setFont(Font.font("Segoe UI", FontWeight.BOLD, 10));
        gc.setTextAlign(TextAlignment.LEFT);
        String[][] legend = {
            {"#4caf50", "NORMAL"},
            {"#ff9800", "WARNING"},
            {"#f44336", "CRITIC"}
        };
        for (int i = 0; i < legend.length; i++) {
            gc.setFill(Color.web(legend[i][0]));
            gc.fillRoundRect(legendX, legendY + i * 16, 10, 10, 3, 3);
            gc.setFill(Color.web("#c0d8c8"));
            gc.fillText(legend[i][1], legendX + 14, legendY + i * 16 + 9);
        }

        // ── X-axis title ──────────────────────────────────────────────────────
        gc.setFill(Color.web("#8fb8a0"));
        gc.setFont(Font.font("Segoe UI", 10));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("Relevés (R1, R2, … = order of recording  |  Sol/Env/Eau/GPS = sensor type)",
                canvasW / 2.0, PAD_TOP + chartH + 60);

        // ── Wrap in scrollable stage ──────────────────────────────────────────
        ScrollPane scroll = new ScrollPane(canvas);
        scroll.setFitToHeight(true);
        scroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scroll.setStyle("-fx-background: #1a2e20; -fx-background-color: #1a2e20;");

        VBox root = new VBox(scroll);
        root.setStyle("-fx-background-color: #1a2e20;");
        VBox.setVgrow(scroll, Priority.ALWAYS);

        Scene scene = new Scene(root, Math.min(canvasW + 40, 1200), canvasH + 20);
        Stage chartStage = new Stage();
        chartStage.setTitle("Graphique des relevés");
        chartStage.setScene(scene);
        chartStage.show();

        appendLog("Chart opened: " + chartTitle + " (" + n + " readings)");
    }

    private void handleShowAlerts() { appendLog(ferme.getHist_alertes().display_sorted_alertes()); refreshOverview(); }

    private void handleAcknowledgeAlert() {
        Alerte al=chooseItem("Acquitter","Alert",sortedAlerts(),this::alertLabel); if(al==null)return;
        ferme.aquitter_alerte(al); refreshOverview(); appendLog("Alert acknowledged.");
    }

    private void handleDeleteAlert() {
        Alerte al=chooseItem("Supprimer","Alert",sortedAlerts(),this::alertLabel); if(al==null)return;
        ferme.supprimer_alerte(al); refreshOverview(); appendLog("Alert deleted.");
    }

    private void handleProduction() {
        Zone z=chooseItem("Production","Zone",zoneList(),this::zoneLabel); if(z==null)return;
        if(!requireActiveZone(z))return;
        Type_Prod tp=chooseEnum("Type","Production type",Type_Prod.values()); if(tp==null)return;
        boolean okE=z instanceof Elevage&&(tp==Type_Prod.LAIT||tp==Type_Prod.OEUF);
        boolean okC=z instanceof Culture&&(tp==Type_Prod.RECOLTE||tp==Type_Prod.REND_CULT);
        if(!(okE||okC)){showError("Incompatible","Type not compatible with zone.");return;}
        Integer q=promptInt("Qté","Quantity"); if(q==null)return;
        String u=promptText("Unité","Unit"); if(u==null)return;
        z.ajouter_prod(new Prod(LocalDate.now(),LocalTime.now(),tp,q,u));
        refreshOverview(); appendLog("Production recorded for "+z.getNom());
    }

    private void handleProductionHistory() {
        StringBuilder sb=new StringBuilder();
        for(Zone z:ferme.getAll_zones()){
            sb.append("=== ").append(z.getNom()).append(" ===\n");
            List<Prod> ps=z.getHistorique_prod().getContent();
            if(ps.isEmpty()) sb.append("(none)\n\n");
            else{for(Prod p:ps)sb.append(p.display_prod()).append("\n");sb.append("\n");}
        }
        appendLog(sb.length()==0?"No zones.":sb.toString());
    }

    // ── HELPERS ─────────────────────────────────────────────────────────────
    private List<Zone>    zoneList()     { return new ArrayList<>(ferme.getAll_zones()); }
    private List<Culture> cultureZones() { return ferme.getAll_zones().stream().filter(z->z instanceof Culture).map(z->(Culture)z).collect(Collectors.toList()); }
    private List<Elevage> elevageZones() { return ferme.getAll_zones().stream().filter(z->z instanceof Elevage).map(z->(Elevage)z).collect(Collectors.toList()); }
    private List<Zone> concatProgrammableZones(List<Elevage> elevages, List<Aqua> aquas) {
        List<Zone> zones = new ArrayList<>(elevages);
        zones.addAll(aquas);
        return zones;
    }
    private List<Capteur> allSensors()   { List<Capteur> l=new ArrayList<>(); for(Zone z:ferme.getAll_zones())l.addAll(z.getAll_capteurs()); return l; }
    private Capteur       chooseSensor() { return chooseItem("Capteur","Choose sensor",allSensors(),this::capteurLabel); }
    private List<Alerte>  sortedAlerts() { List<Alerte> l=new ArrayList<>(ferme.getAlertes()); Collections.sort(l); return l; }

    private int scaleBar(float v,float mn,float mx,int w){
        if(Float.compare(mn,mx)==0)return Math.max(1,w/2);
        return Math.max(1,Math.round(Math.max(0f,Math.min(1f,(v-mn)/(mx-mn)))*w));
    }
}