package com.lpoo.terrarius.model.game.items;

import com.lpoo.terrarius.model.game.BlockPouch;
import com.lpoo.terrarius.model.game.elements.hero.Hero;
import com.lpoo.terrarius.model.game.items.tools.Tool;
import com.lpoo.terrarius.utils.Dimensions;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import static com.lpoo.terrarius.utils.GameConstants.*;

public class Toolbar {
    private final Map<Integer, Item> toolBar;
    private Integer activeItemIdx;
    private final Integer maxSlots;

    private final Dimensions dimensions;
    private final int toolbarCellLength;
    private final int toolbarSeparatorWidth;

    private final BlockPouch blockPouch;
    private Tool hand;

    public Toolbar(Hero hero) {
        this.toolBar = new HashMap<>();
        this.activeItemIdx = 1;
        this.maxSlots = TOOLBAR_SLOTS;

        this.dimensions = new Dimensions(TOOLBAR_HEIGHT, SCREEN_WIDTH);
        this.toolbarCellLength = TOOLBAR_CELL_LENGTH;
        this.toolbarSeparatorWidth = TOOLBAR_SEPARATOR_THICKNESS;

        this.blockPouch = new BlockPouch();

        try {
            this.hand = new Tool(hero, "Hand");
            this.toolBar.put(0, new BlockPlacer(hero));
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public Integer getActiveItemIdx() {
        return activeItemIdx;
    }

    public void setActiveItemIdx(Integer activeItemIdx) {

        if (activeItemIdx == 0 && this.activeItemIdx == 0){
            blockPouch.cycleCurrentBlock();
        }
        this.activeItemIdx = activeItemIdx;
    }

    public Item getActiveItem() {
        Item item = this.toolBar.get(activeItemIdx);
        if (item == null) return hand;
        return item;
    }

    public Item getItem(Integer index) {
        return this.toolBar.get(index);
    }

    public void setItem(Integer index, Item item) {
        if (index < 1 || index > 9) return;
        this.toolBar.put(index, item);
    }

    public void removeItem(Integer index) {
        this.toolBar.remove(index);
    }

    public Integer findFreeSlot() {
        for(Integer i = 1; i <= maxSlots; i++) {
            if(!this.toolBar.containsKey(i))
                return i;
        }
        return -1;
    }

    public void updateItems() {
        toolBar.forEach((slot, item) -> item.updateStats());
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Map<Integer, Item> getToolBar() {
        return toolBar;
    }

    public BlockPouch getBlockPouch(){
        return this.blockPouch;
    }

    public int getToolbarCellLength() {
        return this.toolbarCellLength;
    }

    public int getToolbarSeparatorWidth() {
        return toolbarSeparatorWidth;
    }

    public Integer getMaxSlots() {
        return maxSlots;
    }
}
