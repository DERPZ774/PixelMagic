package com.derpz.pixelmagic.entity;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.object.Key;
import com.derpz.pixelmagic.object.ShieldWood;
import com.derpz.pixelmagic.object.SwordNormal;
import com.derpz.pixelmagic.util.KeyHandler;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Player extends Entity {

    KeyHandler keyHandler;
    public final int screenX;
    public final int screenY;
    public int hasKey = 0;
    public boolean attackCancel = false;
    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int maxInventorySize = 20;

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
        solidArea.width = 33;
        solidArea.height = 33;

        //attack range
        attackArea.width = 36;
        attackArea.height = 36;

        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gamePanel.tileSize * 23;
        worldY = gamePanel.tileSize * 21;
        speed = 4;
        direction = "idle";

        //player stats
        level = 1;
        maxLife = 6;
        life = maxLife;
        strength = 1;
        dexterity = 1;
        exp = 0;
        nextLevelExp = 5;
        coin = 0;
        currentWeapon = new SwordNormal(gamePanel);
        currentShield = new ShieldWood(gamePanel);
        attack = getAttack(); //Total attack value is decided by strength and weapon
        defense = getDefense(); //Total attack value is decided by dex and shield
    }

    public void setItems() {
        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new Key(gamePanel));
        inventory.add(new Key(gamePanel));
    }

    public int getAttack() {
        return attack = strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return defense = dexterity * currentShield.defenseValue;
    }

    public void getPlayerImage() {
        up1 = setup("/player/wizard_up_1" , gamePanel.tileSize, gamePanel.tileSize);
        up2 = setup("/player/wizard_up_2", gamePanel.tileSize, gamePanel.tileSize);
        down1 = setup("/player/wizard_down_1", gamePanel.tileSize, gamePanel.tileSize);
        down2 = setup("/player/wizard_down_2", gamePanel.tileSize, gamePanel.tileSize);
        left1 = setup("/player/wizard_left_1", gamePanel.tileSize, gamePanel.tileSize);
        left2 = setup("/player/wizard_left_2", gamePanel.tileSize, gamePanel.tileSize);
        right1 = setup("/player/wizard_right_1", gamePanel.tileSize, gamePanel.tileSize);
        right2 = setup("/player/wizard_right_2", gamePanel.tileSize, gamePanel.tileSize);
        idle = setup("/player/wizard_up_1", gamePanel.tileSize, gamePanel.tileSize);
    }

    public void getPlayerAttackImage() {
        attackUp1 = setup("/player/boy_attack_up_1" , gamePanel.tileSize, gamePanel.tileSize * 2);
        attackUp2 = setup("/player/boy_attack_up_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown1 = setup("/player/boy_attack_down_1", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackDown2 = setup("/player/boy_attack_down_2", gamePanel.tileSize, gamePanel.tileSize * 2);
        attackLeft1 = setup("/player/boy_attack_left_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackLeft2 = setup("/player/boy_attack_left_2", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight1 = setup("/player/boy_attack_right_1", gamePanel.tileSize * 2, gamePanel.tileSize);
        attackRight2 = setup("/player/boy_attack_right_2", gamePanel.tileSize * 2, gamePanel.tileSize);
    }

    public void update() {
        if (attacking) {
            attacking();
        }
       else if(keyHandler.upPressed || keyHandler.downPressed ||
                keyHandler.leftPressed || keyHandler.rightPressed || keyHandler.enterPressed) {
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

            //if collision is not on, player can move
            if(!collisionOn && !keyHandler.enterPressed) {
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
            if (keyHandler.enterPressed && !attackCancel) {
                //gamePanel.playSoundEvent(7);
                attacking = true;
                spriteCounter = 0;
            }
            attackCancel = false;
            gamePanel.keyHandler.enterPressed = false;

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

    public void attacking() {
        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }
        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            //Save current world X,Y, and solidArea
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            //Adjust players worldX/Y for attackArea
            switch (direction) {
                case "up": worldY -= gamePanel.tileSize; break;
                case "down": worldY +=  gamePanel.tileSize; break;
                case "left": worldX -= attackArea.width; break;
                case "right": worldX += attackArea.width; break;
            }
            //attackArea becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;
            //Check monster collision with the updated worldX, worldY, and solid Area
            int monsterIndex = gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);
            damageMonster(monsterIndex);

            //after checking collision, restore original data
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;
        }
        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
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
            }
        }
    }

    public void interactNpc(int i) {

        if (gamePanel.keyHandler.enterPressed) {
            if (i != 999) {
                attackCancel = true;
                gamePanel.gameState = gamePanel.dialogueState;
                gamePanel.npc[i].speak();
            }
            else {
                gamePanel.playSoundEvent(7);
                attacking = true;
            }

        }
    }

    public void contactMonster(int i) {
        if(i != 999) {
            if(!invincible && gamePanel.monster[i].alive && !gamePanel.monster[i].dying) {
                gamePanel.playSoundEvent(6);

                int damage = gamePanel.monster[i].attack - defense;
                if (damage < 0) {
                    damage = 0;
                }

                life -= damage;
                invincible = true;
            }
        }
    }

    public void damageMonster(int i) {
        if (i != 999) {
            if (!gamePanel.monster[i].invincible) {
                gamePanel.playSoundEvent(5);

                int damage = attack - gamePanel.monster[i].defense;
                if (damage < 0) {
                    damage = 0;
                }

                gamePanel.monster[i].life -= damage;
                gamePanel.ui.addMessage(damage + " damage!");
                gamePanel.monster[i].invincible = true;
                gamePanel.monster[i].damageReaction();

                if (gamePanel.monster[i].life <= 0) {
                    gamePanel.monster[i].dying = true;
                    gamePanel.ui.addMessage("Killed the " + gamePanel.monster[i].name + "!");
                    gamePanel.ui.addMessage("Exp +" + gamePanel.monster[i].exp + "!");
                    exp += gamePanel.monster[i].exp;
                    checkLevelUp();
                }
            }
        }

    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexterity++;
            attack = getAttack();
            defense = getDefense();

            gamePanel.playSoundEvent(8);
            gamePanel.gameState = gamePanel.dialogueState;
            gamePanel.ui.currentDialogue = "You are level " + level + " now!\n"
                    + "You feel stronger!";
        }
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int tempScreenX = screenX;
        int tempScreenY = screenY;
        switch (direction) {
            case "up" :
                if (!attacking) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking) {
                    tempScreenY = screenY - gamePanel.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;
            case "down" :
                if (!attacking) {
                    if(spriteNum == 1) {
                        image = down1;
                    }
                    if(spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;
            case "left" :
                if (!attacking) {
                    if(spriteNum == 1) {
                        image = left1;
                    }
                    if(spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking) {
                    tempScreenX = screenX - gamePanel.tileSize;
                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;
            case "right" :
                if (!attacking) {
                    if(spriteNum == 1) {
                        image = right1;
                    }
                    if(spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;
            case "idle" :
                image = idle;
                break;
        }

        if (invincible) {
            float opacity = (float) Math.abs(Math.sin(invincibleCounter * 0.1)); // Adjust the multiplier for speed
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        }

        g2.drawImage(image, tempScreenX, tempScreenY,null);
        //Reset alpha
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

        //Debug
//        g2.setFont(new Font("Arial", Font.PLAIN, 26));
//        g2.setColor(Color.white);
//        g2.drawString("Invincible:"+invincibleCounter, 10, 400);
        // DEBUG
//        // AttackArea
//        tempScreenX = screenX + solidArea.x;
//        tempScreenY = screenY + solidArea.y;
//        switch(direction) {
//            case "up": tempScreenY = screenY - attackArea.height; break;
//            case "down": tempScreenY = screenY + gamePanel.tileSize; break;
//            case "left": tempScreenX = screenX - attackArea.width; break;
//            case "right": tempScreenX = screenX + gamePanel.tileSize; break;
//        }
//        g2.setColor(Color.red);
//        g2.setStroke(new BasicStroke(1));
//        g2.drawRect(tempScreenX, tempScreenY, attackArea.width, attackArea.height);
    }
}
