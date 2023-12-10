package com.derpz.pixelmagic.object;

import com.derpz.pixelmagic.GamePanel;
import com.derpz.pixelmagic.entity.Entity;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Objects;

public class Key extends Entity {

    public Key(GamePanel gamePanel) {
        super(gamePanel);
        name = "Key";
        down1 = setup("/objects/key");
    }
}
