package sm_player.sm_proj_gebski_11b.JakubGebski;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class PrerunClass implements StaticObjects {
    private static String currentpath;
    private ObservableList<Double> speeds = FXCollections.observableArrayList(0.25, 0.5, 0.75, 1.0, 1.25, 1.5, 1.75, 2.0);
    public String getCurrentpath(){return currentpath;}
    public ObservableList<Double> getSpeeds(){return speeds;}

    public List<String> loadMusicFiles(String path)  {
        queue.clear();
        List<String> list=new LinkedList<>();
        File folder = new File(path);
        try
        {
            if (folder.exists() && folder.isDirectory()) {
                list = Arrays.stream(Objects.requireNonNull(folder.list((dir, name) -> name.endsWith(".mp3") || name.endsWith(".m4a") || name.endsWith(".wav")))).collect(Collectors.toList());
                currentpath=path;
            } else throw new FileNotFoundException("Nieprawidlowa sciezka: "+path+"\n");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return list;
    }

    public void fillQueue(List<String> list)
    {
        queue.clear();
        try
        {
            if(list==null) throw new Exception("Lista ktora podales jest null!\n");
            if(list.isEmpty()) throw new Exception("Lista ktora podales jest pusta!\n");
            Iterator<String> it= list.iterator();
            while (it.hasNext())
            {
                queue.add(currentpath+"/"+it.next());
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
    public List<String> Shuffle(List<String> list)
    {
        Collections.shuffle(list);

        List<String> fileNames = new ArrayList<>();

        for (int i = 0; i < list.size(); i++) {
            String path = list.get(i); // Pobranie elementu z listy
            int lastSlashIndex = path.lastIndexOf("/");
            if (lastSlashIndex != -1) {
                fileNames.add(path.substring(lastSlashIndex + 1));
            }
        }
        return fileNames;
    }

    // Dla MediaPlayera

    public String initTime(int trackLength)
    {
        if(trackLength!=0)
        {
            int minutes = trackLength / 60;
            int seconds = trackLength % 60;
            return String.format("%02d:%02d", minutes, seconds);
        }
        return String.format("%02d:%02d", 0, 0);
    }
}
