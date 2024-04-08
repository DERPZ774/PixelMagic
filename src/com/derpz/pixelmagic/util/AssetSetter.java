package com.derpz.pixelmagic.util;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.NPCWizard;
import com.derpz.pixelmagic.monster.GreenSlime;
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
        gamePanel.obj[0].worldX = gamePanel.tileSize * 23;
        gamePanel.obj[0].worldY = gamePanel.tileSize * 7;

        gamePanel.obj[1] = new Key(gamePanel);
        gamePanel.obj[1].worldX = gamePanel.tileSize * 23;
        gamePanel.obj[1].worldY = gamePanel.tileSize * 40;

        gamePanel.obj[2] = new Key(gamePanel);
        gamePanel.obj[2].worldX = gamePanel.tileSize * 38;
        gamePanel.obj[2].worldY = gamePanel.tileSize * 8;

        gamePanel.obj[3] = new Door(gamePanel);
        gamePanel.obj[3].worldX = gamePanel.tileSize * 10;
        gamePanel.obj[3].worldY = gamePanel.tileSize * 12;

        gamePanel.obj[4] = new Door(gamePanel);
        gamePanel.obj[4].worldX = gamePanel.tileSize * 8;
        gamePanel.obj[4].worldY = gamePanel.tileSize * 28;

        gamePanel.obj[5] = new Door(gamePanel);
        gamePanel.obj[5].worldX = gamePanel.tileSize * 12;
        gamePanel.obj[5].worldY = gamePanel.tileSize * 23;

        gamePanel.obj[6] = new Chest(gamePanel);
        gamePanel.obj[6].worldX = gamePanel.tileSize * 10;
        gamePanel.obj[6].worldY = gamePanel.tileSize * 8;

        gamePanel.obj[7] = new Boots(gamePanel);
        gamePanel.obj[7].worldX = gamePanel.tileSize * 37;
        gamePanel.obj[7].worldY = gamePanel.tileSize * 42;
    }

    public void setNPC() {
        gamePanel.npc[0] = new NPCWizard(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {
        gamePanel.monster[0] = new GreenSlime(gamePanel);
        gamePanel.monster[0].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[0].worldY = gamePanel.tileSize * 36;

        gamePanel.monster[1] = new GreenSlime(gamePanel);
        gamePanel.monster[1].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[1].worldY = gamePanel.tileSize * 37;
    }

}
