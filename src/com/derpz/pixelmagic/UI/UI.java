package com.derpz.pixelmagic.UI;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;
import com.derpz.pixelmagic.object.Heart;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class UI {
    GamePanel gamePanel;
    Graphics2D g2;
    Font mauraMonica, purisaB;
    BufferedImage heart_full, heart_half, heart_blank;

    public boolean messageOn = false;
    public String message = "";
    public String currentDialogue = "";
    public int commandNum = 0;
    public int titleScreenState = 0;
    public boolean gameFinished = false;


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

    public void showMessage(String text) {
        message = text;
        messageOn = true;
    }
    public void draw(Graphics2D g2) {
        this.g2 = g2;
        g2.setFont(mauraMonica);
        g2.setColor(Color.white);

        //title state
        if(gamePanel.gameState == gamePanel.titleState) {
            drawTitleScreen();
        }

        if(gamePanel.gameState == gamePanel.playState) {
            //Play state stuff
            drawPlayerLife();
        }

        if(gamePanel.gameState == gamePanel.pauseState) {
            drawPlayerLife();
            drawPauseScreen();
        }

        if(gamePanel.gameState == gamePanel.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        if(gameFinished && gamePanel.gameState != gamePanel.dialogueState) {
            System.exit(0);
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

}
