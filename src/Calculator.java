import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {
    JFrame frame;
    JButton[] numberButtons = new JButton[10];
    JButton[] opButtons = new JButton[4];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, eqButton, delButton, clrButton;
    JPanel panel;
    JTextField textfield;

    Font myFont = new Font("Arial", Font.PLAIN, 30);

    double num1 = 0, num2 = 0, result = 0;
    String operator;

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

            // Unchanged overrides
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
        mulButton = new JButton("*");
        divButton = new JButton("÷");
        eqButton = new JButton("=");
        addButton.setSize(20, 20);
        subButton.setSize(20, 20);
        mulButton.setSize(20, 20);
        divButton.setSize(20, 20);

        JButton[] btnArray = new JButton[4];

        opButtons[0] = addButton;
        opButtons[1] = subButton;
        opButtons[2] = mulButton;
        opButtons[3] = divButton;


        panel = new JPanel(new GridLayout(5, 3, 10, 10));
        panel.setBounds(50, 100, 300, 300);

        textfield = new JTextField("0");
        textfield.setBounds(50, 25, 300, 50);
        textfield.setFont(myFont);
        textfield.setEditable(false);
        textfield.setFocusable(false);
        textfield.putClientProperty("clicked", false);

        // Set up number buttons
        for (int i = 0; i < numberButtons.length; i++) {
            JButton newButton = new JButton(Integer.toString(i));
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
                    if (num1 == 0) {
                        num1 = Double.parseDouble(textfield.getText());
                    } else {
                        num2 = Double.parseDouble(textfield.getText());
                    }
                    System.out.printf("Num1: %s\nNum2: %s", num1, num2);
                }
            });
            JButton formattedButton = formatButton(newButton);
            numberButtons[i] = newButton;
            panel.add(newButton);
        }

        // Set up operation buttons
        for (JButton button : opButtons) {
            button.setFocusable(false);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    textfield.putClientProperty("clicked", false);
                    if (operator == null || operator.isEmpty()) {
                        operator = e.getActionCommand();
                        textfield.setText("0");
                        System.out.println("Operator was empty");
                    } else {
                        result = calculate(operator, num1, num2);
                        textfield.setText(Double.toString(result));
                        System.out.printf("Result is %s", Double.toString(result));
                        num1 = result;
                        num2 = 0;
                        result = 0;
                    }
                }
            });

            JButton formattedOpBtn = formatButton(button);
            panel.add(formattedOpBtn);
        }

        eqButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                result = calculate(operator, num1, num2);
                textfield.setText(Double.toString(result));
                clearAll();
            }
        });

        JButton formattedEqButton = formatButton(eqButton);

        panel.add(formattedEqButton);

        frame.add(panel);
        frame.add(textfield);
        frame.setVisible(true);
    }

    private void clearAll() {
        num1 = result;
        num2 = 0;
        result = 0;
        operator = "";
    }

    private static double calculate(String operation, double val1, double val2) {
        switch (operation) {
            case "+" -> {
                return val1 + val2;
            }
            case "-" -> {
                return val1 - val2;
            }
            case "*" -> {
                return val1 * val2;
            }
            case "/" -> {
                return val1 / val2;
            }
            default -> {
                throw new IllegalArgumentException("Invalid operation");
            }
        }
    }

    private JButton formatButton(JButton button) {
        button.setFocusable(false);
        button.setFont(new Font("Arial", Font.PLAIN, 30));
        return button;
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

}
