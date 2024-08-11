import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Calculator {
    JFrame frame;
    JButton[] numberButtons = new JButton[10];
    JButton[] opButtons = new JButton[4];
    JButton addButton, subButton, mulButton, divButton;
    JButton decButton, eqButton, bspButton, clrButton;
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
        divButton = new JButton("/");
        eqButton = new JButton("=");


        opButtons[0] = addButton;
        opButtons[1] = subButton;
        opButtons[2] = mulButton;
        opButtons[3] = divButton;

        clrButton = new JButton("AC");
        clrButton.addActionListener(e -> clearAll());

        decButton = new JButton(".");
        decButton.addActionListener(e -> {
            if (textfield.getText().contains(".")) {
                return;
            }
            textfield.setText(textfield.getText() + e.getActionCommand());
        });

        bspButton = new JButton("\u2190"); // Unicode for ←
        bspButton.addActionListener(e -> {
            String text = textfield.getText();
            if (text.length() > 1) {
                textfield.setText(text.substring(0, text.length() - 1));
                setNumber();
            }
        });

        panel = new JPanel(new GridLayout(5, 3, 10, 10));
        panel.setBounds(50, 100, 300, 300);

        textfield = new JTextField("0");
        textfield.setBounds(50, 25, 300, 50);
        textfield.setFont(myFont);
        textfield.setEditable(false);
        textfield.setFocusable(false);
        textfield.setBackground(new Color(107, 201, 201));
        textfield.putClientProperty("clicked", false);

        // Set up number buttons
        for (int i = 0; i < numberButtons.length; i++) {
            JButton numberButton = new JButton(Integer.toString(i));
            numberButton.addActionListener(e -> {
                if (textfield.getClientProperty("clicked").equals(false)) {
                    textfield.putClientProperty("clicked", true);
                    textfield.setText(e.getActionCommand());
                } else {
                    textfield.setText(textfield.getText() + e.getActionCommand());
                }

                setNumber();
                System.out.printf("\n" +
                        "Num1: %s\n" +
                        "Operator: %s\n" +
                        "Num2: %s", num1, operator, num2);
            });
            JButton formattedNumButton = formatButton(numberButton);
            numberButtons[i] = formattedNumButton;
            panel.add(formattedNumButton);
        }

        // Set up operation buttons
        for (JButton button : opButtons) {
            button.setFocusable(false);
            button.addActionListener(e -> {
                textfield.putClientProperty("clicked", false);
                if (operator != null && !operator.isEmpty()) {
                    result = calculate(operator, num1, num2);
                    textfield.setText(Double.toString(result));
                    setupForNextCalc();
                }
                operator = e.getActionCommand();
            });

            JButton formattedOpBtn = formatButton(button);
            panel.add(formattedOpBtn);
        }

        eqButton.addActionListener(e -> {
            System.out.printf("\n" +
                    "Num1: %s\n" +
                    "Operator: %s\n" +
                    "Num2: %s", num1, operator, num2);
            if (operator == null || operator.isEmpty()) {
                return;
            }
            result = calculate(operator, num1, num2);
            textfield.setText(Double.toString(result));
            System.out.printf("\nResult is %s", result);
            setupForNextCalc();
        });

        JButton formattedEqButton = formatButton(eqButton);
        panel.add(formattedEqButton);
        JButton formattedClrButton = formatButton(clrButton);
        formattedClrButton.setFont(new Font("Arial", Font.PLAIN, 20));
        panel.add(formattedClrButton);
        JButton formattedDecButton = formatButton(decButton);
        panel.add(formattedDecButton);
        JButton formattedBspButton = formatButton(bspButton);
        panel.add(formattedBspButton);

        frame.add(panel);
        frame.add(textfield);
        frame.setVisible(true);
    }

    private void clearAll() {
        num1 = 0;
        num2 = 0;
        result = 0;
        operator = "";
        textfield.setText("0");
        textfield.putClientProperty("clicked", false);
    }

    private void setupForNextCalc() {
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
        button.setSize(50, 50);
        return button;
    }

    private void setNumber() {
        if (operator == null || operator.isEmpty()) {
            num1 = Double.parseDouble(textfield.getText());
        } else {
            num2 = Double.parseDouble(textfield.getText());
        }
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator();
    }

}
