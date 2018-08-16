package PixelArtAndAnimation;

import model.Board;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by Hermann on 13.08.2018.
 */
public class PixelArtFileParser {
    public static Board loadedBoard;

    public static Board load() {
        loadedBoard = new Board();
        final JFileChooser loadChooser = new JFileChooser();	// Similar to saving we use a filefilter
        //loadChooser.setFileFilter(new ChessFileFilter());		// to select only the .chs files

        boolean chooseIntro = false;
        if(chooseIntro) {
            loadChooser.setCurrentDirectory(new File("."));
            int selection = loadChooser.showOpenDialog(null);        // Here's the loading selection window
            if (selection == JFileChooser.APPROVE_OPTION) {
                try (Scanner scanner = new Scanner(loadChooser.getSelectedFile())) {
                    while (scanner.hasNextLine()) {
                        processLine(scanner.nextLine());
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }else{
            try (Scanner scanner = new Scanner(new File("snake.txt"))) {
                while (scanner.hasNextLine()) {
                    processLine(scanner.nextLine());
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        return loadedBoard;
    }

    private static void processLine(String line) {
        //use a second Scanner to parse the content of each line
        Scanner scanner = new Scanner(line);
        scanner.useDelimiter(" ");
        for (int i = 0; i < 14; i++){
            for (int j = 0; j < 14; j++){
                if (scanner.hasNext()) {
                    try {
                        int red = scanner.nextInt();
                        int green = scanner.nextInt();
                        int blue = scanner.nextInt();

                        loadedBoard.setColorToPixel(i, 13-j, new Color(red, green, blue));

                    } catch (InputMismatchException e) {
                        //Is thrown for the Header, can be ignored
                        //e.printStackTrace();
                    }
                } else {
                    //System.out.println("Empty or invalid line. Unable to process.");
                }
            }
        }
    }

}
