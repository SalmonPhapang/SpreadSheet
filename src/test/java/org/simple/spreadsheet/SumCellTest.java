package org.simple.spreadsheet;

import org.junit.jupiter.api.Test;
import org.simple.spreadsheet.exception.SpreadsheetException;
import org.simple.spreadsheet.implementation.CellCoordinate;
import org.simple.spreadsheet.implementation.NumberCell;
import org.simple.spreadsheet.implementation.Spreadsheet;
import org.simple.spreadsheet.implementation.SumCell;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SumCellTest {

    @Test
    void constructorWithValidFormula() {
        SumCell cell = new SumCell("#(sum A1 B2 C3)");
        // No exception should be thrown
    }

    @Test
    void constructorWithInvalidFormula() {
        assertThrows(IllegalArgumentException.class, () -> new SumCell("sum A1 B2"));
        assertThrows(IllegalArgumentException.class, () -> new SumCell("#(sumA1)"));
    }

    @Test
    void getNumericValueWithValidReferences() throws SpreadsheetException {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        NumberCell cellA1 = new NumberCell("1.0");
        NumberCell cellB2 = new NumberCell("2.5");
        NumberCell cellC3 = new NumberCell("3.5");
        when(mockSpreadsheet.getCell(new CellCoordinate("A1"))).thenReturn(cellA1);
        when(mockSpreadsheet.getCell(new CellCoordinate("B2"))).thenReturn(cellB2);
        when(mockSpreadsheet.getCell(new CellCoordinate("C3"))).thenReturn(cellC3);

        SumCell cell = new SumCell("#(sum A1 B2 C3)");
        assertEquals(7.0, cell.getNumericValue(mockSpreadsheet), 0.001);
    }

    @Test
    void getNumericValueWithInvalidReference() {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        when(mockSpreadsheet.getCell(new CellCoordinate("Z9"))).thenReturn(null);

        SumCell cell = new SumCell("#(sum A1 Z9)");
        assertThrows(SpreadsheetException.class, () -> cell.getNumericValue(mockSpreadsheet));
    }

    @Test
    void getNumericValueWithoutSpreadsheet() {
        SumCell cell = new SumCell("#(sum A1 B1)");
        assertThrows(IllegalStateException.class, () -> cell.getNumericValue(null));
    }

    @Test
    void getValueAfterCalculation() throws SpreadsheetException {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        NumberCell cellX1 = new NumberCell("10.0");
        NumberCell cellY2 = new NumberCell("5.0");
        when(mockSpreadsheet.getCell(new CellCoordinate("X1"))).thenReturn(cellX1);
        when(mockSpreadsheet.getCell(new CellCoordinate("Y2"))).thenReturn(cellY2);

        SumCell cell = new SumCell("#(sum X1 Y2)");
        cell.getNumericValue(mockSpreadsheet); // Trigger calculation
        assertEquals("15.00", cell.getValue(mockSpreadsheet));
    }

    @Test
    void getPreferredWidthAfterCalculation() throws SpreadsheetException {
        Spreadsheet mockSpreadsheet = mock(Spreadsheet.class);
        NumberCell cellP1 = new NumberCell("100");
        NumberCell cellQ2 = new NumberCell("200");
        when(mockSpreadsheet.getCell(new CellCoordinate("P1"))).thenReturn(cellP1);
        when(mockSpreadsheet.getCell(new CellCoordinate("Q2"))).thenReturn(cellQ2);

        SumCell cell = new SumCell("#(sum P1 Q2)");
        cell.getNumericValue(mockSpreadsheet);
        assertEquals(String.format("%.2f", 300.0).length(), cell.getPreferredWidth());
    }
}