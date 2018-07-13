package FriendList;

import AddFriend.AddMain;
import Chat.ChatMain;
import SocketOfClient.ClientSocket;
import SocketOfClient.CloseSocket;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import Login.LoginController;

import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class FriendListController implements Initializable {
    @FXML
    private ListView ListOfFriend;          //好友列表视图
    @FXML
    private TextField SearchOfName;         //搜索的好友的名字
    @FXML
    private Label UserName;                 //用户名
    @FXML
    private Label DATE;                     //年月日
    @FXML
    private Label TIME;                     //时间
    @FXML
    private Button CLOSE;                   //关闭按钮
    @FXML
    private HBox TOP;

    private Timer timer;                     //控制时钟状态
    private String SelectedUser;            //当前选中的好友名
    private ObservableList<String> list ;   //好友列表信息
    private static FriendListController friendListController;
    private ClientSocket clientSocket;  //请求服务器对象

    public void initialize(URL location, ResourceBundle resources) {
        friendListController = this;

        clientSocket = ClientSocket.getClientSocket();
        UserName.setText(LoginController.getName());     //获得当前用户名
        clock();                              //显示时间
        FriendListMain.setTOP(TOP);

        //从服务端获得好友列表
        String upmsg = "@getlist#"+LoginController.getName()+"$";
        clientSocket.getPrintWriter().println(upmsg);
        list = FXCollections.observableArrayList(getFriendList());
        ListOfFriend.setItems(list);
    }

    public List<String> getFriendList() {
        List<String> friendlist = new ArrayList<String>();
        try {
            String name = clientSocket.getBufferedReader().readLine();
            while (!name.equals("end")) {
                friendlist.add(name);
                name = clientSocket.getBufferedReader().readLine();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return friendlist;
    }

    public void Search() {
        String search = SearchOfName.getText();
        for (int i=0; i<list.size(); i++) {
            if(list.get(i).equals(search)) {
                String str = list.get(0);
                list.set(0,search);
                list.set(i,str);
                ListOfFriend.setItems(list);
                break;
            }
        }
        SearchOfName.setText("");
    }

    public static FriendListController getFriendListController() {
        return friendListController;
    }

    public ListView getListOfFriend() {
        return ListOfFriend;
    }

    public String getSelectedUser() {
        return SelectedUser;
    }

    public void Add() {
        try {
            (new AddMain()).start(new Stage());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Delete() throws SQLException {
        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION,"Are you want to delete?",new ButtonType("YES", ButtonBar.ButtonData.YES),new ButtonType("NO", ButtonBar.ButtonData.NO));
        confirmation.setTitle("");
        confirmation.setHeaderText("confirm");
        confirmation.setX(600);
        confirmation.setY(300);
        Optional<ButtonType> result = confirmation.showAndWait();
        if (result.get().getButtonData().equals(ButtonBar.ButtonData.YES)){
            //执行删除
            String upmsg = "@delete#"+SelectedUser+"$"+LoginController.getName();
            clientSocket.getPrintWriter().println(upmsg);
            list = FXCollections.observableArrayList(getFriendList());
            ListOfFriend.setItems(list);
        }
    }

    public void ListOfValue() {
        SelectedUser = ListOfFriend.getSelectionModel().selectedItemProperty().getValue().toString();
    }

    public void clock() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run () {
                Platform.runLater(new Runnable() {
                @Override
                    public void run() {
                        Date date = new Date(System.currentTimeMillis());
                        SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY-MM-dd");
                        SimpleDateFormat sdf2 = new SimpleDateFormat("HH:mm:ss");
                        DATE.setText(sdf1.format(date));
                        TIME.setText(sdf2.format(date));
                    }
                });
            }
        },1000,1000);
    }

    public void Close(ActionEvent event) {
        timer.cancel();
        CloseSocket.exit();
        FriendListMain.stage.close();
    }

    public void Chat(ActionEvent event) throws Exception {
        //打开聊天界面，聊天对象为SelectedUser
        (new ChatMain()).start(new Stage());
    }
}
