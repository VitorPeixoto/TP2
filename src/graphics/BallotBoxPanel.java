package graphics;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by Vitor on 15/06/2016.
 */
public class BallotBoxPanel extends JComponent {
    private Image ballotBoxImage;
    private JLabel number1, number2, number3, number4,
                   candidateLabel,
                   nomeLabel,
                   partidoLabel;

    private JLabel[] numPads = new JLabel[10];

    private JLabel brancoLabel,
                   corrigeLabel,
                   confirmaLabel;

    private boolean vereador = false, done = false;

    private HashMap<Integer, String> mayorEntries,
                                     councilmanEntries;

    private Object lock;
    private int[] voto = new int[2];

    public BallotBoxPanel(HashMap<Integer, String> mayorEntries, HashMap<Integer, String> councilmanEntries, Object lock) {
        this.mayorEntries      = mayorEntries;
        this.councilmanEntries = councilmanEntries;
        this.lock            = lock;

        number1        = new JLabel("", SwingConstants.CENTER);
        number2        = new JLabel("", SwingConstants.CENTER);
        number3        = new JLabel("", SwingConstants.CENTER);
        number4        = new JLabel("", SwingConstants.CENTER);
        candidateLabel = new JLabel("Prefeito");
        nomeLabel      = new JLabel("");
        partidoLabel   = new JLabel("");
        brancoLabel    = new JLabel("");
        corrigeLabel   = new JLabel("");
        confirmaLabel  = new JLabel("");

        nomeLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        partidoLabel.setVerticalAlignment(SwingConstants.BOTTOM);

        number1.setBounds(286, 178, 50, 50);
        number2.setBounds(345, 178, 50, 50);
        number3.setBounds(404, 178, 50, 50);
        number4.setBounds(463, 178, 50, 50);
        candidateLabel.setBounds(187, 91, 250, 35);
        nomeLabel.setBounds(275, 240, 300, 35);
        partidoLabel.setBounds(275, 290, 100, 35);
        brancoLabel.setBounds(825, 349, 96, 48);
        corrigeLabel.setBounds(940, 349, 99, 47);
        confirmaLabel.setBounds(1055, 335, 101, 59);

        number1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number4.setBorder(BorderFactory.createLineBorder(Color.black, 3));

        number1.setFont(numbersFont);
        number2.setFont(numbersFont);
        number3.setFont(numbersFont);
        number4.setFont(numbersFont);
        candidateLabel.setFont(candidateFont);
        nomeLabel.setFont(nomeFont);
        partidoLabel.setFont(partidoFont);

        brancoLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                branco();
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        corrigeLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                corrige();
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });
        confirmaLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                confirma();
            }
            public void mousePressed(MouseEvent e) {}
            public void mouseReleased(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseExited(MouseEvent e) {}
        });

        try {
            ballotBoxImage = ImageIO.read(new File(getClass().getResource("/graphics/images/BallotBox.png").getPath()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(null);
        this.add(number1);
        this.add(number2);
        this.add(number3);
        this.add(number4);
        this.add(candidateLabel);
        this.add(nomeLabel);
        this.add(partidoLabel);
        this.add(brancoLabel);
        this.add(corrigeLabel);
        this.add(confirmaLabel);

        number3.setVisible(false);
        number4.setVisible(false);
        initNumPad();
    }

    public void nextNumber(int number) {
        nomeLabel.setText("");
        if(number1.getText().equals("")) {
            number1.setText(""+number);
        }
        else if(number2.getText().equals("")) {
            number2.setText(""+number);
        }
        else if(vereador && number3.getText().equals("")) {
            number3.setText(""+number);
        }
        else if(vereador && number4.getText().equals("")) {
            number4.setText(""+number);
        }
        if(!vereador) {
            Integer i = Integer.parseInt(number1.getText()+number2.getText());
            if(mayorEntries.keySet().contains(i)) {
                nomeLabel.setText(mayorEntries.get(i));
            }
        }
        else {
            Integer i = Integer.parseInt(number1.getText()+number2.getText()+number3.getText()+number4.getText());
            if(councilmanEntries.keySet().contains(i)) {
                nomeLabel.setText(councilmanEntries.get(i));
            }
        }
        number1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number4.setBorder(BorderFactory.createLineBorder(Color.black, 3));
    }

    public void corrige() {
        number1.setText("");
        number2.setText("");
        number3.setText("");
        number4.setText("");
        number1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number4.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        nomeLabel.setText("");
    }

    public void branco() {
        number1.setText("9");
        number2.setText("9");
        number3.setText("9");
        number4.setText("9");
        number1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        number4.setBorder(BorderFactory.createLineBorder(Color.black, 3));
        nomeLabel.setText("");
    }

    public void confirma() {
        if(!vereador) {
            if(number2.getText().equals("")) {
                if(number1.getText().equals("")) {
                    number1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                }
                number2.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                return;
            }
            vereador = true;
            number3.setVisible(true);
            number4.setVisible(true);
            number1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            voto[0] = Integer.parseInt(number1.getText()+number2.getText());
            corrige();
            candidateLabel.setText("Vereador");
        }
        else {
            if(number4.getText().equals("")) {
                number4.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                if(number3.getText().equals("")) {
                    number3.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                    if(number2.getText().equals("")) {
                        number2.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                        if(number1.getText().equals("")) {
                            number1.setBorder(BorderFactory.createLineBorder(Color.red, 3));
                        }
                    }
                }
                return;
            }
            number1.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            number3.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            number2.setBorder(BorderFactory.createLineBorder(Color.black, 3));
            voto[1] = Integer.parseInt(number1.getText()+number2.getText()+number3.getText()+number4.getText());
            corrige();
            done = true;
            synchronized (lock) {
                lock.notify();
            }
        }
    }

    public void initNumPad() {
        for(int i = 0; i < numPads.length; i++) {
            numPads[i] = new JLabel();
            numPads[i].setSize(60, 45);
            numPads[i].addMouseListener(new NumpadListener(i));
        }
        numPads[1].setLocation(851,  104);
        numPads[2].setLocation(934,  104);
        numPads[3].setLocation(1020, 104);
        numPads[4].setLocation(857,  161);
        numPads[5].setLocation(943,  161);
        numPads[6].setLocation(1026, 161);
        numPads[7].setLocation(863,  223);
        numPads[8].setLocation(947,  223);
        numPads[9].setLocation(1033, 223);
        numPads[0].setLocation(955,  286);

        this.add(numPads[1]);
        this.add(numPads[2]);
        this.add(numPads[3]);
        this.add(numPads[4]);
        this.add(numPads[5]);
        this.add(numPads[6]);
        this.add(numPads[7]);
        this.add(numPads[8]);
        this.add(numPads[9]);
        this.add(numPads[0]);
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void setVereador(boolean vereador) {
        this.vereador = vereador;
        number3.setVisible(vereador);
        number4.setVisible(vereador);
        corrige();
        candidateLabel.setText(vereador ? "Vereador" : "Prefeito");
    }

    public int[] getVoto() {
        return voto;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(ballotBoxImage, 0, 0, null);
    }

    private Font numbersFont   = new Font("Dialog", Font.BOLD, 45),
                 candidateFont = new Font("Dialog", Font.BOLD, 48),
                 nomeFont      = new Font("Dialog", Font.BOLD, 20),
                 partidoFont   = new Font("Dialog", Font.BOLD, 30);

    private class NumpadListener implements MouseListener {
        private final int number;

        public NumpadListener(int number) {
            this.number = number;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            nextNumber(number);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}