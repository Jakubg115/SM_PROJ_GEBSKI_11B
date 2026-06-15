package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.Controllers.FolderViewController;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class FolderView extends AnchorPane {
    private final File folder;

    private FolderViewController pointer;

    public FolderView(String Foldername, String FolderPath){
        this.folder = new File(FolderPath);

        try {
            if (this.folder.exists() && this.folder.isDirectory()) {
                FXMLLoader loader =new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/FolderView.fxml"));
                loader.setRoot(this);
                List<String> list = Arrays.stream(Objects.requireNonNull(folder.list((_, name) -> name.endsWith(".mp3") || name.endsWith(".m4a") || name.endsWith(".wav")))).toList();
                loader.load();
                pointer=loader.getController();
                pointer.setFileName(Foldername);
                pointer.cloneView(this);

                for (String s : list) {
                    pointer.addFile(s, getFolderPath());
                }

            } else throw new FileNotFoundException("Nieprawidlowa sciezka: "+FolderPath+"\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getFolderPath(){return  folder.getAbsolutePath();}

    public void addFileToBranch(FileListCell cell){
        Settings.addFileToBranch(cell);
    }

    public void deleteFileFromBranch(FileListCell cell){
        Settings.deleteFileFromBranch(cell);
    }

    public void manageSelected(FileListCell file){
        String path=this.folder.getAbsolutePath()+"\\"+file.getFileName();
        int index=Settings.addFileToQueue(path);
        Settings.queuepage.updateQueueView();
        Settings.openMediaPlayerScene(index<=-1?0:index);
    }

    public void prepareForDelete(){
        List<FileListCell> cells=pointer.getFiles();
        for(FileListCell item: cells){
            item.checkCell(false);
            item.manageThisFile(false);
        }
    }
}
