package org.unibl.etf.controllers;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.unibl.etf.models.pawn.Pawn;
import org.unibl.etf.models.tile.Tile;
import org.unibl.etf.models.util.ReadConfig;

public class PawnController {
    public Label pawnLbl;
    public GridPane gridPawn;
    private Pawn selectedPawn;
    public final int MAX_GRID_SIZE=10;

    public Pawn getSelectedPawn() {
        return selectedPawn;
    }

    public void setSelectedPawn(Pawn selectedPawn) {
        this.selectedPawn = selectedPawn;
    }

    public void display(){
        Paint color = Color.valueOf(selectedPawn.getColor());
        pawnLbl.setText(selectedPawn.toString());
        pawnLbl.setTextFill(color);
        int reduceGrid = MAX_GRID_SIZE - ReadConfig.gridSize;
        for(int i=0; i<reduceGrid; i++){
            gridPawn.getColumnConstraints().remove(0);
            gridPawn.getRowConstraints().remove(0);
        }

        //adding fields on grid that pawn went over and coloring that fields
        for(Tile tile: selectedPawn.getHistoryOfMovement()){
            Paint colorPawn = Color.valueOf(selectedPawn.getColor());
            StackPane pane = new StackPane();
            pane.setBackground(new Background(new BackgroundFill(colorPawn, CornerRadii.EMPTY, Insets.EMPTY)));
            pane.setBorder(new Border(new BorderStroke(Color.BLACK,
                    BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            int x = GridPane.getRowIndex(tile);
            int y = GridPane.getColumnIndex(tile);
            gridPawn.add(pane, y, x);
        }

    }
}
