package ArduinoInterface;

import Control.Constants;

import java.awt.*;

/**
 * Created by Hermann on 03.08.2018.
 */
public class BoardTransformerWS2812B {

    public static int[] transformToIntArray(Color[][] board){
        int[] intArray = new int[3*14*14+4];

        intArray[0] = Constants.header[0];
        intArray[1] = Constants.header[1];
        intArray[2] = Constants.header[2];
        intArray[3] = Constants.header[3];



        for(int i = 0; i < 14; i++){
            if(i % 2 == 0){
                for(int j = 0; j<14;j++){
                    intArray[i*14*3+j*3+4] = board[i][j].getRed();
                    intArray[i*14*3+j*3+1+4] = board[i][j].getGreen();
                    intArray[i*14*3+j*3+2+4] = board[i][j].getBlue();
                }
            }else{
                for(int j = 13; j>=0;j--){
                    intArray[i*14*3+(13-j)*3+4] = board[i][j].getRed();
                    intArray[i*14*3+(13-j)*3+1+4] = board[i][j].getGreen();
                    intArray[i*14*3+(13-j)*3+2+4] = board[i][j].getBlue();
                }
            }
        }

        return intArray;
    }

    public static void printIntArray(int[] intArray){
        for(int i = 0; i<4; i++){
            System.out.print(intArray[i] + " ");
        }
        System.out.print("\n");
        for(int i = 0; i < 14; i++){
            System.out.print("{");
            for(int j = 0; j < 14; j++){
                System.out.print("{");
                for(int k = 0; k < 3; k++){
                    System.out.print(intArray[i*14*3+j*3+k+4] + " ");
                }
                System.out.print("}");
            }
            System.out.print("}\n");
        }
        System.out.println("------------------------End");
    }
}
