package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sm_player.sm_proj_gebski_11b.Components.FileListCell;

public class FileListCellController {
    public Label fileName;
    public CheckBox selectedFile;

    public FileListCell copy;

    public void obtainComponent(FileListCell c){this.copy=c;}

    public void set(String filename){
        this.fileName.setText(filename);
    }

    public void unmarkCell(){this.selectedFile.setSelected(false);}

    public String getFileName(){return this.fileName.getText();}

    public void onClick(MouseEvent event) {

    }

    public void onMarked() {
        this.copy.manageQueueOption(this.selectedFile.isSelected());
    }
}
