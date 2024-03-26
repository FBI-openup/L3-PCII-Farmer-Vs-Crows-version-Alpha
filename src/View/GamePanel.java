package View;

import Controller.FarmerMouseListener;
import Model.*;
import View.*;

import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;


public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 pixels
    private final int scale = 3; // 3x scale

    private final int tileSize = originalTileSize * scale; // 48x48 pixels
    private final int maxScreenCol = 16; // 16 tiles wide
    private final int maxScreenRow = 12; // 12 tiles tall
    private final int screenWidth = tileSize * maxScreenCol; // 768 pixels wide
    private final int screenHeight = tileSize * maxScreenRow; // 576 pixels tall
    private BufferedImage farmerImage;
    private BufferedImage crowImage;
    private BufferedImage cornImage;
    private BufferedImage scarecrowImage;
    private BufferedImage backgroundImage;

    private final GameEngine gameEngine;

    // CONSTRUCTOR
    public GamePanel(GameEngine gameEngine) {

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.gameEngine = gameEngine;
        this.setLayout(new BorderLayout());
        /*backgroundPanel = new BackgroundPanel("images/map.png");
        this.add(backgroundPanel, BorderLayout.CENTER);*/
        this.setDoubleBuffered(true);

        try {
            backgroundImage = ImageIO.read(new File("images/map.png"));
            farmerImage = ImageIO.read(new File("images/farmer.png").getAbsoluteFile());
            crowImage = ImageIO.read(new File("images/crow.png").getAbsoluteFile());
            cornImage = ImageIO.read(new File("images/corn.png").getAbsoluteFile());
            scarecrowImage = ImageIO.read(new File("images/scarecrow2.png").getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            try {
                backgroundImage = Toolkit.getDefaultToolkit().getImage(imagePath);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    // DRAW
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }

        Graphics2D g2d = (Graphics2D) g;

        //for (Units unit : gameEngine.getUnits()) { /// ISSUE : THREAD SAFETY
        for (int i = 0; i < gameEngine.getUnits().size(); i++) {
            Units unit = gameEngine.getUnits().get(i);
            BufferedImage currentImage = null;
            Point position = unit.getPosition();
            // Draw the farmer
            if (unit instanceof Farmer) {
                currentImage = farmerImage;
                FarmerMouseListener farmerMouseListener = new FarmerMouseListener(gameEngine);
                this.addMouseListener(farmerMouseListener);
                g.setColor(((Farmer) unit).isSelected() ? Color.BLUE : Color.RED);
                g.fillOval(position.x - 48, position.y - 48, tileSize * 2, tileSize * 2);
                g.setColor(Color.BLACK);
                g.drawOval(position.x- ((Farmer) unit).getScareRange()/2, position.y- ((Farmer) unit).getScareRange()/2, ((Farmer) unit).getScareRange(), ((Farmer) unit).getScareRange()); // represent the farmer as a circle
                // Draw a line from the farmer to the destination
                if (((Farmer) unit).getDestination() != null) {
                    g2d.setColor(Color.BLACK);
                    //draw a line from the farmer to the destination
                    g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1.0f, new float[]{5.0f}, 0.0f));
                    g2d.drawLine(position.x, position.y, ((Farmer) unit).getDestination().x, ((Farmer) unit).getDestination().y);
                }
                g2d.drawImage(currentImage, position.x - 48, position.y - 48, tileSize * 2, tileSize * 2, null);
            }
            // Draw the crow
            else if (unit instanceof Crow) {
                currentImage = crowImage;
                g2d.drawImage(currentImage, position.x - 24, position.y - 24, tileSize, tileSize, null);
                // Draw a circle representing the safety distance of the crow centered at the crow's position
                g2d.setColor(Color.BLACK);
                g2d.drawOval(position.x - ((Crow) unit).getSafetyDistance() / 2, position.y - ((Crow) unit).getSafetyDistance() / 2, ((Crow) unit).getSafetyDistance(), ((Crow) unit).getSafetyDistance());
            }
            // Draw the corn
            else if (unit instanceof Corn) {
                currentImage = cornImage;
                g2d.drawImage(currentImage, position.x - 24, position.y - 24, tileSize, tileSize, null);
                // TODO : Draw a progress bar representing the growth of the corn
            }
            // Draw the scarecrow
            else if (unit instanceof Scarecrow) {
                currentImage = scarecrowImage;
                if (((Scarecrow) unit).isTaken()) {
                    g2d.drawImage(currentImage, position.x - 36 - tileSize / 2, position.y - 36 - tileSize / 2, (int) ((int) tileSize * 1.5), (int) ((int) tileSize * 1.5), null);
                }
                else {
                    g2d.drawImage(currentImage, position.x - 48, position.y - 48, tileSize * 2, tileSize * 2, null);
                    // Draw a circle representing the safety distance of the scarecrow centered at the scarecrow's position
                    g.setColor(Color.BLACK);
                    g.drawOval(position.x - ((Scarecrow) unit).getEfficiencyRange() / 2, position.y - ((Scarecrow) unit).getEfficiencyRange() / 2, ((Scarecrow) unit).getEfficiencyRange(), ((Scarecrow) unit).getEfficiencyRange());
                }
            }
        }
        g2d.dispose();
    }
}