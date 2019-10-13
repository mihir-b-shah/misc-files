
package ch2;

import java.util.function.BiFunction;
import java.util.function.Function;

public class ch2_test {
    public void test() {
        Function<Double, Double> f = x->Math.sin(x);
        BiFunction<Double, Double, Double> df = (x,i)->Math.sin(x+Math.PI/2*i);
        taylor t = new taylor(2,0,f,df);
        legendre l = new legendre(2,f,-1,1);
        
        System.out.printf("Taylor: %f, Legendre: %f, Actual: %f%n",
                t.compute(1), l.compute(1), Math.sin(1));
        System.out.printf("ERROR: Taylor: %f, Legendre: %f%n",
                t.ms_error(-1,1), l.ms_error());
    }
}
