package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.util.Collections;
import java.util.LinkedList;

public class QueuePage {
    @FXML
    private ListView<String> queueView=new ListView<>();
    @FXML
    private Button unShuffleButton, clearButton, shuffleButton, initButton;

    private final LinkedList<String> forShuffle=new LinkedList<>();

    public void updateQueueView(){
        queueView.getItems().clear();
        queueView.getItems().setAll(Settings.getActiveQueue());
        shuffleButton.setDisable(queueView.getItems().isEmpty());
        clearButton.setDisable(queueView.getItems().isEmpty());
        initButton.setDisable(queueView.getItems().isEmpty());
    }

    public void initQueue() {Settings.openMediaPlayerScene(0);}

    public void shuffle() {
        if(forShuffle.isEmpty()){
            forShuffle.addAll(Settings.queue);
            unShuffleButton.setDisable(false);
        }
        Collections.shuffle(Settings.queue);
        updateQueueView();
    }

    public void resetShuffle() {
        Settings.queue.clear();
        Settings.queue.addAll(forShuffle);
        updateQueueView();
        forShuffle.clear();
        unShuffleButton.setDisable(true);
    }

    public void initMusic(MouseEvent event) {
        if(event.getClickCount()==2){Settings.openMediaPlayerScene(queueView.getSelectionModel().getSelectedIndex());}
    }

    public void addFile(String s){
        Settings.queue.add(s);
        updateQueueView();
    }

    public void clearQueue() {
        String s="";
        try {
            s=Settings.getActiveFile();
            Settings.queue.clear();
            forShuffle.clear();
            updateQueueView();
            if(Settings.mediaPlayerStage.isShowing())
            {
                if(!s.isEmpty()) addFile(s);
                Settings.validateInMediaPlayer();
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        unShuffleButton.setDisable(true);
    }
}
