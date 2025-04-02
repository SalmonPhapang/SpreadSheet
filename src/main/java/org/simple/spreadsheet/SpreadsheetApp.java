package org.simple.spreadsheet;

import org.simple.spreadsheet.exception.SpreadsheetException;
import org.simple.spreadsheet.implementation.Spreadsheet;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SpreadsheetApp {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: java SpreadsheetApp <input_file> <output_file>");
            System.exit(1);
        }

        String inputFile = args[0];
        String outputFile = args[1];

        try {
            Spreadsheet spreadsheet = new Spreadsheet(inputFile);
            String output = spreadsheet.format();
            try (FileWriter writer = new FileWriter(outputFile)) {
                BufferedWriter bf = new BufferedWriter(writer);
                bf.write(output);
                bf.close();
            }
            System.out.println(output);
            System.out.println("Spreadsheet processed successfully. Output written to: " + outputFile);
        } catch (IOException e) {
            System.err.println("Error reading/writing files: " + e.getMessage());
        } catch (SpreadsheetException e) {
            System.err.println("Spreadsheet error: " + e.getMessage());
        }
    }
}