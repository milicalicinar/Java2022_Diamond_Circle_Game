package org.unibl.etf.models.tile;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import org.unibl.etf.models.util.ReadConfig;

public class Tile extends StackPane {
    private Boolean busy;
    private ImageView image = new ImageView();
    private int numofDiamonds = 0;

    public Tile(Boolean busy, GridPane grid){
        this.getChildren().add(image);
        this.busy = busy;

        image.fitWidthProperty().bind((grid.widthProperty().divide(grid.getColumnCount())));
        image.fitHeightProperty().bind(grid.heightProperty().divide(grid.getRowCount()));
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setImage(Image image) {
        if(image != null){
            busy = true;
        }else{
            busy = false;
        }
        Platform.runLater(() -> {
            this.image.setImage(image);
        });
    }

    public void setDiamondImage(Image image){
        Platform.runLater(() -> {
            this.image.setImage(image);
        });
    }

    public ImageView getImage() {
        return image;
    }

    public int getNumofDiamonds() {
        return numofDiamonds;
    }

    public void setNumofDiamonds(int numofDiamonds) {
        this.numofDiamonds = numofDiamonds;
    }

    @Override
    public String toString() {
        return (GridPane.getRowIndex(this) * ReadConfig.gridSize + GridPane.getColumnIndex(this) + "");
    }
}
