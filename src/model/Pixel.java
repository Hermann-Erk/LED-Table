package model;

import java.awt.*;

/**
 * Created by Hermann on 02.08.2018.
 */
public class Pixel {
    public Color color;
    public int row;
    public int column;

    public Pixel(int col, int rw, Color color){
        this.column = col;
        this.row = rw;
        this.color = color;
    }

    public Pixel(int col, int rw){
        this.column = col;
        this.row = rw;
        this.color = Color.WHITE;
    }

    public void changeColor(Color color){
        this.color = color;
    }

    public void moveToAbsPos(int col, int rw){
        this.column = col;
        this.row = rw;
    }

    public void moveToRelPos(int colIncrement, int rwIncrement){
        this.column = this.column + colIncrement;
        this.row = this.row + rwIncrement;
    }

}
