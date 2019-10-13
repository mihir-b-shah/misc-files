package caloptimizer;

/**
 * Solves a consistent system of N equations using Gaussian-Jordan elimination
 * Does not work to solve inconsistent systems with error
 */

public class Taylor_Matrix {
    
    private double[][] taylor_matrix;
    public final double DIFF = 1; 
    public final double START = 0;
        
    public double[] solutions;
    public double[] refEqn;
    public double[] coeffs;
    
    public Taylor_Matrix(Function f, int N) {
        taylor_matrix = new double[N][N+1];
        
        for(int i = 0; i<taylor_matrix[0].length; i++)
            taylor_matrix[0][i] = 1;
        
        coeffs = new double[N+1];
        coeffs[0] = 1;
        coeffs[1] = 1;
        
        for(int i = 2; i<N+1; i++) {
            taylor_matrix[0][i-1] = 1/((double) i)*taylor_matrix[0][i - 2];
            coeffs[i] = taylor_matrix[0][i - 1];
        }
        
        for(int i = 1; i<N; i++) {
            for(int j = 0; j<N+1; j++) {
                taylor_matrix[i][j] = taylor_matrix[0][j];
            }
        }
        
        double diff_max = ((double) N)/2;
        
        int TOP = (int) Math.ceil(diff_max);
        int BOTTOM = (int) Math.ceil(-1*diff_max);
       
        for(int i = TOP; i>= BOTTOM; i--) {
            if(i != 0) {
                
                int index = i > 0 ? TOP - i : TOP - i - 1;
                for(int j = 0; j<N; j++) {
                    taylor_matrix[index][j] *= Math.pow(i*DIFF, j+1);
                }
                
                taylor_matrix[index][N] = f.eval(START + i*DIFF);
                taylor_matrix[index][N] -= f.eval(START);
            }
        }
    }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        int LIM_LEN = 24;
        
        for(double[] arr: taylor_matrix) {
            for(double d: arr) {
                
                String pr = Double.toString(d);
                s.append(Double.toString(d));
                
                for(int i = pr.length(); i<LIM_LEN; i++)
                    s.append(' ');
                
            }
            s.append("\n");
        }
        
        return s.toString();
    }
    
    public double[][] getMatrix() {
        return taylor_matrix;
    }
    
    public double[] getTaylorPolynomal() {
        double[] derivatives = gaussian_elim();
        
        for(int i = 0; i<derivatives.length; i++) {
            coeffs[i] *= derivatives[i];
        }

        return coeffs;
    }

    public double[] gaussian_elim() {

        double[][] mat = new double[taylor_matrix.length][taylor_matrix.length + 1];
        solutions = new double[taylor_matrix[0].length - 1];
        refEqn = new double[taylor_matrix[0].length];
        
        System.arraycopy(taylor_matrix[0], 0, refEqn, 0, taylor_matrix[0].length);
        
        for (int i = 0; i < mat.length; i++) {
            System.arraycopy(taylor_matrix[i], 0, mat[i], 0, taylor_matrix.length + 1);
        }

        recur(mat, taylor_matrix[0].length);
        return solutions;
    }

    public  void recur(double[][] mat, int N) {
    
        if(N > 2) {
            // Get LCM and isolate
            int col = N - 2;
            double max = mat[0][col];
    
            for (int i = 0; i < N-1; i++) {
                double factor = max / mat[i][col];
    
                for (int j = 0; j < N; j++) {
                    mat[i][j] *= factor;
                }
            }
    
                // Subract
            double[][] matNew = new double[N - 2][N - 1];
    
            for (int i = 0; i < N-2; i++) {
                for (int j = 0; j < N-2; j++) {
                    matNew[i][j] = mat[i + 1][j] - mat[i][j];
                }
                
                matNew[i][N-2] = mat[i + 1][N - 1] - mat[i][N - 1];
            }
    
            recur(matNew, N - 1);
            int solved = 0;
                
            for(int i = 0; i<N-1; i++) {
                solved += mat[0][i]*solutions[i];
            }
                
            solutions[N-2] = (mat[0][mat[0].length - 1] - solved)/(mat[0][mat[0].length - 2]);
        } else {
            solutions[N-2] = mat[0][1]/mat[0][0];
        }
    }
}