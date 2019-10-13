
package ch2;

import static java.lang.Math.*;
import java.util.function.Function;

public class legendre {
    private final term[] terms;
    private final Function<Double,Double> f;
    private final int N;
    private final term[] pol;
    private final term[][] legendre_series;
    private final double LL, UL;
    
    public legendre(int N, Function<Double,Double> f, double LL, double UL) {
        this.N = N;
        this.f = f;
        terms = new term[N];
        for(int i = 0; i<N; i++)
            terms[i] = new term(0,i);
        pol = new term[N];
        this.LL = LL; this.UL = UL;
        legendre_series = new term[N][N];
        for(int i = 0; i<N; i++)
            for(int j = 0; j<N; j++)
                legendre_series[i][j] = new term(0,j);
        for(int i = 0; i<N; i++)
            pol[i] = new term(1,i);
        gen_coeff();
    }
    
    public double ms_error() {
        double mse = 0;
        double incr = (UL-LL)/1000;
        for(double x = LL; x<UL; x+=incr)
            mse += pow(compute(x)-f.apply(x),2);
        return mse/1000;
    }
    
    public double compute(double x) {
        double sum = 0;
        for(int i = 0; i<N; i++) 
            sum += terms[i].eval(x);
        return sum;
    }
   
    private void gen_coeff() {
        gen_ls();
        double coeff;
        for(int i = 0; i<N; i++) {
            coeff = dot_f(legendre_series[i]);
            for(int j = 0; j<N; j++) {
                terms[j].set_c(terms[j].get_c()+
                        coeff*legendre_series[i][j].get_c());
            }
        }
    } 
    
    private void gen_ls() {
        // look at each of 1,t,t^2...t^n
        for(int i = 0; i<N; i++) {
            // remove components
            term[] loc = legendre_series[i];
            for(int j = 0; j<i; j++) {
                // look at s(i) and u(j)
                double sc = dot(pol[i], legendre_series[j]);
                handle_diff(sc,legendre_series[j],loc);
            }
            // normalize
            loc[i].set_c(loc[i].get_c()+1);
            scl(1/dot_self(loc),loc);
            legendre_series[i] = loc;
        }
    }

    private double dot(term t1, term[] t2) {
        double incr = (UL-LL)/100;
        double sum = 0;
        for(double i = LL; i<UL; i+=incr)
            sum += incr*(t1.eval(i)*eval(i,t2)+
                         t1.eval(i+incr)*eval(i+incr,t2))/2;
        return sum;
    }    
    
    private double dot_self(term[] t1) {
        double incr = (UL-LL)/100;
        double sum = 0;
        for(double i = LL; i<UL; i+=incr)
            sum += incr*(pow(eval(i,t1),2)+
                         pow(eval(i+incr,t1),2))/2;
        return signum(sum)*sqrt(abs(sum));
    }
    
    private double dot_f(term[] t1) {
        double incr = (UL-LL)/100;
        double sum = 0;
        for(double i = LL; i<UL; i+=incr) 
            sum += incr*(eval(i,t1)*f.apply(i)+
                         eval(i+incr,t1)*f.apply(i+incr))/2;
        return sum;
    }
    
    private double eval(double v, term[] t) {
        double sum = 0;
        for (term t1 : t) 
            sum += t1.eval(v);
        return sum;
    }
    
    private void scl(double s, term[] t) {
        for(term tr: t)
            tr.set_c(tr.get_c()*s);
    }
    
    private void handle_diff(double s, 
            term[] src, term[] dest) {
        for(int i = 0; i<dest.length; i++) 
            dest[i].set_c(dest[i].get_c()-src[i].get_c()*s);
    }
}
