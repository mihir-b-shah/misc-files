
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class gui extends Application {
    
    private Stage stage;
    private static final data dat;
    
    static {
        dat = new data(10);
    }
    
    public static void main(String[] args) {
        System.out.printf("Mean squared error: %f%n", 
                dat.mean_sq_error(dat::gen_rating_opt1));
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        BorderPane bp = new BorderPane();
        VBox layout = new VBox();
        Text prompt = new Text("Enter your review: \n");
        prompt.setTextAlignment(TextAlignment.CENTER);
        layout.getChildren().add(prompt);
        TextArea tf = new TextArea();
        layout.getChildren().add(tf);
        Button button = new Button("Submit");
        button.setAlignment(Pos.CENTER);
        layout.getChildren().add(button);
        Text result = new Text("Rating: ");
        result.setTextAlignment(TextAlignment.CENTER);
        layout.getChildren().add(result);
                
        bp.setCenter(layout);
        
        Scene sc = new Scene(bp, 600, 400);
        stage.setScene(sc);
        stage.show();
        
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            String res = tf.getText().toLowerCase().
                    replaceAll("[^ A-Za-z]+", "").replaceAll("\\s+"," ");
            result.setText("Rating: " + String.format("%.2f", 
                    dat.gen_rating_opt1(res)));
        });
    }
}
