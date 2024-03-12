package view;

import control.MouseLis;
import model.units.Farmer;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {
    private final Farmer farmer;

    public GamePanel(Farmer farmer) {
        this.farmer = farmer;
        setPreferredSize(new Dimension(200, 200)); // 设置面板大小
        setBackground(Color.WHITE); // 设置背景色

        MouseLis mouseListener = new MouseLis(farmer);
        addMouseListener(mouseListener);
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 绘制农夫
        Point pos = farmer.getPosition();
        g.setColor(farmer.isSelected() ? Color.GREEN : Color.RED);
        g.fillOval(pos.x - 10, pos.y - 10, 20, 20); // represent the farmer as a circle
        MouseLis mouseListener = new MouseLis(farmer);
        // if the farmer has a destination, draw a line to it
        if (farmer.getDestination() != null) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(Color.GREEN);
            g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5.0f}, 0.0f));
            g2d.drawLine(pos.x, pos.y, farmer.getDestination().x, farmer.getDestination().y);
            g2d.dispose();
        }
    }
}
