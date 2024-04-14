package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Boots extends Entity {

    public Boots(GamePanel gamePanel) {
        super(gamePanel);
        name = "Boots";
        down1 = setup("/objects/boots", gamePanel.tileSize, gamePanel.tileSize);
    }
}
