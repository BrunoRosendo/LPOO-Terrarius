package com.lpoo.terrarius.viewer.image;

import com.lpoo.terrarius.gui.GUI;
import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.utils.Dimensions;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ColoredImage extends StillImage {
    private final Map<Character, String> charColorMap;
    private char[][] charColors;
    private final Map<Character, String> bgColorMap;
    private char[][] bgColors;

    public ColoredImage() {
        charColorMap = new HashMap<>();
        bgColorMap = new HashMap<>();
    }

    public Dimensions getDimension(){
        return super.getDimensions();
    }

    @Override
    public void load(String fname) throws FileNotFoundException, URISyntaxException {
            Scanner imageScanner = getScannerFromFile(fname);
            int height = imageScanner.nextInt();
            int width = imageScanner.nextInt();

            loadAspect(imageScanner, width, height);
            loadCharColors(imageScanner, width, height);
            loadBackgroundColors(imageScanner, width, height);
    }

    @Override
    public void draw(Position position, Element.Orientation orientation, GUI gui) {
        for(int i = 0; i < this.dimensions.getHeight(); i++){
            for (int j = 0; j < this.dimensions.getWidth(); j++){

                char aspect_char = ' ';
                String charColor;
                String bgColor;

                if (orientation == Element.Orientation.RIGHT) {
                    aspect_char = aspect[i][j];
                    bgColor = bgColorMap.get(bgColors[i][j]);
                    charColor = charColorMap.get(charColors[i][j]);
                }
                else {
                    aspect_char = aspect[i][this.dimensions.getWidth() - 1 - j];
                    bgColor = bgColorMap.get(bgColors[i][this.dimensions.getWidth() - 1 - j]);
                    charColor = charColorMap.get(charColors[i][this.dimensions.getWidth() - 1 - j]);
                }

                if (aspect_char == '.') continue;

                gui.drawCharacter(
                        position.getX()+j, position.getY()+i,
                        aspect_char, charColor, bgColor);
            }
        }
    }

    protected void loadCharColors(Scanner imageScanner, int width, int height) {
        charColors = new char[height][width];

        int noCharColors = imageScanner.nextInt();

        imageScanner.nextLine();

        for (int i = 0; i < noCharColors; i++) {
            char key;
            key = imageScanner.next().charAt(0);

            String value;
            value = imageScanner.next();

            charColorMap.put(key, value);
            imageScanner.nextLine();
        }

        for(int i = 0; i < height; i++){

            String data = imageScanner.nextLine();

            for (int j = 0; j < width; j++){
                charColors[i][j] = data.charAt(j);
            }
        }
    }

    protected void loadBackgroundColors(Scanner imageScanner, int width, int height) {
        bgColors = new char[height][width];

        int noBgColors = imageScanner.nextInt();

        imageScanner.nextLine();

        for (int i = 0; i < noBgColors; i++) {
            char key;
            key = imageScanner.next().charAt(0);

            String value;
            value = imageScanner.next();

            bgColorMap.put(key, value);
            imageScanner.nextLine();
        }

        for(int i = 0; i < height; i++) {

            String data = imageScanner.nextLine();

            for (int j = 0; j < width; j++){
                bgColors[i][j] = data.charAt(j);
            }
        }
    }
}
