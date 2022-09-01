package org.unibl.etf.models.util;

import javafx.scene.image.Image;
import org.unibl.etf.Main;
import org.unibl.etf.models.card.Card;
import org.unibl.etf.models.card.RegularCard;
import org.unibl.etf.models.card.SpecialCard;
import org.unibl.etf.models.pawn.FastPawn;
import org.unibl.etf.models.pawn.FlyingPawn;
import org.unibl.etf.models.pawn.Pawn;
import org.unibl.etf.models.pawn.StandardPawn;
import org.unibl.etf.models.player.Player;


import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class Generator {
    private static final int MAX_NUM_OF_CARDS=52;
    private static final int NUM_OF_REGULAR_CARDS=10;
    private static final int NUM_OF_PLAYER_PAWNS=4;
    private static final int ONE_FIELD=1;
    private static final int TWO_FIELDS=2;
    private static final int THREE_FIELDS=3;
    private static final int FOUR_FIELDS=4;
    private static final int MAX_HOLES_ON_GRID=12;
    public static LinkedList<Card> cards=new LinkedList<>();
    public static LinkedList<Player> players = new LinkedList<>();

    public static void generateCards(){
        Random random = new Random();

        for(int i=0; i<MAX_NUM_OF_CARDS; i++){
            if(i < NUM_OF_REGULAR_CARDS){
                cards.add(new RegularCard(new Image(String.valueOf(Main.class.getResource("resources/img/cards/1.png"))), ONE_FIELD));
            }else if(i < TWO_FIELDS * NUM_OF_REGULAR_CARDS){
                cards.add(new RegularCard(new Image(String.valueOf(Main.class.getResource("resources/img/cards/2.png"))), TWO_FIELDS));
            }else if(i < THREE_FIELDS  * NUM_OF_REGULAR_CARDS){
                cards.add(new RegularCard(new Image(String.valueOf(Main.class.getResource("resources/img/cards/3.png"))), THREE_FIELDS));
            }else if(i < FOUR_FIELDS * NUM_OF_REGULAR_CARDS){
                cards.add(new RegularCard(new Image(String.valueOf(Main.class.getResource("resources/img/cards/4.png"))), FOUR_FIELDS));
            }else{
                cards.add(new SpecialCard(new Image(String.valueOf(Main.class.getResource("resources/img/cards/5.png"))),random.nextInt(MAX_HOLES_ON_GRID) +1));
            }
        }
        Collections.shuffle(cards);
    }

    public static void generatePlayers(){
        for(int i=0; i<ReadConfig.players.size(); i++){
            LinkedList<Pawn> playerPawns = generatePawns(i % ReadConfig.players.size());
            players.add(new Player(ReadConfig.players.get(i), playerPawns));

        }
        Collections.shuffle(players);
    }

    public static LinkedList<Pawn> generatePawns(int colorCode){
        LinkedList<Pawn> pawns;
        if(colorCode == 0){

            pawns = generateColoredPawns("RED");

        }else if(colorCode == 1){

            pawns = generateColoredPawns("YELLOW");

        }else if(colorCode == 2){


            pawns = generateColoredPawns("BLUE");

        }else{

            pawns = generateColoredPawns("GREEN");

        }
        return pawns;
    }

    private static LinkedList<Pawn> generateColoredPawns(String color){
        LinkedList<Pawn> pawns = new LinkedList<>();
        Random random = new Random();
        for(int i=0; i<NUM_OF_PLAYER_PAWNS; i++){
            int temp = random.nextInt(3);
            if(temp == 0){
                pawns.add(new StandardPawn(new Image(String.valueOf(Main.class.getResource("resources/img/pawns/"+ color + ".png")))));
            }else if(temp == 1){
                pawns.add(new FlyingPawn(new Image(String.valueOf(Main.class.getResource("resources/img/ballons/" + color + ".png")))));
            }else if(temp == 2){
                pawns.add(new FastPawn(new Image(String.valueOf(Main.class.getResource("resources/img/cars/" + color + ".png")))));
            }
        }
        return pawns;
    }
}
