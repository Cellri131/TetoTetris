package main;

//import interne à java
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.util.ArrayList;
import java.util.Random;

//import interne au projet
import mino.Block;
import mino.Mino;
import mino.Mino_Bar;
import mino.Mino_L1;
import mino.Mino_L2;
import mino.Mino_Squarre;
import mino.Mino_T;
import mino.Mino_Z1;
import mino.Mino_Z2;


public class PlayManager {
    private GamePanel gamePanel;

    //Main Play Area
    final int WIDTH = 360;
    final int HEIGHT = 600;
    public static int left_x;
    public static int right_x;
    public static int top_y;
    public static int bottom_y;

    //Mino
    Mino currentMino;
    final int MINO_START_X;
    final int MINO_START_Y;
    
    //Next Mino
    Mino nextMino;
    final int NEXTMINO_X;
    final int NEXTMINO_Y;
    public static ArrayList<Block> staticBlocks = new ArrayList<>();

    //Other
    public static int dropInterval = 60; //mino drops in every 60 frames

    public PlayManager(){

        //Main Play Area Frame
        left_x = (GamePanel.WIDTH/2) - (WIDTH/2); //1280/2 - 360/2 = 460
        right_x = left_x + WIDTH;
        top_y = 50;
        bottom_y = top_y + HEIGHT;
        System.out.print(bottom_y);

        MINO_START_X = left_x + (WIDTH/2) - Block.SIZE;
        MINO_START_Y = top_y  + Block.SIZE;

        NEXTMINO_X = right_x + 175;
        NEXTMINO_Y = top_y + 500;
        nextMino = pickMino();
        nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);

        //Set the starting Mino
        currentMino = pickMino();
        currentMino.setXY(MINO_START_X, MINO_START_Y);
    }

    public void setGamePanel(GamePanel panel) {
        this.gamePanel = panel;
    }

    private Mino pickMino() {

        //Pick a random Mino
        Mino mino = null;
        int i = new Random().nextInt(7);

        switch (i) {
            case 0: mino = new Mino_L1     ();break;
            case 1: mino = new Mino_L2     ();break;
            case 2: mino = new Mino_Squarre();break;
            case 3: mino = new Mino_Bar    ();break;
            case 4: mino = new Mino_T      ();break;
            case 5: mino = new Mino_Z1     ();break;
            case 6: mino = new Mino_Z2     ();break;
        }
        return mino;
    }

    public void update(){
        //check if the current Mino is active / vérifie si le Mino actuel est actif
        if(currentMino.active == false) {
            //if it's not active, place it into the staticBlock / si il n'est pas actif, go Array
            staticBlocks.add(currentMino.b[0]);
            staticBlocks.add(currentMino.b[1]);
            staticBlocks.add(currentMino.b[2]);
            staticBlocks.add(currentMino.b[3]);

            currentMino.deactivating = false;
        
            //replace the currentMino with the nextMino / on le remplace par un nouveau
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
        }
        else
            currentMino.update();
    }

    public void draw (Graphics2D g2) {
        
        //Draw Play Area Frame
        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(4f));
        g2.drawRect(left_x-4, top_y-4, WIDTH+8, HEIGHT+8);

        //Draw Next Mino Frame / affichage de la fenetre du Mino d'après
        int x = right_x + 100;
        int y = bottom_y - 200;
        g2.drawRect(x, y, 200, 200);
        g2.setFont(new Font("Arial", Font.PLAIN, 30));
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.drawString("SUIVANT", x+35, y+60);

        //draw the currentMino / affichage du Mino actuel
        if(currentMino != null)
            currentMino.draw(g2);

        //draw the nextMino
        nextMino.draw(g2);

        //draw Static Blocks
        for(int i = 0; i < staticBlocks.size(); i++)
            staticBlocks.get(i).draw(g2);

        //draw pause / affichage de pause
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gamePanel != null && gamePanel.paused) {
            x = left_x + 90;
            y = top_y + 320;
            g2.drawString("PAUSE", x, y);
        }
    }
    
}
