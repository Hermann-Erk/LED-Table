package model;

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
                                // TODO runDeathAnimation(Color culprit)
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

}
