package org.unibl.etf.models.tile;

import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

public class Tile extends StackPane {
    private Boolean busy;
    private ImageView image;

    public Tile(Boolean busy){
        this.busy = busy;
    }

    public void setBusy(Boolean busy) {
        this.busy = busy;
    }

    public Boolean getBusy() {
        return busy;
    }

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }
}
