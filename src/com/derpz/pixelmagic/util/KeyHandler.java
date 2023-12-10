package com.derpz.pixelmagic.util;

import com.derpz.pixelmagic.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    public KeyHandler(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        //title state
        if(gamePanel.gameState == gamePanel.titleState) {
            switch (gamePanel.ui.titleScreenState) {
                case 0 -> {
                    switch (code) {
                        case KeyEvent.VK_W -> {
                            gamePanel.ui.commandNum --;
                            if(gamePanel.ui.commandNum < 0) {
                                gamePanel.ui.commandNum = 3;
                            }
                        }
                        case KeyEvent.VK_S -> {
                            gamePanel.ui.commandNum ++;
                            if(gamePanel.ui.commandNum > 3) {
                                gamePanel.ui.commandNum = 0;
                            }
                        }
                        case KeyEvent.VK_ENTER -> {
                            switch (gamePanel.ui.commandNum) {
                                case 0 -> {
                                    gamePanel.gameState = gamePanel.playState;
                                    gamePanel.playMusic(0);
                                }
                                case 1 -> {
                                    //add later
                                }
                                case 2 -> {
                                    gamePanel.ui.titleScreenState = 1;
                                    gamePanel.ui.commandNum = 0;
                                }
                                case 3 -> {
                                    System.exit(0);
                                }
                            }
                        }
                    }
                }
                case 1 -> {
                    if (code == KeyEvent.VK_ENTER) {
                        gamePanel.ui.titleScreenState = 0;
                    }
                }
            }

        }

        //play state
        if(gamePanel.gameState == gamePanel.playState) {
            switch (code) {
                case KeyEvent.VK_W -> {
                    upPressed = true;
                }
                case KeyEvent.VK_S -> {
                    downPressed = true;
                }
                case KeyEvent.VK_A -> {
                    leftPressed = true;
                }
                case KeyEvent.VK_D -> {
                    rightPressed = true;
                }
                case KeyEvent.VK_ESCAPE -> {
                    gamePanel.gameState = gamePanel.pauseState;
                }
                case KeyEvent.VK_ENTER -> {
                    enterPressed = true;
                }
            }
        }

        //pause state
        else if(gamePanel.gameState == gamePanel.pauseState) {
            if(code == KeyEvent.VK_ESCAPE) {
                gamePanel.gameState = gamePanel.playState;
            }
        }

        //dialogue state
        else if (gamePanel.gameState == gamePanel.dialogueState) {
            if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
                gamePanel.gameState = gamePanel.playState;
            }

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        switch (code) {
            case KeyEvent.VK_W -> {
                upPressed = false;
            }
            case KeyEvent.VK_S -> {
                downPressed = false;
            }
            case KeyEvent.VK_A -> {
                leftPressed = false;
            }
            case KeyEvent.VK_D -> {
                rightPressed = false;
            }
        }
    }
}
