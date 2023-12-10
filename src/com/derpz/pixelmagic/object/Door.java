package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends SuperObject {
    GamePanel gamePanel;
    public Door(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Door";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/door.png")));
            imageScale.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        collision = true;
    }
}
