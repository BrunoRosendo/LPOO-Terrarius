package com.lpoo.terrarius.viewer.game;

import com.lpoo.terrarius.gui.GUI;
import com.lpoo.terrarius.model.game.BlockPouch;
import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.arena.Arena;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.model.game.items.Item;
import com.lpoo.terrarius.utils.Dimensions;
import com.lpoo.terrarius.model.game.items.Toolbar;
import com.lpoo.terrarius.viewer.image.Image;
import net.jqwik.api.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class ToolbarViewerTest {
    private ToolbarViewer toolbarViewer;
    private Toolbar toolbar;
    private Image image;
    private GUI gui;
    private BlockPouch blockPouch;
    private Item currItem;

    @BeforeEach
    public void setup() {
        image = Mockito.mock(Image.class);
        gui = Mockito.mock(GUI.class);

        toolbar = Mockito.mock(Toolbar.class);
        currItem = Mockito.mock(Item.class);
        Mockito.when(toolbar.getActiveItem()).thenReturn(currItem);

        blockPouch = Mockito.mock(BlockPouch.class);
        Mockito.when(blockPouch.getCurrentBlockName()).thenReturn("StoneBlock");
        Mockito.when(blockPouch.getCurrentBlockQuantity()).thenReturn(10);

        Mockito.when(toolbar.getDimensions()).thenReturn(new Dimensions(7, 128));
        Mockito.when(toolbar.getBlockPouch()).thenReturn(blockPouch);

        toolbarViewer = new ToolbarViewer();
        toolbarViewer.setImage(image);
    }

    @Test
    public void draw() {
        toolbarViewer.draw(toolbar, new Dimensions(50, 50), gui);

        Mockito.verify(image, Mockito.times(1))
                .draw(new Position(0, 50), Element.Orientation.RIGHT, gui);
    }

    @Property
    public void calculateIconPosition(@ForAll("indexGenerator") Integer iconIndex) {
        this.setup();

        Arena arena = Mockito.mock(Arena.class);
        Mockito.when(arena.getHeight()).thenReturn(71);
        Mockito.when(arena.getWidth()).thenReturn(128);

        Position currIconPos = toolbarViewer.calculateIconPosition(arena, toolbar, iconIndex);
        Position nextIconPos = toolbarViewer.calculateIconPosition(arena, toolbar, iconIndex+1);
        Position previousIconPos = toolbarViewer.calculateIconPosition(arena, toolbar, iconIndex-1);

        Assertions.assertTrue((currIconPos.getY() == previousIconPos.getY()) && (currIconPos.getY() == nextIconPos.getY()));
        Assertions.assertTrue(currIconPos.getX() == previousIconPos.getX() + toolbar.getToolbarCellLength() + 1);
        Assertions.assertTrue(nextIconPos.getX() == currIconPos.getX() + toolbar.getToolbarCellLength() + 1);
    }

    @Provide
    Arbitrary<Integer> indexGenerator() {
        return Arbitraries.integers().between(2, 8);
    }
}
