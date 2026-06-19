package com.example.gameoflife;

import com.example.gameoflife.gui.GameOfLifeView;
import com.example.gameoflife.logic.GameOfLifeGrid;
import com.example.gameoflife.logic.StartPattern;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

//Game of Life
//By Franz Horatio Ingo Hirsch
//952210

//Herzstueck, baut das Fenster auf und verwendet die anderen Klassen
public class GameOfLifeApp extends Application {

    private static final int ROWS = 40;
    private static final int COLUMNS = 60;
    private static final int CELL_SIZE = 14; // Pixelgroesse einer Zelle

    private final GameOfLifeGrid grid = new GameOfLifeGrid(ROWS, COLUMNS);
    private GameOfLifeView view;
    private Timeline timeline;            // Steuert die automatische Generationenfolge
    private Label generationLabel;
    private ComboBox<StartPattern> patternComboBox; // Auswahl des Startmusters
    private boolean paintingAlive;        // Wert, der waehrend eines Drag-Vorgangs gesetzt wird
    private int generationCount = 0;

    @Override
    //Baut das Fenster auf
    public void start(Stage stage) {
        view = new GameOfLifeView(grid, CELL_SIZE);
        view.draw();

        //Legt die Aktionen fest, wenn die Maus bewegt oder geclickt wird
        view.setOnMousePressed(this::handleMousePressed);
        view.setOnMouseDragged(this::handleMouseDragged);

        BorderPane root = new BorderPane();
        root.setBottom(buildControlBar());
        root.setCenter(view);

        Scene scene = new Scene(root);
        stage.setTitle("Game of Life");
        stage.setScene(scene);
        stage.show();
    }

    // Methode, welche verwaltet, was bei einem Mausclick passiert
    private void handleMousePressed(MouseEvent event) {
        if (isThisTheMatrix()) {
            return; //Waehrend die Simulation laeuft, darf das Feld nicht bearbeitet werden
        }
        int row = view.toRow(event.getY());
        int column = view.toColumn(event.getX());

        StartPattern selectedPattern = patternComboBox.getValue();
        if (selectedPattern != StartPattern.NONE) {
            grid.placePattern(selectedPattern, row, column);
        } else {
            //Legt fest, ob ein Drag-Versuch leben gibt oder nimmt
            paintingAlive = !grid.isAlive(row, column);
            grid.setAlive(row, column, paintingAlive);
        }
        view.draw();
    }

    //Methode, welche auslöst, wenn Mausclick gedraggt wird
    private void handleMouseDragged(MouseEvent event) {
        //Nur bei freien Zeichnen, filtert andere Muster
        if (isThisTheMatrix() || patternComboBox.getValue() != StartPattern.NONE) {
            return;
        }
        int row = view.toRow(event.getY());
        int column = view.toColumn(event.getX());
        grid.setAlive(row, column, paintingAlive);
        view.draw();
    }

    //Methode, die ueberprueft, ob die Simulation noch laeuft
    private boolean isThisTheMatrix() {
        return timeline != null && timeline.getStatus() == Timeline.Status.RUNNING;
    }

    //Constructor fuer die Kontrollleiste
    private HBox buildControlBar() {
        Button startButton = new Button("Start");
        Button pauseButton = new Button("Pause");
        Button resetButton = new Button("Reset");

        Label speedLabel = new Label("Speed:");
        Slider speedSlider = new Slider(5, 550, 500); // Millisekunden pro Generation
        speedSlider.setPrefWidth(150);

        CheckBox gridToggle = new CheckBox("Gitter");
        gridToggle.setSelected(true);

        Label patternLabel = new Label("Pattern:");
        patternComboBox = new ComboBox<>();
        patternComboBox.getItems().addAll(StartPattern.values());
        patternComboBox.setValue(StartPattern.NONE); // Standard: frei zeichnen

        generationLabel = new Label("Generation: 0");

        startButton.setOnAction(e -> startSimulation(speedSlider.getValue()));
        pauseButton.setOnAction(e -> pauseSimulation());
        resetButton.setOnAction(e -> resetSimulation());
        gridToggle.selectedProperty().addListener((obs, oldVal, newVal) -> view.setShowGrid(newVal));

        //Slider, um die Geschwindigkeit zu kontrollieren
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
                startSimulation(newVal.doubleValue());
            }
        });

        HBox controlBar = new HBox(10, startButton, pauseButton, resetButton,
                speedLabel, speedSlider, gridToggle, patternLabel, patternComboBox, generationLabel);
        controlBar.setAlignment(Pos.CENTER_LEFT);
        controlBar.setPadding(new Insets(10));
        return controlBar;
    }

    //Methode zur (Wieder-)Aufnahme der Simulation
    private void startSimulation(double millisPerGeneration) {
        if (timeline != null) {
            timeline.stop();
        }
        timeline = new Timeline(new KeyFrame(Duration.millis(millisPerGeneration), e -> step()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //Methode zum Pausieren der Simulation
    private void pauseSimulation() {
        if (timeline != null) {
            timeline.pause();
        }
    }

    //Methode zum vollstaendigen Reset
    private void resetSimulation() {
        pauseSimulation();
        grid.reset();
        generationCount = 0;
        generationLabel.setText("Generation: 0");
        view.draw();
    }

    //Methode, welche den naechsten Schritt der Simulation festlegt
    private void step() {
        grid.nextGeneration();
        generationCount++;
        generationLabel.setText("Generation: " + generationCount);
        view.draw();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
