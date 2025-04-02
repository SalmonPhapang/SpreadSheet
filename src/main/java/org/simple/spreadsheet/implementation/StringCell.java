package org.simple.spreadsheet.implementation;

import org.simple.spreadsheet.cell.Cell;
import org.simple.spreadsheet.exception.SpreadsheetException;

public class StringCell implements Cell {
    private final String value;

    public StringCell(String value) {
        this.value = value;
    }

    @Override
    public String getValue(Spreadsheet spreadsheet) {
        return value;
    }

    @Override
    public double getNumericValue(Spreadsheet spreadsheet) throws SpreadsheetException {
        throw new SpreadsheetException("String cell cannot be used in numeric calculations.");
    }

    @Override
    public int getPreferredWidth() {
        return value.length();
    }
}