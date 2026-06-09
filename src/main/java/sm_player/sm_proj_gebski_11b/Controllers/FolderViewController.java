package sm_player.sm_proj_gebski_11b.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import sm_player.sm_proj_gebski_11b.Components.FileListCell;

public class FolderViewController {

    public AnchorPane mainFolderPanel;
    public Label folderName;
    public ListView<FileListCell> fileListView=new ListView<>();
    public Button fileOptions;
    public FontAwesomeIconView listEnter;

    private boolean opened=false;

    public void addFile(String name){
        this.fileListView.getItems().add(new FileListCell(name));
    }

    public void onOptionsClick(ActionEvent actionEvent) {
    }

    public void setList(MouseEvent event) {
        this.opened=!this.opened;
        this.fileListView.setPrefHeight(this.opened?200:5);
        this.fileListView.setVisible(this.opened);
        this.listEnter.setGlyphName(this.opened?"CARET_DOWN":"CARET_RIGHT");
    }


}
