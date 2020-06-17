import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeSwing extends JPanel {
    private static Font font = new Font("SanSerif", Font.BOLD, 20);
    private static JButton b1;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ButtonActionListener handler = new ButtonActionListener();
    private boolean flag = false;
    private final JLabel textResult = new JLabel("", SwingConstants.CENTER);


    public TicTacToeSwing() {
        int x = 10;
        int y = 10;
        setLayout(null);
        for (int i = 1; i <= 9; i++) {
            createButton("", x, y);
            x += 100;
            if (i % 3 == 0) {
                y += 100;
                x = 10;
            }
        }

    }

    public class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String character = "X";
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("") && !button.getText().equals("X") && !button.getText().equals("0")) {
                changeButtonCharacter(button, character);
                if (checkWin()) {
                    return;
                }
                flag = !flag;
            } else {
                return;
            }
            if (flag) {
                if (spaceCount() > 7) {
                    if (buttons.get(4).getText().equals("")) {
                        e.setSource(buttons.get(4));
                    } else {
                        e.setSource(buttons.get(8));
                    }
                } else {
                    e.setSource(buttons.get(machineWinAvailable()));
                }
                character = "0";
            }
            button = (JButton) e.getSource();
            if (button.getText().equals("") && !button.getText().equals("X") && !button.getText().equals("0")) {
                changeButtonCharacter(button, character);
                if (checkWin()) {
                    return;
                }
                flag = !flag;
            }
        }
    }
        private void changeButtonCharacter(JButton button, String character) {
                button.setText(character);
                remove(button);
                add(button);
                updateButton(button);
        }

        private void createButton(String colum, int x, int y) {
            b1 = new JButton(colum);
            b1.setFont(font);
            b1.addActionListener(handler);
            add(b1);
            b1.setBounds(x, y, 100, 100);
            b1.setFont(font.deriveFont(Font.BOLD, 50));
            buttons.add(b1);
        }

        private void updateButton(JButton button) {
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getX() == button.getX() && buttons.get(i).getY() == button.getY()) {
                    buttons.get(i).setText(button.getText());
                }
            }
        }

        private void setTextResult(String winner) {
            removeAll();
            textResult.setText(winner);
            int gap = 5;
            setLayout(new BorderLayout(gap, gap));
            setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
            add(textResult, BorderLayout.CENTER);
            textResult.setFont(font.deriveFont(Font.BOLD, 50));
        }

        private boolean checkWin() {
            int counterX = 0;
            int counterO = 0;
            for (int i = 0; i < buttons.size(); i++) {
                String buttonText = buttons.get(i).getText();
                if (buttonText.equals("X")) {
                    counterX++;
                }
                if (buttonText.equals("0")) {
                    counterO++;
                }
                if (counterX == 3) {
                    setTextResult("Победа");
                    return true;
                }
                if (counterO == 3) {
                    setTextResult("Поражение");
                    return true;
                }
                if ((i + 1) % 3 == 0) {
                    counterX = 0;
                    counterO = 0;
                }
            }
            for (int i = 0; i < 3; i++) {
                int count = 0;
                String buttonsText = buttons.get(i).getText();
                for (int j = i + 3; j < buttons.size(); j += 3) {
                    String buttonsText2 = buttons.get(j).getText();
                    if (!buttonsText.equals("")) {
                        if (buttonsText.equals(buttonsText2)) {
                            count++;
                        }
                    } else {
                        break;
                    }
                }
                if (count == 2) {
                    if (buttonsText.equals("X")) {
                        setTextResult("Победа");
                        return true;
                    }
                    setTextResult("Поражение");
                    return true;
                }
            }
            String winner = buttons.get(4).getText();
            if (!winner.equals("")) {
                if (buttons.get(0).getText().equals(winner) && buttons.get(8).getText().equals(winner)) {
                    if (winner.equals("X")) {
                        setTextResult("Победа");
                    } else {
                        setTextResult("Поражение");
                    }
                    return true;
                } else if (buttons.get(2).getText().equals(winner) && buttons.get(6).getText().equals(winner)) {
                    if (winner.equals("X")) {
                        setTextResult("Победа");
                    } else {
                        setTextResult("Поражение");
                    }
                    return true;
                }
            }
            if (spaceCount() == 0) {
                setTextResult("Ничья!");
                return true;
            }
            return false;
        }

        private int machineWinAvailable() {
            for (int i = 0; i < buttons.size(); i += 3) {
                int count = 0;
                for (int j = i; j < i + 3; j ++) {
                    if (buttons.get(j).getText().equals("0")) {
                        count++;
                    }
                    if (count == 2) {
                        j = i;
                        while (j < buttons.size()) {
                            if (buttons.get(j).getText().equals("")) {
                                return j;
                            }
                            j ++;
                        }
                    }
                }
            }
                for (int i = 0; i < 3; i ++) {
                    int count = 0;
                    for (int j = i; j < buttons.size(); j += 3) {
                        if (buttons.get(j).getText().equals("0")) {
                            count++;
                        }
                        if (count == 2) {
                            j = i;
                            while (j < buttons.size()) {
                                if (buttons.get(j).getText().equals("")) {
                                    return j;
                                }
                                j += 3;
                            }
                        }
                    }
                }
            if (buttons.get(4).getText().equals("0")) {
                if (buttons.get(0).getText().equals("0") && buttons.get(8).getText().equals("")) {
                    return 8;
                } else if (buttons.get(8).getText().equals("0") && buttons.get(0).getText().equals("")) {
                    return 0;
                } else if (buttons.get(6).getText().equals("0") && buttons.get(2).getText().equals("")) {
                    return 2;
                } else if (buttons.get(2).getText().equals("0") && buttons.get(6).getText().equals("")) {
                    return 6;
                }
            }
            return humanWinAvailable();
        }

        private int humanWinAvailable() {
            for (int i = 0; i < buttons.size(); i += 3) {
                int count = 0;
                for (int j = i; j < i + 3; j++) {
                    if (buttons.get(j).getText().equals("X")) {
                        count++;
                    }
                    if (count == 2) {
                        j = i;
                        while (j < buttons.size()) {
                            if (buttons.get(j).getText().equals("")) {
                                return j;
                            }
                            j ++;
                        }
                    }
                }
            }
            for (int i = 0; i < 3; i ++) {
                int count = 0;
                for (int j = i; j < buttons.size(); j += 3) {
                    if (buttons.get(j).getText().equals("X")) {
                        count++;
                    }
                    if (count == 2) {
                        j = i;
                        while (j < buttons.size()) {
                            if (buttons.get(j).getText().equals("")) {
                                return j;
                            }
                            j += 3;
                        }
                    }
                }
            }
            if (buttons.get(4).getText().equals("X")) {
                if (buttons.get(0).getText().equals("X")) {
                    return 6;
                } else if (buttons.get(8).getText().equals("X") && buttons.get(0).getText().equals("")) {
                    return 0;
                } else if (buttons.get(6).getText().equals("X") && buttons.get(2).getText().equals("")) {
                    return 2;
                } else if (buttons.get(2).getText().equals("X") && buttons.get(6).getText().equals("")) {
                    return 6;
                }
            }
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals("")) {
                    return i;
                }
            }
            return 10;
        }

        private int spaceCount() {
            int countSpace = 0;
            for (int i = 0; i < buttons.size(); i++) {
                if (buttons.get(i).getText().equals("")) {
                    countSpace++;
                }
            }
            return countSpace;
        }
    }
