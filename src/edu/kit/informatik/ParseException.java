package edu.kit.informatik;

/**
 * Exception für das einsetzen
 * @author uwhlp
 * @version 1.0
 */

public class ParseException extends Exception {

    /**
     * Konstruktor mit der beliegen Nachricht
     * 
     * @param message die Nachricht
     */
    public ParseException(String message) {
        super(message);
    }
}
