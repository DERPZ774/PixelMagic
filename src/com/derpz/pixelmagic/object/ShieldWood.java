package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

public class ShieldWood extends Entity {
    public ShieldWood(GamePanel gamePanel) {
        super(gamePanel);

        name = "Wood Shield";
        down1 = setup("/objects/shield_wood", gamePanel.tileSize, gamePanel.tileSize);
        defenseValue = 1;
    }
}
