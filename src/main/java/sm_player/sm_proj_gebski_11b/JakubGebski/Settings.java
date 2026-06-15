package sm_player.sm_proj_gebski_11b.JakubGebski;

import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.jetbrains.annotations.NotNull;
import sm_player.sm_proj_gebski_11b.Components.*;
import sm_player.sm_proj_gebski_11b.Controllers.MainScreen;
import sm_player.sm_proj_gebski_11b.Controllers.MediaController;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Settings implements StaticObjects {

    public static LinkedList<String> queue=new LinkedList<>();

    public static HomePage homepage;
    public static LibraryPage librarypage;
    public static QueuePage queuepage;
    public static AlbumPage albumpage;
    public static SettingsPage settingspage;

    public static int activeIndex=0;

    private static String programName;
    private static String currentTheme;
    private static double width,height;

    public static Stage mediaPlayerStage;
    private static MediaController mediaController;
    private static MainScreen mainscreenController;





    //-------------------------------------gettery----------------------------------------//

    public static double[] getResolution(){return new double[]{width, height};}

    public static String getProgramName(){return programName;}

    public static Iterator<String> getDirectories(){return Directories.iterator();}

    public static String getMainDirPath(){return Directories.get(0);}

    public static AnchorPane getPage(int index){
        return MainPages.get(index);
    }
    public static String getCurrentTheme(){return currentTheme;}

    public static ObservableList<String> getThemeLists(){return themes;}

    public static List<String> getSelectedFolders(){
        return Directories;
    }

    public static List<String> getActiveQueue(){return queue;}

    public static void validateInMediaPlayer(){
        mediaController.zeroIndex();
        mediaController.manageQueueButtons();
    }

    public static String getActiveFile(){return mediaController.getActiveFile();}

    public static MainScreen getController(){return mainscreenController;}

    //-------------------------------------settery----------------------------------------//

    public static void setController(MainScreen con){mainscreenController=con;}

    public static void initTheme(@NotNull Stage stage){
        try {
            stage.getScene().getStylesheets().clear();
            stage.getScene().getStylesheets().add(Objects.requireNonNull(Settings.class.getResource("/styles/" + currentTheme + ".css")).toExternalForm());
        }catch (NullPointerException e)
        {
            System.out.println("W pliku konfigoracyjnym doszlo do naruszenia wartosci do motywu!\n Wdrozenie domyslnej konfiguracji (Jasny)");
            currentTheme="Bright";
            stage.getScene().getStylesheets().add(Objects.requireNonNull(Settings.class.getResource("/styles/" + currentTheme + ".css")).toExternalForm());
        }

    }

    public static void setTheme(String mode){
        currentTheme= mode;
    }

    public static void setResolution(double x, double y){
        width=x;
        height=y;
    }

    public static void setmainDir(String filepath){
        String[] parsec=filepath.split(": ");
        if(Directories.isEmpty())
        {
            Directories.add(parsec[0]+": "+parsec[1]);
        }
        else {Directories.set(0,parsec[0]+": "+parsec[1]);}
    }

    public static void setProgramName(String name){programName=name;}

    //------------------------------operacje do programu------------------------------------//

    public static int addFileToQueue(String filePath){
        if(!queue.contains(filePath)){
            queue.add(filePath);
        }
        return queue.indexOf(filePath);
    }


    public static void addFolder(String name, String path){
        Directories.add(name+": "+path);
        settingspage.refreshFolderList();
    }

    public static void setChanges(){
        Stage stage=mainscreenController.getStage();
        double[] resolution=getResolution();
        initTheme(stage);
        stage.setWidth(resolution[0]);
        stage.setHeight(resolution[1]);
        initTheme(mediaPlayerStage);
        mainscreenController.setProgramName(getProgramName());

    }

    public static void openMediaPlayerScene(int index){
        try {
            FXMLLoader loader = new FXMLLoader(Settings.class.getResource("/sm_player/sm_proj_gebski_11b/MusicPlayerScene.fxml"));
            if(mediaPlayerStage ==null){

                Parent root = loader.load();
                mediaPlayerStage=new Stage();
                mediaPlayerStage.setScene(new Scene(root));
                mediaPlayerStage.setTitle("Odtwarzacz Muzyki");

                initTheme(mediaPlayerStage);
            }
            mediaPlayerStage.show();
            if(mediaController ==null)
            {
                mediaController = loader.getController();
                mediaController.copyStage(mediaPlayerStage);
                mediaController.Start(index);
            }
            else {
                mediaController.setIndex(index);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //------------------------------Zapis i odczyt pliku------------------------------------//

    private static void writeFolderPaths(FileWriter file){
        try {
            file.write("Directories:{\n");
            for (String directory : Directories) {
                file.write(directory + "\n");
            }
            file.write("}\n");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    private static void writeCurrentTheme(FileWriter file){
        String theme="Theme: "+currentTheme+"\n";
        try {
            file.append(theme);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void writeResolution(FileWriter file){
        try {
            String resolution="Width: "+width+"\nHeight: "+height+"\n";
            file.write(resolution);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void saveChanges(){
        try{
            FileWriter result=new FileWriter(configDirPath);
            result.write("Name: "+programName+"\n");
            writeResolution(result);
            writeCurrentTheme(result);
            writeFolderPaths(result);
            result.close();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static void lookForFolders(Iterator<String> it){
        String s;
        while (it.hasNext())
        {
            s=it.next();
            if(!s.equals("}")){
                if(s.contains("Directories:{")){continue;}
                Directories.add(s);
            }
            else {break;}
        }

        if(Directories.isEmpty()){
            Directories.add(defaultDirPath);
        }

    }

    public static void readSettings(){
        try {
            FileReader reader=new FileReader(configDirPath);
            List<String> readed= reader.readAllLines();
            Iterator<String> it=readed.iterator();

            programName=it.next().split(": ")[1];
            width=Double.parseDouble(it.next().split(": ")[1]);
            height=Double.parseDouble(it.next().split(": ")[1]);
            currentTheme=it.next().split(": ")[1];
            lookForFolders(it);

        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono w programie pliku konfiguracyjnego.\n Wdrozenie konfiguracji domyslnej!");
            defaultSetting();
            saveChanges();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        catch (NumberFormatException | NoSuchElementException e){
            System.out.println("Blad w trakcie odczytywania ustawien do programu! Wdrozenie konfiguracji domyslnej!");
            defaultSetting();
            saveChanges();
        }

    }

    public static void defaultSetting(){
        programName="Odtwarzacz MP3";
        width=800; height=600;
        currentTheme="Bright";
        if(Settings.Directories.isEmpty()){
            Settings.Directories.add(Settings.defaultDirPath);
        }
        else {
            Settings.Directories.set(0,Settings.defaultDirPath);
        }

    }


}
