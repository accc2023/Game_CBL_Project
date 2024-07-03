import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Graphical user interface for Start Menu.
 * 
 * Contains all the methods to draw the Start Menu, including:
 * - The title of the game;
 * - A button for a pop-up of instruction on how to play the game;
 * - The three different buttons for the difficulties:
 * - easy, medium, hard.
 * 
 * @author JOAO FREITAS
 * @id 1942395
 * @author ARHAN CHHABRA
 * @id 1940198
 */
public class StartGUI implements ActionListener {
    // GUI components
    private JFrame frame;
    private JLabel panel;
    private JPanel panelInstructions;
    private JLabel label;
    private JLabel labelInstructions;
    private JLabel textArea;
    private JLabel textArea2;
    private JLabel textArea3;

    private JButton easyButton = new JButton("Easy");
    private JButton mediumButton = new JButton("Medium");
    private JButton hardButton = new JButton("Hard");
    private JButton instructionsButton = new JButton("Instructions");
    private static final String BACKGROUND = "https://imgur.com/OaHhKhc.png";
    private ImageIcon background;
    private GridBagConstraints constraints = new GridBagConstraints();

    private String difficulty;

    /**
     * Controls the drawing of the Start Menu as a whole.
     */
    public void drawStartMenu() {
        drawFrame("Where Am I");

        // Draws all the buttons
        menuButton(instructionsButton, "Instruction", 300, 65);
        menuButton(easyButton, "Easy", 400, 65);
        menuButton(mediumButton, "Medium", 400, 65);
        menuButton(hardButton, "Hard", 400, 65);
        // Positions the buttons within the grid
        gridBagConstraintsMethod(instructionsButton, 1, 1, 1,
                1, GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);
        gridBagConstraintsMethod(easyButton, 1, 2, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);
        gridBagConstraintsMethod(mediumButton, 1, 3, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);
        gridBagConstraintsMethod(hardButton, 1, 4, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);
        frame.revalidate();
    }

    // Draws the frame for the Start Menu
    private void drawFrame(String title) {
        frame = new JFrame(title);
        frame.setSize(1400, 800);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        getURL();
        drawMenuPanel();
        drawLabel();
        frame.add(panel);
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

    // Draws the panel of the GUI Start Menu
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

    // Draws and specifies/formats the label of the start menu
    private void drawLabel() {
        label = new JLabel("Where Am I");
        Font labelFont = new Font("Candara", Font.BOLD, 130);
        label.setFont(labelFont);
        label.setPreferredSize(new Dimension(800, 140));
        panel.add(label);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        gridBagConstraintsMethod(label, 1, 0, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.CENTER);
        frame.revalidate();
    }

    // Creates a button based on input
    private void menuButton(JButton button, String buttonName, int width, int height) {
        button.setText(buttonName);
        button.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        button.setForeground(new Color(2, 177, 196));
        button.addActionListener(this);
        button.setPreferredSize(new Dimension(width, height));
        button.setVisible(true);
        button.setEnabled(true);
        panel.add(button);
        frame.revalidate();
    }

    // Performs actions (going to instructions and rounds) when buttons are clicked
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == easyButton) {
            frame.dispose();
            difficulty = "easy";
        } else if (e.getSource() == mediumButton) {
            difficulty = "medium";
            frame.dispose();
        } else if (e.getSource() == hardButton) {
            difficulty = "hard";
            frame.dispose();
        } else if (e.getSource() == instructionsButton) {
            drawPanelInstructions();
        }
    }

    // Draws the Instructions GUI PopUp
    private void drawPanelInstructions() {
        frame = new JFrame("Instructions");
        drawFrame("Instructions");
        panelInstructions = new JPanel();
        panelInstructions.setLayout(new GridBagLayout());

        // Instruction title
        labelInstructions = new JLabel("Instructions");
        Font labelFont = new Font("Playbill", Font.PLAIN, 90);
        labelInstructions.setFont(labelFont);
        labelInstructions.setPreferredSize(new Dimension(1500, 100));
        panel.add(labelInstructions);
        labelInstructions.setHorizontalAlignment(SwingConstants.CENTER);
        labelInstructions.setVerticalAlignment(SwingConstants.NORTH);
        gridBagConstraintsMethod(labelInstructions, 1, 0, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);
        label.setVisible(false);

        // List of instructions
        String text = "In this game, your goal is to guess the location"
                + "on the map based on an image.";
        textArea = new JLabel(text);
        Font labelFont2 = new Font("Segoe UI Semibold", Font.PLAIN, 35);
        textArea.setFont(labelFont2);
        textArea.setForeground(new Color(1, 0, 87));
        textArea.setPreferredSize(new Dimension(1500, 50));
        panel.add(textArea);
        textArea.setHorizontalAlignment(SwingConstants.CENTER);
        textArea.setVerticalAlignment(SwingConstants.NORTH);
        gridBagConstraintsMethod(textArea, 1, 1, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);

        // Second line
        String text2 = "There are 10 rounds, and your score depends on the"
                + "accuracy of your guesses.";
        textArea2 = new JLabel(text2);
        textArea2.setFont(labelFont2);
        textArea2.setForeground(new Color(1, 0, 87));
        textArea2.setPreferredSize(new Dimension(1500, 50));
        panel.add(textArea2);
        textArea2.setHorizontalAlignment(SwingConstants.CENTER);
        textArea2.setVerticalAlignment(SwingConstants.NORTH);
        gridBagConstraintsMethod(textArea2, 1, 2, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);

        // Third line
        String text3 = "Difficulty settings determine the locations, with a"
                + "20-second time limit in hard mode.";
        textArea3 = new JLabel(text3);
        textArea3.setFont(labelFont2);
        textArea3.setForeground(new Color(1, 0, 87));
        textArea3.setPreferredSize(new Dimension(1500, 50));
        panel.add(textArea3);
        textArea3.setHorizontalAlignment(SwingConstants.CENTER);
        textArea3.setVerticalAlignment(SwingConstants.NORTH);
        gridBagConstraintsMethod(textArea3, 1, 3, 1, 1,
                GridBagConstraints.NONE, 1, 1, GridBagConstraints.NORTH);
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

    public String getDifficulty() {
        return difficulty;
    }
}
