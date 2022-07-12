import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;



public class TestSim {
    public static void main(String[] args) {
        int xMax = 1000;
        int yMax = 600;
        // Scale to divide x by to get xy coords
        double scale = Math.pow(10, 6);

        ArrayList<AstroObject> planets = new ArrayList<>();
        planets.add(CustomAstroObject.Sun(500 * scale, 300 * scale));
        planets.add(CustomAstroObject.Earth(656 * scale, 300 * scale));


        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame();
                frame.setBounds(0, 0, xMax, yMax);
                frame.add(new TestCanvas(planets));
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
            for (AstroObject p : planets) {
                p.calculateVectors(planets);
                System.out.println(p.getVelocityVector().toString());
                p.updateCoords();
            }
            planets.removeIf(m -> m.getSize() == 0);
        }
    }


    static class TestCanvas extends JPanel {
        double scale = Math.pow(10, 6);
        private Timer timer;
        public ArrayList<AstroObject> astroObjects;

        public TestCanvas(ArrayList<AstroObject> astroObjects) {
            setBackground (Color.GRAY);
            this.astroObjects = astroObjects;
        }
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            for (AstroObject p : astroObjects) {
                g2d.setColor(p.getColor());
                g2d.fillOval((int)(p.getxPos() / scale), (int)(p.getyPos() / scale),
                (int) p.getSize(), (int) p.getSize());
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




