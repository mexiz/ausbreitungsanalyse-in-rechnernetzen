package edu.kit.informatik.model;

import java.util.Arrays;

import edu.kit.informatik.ParseException;

/**
 * @author uwhlp
 * @version 1.0
 */

public class IP implements Comparable<IP> {

    private int[] adress;

    private String regexByte = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
    private String regexIP = "(" + regexByte + "\\.){3}" + regexByte;

    /**
     * Konstruktor mit der Pointnotation
     * 
     * @param pointNotation die Adresse
     * @throws ParseException wenn die Adresse nicht der Form passt
     */
    public IP(final String pointNotation) throws ParseException {

        if (!pointNotation.matches(regexIP)) {
            throw new ParseException("Error: Invalid IP form");
        }

        String[] adressString = pointNotation.split("\\.");

        if (adressString.length != 4) {
            throw new ParseException("Invalid IP-Adress!");
        }
        adress = new int[adressString.length];

        for (int i = 0; i < adressString.length; i++) {
            adress[i] = Integer.parseInt(adressString[i]);

        }

    }

    /**
     * Konstruktor für die copy()-Methide
     * 
     * @param adress die Adresse
     */

    public IP(int[] adress) {
        this.adress = adress;
    }

    @Override
    public String toString() {

        StringBuilder stringBuilder = new StringBuilder(String.valueOf(this.adress[0]));
        for (int i = 1; i < adress.length; i++) {
            stringBuilder.append(".");
            stringBuilder.append(this.adress[i]);
        }
        return stringBuilder.toString();
    }

    @Override
    public int compareTo(IP o) {
        for (int i = 0; i < adress.length; i++) {
            if (o.adress[i] < this.adress[i]) {
                return 1;
            }
            if (o.adress[i] > this.adress[i]) {
                return -1;
            }
        }
        return 0;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(adress);
        result = prime * result + ((regexByte == null) ? 0 : regexByte.hashCode());
        result = prime * result + ((regexIP == null) ? 0 : regexIP.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        IP other = (IP) obj;
        if (!Arrays.equals(adress, other.adress))
            return false;
        if (regexIP == null) {
            if (other.regexIP != null)
                return false;
        } else if (!regexIP.equals(other.regexIP))
            return false;
        return true;
    }

    /**
     * Kopiert das IP-Objekt
     * 
     * @return das kopierte IP-Objekt
     */

    public IP copy() {
        return new IP(adress.clone());
    }
}
