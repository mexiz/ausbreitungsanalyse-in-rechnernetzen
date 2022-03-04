package edu.kit.informatik;

import edu.kit.informatik.model.IP;

/**
 * 
 * Testklasse
 * 
 * @author uwhlp
 * @version 1.0
 * 
 */

public class Test {

    /**
     * Main-Methde
     * 
     * @param args args
     * @throws ParseException Exception
     */
    public static void main(String[] args) throws ParseException {

        final String SMALL_NET = "(85.193.148.81 (141.255.1.133 122.117.67.158 0.146.197.108) 34.49.145.239 "
                        + "(231.189.0.127 77.135.84.171 39.20.222.120 252.29.23.0 116.132.83.77))";


        final String SMALL_NET_SORTED = "(85.193.148.81 34.49.145.239 (141.255.1.133 0.146.197.108 122.117.67.158) "
                        + "(231.189.0.127 39.20.222.120 77.135.84.171 116.132.83.77 252.29.23.0))";

        Network net = new Network(SMALL_NET);
        net.getLevels(new IP("0.0.0.0"));
        
        //Network net2 = new Network("(0.0.0.0 1.1.1.1)");

        //net.add(net2);


        
        System.out.println("ENDE");
    }

}
