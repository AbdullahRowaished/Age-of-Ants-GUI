package antgame;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.EmptyStackException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;

/**
 * FXML Controller class
 *
 * @author Abdullah Rowaished
 */
public class Controller implements Initializable {
    /**
     * Initialises the controller class (leave it the F**K alone!)
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Main.controller = this;
    }
    @FXML
    public Label launcherLabel/*tourney - error messages: illegal number of players; illegal world importation; missing parameters for game to start*/,
            addPlayerLabel/*subtourney - error messages: illegal name of player; illegal ant brain importation; missing parameters for player to be added*/,
            loadBrainLabel/*subtourney - error messages: NONE; used to indicate if a brain is loaded or not, and the file name if loaded*/,
            redAntLabel/*tourney*/,
            blackAntLabel/*tourney*/,
            worldLabel/*tourney*/,
            scoreNameLabel/*results*/,
            scoreNumbLabel/*results*/,
            victorLabel/*victory*/;
    @FXML
    public Button quitButton/*launcher*/,
            homeButton/*tourney*/,
            loadButton/*tourney*/,
            addButton/*subtourney*/,
            brainButton/*subtourney*/,
            worldButton/*tourney*/,
            playButton/*touney*/,
            resumeButton/*battle*/,
            resetButton/*battle*/,
            quitsimButton/*battle*/,
            skipButton/*subtourney*/,
            showButton/*tourney*/,
            nextGameButton/*results*/,
            interCancelButton/*results*/,
            scoreButton/*results*/,
            victoryHomeButton/*victory*/;
    @FXML
    public TextField numOfPlayersTA/*tourney*/,
            playerAddTA/*subtourney*/;
    @FXML
    public TextArea battlefield;

    /**
     * hides the Launcher panel as it opens a new Tourney panel via clicking
     * 'Start' from the Launcher panel.
     */
    @FXML
    public void startGame() {
        try {
            Stage tourney = new Stage();
            tourney.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("Tourney.fxml"))));
            tourney.setTitle("Tourney");
            Main.stages.peek().hide();
            tourney.initStyle(StageStyle.UNDECORATED);
            tourney.show();
            Main.stages.push(tourney);
        } catch (NullPointerException | IOException ex) {
            Main.exceptions.push(ex);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            System.exit(-1);
        }
    }

    /**
     * closes all the panels associated with the program via clicking on the
     * 'Quit' button from the Launcher panel.
     */
    @FXML
    public void quitGame() {
        Main.stages.push((Stage) quitButton.getScene().getWindow());
        for (Stage stage : Main.stages) {
            stage.close();
        }
    }

    /**
     * opens a file chooser after clicking on the 'Brain' button from the
     * 'Subtourney' Panel
     */
    @FXML
    public void loadBrain() {
        try {
            FileChooser fc = new FileChooser();
            Stage window = new Stage();
            window.initStyle(StageStyle.UNDECORATED);
            fc.setTitle("Pick your Brain!");
            Main.tempBrainFile = (fc.showOpenDialog(window));
            if (!Main.tempBrainFile.getCanonicalPath().endsWith(".ant")) {
                throw new FileExtensionException();
            }
            if (!Main.load_flag) {
                Main.brain_counter--;
            }
            Main.load_flag = true;
            loadBrainLabel.setText("brain: " + Main.tempBrainFile.getName());
            clearErrors();
            //System.out.println("try: " + Main.brain_counter); //DEBUGGER
        } catch (FileExtensionException ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(brainButton, ex);
        } catch (IOException ex) {
            Main.exceptions.push(ex);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex); //DEBUGGER
        } catch (NullPointerException ex) {
            Main.exceptions.push(ex);
            //System.out.println("catch: " + Main.brain_counter); //DEBUGGER
        }
    }

    /**
     * closes tournament input feeder and shows back the launcher menu
     */
    @FXML
    public void goHome() {
        Main.stages.pop().close();
        Main.stages.peek().show();
    }

    /**
     * pops up a menu for loading brains
     */
    @FXML
    public void loadPlayers() {
        try {
            int number_of_players = getNumOfPlayers();
            if (number_of_players == -1) {
                throw new PlayersOutOfBoundsException();
            }
            Main.popup_counter = number_of_players;
            Main.brain_counter = number_of_players + 1;
            for (int i = 0; i < number_of_players; i++) {
                Stage player_loader = new Stage();
                player_loader.setScene(new Scene(((Parent) FXMLLoader.load(getClass().getResource("Subtourney.fxml")))));
                player_loader.setTitle("Player " + (i + 1) + "/" + number_of_players);
                player_loader.initStyle(StageStyle.UNDECORATED);
                Main.stages.peek().hide();
                Main.stages.push(player_loader);
                Main.load_flag = false;
                Main.stages.peek().showAndWait();
            }
            clearErrors();
        } catch (NullPointerException | IOException ex) {
            Main.exceptions.push(ex);
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NumberFormatException | WrongParametersException ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(loadButton, ex);
        }
    }

    /**
     * click OK to continue the game to recover from faultyParamScenario-related
     * exceptions
     *
     */
    @FXML
    public void addPlayer() {
        try {
            if (playerAddTA.getText().isEmpty() || !Character.isLetter(playerAddTA.getText().toCharArray()[0])) {
                throw new WrongNamingException();
            }
            if (Main.tempBrainFile == null) {
                throw new NoBrainException();
            }
            if (playersExists(playerAddTA.getText())) {
                throw new PlayerAlreadyExistsException();
            }
            //OSCAR (add new player)
            Main.game.addPlayer(playerAddTA.getText(), Main.tempBrainFile);
            if (Main.popup_counter >= 1) {
                Main.stages.pop().close();
                Main.popup_counter--;
            }
            Main.tempBrainFile = null;
            clearErrors();
            Main.stages.peek().show();
            createPairings();
        } catch (PlayerAlreadyExistsException ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(addButton, ex);
        } catch (WrongNamingException | NoBrainException ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(addButton, ex);
        } catch (FileNotFoundException ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(addButton, ex);
        } catch (IOException ex) {
            Main.exceptions.push(ex);
        } catch (Exception ex) {
            Main.exceptions.push(ex);
        }
    }

    /**
     * imports world via clicking on the import world button.
     */
    @FXML
    public void importWorld() {
        try {
            FileChooser fc = new FileChooser();
            Window window = new Stage();
            fc.setTitle("Pick your Fight!");
            ((Stage) window).initStyle(StageStyle.UNDECORATED);
            File file = fc.showOpenDialog(window);
            if (!file.getName().endsWith(".world")) {
                throw new FileExtensionException();
            }
            Main.game.loadWorld(file);
            Main.tempWorldFile = file;
            clearErrors();
            createPairings();
        } catch (Exception ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(worldButton, ex);
        }
        //OSCAR (grammar)

    }

    @FXML
    public void skipLoad() {
        try {
            if (Main.popup_counter >= 1) {
                Main.stages.pop().close();
                Main.brain_counter--;
                Main.popup_counter--;
            }
            Main.stages.peek().show();
            clearErrors();
        } catch (EmptyStackException ex) {
            Main.exceptions.push(ex);
        }
    }

    /**
     * my lords, we enter the fray!
     */
    @FXML
    public void playGame() {
        try {
            if (Main.game.getPlayerNum() < 2) {
                throw new MissingGameParamsException();
            }
            if (Main.tempWorldFile == null) {
                Main.game.randomiseWorld();
            }
            Stage battle = new Stage();
            battle.setScene(new Scene((Parent) FXMLLoader.load(getClass().getResource("Battle.fxml"))));
            Main.stages.peek().hide();
            battle.initStyle(StageStyle.UNDECORATED);
            battle.show();
            Main.stages.push(battle);
            clearErrors();
        } catch (Exception ex) {
            Main.exceptions.push(ex);
            faultyParamScenario(playButton, ex);
        }
    }

    @FXML
    public void showPairings() {

        String redListings = "red ant\n", blackListings = "black ant\n", worldListings = "world\n";
        int i = 0;
        for (Player red : Main.redPairing) {
            redListings += red.getName() + "\n";
            blackListings += Main.blackPairing.get(i).getName() + "\n";
            try {
                worldListings += Main.tempWorldFile.getName() + "\n";
            } catch (NullPointerException ex) {
                Main.exceptions.push(ex);
                worldListings += "randomized\n";
            }
            i++;
        }
        redAntLabel.setText(redListings);
        blackAntLabel.setText(blackListings);
        worldLabel.setText(worldListings);
    }

    @FXML
    public void quitSimulation() {
        //kill the game
        Main.stages.pop().close();
        Main.stages.peek().show();
    }

    @FXML
    public void startSimulation() {
        try {
            //OSCAR
            Main.game.startTournament();
        } catch (Exception ex) {
            Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    public void nextMatch() {
        goHome();
        //prepareNextMatch();
        //OSCAR
    }

    @FXML
    public void goHomeResults() {
        goHome();
        //OSCAR
    }

    @FXML
    public void showScores() {
        for (Player player : Main.game.getPlayers()) {
            scoreNameLabel.setText(player.getName() + "\n");
            scoreNumbLabel.setText(player.currentScore() + "\n");
        }
        //OSCAR
    }

    @FXML
    public void goHomeVictory() {
        //OSCAR
        goHome();
        goHome();
    }

    /*##############################################################################
    ###############CONTROLLERS###########CONTROLLERS###########CONTROLLERS##########
    ################################################################################
    ################################################################################
    ################################################################################
    #############PRIVATE METHODS#######PRIVATE METHODS#######PRIVATE METHODS########
    ##############################################################################*/
    /**
     * checks if the string in the text field in the tournament setup contains
     * nothing but integers above 1 and returns them, otherwise it returns -1
     *
     * @return the number of playerFiles as put in the text field of the
     * tournament setup page
     */
    private int getNumOfPlayers() {
        String taStr = numOfPlayersTA.getText();
        char[] ta = taStr.toCharArray();
        boolean isNum = true;
        int numOfPlayersInt = -1;
        for (char potentialNo : ta) {
            if (!Character.isDigit(potentialNo)) {
                isNum = false;
            }
        }
        if (isNum && Integer.parseInt(taStr) >= 1 && Integer.parseInt(taStr) <= 64) {
            numOfPlayersInt = Integer.parseInt(taStr);
        }
        return numOfPlayersInt;
    }

    /**
     * when a user inputs an faulty String in the text field of the any window,
     * this pops up.
     */
    private void faultyParamScenario(Button button, Exception exept) {
        try {
            Label label = null;
            if (button == loadButton) {
                label = launcherLabel;
                label.setText("error: user must enter an integer between \n and including 1 and 64");
            } else if (button == playButton) {
                label = launcherLabel;
                if (exept instanceof WrongParametersException) {
                    label.setText("error: user cannot start the game; \n must have at least 2 players");
                } else if (exept instanceof IOException) {
                    label.setText("error: user cannot start the game; \n world file may be missing from directory");
                } else if (exept instanceof NullPointerException) {
                    label.setText("warning: custom game world not loaded; \n expect a randomized one");
                }
            } else if (button == addButton) {
                label = addPlayerLabel;
                if (exept instanceof PlayerAlreadyExistsException) {
                    label.setText("error: cannot add new player; \n use must enter a player that is not loaded already");
                } else if (exept instanceof WrongNamingException) {
                    label.setText("error: cannot add new player; \n user must enter a String beginning with an alphabetic letter");
                } else if (exept instanceof NoBrainException) {
                    label.setText("error: cannot add new player; \n user must load an ant brain");
                } else if (exept instanceof FileNotFoundException) {
                    label.setText("error: cannot add new player: ant brain must be well formed");
                }
            } else if (button == brainButton) {
                //OSCAR (separate between grammatically correct ant brains and wrong extensions)
                label = addPlayerLabel;
                if (exept instanceof FileExtensionException) {
                    label.setText("error: user must load a proper .ant file");
                }
            } else if (button == worldButton) {
                //OSCAR (separate between grammatically correct worlds and wrong extensions)
                label = launcherLabel;
                if (exept instanceof FileExtensionException) {
                    label.setText("error: user must load a proper .world file");
                } else if (exept instanceof NullPointerException) {
                    label.setText("error: user must load a well formed .world file");
                }
            }
            label.setVisible(true);
        } catch (NullPointerException ex) {
            Main.exceptions.push(ex);
        }
    }

    /**
     * clears all labels which may contain an un-updated text indicating a
     * non-existent error; separate try catches because they are bound to
     * happen, thus it would revoke every executed statement were it a single
     * try catch.
     */
    private void clearErrors() {
        try {
            this.addPlayerLabel.setText("");
        } catch (NullPointerException ex) {
            Main.exceptions.push(ex);
        }
        try {
            this.launcherLabel.setText("");
        } catch (NullPointerException ex) {
            Main.exceptions.push(ex);
        }
        try {
            redAntLabel.setText("");
            blackAntLabel.setText("");
            worldLabel.setText("");
        } catch (NullPointerException ex) {
            Main.exceptions.push(ex);
        }

    }

    /**
     * checks if player already exists in the database
     *
     * @param potential
     * @return
     */
    private boolean playersExists(String potential) {
        try {
            for (Player player : Main.game.getPlayers()) {
                if (player.getName().equals(potential)) {
                    return true;
                }
            }
        } catch (NullPointerException ex) {
            Main.exceptions.push(ex);
        }
        return false;
    }

    /**
     * creates a new pair of players for face off for every world map
     */
    private void createPairings() {
        try {
            Main.redPairing.clear();
            Main.blackPairing.clear();
            for (int i = 0; i < Main.game.getPlayerNum(); i++) {
                for (int j = 0; j < Main.game.getPlayerNum(); j++) {
                    if (i != j) {
                        Main.redPairing.push(Main.game.getPlayers().get(i));
                        Main.blackPairing.push(Main.game.getPlayers().get(j));
                    }
                }
            }
        } catch (NullPointerException | EmptyStackException ex) {
            Main.exceptions.push(ex);
        }
        //System.out.println("redPairing: " + Main.pairs.size() + "\nmatches: " + Main.matches.size()); //DEBUGGER
    }
    
    class AoAThread extends Thread {
        AoAThread(){
            super();
        }
        
        @Override
        public void run() {
            try {
                Main.game.startTournament();
            } catch (Exception ex) {
                Main.exceptions.push(ex);
            }
        }
    }


    /*##############################################################################
    #############PRIVATE METHODS#######PRIVATE METHODS#######PRIVATE METHODS########
    ################################################################################
    ################################################################################
    ################################################################################
    ###########CUSTOM EXCEPTIONS#####CUSTOM EXCEPTIONS#####CUSTOM EXCEPTIONS########
    ##############################################################################*/
    /**
     * Exception handling for the game
     *
     * @author Abdullah Rowaished
     */
    private class WrongParametersException extends Exception {

        WrongParametersException() {
            super();
        }
    }

    /**
     * handles invalid text field texts
     */
    private class WrongTextFieldTextException extends WrongParametersException {

        WrongTextFieldTextException() {
            super();
        }
    }

    /**
     * handles invalid text field texts; specifically the player name text field
     */
    private class WrongNamingException extends WrongParametersException {

        WrongNamingException() {
            super();
        }
    }

    /**
     * 'abstract' exception handling all missing parameters of the game
     */
    private class MissingGameParamsException extends WrongParametersException {

        MissingGameParamsException() {
            super();
        }
    }

    /**
     * handles invalid text field texts; specifically the number of playerFiles
     * text field
     */
    private class PlayersOutOfBoundsException extends WrongTextFieldTextException {

        PlayersOutOfBoundsException() {
            super();
        }
    }

    /**
     * handles invalid text; specifically
     */
    private class NoBrainException extends MissingGameParamsException {

        NoBrainException() {
            super();
        }
    }

    /**
     * if loaded file via file chooser does not end with a string representing
     * the required extension, this is thrown (and caught & handled)
     */
    private class FileExtensionException extends IOException {

        FileExtensionException() {
            super();
        }
    }

    /**
     * if player name matches another that is already loaded, this is thrown
     */
    private class PlayerAlreadyExistsException extends WrongNamingException {

        PlayerAlreadyExistsException() {
            super();
        }
    }
}
