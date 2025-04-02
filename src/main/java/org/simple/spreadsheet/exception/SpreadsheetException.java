package org.simple.spreadsheet.exception;

public class SpreadsheetException extends Exception {
    public SpreadsheetException(String message) {
        super(message);
    }

    public SpreadsheetException(String message, Throwable cause) {
        super(message, cause);
    }
}
