
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main extends JFrame implements ActionListener {

    private final JButton[] buttons = new JButton[9];

    private JLabel statusLabel;
    private JLabel xScoreLabel;
    private JLabel oScoreLabel;

    private char currentPlayer = 'X';

    private int xScore = 0;
    private int oScore = 0;

    private boolean gameOver = false;

    private final Color backgroundColor = new Color(18, 22, 35);
    private final Color buttonColor = new Color(35, 42, 62);
    private final Color xColor = new Color(0, 210, 255);
    private final Color oColor = new Color(255, 85, 125);
    private final Color winColor = new Color(40, 190, 125);

    public Main() {

        setTitle("Tic-Tac-Toe");
        setSize(450, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(backgroundColor);
        mainPanel.setLayout(new BorderLayout(15, 15));
        mainPanel.setBorder(
                BorderFactory.createEmptyBorder(25, 25, 25, 25)
        );

        // Title
        JLabel titleLabel = new JLabel(
                "TIC-TAC-TOE",
                SwingConstants.CENTER
        );

        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);

        // Scoreboard
        JPanel scorePanel = new JPanel(new GridLayout(1, 2, 15, 0));
        scorePanel.setBackground(backgroundColor);

        xScoreLabel = createScoreLabel("PLAYER X", xScore, xColor);
        oScoreLabel = createScoreLabel("PLAYER O", oScore, oColor);

        scorePanel.add(xScoreLabel);
        scorePanel.add(oScoreLabel);

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout(10, 15));
        headerPanel.setBackground(backgroundColor);
        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(scorePanel, BorderLayout.SOUTH);

        // Game board
        JPanel boardPanel = new JPanel(new GridLayout(3, 3, 10, 10));
        boardPanel.setBackground(backgroundColor);

        for (int i = 0; i < 9; i++) {

            buttons[i] = new JButton("");

            buttons[i].setFont(
                    new Font("Arial", Font.BOLD, 55)
            );

            buttons[i].setForeground(Color.WHITE);
            buttons[i].setBackground(buttonColor);
            buttons[i].setFocusPainted(false);
            buttons[i].setBorderPainted(false);
            buttons[i].setOpaque(true);
            buttons[i].setCursor(
                    new Cursor(Cursor.HAND_CURSOR)
            );

            buttons[i].addActionListener(this);

            boardPanel.add(buttons[i]);
        }

        // Status
        statusLabel = new JLabel(
                "Player X's turn",
                SwingConstants.CENTER
        );

        statusLabel.setFont(new Font("Arial", Font.BOLD, 18));
        statusLabel.setForeground(xColor);

        // Restart button
        JButton restartButton = new JButton("NEW GAME");

        restartButton.setFont(
                new Font("Arial", Font.BOLD, 16)
        );

        restartButton.setForeground(Color.WHITE);
        restartButton.setBackground(new Color(100, 85, 230));
        restartButton.setFocusPainted(false);
        restartButton.setBorderPainted(false);
        restartButton.setOpaque(true);
        restartButton.setCursor(
                new Cursor(Cursor.HAND_CURSOR)
        );

        restartButton.addActionListener(e -> resetBoard());

        JPanel bottomPanel = new JPanel(new GridLayout(2, 1, 10, 15));
        bottomPanel.setBackground(backgroundColor);
        bottomPanel.add(statusLabel);
        bottomPanel.add(restartButton);

        mainPanel.add(headerPanel, BorderLayout.NORTH);
        mainPanel.add(boardPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setVisible(true);
    }

    private JLabel createScoreLabel(
            String player,
            int score,
            Color color
    ) {

        JLabel label = new JLabel(
                "<html><center>" + player +
                        "<br><br><font size='6'>" +
                        score + "</font></center></html>",
                SwingConstants.CENTER
        );

        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(color);
        label.setOpaque(true);
        label.setBackground(buttonColor);
        label.setBorder(
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        );

        return label;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (gameOver) {
            return;
        }

        JButton clickedButton = (JButton) e.getSource();

        if (!clickedButton.getText().isEmpty()) {
            return;
        }

        clickedButton.setText(String.valueOf(currentPlayer));

        if (currentPlayer == 'X') {
            clickedButton.setForeground(xColor);
        } else {
            clickedButton.setForeground(oColor);
        }

        if (checkWinner()) {

            gameOver = true;

            if (currentPlayer == 'X') {
                xScore++;
                statusLabel.setText("Player X wins!");
                statusLabel.setForeground(xColor);
            } else {
                oScore++;
                statusLabel.setText("Player O wins!");
                statusLabel.setForeground(oColor);
            }

            updateScoreboard();

        } else if (isBoardFull()) {

            gameOver = true;

            statusLabel.setText("It's a draw!");
            statusLabel.setForeground(Color.YELLOW);

        } else {

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';

            statusLabel.setText(
                    "Player " + currentPlayer + "'s turn"
            );

            statusLabel.setForeground(
                    currentPlayer == 'X' ? xColor : oColor
            );
        }
    }

    private boolean checkWinner() {

        int[][] winningCombinations = {

                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8},

                {0, 3, 6},
                {1, 4, 7},
                {2, 5, 8},

                {0, 4, 8},
                {2, 4, 6}
        };

        for (int[] combination : winningCombinations) {

            int a = combination[0];
            int b = combination[1];
            int c = combination[2];

            String first = buttons[a].getText();

            if (!first.isEmpty()
                    && first.equals(buttons[b].getText())
                    && first.equals(buttons[c].getText())) {

                buttons[a].setBackground(winColor);
                buttons[b].setBackground(winColor);
                buttons[c].setBackground(winColor);

                return true;
            }
        }

        return false;
    }

    private boolean isBoardFull() {

        for (JButton button : buttons) {

            if (button.getText().isEmpty()) {
                return false;
            }
        }

        return true;
    }

    private void updateScoreboard() {

        xScoreLabel.setText(
                "<html><center>PLAYER X<br><br><font size='6'>" +
                        xScore + "</font></center></html>"
        );

        oScoreLabel.setText(
                "<html><center>PLAYER O<br><br><font size='6'>" +
                        oScore + "</font></center></html>"
        );
    }

    private void resetBoard() {

        for (JButton button : buttons) {

            button.setText("");
            button.setBackground(buttonColor);
        }

        currentPlayer = 'X';
        gameOver = false;

        statusLabel.setText("Player X's turn");
        statusLabel.setForeground(xColor);
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(Main::new);
    }
}
