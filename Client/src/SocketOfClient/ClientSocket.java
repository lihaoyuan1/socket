package SocketOfClient;

import java.io.*;
import java.net.Socket;

/**
 * Created by pc on 2018/4/9.
 */
public class ClientSocket {
    private Socket socket;      //连接服务器对象

    //发送信息所用对象
    private OutputStream outputStream;
    private OutputStreamWriter outputStreamWriter;
    private PrintWriter printWriter;

    //接收信息所用对象
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader bufferedReader;

    //把当前对象设为静态
    private static ClientSocket clientSocket;

    public ClientSocket() {

        clientSocket = this;

        //初始化发送、接收信息对象
        try {
            socket = new Socket("118.126.88.69",8888);

            outputStream = socket.getOutputStream();
            outputStreamWriter = new OutputStreamWriter(outputStream);
            printWriter = new PrintWriter(outputStreamWriter,true);

            inputStream = socket.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public PrintWriter getPrintWriter() {
        return printWriter;
    }

    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }

    public Socket getSocket() {
        return socket;
    }

    public static ClientSocket getClientSocket() {
        return clientSocket;
    }
}
