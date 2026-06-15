package sm_player.sm_proj_gebski_11b.Components;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import sm_player.sm_proj_gebski_11b.Controllers.FileListCellController;

public class FileListCell extends AnchorPane {

    private final FileListCellController pointer;
    private final String filepath;

    private FolderView foldercontainer;

    public String getFilepath(){return this.filepath;}

    public FileListCell(String name, String relpath){
        FXMLLoader loader=new FXMLLoader(getClass().getResource("/sm_player/sm_proj_gebski_11b/FileListCellComponent.fxml"));

        try {
            loader.setRoot(this);
            loader.load();
            this.pointer=loader.getController();
            this.pointer.set(name);
            this.pointer.obtainComponent(this);
            this.filepath=relpath+"\\"+name;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void giveFolderPointer(FolderView pointer){this.foldercontainer=pointer;}

    public String getFileName(){return this.pointer.getFileName();}

    public void manageThisFile(boolean state){
        switch (state){
            case true:
                this.foldercontainer.addFileToBranch(this);
                break;
                case false:
                    this.foldercontainer.deleteFileFromBranch(this);
                    break;
        }
    }

    public void checkCell(boolean flag){this.pointer.markCell(flag);}
}
