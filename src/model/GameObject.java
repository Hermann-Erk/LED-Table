package model;

import java.awt.*;
import java.util.LinkedList;

/**
 * Created by Hermann on 02.08.2018.
 */
public abstract class GameObject {

    LinkedList<Pixel> pixels = new LinkedList<>();
    Color color = Color.WHITE;

    public void addPixel(int column, int row, Color color){
        this.pixels.add(new Pixel(column, row, color));
    }

    public void addPixel(int column, int row){
        this.pixels.add(new Pixel(column, row));
    }

    public int rmPixel(int column, int row){
        for(int i = 0; i < pixels.size(); i++){
            if(this.pixels.get(i).column == column && this.pixels.get(i).row == row){
                this.pixels.remove(i);
                return 0;
            }
        }
        return -1;
    }

    public int rmByIndex(int index){
        try {
            this.pixels.remove(index);
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public Pixel getFirstPixel(){
        return this.pixels.get(0);
    }

    public Color getColor(){
        return this.color;
    }

    public abstract int move(Direction dir);
}
