package sm_player.sm_proj_gebski_11b.JakubGebski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;

import java.util.LinkedList;
import java.util.List;

public interface StaticObjects {

    List<String> Directories=new LinkedList<>();
    String configDirPath= "config.txt";
    LinkedList<AnchorPane> MainPages=new LinkedList<>();
    ObservableList<Double> speeds = FXCollections.observableArrayList(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);
    ObservableList<String> themes = FXCollections.observableArrayList("Bright","Dark");
    String defaultDirPath="Główny: src/main/resources/music";

}
