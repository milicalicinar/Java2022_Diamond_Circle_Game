package org.unibl.etf.models.pawn;

import javafx.scene.image.Image;
import org.unibl.etf.models.tile.Tile;
import org.unibl.etf.models.util.ReadConfig;

import java.util.LinkedList;

public abstract class Pawn {
    protected Image image;

    private boolean started = false;
    private int upperLimit = ReadConfig.gridSize - 1;
    private int lowerLimit = 0;
    private int x = 0;
    private int y =  ReadConfig.gridSize / 2;
    private boolean maxX = false;
    private boolean maxY = false;
    protected boolean cameToEnd = false;

    private final LinkedList<Tile> historyOfMovement = new LinkedList<>();

    protected String color;

    public Pawn(Image image){
        this.image=image;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getLowerLimit() {
        return lowerLimit;
    }

    public void setLowerLimit(int lowerLimit) {
        this.lowerLimit = lowerLimit;
    }

    public void setUpperLimit(int upperLimit) {
        this.upperLimit = upperLimit;
    }

    public int getUpperLimit() {
        return upperLimit;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }



    public LinkedList<Tile> getHistoryOfMovement() {
        return historyOfMovement;
    }
    public void addToHistoryOfMovement(Tile tile){
        historyOfMovement.add(tile);
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public boolean isMaxX() {
        return maxX;
    }

    public void setMaxX(boolean maxX) {
        this.maxX = maxX;
    }

    public boolean isMaxY() {
        return maxY;
    }

    public void setMaxY(boolean maxY) {
        this.maxY = maxY;
    }

    @Override
    public String toString() {
        return "Pawn";
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setCameToEnd(boolean cameToEnd){
        this.cameToEnd = cameToEnd;
    }

    public boolean isCameToEnd() {
        return cameToEnd;
    }

    public String writeResults(){
        return "(" + this.toString() + " " + this.color+ ")" + " - pređeni put " + this.historyOfMovement.toString() + " - stigla do cilja - " + (cameToEnd?"da":"ne");
    }
}
