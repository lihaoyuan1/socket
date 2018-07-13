package Server;

/**
 * Created by pc on 2018/4/9.
 */
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static int count = 0;
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket;
            System.out.println("服务器已启动");
            while (true) {
                socket = serverSocket.accept();
                count++;
                System.out.println("用户连接，服务器连接次数："+count);
                ServerThread serverThread = new ServerThread(socket);
                serverThread.start();
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}