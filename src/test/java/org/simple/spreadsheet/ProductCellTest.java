package org.simple.spreadsheet;

import org.junit.jupiter.api.Test;
import org.simple.spreadsheet.exception.SpreadsheetException;
import org.simple.spreadsheet.implementation.CellCoordinate;
import org.simple.spreadsheet.implementation.NumberCell;
import org.simple.spreadsheet.implementation.ProductCell;
import org.simple.spreadsheet.implementation.Spreadsheet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProductCellTest {

    @Test
    void constructorWithValidFormula() {
        ProductCell cell = new ProductCell("#(prod A1 B2 C3)");
        // No exception should be thrown
    }

    @Test
    void constructorWithInvalidFormula() {
        assertThrows(IllegalArgumentException.class, () -> new ProductCell("prod A1 B2"));
        assertThrows(IllegalArgumentException.class, () -> new ProductCell("#(product A1)"));
    }

    @Test
    void getNumericValueWithValidReferences() throws SpreadsheetException {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        NumberCell cellA1 = new NumberCell("2.0");
        NumberCell cellB2 = new NumberCell("3.0");
        when(mockSpreadsheet.getCell(new CellCoordinate("A1"))).thenReturn(cellA1);
        when(mockSpreadsheet.getCell(new CellCoordinate("B2"))).thenReturn(cellB2);

        ProductCell cell = new ProductCell("#(prod A1 B2)");
        assertEquals(6.0, cell.getNumericValue(mockSpreadsheet));
    }

    @Test
    void getNumericValueWithInvalidReference() {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        when(mockSpreadsheet.getCell(new CellCoordinate("C5"))).thenReturn(null);

        ProductCell cell = new ProductCell("#(prod A1 C5)");
        assertThrows(SpreadsheetException.class, () -> cell.getNumericValue(mockSpreadsheet));
    }

    @Test
    void getNumericValueWithoutSpreadsheet() {
        ProductCell cell = new ProductCell("#(prod A1 B1)");
        assertThrows(IllegalStateException.class, () -> cell.getNumericValue(null));
    }

    @Test
    void getValueAfterCalculation() throws SpreadsheetException {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        NumberCell cellA1 = new NumberCell("2.5");
        NumberCell cellB2 = new NumberCell("4.0");
        when(mockSpreadsheet.getCell(new CellCoordinate("A1"))).thenReturn(cellA1);
        when(mockSpreadsheet.getCell(new CellCoordinate("B2"))).thenReturn(cellB2);

        ProductCell cell = new ProductCell("#(prod A1 B2)");
        cell.getNumericValue(mockSpreadsheet); // Trigger calculation
        assertEquals("10.00", cell.getValue(mockSpreadsheet));
    }

    @Test
    void getPreferredWidthAfterCalculation() throws SpreadsheetException {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        NumberCell cellA1 = new NumberCell("10");
        NumberCell cellB2 = new NumberCell("20");
        when(mockSpreadsheet.getCell(new CellCoordinate("A1"))).thenReturn(cellA1);
        when(mockSpreadsheet.getCell(new CellCoordinate("B2"))).thenReturn(cellB2);

        ProductCell cell = new ProductCell("#(prod A1 B2)");
        cell.getNumericValue(mockSpreadsheet);
        assertEquals(String.format("%.2f", 200.0).length(), cell.getPreferredWidth());
    }
}