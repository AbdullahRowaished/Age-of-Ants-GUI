package antgame;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
/**
 *
 * @author Abdullah Rowaished
 */
public class Main extends Application {
    public static int popup_counter, player_counter, brain_counter, map_counter, number_of_players, number_of_maps;
    public static Stack<Stage> stages;
    public static Stack<File> worldFiles, brainFiles, playerFiles;
    public static Stack<Exception> exceptions;
    public static HashMap<File, File> player_brain_map;
    public static boolean load_flag;
    
    @Override
    public void start(Stage launcher) {
        //Image image = new Image("age-of-ants.jpg");
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Launcher.fxml"));
            launcher.setScene(new Scene(root));
            launcher.setTitle("Launcher"); //optional
            launcher.setResizable(true); //optional
            launcher.initStyle(StageStyle.UNDECORATED);
            stages.push(launcher); //IMPORTANT
            launcher.show();
        } catch (NullPointerException | IOException ex) {
            exceptions.push(ex);
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex); //DEBUGGER
            System.exit(-1);
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        stages = new Stack<>();
        worldFiles = new Stack<>();
        brainFiles = new Stack<>();
        playerFiles = new Stack<>();
        exceptions = new Stack<>();
        player_brain_map = new HashMap<>();
        popup_counter = 0;
        brain_counter = 0;
        player_counter = 0;
        map_counter = 0;
        number_of_players = 0;
        number_of_maps = 0;
        load_flag = false;
        launch(args);
    }
}
