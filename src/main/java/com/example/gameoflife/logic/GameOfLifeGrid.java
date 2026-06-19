package com.example.gameoflife.logic;

//Klasse für die Logik des Grids

public class GameOfLifeGrid {

    private final int rows;       // Anzahl der Zeilen im Raster
    private final int columns;    // Anzahl der Spalten im Raster
    private boolean[][] cells;    // true = Zelle lebt, false = Zelle ist tot

    //Initiziert das Feld
    public GameOfLifeGrid(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.cells = new boolean[rows][columns]; // Spielfeld, standardmäßig auf false
    }

    //Überprüft, ob die ausgewählte Zelle lebt
    public boolean isAlive(int row, int column) {
        //Zellen außerhalb des Feldes gelten als tot
        if (!isInsideGrid(row, column)) {
            return false;
        }
        return cells[row][column];
    }

    //Setzt eine Zelle als Alive, wenn sie innerhalb des Feldes liegt
    public void setAlive(int row, int column, boolean alive) {
        if (isInsideGrid(row, column)) {
            cells[row][column] = alive;
        }
    }

    // Prueft, ob eine Koordinate innerhalb des Feldes liegt
    private boolean isInsideGrid(int row, int column) {
        return row >= 0 && row < rows && column >= 0 && column < columns;
    }

    //Setzt ein Pattern basierend auf den Mustern von @StartPattern
    public void placePattern(StartPattern pattern, int centerRow, int centerColumn) {
        for (int[] offset : pattern.getOffsets()) {
            setAlive(centerRow + offset[0], centerColumn + offset[1], true);
        }
    }

    //Methode, welche die lebenden Nachbarzellen zählt
    public int countLivingNeighbours(int row, int column) {
        //Legt fest, was das Maximum und Minimum zum Überprüfen ist
        int rowStart = Math.max(row - 1, 0);
        int rowEnd = Math.min(row + 1, rows - 1);
        int columnStart = Math.max(column - 1, 0);
        int columnEnd = Math.min(column + 1, columns - 1);

        int livingNeighbours = 0; // Zaehlt die lebenden Nachbarn

        // Laeuft den (maximal) 3x3 Bereich rund um die Zelle ab
        for (int i = rowStart; i <= rowEnd; i++) {
            for (int j = columnStart; j <= columnEnd; j++) {
                if (i == row && j == column) {
                    //Ueberspringt sich selbst als Nachbar
                    continue;
                }
                if (cells[i][j]) {
                    livingNeighbours++;
                }
            }
        }
        return livingNeighbours;
    }

    //Erstellung der naechsten Generation für das Feld, gemäß der Direktiven (Regeln)
    public boolean[][] theNextGeneration() {
        boolean[][] NCC1701D = new boolean[rows][columns];

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                int neighbours = countLivingNeighbours(row, column);
                boolean ItzAlive = cells[row][column];

                // Die vier Direktiven durch drei Faellen umgesetzt
                if (ItzAlive && (neighbours == 2 || neighbours == 3)) {
                    NCC1701D[row][column] = true;  // Ueberlebt
                } else if (!ItzAlive && neighbours == 3) {
                    NCC1701D[row][column] = true;  // Wird lebendig
                } else {
                    NCC1701D[row][column] = false; // Stirbt (Unter-/Ueberbevoelkerung) oder bleibt tot
                }
            }
        }
        return NCC1701D;
    }

    //Methode, um die naechste Generation auszulösen
    public void nextGeneration() {
        cells = theNextGeneration();
    }

    //Setzt das Grid zurueck
    public void reset() {
        cells = new boolean[rows][columns];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}
