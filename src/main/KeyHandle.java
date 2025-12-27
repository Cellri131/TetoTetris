package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{


    public static boolean upPressed, leftPressed, downPressed, rightPressed, pausePressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_Z)
            upPressed    = true;
    
        if(code == KeyEvent.VK_Q)
            leftPressed  = true;

        if(code == KeyEvent.VK_S)
            downPressed  = true;

        if(code == KeyEvent.VK_D)
            rightPressed = true;

        if(code == KeyEvent.VK_SPACE)
            if (pausePressed)
                pausePressed = false;
            else
                pausePressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    
}
