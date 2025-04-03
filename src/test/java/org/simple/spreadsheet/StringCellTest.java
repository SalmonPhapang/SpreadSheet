package org.simple.spreadsheet;

import org.junit.jupiter.api.Test;
import org.simple.spreadsheet.exception.SpreadsheetException;
import org.simple.spreadsheet.implementation.StringCell;

import static org.junit.jupiter.api.Assertions.*;

class StringCellTest {
    @Test
    void getValue() {
        StringCell cell = new StringCell("Hello");
        assertEquals("Hello", cell.getValue(null));
    }

    @Test
    void getNumericValueThrowsException() {
        StringCell cell = new StringCell("Text");
        assertThrows(SpreadsheetException.class, () -> cell.getNumericValue(null));
    }

    @Test
    void getPreferredWidth() {
        StringCell cell = new StringCell("World");
        assertEquals(5, cell.getPreferredWidth());
    }

    @Test
    void getValueWithEmptyString() {
        StringCell cell = new StringCell("");
        assertEquals("", cell.getValue(null));
    }

    @Test
    void getPreferredWidthWithEmptyString() {
        StringCell cell = new StringCell("");
        assertEquals(0, cell.getPreferredWidth());
    }

    @Test
    void getValueWithSpecialCharacters() {
        StringCell cell = new StringCell("!@#$%^&*()");
        assertEquals("!@#$%^&*()", cell.getValue(null));
        assertEquals(10, cell.getPreferredWidth());
    }
}