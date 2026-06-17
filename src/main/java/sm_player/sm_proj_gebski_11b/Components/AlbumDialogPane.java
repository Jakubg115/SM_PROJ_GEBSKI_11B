package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.Controllers.AlbumDialogPaneController;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.IOException;

public class AlbumDialogPane {

    private int operationNumber;
    private final AlbumPage page;
    public AlbumDialogPane(AlbumPage pointer, int operationNumber){
        this.page=pointer;
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/AlbumDialogPane.fxml"));
        try {
            this.operationNumber=operationNumber;
            Parent root=loader.load();
            Stage stage=new Stage();
            stage.setScene(new Scene(root));
            AlbumDialogPaneController controller = loader.getController();
            controller.SetPane(this);
            controller.setStage(stage);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void giveName(String name){
        switch (this.operationNumber){
            case 1:
                page.addNewAlbum(name);
                break;
            case 2:
                Settings.addAlbumWithFiles(name);
        }

    }
}
