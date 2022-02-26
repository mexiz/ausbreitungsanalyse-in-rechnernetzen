import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ParseException {

        //List<Tuple<IP>> test = new TupleParser().getTuple("(85.193.148.81)");

        //for (Tuple<IP> tuple : test) {
        //    System.out.println(tuple.connection());
        //}

        String regexByte = "(((2[0-5][0-5])|(2[0-4]\\d)|(1\\d\\d)|([1-9]\\d)|([0-9])))";
        String regexIP = "(" + regexByte + "[.]){3}" + regexByte;
        String regexBracket = "[(]" + regexIP + "(\\s"+ regexIP  +")*" + "[)]";
    
        String regex = "[(]" + regexIP + "\\s(" + regexIP + "\\s" + "|" + regexBracket  +  "\\s" + ")*" + "[)]";
        
        String ip = "(85.193.148.81 85.193.148.81 (85.193.148.81 (85.193.148.81 85.193.148.81)  85.193.148.81) )";
        System.out.println(ip.matches(regex));
    }

}
