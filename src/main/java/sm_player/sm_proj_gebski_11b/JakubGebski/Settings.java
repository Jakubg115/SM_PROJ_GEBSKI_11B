package sm_player.sm_proj_gebski_11b.JakubGebski;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;
import sm_player.sm_proj_gebski_11b.Controllers.MediaController;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Settings implements StaticObjects {

    private static final List<String> Directories=new LinkedList<>();


    private static final String configDirPath= "config.txt";

    private static String programName;
    private static String currentTheme;
    private static String mainDirPath="";
    private static double width,height;

    private static Stage mediaPlayerStage;



    //-------------------------------------gettery----------------------------------------//

    public static double[] getResolution(){return new double[]{width, height};}

    public static String getProgramName(){return programName;}

    public static Iterator<String> getDirectories(){return Directories.iterator();}

    public static List<String> getActiveQueue(){return queue;}


    //-------------------------------------settery----------------------------------------//

    public static void initTheme(@NotNull Stage stage){
        stage.getScene().getStylesheets().add(Settings.class.getResource("/styles/"+currentTheme+".css").toExternalForm());
    }

    public static void setTheme(ThemeModes mode){
        currentTheme= mode.toString();
    }

    public static void changeMainDir(File newMainDir){
        try {
            if(newMainDir.exists()){
                mainDirPath= newMainDir.getAbsolutePath();
            }
            else {throw new Exception();}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setResolution(double x, double y){
        width=x;
        height=y;
    }

    //------------------------------operacje do programu------------------------------------//

    public static int addFileToQueue(String filePath){
        if(!queue.contains(filePath)){
            queue.add(filePath);
        }
        return queue.indexOf(filePath);
    }

    public static void readQueue(){
        for (String s : queue) {
            System.out.println(s);
        }
    }

    public static void openMediaPlayerScene(int index){
        try {
            FXMLLoader loader = new FXMLLoader(Settings.class.getResource("/sm_player/sm_proj_gebski_11b/MusicPlayerScene.fxml"));
            if(!mediaPlayerStage.isShowing()){
                Parent root = loader.load();
                mediaPlayerStage.setScene(new Scene(root));
                mediaPlayerStage.setTitle("Odtwarzacz Muzyki");

            }
            mediaPlayerStage.show();
            initTheme(mediaPlayerStage);
            MediaController controller = loader.getController();
            controller.copyStage(mediaPlayerStage);
            controller.Start(index);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //------------------------------Zapis i odczyt pliku------------------------------------//

    private static void writeMainDirPath(FileWriter file){
        String defaultMusicPath = "src/main/resources/music";
        String mainDir="MainDir: "+(mainDirPath.isEmpty()? defaultMusicPath :mainDirPath)+"\n";
        try {
            file.append(mainDir);
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
            writeMainDirPath(result);
            writeResolution(result);
            writeCurrentTheme(result);
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

    }

    public static void readSettings(){
        try {
            FileReader reader=new FileReader(configDirPath);
            List<String> readed=  reader.readAllLines();
            Iterator<String> it=readed.iterator();

            programName=it.next().split(": ")[1];
            width=Double.parseDouble(it.next().split(": ")[1]);
            height=Double.parseDouble(it.next().split(": ")[1]);
            currentTheme=it.next().split(": ")[1];
            lookForFolders(it);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
