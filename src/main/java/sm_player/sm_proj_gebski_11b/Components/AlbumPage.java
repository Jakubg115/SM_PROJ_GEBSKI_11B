package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.FlowPane;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;
import java.util.LinkedList;

public class AlbumPage {

    @FXML
    private FlowPane albumList;

    private boolean loaded=false;

    public boolean isLoaded(){return loaded;}

    public void setLoaded(boolean flag){this.loaded=flag;}

    public void onAddClick(){
        new AlbumDialogPane(this,1);
    }

    public void addNewAlbum(String name) {
        albumList.getChildren().add(new AlbumComponent(name));
        Settings.librarypage.loadAlbums();
    }

    public void addNewAlbum(String name, LinkedList<FileListCell> list){
        albumList.getChildren().add(new AlbumComponent(name,list));
        Settings.librarypage.loadAlbums();
    }

    public AlbumComponent getComponent(String name){
        for(Node component: albumList.getChildren()){
            if(component instanceof AlbumComponent){
                if(((AlbumComponent) component).getAlbumName().equals(name)) return (AlbumComponent) component;
            }
        }
        return null;
    }

    public void initAlbums(){
        albumList.getChildren().clear();
        for(int i=0; i<Settings.readAlbumFolder().size(); i++)
        {
            albumList.getChildren().add(new AlbumComponent(i));
        }
        Settings.librarypage.loadAlbums();
    }

    public void removeAlbum(int index){
        albumList.getChildren().remove(index);

    }
}
