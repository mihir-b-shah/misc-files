
package ch3;

import static java.lang.Math.*;

public class fourier {
    private final int N;
    private final complex_num[] out;
    private final complex_num[] in;
    private final complex_num[][] W;
    
    public fourier(complex_num[] in) {
        this.in = in;
        this.N = in.length;
        out = new complex_num[N];
        for(int i = 0; i<N; ++i)
            out[i] = new complex_num(0,0);
        W = new complex_num[N][N];
    }
    
    public complex_num[] dft_mat() {
        for(int i = 0; i<N; ++i) out[i] = new complex_num(0,0);
        constr_W(new complex_num(cos(-2*PI/N),sin(-2*PI/N)),W);
        matmult_cpx(1);
        return out;
    }
    
    // maps in onto out, NxN mat against Nx1 col vect
    private void matmult_cpx(double sc) {
        for(int i = 0; i<N; ++i) {
            for(int j = 0; j<N; ++j) 
                out[i].add_mult(W[i][j],in[j]);
            out[i].scal_mlt(sc);
        }
    }
    
    private void constr_W(complex_num base, complex_num[][] W) {
        for(int i = 0; i<N; ++i)
            for(int j = 0; j<N; ++j)
                W[i][j] = base.exp(i*j);
    }
    
     /* passing conjugate performs hermitian transpose since symmetric already.
        not working yet */
    public complex_num[] rev_dft_mat() {
        constr_W(new complex_num(cos(2*PI/N),sin(2*PI/N)),W);
        double scl = (double) 1/N;
        matmult_cpx(scl);     
        return out;
    }
    
    /*
    Algorithm: each item in {out} has two components A and θ
    1.  Multiply the original sinusoid term by A and phase shift it
    2.  The actual sinusoid term is sin(k*(x - θ)).
    */
    public double eval_fr(double x) {
        double sum = 0;
        for(int i = 0; i<N; ++i) 
            sum += out[i%N].calc_mgn()*sin(i*PI*x/N-out[i%N].calc_arg());
        return sum;
    }
    
    public complex_num[] get_out() {return out;}
}
