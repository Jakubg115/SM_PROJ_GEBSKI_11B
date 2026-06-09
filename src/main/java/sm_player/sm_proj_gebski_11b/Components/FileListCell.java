package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.Controllers.FileListCellController;

public class FileListCell extends AnchorPane {

    private FileListCellController pointer;

    public FileListCell(String name){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/FileListCellComponent.fxml"));

        try {
            loader.setRoot(this);
            loader.load();
            this.pointer=loader.getController();
            this.pointer.set(name);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
