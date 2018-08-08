package model;

import Control.Constants;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hermann on 07.08.2018.
 */
public class PongPlayer extends GameObject {
    private float borderXLeft = 0;
    private float borderXRight = 0;

    public PongPlayer(ArrayList<Pixel> pongPixels, Color color){
        for(Pixel pix : pongPixels){
            this.addPixel(pix.column, pix.row, color);
        }

        this.borderXLeft = this.pixels.getFirst().column;
        this.borderXRight = this.pixels.get(this.pixels.size()-1).column + 1;
        this.color=color;
    }

    public int move(Direction dir){
        if (dir != null) {
            switch (dir) {
                case LEFT:
                    float borderLeftAfterInc = borderXLeft - Constants.pongPlayerVelocity * Constants.pongDT;
                    if (borderLeftAfterInc > 0) {
                        borderXLeft = borderLeftAfterInc;
                        borderXRight = borderXRight - Constants.pongPlayerVelocity * Constants.pongDT;
                    }
                    return 0;
                case RIGHT:
                    float borderRightAfterInc = borderXRight + Constants.pongPlayerVelocity * Constants.pongDT;
                    if (borderRightAfterInc < 14) {
                        borderXRight = borderRightAfterInc;
                        borderXLeft = borderXLeft + Constants.pongPlayerVelocity * Constants.pongDT;
                    }
                    return 0;
                default:
                    return -1;
            }
        }
        return -1;
    }

    public float getBorderXLeft(){
        return borderXLeft;
    }

    public float getBorderXRight(){
        return borderXRight;
    }
}
