# Simple Spreadsheet Application

This project is a simple command-line spreadsheet application built in Java 8. It reads spreadsheet definitions from CSV files, performs calculations (sum and product), and writes the formatted results to a text file.

## Features

* **Cell Types:** Supports String, Number, Sum, Product, and Horizontal Line cells.
* **CSV Input:** Reads spreadsheet definitions from CSV formatted files.
* **Text Output:** Writes the formatted spreadsheet output to a text file.
* **Calculations:** Implements `#sum` and `#prod` operations referencing cell coordinates.
* **Formatting:**
    * Dynamically adjusts column widths based on the longest cell content.
    * Columns are separated by the "|" character.
    * Strings are left-aligned, and numbers are right-aligned.
    * `#hl` creates horizontal separator lines.
* **Standard Java 8 API:** Uses only standard Java libraries (no external dependencies).
* **Unit Tests:** Includes JUnit tests for core functionality.
* **Build Files:** Provides Maven (`pom.xml`) and Gradle (`build.gradle`) build files.

## Getting Started

### Prerequisites

* Java 8 or higher installed.
* Maven or Gradle (optional, for building and running tests).

### Usage

1.  **Clone the repository:**
    ```bash
    git clone <repository_url>
    cd simple-spreadsheet
    ```

2.  **Build the application (using Maven or Gradle):**

    **Maven:**
    ```bash
    mvn clean package
    ```
    The executable JAR will be in the `target` directory.

    **Gradle:**
    ```bash
    gradle clean build
    ```
    The executable JAR will be in the `build/libs` directory.

3.  **Run the application:**

    ```bash
    java -jar <path_to_executable_jar> <input_csv_file> <output_text_file>
    ```

    * `<input_csv_file>`: The path to your CSV file defining the spreadsheet.
    * `<output_text_file>`: The path where the formatted output will be written.

    **Example:**
    ```bash
    java -jar target/simple-spreadsheet-1.0-SNAPSHOT.jar input.csv output.txt
    ```

### Example CSV Input (`input.csv`):

```csv
Values,Factor,Product
#hl,#hl,
2.0,1.5,#(prod A3 B3)
3.0,2.0,#(prod A4 B4)
4.5,2.5,#(prod A5 B5)
,,#hl
,Total:,#(sum C3 C4 C5)
Sum test:,#(sum A2 A3),
Prod test:,#(prod A2 A3)
3.0,2.0,#(prod A4 B4)
4.5,2.5,#(prod A5 B5)
,,#hl
,Total:,#(sum C3 C4 C4)
Sum test:,#(sum A2 A3),
Prod test:,#(prod A2 A3)
