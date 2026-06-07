package sm_player.sm_proj_gebski_11b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sm_player.sm_proj_gebski_11b.Controllers.LibraryController;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Settings.readSettings();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("LibraryScene.fxml"));
        double[] resolution=Settings.getResolution();
        Scene scene = new Scene(fxmlLoader.load(),resolution[0],resolution[1]);
        stage.setTitle("Odtwarzacz Muzyki!");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        LibraryController s=fxmlLoader.getController();
        s.setStage(stage);
        Settings.initTheme(stage);
        System.out.println(Settings.getMainDirPath());
    }
}