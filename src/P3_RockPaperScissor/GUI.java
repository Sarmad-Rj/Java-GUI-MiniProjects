package P3_RockPaperScissor;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException; // Required for font loading if using custom fonts
import java.io.InputStream; // Required for font loading if using custom fonts

public class GUI extends JFrame implements ActionListener {
    // Player buttons
    JButton rockButton, paperButton, scissorButton;

    // Will display the choice of the computer
    JLabel computerChoiceLabel; // Renamed for clarity

    // Will display the score of the computer and the player
    JLabel computerScoreLabel, playerScoreLabel;

    // Game logic instance
    RockPaperScissor rockPaperScissor;

    // --- Modern Color Palette ---
    private static final Color BACKGROUND_LIGHT = new Color(240, 242, 245);
    private static final Color PRIMARY_ACCENT = new Color(60, 179, 113);
    private static final Color SECONDARY_ACCENT = new Color(255, 160, 0);
    private static final Color TEXT_DARK = new Color(50, 50, 50);
    private static final Color TEXT_LIGHT = Color.WHITE;
    private static final Color BORDER_COLOR = new Color(180, 180, 180);
    private static final Color BUTTON_HOVER_COLOR = new Color(70, 190, 120);

    // --- Custom Fonts (Optional but highly recommended for professional look) ---
    private Font headerFont;
    private Font scoreFont;
    private Font buttonFont;
    private Font choiceFont;
    private Font dialogFont;

    public GUI() {
        super("Rock Paper Scissors");
        setSize(440, 480);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Load custom fonts
        loadFonts();

        // Use the defined subtle background color
        getContentPane().setBackground(BACKGROUND_LIGHT);

        rockPaperScissor = new RockPaperScissor();

        addGuiComponents();
    }

    // Method to load custom fonts from resources
    private void loadFonts() {
        try {
            InputStream is = getClass().getResourceAsStream("/fonts/Montserrat-Bold.ttf");
            if (is != null) {
                Font baseFont = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                ge.registerFont(baseFont);

                headerFont = baseFont.deriveFont(Font.BOLD, 36f);
                scoreFont = baseFont.deriveFont(Font.BOLD, 24f);
                buttonFont = baseFont.deriveFont(Font.BOLD, 18f);
                choiceFont = baseFont.deriveFont(Font.BOLD, 50f); // Larger for "?"
                dialogFont = baseFont.deriveFont(Font.BOLD, 16f);
            } else {
                System.err.println("Font file not found. Using default fonts.");
                setDefaultFonts();
            }
        } catch (FontFormatException | IOException e) {
            System.err.println("Error loading custom font: " + e.getMessage());
            setDefaultFonts();
        }
    }

    // Fallback to default Swing fonts if custom fonts fail to load
    private void setDefaultFonts() {
        headerFont = new Font("Segoe UI", Font.BOLD, 36);
        scoreFont = new Font("Segoe UI", Font.BOLD, 24);
        buttonFont = new Font("Segoe UI", Font.BOLD, 18);
        choiceFont = new Font("Segoe UI", Font.BOLD, 50);
        dialogFont = new Font("Segoe UI", Font.BOLD, 16);
    }


    private void addGuiComponents() {
        // Use GridBagLayout for flexible and professional alignment
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // --- Header ---
        JLabel titleLabel = new JLabel("Rock Paper Scissors!");
        titleLabel.setFont(headerFont);
        titleLabel.setForeground(PRIMARY_ACCENT);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3; // Span across all three columns
//        gbc.weighty = 0.1; // Give it some vertical space
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);

        // --- Computer Score Label ---
        computerScoreLabel = new JLabel("Computer: 0");
        computerScoreLabel.setFont(scoreFont);
        computerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        computerScoreLabel.setForeground(SECONDARY_ACCENT); // Orange for computer
        gbc.gridy = 1;
        gbc.weighty = 0;
        add(computerScoreLabel, gbc);

        // --- Computer Choice Display ---
        computerChoiceLabel = new JLabel("?");
//        computerChoiceLabel.setFont(choiceFont);
        computerChoiceLabel.setFont(new Font("Dialog", Font.BOLD, 25));
        computerChoiceLabel.setHorizontalAlignment(SwingConstants.CENTER);
        computerChoiceLabel.setPreferredSize(new Dimension(100, 100));
        computerChoiceLabel.setOpaque(true);
        computerChoiceLabel.setBackground(TEXT_LIGHT);
        computerChoiceLabel.setForeground(TEXT_DARK);
        computerChoiceLabel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2, true));
        gbc.gridy = 2;
//        gbc.weighty = 0.3; // Give it more vertical space
        add(computerChoiceLabel, gbc);

        // --- Player Score Label ---
        playerScoreLabel = new JLabel("Player: 0");
        playerScoreLabel.setFont(scoreFont);
        playerScoreLabel.setHorizontalAlignment(SwingConstants.CENTER);
        playerScoreLabel.setForeground(PRIMARY_ACCENT); // Green for player
        gbc.gridy = 3;
//        gbc.weighty = 0; // Don't stretch vertically
        add(playerScoreLabel, gbc);

        // --- Player Buttons Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Spacing between buttons
        buttonPanel.setBackground(BACKGROUND_LIGHT); // Match frame background

        // Rock button
        rockButton = createStyledButton("Rock");
        rockButton.addActionListener(this);
        buttonPanel.add(rockButton);

        // Paper button
        paperButton = createStyledButton("Paper");
        paperButton.addActionListener(this);
        buttonPanel.add(paperButton);

        // Scissor button
        scissorButton = createStyledButton("Scissors");
        scissorButton.addActionListener(this);
        buttonPanel.add(scissorButton);

        gbc.gridy = 4;
//        gbc.weighty = 0.3; // Give buttons panel some vertical space
        gbc.anchor = GridBagConstraints.SOUTH; // Align to bottom
        add(buttonPanel, gbc);
    }

    // Helper method to create consistently styled buttons
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(buttonFont);
        button.setBackground(PRIMARY_ACCENT);
        button.setForeground(TEXT_LIGHT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_ACCENT.darker(), 1),
                new EmptyBorder(15, 25, 15, 25)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(BUTTON_HOVER_COLOR);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_ACCENT);
            }
        });

        return button;
    }

    // Displays a message dialog which will show the winner and a try again button to play again
    private void showDialog(String message) {
        JDialog resultDialog = new JDialog(this, "Game Over", true);
        resultDialog.setSize(300, 180);
        resultDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        resultDialog.setResizable(false);
        resultDialog.setLayout(new BorderLayout(10, 10));
        resultDialog.getContentPane().setBackground(BACKGROUND_LIGHT);
        resultDialog.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));

        // Message label
        JLabel resultLabel = new JLabel(message);
        resultLabel.setFont(dialogFont);
        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setForeground(TEXT_DARK);
        resultDialog.add(resultLabel, BorderLayout.CENTER);

        // Try again button
        JButton tryAgainButton = new JButton("Play Again?");
        tryAgainButton.setFont(buttonFont.deriveFont(Font.PLAIN, 16f));
        tryAgainButton.setBackground(PRIMARY_ACCENT);
        tryAgainButton.setForeground(TEXT_LIGHT);
        tryAgainButton.setFocusPainted(false);
        tryAgainButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        tryAgainButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset computer choice
                computerChoiceLabel.setText("?");
                computerScoreLabel.setText("Computer: " + rockPaperScissor.getComputerScore());
                playerScoreLabel.setText("Player: " + rockPaperScissor.getPlayerScore());
                resultDialog.dispose();
            }
        });

        JPanel dialogButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        dialogButtonPanel.setBackground(BACKGROUND_LIGHT);
        dialogButtonPanel.add(tryAgainButton);
        resultDialog.add(dialogButtonPanel, BorderLayout.SOUTH);

        resultDialog.setLocationRelativeTo(this);
        resultDialog.setLocation(750, 320);
        resultDialog.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Get player choice
        String playerChoice = e.getActionCommand(); // Use getActionCommand directly

        // Play rock paper scissors and store result into string var
        String result = rockPaperScissor.playRockPaperScissor(playerChoice);

        // Load computer's choice
        computerChoiceLabel.setText(rockPaperScissor.getComputerChoice());

        // Update score
        computerScoreLabel.setText("Computer: " + rockPaperScissor.getComputerScore());
        playerScoreLabel.setText("Player: " + rockPaperScissor.getPlayerScore());

        // Display result dialog
        showDialog(result);
    }
}