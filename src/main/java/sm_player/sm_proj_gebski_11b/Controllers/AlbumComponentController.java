package sm_player.sm_proj_gebski_11b.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.Components.AlbumComponent;

import java.io.File;

public class AlbumComponentController {
    @FXML
    private MenuButton albumOptions;
    @FXML
    private FontAwesomeIconView albumPlayButton;
    @FXML
    private CheckBox albumCheckBox;
    private AlbumComponent pointer;

    @FXML
    private ImageView albumCover;
    @FXML
    private Label albumName;
    @FXML
    private VBox albumPanel;

    public void SetName(String name){this.albumName.setText(name);}

    public void setAlbumCover(Image cover){
        this.albumCover.setImage(cover);
        this.albumCover.setFitWidth(100.0);
        this.albumCover.setFitHeight(100.0);
    }

    public String getName(){return this.albumName.getText();}

    public Image getImage(){return this.albumCover.getImage();}

    public void setPointer(AlbumComponent pointer){this.pointer=pointer;}

    @FXML
    private void onClicked(MouseEvent e) {
        if(e.getClickCount()==2){
            this.pointer.showViewOfThisAlbum();
        }
    }

    @FXML
    private void playThisAlbum() {
        this.pointer.initThisAlbum();
    }


    @FXML
    private void changeAlbumName() {
        TextField area=new TextField();
        albumPanel.getChildren().set(1,area);
        area.setText(albumName.getText());
        area.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String oldname=albumName.getText();
                albumName.setText(area.getText());
                albumPanel.getChildren().set(1,albumName);
                pointer.changeFolder(oldname,albumName.getText());
            }
        });
    }

    @FXML
    private void setCover() {
        FileChooser chooser=new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files","*.png","*.jpg","*.gif"));
        File newCover= chooser.showOpenDialog(new Stage());
        try {
            if(newCover !=null)
            {
                this.pointer.setCoverImage(newCover.toURI().toURL().toExternalForm());
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void deleteAlbum(){
        this.pointer.prepareToDeleteAlbum();
    }

    @FXML
    private void onMarked() {
    }
}
