package Register;

import SocketOfClient.ClientSocket;
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
import Login.LoginMain;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Created by pc on 2018/4/3.
 */
public class RegisterController implements Initializable {
    @FXML
    private TextField UserName;
    @FXML
    private PasswordField PassWord;
    @FXML
    private PasswordField Confirm;
    @FXML
    private Label Message;
    @FXML
    private Button CLOSE;
    @FXML
    private HBox TOP;

    private static String name = "";
    private ClientSocket clientSocket;  //请求服务器对象

    public void initialize(URL location, ResourceBundle resources) {
        RegisterMain.setTOP(TOP);
        clientSocket = ClientSocket.getClientSocket();
    }

    public static String getName(){
        return name;
    }

    public void Submit() throws SQLException {
        if (PassWord.getText().equals(Confirm.getText())) {
            //提交注册信息，后台返回flag
            String upmsg = null;
            String message = null;
            try {
                upmsg = "@register#"+UserName.getText()+"$"+PassWord.getText();
                clientSocket.getPrintWriter().println(upmsg);

                //获得服务器返回数据
                message = clientSocket.getBufferedReader().readLine();
            }catch (Exception e){
                e.printStackTrace();
            }
            if (message.equals("")) {
                try {
                    name = UserName.getText();
                    RegisterMain.stage.close();
                    (new LoginMain()).start(new Stage());
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            else{
                Message.setText(message);
            }
        }else {
            Message.setText("两次输入密码不一致");
        }
    }

    public void Close(ActionEvent event) {
        CloseSocket.exit();
        RegisterMain.stage.close();
    }
}
