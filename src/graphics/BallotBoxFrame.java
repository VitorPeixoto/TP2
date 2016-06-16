package graphics;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by Peixoto on 15/06/2016.
 */
public class BallotBoxFrame extends JFrame {
    BallotBoxPanel panel;
    public BallotBoxFrame(HashMap<Integer, String> mayorEntries, HashMap<Integer, String> councilmanEntries, Object lock) {
        panel = new BallotBoxPanel(mayorEntries, councilmanEntries, lock);
        this.add(panel);

        this.setSize(1222, 455);
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setBackground(new Color(0, 0, 0, 0));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    }

    public void setVisible(boolean bool) {
        if(bool) {
            panel.setDone(false);
            panel.setVereador(false);
        }
        super.setVisible(bool);
    }

    public boolean isDone() {
        return panel.isDone();
    }

    public int[] getVote() {
        return panel.getVoto();
    }
}
