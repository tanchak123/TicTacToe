import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

public class TicTacToeSwing extends JPanel{
    private static Scanner sc = new Scanner(System.in);
    private static Font font = new Font("SanSerif", Font.BOLD, 20);
    private static JButton b1;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private ButtonActionListener handler = new ButtonActionListener();
    private boolean flag = false;
    private JLabel textResult = new JLabel("", SwingConstants.CENTER);


    public TicTacToeSwing() {
        int x = 10;
        int y = 10;
        setLayout(null);
        JButton jButton1 = new JButton("X");
        for (int i = 1; i <= 9; i++) {
            createButton("", x, y);
            x += 100;
            if (i % 3 == 0) {
                y += 100;
                x = 10;
            }
        }
        for (int i = 0; i < buttons.size(); i++) {
            System.out.println(buttons.get(i).getX() + " " + buttons.get(i).getY());
        }

    }

    public class ButtonActionListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            String character = "X";
            if (flag) {
                character = "0";
            }
            JButton button = (JButton) e.getSource();
            if (button.getText().equals("")) {
                button.setText(character);
                add(button);
                updateButton(button);
            }
            checkWin();
            flag = !flag;
        }
    }

    private void createButton(String colum, int x, int y) {
        b1 = new JButton(colum);
        b1.setFont(font);
        b1.addActionListener(handler);
        add(b1);
        b1.setBounds(x, y, 100, 100);
        b1.setFont(font.deriveFont(Font.BOLD,50));
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
        textResult.setFont(font.deriveFont(Font.BOLD,50));
    }

    private void checkWin() {
        int counterX = 0;
        int counterO = 0;
        int countSpace = 0;
        for (int i = 0; i < buttons.size(); i++) {
            String buttonText = buttons.get(i).getText();
            if (buttonText.equals("X")) {
                counterX++;
            }
            if (buttonText.equals("0")) {
                counterO++;
            }
            if (counterX == 3) {
                setTextResult("Поздравляем вы победили");
                return;
            }
            if (counterO == 3) {
                setTextResult("Виртуальный интеллект победил");
                return;
            }
            if ((i + 1) % 3 == 0) {
                counterX = 0;
                counterO = 0;
            }
            if (buttonText.equals("")) {
                countSpace++;
            }
        }
        for (int i = 0; i < 3; i ++) {
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
                    setTextResult("Поздравляем вы победили");
                    return;
                }
                setTextResult("Виртуальный интеллект победил");
                return;
            }
        }
        String winner = buttons.get(4).getText();
        if (!winner.equals("")) {
            if (buttons.get(0).getText().equals(winner) && buttons.get(8).getText().equals(winner)) {
                if (winner.equals("X")) {
                    setTextResult("Поздравляем вы победили");
                } else {
                    setTextResult("Виртуальный интеллект победил");
                }
            } else if (buttons.get(2).getText().equals(winner) && buttons.get(6).getText().equals(winner)) {
                if (winner.equals("X")) {
                    setTextResult("Поздравляем вы победили");
                } else {
                    setTextResult("Виртуальный интеллект победил");
                }
            }
        }
        if (countSpace == 0) {
            setTextResult("Ничья!");
        }
    }
}
