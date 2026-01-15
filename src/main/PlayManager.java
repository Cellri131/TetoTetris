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
    boolean gameOver;

    //effect
    boolean effectCounterOn;
    int effectCounter;
    ArrayList<Integer> effectY = new ArrayList<>();

    //Score
    int level = 1;
    int lines;
    int scores;

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

            //check if the game is over
            if (currentMino.b[0].x == MINO_START_X && currentMino.b[0].y == MINO_START_Y) {
                //this means the currentMino instant collided a block and couldn't move att all
                gameOver = true;
                
            }

            currentMino.deactivating = false;
        
            //replace the currentMino with the nextMino / on le remplace par un nouveau
            currentMino = nextMino;
            currentMino.setXY(MINO_START_X, MINO_START_Y);
            nextMino = pickMino();
            nextMino.setXY(NEXTMINO_X, NEXTMINO_Y);
        
            //when a line is full we delete it / on supprime la ligne si elle est pleine
            checkDelete();
        }
        else
            currentMino.update();
    }

    private void checkDelete(){

        int x = left_x;
        int y = top_y;
        int blockCount = 0;
        int lineCount = 0;

        while (x < right_x && y < bottom_y) {

            for(int i = 0; i < staticBlocks.size(); i++){
                if (staticBlocks.get(i).x == x && staticBlocks.get(i).y == y) {
                    //increse the count if there a static block / augmentation du conteur
                    blockCount++;
                    
                }
            }

            x += Block.SIZE;

            if ( x >= right_x) {

                //if the BlockCount hits 12, that mean the current y line is filled with block
                //so we can delet it
                if (blockCount == 12) {

                    effectCounterOn = true;
                    effectY.add(y);
                    
                    for(int i = staticBlocks.size()-1; i > -1; i--){
                        //remove all the block from the line
                        if (staticBlocks.get(i).y == y) {
                            staticBlocks.remove(i);
                        }
                    }

                    lineCount++;
                    lines++;

                    for(int i = 0; i < staticBlocks.size(); i++){
                        //if a block is above the current y, move it down by the block size
                        if (staticBlocks.get(i).y < y) {
                            staticBlocks.get(i).y += Block.SIZE;
                        }
                    }
                }

                blockCount = 0;
                x = left_x;
                y += Block.SIZE;
            }
        }

        //Add Score
        if (lineCount > 0) {
            int simpleLineScore = 10 + level;
            scores += simpleLineScore + lineCount;
        }
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

        //draw the Score Frame
        g2.drawRect(x, top_y, 250, 300);
        x += 40;
        y = top_y + 90;
        g2.drawString("LEVEL: "  + level,  x, y); y += 70;
        g2.drawString("LIGNES: " + lines,  x, y); y += 70;
        g2.drawString("SCORES: " + scores, x, y);

        //draw the currentMino / affichage du Mino actuel
        if(currentMino != null)
            currentMino.draw(g2);

        //draw the nextMino
        nextMino.draw(g2);

        //draw Static Blocks
        for(int i = 0; i < staticBlocks.size(); i++)
            staticBlocks.get(i).draw(g2);

        //draw effect
        if (effectCounterOn) {
            effectCounter++;

            g2.setColor(Color.red);
            for(int i = 0; i < effectY.size(); i++){
                g2.fillRect(left_x, effectY.get(i), WIDTH, Block.SIZE);
            }

            if (effectCounter == 10) {
                effectCounterOn = false;
                effectCounter = 0;
                effectY.clear();
            }
        }

        //draw pause and game over / affichage de pause et de fin de partie
        g2.setColor(Color.yellow);
        g2.setFont(g2.getFont().deriveFont(50f));
        if (gameOver) {
            x = left_x + 25;
            y = top_y + 320;
            g2.drawString("GAME OVER", x, y);
        }
        else if (gamePanel != null && gamePanel.paused) {
            x = left_x + 90;
            y = top_y + 320;
            g2.drawString("PAUSE", x, y);
        }

        //draw the game title / titre du jeu
        x = 170;
        y = top_y + 320;
        g2.setColor(Color.white);
        g2.setFont(new Font("Times New Roman", Font.ITALIC, 60));
        g2.drawString("Tetris", x, y);
    }
    
}
