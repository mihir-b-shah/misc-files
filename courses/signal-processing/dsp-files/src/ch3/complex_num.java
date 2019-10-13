
package ch3;

import static java.lang.Math.*;

public class complex_num {
    
    private double re;
    private double im;
    
    public complex_num(double r, double i) {
        re = r; im = i;
    }

    public double get_re() {return re;}
    public double get_im() {return im;}
    
    /* Optimize memory allocation for add/mult
       Pass the accruer as the calling object */
    public void add_mult(complex_num c1, complex_num c2) {
        re += c1.get_re()*c2.get_re()-c1.get_im()*c2.get_im();
        im += c1.get_im()*c2.get_re()+c1.get_re()*c2.get_im();
    }
    
    public complex_num exp(double p) {
        double mgn = sqrt(re*re+im*im);
        double mgn_adj = signum(mgn)*pow(abs(mgn),p/2);
        double adj_arg = Double.compare(re,0) == 0 ? 0 : im/re;
        double ang = p*(re>0 ? atan(adj_arg): atan(adj_arg)+PI);
        return new complex_num(mgn_adj*cos(ang), mgn_adj*sin(ang));
    }
    
    public complex_num add(complex_num c1, complex_num c2) {
        return new complex_num(c1.get_re()+c2.get_re(),c1.get_im()+c2.get_im());
    }
    
    // Optimize incrementing
    public void incr(complex_num c) {
        re += c.get_re(); im += c.get_im();
    }
    
    public double calc_mgn() {
        return sqrt(re*re+im*im);
    }
    
    public double calc_arg() {
        return re > 0 ? atan(im/re) : atan(im/re) + PI;
    }
    
    @Override
    public boolean equals(Object o) {
        complex_num obj = (complex_num) o;
        return o instanceof complex_num 
                && abs(re-obj.get_re()) < 1e-4
                && abs(im-obj.get_im()) < 1e-4;
    }
    
    public complex_num mult(complex_num c1, complex_num c2) {
        return new complex_num(c1.get_re()*c2.get_re()-c1.get_im()*c2.get_im(),
                               c1.get_im()*c2.get_re()+c1.get_re()*c2.get_im());
    }
    
    public complex_num scal_mlt(double sc) {
        re *= sc; im *= sc; 
        return this;
    }
    
    public complex_num scal_mlt_new(double sc) {
        return new complex_num(re*sc,im*sc);
    }
    
    @Override
    public String toString() {
        return String.format("|Z| = %.3f, θ = %.3f", calc_mgn(), calc_arg());
        //return String.format("%.2f + %.2fi", re, im);
    }
}
