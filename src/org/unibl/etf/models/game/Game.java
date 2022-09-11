package org.unibl.etf.models.game;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import org.unibl.etf.Main;
import org.unibl.etf.controllers.MainController;
import org.unibl.etf.models.card.Card;
import org.unibl.etf.models.card.ISpecial;
import org.unibl.etf.models.pawn.Flyable;
import org.unibl.etf.models.pawn.IFast;
import org.unibl.etf.models.pawn.Pawn;
import org.unibl.etf.models.player.Player;
import org.unibl.etf.models.tile.Tile;
import org.unibl.etf.models.timer.Timer;
import org.unibl.etf.models.util.Generator;
import org.unibl.etf.models.util.ReadConfig;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game extends Thread {
    MainController mainController;
    private static final int SLEEP_TIME = 1000;
    private static final int SLEEP_TIME_PAUSE = 100;


    public Game(MainController mainController) {
        this.mainController = mainController;
        setDaemon(true);
    }

    @Override
    public void run() {

        LinkedList<Player> tempPlayers = new LinkedList<>(Generator.players);

        pawnMovement();

        mainController.endGame.set(true);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss");
        LocalDateTime now = LocalDateTime.now();
        Path newFilePath = Paths.get("results"+ File.separator + "IGRA_" + dtf.format(now)+".txt");
        try {
            Files.createFile(newFilePath);
            try(BufferedWriter writer = Files.newBufferedWriter(newFilePath, Charset.forName("UTF-8"))){
                for(int i=0; i<tempPlayers.size(); i++){
                    writer.write("Igrač " + (i+1) + " - " + tempPlayers.get(i).getName());
                    for(int j=i * 4; j<i * 4 + 4; j++){
                        writer.write("\n\t Figura " + (j+1) + "(" + MainController.pawns.get(j).writeResults() + ")");
                    }
                    writer.write("\n");
                }
                writer.write("\n Ukupno vrijeme trajanja igre: " + Timer.time + "s");

            }catch(IOException ex){
                Main.LOGGER.log(Level.INFO, ex.toString(), ex);
            }

        } catch (IOException e) {
            Main.LOGGER.log(Level.INFO, e.toString(), e);
        }

        mainController.NUM_OF_GAMES=mainController.directory.list().length;
        Platform.runLater(() ->{
            mainController.lblNumOfGames.setText(String.valueOf(mainController.NUM_OF_GAMES));
        });


    }

    public void pawnMovement(){

        while (Generator.players.size() > 0) {

            while (mainController.pause.get()) {
                try {
                    Thread.sleep(SLEEP_TIME_PAUSE);
                } catch (InterruptedException e) {
                    Main.LOGGER.log(Level.INFO, e.toString(), e);
                }
            }
            Card c = Generator.cards.remove();
            Platform.runLater(() -> {
                mainController.cardImage.setImage(c.getImage());
            });


            if (c instanceof ISpecial) {
                Random random = new Random();
                Player ptemp = Generator.players.remove();
                Platform.runLater(() ->{
                    mainController.cardMeaning.setText("Izvučena je specijalna karta, na potezu je igrač " + ptemp.getName());
                });
                Generator.players.add(ptemp);
                Paint paint = Color.BLACK;
                ArrayList<Integer> holesIndex = new ArrayList<>();
                //adding holes on grid
                for(int i=0; i<c.getNumOfFields(); i++){
                    int holeIndex = random.nextInt(ReadConfig.gridSize * ReadConfig.gridSize);
                    Tile holeTile = mainController.tiles.get(holeIndex);
                    holeTile.setBackground(new Background(new BackgroundFill(paint, CornerRadii.EMPTY, Insets.EMPTY)));
                    holesIndex.add(holeIndex);
                }

                try {
                    Thread.sleep(SLEEP_TIME);
                } catch (InterruptedException e) {
                    Main.LOGGER.log(Level.INFO, e.toString(), e);
                }

                LinkedList<Player> tempPlayers =new LinkedList<>(Generator.players) ;
                for(Player p : Generator.players){
                    Pawn pawn = p.getPlayerPawns().getFirst();

                    if(!(pawn instanceof Flyable)){
                        for(Integer index : holesIndex){
                            if((pawn.getX() * ReadConfig.gridSize + pawn.getY()) == index && pawn.isStarted()){
                                mainController.tiles.get(pawn.getX() * ReadConfig.gridSize + pawn.getY()).setImage(null);
                                p.getPlayerPawns().remove(pawn);
                            }
                        }

                    }
                    if(p.getPlayerPawns().size() == 0){
                        tempPlayers.remove(p);
                    }
                }
                Generator.players = tempPlayers;

                //removing holes from grid
                Paint paintWhite = Color.TRANSPARENT;
                for(Integer index : holesIndex){
                    mainController.tiles.get(index).setBackground(new Background(new BackgroundFill(paintWhite, CornerRadii.EMPTY, Insets.EMPTY)));
                }

            } else {
                Player player = Generator.players.remove();
                Pawn playerPawn = player.getPlayerPawns().remove();

                int fieldsToTraverse;
                if(playerPawn instanceof IFast){
                    fieldsToTraverse = 2 * c.getNumOfFields();
                }else{
                    fieldsToTraverse = c.getNumOfFields();
                }
                boolean finished = false;
                int diamondsCounter = 0;
                while(fieldsToTraverse + diamondsCounter > 0){
                    int sum = fieldsToTraverse + diamondsCounter;
                    Platform.runLater(() ->{
                        mainController.cardMeaning.setText("Na potezu je igrač " + player.getName() + ",prelazi " + sum);
                    });
                    if (!playerPawn.isStarted()) {
                        playerPawn.setStarted(true);
                        fieldsToTraverse--;
                        playerPawn.addToHistoryOfMovement(mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()));
                        if(mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).getBusy())
                            continue;
                        mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).setImage(playerPawn.getImage());


                    }
                    else {
                        if(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY() == ReadConfig.gridSize/2){
                            if( playerPawn.getImage().equals(mainController.tiles.get(ReadConfig.gridSize/2).getImage().getImage()))
                                mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).setImage(null);
                        }
                        else{
                            mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).setImage(null);
                        }

                       do {
                           //checking limits so you can determine next position
                           if (playerPawn.getX() == playerPawn.getUpperLimit()) {
                               playerPawn.setMaxX(true);
                           }
                           if (playerPawn.getX() == playerPawn.getLowerLimit()) {
                               playerPawn.setMaxX(false);
                           }
                           if (playerPawn.getY() == playerPawn.getUpperLimit()) {
                               playerPawn.setMaxY(true);
                           }
                           if (playerPawn.getY() == playerPawn.getLowerLimit()) {
                               playerPawn.setMaxY(false);
                           }

                           int tempX = playerPawn.getX();
                           int tempY = playerPawn.getY();

                           if (playerPawn.isMaxX()) {
                               tempX--;
                           } else {
                               tempX++;
                           }

                           if (playerPawn.isMaxY()) {
                               tempY--;
                           } else {
                               tempY++;
                           }
                           Tile tempTile = mainController.tiles.get(tempX * ReadConfig.gridSize + tempY);
                           if (playerPawn.getHistoryOfMovement().contains(tempTile)) {
                               tempX++;
                               playerPawn.setUpperLimit(playerPawn.getUpperLimit() - 1);
                               playerPawn.setLowerLimit(playerPawn.getLowerLimit() + 1);
                           }
                           playerPawn.setX(tempX);
                           playerPawn.setY(tempY);
                           fieldsToTraverse--;
                           diamondsCounter += mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).getNumofDiamonds();
                           mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).setNumofDiamonds(0);
                           playerPawn.addToHistoryOfMovement(mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()));
                       }while( mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).getBusy());
                    }

                    while (mainController.pause.get()) {
                        try {
                            Thread.sleep(SLEEP_TIME_PAUSE);
                        } catch (InterruptedException e) {
                            Main.LOGGER.log(Level.INFO, e.toString(), e);
                        }
                    }

                    mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).setImage(playerPawn.getImage());

                    try {
                        Thread.sleep(SLEEP_TIME);
                    } catch (InterruptedException e) {
                        Main.LOGGER.log(Level.INFO, e.toString(), e);
                    }
                    if (playerPawn.getHistoryOfMovement().contains(mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY() + 1)) &&
                            playerPawn.getHistoryOfMovement().contains(mainController.tiles.get((playerPawn.getX() + 1) * ReadConfig.gridSize + playerPawn.getY() - 1)) &&
                            playerPawn.getHistoryOfMovement().contains(mainController.tiles.get((playerPawn.getX() - 1) * ReadConfig.gridSize + playerPawn.getY() - 1))
                    ) {
                        mainController.tiles.get(playerPawn.getX() * ReadConfig.gridSize + playerPawn.getY()).setImage(null);
                        finished = true;
                        break;
                    }
                }

                if(!finished){
                    player.addPlayerPawn(playerPawn);
                }else{
                    playerPawn.setCameToEnd(true);
                }


                if (player.getPlayerPawns().size() != 0) {
                    Generator.players.add(player);
                }

            }


            Generator.cards.add(c);

        }
    }
}
