package org.unibl.etf.models.card;


import javafx.scene.image.Image;

public class SpecialCard extends Card{
    protected int numOfHoles;

    public SpecialCard(Image image, int numOfHoles){
        super(image);
        this.numOfHoles=numOfHoles;
    }

    public int getNumOfHoles() {
        return numOfHoles;
    }

    public void setNumOfHoles(int numOfHoles){
        this.numOfHoles=numOfHoles;
    }
}
