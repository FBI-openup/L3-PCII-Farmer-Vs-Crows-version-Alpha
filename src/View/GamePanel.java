package View;

import Model.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    // SCREEN SETTINGS
    private final int originalTileSize = 16; // 16x16 pixels
    private final int scale = 3; // 3x scale

    private final int tileSize = originalTileSize * scale; // 48x48 pixels
    private final int maxScreenCol = 16; // 16 tiles wide
    private final int maxScreenRow = 12; // 12 tiles tall
    private final int screenWidth = tileSize * maxScreenCol; // 768 pixels wide
    private final int screenHeight = tileSize * maxScreenRow; // 576 pixels tall

    private final GameEngine gameEngine;

    // CONSTRUCTOR
    public GamePanel(GameEngine gameEngine) {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(new Color(0, 100, 0));
        this.setDoubleBuffered(true);
        this.gameEngine = gameEngine;
    }

    // DRAW
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Units unit : gameEngine.getUnits()) {
            if (unit instanceof Farmer) {
                g2d.setColor(Color.BLUE); // Set color for Farmer
            } else if (unit instanceof Crow) {
                g2d.setColor(Color.BLACK); // Set color for Crow
                // Draw a circle representing the safety distance of the crow centered at the crow's position
                g2d.drawOval(unit.getPosition().x - ((Crow) unit).getSafetyDistance() / 2, unit.getPosition().y - ((Crow) unit).getSafetyDistance() / 2, ((Crow) unit).getSafetyDistance(), ((Crow) unit).getSafetyDistance());
            } else if (unit instanceof Corn) {
                g2d.setColor(Color.YELLOW); // Set color for Corn
            } else if (unit instanceof Scarecrow) {
                g2d.setColor(Color.GRAY); // Set color for Scarecrow
            }
            Point position = unit.getPosition();
            g2d.fillRect(position.x, position.y, tileSize, tileSize);
        }
        g2d.dispose();
    }
}

/*
To replace the filled rectangle with an image, you need to load the image and then draw it in the `paintComponent` method. You can use the `ImageIO.read` method to load the image and the `Graphics2D.drawImage` method to draw it.

Here's how you can modify the `paintComponent` method in your `GamePanel` class:

```java
import javax.imageio.ImageIO;
import java.io.IOException;
import java.awt.image.BufferedImage;

// ...

public class GamePanel extends JPanel {
    // ...

    private BufferedImage farmerImage;
    private BufferedImage crowImage;
    private BufferedImage cornImage;
    private BufferedImage scarecrowImage;

    public GamePanel() {
        // ...

        try {
            farmerImage = ImageIO.read(getClass().getResource("/path/to/farmer/image.png"));
            crowImage = ImageIO.read(getClass().getResource("/path/to/crow/image.png"));
            cornImage = ImageIO.read(getClass().getResource("/path/to/corn/image.png"));
            scarecrowImage = ImageIO.read(getClass().getResource("/path/to/scarecrow/image.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (Units unit : gameEngine.getUnits()) {
            BufferedImage currentImage = null;
            if (unit instanceof Farmer) {
                currentImage = farmerImage;
            } else if (unit instanceof Crow) {
                currentImage = crowImage;
            } else if (unit instanceof Corn) {
                currentImage = cornImage;
            } else if (unit instanceof Scarecrow) {
                currentImage = scarecrowImage;
            }

            if (currentImage != null) {
                Point position = unit.getPosition();
                g2d.drawImage(currentImage, position.x, position.y, tileSize, tileSize, null);
            }
        }

        g2d.dispose();
    }
}
```

In this code, we first load the images for each type of unit in the `GamePanel` constructor. Then, in the `paintComponent` method, we select the appropriate image for each unit and draw it at the unit's position. The images are scaled to the size of a tile.

Please replace `"/path/to/farmer/image.png"` (and the other paths) with the actual paths to your images. The paths are relative to the project's root directory. If the images are not found, an `IOException` will be thrown.
 */


