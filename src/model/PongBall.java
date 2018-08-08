package model;

import Control.Constants;

import java.awt.*;

/**
 * Created by Hermann on 07.08.2018.
 */
public class PongBall extends GameObject {
    private float velocityX = 0;
    private float velocityY = 0;

    private float positionX = 0;
    private float positionY = 0;

    private float  totalVelocity;

    public PongBall(Pixel pix, float velocityX, float velocityY){
        this.positionX = pix.column;
        this.positionY = pix.row;
        this.color = pix.color;
        this.pixels.add(pix);
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        totalVelocity = (float) Math.sqrt(velocityX*velocityX+velocityY*velocityY);
    }

    public PongBall(float x, float y, float velocityX, float velocityY){
        this.positionX = x;
        this.positionY = y;
        this.color = Color.WHITE;
        this.pixels.add(new Pixel((int) x, (int) y));
        this.velocityX = velocityX;
        this.velocityY = velocityY;
        totalVelocity = (float) Math.sqrt(velocityX*velocityX+velocityY*velocityY);
    }

    @Override
    public int move(Direction dir) {
        //System.out.println(velocityX * Constants.pongDT);
        positionX = positionX + velocityX * Constants.pongDT;
        positionY = positionY + velocityY * Constants.pongDT;
        return 0;
    }

    public int collideWithWall(Direction dir){
        switch (dir){
            case UP:
                velocityY = -velocityY;
                return 0;
            case DOWN:
                return -1;
            case RIGHT:
            case LEFT:
                velocityX = -velocityX;
                return 0;
        }
        return 0;
    }

    public void collide(float degree,Direction dir){

        if(degree < 10){
            degree = 10;
        }

        if(degree > 170){
            degree = 170;
        }

        increaseTotalVelocityByIncrement();

        System.out.println(totalVelocity);
        //gesamtgeschwindigkeit aufteilen!
        velocityX = (float) Math.cos(degree*Math.PI/180)*totalVelocity;
        velocityY = (float) Math.sin(degree*Math.PI/180)*totalVelocity;

        if(dir.equals(Direction.UP)){
            velocityY = -velocityY;
        }
    }

    public float getIncrementYPosition(){
        return positionY + velocityY * Constants.pongDT;
    }

    public float getIncrementXPosition(){
        return positionX + velocityX * Constants.pongDT;
    }

    public double getPositionX(){
        return positionX;
    }

    public double getPositionY(){
        return positionY;
    }

    private void increaseTotalVelocityByIncrement(){
        totalVelocity = totalVelocity + Constants.PONG_BALL_VELOCITY_INCREMENT;
    }

}
