package com.derpz.pixelmagic.entity;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.util.ImageScale;
import com.derpz.pixelmagic.util.KeyHandler;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Objects;

public class Player extends Entity {
    GamePanel gamePanel;
    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        this.gamePanel = gamePanel;
        this.keyHandler = keyHandler;
        screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
        screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);

        solidArea = new Rectangle();

        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 20;
        solidArea.height = 20;

        setDefaultValues();
        getPlayerImage();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "idle";
    }
    public void getPlayerImage() {
        up1 = setup("wizard_up_1");
        up2 = setup("wizard_up_2");
        down1 = setup("wizard_down_1");
        down2 = setup("wizard_down_2");
        left1 = setup("wizard_left_1");
        left2 = setup("wizard_left_2");
        right1 = setup("wizard_right_1");
        right2 = setup("wizard_right_2");
        idle = setup("wizard_up_1");
    }

    public BufferedImage setup(String imageName) {
        ImageScale imageScale = new ImageScale();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/player/" + imageName + ".png")));
            image = imageScale.scaleImage(image, gamePanel.tileSize, gamePanel.tileSize);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    public void update() {
        if(keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed) {
            if (keyHandler.upPressed) {
                direction = "up";
            } else if (keyHandler.downPressed) {
                direction = "down";
            } else if (keyHandler.leftPressed) {
                direction = "left";
            } else if (keyHandler.rightPressed) {
                direction = "right";
            }
            //Check tile collision
            collisionOn = false;
            gamePanel.collisionChecker.checkTile(this);


            //check obj collision
            int objIndex = gamePanel.collisionChecker.checkObject(this, true);
            pickUpObject(objIndex);

            //if collision is on player can move
            if(!collisionOn) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter ++;
            if (spriteCounter > 16) {
                spriteNum = (spriteNum == 1) ? 2 : 1;
                spriteCounter = 0;
            }
        }
    }

    public void pickUpObject(int i) {
        if(i != 999) {
            String objectName = gamePanel.obj[i].name;

            switch (objectName) {
                case "Key":
                    gamePanel.playSoundEvent(1);
                    hasKey++;
                    gamePanel.obj[i] = null;
                    gamePanel.ui.showMessage("You obtained a key!");
                    break;
                case "Door":
                    if(hasKey > 0) {
                        gamePanel.playSoundEvent(3);
                        gamePanel.obj[i] = null;
                        hasKey--;
                        gamePanel.ui.showMessage("Door opened!");
                    } else {
                        gamePanel.ui.showMessage("You don't have a key!");
                    }
                    break;
                case "Chest":
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEvent(4);
                    break;
            }
        }
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = null;
        switch (direction) {
            case "up" :
                if(spriteNum == 1) {
                    image = up1;
                }
                if(spriteNum == 2) {
                    image = up2;
                }
                break;
            case "down" :
                if(spriteNum == 1) {
                    image = down1;
                }
                if(spriteNum == 2) {
                    image = down2;
                }
                break;
            case "left" :
                if(spriteNum == 1) {
                    image = left1;
                }
                if(spriteNum == 2) {
                    image = left2;
                }
                break;
            case "right" :
                if(spriteNum == 1) {
                    image = right1;
                }
                if(spriteNum == 2) {
                    image = right2;
                }
                break;
            case "idle" :
                image = idle;
                break;
        }

        g2.drawImage(image, screenX, screenY,null);
    }
}
