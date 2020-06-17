import javax.swing.*;

public class Start {
    private JFrame window;

    public Start() {
        this.window = new JFrame("Calculator");
        window.setSize(337, 360);
        window.setLocationRelativeTo(null);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.add(new TicTacToeSwing());
        window.setVisible(true);
        window.setResizable(false);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Start::new);
    }
}
