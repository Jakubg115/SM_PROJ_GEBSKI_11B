module sm_player.sm_proj_gebski_11b {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.media;
    requires jaudiotagger;

    opens sm_player.sm_proj_gebski_11b to javafx.fxml;
    exports sm_player.sm_proj_gebski_11b;
}