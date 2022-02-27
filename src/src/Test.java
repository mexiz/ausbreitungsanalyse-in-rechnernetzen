import static org.junit.jupiter.api.DynamicTest.stream;

import java.util.ArrayList;
import java.util.List;

public class Test {

    private static String regexByte = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
    private static String regexIP = "(" + regexByte + "\\.){3}" + regexByte;

    private static String regexSPACE = "(" + regexIP + "\\s)*" + regexIP;

    public static void main(String[] args) throws ParseException {

        String ip = "(85.193.148.81 85.193.148.81 (85.193.148.81 (85.193.148.81 85.193.148.81)))";
        
    }


}
