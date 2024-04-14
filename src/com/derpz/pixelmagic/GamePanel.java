package com.derpz.pixelmagic;

import com.derpz.pixelmagic.UI.UI;
import com.derpz.pixelmagic.entity.Entity;
import com.derpz.pixelmagic.entity.Player;
import com.derpz.pixelmagic.event.EventHandler;
import com.derpz.pixelmagic.tile.TileManager;
import com.derpz.pixelmagic.util.AssetSetter;
import com.derpz.pixelmagic.util.CollisionChecker;
import com.derpz.pixelmagic.util.KeyHandler;
import com.derpz.pixelmagic.util.Sound;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class GamePanel extends JPanel implements Runnable {
    //SCREEN SETTINGS
    public final int originalTileSize = 16; //16x16 tile
    final int scale = 3;
    public final int tileSize = originalTileSize * scale; //48x48 tile
    public final int maxScreenCol = 16;
   public final int maxScreenRow = 12;
   public final int screenWidth = tileSize * maxScreenCol;
   public final int screenHeight = tileSize * maxScreenRow;

   //WORLD SETTINGS
    public final int maxWorldCol = 50;
    public final int maxWorldRow = 50;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;
    public TileManager tileManager = new TileManager(this);
    public KeyHandler keyHandler = new KeyHandler(this);
    Sound music = new Sound();
    Sound soundEvent = new Sound();

    public CollisionChecker collisionChecker = new CollisionChecker(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public UI ui = new UI(this);
    public EventHandler eventHandler = new EventHandler(this);
    public Thread gameThread;
    //Entity and Obj
    public Player player = new Player(this, keyHandler);
    public Entity[] obj = new Entity[10];
    public Entity[] npc = new Entity[10];
    public Entity[] monster = new Entity[20];
    ArrayList<Entity> entityList = new ArrayList<>();
    //Game state
    public int gameState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int dialogueState = 3;
    public final int characterState = 4;



    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyHandler);
        this.setFocusable(true);
    }

    public void setupGame() {
        assetSetter.setObject();
        assetSetter.setNPC();
        assetSetter.setMonster();
        //playMusic(0);
        gameState = titleState;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }


    @Override
    public void run() {
        double drawInterval = (double) 1000000000 /FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0; 

        while (gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                //updates information
                update();
                //draws updated info
                repaint();
                delta--;
                drawCount++;
            }
            if (timer >= 1000000000) {
                System.out.println("FPS:" + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update() {
        if(gameState == playState) {
            //Player
            player.update();
            //Npc
            for (Entity entity : npc) {
                if (entity != null) {
                    entity.update();
                }
            }
        }
        //monster
        for (Entity entity : monster) {
            if (entity != null) {
                if (entity.alive && !entity.dying) {
                    entity.update();
                }
                if (!entity.alive) {
                    entity = null;
                }
            }
        }
        if(gameState == pauseState) {

        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        //title screen
        if(gameState == titleState) {
            ui.draw(g2);
        }
        //others
        else {
            //tile
            tileManager.draw(g2);

            // add entities to list
            entityList.add(player);

            for (Entity entity : npc) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

            for (Entity entity : obj) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

            for (Entity entity : monster) {
                if (entity != null) {
                    entityList.add(entity);
                }
            }

            // Sort
            entityList.sort(Comparator.nullsLast(Comparator.comparingInt(e -> e.worldY)));

            // Draw entities
            for (Entity entity : entityList) {
                if (entity != null) {
                    entity.draw(g2);
                }
            }
            // Empty list
            entityList.clear();

            //UI
            ui.draw(g2);
        }


        g2.dispose();
    }

    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic() {
        music.stop();
    }

    public void playSoundEvent (int i) {
        soundEvent.setFile(i);
        soundEvent.play();
    }

}
