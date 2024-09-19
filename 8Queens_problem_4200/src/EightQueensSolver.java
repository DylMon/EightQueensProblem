import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class EightQueensSolver extends JFrame implements ActionListener {
    private static final int N = 8;
    private JPanel chessBoardPanel;
    private JButton runButton;
    private JButton resetButton;
    private int[] queens; // queens[i] = column position of queen in row i

    public EightQueensSolver() {
        setTitle("8 Queens Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 650);
        setLocationRelativeTo(null);

        // Initialize the queens array
        queens = new int[N];
        for (int i = 0; i < N; i++) {         
            queens[i] = -1;
        }

        // Initialize the GUI components
        chessBoardPanel = new JPanel(new GridLayout(N, N));
        chessBoardPanel.setPreferredSize(new Dimension(600, 600));

        // Create the chessboard squares
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                JPanel square = new JPanel(new BorderLayout());
                if ((row + col) % 2 == 0) {
                    square.setBackground(Color.WHITE);
                } else {
                    square.setBackground(Color.GRAY);
                }
                square.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                chessBoardPanel.add(square);
            }
        }

        // Create buttons
        JPanel buttonPanel = new JPanel();
        runButton = new JButton("Run");
        resetButton = new JButton("Reset");

        runButton.addActionListener(this);
        resetButton.addActionListener(this);

        buttonPanel.add(runButton);
        buttonPanel.add(resetButton);

        // Add components to the frame
        add(chessBoardPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new EightQueensSolver();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == runButton) {
            // Run the solver and display the result
            boolean solutionFound = solveNQueens();
            if (solutionFound) {
                updateChessBoard();
            } else {
                JOptionPane.showMessageDialog(this, "No solution found.");
            }
        } else if (e.getSource() == resetButton) {
            // Reset the board
            resetBoard();
        }
    }

    private boolean solveNQueens() {
        // Initialize the queens array
        for (int i = 0; i < N; i++) {
            queens[i] = -1;
        }

        return placeQueen(0);
    }

    private boolean placeQueen(int row) {
        if (row == N) {
            return true; // All queens placed successfully
        }

        for (int col = 0; col < N; col++) {
            if (isSafe(row, col)) {
                queens[row] = col; // Place queen
                if (placeQueen(row + 1)) {
                    return true;
                }
                // Backtrack
                queens[row] = -1;
            }
        }

        return false; // No valid position found
    }

    private boolean isSafe(int row, int col) {
        // Check for conflicts with already placed queens
        for (int i = 0; i < row; i++) {
            int qCol = queens[i];
            if (qCol == col || Math.abs(qCol - col) == Math.abs(i - row)) {
                return false; // Conflict
            }
        }
        return true;
    }

    private void updateChessBoard() {
        Component[] components = chessBoardPanel.getComponents();
        for (Component comp : components) {
            JPanel square = (JPanel) comp;
            square.removeAll();
        }

        for (int row = 0; row < N; row++) {
            int col = queens[row];
            if (col >= 0) {
                JPanel square = (JPanel) chessBoardPanel.getComponent(row * N + col);
                JLabel queenLabel = new JLabel("\u265B"); // Black queen Unicode character
                queenLabel.setFont(new Font("Serif", Font.PLAIN, 48));
                queenLabel.setHorizontalAlignment(SwingConstants.CENTER);
                queenLabel.setVerticalAlignment(SwingConstants.CENTER);
                square.add(queenLabel);
            }
        }

        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
    }

    private void resetBoard() {
        for (int i = 0; i < N; i++) {
            queens[i] = -1;
        }

        Component[] components = chessBoardPanel.getComponents();
        for (Component comp : components) {
            JPanel square = (JPanel) comp;
            square.removeAll();
        }

        chessBoardPanel.revalidate();
        chessBoardPanel.repaint();
    }
}
