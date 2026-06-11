package sm_player.sm_proj_gebski_11b.Components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

public class SettingsPage extends AnchorPane {
    @FXML
    private TextField programName, programWidth, programHeight, mainDirPath;
    @FXML
    private ChoiceBox<String> programTheme=new ChoiceBox<>();
    @FXML
    private ListView<String> folderView;

    public void initReadedValues(){
        programName.setText(Settings.getProgramName());
        double[] resolution=Settings.getResolution();
        programWidth.setText(resolution[0]+"");
        programHeight.setText(resolution[1]+"");
        mainDirPath.setText(Settings.getMainDirPath());
        programTheme.setItems(Settings.getThemeLists());
        programTheme.setValue(Settings.getCurrentTheme());

        folderView.getItems().setAll(Settings.getSelectedFolders());
    }

    @FXML
    public void initialize(){
        initReadedValues();
        folderView.setEditable(true);
        folderView.setCellFactory(TextFieldListCell.forListView());
    }

    public void setSaved(ActionEvent actionEvent) {
        Settings.readSettings();
        initReadedValues();
    }

    public void saveChanges(ActionEvent actionEvent) {

    }

    public void loadFolderPath(ActionEvent actionEvent) {
    }

    public void resetFolderPath(ActionEvent actionEvent) {

    }
}
