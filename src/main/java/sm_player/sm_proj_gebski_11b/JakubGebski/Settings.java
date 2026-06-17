package sm_player.sm_proj_gebski_11b.JakubGebski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import sm_player.sm_proj_gebski_11b.Components.*;
import sm_player.sm_proj_gebski_11b.Controllers.MainScreen;
import sm_player.sm_proj_gebski_11b.Controllers.MediaController;

import java.io.*;
import java.util.*;

public class Settings {

    private static final List<String> Directories=new LinkedList<>();
    private static final String configDirPath= "config.txt";
    private static final LinkedList<AnchorPane> MainPages=new LinkedList<>();
    private static final LinkedList<FileListCell> fileBranch=new LinkedList<>();
    private static final ObservableList<Double> speeds = FXCollections.observableArrayList(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);
    private static final ObservableList<String> themes = FXCollections.observableArrayList("Bright","Dark");
    private static final String defaultAlbumDirPath="src/main/resources/Albums";
    private static String currentAlbumDirPath;

    public static LinkedList<String> queue=new LinkedList<>();

    public static HomePage homepage;
    public static LibraryPage librarypage;
    public static QueuePage queuepage;
    public static AlbumPage albumpage;
    public static SettingsPage settingspage;
    public static AlbumViewSubPage albumsubpage;

    public static int activeIndex=0;

    private static String programName;
    private static String currentTheme;
    private static double width,height;

    public static Stage mediaPlayerStage;
    private static MediaController mediaController;
    private static MainScreen mainscreenController;

    private static AnchorPane albumPageList, albumPageView;



    //-------------------------------------gettery----------------------------------------//

    public static double[] getResolution(){return new double[]{width, height};}

    public static String getProgramName(){return programName;}

    public static Iterator<String> getDirectories(){return Directories.iterator();}

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

    public static LinkedList<FileListCell> getFileBranch(){return fileBranch;}

    public static String getAlbumDirectory(){return currentAlbumDirPath;}

    public static MainScreen getController(){return mainscreenController;}

    public static ObservableList<Double>getSpeeds(){return speeds;}

    public static boolean isDirectoriesEmpty(){return Directories.isEmpty();}

    public static boolean isBranchesEmpty(){return fileBranch.isEmpty();}

    public static boolean isQueueEmpty(){return queue.isEmpty();}

    public static int getBranchSize(){return fileBranch.size();}

    public static MediaController getMediaController(){return mediaController;}

    //-------------------------------------settery----------------------------------------//

    public static void setAlbumPageList(AnchorPane pane){albumPageList=pane;}

    public static void setAlbumPageView(AnchorPane pane){albumPageView=pane;}

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

    public static void setAlbumDir(String filepath){
        currentAlbumDirPath=filepath.isEmpty()?defaultAlbumDirPath:filepath;
    }

    public static void setProgramName(String name){programName=name;}

    public static void addFileToBranch(FileListCell cell){
        fileBranch.add(cell);
        librarypage.initBranchOptions();
    }

    public static void deleteFileFromBranch(FileListCell cell){
        fileBranch.remove(cell);
        if(queue.contains(cell.getFilepath()))
        {
            queue.remove(cell.getFilepath());
            queuepage.updateQueueView();
        }
        librarypage.initBranchOptions();
    }

    public static void addPage(AnchorPane page){
        MainPages.add(page);
    }

    public static void setFoldersList(ObservableList<String> Folders){
        clearDirectories();
        Directories.addAll(Folders);
    }

    public static String initTime(int trackLength)
    {
        if(trackLength!=0)
        {
            int minutes = trackLength / 60;
            int seconds = trackLength % 60;
            return String.format("%02d:%02d", minutes, seconds);
        }
        return String.format("%02d:%02d", 0, 0);
    }

    //------------------------------operacje do programu------------------------------------//

    public static void addAlbumWithFiles(String name){
        albumpage.addNewAlbum(name,fileBranch);
    }

    public static void addFilesToAlbum(String name, LinkedList<FileListCell> list){

        AlbumComponent component=albumpage.getComponent(name);

        if(component !=null){

            component.copyFilesToAlbum(list);
        }
    }

    public static void clearBranch(){
        for (FileListCell branch : fileBranch) {
            branch.checkCell(false);
        }
        fileBranch.clear();
        librarypage.initBranchOptions();
    }

    public static void clearQueue(){
        queue.clear();
    }

    public static void clearDirectories(){
        Directories.clear();
    }

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

    public static void deleteFolder(String name, String path){
        int index=Directories.indexOf(name+": "+path);
        System.out.println("Znaleziony indeks: "+index);
        Directories.remove(name+": "+path);
        librarypage.deleteFolder(index);
        settingspage.refreshFolderList();
    }

    public static void changeFolderName(String name, String path, String newName)
    {
        int index=Directories.indexOf(name+": "+path);
        Directories.set(index, newName+": "+path);
        settingspage.refreshFolderList();
    }

    public static void setChanges(){
        Stage stage=mainscreenController.getStage();
        double[] resolution=getResolution();
        initTheme(stage);
        stage.setWidth(resolution[0]);
        stage.setHeight(resolution[1]);
        if(mediaPlayerStage != null) initTheme(mediaPlayerStage);
        mainscreenController.setProgramName(getProgramName());
        librarypage.setLoaded(false);
        albumpage.setLoaded(false);

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

    public static void initBranch(){
        clearQueue();
        for (FileListCell branch : fileBranch) {
            queue.add(branch.getFilepath());
        }
        queuepage.updateQueueView();
        openMediaPlayerScene(0);
        clearBranch();
    }

    public static void appendQueue(){
        for(FileListCell branch: fileBranch){
            String path=branch.getFilepath();
            addFileToQueue(path);
        }
        queuepage.updateQueueView();
        clearBranch();
        if(mediaController !=null) mediaController.manageQueueButtons();
    }

    public static void initiateAlbum(LinkedList<String> list){
        clearQueue();
        for (String file:list){
            addFileToQueue(file.split(": ")[1]);
        }
        queuepage.updateQueueView();
        openMediaPlayerScene(0);
        if(mediaPlayerStage.isShowing()) mediaController.manageQueueButtons();
    }

    public static List<String> readAlbumFolder(){
        File albumObtainator=new File(currentAlbumDirPath);
        List<String> list=new LinkedList<>();
        if(albumObtainator.exists() && albumObtainator.isDirectory())
        {
            list.addAll(List.of(Objects.requireNonNull(albumObtainator.list())));
        }
        return list;
    }

    public static void deleteAlbum(String name){
        int index=readAlbumFolder().indexOf(name);
        albumpage.removeAlbum(index);
    }

    public static void openSubPage(AlbumComponent component){
        mainscreenController.subPaged=true;
        albumsubpage.setAlbumName(component.getAlbumName());
        albumsubpage.setCover(component.getCover());
        albumsubpage.setList(component.getFiles());
        mainscreenController.toAlbumPage();
    }

    public static void closeSubPage(){
        mainscreenController.subPaged=false;
        mainscreenController.toAlbumPage();
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
            result.write("Albums: "+currentAlbumDirPath+"\n");
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
                if(s.contains("Directories:{")|| s.isEmpty()) continue;
                Directories.add(s);
            }
            else {break;}
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
            currentAlbumDirPath=it.next().split(": ")[1];
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
        currentAlbumDirPath=defaultAlbumDirPath;

    }


}
