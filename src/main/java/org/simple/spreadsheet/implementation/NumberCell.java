package org.simple.spreadsheet.implementation;

import org.simple.spreadsheet.cell.Cell;
import org.simple.spreadsheet.util.Constants;

public class NumberCell implements Cell {
    private final double value;

    public NumberCell(String value) {
        try {
            this.value = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format: " + value);
        }
    }

    @Override
    public String getValue(Spreadsheet spreadsheet) {
        return String.format(Constants.TWO_DECIMAL_FORMAT, value); // Format to 2 decimal places
    }

    @Override
    public double getNumericValue(Spreadsheet spreadsheet) {
        return value;
    }

    @Override
    public int getPreferredWidth() {
        return String.format(Constants.TWO_DECIMAL_FORMAT, value).length();
    }
}