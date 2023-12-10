package com.derpz.pixelmagic.entity;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.util.ImageScale;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import java.util.Random;

public class NPCWizard extends Entity{
    public NPCWizard(GamePanel gamePanel) {
        super(gamePanel);

        direction = "down";
        speed = 1;
        getWizardImage();
        setDialogue();
    }
    public void getWizardImage() {
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "Hello traveller!";
        dialogues[1] = "So you've come to seek magical \nknowledge?";
        dialogues[2] = "I may be old, but I still have some fight in \nme... Allow me to aid you on your adventure";
        dialogues[3] = "First things first, find the keys hidden around \nthe neighboring forest. \nThey can be used to unlock the doors nearby.";
    }

    public void setAction() {
        actionCounter ++;
        if(actionCounter == 120) {
            Random random = new Random();
            int i = random.nextInt(100) + 1;
            if (i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if (i > 50 && i <= 75) {
                direction = "left";
            }
            if (i > 75 && i <= 100) {
                direction = "right";
            }
            actionCounter = 0;
        }
    }

    public void speak() {
      super.speak();
    }

}
