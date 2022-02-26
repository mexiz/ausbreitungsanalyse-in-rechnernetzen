
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

public class JunitTest {

    @Test
    @DisplayName("HELLO")
    @RepeatedTest(1)
    void simpleIP() throws ParseException {

        assertEquals(new IP("0.0.0.0").toString(), "0.0.0.0");
        assertEquals(new IP("0.0.0.0").toString(), "0.0.0.0");
        assertEquals(new IP("255.255.255.255").toString(), "255.255.255.255");
        assertEquals(new IP("34.49.145.239").toString(), "34.49.145.239");
        assertThrows(ParseException.class, () -> new IP("256.256.256.256"));
        assertThrows(ParseException.class, () -> new IP("00.00.00.001"));
        assertThrows(ParseException.class, () -> new IP("255.255.255.255.255"));
        assertThrows(ParseException.class, () -> new IP("a.a.a.a"));
        assertThrows(ParseException.class, () -> new IP("0.0.0.0."));
        assertThrows(ParseException.class, () -> new IP("..."));
    }

    @Test
    @RepeatedTest(1)
    void compareTo() throws ParseException {

        assertEquals(new IP("0.0.0.0").compareTo(new IP("255.255.255.255")), -1);

        assertEquals(new IP("0.0.0.0").compareTo(new IP("0.0.0.1")), -1);
        assertEquals(new IP("0.0.0.0").compareTo(new IP("0.0.1.0")), -1);
        assertEquals(new IP("0.0.0.0").compareTo(new IP("0.1.0.0")), -1);
        assertEquals(new IP("0.0.0.0").compareTo(new IP("1.0.0.0")), -1);

        assertEquals(new IP("1.0.0.0").compareTo(new IP("0.0.0.0")), 1);
        assertEquals(new IP("0.1.0.0").compareTo(new IP("0.0.0.0")), 1);
        assertEquals(new IP("0.0.1.0").compareTo(new IP("0.0.0.0")), 1);
        assertEquals(new IP("0.0.0.1").compareTo(new IP("0.0.0.0")), 1);

        assertEquals(new IP("0.0.0.0").compareTo(new IP("0.0.0.0")), 0);

    }

    @Test
    @RepeatedTest(1)
    void testGetTree() {

    }

}