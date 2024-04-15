package com.derpz.pixelmagic.UI;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;
import com.derpz.pixelmagic.object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class UI {
    GamePanel gamePanel;
    Graphics2D g2;
    Font mauraMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;
    public boolean messageOn = false;
    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public int slotCol = 0;
    public int slotRow = 0;


    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        try {
            InputStream inputStream = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
            assert inputStream != null;
            mauraMonica = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            //example of how to import another font
            inputStream = getClass().getResourceAsStream("/font/Purisa Bold.ttf");
            assert inputStream != null;
            purisaB = Font.createFont(Font.TRUETYPE_FONT, inputStream);
        }catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        //Hud obj
        Entity heart = new Heart(gamePanel);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(mauraMonica);
        g2.setColor(Color.white);

        //title state
        if(gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }

        //play state
        if(gamePanel.gameState == gamePanel.playState) {
            //Play state stuff
            drawPlayerLife();
            drawMessage();
        }

        //pause state
        if(gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        //dialogue state
        if(gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        //Character state
        if (gamePanel.gameState == gamePanel.characterState) {
            drawCharacterScreen();
            drawInventory();
        }

    }

    public void drawPlayerLife() {

        int x = gamePanel.tileSize / 2;
        int y = gamePanel.tileSize / 2;
        int i = 0;

        //Max Life
        while (i < gamePanel.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gamePanel.tileSize;
        }
        //reset
        x = gamePanel.tileSize / 2;
        y = gamePanel.tileSize / 2;
        i = 0;

        //current life
        while (i < gamePanel.player.life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < gamePanel.player.life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gamePanel.tileSize;
        }
    }

    public void drawMessage() {
        int messageX = gamePanel.tileSize;
        int messageY = gamePanel.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if(message.get(i) != null) {
                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);
                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; //messageCounter++
                messageCounter.set(i, counter); //set counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) {
                    message.remove(i);
                    messageCounter.remove(i);
                }
            }
        }
    }

    public void drawTitleScreen() {
        if (titleScreenState == 0) {
            //Title background
            g2.setColor(new Color(59, 15, 87));
            g2.fillRect(0, 0, gamePanel.screenWidth, gamePanel.screenHeight);

            //Title name
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 96F));
            String text = "Shadow Wizard";
            int x = getXCenteredText(text);
            int y = gamePanel.tileSize * 3;

            //Shadow
            g2.setColor(Color.DARK_GRAY);
            g2.drawString(text, x + 4, y+ 4);


            //Main color
            g2.setColor(Color.BLACK);
            g2.drawString(text ,x, y);

            //Player image
            x = gamePanel.screenWidth / 2 - (gamePanel.tileSize * 2) / 2;
            y += gamePanel.tileSize * 2;
            g2.drawImage(gamePanel.player.down1, x, y, gamePanel.tileSize * 2, gamePanel.tileSize * 2, null);

            //Menu
            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "NEW GAME";
            x = getXCenteredText(text);
            y += gamePanel.tileSize * 4;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x - gamePanel.tileSize, y );
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "LOAD GAME";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 1) {
                g2.drawString(">", x - gamePanel.tileSize, y );
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "CONTROLS";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 2) {
                g2.drawString(">", x - gamePanel.tileSize, y );
            }

            g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
            text = "QUIT";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);
            if(commandNum == 3) {
                g2.drawString(">", x - gamePanel.tileSize, y );
            }
        } else if (titleScreenState == 1) {
            g2.setColor(Color.white);
            g2.setFont(g2.getFont().deriveFont(42F));
            String text = "Controls";
            int x = getXCenteredText(text);
            int y = gamePanel.tileSize;
            g2.drawString(text, x , y);

            text = "Move up  = W";
            x = getXCenteredText(text);
            y += gamePanel.tileSize * 2;
            g2.drawString(text, x, y);

            text = "Move down = S";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Move left = A";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Move right = D";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Interact = Enter";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Pause = Escape";
            x = getXCenteredText(text);
            y += gamePanel.tileSize;
            g2.drawString(text, x, y);

            text = "Back";
            x = getXCenteredText(text);
            y += gamePanel.tileSize * 3;
            g2.drawString(text, x, y);
            if(commandNum == 0) {
                g2.drawString(">", x -gamePanel.tileSize, y);
            }
        }
    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
        String text = "PAUSED";
        int x = getXCenteredText(text);
        int y = gamePanel.screenHeight / 2;

        g2.drawString(text, x, y);
    }

    public void drawDialogueScreen() {
        //Window
        int x = gamePanel.tileSize * 2;
        int y = gamePanel.tileSize / 2;
        int width = gamePanel.screenWidth - (gamePanel.tileSize * 4);
        int height = gamePanel.tileSize * 4;
        drawSubWindow(x, y, width, height);

        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 28F));
        x += gamePanel.tileSize;
        y += gamePanel.tileSize;

        for(String line : currentDialogue.split("\n")) {
            g2.drawString(line, x, y);
            y += 40;
        }
    }

    public void drawCharacterScreen() {
        // Create frame
        final int frameX = gamePanel.tileSize;
        final int frameY = gamePanel.tileSize;
        final int frameWidth = gamePanel.tileSize * 5;
        final int frameHeight = gamePanel.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //Text
        g2.setColor(Color.white);
        g2.setFont(g2.getFont().deriveFont(32F));

        int textX = frameX + 20;
        int textY = frameY + gamePanel.tileSize;
        final int lineHeight = 35;

        //Names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexterity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight + 15;

        //values
        int tailX = (frameX + frameWidth) - 30;
        //reset textY
        textY = frameY + gamePanel.tileSize;
        String value;

        value = String.valueOf(gamePanel.player.level);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.life + "/" + gamePanel.player.maxLife);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.strength);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.dexterity);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.attack);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.defense);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.exp);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.nextLevelExp);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gamePanel.player.coin);
        textX = getXAlignToRightText(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        g2.drawImage(gamePanel.player.currentWeapon.down1, tailX - gamePanel.tileSize, textY - 14, null);
        textY += gamePanel.tileSize;
        g2.drawImage(gamePanel.player.currentShield.down1, tailX - gamePanel.tileSize, textY - 14, null);
    }

    public void drawInventory() {
        //Frame
        int frameX = gamePanel.tileSize * 9;
        int frameY = gamePanel.tileSize;
        int frameWidth = gamePanel.tileSize * 6;
        int frameHeight = gamePanel.tileSize * 5;

        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        //slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        int slotSize = gamePanel.tileSize + 3;

        //Draw items
        for (int i = 0; i < gamePanel.player.inventory.size(); i++) {
            g2.drawImage(gamePanel.player.inventory.get(i).down1, slotX, slotY, null);

            slotX += slotSize;
            if ( i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        //Cursor
        int cursorX = slotXStart + (slotSize * slotCol);
        int cursorY = slotYStart + (slotSize * slotRow);
        int cursorWidth = gamePanel.tileSize;
        int cursorHeight = gamePanel.tileSize;

        //Draw Cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        //Description
        int dFrameX = frameX;
        int dFrameY = frameY + frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gamePanel.tileSize * 3;
        drawSubWindow(dFrameX, dFrameY, dFrameWidth, dFrameHeight);

        //Description text
        int textX = dFrameX + 20;
        int textY = dFrameY + gamePanel.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));

        int itemIndex = getItemIndexOnSlot();
        if (itemIndex < gamePanel.player.inventory.size()) {
            for (String line: gamePanel.player.inventory.get(itemIndex).description.split("\n")) {
                g2.drawString(line, textX, textY);
                textY += 32;
            }

        }

    }

    public int getItemIndexOnSlot() {
        int itemIndex = slotCol + (slotRow * 5);
        return itemIndex;
    }

    public void drawSubWindow(int x, int y, int width, int height) {
        Color c = new Color(0 , 0 ,0, 205);
        g2.setColor(c);
        g2.fillRoundRect(x, y , width, height, 35, 35);

        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 25, 25);
    }

    public int getXCenteredText(String text) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
       int x = gamePanel.screenWidth / 2 - length / 2;

        return x;
    }

    public int getXAlignToRightText(String text, int tailX) {
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

        return tailX - length;
    }
}