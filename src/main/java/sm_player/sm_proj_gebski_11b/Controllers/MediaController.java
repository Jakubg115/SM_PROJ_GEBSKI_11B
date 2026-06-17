package sm_player.sm_proj_gebski_11b.Controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.scene.media.MediaPlayer;
import sm_player.sm_proj_gebski_11b.JakubGebski.*;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;

import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.Tag;
import org.jaudiotagger.tag.datatype.Artwork;



public class MediaController {
    // obiekty dla fxml
    @FXML
    private AnchorPane mainPanel, coverPanel;
    @FXML
    protected HBox ButtonMenu = new HBox();
    @FXML
    protected Button PauseButton = new Button();
    @FXML
    protected Button PrevButton = new Button();
    @FXML
    protected Button NextButton = new Button();
    @FXML
    protected Slider VolSlider = new Slider();
    @FXML
    protected Label MuteButton = new Label();
    @FXML
    protected ComboBox<Double> SpeedBox = new ComboBox<>();
    @FXML
    protected Slider PlaybackSlider = new Slider();
    @FXML
    protected ImageView MusicImage = new ImageView();
    @FXML
    protected Label curTime = new Label();
    @FXML
    protected Label finTime = new Label();

    private MediaPlayer player;
    private Stage stage;
    //PrerunClass prerun=new PrerunClass();
    private boolean pausestate = false;
    private boolean muted = false;
    private static int currindex;
    private double volval;

    private final InvalidationListener playbacklistener = new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            PlaybackSlider.setValue(player.getCurrentTime().toMillis());
            Platform.runLater(() -> curTime.setText(Settings.initTime((int)player.getCurrentTime().toSeconds())));
        }
    };
    private final InvalidationListener volchangelistener=new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            volval=player.getVolume();
        }
    };


    public void initListeners()
    {
        MusicImage.setFitWidth(coverPanel.getWidth());
        MusicImage.setFitHeight(coverPanel.getHeight());
        player.currentTimeProperty().addListener(playbacklistener);
        VolSlider.valueProperty().addListener(volchangelistener);
        stage.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                VolSlider.setMajorTickUnit(VolSlider.getWidth()<90?0.5:0.2);
                ButtonMenu.setSpacing((ButtonMenu.getWidth()/30)-5);
                MusicImage.setFitWidth(coverPanel.getWidth());
                AnchorPane.setLeftAnchor(coverPanel,mainPanel.getWidth()<=600.0?50.0:150.0);
                AnchorPane.setRightAnchor(coverPanel,mainPanel.getWidth()<=600.0?50.0:150.0);
                System.out.println(mainPanel.getWidth());
                MusicImage.setSmooth(true);
            }
        });
        stage.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                MusicImage.setFitHeight(coverPanel.getHeight());
            }
        });
        stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, _ -> closePlayer());
        player.setRate(1.0);
        player.setVolume(0.5);

    }

    public void closePlayer(){
        player.stop();
        stage.close();
    }

    public void manageQueueButtons(){
        PrevButton.setDisable(currindex == 0);
        NextButton.setDisable(currindex == Settings.queue.size() - 1);
    }

    @FXML
    public void initialize(){
        SpeedBox.setItems(Settings.getSpeeds());
        VolSlider.setValue(0.5);
    }

    private final Runnable onRun=new Runnable() {
        @Override
        public void run() {
            PlaybackSlider.setMax(player.getTotalDuration().toMillis());
            manageQueueButtons();
            player.setVolume(VolSlider.getValue());
        }
    };

    public void Start(int index){
        try {

            if(Settings.queue.isEmpty()){throw new FileNotFoundException();}
            currindex = index;

            // obiekty niestandardowe
            Media currmusic = new Media(new File(Settings.queue.get(currindex)).toURI().toString());
            player = new MediaPlayer(currmusic);
            initListeners();
            SetMeta(Settings.queue.get(currindex));
            player.setOnReady(onRun);
            if(pausestate){
                PauseButton.setText("⏸");
                switchPauseState();
            }
            player.play();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        NextButton.setOnMouseClicked(_ -> {
            try {
                changeIndex(1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        PrevButton.setOnMouseClicked(_ -> {
            try {
                changeIndex(-1);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

    }

    public String getActiveFile(){return Settings.queue.get(currindex);}

    public void SetMeta(String filePath) {
        Image no = new Image("No_Cover.jpg");
        try {
            AudioFile audioFile = AudioFileIO.read(new File(filePath));
            Tag tag = audioFile.getTag();
            AudioHeader audioHeader = audioFile.getAudioHeader();
            final String finalTitle = (tag != null && tag.getFirst(FieldKey.TITLE) != null && !tag.getFirst(FieldKey.TITLE).isEmpty()) ? tag.getFirst(FieldKey.TITLE) : new File(filePath).getName();

            Platform.runLater(() -> {
                finTime.setText(Settings.initTime(audioHeader.getTrackLength()));
                this.stage.setTitle(finalTitle);
            });

            if (tag != null && !tag.getArtworkList().isEmpty()) {
                Artwork artwork = tag.getFirstArtwork();
                if (artwork != null) {
                    ByteArrayInputStream imageStream = new ByteArrayInputStream(artwork.getBinaryData());
                    Image albumImage = new Image(imageStream);

                    Platform.runLater(() -> {
                        MusicImage.setImage(albumImage);
                        System.out.println("Obraz albumu został ustawiony.");
                    });
                } else {
                    Platform.runLater(() -> MusicImage.setImage(no));
                    System.out.println("Brak obrazu albumu w metadanych.");
                }
            } else {
                Platform.runLater(() -> MusicImage.setImage(no));
                System.out.println("Brak metadanych lub brak obrazu albumu.");
            }
        } catch (Exception e) {
            System.err.println("Błąd podczas odczytu metadanych: " + e.getMessage());
            Platform.runLater(() -> MusicImage.setImage(no));
        }
    }


    public void copyStage(Stage stage) {
        this.stage = stage;
    }
    // metody eventowe z kontrolkami

    private boolean switchPauseState() {
        pausestate = !pausestate;
        return pausestate;
    }

    @FXML
    public void PauseState() {
        if (switchPauseState()) {
            PauseButton.setText("▶");
            player.pause();
        } else {
            PauseButton.setText("⏸");
            player.play();
        }
    }

    private boolean switchmuted() {
        muted = !muted;
        return muted;
    }

    @FXML
    public void mute() {
        if (switchmuted()) {
            MuteButton.setText("\uD83D\uDD68");
            player.setMute(true);
            VolSlider.setValue(0.0);
        } else {
            MuteButton.setText("\uD83D\uDD69");
            player.setMute(false);
            VolSlider.setValue(volval);
        }
    }

    @FXML
    public void VolSliderAction() {
        player.setVolume(VolSlider.getValue());
    }

    @FXML
    public void SpeedBoxAction() {
        player.setRate(SpeedBox.getValue());
    }

    @FXML
    public void OnPlaybackPressed() {
        player.currentTimeProperty().removeListener(playbacklistener);
    }

    @FXML
    public void OnPlaybackReleased() {
        player.seek(player.getMedia().getDuration().subtract(new Duration(player.getTotalDuration().toMillis() - PlaybackSlider.getValue())));
        player.currentTimeProperty().addListener(playbacklistener);
    }


    public void changeIndex(int wy) throws FileNotFoundException {
        pausestate = true;
        PauseState();
        player.stop();
        MusicImage.setImage(null);
        PrevButton.setDisable(false);
        NextButton.setDisable(false);

        currindex = currindex + wy;
        Start(currindex);
    }
    public void setIndex(int index) {
        player.stop();
        MusicImage.setImage(null);
        Start(index);

    }
    public void zeroIndex(){currindex=0;}
}
