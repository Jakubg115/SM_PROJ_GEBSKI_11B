package sm_player.sm_proj_gebski_11b.Components;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class QueuePage {
    public ListView<String> queueView=new ListView<>();
    public Button unShuffleButton;
    public Button clearButton;
    public Button shuffleButton;
    public Button initButton;
    private LinkedList<String> forShuffle=new LinkedList<>();

    public void updateQueueView(){
        queueView.getItems().clear();
        queueView.getItems().setAll(Settings.getActiveQueue());
        shuffleButton.setDisable(queueView.getItems().isEmpty());
        clearButton.setDisable(queueView.getItems().isEmpty());
        initButton.setDisable(queueView.getItems().isEmpty());
    }


    public void initQueue(ActionEvent actionEvent) {
        Settings.openMediaPlayerScene(0);

    }

    public void shuffle(ActionEvent actionEvent) {
        if(forShuffle.isEmpty()){

            forShuffle.addAll(Settings.queue);
            System.out.println(forShuffle.size()+" | "+Settings.queue.size());
            unShuffleButton.setDisable(false);
        }
        Collections.shuffle(Settings.queue);
        updateQueueView();

        
    }

    public void resetShuffle(ActionEvent actionEvent) {
        Settings.queue.clear();
        Settings.queue.addAll(forShuffle);
        updateQueueView();
        forShuffle.clear();
        unShuffleButton.setDisable(true);
    }

    public void initMusic(MouseEvent event) {
        if(event.getClickCount()==2){
            Settings.openMediaPlayerScene(queueView.getSelectionModel().getSelectedIndex());
        }
    }

    private void addFile(String s){
        Settings.queue.add(s);
        updateQueueView();
    }

    public void clearQueue(ActionEvent actionEvent) {
        String s="";
        if(Settings.mediaPlayerStage.isShowing())
        {
            s=Settings.getActiveFile();
        }
        Settings.queue.clear();
        forShuffle.clear();
        updateQueueView();
        if(!s.isEmpty()){addFile(s);}
        Settings.validateInMediaPlayer();
    }
}
