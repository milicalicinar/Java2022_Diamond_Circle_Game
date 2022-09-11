package org.unibl.etf.controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.unibl.etf.Main;
import org.unibl.etf.controllers.PawnController;
import org.unibl.etf.exceptions.ReadConfigException;
import org.unibl.etf.models.game.Game;
import org.unibl.etf.models.pawn.GhostPawn;
import org.unibl.etf.models.pawn.Pawn;
import org.unibl.etf.models.tile.Tile;
import org.unibl.etf.models.timer.Timer;
import org.unibl.etf.models.util.Generator;
import org.unibl.etf.models.util.ReadConfig;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;

public class MainController implements Initializable {
    public GridPane grid;
    public Label lblNumOfGames;
    public int NUM_OF_GAMES;
    public File directory=new File("results");


    public AtomicBoolean pause = new AtomicBoolean(false);
    public AtomicBoolean endGame = new AtomicBoolean(false);
    public AtomicInteger gameDuration = new AtomicInteger();
    public boolean started = false;
    @FXML
    public Label lblPlayer1;
    @FXML
    public Label lblPlayer2;
    @FXML
    public Label lblPlayer3;
    @FXML
    public Label lblPlayer4;

    public Label gameDurationLbl;
    public ImageView cardImage;
    public Label cardMeaning;
    public int reduce;
    public final int MAX_GRID_SIZE=10;
    public final HashMap<Integer, Tile> tiles = new HashMap<>();
    public ListView<Pawn> listView;

    public final static LinkedList<Pawn> pawns = new LinkedList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        NUM_OF_GAMES=directory.list().length;
        lblNumOfGames.setText(String.valueOf(NUM_OF_GAMES));
        ArrayList<Label> labels=new ArrayList<Label>(Arrays.asList(lblPlayer1, lblPlayer2, lblPlayer3, lblPlayer4));

        reduce=MAX_GRID_SIZE - ReadConfig.gridSize;
            for(int i=0; i<reduce; i++){
                grid.getColumnConstraints().remove(0);
                grid.getRowConstraints().remove(0);
            }


        Generator.generatePlayers();
        for(int i=0; i<Generator.players.size(); i++){
            labels.get(i).setText(Generator.players.get(i).getName());
            Paint color = Color.valueOf(Generator.players.get(i).getColor());
            pawns.addAll(Generator.players.get(i).getPlayerPawns());
            labels.get(i).setTextFill(color);
            labels.get(i).setStyle("-fx-font-weight: bold");
        }



        listView.setCellFactory(new Callback<>() {
            public ListCell<Pawn> call(ListView<Pawn> param) {
                return new ListCell<>() {
                    @Override
                    public void updateItem(Pawn item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!empty || item != null ) {
                            setTextFill(Color.web(item.getColor()));
                            setText(item.toString());
                        }
                    }
                };

            }
        });
        listView.setItems(FXCollections.observableArrayList(pawns));

        if(Generator.players.size() == ReadConfig.MIN_NUM_PLAYERS){
            lblPlayer3.setVisible(false);
            lblPlayer4.setVisible(false);
        }else if(Generator.players.size() == ReadConfig.MIN_NUM_PLAYERS + 1){
            lblPlayer4.setVisible(false);
        }

        Generator.generateCards();

        //adding tiles to grid
        for(int i=0; i<ReadConfig.gridSize; i++){
            for(int j=0; j<ReadConfig.gridSize; j++) {
                Tile tempTile = new Tile(false, grid);
                grid.add(tempTile, j, i);
                tiles.put(i * ReadConfig.gridSize + j, tempTile);
            }
        }



    }

    public void startPauseGame(MouseEvent mouseEvent) {
        if(!started){
            //GhostPawn ghostPawn = new GhostPawn(new Image(String.valueOf(Main.class.getResource("resources/img/diamond.png"))), this);
            Thread ghostThread = new Thread( new GhostPawn(new Image(String.valueOf(Main.class.getResource("resources" + File.separator + "img" + File.separator + "diamond.png"))), this));
            ghostThread.setDaemon(true);
            ghostThread.start();
            Game game = new Game(this);
            game.start();
            started = true;
            Timer timer = new Timer(this);
            timer.start();

        }else{
            pause.set(!pause.get());
        }

    }

    public void showResults(MouseEvent mouseEvent){
        Stage stage = new Stage();
        stage.initOwner(grid.getScene().getWindow());
        stage.initModality(Modality.WINDOW_MODAL);
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("resources" + File.separator + "views" + File.separator + "results.fxml"));
            Parent root = (Parent) loader.load();
            ResultController resultController = loader.getController();
            stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
            stage.show();
        } catch (IOException ex) {
            Main.LOGGER.log(Level.INFO, ex.toString(), ex);
        }
    }

    public void openPawnMovement() {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            Stage stage = new Stage();
            stage.initOwner(grid.getScene().getWindow());
            stage.initModality(Modality.WINDOW_MODAL);
            try {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("resources" + File.separator + "views" + File.separator + "grid.fxml"));
                Parent root = (Parent) loader.load();
                PawnController pawnController = loader.getController();
                pawnController.setSelectedPawn(listView.getSelectionModel().getSelectedItem());
                pawnController.display();
                stage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
                stage.show();
            } catch (IOException ex) {
                Main.LOGGER.log(Level.INFO, ex.toString(), ex);
            }
        }
    }
}
