package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends SuperObject {
    GamePanel gamePanel;
    public Chest(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Chest";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/chest.png")));
            imageScale.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}
