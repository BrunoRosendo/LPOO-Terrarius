package Model.items;

import Model.Dimensions;

import java.util.HashMap;
import java.util.Map;

import static Viewer.ViewerConstants.*;

public class Toolbar {
    private Map<Integer, Item> toolBar;
    private Integer activeItemIdx;
    private final Integer maxSlots;

    private final Dimensions dimensions;
    private final int toolbarCellLength;
    private final int toolbarSeparatorWidth;

    public Toolbar() {
        this.toolBar = new HashMap<>();
        this.activeItemIdx = 1;  // Just for testing. Should change this to 0 (unarmed) and change when pressing the numbers on the keyboard
        this.maxSlots = TOOLBAR_SLOTS;
        this.dimensions = new Dimensions(TOOLBAR_HEIGHT, SCREEN_WIDTH);
        this.toolbarCellLength = TOOLBAR_CELL_LENGTH;
        this.toolbarSeparatorWidth = TOOLBAR_SEPARATOR_THICKNESS;
    }

    public Integer getActiveItemIdx() {
        return activeItemIdx;
    }

    public void setActiveItemIdx(Integer activeItemIdx) {
        this.activeItemIdx = activeItemIdx;
    }

    public Item getActiveItem() {
        return this.toolBar.get(activeItemIdx);
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

    /**
     * Finds the first free slot on the toolbar
     * @return key of the first free slot. If there is no free slot, returns -1
     */
    public Integer findFreeSlot() {
        for(Integer i = 1; i <= maxSlots; i++) {
            if(!this.toolBar.containsKey(i))
                return i;
        }
        return -1;
    }

    public Dimensions getDimensions() {
        return dimensions;
    }

    public Map<Integer, Item> getToolBar() {
        return toolBar;
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
