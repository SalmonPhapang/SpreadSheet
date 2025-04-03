package org.simple.spreadsheet;

import org.junit.jupiter.api.Test;
import org.simple.spreadsheet.exception.SpreadsheetException;
import org.simple.spreadsheet.implementation.CellCoordinate;
import org.simple.spreadsheet.implementation.Spreadsheet;
import org.simple.spreadsheet.implementation.StringCell;

import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;

class SpreadsheetTest {

    @Test
    void loadFromCSV_validFile() throws IOException, SpreadsheetException {
        // Create a simple test CSV file in memory
        String csvContent = "Name,Age\nJohn,30\nJane,25";
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("test_spreadsheet", ".csv");
        java.nio.file.Files.write(tempFile, csvContent.getBytes());
        tempFile.toFile().deleteOnExit();

        Spreadsheet spreadsheet = new Spreadsheet(tempFile.toString());
        assertNotNull(spreadsheet.getCell(new CellCoordinate("A1")));
        assertTrue(spreadsheet.getCell(new CellCoordinate("A1")) instanceof StringCell);
        assertEquals("Name", spreadsheet.getCell(new CellCoordinate("A1")).getValue(spreadsheet));
        assertEquals("30.00", spreadsheet.getCell(new CellCoordinate("B2")).getValue(spreadsheet));
    }

    @Test
    void loadFromCSV_inconsistentColumns() throws IOException {
        String csvContent = "Col1,Col2\nVal1\nValA,ValB";
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("inconsistent", ".csv");
        java.nio.file.Files.write(tempFile, csvContent.getBytes());
        tempFile.toFile().deleteOnExit();

        assertThrows(SpreadsheetException.class, () -> new Spreadsheet(tempFile.toString()));
    }

    @Test
    void format_simpleSpreadsheet() throws IOException, SpreadsheetException {
        String csvContent = "Name,Age\nJohn,30\nJane,25";
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("format_simple", ".csv");
        java.nio.file.Files.write(tempFile, csvContent.getBytes());
        tempFile.toFile().deleteOnExit();

        Spreadsheet spreadsheet = new Spreadsheet(tempFile.toString());
        assertNotNull(spreadsheet.format());
    }

    @Test
    void format_withCalculationsAndHorizontalLine() throws IOException, SpreadsheetException {
        String csvContent = "Value1,Value2,Sum\n10,20,#(sum A2 B2)\n#hl,#hl,#hl\nTotal:, ,#(sum C2)";
        java.nio.file.Path tempFile = java.nio.file.Files.createTempFile("format_complex", ".csv");
        java.nio.file.Files.write(tempFile, csvContent.getBytes());
        tempFile.toFile().deleteOnExit();

        Spreadsheet spreadsheet = new Spreadsheet(tempFile.toString());
        assertNotNull(spreadsheet.format());
    }
}