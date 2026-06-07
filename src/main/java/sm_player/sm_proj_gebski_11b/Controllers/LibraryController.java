package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.JakubGebski.StaticObjects;

public class LibraryController implements StaticObjects {

    private double x, y;

    @FXML
    private Label activeTitle;
    @FXML
    private VBox sidePanel;
    @FXML
    private AnchorPane windowPanel, mainPanel;
    @FXML
    private Button chooserButton;

    private Stage stage;
    private boolean maximized = false;

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


    @FXML
    private void toMainPage() {
        this.activeTitle.setText("Strona główna");
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/MainScene.fxml"));
        try {
            AnchorPane asd=loader.load();
            mainPanel.getChildren().add(asd);

            AnchorPane.setBottomAnchor(asd,0.0);
            AnchorPane.setLeftAnchor(asd,0.0);
            AnchorPane.setRightAnchor(asd,0.0);

            System.out.println(mainPanel.getWidth());
            System.out.println(mainPanel.getHeight());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void toLibraryPage() {
        this.activeTitle.setText("Przegląd bibliotek");
    }

    @FXML
    private void toQueuePage() {
        this.activeTitle.setText("Kolejka odtwarzania");
    }

    @FXML
    private void toSettingsPage() {
        this.activeTitle.setText("Ustawienia");
    }

    @FXML
    private void toAlbumPage() {
        this.activeTitle.setText("Albumy");
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
