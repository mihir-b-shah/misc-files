
package ch3;

import java.util.ArrayList;
import java.util.function.Function;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class ch3_2_test extends Application {
    
    private static complex_num[] data;
    private static boolean flg = false; // indicate use cpx nums
    private static double[] data_pr;
    private static final int N = 63;
    
    // comment this out to test it from main file
    public static void main(String[] args) {test();}
    
    private static void test() {
        
        // test ch 3.2

        Function<Double,Double> fx = x->1+Math.sin(x);
        ArrayList<complex_num> vect = new ArrayList<>();
        for(int i = 0; i<N; i++) {
            vect.add(new complex_num(fx.apply(0.1*i),0));
        }
        complex_num[] cpx = new complex_num[vect.size()];
        vect.toArray(cpx);
        data = cpx;
        
        fourier fr = new fourier(cpx);
        fr.dft_mat();
        //fr.rev_dft_mat(); INVERSE DOES NOT WORK YET
        complex_num[] freq = fr.get_out();        
        data = freq;
        
        for(int i = 0; i<N; ++i)
            data[i].scal_mlt((double) 1/N);
        
        double[] output = new double[N];
        
        for(int i = 0; i<N; ++i)
            output[i] = fr.eval_fr(i);
        
        data_pr = output; 
        flg = true;
        
        Application.launch();   
    }

    @Override
    public void start(Stage st) throws Exception {
                
        final NumberAxis x_axis = new NumberAxis();
        final NumberAxis y_axis = new NumberAxis();

        x_axis.setAutoRanging(false);
        x_axis.setLowerBound(0);
        x_axis.setUpperBound(N);
        x_axis.setTickUnit(1);
        
        final ScatterChart<Integer,Double> gph = 
                new ScatterChart(x_axis,y_axis);

        XYChart.Series<Integer,Double> srs = new XYChart.Series();
        int size = flg ? data_pr.length : data.length;
        for(int i = 0; i<size; ++i) 
            if(!flg)
                srs.getData().add(new XYChart.Data(i, data[i].calc_mgn()));
            else
                srs.getData().add(new XYChart.Data(i, data_pr[i]));
        Scene sc = new Scene(gph, 1080,540);
        gph.getData().add(srs);
        st.setScene(sc);
        st.show();
    }
}

