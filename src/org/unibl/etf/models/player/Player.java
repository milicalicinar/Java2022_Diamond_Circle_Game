package org.unibl.etf.models.player;

import org.unibl.etf.models.pawn.Pawn;

import java.util.ArrayList;
import java.util.LinkedList;

public class Player {
    private String name;
    private LinkedList<Pawn> playerPawns = new LinkedList<>();

    public Player(String name, LinkedList<Pawn> playerPawns){
        this.name=name;
        this.playerPawns=playerPawns;
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

    public void setPlayerPawns(LinkedList<Pawn> playerPawns) {
        this.playerPawns = playerPawns;
    }
}
