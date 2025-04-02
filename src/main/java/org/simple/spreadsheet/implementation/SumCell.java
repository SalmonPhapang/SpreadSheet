package org.simple.spreadsheet.implementation;

import org.simple.spreadsheet.cell.Cell;
import org.simple.spreadsheet.util.Constants;
import org.simple.spreadsheet.exception.SpreadsheetException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SumCell implements Cell {
    private static final Pattern SUM_PATTERN = Pattern.compile("#\\(sum\\s+(.+)\\)");
    private final List<CellCoordinate> cellReferences;
    private String cachedValue; // To store the formatted value after calculation

    public SumCell(String formula) {
        Matcher matcher = SUM_PATTERN.matcher(formula);
        if (matcher.matches()) {
            String[] refs = matcher.group(1).split("\\s+");
            this.cellReferences = new ArrayList<>();
            for (String ref : refs) {
                this.cellReferences.add(new CellCoordinate(ref));
            }
        } else {
            throw new IllegalArgumentException("Invalid sum formula: " + formula);
        }
        this.cachedValue = null; // Invalidate cache on creation
    }

    @Override
    public String getValue(Spreadsheet spreadsheet) throws SpreadsheetException {
        // Return the cached value if available
        return (cachedValue != null) ? cachedValue : String.format(Constants.TWO_DECIMAL_FORMAT, getNumericValue(spreadsheet));
    }

    @Override
    public double getNumericValue(Spreadsheet spreadsheet) throws SpreadsheetException {
        if (spreadsheet == null) {
            throw new IllegalStateException("Spreadsheet instance required for calculation.");
        }
        double sum = 0;
        for (CellCoordinate ref : cellReferences) {
            Cell cell = spreadsheet.getCell(ref);
            if (cell == null) {
                throw new SpreadsheetException("Invalid cell reference: " + ref);
            }
            sum += cell.getNumericValue(spreadsheet);
        }
        this.cachedValue = String.format(Constants.TWO_DECIMAL_FORMAT, sum); // Cache the formatted value
        return sum;
    }

    @Override
    public int getPreferredWidth() {
        // Calculate preferred width based on the cached value or a placeholder
        return (cachedValue != null) ? cachedValue.length() : String.format(Constants.TWO_DECIMAL_FORMAT, 0.0).length();
    }
}
