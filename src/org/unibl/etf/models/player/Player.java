package org.unibl.etf.models.player;

import org.unibl.etf.models.pawn.Pawn;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private String name;
    private String color;
    private LinkedList<Pawn> playerPawns = new LinkedList<>();

    public Player(String name, LinkedList<Pawn> playerPawns, String color){
        this.name=name;
        this.playerPawns=playerPawns;
        this.color=color;
    }

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LinkedList<Pawn> getPlayerPawns() {
        return playerPawns;
    }

    public void addPlayerPawn(Pawn pawn) {
        this.playerPawns.addFirst(pawn);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setPlayerPawns(LinkedList<Pawn> playerPawns){
        this.playerPawns = playerPawns;
    }
}
