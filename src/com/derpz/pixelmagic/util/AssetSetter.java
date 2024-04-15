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
        gamePanel.obj[0] = new Boots(gamePanel);
        gamePanel.obj[0].worldX = gamePanel.tileSize * 37;
        gamePanel.obj[0].worldY = gamePanel.tileSize * 42;

        gamePanel.obj[1]  = new Boots(gamePanel);
        gamePanel.obj[1].worldX  = gamePanel.tileSize * 25;
        gamePanel.obj[1].worldY  = gamePanel.tileSize * 19;
    }

    public void setNPC() {
        gamePanel.npc[0] = new NPCWizard(gamePanel);
        gamePanel.npc[0].worldX = gamePanel.tileSize * 21;
        gamePanel.npc[0].worldY = gamePanel.tileSize * 21;
    }

    public void setMonster() {
        int i = 0;
        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 21;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 38;
        i++;

        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 23;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
        i++;

        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 24;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 37;
        i++;

        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 34;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
        i++;

        gamePanel.monster[i] = new GreenSlime(gamePanel);
        gamePanel.monster[i].worldX = gamePanel.tileSize * 38;
        gamePanel.monster[i].worldY = gamePanel.tileSize * 42;
        i++;
    }

}
