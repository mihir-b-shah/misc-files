
package ch2;

// gen taylor series 

import java.util.function.BiFunction;
import java.util.function.Function;

public class taylor {
    private final term[] terms;
    private final Function<Double,Double> f;

    public taylor(int N, double r, Function<Double,Double> f, BiFunction<Double,Double,Double> df) {
        terms = new term[N];
        this.f = f;
        int factr = 1;
        terms[0] = new term(f.apply(r),0);
        for(int i = 1; i<N; i++) 
            terms[i] = new term(df.apply(r,(double) i)/(factr*=i),i);
    }

    public double ms_error(double LL, double UL) {
        double mse = 0;
        double incr = (UL-LL)/1000;
        for(double x = LL; x<UL; x+=incr)
            mse += Math.pow(compute(x)-f.apply(x),2);
        return mse/1000;
    }
    
    public double compute(double x) {
        double sum = 0;
        for(term t: terms)
            sum += t.eval(x);
        return sum;
    }
}
