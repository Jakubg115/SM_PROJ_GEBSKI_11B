package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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
        Settings.homepage=createConcretePage("HomePage");
        Settings.librarypage=createConcretePage("LibraryPage");
        Settings.queuepage=createConcretePage("QueuePage");
        Settings.albumpage=createConcretePage("AlbumPage");
        Settings.settingspage=createConcretePage("SettingsPage");

        toMainPage();
    }

    public void setStage(Stage st) {
        this.stage = st;
    }
    public Stage getStage(){return stage;}

    public void setProgramName(String name){mainTitle.setText(name);}

    @FXML
    private void onCloseWindow() {
        Settings.saveChanges();
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

    private <T>T createConcretePage(String component_name){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/"+component_name+".fxml"));
        try {
            AnchorPane page=loader.load();
            Settings.MainPages.add(page);
            AnchorPane.setRightAnchor(page,0.0);
            AnchorPane.setLeftAnchor(page,0.0);
            AnchorPane.setBottomAnchor(page,0.0);
            AnchorPane.setTopAnchor(page,0.0);

            return loader.getController();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    private void toMainPage() {
        mainPanel.getChildren().clear();
        Settings.activeIndex=0;
        mainPanel.getChildren().add(Settings.getPage(Settings.activeIndex));

    }

    @FXML
    private void toLibraryPage() {
        mainPanel.getChildren().clear();
        Settings.activeIndex=1;
        System.out.println(Settings.librarypage.loaded);
        mainPanel.getChildren().add(Settings.getPage(Settings.activeIndex));
        if(!Settings.librarypage.loaded){Settings.librarypage.initFolders();Settings.librarypage.loaded=true;}

    }

    @FXML
    private void toQueuePage() {
        mainPanel.getChildren().clear();
        Settings.activeIndex=2;
        mainPanel.getChildren().add(Settings.getPage(Settings.activeIndex));
    }

    @FXML
    private void toAlbumPage() {
        mainPanel.getChildren().clear();
        Settings.activeIndex=3;
        mainPanel.getChildren().add(Settings.getPage(Settings.activeIndex));
    }

    @FXML
    private void toSettingsPage() {
        mainPanel.getChildren().clear();
        Settings.activeIndex=4;
        mainPanel.getChildren().add(Settings.getPage(Settings.activeIndex));
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
