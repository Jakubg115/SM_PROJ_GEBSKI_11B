package sm_player.sm_proj_gebski_11b.JakubGebski;

import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.List;

public class Settings {

    private static LinkedList<String> queue=new LinkedList<>();
    private static final String configDirPath="src/main/resources/config.txt";

    private static String currentTheme;
    private static String mainDirPath="";
    private static double width,height;

    public static void initTheme(@NotNull Stage stage){
        stage.getScene().getStylesheets().add(Settings.class.getResource("/styles/"+currentTheme+".css").toExternalForm());
    }

    public static void setTheme(@NotNull Stage stage, ThemeModes mode){
        stage.getScene().getStylesheets().add(Settings.class.getResource("/styles/"+mode+".css").toExternalForm());
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
    public static String getMainDirPath(){return mainDirPath;}

    public static void setResolution(double x, double y){
        width=x;
        height=y;
    }
    public static double[] getResolution(){return new double[]{width, height};}

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

    private static void readMainDirPath(String str){
        mainDirPath=str.split(" ")[1];
    }
    private static void readResolution(String str1, String str2){
        width= Double.parseDouble(str1.split(" ")[1]);
        height=Double.parseDouble(str2.split(" ")[1]);
    }

    private static void readTheme(String str){
        currentTheme=str.split(" ")[1];
    }

    public static void readSettings(){
        try {
            FileReader reader=new FileReader(configDirPath);
            List<String> readed=  reader.readAllLines();
            readMainDirPath(readed.get(0));
            readResolution(readed.get(1),readed.get(2));
            readTheme(readed.get(3));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
