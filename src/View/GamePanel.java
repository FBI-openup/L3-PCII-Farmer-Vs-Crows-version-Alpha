package View;

import Model.*;

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


    private final GameEngine gameEngine;

    // CONSTRUCTOR
    public GamePanel(GameEngine gameEngine) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0, 100, 0));
        this.setDoubleBuffered(true);
        this.gameEngine = gameEngine;

        try {
            farmerImage = ImageIO.read(new File("images/farmer.png").getAbsoluteFile());
            crowImage = ImageIO.read(new File("images/crow.png").getAbsoluteFile());
            cornImage = ImageIO.read(new File("images/corn.png").getAbsoluteFile());
            scarecrowImage = ImageIO.read(new File("images/scarecrow2.png").getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // DRAW
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        // Draw the units
        for (Units unit : gameEngine.getUnits()) {
            BufferedImage currentImage = null;
            Point position = unit.getPosition();
            if (unit instanceof Farmer) {
                currentImage = farmerImage;
                g2d.drawImage(currentImage, position.x - 48, position.y - 48, tileSize * 2, tileSize * 2, null);
            } else if (unit instanceof Crow) {
                currentImage = crowImage;
                g2d.drawImage(currentImage, position.x - 24, position.y - 24, tileSize, tileSize, null);
                // Draw a circle representing the safety distance of the crow centered at the crow's position
                g2d.drawOval(unit.getPosition().x - ((Crow) unit).getSafetyDistance(), unit.getPosition().y - ((Crow) unit).getSafetyDistance(), ((Crow) unit).getSafetyDistance() * 2, ((Crow) unit).getSafetyDistance() * 2);
            } else if (unit instanceof Corn) {
                currentImage = cornImage;
                g2d.drawImage(currentImage, position.x - 24, position.y - 24, tileSize, tileSize, null);
            } else if (unit instanceof Scarecrow) {
                currentImage = scarecrowImage;
                g2d.drawImage(currentImage, position.x - 48, position.y - 48, tileSize * 2, tileSize * 2, null);
            }
        }
        g2d.dispose();
    }
}


