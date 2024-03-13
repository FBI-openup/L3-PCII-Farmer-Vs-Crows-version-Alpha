package view;

import control.MouseLis;
import model.units.Farmer;
import model.units.Corn;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GamePanel extends JPanel {
    private final Farmer farmer;
    private final ArrayList<Corn> corns= generateCorns(5);//generate 5 corns;
    public GamePanel(Farmer farmer) {
        this.farmer = farmer;
        setPreferredSize(new Dimension(200, 200)); //set the size of the panel
        setBackground(Color.WHITE); //set the background color

        MouseLis mouseListener = new MouseLis(farmer);
        addMouseListener(mouseListener);
    }
    //draw the corns
    public ArrayList<Corn> generateCorns(int numberOfCorns) {
        ArrayList<Corn> corns = new ArrayList<>();
        int attempts = 0;
        while (corns.size() < numberOfCorns && attempts < 1000) {
            int x = (int) (Math.random() * 800) + 100;// leave some space on the left and right
            int y = (int) (Math.random() * 600) + 100; // to prevent the corns from being drawn too close to the edge
            boolean tooClose = false;
            for (Corn corn : corns) {
                Point pos = corn.getPosition();
                if (Math.abs(pos.x - x) < 100 || Math.abs(pos.y - y) < 100) {
                    tooClose = true;
                    break;
                }
            }
            if (!tooClose) {
                corns.add(new Corn(new Point(x, y)));
            }
            attempts++;
        }
        return corns;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // draw the farmer
        Point pos = farmer.getPosition();
        g.setColor(farmer.isSelected() ? Color.GREEN : Color.RED);
        g.fillOval(pos.x - 10, pos.y - 10, 20, 20); // represent the farmer as a circle
        int destiX=farmer.getDestination().x;
        int destiY=farmer.getDestination().y;
        g.setColor(Color.BLUE);
        g.fillOval(destiX-5, destiY-5, 10, 10); // represent the farmer as a circle
        MouseLis mouseListener = new MouseLis(farmer);
        // if the farmer has a destination, draw a line to it
        if (farmer.getDestination() != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GREEN);
            //draw a line from the farmer to the destination
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5.0f}, 0.0f));
            g2d.drawLine(pos.x, pos.y, destiX, destiY);
            g2d.dispose();
        }
        //draw the corns

        for (Corn corn : corns) {
            Point posi = corn.getPosition();
            g.setColor(Color.YELLOW);
            g.fillOval(posi.x - 5, posi.y - 5, 10, 20);
            g.setColor(Color.BLACK);
            g.drawOval(posi.x - 5, posi.y - 5, 10, 20);//highlight the corns
        }
    }

}
