package sm_player.sm_proj_gebski_11b;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sm_player.sm_proj_gebski_11b.Controllers.MainScreen;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        Settings.readSettings();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScreen.fxml"));
        double[] resolution=Settings.getResolution();
        Scene scene = new Scene(fxmlLoader.load(),resolution[0],resolution[1]);
        stage.setTitle(Settings.getProgramName());
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        Settings.setController(fxmlLoader.getController());
        Settings.getController().setStage(stage);
        Settings.initTheme(stage);

    }
}