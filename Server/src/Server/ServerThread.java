package Server;

/**
 * Created by pc on 2018/4/9.
 */
import action.FriendAction;
import action.LandAction;
import action.UserAction;
import model.User;

import java.io.*;
import java.net.Socket;

/**
 * Created by pc on 2018/3/29.
 */
public class ServerThread extends Thread {
    private Socket socket1 = null;
    private Socket socket2 = null;
    public ServerThread(Socket socket) {
        socket1 = socket;
    }
    public void run() {
        String line;
        String msgstream;
        String label1;              //发送消息方
        String label2;              //接收消息方
        String message;             //消息
        boolean flag = true;
        try {
            InputStream inputStream  = socket1.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            OutputStream outputStream;
            OutputStreamWriter outputStreamWriter;
            PrintWriter printWriter = null;

            while(flag) {
                try {
                    //服务器接收字符流
                    msgstream = bufferedReader.readLine();

                    //解析接受到的字符流
                    label1 = msgstream.substring(msgstream.indexOf('@') + 1, msgstream.indexOf('#'));
                    label2 = msgstream.substring(msgstream.indexOf('#') + 1, msgstream.indexOf('$'));

                    if (label1.equals("login")){
                        //验证登录
                        String password = msgstream.substring(msgstream.indexOf('$')+1);
                        LandAction landAction=new LandAction();
                        String msg = landAction.UserLand(label2,password);
                        if (msg.equals("")) {
                            //登录验证通过，保存socket对象
                            SocketGather gather = new SocketGather();
                            gather.setSocket(socket1);
                            gather.setLabel(label2);
                            SocketGather.Add(gather);
                        }
                        //返回信息
                        OutputStream output = socket1.getOutputStream();
                        OutputStreamWriter writer = new OutputStreamWriter(output);
                        PrintWriter pw = new PrintWriter(writer,true);
                        pw.println(msg);
                    }
                    else if (label1.equals("register")){
                        String password = msgstream.substring(msgstream.indexOf('$')+1);
                        UserAction userAction=new UserAction();
                        User user=new User();
                        user.setUser_name(label2);
                        user.setPassword(password);
                        OutputStream output = socket1.getOutputStream();
                        OutputStreamWriter writer = new OutputStreamWriter(output);
                        PrintWriter pw = new PrintWriter(writer,true);
                        if (userAction.Add_User(user)) {
                            pw.println("");
                        }
                        else{
                            pw.println("用户名重复");
                        }
                    }
                    else if (label1.equals("getlist")) {
                        //调用GetList类，向客户端返回好友列表数据
                        GetList getList = new GetList(label2,socket1);
                        getList.ReturnList();
                    }
                    else if (label1.equals("delete")) {
                        message = msgstream.substring((msgstream.indexOf('$') + 1));
                        FriendAction friendAction = new FriendAction();
                        friendAction.Delete(message,label2);
                        GetList getList = new GetList(message,socket1);
                        getList.ReturnList();
                    }
                    else if (label1.equals("add")) {
                        String username = msgstream.substring(msgstream.indexOf('$') + 1, msgstream.indexOf('%'));
                        String nickname = msgstream.substring(msgstream.indexOf('%') + 1);

                        FriendAction friendAction=new FriendAction();
                        friendAction.Add(label2, username,nickname);
                        GetList getList = new GetList(label2,socket1);
                        getList.ReturnList();
                    }
                    else if (label1.equals("exit")) {
                        //关闭socket
                        socket1.close();
                        if (!label2.equals("")){
                            //删除List中保存的socket
                            SocketGather.getList().remove(label2);
                        }
                        flag = false;
                    }
                    else {
                        message = msgstream.substring((msgstream.indexOf('$') + 1));
                        //找到接收消息的socket2对象
                        if (socket2 == null) {
                            for (int i = 0; i < SocketGather.getList().size(); i++)
                                if (SocketGather.getList().get(i).getLabel().equals(label2)) {
                                    socket2 = SocketGather.getList().get(i).getSocket();
                                    break;
                                }
                            outputStream = socket2.getOutputStream();
                            outputStreamWriter = new OutputStreamWriter(outputStream);
                            printWriter = new PrintWriter(outputStreamWriter,true);
                        }
                        printWriter.println(message);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                socket1.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

