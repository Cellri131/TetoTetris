package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{


    public static boolean upPressed, leftPressed, downPressed, rightPressed;

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
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    
}
