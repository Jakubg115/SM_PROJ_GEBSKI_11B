package sm_player.sm_proj_gebski_11b.Components;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.File;
import java.util.Iterator;

public class LibraryPage {

    @FXML
    private MenuButton branchContextChoice;
    @FXML
    private Label branchSizeLabel;
    @FXML
    private VBox library;
    @FXML
    private HBox sidebar;

    private boolean loaded=false;


    public boolean isLoaded(){return loaded;}

    public void setLoaded(boolean flag){this.loaded=flag;}

    public void initBranchOptions(){
        boolean flag=Settings.isBranchesEmpty();
        sidebar.setVisible(!flag);
        sidebar.setDisable(flag);
        if(!flag)
        {
            String format=Settings.getBranchSize()+" wybranych";
            branchSizeLabel.setText(format);
        }

    }

    private void addFolder(String name, String path){
        Iterator<String> it=Settings.getDirectories();
        boolean founded=false;
        String relpath;
        while (it.hasNext())
        {
            relpath=it.next();
            if(relpath.isEmpty()) continue;

            String pth=relpath.split(": ")[1];

            if(pth.equals(path)){
                founded=true;
                break;
            }
        }

        if(!founded){
            library.getChildren().add(new FolderView(name,path));
            Settings.addFolder(name,path);
        }
    }

    public void openFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Wybierz folder");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        try
        {
            if (selectedDirectory != null) {
                String name= selectedDirectory.getName();
                String path= selectedDirectory.getAbsolutePath();
                addFolder(name,path);
            }
            else throw new RuntimeException();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initFolders() {
        Settings.clearBranch();
        library.getChildren().clear();
        Iterator<String> it=Settings.getDirectories();
        sidebar.setVisible(false);
        sidebar.setDisable(true);
        String[] values;
        while (it.hasNext()){
            String value=it.next();
            if(!value.isEmpty()){values=value.split(": ");}
            else continue;
            library.getChildren().add(new FolderView(values[0],values[1]));
        }
    }

    public void createAndInsertToNewAlbum() {
        new AlbumDialogPane(Settings.albumpage,2);
    }

    public void clearBranch() {
        Settings.clearBranch();
    }

    public void addToQueue() {
        Settings.appendQueue();
    }

    private void clearAlbumsList(){
        int size=branchContextChoice.getItems().size();
        if(size>=3) branchContextChoice.getItems().remove(2,size);
    }


    public void loadAlbums() {

        clearAlbumsList();
        File albumSearcher=new File(Settings.getAlbumDirectory());

        String[] founded=albumSearcher.list();
        for(String album:founded){
            MenuItem item=new MenuItem(album);
            item.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Settings.addFilesToAlbum(item.getText(),Settings.getFileBranch());
                }
            });
            branchContextChoice.getItems().add(item);
        }
        MenuItem addAlbum=new MenuItem("Dodaj album +");
        addAlbum.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                createAndInsertToNewAlbum();
            }
        });
        branchContextChoice.getItems().add(addAlbum);

    }

    public void initThisBranch() {
        Settings.initBranch();
    }

    public void deleteFolder(int index){
        System.out.println(library.getChildren().size()+" | "+index);
        FolderView view = (FolderView) library.getChildren().get(index);
        view.prepareForDelete();
        library.getChildren().remove(index);
        if(!Settings.isQueueEmpty()){
            Settings.openMediaPlayerScene(0);
        }
        else {
            if(Settings.mediaPlayerStage !=null) Settings.getMediaController().closePlayer();
        }
    }
}
