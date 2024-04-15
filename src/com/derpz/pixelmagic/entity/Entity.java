package com.derpz.pixelmagic.entity;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.util.ImageScale;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Entity {
    GamePanel gamePanel;
    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2, idle;
    public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
    public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
    public Rectangle attackArea = new Rectangle(0, 0, 0, 0);
    public int solidAreaDefaultX, solidAreaDefaultY;
    String[] dialogues = new String[20];
    public boolean collision = false;
    public BufferedImage image, image2, image3;

    //state
    public int worldX, worldY;
    public String direction = "down";
    public int spriteNum = 1;
    int dialogueIndex = 0;
    public boolean collisionOn = false;
    public boolean invincible = false;
    boolean attacking = false;
    public boolean alive = true;
    public boolean dying = false;
    boolean hpBarOn = false;

    //counter
    public int spriteCounter = 0;
    public int actionCounter = 0;
    public int invincibleCounter = 0;
    int dyingCounter = 0;
    int hpBarCounter = 0;

    //character stats
    public int type; //0 = player, 1 = npc, 2 = monster
    public String name;
    public int speed;
    public int maxLife;
    public int life;
    public int level;
    public int strength;
    public int dexterity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;

    //Item attributes
    public int attackValue;
    public int defenseValue;
    public String description = "";


    public Entity(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setAction() {}
    public void damageReaction() {}
    public void speak() {
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gamePanel.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        switch (gamePanel.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    public void update() {
        setAction();

        collisionOn = false;
        gamePanel.collisionChecker.checkTile(this);
        gamePanel.collisionChecker.checkObject(this, false);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.npc);
        gamePanel.collisionChecker.checkEntity(this, gamePanel.monster);

        boolean contactPlayer = gamePanel.collisionChecker.checkPlayer(this);

        if(this.type == 2 && contactPlayer) {
            if(!gamePanel.player.invincible) {
                //dmg player
                gamePanel.playSoundEvent(6);

                int damage = attack - gamePanel.player.defense;
                if (damage < 0) {
                    damage = 0;
                }
                gamePanel.player.life -= damage;
                gamePanel.player.invincible = true;
            }
        }

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
        if(invincible) {
            invincibleCounter ++;
            if (invincibleCounter > 40) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if(worldX  + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

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

            //monster health
            if (type == 2 && hpBarOn && alive) {
                double oneScale = (double) gamePanel.tileSize / maxLife;
                double hpBarValue = oneScale * life;

                g2.setColor(new Color(35, 35, 35));
                g2.fillRect(screenX - 1, screenY - 16, gamePanel.tileSize + 2, 12);
                g2.setColor(new Color(255, 0, 30));
                g2.fillRect(screenX, screenY - 15, (int) hpBarValue, 10);

                hpBarCounter ++;

                if (hpBarCounter > 600) {
                    hpBarCounter = 0;
                    hpBarOn = false;
                }
            }

            if (invincible) {
                hpBarOn = true;
                hpBarCounter = 0;
                float opacity = (float) Math.abs(Math.sin(invincibleCounter * 0.1)); // Adjust the multiplier for speed
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
            }
            if (dying) {
                dyingAnimation(g2);
            }
            g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        }
    }

    public void dyingAnimation(Graphics2D g2) {
        dyingCounter++;

        // Calculate opacity based on whether the counter is even or odd
        float opacity = (dyingCounter % 20 < 10) ? 0.0f : 1.0f;


        // Apply the calculated opacity
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));

        // Stop the animation when dyingCounter reaches 40
        if (dyingCounter >= 40) {
            dyingCounter = 0; // Reset the counter for future use or stop it altogether
            dying = false;
            alive = false;
        }
    }

    public BufferedImage setup(String imagePath, int width, int height) {
        ImageScale imageScale = new ImageScale();
        BufferedImage image = null;

        try {
            image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(  imagePath + ".png")));
            image = imageScale.scaleImage(image, width, height);
        }catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }
}
