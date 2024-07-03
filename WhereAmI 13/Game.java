/**
 * Game.
 * 
 * Runs the main game, displaying the GUI with the Start Menu, Main menu and End
 * Menu,
 * and all functionalities associated with it.
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */

public class Game {
    // Variables used globally in all Menu screens.
    private Location[] locations;
    private String difficulty;

    // Selects the 10 locations that will be used in the game
    private void getLocationData() {
        LocationSelector locationSelector = new LocationSelector(difficulty);
        locations = locationSelector.getLocations();
    }

    // Controls the flow of the rounds, displaying the main menu for each round
    private void controlMainGUI(ScoreCalculator scoreCalculator) {
        int round = 1;
        for (Location location : locations) {
            MainGUI mainGUI = new MainGUI();
            mainGUI.drawMainGUI(location, difficulty, scoreCalculator, round);
            round++;
        }
    }

    /**
     * Controls the game as a whole.
     */
    public void startGame() {
        // Displays the Start Menu
        StartGUI startGUI = new StartGUI();
        startGUI.drawStartMenu();

        // Waits until the user selects a difficulty in the Start Menu
        difficulty = startGUI.getDifficulty();
        while (difficulty == null) {
            try {
                Thread.sleep(1000);
                difficulty = startGUI.getDifficulty();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        getLocationData();

        // Displays the Main Menu
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        controlMainGUI(scoreCalculator);

        // Displays the End Screen
        EndGUI endGUI = new EndGUI(scoreCalculator.getTotalScore(), difficulty);
        endGUI.drawEndMenu();
    }
}
