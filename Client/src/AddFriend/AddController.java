package AddFriend;/**
 * Created by pc on 2018/4/2.
 */

import SocketOfClient.ClientSocket;
import FriendList.FriendListController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import Login.LoginController;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddController implements Initializable {
    @FXML
    private TextField UserName;
    @FXML
    private TextField NickName;
    @FXML
    private Button CLOSE;
    @FXML
    private HBox TOP;

    private ClientSocket clientSocket;  //请求服务器对象

    public void initialize(URL location, ResourceBundle resources) {
        clientSocket = ClientSocket.getClientSocket();
        AddMain.setTOP(TOP);
    }

    public void Add(ActionEvent event) throws SQLException {

        //提交所要添加的好友的信息，后台返回list
        String upmsg = "@add#"+LoginController.getName()+"$"+UserName.getText()+"%"+NickName.getText();
        clientSocket.getPrintWriter().println(upmsg);

        ObservableList<String> list = FXCollections.observableArrayList(FriendListController.getFriendListController().getFriendList());
        FriendListController.getFriendListController().getListOfFriend().setItems(list);
        AddMain.stage.close();
    }

    public void Close(ActionEvent event) {
        AddMain.stage.close();
    }
}
