package org.unibl.etf.models.pawn;

import javafx.scene.image.Image;
import org.unibl.etf.Main;
import org.unibl.etf.controllers.MainController;
import org.unibl.etf.models.util.ReadConfig;

import java.util.Random;
import java.util.logging.Level;

public class GhostPawn extends Pawn implements Runnable{
    private int LOW_NUMBER_OF_DIAMONDS = 2;
    private MainController mainController;
    public GhostPawn(Image image, MainController mainController){
        super(image);
        this.mainController = mainController;
    }
    @Override
    public void run() {
        while (!mainController.endGame.get()) {
            Random random = new Random();
            boolean addedDiamond = false;
            int numberOfDiamonds = random.nextInt(ReadConfig.gridSize - LOW_NUMBER_OF_DIAMONDS) + LOW_NUMBER_OF_DIAMONDS;

            while (!addedDiamond) {
                int diamondTile = random.nextInt(ReadConfig.gridSize * ReadConfig.gridSize);
                if (!mainController.tiles.get(diamondTile).getBusy()) {
                    mainController.tiles.get(diamondTile).setDiamondImage(this.getImage());
                    mainController.tiles.get(diamondTile).setNumofDiamonds(numberOfDiamonds);
                    addedDiamond = true;
                }
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                Main.LOGGER.log(Level.INFO, e.toString(), e);
            }
        }
    }
}
