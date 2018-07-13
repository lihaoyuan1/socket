package dragListener;

import javafx.scene.Node;
import javafx.stage.Stage;

/**
 * Created by pc on 2018/4/8.
 */
public class DragUtil {
    public static void addDragListener(Stage stage, Node root) {
        new DragListener(stage).enableDrag(root);
    }
}
