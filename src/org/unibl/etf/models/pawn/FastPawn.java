package org.unibl.etf.models.pawn;

import javafx.scene.image.Image;

public class FastPawn extends Pawn implements IFast {

    public FastPawn(Image image, String color){
        super(image);
        this.color = color;
    }

    @Override
    public String toString() {
        return "Fast Pawn";
    }
}
