package mino;

import java.awt.Color;

public class Mino_L1 extends Mino {

    public Mino_L1() {
        create(Color.orange);
    }

    public void setXY(int x, int y){
        // [1]
        // [0] <- point de rotation donc point d'orig soit le point b[0]
        // [2][3]

        b[0].x = x;
        b[0].y = y;
        
        b[1].x = b[0].x;
        b[1].y = b[0].y - Block.SIZE;

        b[2].x = b[0].x;
        b[2].y = b[0].y + Block.SIZE;

        b[3].x = b[0].x + Block.SIZE;
        b[3].y = b[0].y + Block.SIZE;

    }
}
