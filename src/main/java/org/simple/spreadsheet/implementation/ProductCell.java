package org.simple.spreadsheet.implementation;

import org.simple.spreadsheet.cell.Cell;
import org.simple.spreadsheet.util.Constants;
import org.simple.spreadsheet.exception.SpreadsheetException;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

public class ProductCell implements Cell {
    private static final Pattern PROD_PATTERN = Pattern.compile("#\\(prod\\s+(.+)\\)");
    private final List<CellCoordinate> cellReferences;
    private String cachedValue; // To store the formatted value after calculation

    public ProductCell(String formula) {
        Matcher matcher = PROD_PATTERN.matcher(formula);
        if (matcher.matches()) {
            String[] refs = matcher.group(1).split("\\s+");
            this.cellReferences = new ArrayList<>();
            for (String ref : refs) {
                this.cellReferences.add(new CellCoordinate(ref));
            }
        } else {
            throw new IllegalArgumentException("Invalid product formula: " + formula);
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
        double product = 1;
        for (CellCoordinate ref : cellReferences) {
            Cell cell = spreadsheet.getCell(ref);
            if (cell == null) {
                throw new SpreadsheetException("Invalid cell reference: " + ref);
            }
            product *= cell.getNumericValue(spreadsheet);
        }
        this.cachedValue = String.format(Constants.TWO_DECIMAL_FORMAT, product); // Cache the formatted value
        return product;
    }

    @Override
    public int getPreferredWidth() {
        // Calculate preferred width based on the cached value or a placeholder
        return (cachedValue != null) ? cachedValue.length() : String.format(Constants.TWO_DECIMAL_FORMAT, 0.0).length();
    }
}