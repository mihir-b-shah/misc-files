
import java.io.FileInputStream;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Paint;

public class GUI extends Application {

    private Stage stage;
    
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        
        BorderPane bp = new BorderPane();
        Scene sc = new Scene(bp, 1024, 768);
        
        VBox lev1;
        bp.getChildren().add(lev1 = new VBox());
        Image im;
        ImageView iv = new ImageView(im = new Image(
                new FileInputStream("Loc8r_Logo_Black.png")));

        double x,y,rx,ry,coeff;
        iv.setFitWidth(900);
        iv.setFitHeight(150);
        rx = iv.getFitWidth()/im.getWidth();
        ry = iv.getFitHeight()/im.getHeight();
        coeff = rx >= ry ? ry : rx;

        iv.setX((iv.getFitWidth()-im.getWidth()*coeff)/2);
        iv.setY((iv.getFitHeight()-im.getHeight()*coeff)/2);
        iv.setPreserveRatio(false);
        
        lev1.getChildren().add(iv);
        HBox lev2;
        lev1.getChildren().add(lev2 = new HBox());
        
        VBox left;
        lev2.getChildren().add(left = new VBox());
        HBox topleft;
        left.getChildren().add(topleft = new HBox());
        
        TextField latitude,longitude;
        Button submitRating = new Button();
        Label space;
        
        topleft.getChildren().add(latitude = new TextField());
        topleft.getChildren().add(space = new Label("\t\t\t\t\t"));
        space.setMinWidth(50);
        topleft.getChildren().add(longitude = new TextField());
        topleft.getChildren().add(submitRating);
        submitRating.setText("Submit ratings");
        Border border = new Border(new BorderStroke(Paint.valueOf("black"), 
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT));
        latitude.setBorder(border);
        longitude.setBorder(border);
        latitude.setText("Latitude");
        longitude.setText("Longitude");
        latitude.setMinWidth(250);
        longitude.setMinWidth(250);
        
        
        
        stage.setScene(sc);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }   
}