package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import sm_player.sm_proj_gebski_11b.Controllers.AlbumComponentController;

import java.util.LinkedList;

public class AlbumComponent extends VBox {
    private String albumName;
    private Image cover;
    private LinkedList<String> files=new LinkedList<>();
    private final AlbumComponentController controller;

    private AlbumComponentController recieveController(){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/AlbumComponent.fxml"));

        try {
            loader.setRoot(this);
            loader.load();
            return loader.getController();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void setCoverImage(String image){
        this.cover=new Image(image);
        this.controller.setAlbumCover(this.cover);
    }

    public AlbumComponent(String name){
        this.albumName=name;
        this.controller=recieveController();
        this.controller.SetName(albumName);
        setCoverImage(String.valueOf(getClass().getResource("/No_Cover.jpg")));
    }

    public AlbumComponent(String name, LinkedList<String> list){
        this.albumName=name;
        this.files=list;
        this.controller=recieveController();
        this.controller.SetName(albumName);
        setCoverImage(String.valueOf(getClass().getResource("/No_Cover.jpg")));
    }

}
