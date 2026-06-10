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

    public FolderView(String FolderPath){
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/FolderView.fxml"));
        loader.setRoot(this);
        String[] values=FolderPath.split(": ");
        this.folder = new File(values[1]);

        try {
            if (this.folder.exists() && this.folder.isDirectory()) {
                List<String> list = Arrays.stream(Objects.requireNonNull(folder.list((_, name) -> name.endsWith(".mp3") || name.endsWith(".m4a") || name.endsWith(".wav")))).toList();
                loader.load();
                FolderViewController controller=loader.getController();
                controller.folderName.setText(values[0]);
                controller.cloneView(this);

                for (String s : list) {
                    controller.addFile(s);
                }

            } else throw new FileNotFoundException("Nieprawidlowa sciezka: "+values[1]+"\n");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public void manageSelected(FileListCell file){
        String path=this.folder.getAbsolutePath()+"\\"+file.getFileName();
        int index=Settings.addFileToQueue(path);
        Settings.openMediaPlayerScene(index<=-1?0:index);
    }
}
