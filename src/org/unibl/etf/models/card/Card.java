package org.unibl.etf.models.card;


import javafx.scene.image.Image;

public abstract class Card {
    protected Image image;

    protected Card(Image image){
        this.image=image;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image=image;
    }
}
