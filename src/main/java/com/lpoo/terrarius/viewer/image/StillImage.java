package com.lpoo.terrarius.viewer.image;

import com.lpoo.terrarius.gui.GUI;
import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.utils.Dimensions;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;

public class StillImage extends Image{
    protected Dimensions dimensions;
    protected char[][] aspect;

    public StillImage() {
        dimensions = new Dimensions(0, 0);
    }

    public void load(String fname) throws FileNotFoundException, URISyntaxException {
        Scanner imageScanner = getScannerFromFile(fname);
        int height = imageScanner.nextInt();
        int width = imageScanner.nextInt();

        loadAspect(imageScanner, width, height);
    }

    public void draw(Position position, Element.Orientation orientation, GUI gui){
        for(int i = 0; i < this.dimensions.getHeight(); i++){
            for (int j = 0; j < this.dimensions.getWidth(); j++){
                char aspect_char = ' ';

                if(orientation == Element.Orientation.RIGHT){
                    aspect_char = aspect[i][j];
                }
                else{
                    aspect_char = aspect[i][this.dimensions.getWidth() - 1 - j];
                }

                if (aspect_char == '.') continue;

                gui.drawCharacter(position.getX()+j, position.getY()+i, aspect_char);
            }
        }
    }

    protected void loadAspect(Scanner imageScanner, int width, int height) {
        this.dimensions = new Dimensions(height, width);

        aspect = new char[height][width];

        imageScanner.nextLine();

        for(int i = 0; i < height; i++) {

            String data = imageScanner.nextLine();

            for (int j = 0; j < width; j++){
                aspect[i][j] = j >= data.length() ? ' ' : data.charAt(j);
            }
        }
    }

    public Dimensions getDimensions() {
        return dimensions;
    }
}
