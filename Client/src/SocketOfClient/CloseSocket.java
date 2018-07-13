package SocketOfClient;

import Login.LoginController;

/**
 * Created by pc on 2018/4/9.
 */
public class CloseSocket {
    public static void exit() {
        String upmsg = "@exit#"+ LoginController.getName()+"$";
        ClientSocket.getClientSocket().getPrintWriter().println(upmsg);
        try {
            //关闭socket
            ClientSocket.getClientSocket().getSocket().close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
