package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.util.LinkedList;

public class AlbumViewSubPage {

    @FXML
    private AnchorPane albumViewPanel;
    @FXML
    private Label albumName;
    @FXML
    private ListView<String> albumFileList;
    @FXML
    private ImageView albumCover;

    public void setAlbumName(String name){this.albumName.setText(name);}

    public void setCover(Image cover){this.albumCover.setImage(cover);}

    public void setList(LinkedList<String> files){
        albumFileList.getItems().clear();
        albumFileList.getItems().addAll(files);
    }

    @FXML
    private void onClick(){

    }

    @FXML
    private void backToList(){
        Settings.closeSubPage();
    }

}
