package sm_player.sm_proj_gebski_11b.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import sm_player.sm_proj_gebski_11b.Components.AlbumComponent;

public class AlbumComponentController {
    public MenuButton albumOptions;
    public FontAwesomeIconView albumPlayButton;
    public CheckBox albumCheckBox;
    private AlbumComponent pointer;

    @FXML
    private ImageView albumCover;
    @FXML
    private Label albumName;
    @FXML
    private VBox albumPanel;

    public void SetName(String name){this.albumName.setText(name);}

    public void setAlbumCover(Image cover){this.albumCover.setImage(cover);}

    @FXML
    private void onClicked(MouseEvent event) {
    }

    @FXML
    private void changeAlbumName() {
    }

    @FXML
    private void setCover() {
    }

    @FXML
    private void deleteAlbum(){

    }

    @FXML
    private void playThisAlbum() {
    }

    @FXML
    private void onMarked() {
    }
}
