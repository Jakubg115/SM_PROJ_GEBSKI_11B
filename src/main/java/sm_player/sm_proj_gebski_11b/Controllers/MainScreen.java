package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.Components.QueuePage;
import sm_player.sm_proj_gebski_11b.Components.SettingsPage;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

public class MainScreen {

    private double x, y;

    @FXML
    private Label mainTitle;
    @FXML
    private AnchorPane mainPanel;

    private Stage stage;
    private boolean maximized = false;


    @FXML
    public void initialize(){
        mainTitle.setText(Settings.getProgramName());
        Settings.MainPages.add(createConcretePage("HomePage")); //kreuje Strone glowna
        Settings.MainPages.add(createConcretePage("LibraryPage")); //kreuje Strone z Bibliotekami
        Settings.MainPages.add(createConcretePage("QueuePage")); //kreuje Strone z Bibliotekami
        Settings.MainPages.add(createConcretePage("AlbumPage")); //kreuje Strone z Bibliotekami
        Settings.MainPages.add(createConcretePage("SettingsPage")); //kreuje Strone z Bibliotekami
        toMainPage();
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

    private AnchorPane createConcretePage(String component_name){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/"+component_name+".fxml"));
        try {
            AnchorPane page=loader.load();
            AnchorPane.setRightAnchor(page,0.0);
            AnchorPane.setLeftAnchor(page,0.0);
            AnchorPane.setBottomAnchor(page,0.0);
            AnchorPane.setTopAnchor(page,0.0);

            return page;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    @FXML
    private void toMainPage() {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(Settings.getPage(0));

    }

    @FXML
    private void toLibraryPage() {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(Settings.getPage(1));
    }

    @FXML
    private void toQueuePage() {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(Settings.getPage(2));
    }

    @FXML
    private void toAlbumPage() {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(Settings.getPage(3));
    }

    @FXML
    private void toSettingsPage() {
        mainPanel.getChildren().clear();
        mainPanel.getChildren().add(Settings.getPage(4));
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
