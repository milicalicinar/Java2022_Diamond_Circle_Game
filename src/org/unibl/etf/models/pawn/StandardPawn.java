package org.unibl.etf.models.pawn;

import javafx.scene.image.Image;

public class StandardPawn extends Pawn{
    public StandardPawn(Image image, String color){
        super(image);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Standard Pawn";
    }
}
