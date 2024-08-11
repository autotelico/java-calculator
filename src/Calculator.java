import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {
    JFrame frame;
    JButton[] numberButtons = new JButton[10];
    JButton[] functionButtons = new JButton[4];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, eqButton, delButton, clrButton;
    JPanel panel;
    JTextField textfield;

    Font myFont = new Font("Arial", Font.PLAIN, 30);

    double num1 = 0, num2 = 0, result = 0;
    char operator;


    Calculator() {
        frame = new JFrame("My Sweet Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                char pressedKey = e.getKeyChar();
                System.out.printf("You typed key %s\n", pressedKey);
                if (Character.isDigit(pressedKey)) {
                    System.out.printf("%s is also a number!\n\n", pressedKey);
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.setSize(420, 550);
        frame.setLayout(null);
        addButton = new JButton("+");
        subButton = new JButton("-");
        mulButton = new JButton("x");
        divButton = new JButton("÷");
        addButton.setSize(20, 20);
        subButton.setSize(20, 20);
        mulButton.setSize(20, 20);
        divButton.setSize(20, 20);

        functionButtons[0] = addButton;
        functionButtons[1] = subButton;
        functionButtons[2] = mulButton;
        functionButtons[3] = divButton;

        panel = new JPanel(new GridLayout(5, 3, 10, 10));
        panel.setBounds(50, 100, 300, 300);

        textfield = new JTextField("0");
        textfield.setBounds(50, 25, 300, 50);
        textfield.setFont(myFont);
        textfield.setEditable(false);
        textfield.setFocusable(false);
        textfield.putClientProperty("clicked", false);

        for (JButton button : functionButtons) {
            button.setFocusable(false);
            panel.add(button);
            String.valueOf(1);
        }

        for (int i = 0; i < numberButtons.length; i++) {
            JButton newButton = new JButton(String.valueOf(i));
            newButton.setFocusable(false);
            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (textfield.getClientProperty("clicked").equals(false)) {
                        textfield.putClientProperty("clicked", true);
                        textfield.setText(e.getActionCommand());
                        return;
                    }
                    System.out.println(e.getActionCommand());
                    textfield.setText(textfield.getText() + e.getActionCommand());
                }
            });
            numberButtons[i] = newButton;
            panel.add(newButton);
        }

        frame.add(panel);


        frame.add(textfield);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

}
