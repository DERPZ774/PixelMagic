package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

public class SwordNormal extends Entity {
    public SwordNormal(GamePanel gamePanel) {
        super(gamePanel);
        name = "Normal Sword";
        down1 = setup("/objects/sword_normal", gamePanel.tileSize, gamePanel.tileSize);
        attackValue = 1;
    }
}
