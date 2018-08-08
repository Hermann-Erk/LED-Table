package model;

import Control.PongGame;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Hermann on 02.08.2018.
 */
public class Board {

    public Color[][] board = new Color[14][14];
    public ArrayList<GameObject> gameObjects = new ArrayList<>();

    public Board(){
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                board[i][j] = Color.BLACK;
            }
        }

    }

    public void updateBoard(){
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                board[i][j] = Color.BLACK;
            }
        }
        for(GameObject obj : gameObjects){
            for(Pixel pix : obj.pixels){
                if(pix.row < 14 && pix.row >= 0 && pix.column < 14 && pix.column >= 0) {
                    board[pix.column][pix.row] = pix.color;
                }
            }
        }
    }

    public void didSnakeEat(){
        int foodIndexToRemove = -1;

        for (GameObject snake : gameObjects){
            if (snake instanceof Snake){
                for (int i = 0; i < gameObjects.size(); i++){
                    if(gameObjects.get(i) instanceof SnakeFood){
                        if (snake.getFirstPixel().column == gameObjects.get(i).getFirstPixel().column &&
                                snake.getFirstPixel().row == gameObjects.get(i).getFirstPixel().row) {
                            foodIndexToRemove = 1;
                            ((Snake) snake).hasEaten();
                        }
                    }
                }
            }
        }

        if(foodIndexToRemove > -1){
            gameObjects.remove(foodIndexToRemove);

        }
    }

    public Pixel isGameOver_SNAKE(){
        Pixel culprit = null;

        for (GameObject i : gameObjects){
            if(i instanceof Snake) {
                if (i.getFirstPixel().column < 0 || i.getFirstPixel().column >= 14 ||
                        i.getFirstPixel().row < 0 || i.getFirstPixel().row >= 14) {
                    culprit = i.getFirstPixel();
                    // TODO runDeathAnimation(Color culprit)
                    return culprit;
                }

                for (GameObject k : gameObjects) {
                    for (Pixel pix : k.pixels) {
                        if (!(i.getFirstPixel().equals(pix)) && (k instanceof Snake)) {
                            if (i.getFirstPixel().column == pix.column && i.getFirstPixel().row == pix.row) {
                                culprit = i.getFirstPixel();
                                return culprit;
                            }
                        }
                    }
                }
            }
        }

        return culprit;
    }

    public void paintColorMap(){
        for(GameObject obj : gameObjects){
            for(Pixel pix : obj.pixels){
                board[pix.column][pix.row] = pix.color;
            }
        }
    }

    public void addGameObject(GameObject obj){
        this.gameObjects.add(obj);
    }

    public Color getColorToPixel(int column, int row){
        return board[column][row];
    }

    public ArrayList<Pixel> getSnakePixel(){
        ArrayList<Pixel> snakePixels = new ArrayList<>();

        for (GameObject snake : gameObjects){
            if (snake instanceof Snake){
                for(Pixel pix : snake.pixels){
                    snakePixels.add(pix);
                }
            }
        }

        return snakePixels;
    }

    public void clearBoard(){
        this.gameObjects.clear();
    }

    public Pixel checkPongBoardForCollisions(){
        for(GameObject gameObj: gameObjects){
            if(gameObj instanceof PongBall){
                PongBall pongBall = (PongBall) gameObj;

                Direction collisionDirection = null;
                if(pongBall.getIncrementXPosition() < 0.0){
                    collisionDirection = Direction.LEFT;
                }
                if(pongBall.getIncrementXPosition() > 14){
                    collisionDirection = Direction.RIGHT;
                }
                if(pongBall.getIncrementYPosition() > 14){
                    collisionDirection = Direction.UP;
                }
                if(pongBall.getIncrementYPosition() < 0){
                    collisionDirection = Direction.DOWN;
                }

                if(collisionDirection!=null) {
                    if(pongBall.collideWithWall(collisionDirection) == -1){
                        Color culpritColor = Color.WHITE;
                        if((int) pongBall.getPositionY() == 0){
                            culpritColor = PongGame.playerDOWN.color;
                        }
                        return new Pixel((int)pongBall.getPositionX(),(int) pongBall.getPositionY(),
                                culpritColor);
                    }
                    return null;
                }

                for(GameObject player : gameObjects){
                    if(player instanceof PongPlayer){
                        if(player.getFirstPixel().row == 0 && pongBall.getIncrementYPosition() < 1){
                            if(((PongPlayer) player).getBorderXLeft() < pongBall.getIncrementXPosition() &&
                                    ((PongPlayer) player).getBorderXRight() > pongBall.getIncrementXPosition()){

                                float offsetToRight =((PongPlayer) player).getBorderXRight()-
                                        pongBall.getIncrementXPosition();

                                float width = ((PongPlayer) player).getBorderXRight()-
                                        ((PongPlayer) player).getBorderXLeft();

                                float degree = 180*offsetToRight/width;
                                pongBall.collide(degree, Direction.DOWN);
                            }
                        }
                    }
                }
            }
        }
        return null;
    }


    public void updatePongBoardSmooth(){
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                board[i][j] = Color.BLACK;
            }
        }

        for(GameObject pongObj: gameObjects){
            if(pongObj instanceof PongBall){
                PongBall pongBall = (PongBall) pongObj;
                int positionXInteger = (int) pongBall.getPositionX();
                int positionYInteger = (int) pongBall.getPositionY();

                double pongXPos = pongBall.getPositionX();
                double pongYPos = pongBall.getPositionY();


                Color pixelColor;
                for(int i = positionXInteger-1; i<=positionXInteger+1; i++){
                    for(int j = positionYInteger-1; j<=positionYInteger+1; j++){
                        if (i >= 0 && i < 14 && j >= 0 && j < 14) {
                            pixelColor = pongBall.color;

                            //System.out.println(j + "   " + i);
                            //System.out.println(cutoff(1-(pongXPos-(j+0.5)))+ "   " + cutoff(1-(pongYPos-(i+0.5))));

                            pixelColor = new Color(
                                    (int) (pixelColor.getRed()*cutoff(1-Math.abs(pongYPos-(j+0.5)))),
                                    (int) (pixelColor.getGreen()*cutoff(1-Math.abs(pongYPos-(j+0.5)))),
                                    (int) (pixelColor.getBlue()*cutoff(1-Math.abs(pongYPos-(j+0.5)))));

                            pixelColor = new Color(
                                    (int) (pixelColor.getRed()*cutoff(1-Math.abs(pongXPos-(i+0.5)))),
                                    (int) (pixelColor.getGreen()*cutoff(1-Math.abs(pongXPos-(i+0.5)))),
                                    (int) (pixelColor.getBlue()*cutoff(1-Math.abs(pongXPos-(i+0.5)))));

                            //System.out.println(pixelColor.getBlue());
                            board[i][j] = pixelColor;
                        }
                    }
                }

            }
            if(pongObj instanceof PongPlayer){
                PongPlayer player = (PongPlayer) pongObj;


                for(int i = (int) player.getBorderXLeft()+1; i <= (int) player.getBorderXRight()-1;i++){
                    board[i][player.getFirstPixel().row] = player.color;
                }


                if (player.getBorderXLeft() >= 0 && player.getBorderXRight() < 14 &&
                        player.getFirstPixel().row >= 0 && player.getFirstPixel().row < 14) {

                    float scalingL = 1-(player.getBorderXLeft() - (int) player.getBorderXLeft());
                    float scalingR = player.getBorderXRight() - (int) player.getBorderXRight();

                    Color colorL = new Color(
                            (int) (player.color.getRed()*scalingL),
                            (int) (player.color.getGreen()*scalingL),
                            (int) (player.color.getBlue()*scalingL)
                    );

                    Color colorR = new Color(
                            (int) (player.color.getRed()*scalingR),
                            (int) (player.color.getGreen()*scalingR),
                            (int) (player.color.getBlue()*scalingR)
                    );

                    board[(int) player.getBorderXLeft()][player.getFirstPixel().row] = colorL;
                    board[(int) player.getBorderXRight()][player.getFirstPixel().row] = colorR;
                }


            }
        }
    }


    public synchronized void updatePongBoard(){
        for (int i = 0; i < 14; i++) {
            for (int j = 0; j < 14; j++) {
                board[i][j] = Color.BLACK;
            }
        }

        for(GameObject pongObj: gameObjects){
            if(pongObj instanceof PongBall){
                PongBall pongBall = (PongBall) pongObj;
                int positionXInteger = (int) pongBall.getPositionX();
                int positionYInteger = (int) pongBall.getPositionY();

                double pongXPos = pongBall.getPositionX();
                double pongYPos = pongBall.getPositionY();


                if (positionXInteger >= 0 && positionXInteger < 14 && positionYInteger >= 0 && positionYInteger < 14) {
                    board[positionXInteger][positionYInteger] = pongBall.color;
                }


            }
            if(pongObj instanceof PongPlayer){
                PongPlayer player = (PongPlayer) pongObj;


                for(int i = (int) player.getBorderXLeft()+1; i <= (int) player.getBorderXRight()-1;i++){
                    board[i][player.getFirstPixel().row] = player.color;
                }


                if (player.getBorderXLeft() >= 0 && player.getBorderXRight() < 14 &&
                        player.getFirstPixel().row >= 0 && player.getFirstPixel().row < 14) {

                    float scalingL = 1-(player.getBorderXLeft() - (int) player.getBorderXLeft());
                    float scalingR = player.getBorderXRight() - (int) player.getBorderXRight();

                    Color colorL = new Color(
                            (int) (player.color.getRed()*scalingL),
                            (int) (player.color.getGreen()*scalingL),
                            (int) (player.color.getBlue()*scalingL)
                    );

                    Color colorR = new Color(
                            (int) (player.color.getRed()*scalingR),
                            (int) (player.color.getGreen()*scalingR),
                            (int) (player.color.getBlue()*scalingR)
                    );

                    board[(int) player.getBorderXLeft()][player.getFirstPixel().row] = colorL;
                    board[(int) player.getBorderXRight()][player.getFirstPixel().row] = colorR;
                }


            }

        }
    }


    private static double cutoff(double num){
        num = Math.abs(num);
        if(num >= 0 && num <= 1){
            return num;
        }else{
            return 0;
        }
    }

}
