package org.simple.spreadsheet;

import org.junit.jupiter.api.Test;
import org.simple.spreadsheet.exception.SpreadsheetException;
import org.simple.spreadsheet.implementation.NumberCell;

import static org.junit.jupiter.api.Assertions.*;

class NumberCellTest {
    @Test
    void constructorWithValidInteger() {
        NumberCell cell = new NumberCell("123");
        assertEquals(123.0, cell.getNumericValue(null));
        assertEquals("123.00", cell.getValue(null));
        assertEquals(6, cell.getPreferredWidth());
    }

    @Test
    void constructorWithValidDecimal() {
        NumberCell cell = new NumberCell("45.67");
        assertEquals(45.67, cell.getNumericValue(null));
        assertEquals("45.67", cell.getValue(null));
        assertEquals(5, cell.getPreferredWidth());
    }

    @Test
    void constructorWithNegativeNumber() {
        NumberCell cell = new NumberCell("-8.9");
        assertEquals(-8.9, cell.getNumericValue(null));
        assertEquals("-8.90", cell.getValue(null));
        assertEquals(5, cell.getPreferredWidth());
    }

    @Test
    void constructorWithZero() {
        NumberCell cell = new NumberCell("0");
        assertEquals(0.0, cell.getNumericValue(null));
        assertEquals("0.00", cell.getValue(null));
        assertEquals(4, cell.getPreferredWidth());
    }

    @Test
    void constructorWithLeadingAndTrailingSpaces() {
        NumberCell cell = new NumberCell("  10.0  ");
        assertEquals(10.0, cell.getNumericValue(null));
        assertEquals("10.00", cell.getValue(null));
        assertEquals(5, cell.getPreferredWidth());
    }

    @Test
    void invalidNumberFormatThrowsException() {
        assertThrows(IllegalArgumentException.class, () -> new NumberCell("abc"));
        assertThrows(IllegalArgumentException.class, () -> new NumberCell("12.3x"));
        assertThrows(IllegalArgumentException.class, () -> new NumberCell(""));
        assertThrows(IllegalArgumentException.class, () -> new NumberCell(" "));
    }

    @Test
    void getNumericValue() throws SpreadsheetException {
        NumberCell cell = new NumberCell("10.5");
        assertEquals(10.5, cell.getNumericValue(null));
    }

    @Test
    void getValue() {
        NumberCell cell = new NumberCell("98.765");
        assertEquals("98.77", cell.getValue(null)); // Default formatting to 2 decimal places
    }

    @Test
    void getPreferredWidthInteger() {
        NumberCell cell = new NumberCell("1000");
        assertEquals(7, cell.getPreferredWidth()); // "1000.00"
    }

    @Test
    void getPreferredWidthDecimal() {
        NumberCell cell = new NumberCell("3.14159");
        assertEquals(4, cell.getPreferredWidth()); // "3.14"
    }
}
