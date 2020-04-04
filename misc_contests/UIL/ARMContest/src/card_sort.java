
import java.util.*;
import java.io.*;

public class card_sort {
    public static void main(String[] args) throws Exception {
        Scanner f = new Scanner(new File("card_sort.dat"));
        ArrayList<Card> list = new ArrayList<Card>();
        while(f.hasNextLine()){
            String[] raw = f.nextLine().split("/");
            Card temp = new Card(raw[0], Double.parseDouble(raw[1]), Double.parseDouble(raw[2]));
            list.add(temp);
        }
        Collections.sort(list);
        Collections.reverse(list);
        for(Card c: list){
            System.out.println(c);
        }
        f.close();
    }
}
class Card implements Comparable<Card>{
    double a,d,p;
    String name;
    public Card(String n, double a, double d){
        this.d = d;
        this.a = a;
        this.name = n;
        p = a +d;
    }
    public int compareTo(Card y){
        if(this.p > y.p){
            return 1;
        }else if(this.p < y.p){
            return -1;
        }else{
            if(this.a > y.a){
                return 1;
            }else if(this.a < y.a){
                return -1;
            }else{
                return y.name.compareTo(this.name);
            }
        }
    }  
    public String toString(){
        return String.format("%.0f (%s/%.0f/%.0f)", p, name, a ,d);
    }
}
