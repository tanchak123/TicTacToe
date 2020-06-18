import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class TicTacToeSwing extends JPanel {
    private static final Font FONT = new Font("SanSerif", Font.BOLD, 20);
    private static JButton b1;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ButtonActionListener handler = new ButtonActionListener();
    private boolean human;
    private final JLabel textResult = new JLabel("", SwingConstants.CENTER);
    private String machineCharacter;
    private String humanCharacter;

    public TicTacToeSwing() {
        setLayout(null);
        setBackground(Color.CYAN);
        createChanger("Выбирай", "X", "0");
    }

    public class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (human) {
                if (!step(e)) {
                    return;
                }
                if (checkWin()) {
                    return;
                }
            }
            notHumanStep(e);
            checkWin();
        }
    }

    public class Menu implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            String butText = button.getText();
            if (butText.equals("X")
                    || butText.equals("0")) {
                if (butText.equals("X")) {
                    human = true;
                }
                humanCharacter = butText;
                machineCharacter = "0";
                if (humanCharacter.equals("0")) {
                    machineCharacter = "X";
                }
                removeAll();
                gameField();
                return;
            }
            if (butText.charAt(0) == 'Н') {
                reset();
            } else {
                setVisible(false);
                System.exit(0);
            }

        }
    }

    private void reset() {
        buttons = new ArrayList<>();
        human = false;
        removeAll();
        createChanger("Выбирай", "X", "0");
    }

    private void gameField() {
        int x = 10;
        int y = 10;
        setLayout(null);
        for (int i = 1; i <= 9; i++) {
            if (i == 5 && machineCharacter.equals("X")) {
                createButton("X", x, y);
                human = !human;
            } else {
                createButton("", x, y);
            }
            x += 100;
            if (i % 3 == 0) {
                y += 100;
                x = 10;
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
        b1.setFont(FONT);
        b1.addActionListener(handler);
        add(b1);
        b1.setBounds(x, y, 100, 100);
        b1.setFont(FONT.deriveFont(Font.BOLD, 50));
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
        createChanger(winner, "Новая игра", "Выйти");

    }

    private void createChanger(String tag, String first, String second) {
        b1 = new JButton(first);
        b1.setBounds(10, 160, 150, 100);
        b1.addActionListener(new Menu());
        if (first.equals("X")) {
            b1.setFont(FONT.deriveFont(Font.BOLD, 50));
        }
        add(b1);
        b1 = new JButton(second);
        b1.setBounds(160, 160, 150, 100);
        b1.addActionListener(new Menu());
        if (second.equals("0")) {
            b1.setFont(FONT.deriveFont(Font.BOLD, 50));
        }
        add(b1);
        textResult.setText(tag);
        int gap = 1;
        setLayout(new BorderLayout(gap, gap));
        setBorder(BorderFactory.createEmptyBorder(50, gap, gap, gap));
        add(textResult, BorderLayout.PAGE_START);
        textResult.setFont(FONT.deriveFont(Font.BOLD, 50));
    }

    private boolean checkWin() {
        int counterX = 0;
        int counterO = 0;
        for (int i = 0; i < buttons.size(); i++) {
            String buttonText = buttons.get(i).getText();
            if (buttonText.equals(humanCharacter)) {
                counterX++;
            }
            if (buttonText.equals(machineCharacter)) {
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
                if (buttonsText.equals(humanCharacter)) {
                    setTextResult("Победа");
                    return true;
                }
                setTextResult("Поражение");
                return true;
            }
        }
        String winner = buttons.get(4).getText();
        if (!winner.equals("")) {
            if (buttons.get(0).getText().equals(winner)
                    && buttons.get(8).getText().equals(winner)) {
                if (winner.equals(humanCharacter)) {
                    setTextResult("Победа");
                } else {
                    setTextResult("Поражение");
                }
                return true;
            } else if (buttons.get(2).getText().equals(winner)
                    && buttons.get(6).getText().equals(winner)) {
                if (winner.equals(humanCharacter)) {
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

    private void notHumanStep(ActionEvent e) {
        if (spaceCount() > 7) {
            if (buttons.get(4).getText().equals("")) {
                e.setSource(buttons.get(4));
            } else {
                e.setSource(buttons.get(8));
            }
        } else {
            int result = artificialIntelligenceStep(machineCharacter);
            if (result == - 1) {
                result = artificialIntelligenceStep(humanCharacter);
                if (result == -1) {
                    for (int i = 0; i < buttons.size(); i++) {
                        if (buttons.get(i).getText().equals("")) {
                            result = i;
                            break;
                        }
                    }
                }
            }
            e.setSource(buttons.get(result));
        }
        step(e);
    }

    private boolean step(ActionEvent e) {
        JButton jb = (JButton) e.getSource();
        if (jb.getText().equals("") && !jb.getText().equals("X") && !jb.getText().equals("0")) {
            if (human) {
                changeButtonCharacter(jb, humanCharacter);
            } else {
                changeButtonCharacter(jb, machineCharacter);
            }
            human = !human;
            return true;
        }
        return false;
    }

    private int artificialIntelligenceStep(String character) {
        for (int i = 0; i < buttons.size(); i += 3) {
            int count = 0;
            for (int j = i; j < i + 3; j++) {
                if (buttons.get(j).getText().equals(character)) {
                    count++;
                }
                if (count == 2) {
                    j = i;
                    while (j < i + 3) {
                        if (buttons.get(j).getText().equals("")) {
                            return j;
                        }
                        j++;
                    }
                }
            }
        }
        for (int i = 0; i < 3; i++) {
            int count = 0;
            for (int j = i; j < buttons.size(); j += 3) {
                if (buttons.get(j).getText().equals(character)) {
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
        if (buttons.get(4).getText().equals(character)) {
            for (int i = 0; i < 9; i += 6) {
                if (buttons.get(i).getText().equals(character)
                        && buttons.get(8 - i).getText().equals("")) {
                    return 8 - i;
                }
                if (i == 6) {
                    i = 2;
                }
            }
        }
        if (machineCharacter.equals("0") && buttons.get(0).getText().equals("X")
                && buttons.get(6).getText().equals("")
                && buttons.get(4).getText().equals("X")) {
            return 6;
        }
        return -1;
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
