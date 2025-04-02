package org.simple.spreadsheet.implementation;

import org.simple.spreadsheet.cell.Cell;
import org.simple.spreadsheet.exception.SpreadsheetException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Spreadsheet {
    private final Map<CellCoordinate, Cell> cells = new HashMap<>();
    private int numRows = 0;
    private int numCols = 0;

    public Spreadsheet(String inputFile) throws IOException, SpreadsheetException {
        loadFromCSV(inputFile);
    }

    public Cell getCell(CellCoordinate coordinate) {
        return cells.get(coordinate);
    }

    private void loadFromCSV(String inputFile) throws IOException, SpreadsheetException {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            int row = 0;
            while ((line = reader.readLine()) != null) {
                String[] values = line.split(",", -1);
                if (row == 0) {
                    numCols = values.length;
                } else if (values.length != numCols) {
                    throw new SpreadsheetException("Inconsistent number of columns in CSV file.");
                }
                for (int col = 0; col < values.length; col++) {
                    String content = values[col].trim();
                    CellCoordinate coordinate = new CellCoordinate(row + 1, col + 1);
                    Cell cell = createCell(content);
                    cells.put(coordinate, cell);
                }
                row++;
            }
            numRows = row;
        }
    }

    private Cell createCell(String content) {
        if (content.startsWith("#(sum ")) {
            return new SumCell(content);
        } else if (content.startsWith("#(prod ")) {
            return new ProductCell(content);
        } else if (content.equals("#hl")) {
            return new HorizontalLineCell();
        } else {
            try {
                return new NumberCell(content);
            } catch (IllegalArgumentException e) {
                return new StringCell(content);
            }
        }
    }

    public String format() throws SpreadsheetException {
        List<Integer> columnWidths = calculateColumnWidths();
        StringBuilder sb = new StringBuilder();

        for (int i = 1; i <= numRows; i++) {
            for (int j = 1; j <= numCols; j++) {
                CellCoordinate coordinate = new CellCoordinate(i, j);
                Cell cell = cells.get(coordinate);
                int width = columnWidths.get(j - 1);
                if (cell instanceof StringCell) {
                    String value = cell.getValue(this);
                    sb.append(String.format("%-" + width + "s", value));
                } else if (cell instanceof HorizontalLineCell) {
                    for (int k = 0; k < width; k++) {
                        sb.append("-");
                    }
                } else {
                    String value = (cell != null) ? cell.getValue(this) : "";
                    sb.append(String.format("%" + width + "s", value));
                }
                if (j < numCols) {
                    sb.append("|");
                }
            }
            sb.append(System.lineSeparator());
        }
        return sb.toString();
    }

    private List<Integer> calculateColumnWidths() {
        List<Integer> widths = new ArrayList<>(numCols);
        for (int j = 1; j <= numCols; j++) {
            int maxWidth = 0;
            for (int i = 1; i <= numRows; i++) {
                CellCoordinate coordinate = new CellCoordinate(i, j);
                Cell cell = cells.get(coordinate);
                if (cell != null) {
                    maxWidth = Math.max(maxWidth, cell.getPreferredWidth());
                }
            }
            widths.add(maxWidth);
        }
        return widths;
    }
}