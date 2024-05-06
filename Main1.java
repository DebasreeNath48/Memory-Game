import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

// Abstract class representing a memory game
abstract class MemoryGame extends JFrame implements ActionListener {
    protected ArrayList<Integer> numbers;
    protected JButton[] buttons;
    protected JPanel panel;
    protected Timer timer;
    protected int currentNumberIndex;
    protected ArrayList<Integer> clickedNumbers = new ArrayList<>();

    // Constructor for initializing the game
    public MemoryGame(String title, int width, int height) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setLocationRelativeTo(null);

        numbers = new ArrayList<>();
        buttons = new JButton[16];
        panel = new JPanel(new GridLayout(4, 4));
        timer = new Timer(7000, this);

        generateNumbers();
        createButtons();
        addButtonsToPanel();
        showNumbers();

        getContentPane().add(panel);
        setVisible(true);

        timer.start();
    }

    // Method to generate numbers for the game
    protected void generateNumbers() {
        numbers.clear();
        for (int i = 1; i <= 8; i++) {
            numbers.add(i);
            numbers.add(i);
        }
        Collections.shuffle(numbers);
        for (int i = 0; i < numbers.size() - 1; i += 2) {
            Collections.swap(numbers, i, i + 1);
        }
    }

    // Method to create buttons for the game
    protected void createButtons() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton();
            buttons[i].addActionListener(this);
            buttons[i].setActionCommand(String.valueOf(i));
            buttons[i].setEnabled(false);
        }
    }

    // Method to add buttons to the panel
    protected void addButtonsToPanel() {
        for (JButton button : buttons) {
            panel.add(button);
        }
    }

    // Method to show numbers on buttons
    protected void showNumbers() {
        for (int i = 0; i < buttons.length; i++) {
            buttons[i].setText(String.valueOf(numbers.get(i)));
        }
    }

    // Method to hide numbers on buttons
    protected void hideNumbers() {
        for (JButton button : buttons) {
            button.setText("");
            button.setEnabled(true);
        }
    }

    // Method to reset the game
    protected void resetGame() {
        clickedNumbers.clear();
        for (JButton button : buttons) {
            button.setEnabled(true);
            button.setBackground(null);
        }
        numbers.clear();
        generateNumbers();
        showNumbers();
        currentNumberIndex = 0;
        timer.start();
    }
}

// Concrete implementation of a memory game
class MainMemoryGame extends MemoryGame {
    public MainMemoryGame() {
        super("Memory Game", 400, 400);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof Timer) {
            timer.stop();
            hideNumbers();
        } else if (e.getSource() instanceof JButton) {
            JButton clickedButton = (JButton) e.getSource();
            int index = Integer.parseInt(clickedButton.getActionCommand());
            System.out.println(index);
            int expectedNumber = numbers.get(currentNumberIndex);

            if (index + 1 == expectedNumber || clickedNumbers.contains(index)) {
                if(!(index + 1 == expectedNumber)){
                    JOptionPane.showMessageDialog(this, "Incorrect! Try again.");
                }
                else{
                if (!clickedNumbers.contains(index)) {
                    clickedButton.setEnabled(true);
                    clickedButton.setBackground(Color.GREEN);
                    clickedNumbers.add(index);
                }
                currentNumberIndex++;
                if (currentNumberIndex == numbers.size()) {
                    JOptionPane.showMessageDialog(this, "Congratulations! You won!");
                    resetGame();
                }
            }
            } else {
                JOptionPane.showMessageDialog(this, "Incorrect! Try again.");
            }
        }
    }
}

public class Main1 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMemoryGame::new);
    }
}