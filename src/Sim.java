import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.awt.*;
import java.util.ConcurrentModificationException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Sim {
    public static void main(String[] args) throws ConcurrentModificationException {
        ArrayList<AstroObject> astroObjects = new ArrayList<>();
        int XMax = 1000;
        int YMax = 600;
        for (int i = 0; i < 40; i++) {
            astroObjects.add(new RandomAstroObject(XMax, YMax));
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setBounds(0, 0, XMax, YMax);
                frame.add(new MyCanvas(astroObjects));
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
            for (AstroObject molX : astroObjects) {
                molX.calculateVectors(astroObjects);
                for (AstroObject molY : astroObjects) {
                    if (molX.collision(molY)) {
                        molY.setSize(0);
                        molY.setMass(0);
                    }
                }
            }
            for (AstroObject molX : astroObjects) {
                molX.updateCoords();
            }
            astroObjects.removeIf(m -> m.getSize() == 0);
        }
    }

    static class MyCanvas extends JPanel {
        private Timer timer;
        public ArrayList<AstroObject> astroObjects;

        public MyCanvas(ArrayList<AstroObject> astroObjects) {
            setBackground (Color.GRAY);
            this.astroObjects = astroObjects;
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            for (AstroObject m : astroObjects) {
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
                timer = new Timer(5, e -> repaint());
                timer.start();
            }
        }
    }
}
