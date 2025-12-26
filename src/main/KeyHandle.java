package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandle implements KeyListener{


    public static boolean upPressed, downPressed, leftPressed, rightPressed;

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

    if(code == KeyEvent.VK_Z)
        upPressed = true;
        
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    
}
