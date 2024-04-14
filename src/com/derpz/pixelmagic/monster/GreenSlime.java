package com.derpz.pixelmagic.monster;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

import java.util.Random;

public class GreenSlime extends Entity {
    GamePanel gamePanel;

    public GreenSlime(GamePanel gamePanel) {
        super(gamePanel);
        this.gamePanel = gamePanel;

        type = 2;
        name = "Green Slime";
        speed = 1;
        maxLife = 4;
        life = maxLife;

        solidArea.x = 3;
        solidArea.y = 18;
        solidArea. width = 42;
        solidArea.height = 30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }

    public void getImage() {
        up1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/monster/greenslime_down_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/monster/greenslime_down_2", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void setAction() {
        actionCounter ++;
        if(actionCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionCounter = 0;
        }
    }

}
