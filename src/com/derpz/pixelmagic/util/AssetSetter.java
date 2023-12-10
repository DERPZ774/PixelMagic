package com.derpz.pixelmagic.util;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.object.Boots;
import com.derpz.pixelmagic.object.Chest;
import com.derpz.pixelmagic.object.Door;
import com.derpz.pixelmagic.object.Key;

public class AssetSetter {
    GamePanel gamePanel;
    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

    }

    public void setObject() {
        gamePanel.obj[0] = new Key(gamePanel);
        gamePanel.obj[0].worldX = 23 * gamePanel.tileSize;
        gamePanel.obj[0].worldY = 7 * gamePanel.tileSize;

        gamePanel.obj[1] = new Key(gamePanel);
        gamePanel.obj[1].worldX = 23 * gamePanel.tileSize;
        gamePanel.obj[1].worldY = 40 * gamePanel.tileSize;

        gamePanel.obj[2] = new Key(gamePanel);
        gamePanel.obj[2].worldX = 37 * gamePanel.tileSize;
        gamePanel.obj[2].worldY = 7 * gamePanel.tileSize;

        gamePanel.obj[3] = new Door(gamePanel);
        gamePanel.obj[3].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[3].worldY = 11 * gamePanel.tileSize;

        gamePanel.obj[4] = new Door(gamePanel);
        gamePanel.obj[4].worldX = 8 * gamePanel.tileSize;
        gamePanel.obj[4].worldY = 28 * gamePanel.tileSize;

        gamePanel.obj[5] = new Door(gamePanel);
        gamePanel.obj[5].worldX = 12 * gamePanel.tileSize;
        gamePanel.obj[5].worldY = 22 * gamePanel.tileSize;

        gamePanel.obj[6] = new Chest(gamePanel);
        gamePanel.obj[6].worldX = 10 * gamePanel.tileSize;
        gamePanel.obj[6].worldY = 7 * gamePanel.tileSize;

        gamePanel.obj[7] = new Boots(gamePanel);
        gamePanel.obj[7].worldX = 37 * gamePanel.tileSize;
        gamePanel.obj[7].worldY = 42 * gamePanel.tileSize;
    }

}
