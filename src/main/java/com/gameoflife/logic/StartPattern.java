package com.gameoflife.logic;

public enum StartPattern {

    //Kein Muster ausgewaehlt
    NONE("Einzelzelle (frei zeichnen)", new int[][]{}),

    //Muster der "Rakete"
    ROCKET("Rakete", new int[][]{
            {-1, 0},
            {0, 1},
            {1, -1}, {1, 0}, {1, 1}
    }),

    //Muster eines Sternes
    STAR("Stern", new int[][]{
            {0, 0},
            {-1, 0}, {-2, 0}, {1, 0}, {2, 0},
            {0, -1}, {0, -2}, {0, 1}, {0, 2},
            {-1, -1}, {-2, -2}, {-1, 1}, {-2, 2},
            {1, -1}, {2, -2}, {1, 1}, {2, 2}
    }),

    //Muster eines Propellers - dreht sich nicht
    PROPELLER("Propeller", new int[][]{
            {0, 0},
            {1, 0}, {2, 0}, {3, 1},
            {0, -1}, {0, -2}, {1, -3},
            {-1, 0}, {-2, 0}, {-3, -1},
            {0, 1}, {0, 2}, {-1, 3}
    });

    private final String displayName;  // Wird im Dropdown-Menue angezeigt
    private final int[][] offsets;     // {row, column}-Offsets relativ zum Mittelpunkt

    StartPattern(String displayName, int[][] offsets) {
        this.displayName = displayName;
        this.offsets = offsets;
    }

    public int[][] getOffsets() {
        return offsets;
    }

    // Sorgt dafuer, dass die ComboBox den deutschen Anzeigenamen statt "ROCKET" etc. zeigt
    @Override
    public String toString() {
        return displayName;
    }
}
