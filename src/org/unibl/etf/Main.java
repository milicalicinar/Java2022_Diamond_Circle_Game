package org.unibl.etf;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main extends Application {
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;
    public static Handler handler;
    public final static Logger LOGGER=Logger.getLogger(Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        try{
            handler=new FileHandler("application.log", true);
            LOGGER.addHandler(handler);
            Parent root = FXMLLoader.load(getClass().getResource("resources/views/sample.fxml"));
            primaryStage.setTitle("Java Project");
            primaryStage.setScene(new Scene(root,
                    WIDTH,
                    HEIGHT));
            primaryStage.show();
        }catch(IOException ex){
            LOGGER.log(Level.INFO, ex.toString(), ex);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
