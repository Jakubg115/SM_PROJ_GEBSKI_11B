package sm_player.sm_proj_gebski_11b.Controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import sm_player.sm_proj_gebski_11b.Components.FileListCell;
import sm_player.sm_proj_gebski_11b.Components.FolderView;
import sm_player.sm_proj_gebski_11b.JakubGebski.Settings;

import java.util.List;

public class FolderViewController {

    private FolderView copy;

    @FXML
    private HBox controllsBox;
    @FXML
    private Label folderName;
    @FXML
    private ListView<FileListCell> fileListView=new ListView<>();
    @FXML
    private FontAwesomeIconView listEnter;

    private boolean opened=false;

    public void cloneView(FolderView pointer){
        this.copy=pointer;
        this.fileListView.focusedProperty().addListener((_, _, _) -> {
            if(!fileListView.isFocused()){
                fileListView.getSelectionModel().clearSelection();
            }
        });
    }

    public void setFileName(String name){
        this.folderName.setText(name);
    }

    public void addFile(String name, String path){
        FileListCell cell=new FileListCell(name, path);
        cell.giveFolderPointer(this.copy);
        this.fileListView.getItems().add(cell);
    }

    public void setList() {
        this.opened=!this.opened;
        this.fileListView.setPrefHeight(this.opened?200:5);
        this.fileListView.setVisible(this.opened);
        this.listEnter.setGlyphName(this.opened?"CARET_DOWN":"CARET_RIGHT");
    }
    public void onListClick(MouseEvent e){
        if(e.getClickCount()==2){
            FileListCell cell=this.fileListView.getSelectionModel().getSelectedItem();
            if(cell !=null) copy.manageSelected(cell);
        }
    }

    public void changeFolderName(){
        TextField field=new TextField();
        field.setText(folderName.getText());
        this.controllsBox.getChildren().set(1,field);

        field.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Settings.changeFolderName(folderName.getText(),copy.getFolderPath(),field.getText());
                setFileName(field.getText());
                controllsBox.getChildren().set(1,folderName);
            }
        });
    }

    public void markEveryFile(){
        if(!opened) setList();
        for(FileListCell cell: fileListView.getItems()){
            cell.checkCell(true);
            cell.manageThisFile(true);
        }
    }

    public void deleteThisFolder(){
        Settings.deleteFolder(folderName.getText(), copy.getFolderPath());
    }

    public List<FileListCell> getFiles(){return fileListView.getItems();}

}
