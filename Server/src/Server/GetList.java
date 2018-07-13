package Server;

import action.FriendAction;

import javax.jws.soap.SOAPBinding;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

/**
 * Created by pc on 2018/4/9.
 */
public class GetList {
    private String name;
    private Socket socket;

    public GetList(String name,Socket socket) {
        this.name = name;
        this.socket = socket;
    }

    //返回列表信息给客户端
    public void ReturnList() {
        try {
            FriendAction friendAction = new FriendAction();
            List<String> friendList = friendAction.getFriendList(name);
            OutputStream output = socket.getOutputStream();
            OutputStreamWriter writer = new OutputStreamWriter(output);
            PrintWriter pw = new PrintWriter(writer,true);
            for (int i = 0; i < friendList.size(); i++) {
                pw.println(friendList.get(i));
            }
            pw.println("end");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
