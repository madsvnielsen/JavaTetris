import java.awt.EventQueue;
import javax.swing.JFrame;

public class Tetris extends JFrame{
    public Tetris() {

        initUI();
    }
    private void initUI() {

        add(new TetrisGame());

        setResizable(false);
        pack();

        setTitle("Tetris Game");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {
            JFrame ex = new Tetris();
            ex.setVisible(true);
        });
    }
}