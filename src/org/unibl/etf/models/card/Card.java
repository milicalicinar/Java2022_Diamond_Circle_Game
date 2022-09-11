package org.unibl.etf.models.card;


import javafx.scene.image.Image;

public abstract class Card {
    protected Image image;
    protected int numOfFields;

    protected Card(Image image, int numOfFields){
        this.image=image;
        this.numOfFields=numOfFields;
    }

    public Image getImage(){
        return image;
    }

    public void setImage(Image image){
        this.image=image;
    }

    public int getNumOfFields() {
        return numOfFields;
    }

    public void setNumOfFields(int numOfFields) {
        this.numOfFields = numOfFields;
    }
}
