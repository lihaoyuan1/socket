package Server;

/**
 * Created by pc on 2018/4/9.
 */
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pc on 2018/3/29.
 */
public class SocketGather {
    private Socket socket;
    private String label;
    private static List<SocketGather> list;

    public SocketGather() {
        if (list == null)
            list = new ArrayList<SocketGather>();
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public static void Add(SocketGather socketGather) {
        list.add(socketGather);
    }

    public static List<SocketGather> getList() {
        return list;
    }
}

