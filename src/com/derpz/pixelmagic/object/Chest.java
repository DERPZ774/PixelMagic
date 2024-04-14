package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Chest extends Entity {

    public Chest(GamePanel gamePanel) {
        super(gamePanel);
        name = "Chest";
        down1 = setup("/objects/chest", gamePanel.tileSize, gamePanel.tileSize);
    }
}
