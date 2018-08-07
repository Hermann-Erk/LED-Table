package model;

import java.awt.*;

/**
 * Created by Hermann on 07.08.2018.
 */
public class ExplosionCircle extends GameObject {

    public int move(Direction dir){
        return -1;
    }

    public ExplosionCircle(Color color){
        this.color = color;
    }

    public void clear(){
        this.pixels.clear();
    }

}
