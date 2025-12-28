package mino;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block extends Rectangle {

    public int x, y;
    public static final int SIZE = 30; //30x30
    public Color c;

    public Block(Color c){
        this.c = c;
    }

    public void draw(Graphics2D g2) {
        int margin = 4;
        // Draw main square
        g2.setColor(c);
        g2.fillRect(x + margin, y + margin, Block.SIZE - margin * 2, Block.SIZE - margin * 2);

        // Calculate lighter and darker colors
        Color lighter = c.brighter();
        Color darker = c.darker();

        // Draw beveled edges
        int edge = 8;
        // Top edge (lighter)
        g2.setColor(lighter);
        g2.fillPolygon(
            new int[]{x, x + Block.SIZE, x + Block.SIZE - edge, x + edge},
            new int[]{y, y, y + edge, y + edge},
            4
        );
        // Left edge (lighter)
        g2.setColor(lighter);
        g2.fillPolygon(
            new int[]{x, x + edge, x + edge, x},
            new int[]{y, y + edge, y + Block.SIZE - edge, y + Block.SIZE},
            4
        );
        // Bottom edge (darker)
        g2.setColor(darker);
        g2.fillPolygon(
            new int[]{x, x + Block.SIZE, x + Block.SIZE - edge, x + edge},
            new int[]{y + Block.SIZE, y + Block.SIZE, y + Block.SIZE - edge, y + Block.SIZE - edge},
            4
        );
        // Right edge (darker)
        g2.setColor(darker);
        g2.fillPolygon(
            new int[]{x + Block.SIZE, x + Block.SIZE, x + Block.SIZE - edge, x + Block.SIZE - edge},
            new int[]{y, y + Block.SIZE, y + Block.SIZE - edge, y + edge},
            4
        );

        // Draw border
        g2.setColor(Color.BLACK);
        g2.drawRect(x, y, Block.SIZE, Block.SIZE);
    }

}
