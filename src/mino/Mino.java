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

    boolean leftCollision, rightCollision, bottomCollision;
    public boolean active = true;

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
        
        checkMovementCollision();
        
        if(leftCollision == false && rightCollision == false && bottomCollision == false) {
            
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
    }

    public void getDirection1(){}
    public void getDirection2(){}
    public void getDirection3(){}
    public void getDirection4(){}

    public void posMino(){
        System.out.println(b[0].y);
    }

    public void checkMovementCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        //check frame collision / vérification à chaque frame
        //left wall / mur gauche
        for(int i = 0; i < b.length; i++)
            if(b[i].x == PlayManager.left_x) {
                leftCollision = true; //dans ce cas on vérifie se qui se trouve à gauche
            // System.out.print("collision bas rotation");
        }

        //right wall / mur droit
        for(int i = 0; i < b.length; i++)
            if (b[i].x == PlayManager.right_x){
                rightCollision = true;
            // System.out.print("collision bas rotation");
        }

        //bottom floor / sol
        for(int i = 0; i < b.length; i++)
            if(b[i].y + Block.SIZE >= PlayManager.bottom_y) {
                bottomCollision = true; 
                // System.out.print("collision bas rotation");
            }
    }

    public void checkRotationCollision(){
        leftCollision = false;
        rightCollision = false;
        bottomCollision = false;

        //Check frame collision / vérification à chaque frame
        //Left wall / mur gauche
        for(int i = 0; i < b.length; i++)
            if(tempB[i].x < PlayManager.left_x)
                leftCollision = true; //dans ce cas on vérifie se qui se trouve à gauche

        //Right wall / mur droit
        for(int i = 0; i < b.length; i++)
            if (tempB[i].x + Block.SIZE > PlayManager.right_x)
                rightCollision = true;

        //Bottom floor / sol
        for(int i = 0; i < b.length; i++)
            if(tempB[i].x + Block.SIZE > PlayManager.bottom_y) {
                bottomCollision = true; 
                // System.out.print("collision bas rotation");
            }
    }
    
    public void update() {

        // Move the Mino / déplacement du mino avec les touches
        if (KeyHandle.upPressed) {
            
            // posMino();

            direction++;
            if (direction>4)
                direction=1;
        
            switch (direction) {
                case 1: getDirection1();break;
                case 2: getDirection2();break;
                case 3: getDirection3();break;
                case 4: getDirection4();break;
            }
            KeyHandle.upPressed = false;
        }

        checkMovementCollision();

        if (KeyHandle.leftPressed) {

            // posMino();

            //if the Mino's left part is not hitting it can go do/ si la gauche morceau dépasse pas
            if(leftCollision == false) {
                b[0].x -= Block.SIZE; // on fait decendre de 1 chaque morceau du mino et reset le count
                b[1].x -= Block.SIZE;
                b[2].x -= Block.SIZE;
                b[3].x -= Block.SIZE;
            }
        
            KeyHandle.leftPressed = false;
        }

        if (KeyHandle.downPressed) {

            // posMino();

            //same but for down case / mm chose pour le bas
            if(bottomCollision == false) {
                b[0].y += Block.SIZE; // on fait decendre de 1 chaque morceau du mino et reset le count
                b[1].y += Block.SIZE;
                b[2].y += Block.SIZE;
                b[3].y += Block.SIZE;

                autoDropCounter = 0;
            }

            KeyHandle.downPressed = false;
        }

        if (KeyHandle.rightPressed) {

            // posMino();

            if(rightCollision == false) {
                b[0].x += Block.SIZE; // on fait decendre de 1 chaque morceau du mino et reset le count
                b[1].x += Block.SIZE;
                b[2].x += Block.SIZE;
                b[3].x += Block.SIZE;
            }

            KeyHandle.rightPressed = false;
        }

        if (bottomCollision) {active = false;}
        else {  
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
