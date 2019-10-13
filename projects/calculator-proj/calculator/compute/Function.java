
package caloptimizer;

public class Function {
    
    public Function() {
        
    }
    
    public double eval(double x) {
        return Math.exp(x);
    }
    
    public double approx(double x) {
        Taylor_Matrix tm = new Taylor_Matrix(this, 10);
        double[] solutions = tm.getTaylorPolynomal();
        double val = 0;
        
        for(int i = 0; i<solutions.length-1; i++) {
            val += solutions[i]*Math.pow(x, i);
        }
        
        return val;
    }
}
