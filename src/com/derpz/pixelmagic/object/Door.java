package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Door extends Entity {

    public Door(GamePanel gamePanel) {
        super(gamePanel);

        name = "Door";
        down1 = setup("/objects/door", gamePanel.tileSize, gamePanel.tileSize);
        collision = true;

        solidArea.x = 0;
        solidArea.y = 16;
        solidArea.width = 48;
        solidArea.height = 32;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
    }
}
