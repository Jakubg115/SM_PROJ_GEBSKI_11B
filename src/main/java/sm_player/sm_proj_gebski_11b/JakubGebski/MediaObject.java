package sm_player.sm_proj_gebski_11b.JakubGebski;
import javafx.scene.media.*;

import java.io.File;
import java.util.Objects;


public class MediaObject {
    public Media media;


    public MediaObject(String filename)
    {
        try
        {
            String path= Objects.requireNonNull(getClass().getResource(filename)).getPath();
            System.out.println(path+"\n");
            media=new Media(new File(path).toURI().toString());

        }catch (NullPointerException e)
        {
            System.out.println("Sciezka do tego pliku nie istnieje albo nie jest on osiagalny!\n");
        }
    }

    public void setFile(String filename)
    {
        try
        {
            String path= Objects.requireNonNull(getClass().getResource(filename)).getPath();
            media=new Media(new File(path).toURI().toString());
        }catch (NullPointerException e)
        {
            System.out.println("Sciezka do tego pliku nie istnieje albo nie jest on osiagalny!\n");
        }


    }





}
