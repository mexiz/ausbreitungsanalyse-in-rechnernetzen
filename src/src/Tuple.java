
public class Tuple<A> {

    private A x;
    private A y ;

    public Tuple(A x , A y){
        this.x = x;
        this.y = y;
    }



//TEST
    public String connection(){
        return "(" +  x + " , " + y + ")";
    }

}
