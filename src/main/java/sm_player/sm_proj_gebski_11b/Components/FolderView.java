package sm_player.sm_proj_gebski_11b.Components;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.Controllers.FolderViewController;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class FolderView extends AnchorPane {


    public FolderView(String FolderPath){
        FXMLLoader loader =new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/FolderView.fxml"));
        loader.setRoot(this);
        String[] values=FolderPath.split(": ");
        File folder = new File(values[1]);


        try {
            if (folder.exists() && folder.isDirectory()) {
                List<String> list = Arrays.stream(Objects.requireNonNull(folder.list((dir, name) -> name.endsWith(".mp3") || name.endsWith(".m4a") || name.endsWith(".wav")))).collect(Collectors.toList());
                loader.load();
                FolderViewController controller=loader.getController();
                controller.folderName.setText(values[0]);

                Iterator<String> it=list.iterator();

                while (it.hasNext()){
                    controller.addFile(it.next());
                }

            } else throw new FileNotFoundException("Nieprawidlowa sciezka: "+values[1]+"\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
