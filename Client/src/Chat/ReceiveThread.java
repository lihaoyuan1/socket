package Chat;

import SocketOfClient.ClientSocket;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Created by pc on 2018/4/10.
 */
public class ReceiveThread extends Thread {
    private ChatController chatController = ChatController.getChatController();
    private boolean flag = true;

    public void setFlag() {
        flag = false;
    }

    public void run() {
        try {
            while (flag) {
                String message = ClientSocket.getClientSocket().getBufferedReader().readLine();
                GridPane gridPane = new GridPane();
                gridPane.setHgap(1);
                gridPane.setVgap(0);

                ImageView imageView = new ImageView("/picture/people.png");
                imageView.setFitWidth(30);
                imageView.setFitHeight(30);
                gridPane.setHalignment(imageView, HPos.LEFT);
                gridPane.add(imageView, 0, 0);

                Label label = new Label(message);
                gridPane.setHalignment(label, HPos.LEFT);
                gridPane.add(label, 1, 0);

                chatController.getList().add(gridPane);
                chatController.getMsgArea().setItems(chatController.getList());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
