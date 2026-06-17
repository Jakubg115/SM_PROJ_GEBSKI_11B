package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import sm_player.sm_proj_gebski_11b.Controllers.AlbumComponentController;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;

public class AlbumComponent extends VBox {
    private Image cover;
    private LinkedList<FileListCell> files=new LinkedList<>();

    private LinkedList<String> toRead=new LinkedList<>();
    private AlbumComponentController controller=null;

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



    public AlbumComponent(int index){
        String name=Settings.readAlbumFolder().get(index);
        this.controller=recieveController();
        this.controller.SetName(name);
        this.controller.setPointer(this);
        setCoverImage(String.valueOf(getClass().getResource("/No_Cover.jpg")));

        File folder=new File(Settings.getAlbumDirectory()+"\\"+getAlbumName());
        this.toRead.addAll(Arrays.asList(Objects.requireNonNull(folder.list())));

    }

    public AlbumComponent(String name){
        if(ifNotExists(name)){
            this.controller=recieveController();
            this.controller.SetName(name);
            this.controller.setPointer(this);
            setCoverImage(String.valueOf(getClass().getResource("/No_Cover.jpg")));
            setToFolder(name);
        }
    }


    public void copyFilesToAlbum(LinkedList<FileListCell> files){
        String albumPath=Settings.getAlbumDirectory()+"\\"+getAlbumName();
        for(FileListCell cell: files){
            String name=cell.getFileName();
            try {

                Files.copy(Paths.get(
                        cell.getFilepath()),
                        Paths.get(albumPath+"\\"+name),
                        StandardCopyOption.REPLACE_EXISTING);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    public String getAlbumName(){return this.controller.getName();}

    public AlbumComponent(String name, LinkedList<FileListCell> list){
        if(ifNotExists(name)){
            this.files=list;
            this.controller=recieveController();
            this.controller.SetName(name);
            this.controller.setPointer(this);
            setCoverImage(String.valueOf(getClass().getResource("/No_Cover.jpg")));
            setToFolder(name);
            copyFilesToAlbum(this.files);
            for (FileListCell cell: this.files){
                toRead.add(cell.getFilepath());
            }
        }
    }

    private boolean ifNotExists(String name){
        return !Settings.readAlbumFolder().contains(name);
    }

    private void setToFolder(String name){
        String absolutePath= Settings.getAlbumDirectory()+"\\"+name;
        File folder=new File(absolutePath);
        try {
            folder.mkdir();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void changeFolder(String oldname, String newname){
        String oldpath= Settings.getAlbumDirectory()+"\\"+oldname;
        String newpath= Settings.getAlbumDirectory()+"\\"+newname;

        try {
            Files.move(Paths.get(oldpath),Paths.get(newpath), StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        Settings.librarypage.loadAlbums();
    }
    private void removeFolder(String name){
        String absolutePath= Settings.getAlbumDirectory()+"\\"+name;
        File folder=new File(absolutePath);
        if(folder.exists()){
            folder.delete();
        }
    }

    private void deleteInsidesFirst(){
        String path=Settings.getAlbumDirectory()+"\\"+this.controller.getName();
        File toDelete=new File(path);
        for(File f: Objects.requireNonNull(toDelete.listFiles())){
            f.delete();
        }
    }

    public void prepareToDeleteAlbum(){
        deleteInsidesFirst();
        String name=this.controller.getName();
        Settings.deleteAlbum(name);
        removeFolder(name);
        Settings.librarypage.loadAlbums();
    }
}
