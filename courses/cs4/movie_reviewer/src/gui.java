
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class gui extends Application {
    
    private Stage stage;
    private static data dat;
    
    public static void main(String[] args) {
        dat = new data();
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        BorderPane bp = new BorderPane();
        VBox layout = new VBox();
        Text prompt = new Text("Enter your review: \n");
        layout.getChildren().add(prompt);
        TextField tf = new TextField();
        layout.getChildren().add(tf);
        Button button = new Button("Submit");
        layout.getChildren().add(button);
        Text result = new Text("Rating: ");
        layout.getChildren().add(result);
                
        bp.setCenter(layout);
        
        Scene sc = new Scene(bp, 600, 400);
        stage.setScene(sc);
        stage.show();
        
        button.addEventHandler(MouseEvent.MOUSE_CLICKED, e->{
            result.setText(result.getText()+
                    String.format("%.2f", dat.gen_rating(tf.getText())));
        });
    }
}
