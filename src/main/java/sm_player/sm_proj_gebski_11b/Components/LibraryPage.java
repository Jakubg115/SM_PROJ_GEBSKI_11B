package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.util.Iterator;

public class LibraryPage extends AnchorPane {

    public VBox library;

    @FXML
    public void initialize() {
        Iterator<String> it=Settings.getDirectories();
        while (it.hasNext()){
            library.getChildren().add(new FolderView(it.next()));
        }
    }


}
