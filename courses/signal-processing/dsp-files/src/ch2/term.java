
package ch2;

public class term {
    
    private int pwr;
    private double cf;
    
    public term() {}
    public term(double c, int p) {
        cf = c;
        pwr = p;
    }
    
    public double get_p() {return pwr;}
    public double get_c() {return cf;}
    public void set_p(int p) {pwr = p;}
    public void set_c(double c) {cf = c;}
    public double eval(double t) {return cf*Math.pow(t,pwr);}
    
    @Override
    public String toString() {
        return String.format("%.5fx^%d", cf, pwr);
    }
}
