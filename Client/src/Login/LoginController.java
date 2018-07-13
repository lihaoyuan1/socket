package Login;

import SocketOfClient.ClientSocket;
import FriendList.FriendListMain;
import SocketOfClient.CloseSocket;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Register.RegisterController;
import Register.RegisterMain;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;


/**
 * Created by pc on 2018/4/3.
 */
public class LoginController implements Initializable {
    @FXML
    private TextField UserName;
    @FXML
    private PasswordField PassWord;
    @FXML
    private Label Message;
    @FXML
    private Button CLOSE;
    @FXML
    private HBox TOP;

    private static String name;         //当前用户名
    private ClientSocket clientSocket;  //请求服务器对象

    public void initialize(URL location, ResourceBundle resources) {
        name = "";

        //获得注册界面的用户名,未进行注册操作则返回空
        UserName.setText(RegisterController.getName());

        //获得连接服务器的socket
        ClientSocket clientSocket1 = new ClientSocket();
        clientSocket = ClientSocket.getClientSocket();

        //设值窗口课拖动
        LoginMain.setTop(TOP);
    }

    public static String getName(){
        return name;
    }

    public void button(ActionEvent event) throws SQLException {
        if (!UserName.getText().equals("")&&!PassWord.getText().equals("")) {
            String upmsg = null;
            String message = null;
            try {
                //上传用户名和密码信息，服务器端验证
                upmsg = "@login#" + UserName.getText() + "$" + PassWord.getText();
                clientSocket.getPrintWriter().println(upmsg);

                //获得服务器返回数据
                message = clientSocket.getBufferedReader().readLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (message.equals("")) {
                try {
                    name = UserName.getText();
                    LoginMain.stage.close();
                    (new FriendListMain()).start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Message.setText(message);
            }
        }
    }

    public void register() {
        try {
            LoginMain.stage.close();
            (new RegisterMain()).start(new Stage());
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Close(ActionEvent event) {
        CloseSocket.exit();
        LoginMain.stage.close();
    }
}
