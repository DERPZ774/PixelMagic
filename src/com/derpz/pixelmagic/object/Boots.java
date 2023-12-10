package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends SuperObject{
    GamePanel gamePanel;
    public Boots(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        name = "Boots";
        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/objects/boots.png")));
            imageScale.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        solidArea.x = 5;
    }
}
