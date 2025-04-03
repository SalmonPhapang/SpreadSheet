package org.simple.spreadsheet;


import org.simple.spreadsheet.implementation.CellCoordinate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CellCoordinateTest {

    @Test
    void constructorFromStringValid() {
        CellCoordinate a1 = new CellCoordinate("A1");
        assertEquals(1, a1.getRow());
        assertEquals(1, a1.getCol());

        CellCoordinate b10 = new CellCoordinate("B10");
        assertEquals(10, b10.getRow());
        assertEquals(2, b10.getCol());

        CellCoordinate z26 = new CellCoordinate("Z26");
        assertEquals(26, z26.getRow());
        assertEquals(26, z26.getCol());

        CellCoordinate aa1 = new CellCoordinate("AA1");
        assertEquals(1, aa1.getRow());
        assertEquals(27, aa1.getCol());

        CellCoordinate ab5 = new CellCoordinate("AB5");
        assertEquals(5, ab5.getRow());
        assertEquals(28, ab5.getCol());
    }

    @Test
    void constructorFromStringInvalid() {
        assertThrows(IllegalArgumentException.class, () -> new CellCoordinate("1A"));
        assertThrows(IllegalArgumentException.class, () -> new CellCoordinate("A"));
        assertThrows(IllegalArgumentException.class, () -> new CellCoordinate("A-1"));
        assertThrows(IllegalArgumentException.class, () -> new CellCoordinate(""));
    }

    @Test
    void constructorFromInts() {
        CellCoordinate coord1 = new CellCoordinate(5, 10);
        assertEquals(5, coord1.getRow());
        assertEquals(10, coord1.getCol());
    }

    @Test
    void equalsAndHashCode() {
        CellCoordinate coord1 = new CellCoordinate("C3");
        CellCoordinate coord2 = new CellCoordinate("C3");
        CellCoordinate coord3 = new CellCoordinate("D3");
        CellCoordinate coord4 = new CellCoordinate("C4");

        assertEquals(coord1, coord2);
        assertEquals(coord1.hashCode(), coord2.hashCode());
        assertNotEquals(coord1, coord3);
        assertNotEquals(coord1.hashCode(), coord3.hashCode());
        assertNotEquals(coord1, coord4);
        assertNotEquals(coord1.hashCode(), coord4.hashCode());
    }

    @Test
    void toStringTest() {
        assertEquals("A1", new CellCoordinate(1, 1).toString());
        assertEquals("B10", new CellCoordinate(10, 2).toString());
        assertEquals("Z26", new CellCoordinate(26, 26).toString());
        assertEquals("AA1", new CellCoordinate(1, 27).toString());
        assertEquals("AB5", new CellCoordinate(5, 28).toString());
        assertEquals("AZ1", new CellCoordinate(1, 52).toString());
        assertEquals("BA1", new CellCoordinate(1, 53).toString());
    }
}
