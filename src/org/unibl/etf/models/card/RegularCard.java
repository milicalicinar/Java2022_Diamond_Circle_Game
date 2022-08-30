package org.unibl.etf.models.card;


import javafx.scene.image.Image;

public class RegularCard extends Card{
    protected int numOfFields;

    public  RegularCard(Image image, int numOfFields){
        super(image);
        this.numOfFields=numOfFields;
    }
    public int getNumOfFields(){
        return numOfFields;
    }
    public void setNumOfFields(int numOfFields){
        this.numOfFields=numOfFields;
    }
}
