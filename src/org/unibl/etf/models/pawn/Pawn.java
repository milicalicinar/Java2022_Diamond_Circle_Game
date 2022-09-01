package org.unibl.etf.models.pawn;

import javafx.scene.image.Image;

public abstract class Pawn {
    protected Image image;

    public Pawn(Image image){
        this.image=image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
