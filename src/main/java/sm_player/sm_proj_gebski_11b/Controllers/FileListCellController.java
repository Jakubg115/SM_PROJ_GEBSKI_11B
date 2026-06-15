package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import sm_player.sm_proj_gebski_11b.Components.FileListCell;

public class FileListCellController {
    @FXML
    private Label fileName;
    @FXML
    public CheckBox selectedFile;

    private FileListCell copy;

    public void obtainComponent(FileListCell c){this.copy=c;}

    public void set(String filename){
        this.fileName.setText(filename);
    }

    public void onMarked() {
        this.copy.manageThisFile(this.selectedFile.isSelected());
    }

    public void markCell(boolean flag){this.selectedFile.setSelected(flag);}

    public String getFileName(){return this.fileName.getText();}

    public void onClick(MouseEvent event) {

    }


}
