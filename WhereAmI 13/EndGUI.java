import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/**
 * Graphical user interface for the End Menu.
 * Displays the final score and a custom message to the user based on the score.
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */
public class EndGUI {
    // GUI components
    private JFrame frame;
    private JLabel panel;
    private JLabel finScore;
    private JLabel selDiff;
    private JLabel customM;
    private JLabel thanks;
    private JLabel note;
    private GridBagConstraints constraints = new GridBagConstraints();

    // Displayed values
    private String customMessage;
    private int finalScore;
    private String difficulty;

    // Background image variables
    private static final String BACKGROUND = "https://imgur.com/OaHhKhc.png";
    private ImageIcon background;

    /**
     * Constructor for End Menu.
     * 
     * @param inFinalScore final score to be displayed
     * @param inDifficulty difficulty to be displayed
     */
    public EndGUI(int inFinalScore, String inDifficulty) {
        finalScore = inFinalScore;
        difficulty = inDifficulty;
    }

    /**
     * Controls the drawing of the End Menu as a whole.
     */
    public void drawEndMenu() {
        customMessage();
        drawFrame("End screen");

        // Draws individual components
        getURL();
        drawMenuPanel();
        drawLabels();
        frame.add(panel);
        frame.revalidate();
    }

    // Draws the frame for the End Menu
    private void drawFrame(String title) {
        frame = new JFrame(title);
        frame.setSize(1400, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // Gets the background image from the URL
    private void getURL() {
        try {
            URL url = new URL(BACKGROUND);
            BufferedImage bufferedImage = ImageIO.read(url);
            background = new ImageIcon(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
    }

    // Draws the panel of the GUI End Menu
    private void drawMenuPanel() {
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

    // Draws and specifies/formats the labels of the End Menu
    private void drawLabels() {
        // Final score
        finScore = new JLabel("Final score: " + finalScore);
        Font labelFont = new Font("Playbill", Font.PLAIN, 85);
        finScore.setFont(labelFont);
        finScore.setPreferredSize(new Dimension(1400, 300));
        panel.add(finScore);
        finScore.setHorizontalAlignment(SwingConstants.CENTER);
        finScore.setVerticalAlignment(SwingConstants.NORTH);
        gridBagConstraintsMethod(finScore, 0, 0, 3, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.NORTH);

        // Selected difficulty
        selDiff = new JLabel("Selected Difficulty: " + difficulty);
        Font labelFont2 = new Font("Playbill", Font.PLAIN, 50);
        selDiff.setFont(labelFont2);
        selDiff.setForeground(new Color(71, 71, 71));
        selDiff.setPreferredSize(new Dimension(1400, 300));
        panel.add(selDiff);
        selDiff.setHorizontalAlignment(SwingConstants.CENTER);
        selDiff.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(selDiff, 0, 2, 3, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.CENTER);

        // Custom message
        customM = new JLabel(customMessage);
        Font labelFont3 = new Font("Playbill", Font.PLAIN, 35);
        customM.setFont(labelFont3);
        customM.setForeground(new Color(1, 0, 87));
        customM.setPreferredSize(new Dimension(1350, 80));
        panel.add(customM);
        customM.setHorizontalAlignment(SwingConstants.CENTER);
        customM.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(customM, 0, 3, 0, 1,
                GridBagConstraints.NONE, 0, 2, GridBagConstraints.CENTER);

        // Thanks
        thanks = new JLabel("Thanks for playing!");
        Font labelFont4 = new Font("Playbill", Font.PLAIN, 30);
        thanks.setFont(labelFont4);
        thanks.setPreferredSize(new Dimension(400, 60));
        panel.add(thanks);
        thanks.setHorizontalAlignment(SwingConstants.CENTER);
        thanks.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(thanks, 0, 10, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.SOUTHWEST);

        // Note
        note = new JLabel("*to play again close this window and run again");
        Font labelFont5 = new Font("Playbill", Font.PLAIN, 20);
        note.setFont(labelFont5);
        note.setPreferredSize(new Dimension(600, 50));
        panel.add(note);
        note.setHorizontalAlignment(SwingConstants.CENTER);
        note.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(note, 2, 10, 1, 1,
                GridBagConstraints.NONE, 0, 0, GridBagConstraints.SOUTHEAST);
    }

    // Selects a custom message to the user based on score and difficulty
    private void customMessage() {
        if (finalScore > 48000 && difficulty.equals("hard")) {
            customMessage = "You've found the hidden message congratulations!";
        } else if (finalScore > 48000) {
            customMessage = "You're either cheating or have too much free time.";
        } else if (finalScore > 45000) {
            customMessage = "Great, score shows culture.";
        } else if (finalScore > 40000) {
            customMessage = "Not bad at all. You know your stuff";
        } else if (finalScore > 35000) {
            customMessage = "Above average, good enough.";
        } else if (finalScore > 30000) {
            customMessage = "Boring score, by the way did you know the map you know is wrong?";
        } else if (finalScore < 26500 && finalScore > 23500 && difficulty.equals("medium")) {
            customMessage = "Wow! That's an extremely average score! On medium!";
        } else if (finalScore > 25000) {
            customMessage = "There is still hope for you! Just travel or study.";
        } else if (finalScore > 20000) {
            customMessage = "You should have stayed awake during those geography classes.";
        } else if (finalScore > 10000) {
            customMessage = "https://www.google.com/maps please open this link and explore a little.";
        } else if (finalScore > 5000) {
            customMessage = "Wow! Can you name 3 different continents?";
        } else if (difficulty.equals("easy")) {
            customMessage = "Wow! You actually managed to score " + finalScore + " on easy!";
        } else {
            customMessage = "Wow! Did you know that the world is not flat or is that news to you?";
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
}
