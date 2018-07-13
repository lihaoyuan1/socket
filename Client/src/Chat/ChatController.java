package Chat;

import FriendList.FriendListController;
import Login.LoginController;
import SocketOfClient.ClientSocket;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by pc on 2018/4/9.
 */
public class ChatController implements Initializable {
    @FXML
    private HBox TOP;
    @FXML
    private Label NAME;
    @FXML
    private ListView MsgArea;
    @FXML
    private TextArea EditArea;

    private ObservableList<GridPane> list;
    private ClientSocket clientSocket;  //请求服务器对象
    private static ChatController chatController;
    ReceiveThread receiceThread;

    public void initialize(URL location, ResourceBundle resources) {
        NAME.setText(FriendListController.getFriendListController().getSelectedUser());
        chatController = this;

        clientSocket = ClientSocket.getClientSocket();

        //启动接收消息线程
        list = FXCollections.observableArrayList();
        receiceThread = new ReceiveThread();
        receiceThread.start();

        ChatMain.setTOP(TOP);
    }

    public static ChatController getChatController() {
        return chatController;
    }

    public ObservableList<GridPane> getList() {
        return list;
    }

    public ListView getMsgArea() {
        return MsgArea;
    }

    public void Close(ActionEvent event) {
        receiceThread.setFlag();
        ChatMain.stage.close();
    }

    public void Send(ActionEvent event){
        String msg = EditArea.getText();
        String message = "@"+ LoginController.getName()+"#"+NAME.getText()+"$"+msg;
        EditArea.setText("");
        clientSocket.getPrintWriter().println(message);
        GridPane gridPane = new GridPane();
        gridPane.setVgap(0);
        gridPane.setHgap(1);
        ColumnConstraints column = new ColumnConstraints(530);
        gridPane.getColumnConstraints().add(column);

        ImageView imageView = new ImageView("/picture/people.png");
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        gridPane.setHalignment(imageView, HPos.RIGHT);
        gridPane.add(imageView,1,0);

        Label label = new Label(msg);
        gridPane.setHalignment(label, HPos.RIGHT);
        gridPane.add(label,0,0);

        list.add(gridPane);
        MsgArea.setItems(list);
    }
}
