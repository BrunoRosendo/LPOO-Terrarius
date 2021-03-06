package com.lpoo.terrarius.gui;

import java.io.IOException;
import java.util.List;

public interface GUI {
    void drawCharacter(int x, int y, char c, String charColor, String bgColor);
    void drawCharacter(int x, int y, char c);
    void drawString(int x, int y, String message, String charColor, String bgColor);
    int getFontSize();
    int getMouseX();
    int getMouseY();
    void clear();
    void refresh() throws IOException;
    void close() throws IOException;
    int getWidth();
    int getHeight();
    List<ACTION> getNextActions() throws IOException;

    enum ACTION { UP, DOWN, RIGHT, LEFT, UP_MENU, DOWN_MENU, LEFT_MENU, RIGHT_MENU,
        PRESS, CLICK, QUIT, SELECT, SLOT0, SLOT1, SLOT2, SLOT3, SLOT4, SLOT5,
        SLOT6, SLOT7, SLOT8, SLOT9,
        SKILL_TREE, ITEM_SHOP
    }
}
