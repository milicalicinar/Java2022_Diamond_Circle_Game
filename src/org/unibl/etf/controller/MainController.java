package org.unibl.etf.controller;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import org.unibl.etf.Main;
import org.unibl.etf.exceptions.ReadConfigException;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import org.unibl.etf.models.game.Game;
import org.unibl.etf.models.tile.Tile;
import org.unibl.etf.models.util.Generator;
import org.unibl.etf.models.util.ReadConfig;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class MainController implements Initializable {
    public GridPane grid;
    public Label lblNumOfGames;

    @FXML
    public Label lblPlayer1;
    @FXML
    public Label lblPlayer2;
    @FXML
    public Label lblPlayer3;
    @FXML
    public Label lblPlayer4;
    public ImageView cardImage;
    public int reduce;
    public final int MAX_GRID_SIZE=10;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Label> labels=new ArrayList<Label>(Arrays.asList(lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4));
        try{
            ReadConfig.readConfig();
            reduce=MAX_GRID_SIZE - ReadConfig.gridSize;
            for(int i=0; i<reduce; i++){
                grid.getColumnConstraints().remove(0);
                grid.getRowConstraints().remove(0);
            }
        }catch(ReadConfigException ex){
            Main.LOGGER.log(Level.INFO, ex.toString(), ex);
        }

        Generator.generatePlayers();
        for(int i=0; i<Generator.players.size(); i++){
            labels.get(i).setText(Generator.players.get(i).getName());
        }


        if(Generator.players.size() == ReadConfig.MIN_NUM_PLAYERS){
            lblPlayer3.setVisible(false);
            lblPlayer4.setVisible(false);
        }else if(Generator.players.size() == ReadConfig.MIN_NUM_PLAYERS + 1){
            lblPlayer4.setVisible(false);
        }

        Generator.generateCards();



    }

    public void startPauseGame(MouseEvent mouseEvent) {
        Game game = new Game(this);
        game.start();
    }
}
