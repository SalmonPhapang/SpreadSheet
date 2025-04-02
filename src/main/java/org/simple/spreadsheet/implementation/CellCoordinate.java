package org.simple.spreadsheet.implementation;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CellCoordinate {
    private final int row;
    private final int col;

    public CellCoordinate(String coordinate) {
        Pattern pattern = Pattern.compile("([A-Za-z]+)(\\d+)");
        Matcher matcher = pattern.matcher(coordinate);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Invalid cell coordinate format: " + coordinate);
        }
        String colStr = matcher.group(1).toUpperCase();
        this.row = Integer.parseInt(matcher.group(2));
       int col1 = this.getCol();
        for (int i = 0; i < colStr.length(); i++) {
            col1 = col1 * 26 + (colStr.charAt(i) - 'A' + 1);
        }
        this.col = col1;
    }

    public CellCoordinate(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CellCoordinate that = (CellCoordinate) o;
        return row == that.row && col == that.col;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int tempCol = col;
        while (tempCol > 0) {
            int remainder = (tempCol - 1) % 26;
            sb.insert(0, (char) ('A' + remainder));
            tempCol = (tempCol - 1) / 26;
        }
        return sb.toString() + row;
    }
}