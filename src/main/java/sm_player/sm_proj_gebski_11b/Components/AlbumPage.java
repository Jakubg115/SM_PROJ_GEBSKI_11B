package sm_player.sm_proj_gebski_11b.Components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class AlbumPage {

    @FXML
    private FlowPane albumList;

    @FXML
    public void initialize(){
        albumList.getChildren().add(new AlbumComponent("Tessssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssst"));
        albumList.getChildren().add(new AlbumComponent("Test"));
        albumList.getChildren().add(new AlbumComponent("Test"));
        albumList.getChildren().add(new AlbumComponent("Test"));
        albumList.getChildren().add(new AlbumComponent("Test"));
    }

    public void addNewAlbum(ActionEvent actionEvent) {
    }
}
