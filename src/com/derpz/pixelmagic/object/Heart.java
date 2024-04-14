package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Heart extends Entity {

    public Heart(GamePanel gamePanel) {
        super(gamePanel);

        name = "Heart";
        image = setup("/objects/heart_full", gamePanel.tileSize, gamePanel.tileSize);
        image2 = setup("/objects/heart_half", gamePanel.tileSize, gamePanel.tileSize);
        image3 = setup("/objects/heart_blank", gamePanel.tileSize, gamePanel.tileSize);
    }
}
