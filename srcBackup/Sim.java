import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.*;
import java.util.ConcurrentModificationException;
import java.util.Objects;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Sim {
    public static void main(String[] args) throws ConcurrentModificationException {
        ArrayList<Molecule> molecules = new ArrayList<>();
        int XMax = 1000;
        int YMax = 600;
        for (int i = 0; i < 40; i++) {
            molecules.add(new RandomMolecule(XMax, YMax));
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setBounds(0, 0, 1000, 600);
                frame.add(new MyCanvas(molecules));
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
                frame.addWindowListener(new WindowAdapter() {
                    public void windowClosing(WindowEvent we) {
                        System.exit(0);
                    }
                });
            }
        });
        while (true) {
            for (Molecule molX : molecules) {
                molX.calculateVectors(molecules);
                for (Molecule molY : molecules) {
                    if (molX.collision(molY)) {
                        molY.setSize(0);
                        molY.setMass(0);
                    }
                }
            }
            for (Molecule molX : molecules) {
                if (!molX.isImmobile()) {
                    molX.updateCoords();
//                    if ((molX.getxPos() > XMax) || (molX.getxPos() < 0) ||
//                        (molX.getyPos() > YMax) || (molX.getyPos() < 0)) {
//                        Vector.reverseVel(molX);
//                    }
                }
            }
            molecules.removeIf(m -> m.getSize() == 0);
        }
    }

    static class MyCanvas extends JPanel {
        private Timer timer;
        public ArrayList<Molecule> molecules;

        public MyCanvas(ArrayList<Molecule> molecules) {
            setBackground (Color.GRAY);
            this.molecules = molecules;
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            for (Molecule m : molecules) {
                g2d.setColor(m.getColor());
                g2d.fillOval((int) m.getxPos(), (int) m.getyPos(), (int) m.getSize(), (int) m.getSize());
            }
            g2d.dispose();
        }
        @Override
        public void setLayout(LayoutManager mgr) {
            super.setLayout(null);
        }
        @Override
        public Dimension getPreferredSize() {
            return new Dimension(1000, 600);
        }
        @Override
        public void addNotify() {
            super.addNotify(); //To change body of generated methods, choose Tools | Templates.
            if (timer == null) {
                timer = new Timer(2, e -> repaint());
                timer.start();
            }
        }
    }
}
