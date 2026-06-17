package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.Components.AlbumDialogPane;

public class AlbumDialogPaneController {
    @FXML
    private TextField newAlbumName;
    private Stage stage;
    private AlbumDialogPane pane;

    public void setStage(Stage stage){this.stage=stage;}

    public void SetPane(AlbumDialogPane pane){this.pane=pane;}

    public void onOKClick() {
        decision(true);
    }


    private void decision(boolean flag){
        if(flag)
        {
            this.pane.giveName(newAlbumName.getText());
        }
        stage.close();
    }

    public void cancellOperation() {
        decision(false);
    }
}
