package mino;

import java.awt.Color;

public class Mino_L1 extends Mino {

    public Mino_L1() {
        create(Color.orange);
    }

    public void setXY(int x, int y){
        // []
        // [] <- point de rotation donc point d'orig soit le point b[0]
        // [][]

        b[0].x = x;
        b[0].y = y;
        b[1].x = b[0].x;
    }
}
