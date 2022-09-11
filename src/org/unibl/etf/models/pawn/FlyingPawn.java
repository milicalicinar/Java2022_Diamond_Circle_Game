package org.unibl.etf.models.pawn;

import javafx.scene.image.Image;

public class FlyingPawn extends Pawn implements Flyable{
    public FlyingPawn(Image image, String color){
        super(image);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Flying Pawn";
    }
}
