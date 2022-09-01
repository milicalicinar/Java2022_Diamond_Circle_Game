package org.unibl.etf.models.game;

import javafx.application.Platform;
import org.unibl.etf.Main;
import org.unibl.etf.controller.MainController;
import org.unibl.etf.models.card.Card;
import org.unibl.etf.models.util.Generator;

public class Game extends Thread {
    MainController mainController;

    public Game(MainController mainController) {
        this.mainController = mainController;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            Card c = Generator.cards.remove();
            Platform.runLater(() -> {
                mainController.cardImage.setImage(c.getImage());
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                //todo upisati u log
            }
            Generator.cards.add(c);
        }

    }
}
