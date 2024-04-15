package com.derpz.pixelmagic.util;

import com.derpz.pixelmagic.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    GamePanel gamePanel;
    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;
    //Debug
    public boolean showDebugText = false;

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
            titleState(code);
        }

        //play state
       else if(gamePanel.gameState == gamePanel.playState) {
            playState(code);
        }

        //pause state
        else if(gamePanel.gameState == gamePanel.pauseState) {
            pauseState(code);
        }

        //dialogue state
        else if (gamePanel.gameState == gamePanel.dialogueState) {
            dialogueState(code);
        }
        //character state
        else if (gamePanel.gameState == gamePanel.characterState) {
            characterState(code);
        }
    }
    public void titleState(int code) {
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
                                //gamePanel.playMusic(0);
                                ///ToDo add music here
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

    public void playState(int code) {
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
            case KeyEvent.VK_C -> {
                gamePanel.gameState = gamePanel.characterState;
            }
            case KeyEvent.VK_ENTER -> {
                enterPressed = true;
            }
            //Debug
            case KeyEvent.VK_T -> {
                if (!showDebugText) {
                    showDebugText = true;
                }
                else if (showDebugText) {
                    showDebugText = false;
                }
            }
            case KeyEvent.VK_R -> {
                gamePanel.tileManager.loadMap("/maps/worldV2.txt");
            }
        }
    }

    public void pauseState(int code) {
        if(code == KeyEvent.VK_ESCAPE) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void dialogueState(int code) {
        if(code == KeyEvent.VK_ENTER || code == KeyEvent.VK_SPACE) {
            gamePanel.gameState = gamePanel.playState;
        }
    }

    public void characterState(int code) {

        switch (code) {
            case KeyEvent.VK_C -> {
                gamePanel.gameState = gamePanel.playState;
            }
            case KeyEvent.VK_W -> {
                if (gamePanel.ui.slotRow != 0) {
               gamePanel.ui.slotRow--;
               gamePanel.playSoundEvent(9);
                }
            }
            case KeyEvent.VK_A -> {
                if (gamePanel.ui.slotCol != 0) {
                    gamePanel.ui.slotCol--;
                    gamePanel.playSoundEvent(9);
                }
            }
            case KeyEvent.VK_S -> {
                if (gamePanel.ui.slotRow != 3) {
                    gamePanel.ui.slotRow++;
                    gamePanel.playSoundEvent(9);
                }
            }
            case KeyEvent.VK_D -> {
                if (gamePanel.ui.slotCol != 4) {
                    gamePanel.ui.slotCol++;
                    gamePanel.playSoundEvent(9);
                }
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
