package org.simple.spreadsheet.implementation;


import org.simple.spreadsheet.cell.Cell;
import org.simple.spreadsheet.exception.SpreadsheetException;

public class HorizontalLineCell implements Cell {
    @Override
    public String getValue(Spreadsheet spreadsheet) {
        return ""; // The actual line is generated during formatting
    }

    @Override
    public double getNumericValue(Spreadsheet spreadsheet) throws SpreadsheetException {
        throw new SpreadsheetException("Horizontal line cell has no numeric value.");
    }

    @Override
    public int getPreferredWidth() {
        return 0; // Width will be determined by the column width
    }
}