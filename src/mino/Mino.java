package mino;

import java.awt.Graphics2D;
import java.awt.Color;

import main.KeyHandle;
import main.PlayManager;


//superclass du dossier mino
public class Mino {

    public Block     b[] = new Block[4];
    public Block tempB[] = new Block[4];

    int autoDropCounter = 0;

    public int direction = 1; //4 directions existe (1/2/3/4)

    public void create(Color c){
        
        b    [0] = new Block(c);
        b    [1] = new Block(c);
        b    [2] = new Block(c);
        b    [3] = new Block(c);
        tempB[0] = new Block(c);
        tempB[1] = new Block(c);
        tempB[2] = new Block(c);
        tempB[3] = new Block(c);
    }

    public void setXY(int x, int y){}
    public void updateXY(int direction){
        this.direction = direction;
        b[0].x = tempB[0].x;
        b[0].y = tempB[0].y;
        b[1].x = tempB[1].x;
        b[1].y = tempB[1].y;
        b[2].x = tempB[2].x;
        b[2].y = tempB[2].y;
        b[3].x = tempB[3].x;
        b[3].y = tempB[3].y;
    }

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}
    
    public void update() {

        // Move the Mino / déplacement du mino avec les touches
        if (KeyHandle.upPressed) {
            
            direction++;
            if (direction>4)
                direction=1;
            
            System.out.print("test Z");
            switch (direction) {
                case 1: getDirection1();break;
                case 2: getDirection2();break;
                case 3: getDirection3();break;
                case 4: getDirection4();break;
            }
            KeyHandle.upPressed = false;
        }
        if (KeyHandle.leftPressed) {
            b[0].x -= Block.SIZE; // on fait decendre de 1 chaque morceau du mino et reset le count
            b[1].x -= Block.SIZE;
            b[2].x -= Block.SIZE;
            b[3].x -= Block.SIZE;
        
            KeyHandle.leftPressed = false;
        }
        if (KeyHandle.downPressed) {
            b[0].y += Block.SIZE; // on fait decendre de 1 chaque morceau du mino et reset le count
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;

            autoDropCounter = 0;

            KeyHandle.downPressed = false;
        }
        if (KeyHandle.rightPressed) {
            b[0].x += Block.SIZE; // on fait decendre de 1 chaque morceau du mino et reset le count
            b[1].x += Block.SIZE;
            b[2].x += Block.SIZE;
            b[3].x += Block.SIZE;
            
            KeyHandle.rightPressed = false;
        }


        autoDropCounter++; //the counter increases in every frame / le compteur augmente pour chaque frame
        if (autoDropCounter == PlayManager.dropInterval) {
            //the Mino goes down / le block vas ver le bas
            b[0].y += Block.SIZE; // on fait decendre de 1 chaque morceau du mino
            b[1].y += Block.SIZE;
            b[2].y += Block.SIZE;
            b[3].y += Block.SIZE;
            autoDropCounter = 0;
        }
    }

    public void draw(Graphics2D g2) {

        int margin = 2;
        g2.setColor(b[0].c);
        g2.fillRect(b[0].x+margin, b[0].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[1].x+margin, b[1].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[2].x+margin, b[2].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
        g2.fillRect(b[3].x+margin, b[3].y+margin, Block.SIZE-(margin*2), Block.SIZE-(margin*2));
    }
}
