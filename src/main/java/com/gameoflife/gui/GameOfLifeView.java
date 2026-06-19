package com.gameoflife.gui;

import com.gameoflife.logic.GameOfLifeGrid;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//kreiert die Canvas fuer eine Grid
public class GameOfLifeView extends Canvas {

    private final GameOfLifeGrid grid;
    private final int cellSize;     // Groesse einer Zelle in Pixel
    private boolean showGrid = true; // Boolean für ein/ausschaltbares Grid

    public GameOfLifeView(GameOfLifeGrid grid, int cellSize) {
        super(grid.getColumns() * cellSize, grid.getRows() * cellSize);
        this.grid = grid;
        this.cellSize = cellSize;
    }

    //Zeichnet das gesamte Spielfeld neu
    public void draw() {
        GraphicsContext gc = getGraphicsContext2D();

        //Hintergrund leeren
        gc.setFill(Color.web("#1e1e1e"));
        gc.fillRect(0, 0, getWidth(), getHeight());

        //Lebendige Zelle werden farblich eingezeichnet
        gc.setFill(Color.web("#8b2252"));
        for (int row = 0; row < grid.getRows(); row++) {
            for (int column = 0; column < grid.getColumns(); column++) {
                if (grid.isAlive(row, column)) {
                    gc.fillRect(column * cellSize, row * cellSize, cellSize, cellSize);
                }
            }
        }

        if (showGrid) {
            drawGridLines(gc);
        }
    }

    //Zeichnet das Grid-Muster auf das Feld
    private void drawGridLines(GraphicsContext gc) {
        gc.setStroke(Color.web("#3a3a3a"));
        for (int row = 0; row <= grid.getRows(); row++) {
            gc.strokeLine(0, row * cellSize, getWidth(), row * cellSize);
        }
        for (int column = 0; column <= grid.getColumns(); column++) {
            gc.strokeLine(column * cellSize, 0, column * cellSize, getHeight());
        }
    }

    //Berechnet die Y-Position bei einem Mausclick fuer das Grid
    public int toRow(double pixelY) {
        return (int) (pixelY / cellSize);
    }

    //Berechnet die X-Position bei einem Mausclick fuer das Grid
    public int toColumn(double pixelX) {
        return (int) (pixelX / cellSize);
    }

    public void setShowGrid(boolean showGrid) {
        this.showGrid = showGrid;
        draw();
    }
}
