package Register;

import dragListener.DragUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created by pc on 2018/4/7.
 */
public class RegisterMain extends Application {
    public static Stage stage;
    private static HBox TOP;

    public static void setTOP(HBox top) {
        TOP = top;
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = primaryStage;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root,350,350));
        primaryStage.getIcons().add(new Image("/picture/wechat.png"));
        DragUtil.addDragListener(stage, TOP);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
