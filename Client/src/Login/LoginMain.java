package Login;

import dragListener.DragUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LoginMain extends Application {
    public static Stage stage;
    private static HBox TOP;

    public static void setTop(HBox top) {
        TOP = top;
    }

    public void start(Stage primaryStage) throws Exception{
        //Login
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        stage = primaryStage;
        primaryStage.initStyle(StageStyle.TRANSPARENT);
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 350, 350));
        primaryStage.getIcons().add(new Image("/picture/wechat.png"));
        DragUtil.addDragListener(stage, TOP);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
