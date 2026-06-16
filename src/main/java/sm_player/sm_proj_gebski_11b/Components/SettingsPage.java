package sm_player.sm_proj_gebski_11b.Components;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.io.File;
import java.util.List;

public class SettingsPage {
    @FXML
    private TextField programName, programWidth, programHeight, mainDirPath;
    @FXML
    private ChoiceBox<String> programTheme = new ChoiceBox<>();
    @FXML
    private ListView<String> folderView;

    public void initReadedValues() {
        programName.setText(Settings.getProgramName());
        double[] resolution = Settings.getResolution();
        programWidth.setText(resolution[0] + "");
        programHeight.setText(resolution[1] + "");
        programTheme.setItems(Settings.getThemeLists());
        programTheme.getSelectionModel().select(Settings.getCurrentTheme());
        refreshFolderList();

    }

    @FXML
    public void initialize() {
        initReadedValues();
        folderView.setEditable(true);
    }

    public void refreshFolderList() {
        folderView.getItems().clear();
        List<String> list = Settings.getSelectedFolders();
        for (int i = 0; i < list.size(); i++) {
            folderView.getItems().add(list.get(i));
        }
    }

    private void resetComponents() {
        programName.clear();
        programWidth.clear();
        programHeight.clear();
    }


    public void setSaved() {
        resetComponents();
        Settings.defaultSetting();
        initReadedValues();
    }

    public void saveChanges() {
        Settings.setProgramName(programName.getText());
        try {

            double width = Double.parseDouble(programWidth.getText());
            double height= Double.parseDouble(programHeight.getText());
            Settings.setResolution(width<500?500:width,height<300?300:height);
            Settings.setTheme(programTheme.getValue());
            Settings.setFoldersList(mainDirPath.getText(), folderView.getItems());
            Settings.setmainDir(mainDirPath.getText());
            Settings.saveChanges();
            Settings.setChanges();
        } catch (Exception e) {
            programWidth.setText(""+Settings.getResolution()[0]);
            programHeight.setText(""+Settings.getResolution()[1]);
            Settings.saveChanges();
            Settings.setChanges();
        }


    }

    public void loadFolderPath(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Wybierz folder");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        try
        {
            if (selectedDirectory != null) {
                String name= selectedDirectory.getName();
                String path= selectedDirectory.getAbsolutePath();
                Settings.setmainDir(name+": "+path);
            }
            else throw new RuntimeException();
        } catch (Exception e) {
            System.out.println("Folder nie zostal znleziony! Anulacja zadania");

        }
    }

    public void resetFolderPath(ActionEvent actionEvent) {
        mainDirPath.setText(Settings.getDefaultDirPath());
    }
}
