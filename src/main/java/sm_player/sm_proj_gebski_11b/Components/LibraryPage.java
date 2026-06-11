package sm_player.sm_proj_gebski_11b.Components;

import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.File;
import java.util.Iterator;

public class LibraryPage {

    public VBox library;
    public HBox sidebar;
    public boolean loaded=false;


    private void addFolder(String name, String path){
        Iterator<String> it=Settings.getDirectories();
        boolean founded=false;
        while (it.hasNext())
        {
            String relpath=it.next().split(": ")[1];
            if(relpath.equals(path)){
                founded=true;
                break;
            }
        }

        if(!founded){
            library.getChildren().add(new FolderView(name,path));
            Settings.addFolder(name,path);
        }
    }

    public void openFolderChooser(ActionEvent actionEvent) {
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
            System.out.println("Folder nie zostal znleziony! Anulacja zadania");
        }
    }

    public void initFolders() {
        library.getChildren().clear();
        Iterator<String> it=Settings.getDirectories();
        sidebar.setVisible(false);
        while (it.hasNext()){
            String[] values=it.next().split(": ");
            System.out.println(values[0]+" | "+values[1]);
            library.getChildren().add(new FolderView(values[0],values[1]));
        }
    }
}
