package org.unibl.etf.models.timer;

import javafx.application.Platform;
import org.unibl.etf.Main;
import org.unibl.etf.controllers.MainController;

import java.util.logging.Level;

public class Timer extends Thread{
    MainController mainController;
    public static int time = 1;

    public Timer(MainController mainController){
        this.mainController = mainController;
        setDaemon(true);
    }

    @Override
    public void run() {

        while(!mainController.endGame.get()){
            Platform.runLater(() -> {
                mainController.gameDurationLbl.setText("Vrijeme trajanja igre: " + time);
            });
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Main.LOGGER.log(Level.INFO, e.toString(), e);
            }
            time++;

        }
    }
}
