package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;
import sm_player.sm_proj_gebski_11b.JakubGebski.StaticObjects;

public class MainScreen implements StaticObjects {

    private double x, y;

    @FXML
    private Label mainTitle;
    @FXML
    private VBox sidePanel;
    @FXML
    private AnchorPane windowPanel, mainPanel;

    private Stage stage;
    private boolean maximized = false;


    @FXML
    public void initialize(){
        mainTitle.setText(Settings.getProgramName());
    }

    public void setStage(Stage st) {
        this.stage = st;
    }

    @FXML
    private void onCloseWindow() {
        this.stage.close();
    }

    @FXML
    private void onMaximizeWindow() {
        this.maximized = !this.maximized;
        this.stage.setMaximized(this.maximized);
    }

    @FXML
    private void onMinimizeWindow() {
        this.stage.setIconified(true);
    }

    private void setConcretePage(String component_name){
        mainPanel.getChildren().clear();
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/"+component_name+".fxml"));
        try {
            AnchorPane page=loader.load();
             mainPanel.getChildren().add(page);
            AnchorPane.setRightAnchor(page,0.0);
            AnchorPane.setLeftAnchor(page,0.0);
            AnchorPane.setBottomAnchor(page,0.0);
            AnchorPane.setTopAnchor(page,0.0);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void toMainPage() {
        mainPanel.getChildren().clear();
        setConcretePage("HomePage");

    }



    @FXML
    private void toLibraryPage() {
        setConcretePage("LibraryPage");
    }

    @FXML
    private void toQueuePage() {
        mainPanel.getChildren().clear();
    }

    @FXML
    private void toSettingsPage() {
        mainPanel.getChildren().clear();
    }

    @FXML
    private void toAlbumPage() {
        mainPanel.getChildren().clear();
    }

    @FXML
    private void onChooserClick() {
    }

    public void getMousePos(MouseEvent event) {
        x = event.getSceneX();
        y = event.getSceneY();
    }

    public void moveWindow(MouseEvent event) {
        this.stage.setX(event.getScreenX() - x);
        this.stage.setY(event.getScreenY() - y);
    }
}
