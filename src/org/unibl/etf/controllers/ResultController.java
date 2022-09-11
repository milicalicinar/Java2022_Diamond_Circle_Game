package org.unibl.etf.controllers;


import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.unibl.etf.Main;
import org.unibl.etf.models.pawn.Pawn;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ResultController implements Initializable {

    private File directory = new File("results");
    public ListView<String> resultFiles;
    private LinkedList<String> files = new LinkedList<>();
    @FXML
    private TextArea fileText;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File[] listOfFiles = directory.listFiles();
        for (File file : listOfFiles) {
            if (file.isFile()) {
                files.add(file.getName());
            }
        }

        resultFiles.setItems(FXCollections.observableArrayList(files));
    }

    public void openFile(MouseEvent mouseEvent) {
        StringBuilder results = new StringBuilder();
        if (resultFiles.getSelectionModel().getSelectedItem() != null) {
            Path path = Paths.get("results" + File.separator + resultFiles.getSelectionModel().getSelectedItem());
            try(BufferedReader reader = Files.newBufferedReader(path)){

                String currentLine = null;
                while((currentLine = reader.readLine()) != null){
                    results.append(currentLine + "\n");
                }
            }catch(IOException ex){
                Main.LOGGER.log(Level.INFO, ex.toString(), ex);
            }
        }
        fileText.setText(results.toString());
    }

}
