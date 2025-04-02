package org.simple.spreadsheet.cell;

import org.simple.spreadsheet.implementation.Spreadsheet;
import org.simple.spreadsheet.exception.SpreadsheetException;

public interface Cell {
    String getValue(Spreadsheet spreadsheet) throws SpreadsheetException; // Returns the formatted string representation of the cell's value
    double getNumericValue(Spreadsheet spreadsheet) throws SpreadsheetException; // Returns the numeric value for calculations
    int getPreferredWidth(); // Returns the preferred width for output formatting
}