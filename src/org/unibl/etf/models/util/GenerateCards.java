package org.unibl.etf.models.util;

import javafx.scene.image.Image;
import org.unibl.etf.Main;
import org.unibl.etf.models.card.Card;
import org.unibl.etf.models.card.RegularCard;
import org.unibl.etf.models.card.SpecialCard;


import java.util.LinkedList;
import java.util.Random;

public class GenerateCards {
    private static final int MAX_NUM_OF_CARDS=52;
    private static final int NUM_OF_REGULAR_CARDS=10;
    private static final int ONE_FIELD=1;
    private static final int TWO_FIELDS=2;
    private static final int THREE_FIELDS=3;
    private static final int FOUR_FIELDS=4;
    private static final int MAX_HOLES_ON_GRID=12;
    public static LinkedList<Card> cards=new LinkedList<>();

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
    }
}
