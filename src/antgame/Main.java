package antgame;

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
 * Main class, runs the whole thing
 * @author Abdullah Rowaished
 */
public class Main extends Application {
    public static int
            popup_counter/*counts how many "load player" windows are to popup; usually equates to how many players the user wishes to add at that instance*/,
            player_counter/*counts how many players to be loaded in an instance, decrements once a player is loaded until it reaches 0; initial value equates popup_counter*/,
            brain_counter/*same concept as player_counter except it starts as player_counter - 1; everytime brain_counter == player_counter, the program assumes a player is ready to be added*/,
            map_counter/*temporary value to ensure synchronuousy of map count*/;
    public static Stack<Stage> stages/*a stack machine for windows, the latest window gets to be popped off\peeked at from the stack to be viewed, showed, or updated*/;
    public static File
            //worldFiles/*world files loaded into the game are here NOTE: type of field might change to ArrayList*/,
            tempBrainFile/*TEMP: ant brain files loaded into the game are here NOTE: this field might not be necassry if consiladed as a field of custom class Player along with playerFiles*/;
            //playerFiles /*REFACTOR: this does not have any practical uses and shall be replaced with an ArrayList containing Player objects*/;
    public static Stack<Exception> exceptions/*DEBUGGER: could prove useful in handling exceptions; pushes every exception encountered in a try-catch into it*/;
    public static boolean load_flag/*VITAL: indicates if a file is successfully loaded, ant brain or ant world; prevents unintentional overwrites and bugs associated with it*/;
    public static Game game;
    
    @Override
    public void start(Stage launcher) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Launcher.fxml"));
            launcher.setScene(new Scene(root));
            launcher.setTitle("launcher"); //optional
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
        exceptions = new Stack<>();
        popup_counter = 0;
        brain_counter = 0;
        player_counter = 0;
        map_counter = 0;
        load_flag = false;
        game = new Game();
        launch(args);
        
    }
    
}
