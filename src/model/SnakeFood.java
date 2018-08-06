package model;

import java.awt.*;

/**
 * Created by Hermann on 03.08.2018.
 */
public class SnakeFood extends GameObject {

    public int move(Direction dir){
        return -1;
    }

    public SnakeFood(Pixel pixel){
        this.addPixel(pixel.column, pixel.row, Color.BLUE);
    }

}
