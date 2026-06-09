module sm_player.sm_proj_gebski_11b {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires javafx.media;
    requires jaudiotagger;
    requires annotations;
    requires de.jensd.fx.glyphs.fontawesome;

    opens sm_player.sm_proj_gebski_11b to javafx.fxml;
    exports sm_player.sm_proj_gebski_11b;
    exports sm_player.sm_proj_gebski_11b.Controllers;
    opens sm_player.sm_proj_gebski_11b.Controllers to javafx.fxml;
    exports sm_player.sm_proj_gebski_11b.Components;
    opens sm_player.sm_proj_gebski_11b.Components to javafx.fxml;
}