package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FileListCellController {
    public Label fileName;
    public Button fileOptions;
    public CheckBox selectedFile;

    public void set(String filename){
        this.fileName.setText(filename);
    }

    public void onClick(MouseEvent event) {
    }

    public void onMarked(ActionEvent actionEvent) {
    }

    public void onOptionsClick(ActionEvent actionEvent) {
    }
}
