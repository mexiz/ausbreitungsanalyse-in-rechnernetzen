import java.util.ArrayList;
import java.util.List;

public class Test {

    public static void main(String[] args) throws ParseException {

        //new TupleParser().getTree("(85.193.148.81 (141.255.1.133 122.117.67.158 0.146.197.108) 34.49.145.239 (231.189.0.127 77.135.84.171 39.20.222.120 252.29.23.0 116.132.83.77))");

        String regexByte =  "((2[0-5][0-9])|(1[0-9][0-9])|([1-9][0-9])|([1-9])|(0))";
        
        for(int i = 0; i < 260; i++){
            String ip = String.valueOf(i);
            if(ip.matches(regexByte)){
                System.out.println(ip);
            }else{
                System.out.println("::::");
            }
        }
    

    }

}
