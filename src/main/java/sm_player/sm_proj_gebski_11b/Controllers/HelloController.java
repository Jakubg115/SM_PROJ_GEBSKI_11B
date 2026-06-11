package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.util.*;

import javafx.stage.DirectoryChooser;
import sm_player.sm_proj_gebski_11b.JakubGebski.*;


public class HelloController extends AnchorPane implements StaticObjects {

    public VBox asd;
    public List<String> musicFiles;
    @FXML
    TextField pathTextField;

    @FXML
    Button resetButton;

    @FXML
    Button loadButton;

    @FXML
    Button mixButton;

    @FXML
    Button unmixButton;

    @FXML
    ListView<String> musicListView;

    private final String defaultMusicPath = "/music";
    private String currentMusicPath;

    PrerunClass prerun=new PrerunClass();

    @FXML
    public void initialize() {
        currentMusicPath = getClass().getResource(defaultMusicPath).getPath();
        System.out.println(currentMusicPath);
        musicFiles=prerun.loadMusicFiles(currentMusicPath);

        unmixButton.setDisable(true);
        try
        {
            if(musicFiles==null) throw new Exception("Nie znaleziono plikow!\n");
            insertItems(musicFiles);
            prerun.fillQueue(musicFiles);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        musicListView.setOnMouseClicked(event -> {
            try {
                if (event.getClickCount() == 2) {
                    String selectedFile = musicListView.getSelectionModel().getSelectedItem();
                    if (selectedFile != null) {
                        openMediaPlayerScene();
                    }
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        resetButton.setOnAction(event -> resetToDefaultPath());
        loadButton.setOnMouseClicked(event -> openFolderChooser());
        mixButton.setOnMouseClicked(event->mixMusicFiles());
        unmixButton.setOnMouseClicked(event->unmixMusicFiles());
        pathTextField.setOnAction(event -> insertItems(musicFiles=prerun.loadMusicFiles(pathTextField.getText())));
    }


    private void resetToDefaultPath() {
        currentMusicPath =getClass().getResource(defaultMusicPath).getPath();
        pathTextField.setText(currentMusicPath);
        musicFiles=prerun.loadMusicFiles(currentMusicPath);
        insertItems(musicFiles);
    }

    public void openMediaPlayerScene()throws Exception {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/MusicPlayerScene.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setTitle("Odtwarzacz Muzyki");
            stage.setScene(new Scene(root));
            stage.show();
            MediaController controller = loader.getController();
            controller.copyStage(stage);

            if((musicListView.getSelectionModel().getSelectedIndex()!=-1)){
            controller.Start(musicListView.getSelectionModel().getSelectedIndex());}
            else{controller.Start(0);}

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void openFolderChooser() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Wybierz folder");

        File selectedDirectory = directoryChooser.showDialog(new Stage());
        try
        {
            if (selectedDirectory != null) {
                pathTextField.setText(selectedDirectory.getAbsolutePath());
                musicFiles=prerun.loadMusicFiles(selectedDirectory.getAbsolutePath());
                insertItems(musicFiles);
            }
            else throw new RuntimeException();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    currentMusicPath=prerun.getCurrentpath();
    }

    public void mixMusicFiles(){
        insertItems(prerun.Shuffle(Settings.queue));
        unmixButton.setDisable(false);
    }

    public void unmixMusicFiles(){
        insertItems(musicFiles);
        unmixButton.setDisable(true);
    }

    public void insertItems(List<String> list)
    {
        musicListView.getItems().setAll(list);
        prerun.fillQueue(list);
    }
    public List<String> getmusicFilesList()
    {
        return musicFiles;
    }

}
