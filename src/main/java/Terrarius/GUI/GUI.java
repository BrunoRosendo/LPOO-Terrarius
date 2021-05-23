package Terrarius.GUI;

import java.io.IOException;
import java.util.List;

public interface GUI {
    void drawCharacter(int x, int y, char c, String charColor, String bgColor);
    void drawCharacter(int x, int y, char c);
    void clear();
    void refresh() throws IOException;
    void close() throws IOException;
    List<ACTION> getNextActions() throws IOException;
    // TODO: ADD KEY NUMBERS
    enum ACTION {UP, DOWN, RIGHT, LEFT, CLICK, QUIT}
}