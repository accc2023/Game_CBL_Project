import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Graphical user interface for main game.
 * 
 * Contains all the methods to draw the different parts of the game on the GUI,
 * including:
 * - the image of the location;
 * - a map of the world;
 * - the current round;
 * - a timer if on hard mode;
 * - the score for that round (after the user has guessed);
 * - the answer (after the user has guessed).
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */
public class MainGUI extends JFrame implements ActionListener {
    private ScoreCalculator scoreCalculator;

    // GUI components
    private JFrame frame;
    private JLabel panel;

    private ImageIcon background;
    private JLabel imgLabel;
    private JLabel mapLabel;
    private JLabel currRound;
    private JLabel roundScore;
    private JLabel locationName;

    private GridBagConstraints constraints = new GridBagConstraints();

    private JLabel timerLabel;
    private Timer timer;
    private int counter = 20;

    // Variables with data used by the visual components
    private int x;
    private int y;
    private int xl;
    private int yl;
    private int score;
    private int round;
    private Location location;
    private boolean guessed = false;
    private static final String BACKGROUND = "https://imgur.com/jRv3Qxy.png";
    private static final String MAP = "https://imgur.com/PpIG3gL.png";

    /**
     * Controls the drawing of the Main Menu as whole, taking the
     * difficulty into account.
     */
    public void drawMainGUI(Location inLocation, String difficulty,
            ScoreCalculator inScoreCalculator, int inRound) {
        // Initializing variables
        location = inLocation;
        xl = location.getX();
        yl = location.getY();
        scoreCalculator = inScoreCalculator;
        round = inRound;

        // Draws the hard version of the Main Menu (with a timer)
        if (difficulty.equals("hard")) {
            // Draws individual components
            draw();
            drawImage(location.getImage());
            timer();
            drawMap();
            addMouseListenerMap();
            // Waiting for user input
            while (!guessed) {
                sleep(0.1);
            }
            roundOver();
            // Draws the easy and medium version of the Main menu
        } else {
            // Draws individual components
            draw();
            drawImage(location.getImage());
            drawMap();
            addMouseListenerMap();
            // Waiting for user input
            while (!validCoordinates()) {
                sleep(0.1);
            }
            roundOver();
        }

    }

    /**
     * Displays the frame with the background, location image, and current round on.
     */
    private void draw() {
        drawFrame("Round: " + round);
        getURL();
        drawPanel();
        drawRoundLabel();
        frame.add(panel);
        frame.revalidate();
    }

    // Draws the frame for the Main Menu
    private void drawFrame(String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1400, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Gets the background image from the URL
    private void getURL() {
        try {
            // Fetch the image from the URL
            URL url = new URL(BACKGROUND);
            BufferedImage bufferedImage = ImageIO.read(url);
            background = new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
    }

    // Draws the panel of the GUI Start Menu
    private void drawPanel() {
        panel = new JLabel(background) {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                Dimension lmPrefSize = getLayout().preferredLayoutSize(this);
                size.width = Math.max(size.width, lmPrefSize.width);
                size.height = Math.max(size.height, lmPrefSize.height);
                return size;
            }
        };
        panel.setLayout(new GridBagLayout());
    }

    // Draws the image of the location to be guessed
    private void drawImage(String imageURL) { // 480 x 270
        imgLabel = new JLabel();
        gridBagConstraintsMethod(imgLabel, 1, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.NORTH);
        try {
            // Fetch the image from the URL
            URL url = new URL(imageURL);
            BufferedImage image = ImageIO.read(url);

            // Set the image on the JLabel
            imgLabel.setIcon(new ImageIcon(image));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
        panel.add(imgLabel);
    }

    // Draws the image of the map where the user can click to guess
    private void drawMap() { // 867x500
        mapLabel = new JLabel();
        gridBagConstraintsMethod(mapLabel, 1, 1, 1, 1,
                GridBagConstraints.NONE, 3, 3, GridBagConstraints.SOUTH);
        mapLabel.setPreferredSize(new Dimension(867, 500));
        try {
            // Fetch the image from the URL
            URL url = new URL(MAP);
            BufferedImage map = ImageIO.read(url);

            // Set the image on the JLabel
            mapLabel.setIcon(new ImageIcon(map));

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
    }

    /**
     * Enables the user to click on the map and retrieves the coordinates of that
     * click.
     */
    private void addMouseListenerMap() {
        mapLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                x = e.getX();
                y = e.getY();
            }
        });
    }

    /**
     * Hard mode only.
     * Acts as a time limit waiting counter seconds for the user to guess.
     * If the time is up and the user has not guessed anything the game will move to
     * the next round.
     * A visual timer is also drawn on the Main Menu.
     */
    private void timer() {
        timerLabel = new JLabel("Timer: " + counter);
        timerLabel.setFont(new Font("Trebuchet MS", Font.PLAIN, 20));
        gridBagConstraintsMethod(timerLabel, 2, 0, 50, 50,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.NORTHWEST);
        timerLabel.setPreferredSize(new Dimension(90, 20));

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                counter--;
                if (counter >= 1 && !validCoordinates()) {
                    timerLabel.setText("Time: " + counter);
                } else {
                    timer.stop();
                    timerLabel.setText("");
                    guessed = true;
                }
            }
        });
        timerLabel.setVisible(true);
        timer.start();
    }

    // Draws the visual components on the Main Menu after the user has guessed
    private void roundOver() {
        score = scoreCalculator.score(x, y, xl, yl);
        drawScoreLabel();
        drawLocationLabel();
        frame.add(panel);
        frame.revalidate();
        // Waits before moving on to next round
        sleep(5);
        frame.dispose();
    }

    // Disp the round number
    private void drawRoundLabel() {
        // Current round
        currRound = new JLabel("Round " + round);
        Font labelFont = new Font("Trebuchet MS", Font.PLAIN, 20);
        currRound.setFont(labelFont);
        currRound.setPreferredSize(new Dimension(85, 40));
        panel.add(currRound);
        currRound.setHorizontalAlignment(SwingConstants.CENTER);
        currRound.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(currRound, 0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.NORTHWEST);
    }

    // Displays the score
    private void drawScoreLabel() {
        // Round score
        roundScore = new JLabel("Score: " + score);
        Font labelFont2 = new Font("Trebuchet MS", Font.PLAIN, 20);
        roundScore.setFont(labelFont2);
        roundScore.setPreferredSize(new Dimension(150, 30));
        panel.add(roundScore);
        roundScore.setHorizontalAlignment(SwingConstants.LEFT);
        roundScore.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(roundScore, 0, 1, 1, 1,
                GridBagConstraints.NONE, 0, 1, GridBagConstraints.NORTHWEST);
    }

    // Displays the round's correct location
    private void drawLocationLabel() {
        // Correct answer
        locationName = new JLabel("Answer: " + location.getName());
        Font labelFont = new Font("Trebuchet MS", Font.PLAIN, 20);
        locationName.setFont(labelFont);
        locationName.setPreferredSize(new Dimension(100, 25));
        panel.add(locationName);
        locationName.setHorizontalAlignment(SwingConstants.LEFT);
        locationName.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(locationName, 0, 0, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.SOUTHWEST);
    }

    // Returns true if the user has clicked in the map
    private boolean validCoordinates() {
        return !(x == 0 && y == 0);
    }

    // Sleep method that takes time in seconds as parameter
    private void sleep(double time) {
        try {
            Thread.sleep((int) time * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Formats each individal JComponent with inputted parameters
    private void gridBagConstraintsMethod(Component elementType, int gridxVal, int gridyVal,
            int gridwidthVal, int gridheighthVal, int fillVal, int weightxVal, int weightyVal,
            int anchor) {
        constraints.gridx = gridxVal; // X position within the grid
        constraints.gridy = gridyVal; // Y position within the grid
        constraints.gridwidth = gridwidthVal; // Number of id cells to occupy horizontally
        constraints.gridheight = gridheighthVal; // Number of grid cells to occupy vertically
        constraints.fill = fillVal; // How the component fills the cell
        constraints.weightx = weightxVal; /// How much space it takes up (0 means no extra space)
        constraints.weighty = weightyVal; // How much space it takes up (0 means no extra space)
        constraints.anchor = anchor; // How the component is anchored within the cell
        panel.add(elementType, constraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    }
}
