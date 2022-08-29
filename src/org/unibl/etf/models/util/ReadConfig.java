package org.unibl.etf.models.util;

import org.unibl.etf.Main;
import org.unibl.etf.exceptions.ReadConfigException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class ReadConfig {
    private static final String FILE_PATH="config.properties";
    public static int gridSize;
    public static int MAX_GRID_SIZE=10;
    public static int MIN_GRID_SIZE=7;
    public final static int MAX_NUM_PLAYERS=4;
    public final static int MIN_NUM_PLAYERS=2;
    public static ArrayList<String> players = new ArrayList<>();
    private static List<String> uniqueNames=null;

    public static void readConfig() throws ReadConfigException {
        Properties prop = new Properties();
        try{
            FileInputStream fileProp=new FileInputStream(FILE_PATH);
            prop.load(fileProp);
           gridSize=Integer.parseInt(prop.getProperty("grid_size"));

           for(int i=1; i<=MAX_NUM_PLAYERS; i++){
               players.add(prop.getProperty("player" + i));
           }

           removeNullPlayers(players);
           checkUniqueUsernames(players);

        }catch(IOException ex){
            Main.LOGGER.log(Level.WARNING, ex.toString(), ex);
        }

        if(gridSize > MAX_GRID_SIZE || gridSize < MIN_GRID_SIZE){
            throw new ReadConfigException("Row size or column size must be greater or equal to 7 and lower or equal to 10!");
        }

        if(players.size()<MIN_NUM_PLAYERS){
            throw new ReadConfigException("Number of players must be greater or equal to 2 and lower or equal to 10!");
        }
        if(uniqueNames.size() < players.size()){
            throw new ReadConfigException("Usernames of players must be unique!");
        }
    }

    private static void removeNullPlayers(ArrayList<String> array){
        while(array.remove(null));
    }
    private static void checkUniqueUsernames(ArrayList<String> array){
        uniqueNames = array.stream().distinct().collect(Collectors.toList());
    }
}
