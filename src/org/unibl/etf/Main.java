package org.unibl.etf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.unibl.etf.exceptions.ReadConfigException;
import org.unibl.etf.models.util.ReadConfig;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    public static Handler handler;
    public final static Logger LOGGER=Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            handler=new FileHandler("application.log", true);
            LOGGER.addHandler(handler);
            ReadConfig.readConfig();
            Parent root = FXMLLoader.load(getClass().getResource("resources/views/sample.fxml"));
            primaryStage.setTitle("Java Project 2022");
            primaryStage.setScene(new Scene(root,
                    WIDTH,
                    HEIGHT));
            primaryStage.show();
        }catch(ReadConfigException ex){
            LOGGER.log(Level.INFO, ex.toString(), ex);
            Parent root = FXMLLoader.load(getClass().getResource("resources/views/error.fxml"));
            primaryStage.setScene(new Scene(root,
                    WIDTH,
                    HEIGHT));
            primaryStage.show();
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
