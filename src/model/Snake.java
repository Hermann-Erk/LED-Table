package model;

import Control.LED_Control;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hermann on 02.08.2018.
 */
public class Snake extends GameObject {
    private Direction lastDirection;
    private boolean hasEaten = false;

    public Snake(ArrayList<Pixel> snakePixels,Color color, Direction direction){
        for(Pixel pix : snakePixels){
            this.addPixel(pix.column, pix.row, color);
        }
        this.lastDirection = direction;
        this.color = color;
    }

    // Snake starts moving from the back, the pixels move forward in order, every pixel assumes
    // the position of its front neighbour. If the snake has eaten in the previous frame, a
    // new pixel will be spawned at -1,-1 and instantly be moved to the position of the last (now
    // second last) pixel. The new position of the frontmost pixel is determined by the moving direction.
    public int move(Direction direction){
        if(Direction.areOpposites(direction, lastDirection)){
            direction = lastDirection;
        }
            lastDirection = direction;

            if(this.hasEaten){
                this.pixels.add(new Pixel(-1,-1, this.color));
                this.hasEaten=false;
                LED_Control.setFoodEaten();
            }

            for(int i = this.pixels.size() - 1; i > 0; i--){
                this.pixels.get(i).moveToAbsPos(this.pixels.get(i-1).column, this.pixels.get(i-1).row);
            }


            switch(direction){
                case UP: this.getFirstPixel().moveToRelPos(0,1);
                         break;
                case DOWN: this.getFirstPixel().moveToRelPos(0,-1);
                    break;
                case RIGHT: this.getFirstPixel().moveToRelPos(1,0);
                    break;
                case LEFT: this.getFirstPixel().moveToRelPos(-1,0);
                    break;
            }

            return 0;

    }

    public void hasEaten(){
        this.hasEaten = true;
    }
}
