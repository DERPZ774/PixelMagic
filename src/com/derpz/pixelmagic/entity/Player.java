package com.derpz.pixelmagic.entity;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Entity {

    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;

    public Player(GamePanel gamePanel, KeyHandler keyHandler) {
        super(gamePanel);

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

        //player stats
        maxLife = 6;
        life = maxLife;
    }
    public void getPlayerImage() {
        up1 = setup("/player/wizard_up_1");
        up2 = setup("/player/wizard_up_2");
        down1 = setup("/player/wizard_down_1");
        down2 = setup("/player/wizard_down_2");
        left1 = setup("/player/wizard_left_1");
        left2 = setup("/player/wizard_left_2");
        right1 = setup("/player/wizard_right_1");
        right2 = setup("/player/wizard_right_2");
        idle = setup("/player/wizard_up_1");
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

            //npc collision
            int npcIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
            interactNpc(npcIndex);

            //monster collision
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            contactMonster(monsterIndex);

            //event check
            gamePanel.eventHandler.checkEvent();
            gamePanel.keyHandler.enterPressed = false;

            //if collision is not on player can move
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
        if(invincible) {
            invincibleCounter ++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
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
                    gamePanel.gameState = gamePanel.dialogueState;
                    gamePanel.ui.currentDialogue = "You got a key!";
                    gamePanel.obj[i] = null;
                    break;
                case "Door":
                    if (hasKey > 0) {
                        gamePanel.playSoundEvent(3);
                        gamePanel.gameState = gamePanel.dialogueState;
                        gamePanel.ui.currentDialogue = "The door has been opened!";
                        gamePanel.obj[i] = null;
                        hasKey--;
                    } else if (hasKey == 0){
                        gamePanel.gameState = gamePanel.dialogueState;
                        gamePanel.ui.currentDialogue = "The door is locked!";
                    }
                    break;
                case "Boots":
                    gamePanel.playSoundEvent(2);
                    speed += 1;
                    gamePanel.gameState = gamePanel.dialogueState;
                    gamePanel.ui.currentDialogue = "You have obtained a speed upgrade!";
                    gamePanel.obj[i] = null;
                    break;
                case "Chest":
                    gamePanel.ui.gameFinished = true;
                    gamePanel.stopMusic();
                    gamePanel.playSoundEvent(4);
                    gamePanel.gameState = gamePanel.dialogueState;
                    gamePanel.ui.currentDialogue = "You have obtained new magical knowledge!\n To be continued...";
                    break;
            }
        }
    }

    public void interactNpc(int i) {
        if(i != 999) {
            if(gamePanel.keyHandler.enterPressed) {
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[i].speak();
            }
        }
    }

    public void contactMonster(int i) {
        if(i != 999) {
            if(!invincible) {
                life -= 1;
                invincible = true;
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

        if (invincible) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }
        ///ToDo add blinking affect

        g2.drawImage(image, screenX, screenY,null);
        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter, 10, 400);
    }
}
