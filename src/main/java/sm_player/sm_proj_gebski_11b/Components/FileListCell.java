package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.Controllers.FileListCellController;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

public class FileListCell extends AnchorPane {

    private final FileListCellController pointer;

    public FileListCell(String name){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/FileListCellComponent.fxml"));

        try {
            loader.setRoot(this);
            loader.load();
            this.pointer=loader.getController();
            this.pointer.set(name);
            this.pointer.obtainComponent(this);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getFileName(){return this.pointer.getFileName();}

    public void manageQueueOption(boolean state){
        switch (state){
            case true:
                Settings.fileBranch.add(this);
                break;
                case false:

                    break;
        }
    }
}
