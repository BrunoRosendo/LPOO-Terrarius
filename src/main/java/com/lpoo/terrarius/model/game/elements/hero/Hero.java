package com.lpoo.terrarius.model.game.elements.hero;

import com.lpoo.terrarius.model.game.Level;
import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.model.game.items.Item;
import com.lpoo.terrarius.model.game.items.Toolbar;
import com.lpoo.terrarius.model.game.items.buffs.Buff;
import com.lpoo.terrarius.utils.Dimensions;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class Hero extends Element {
    private Toolbar toolBar;
    private Position targetPosition;
    private final HeroStats stats;
    private final List<Buff> activeBuffs;

    public Hero(Position position) throws FileNotFoundException, URISyntaxException {
        super(position, "Hero");
        this.stats = new HeroStats();
        this.toolBar = new Toolbar(this);
        this.activeBuffs = new ArrayList<>();
    }

    public void addItemFreeSlot(Item item) {
        Integer key = this.toolBar.findFreeSlot();
        if (key != -1) this.toolBar.setItem(key, item);
    }

    public void addItem(Integer toolBarPosition, Item item) {
        this.toolBar.setItem(toolBarPosition, item);
    }

    public void removeItem(Integer toolBarPosition) {
        this.toolBar.removeItem(toolBarPosition);
    }

    public Dimensions getDimensionsWithItem() {
        if(toolBar.getActiveItemIdx() == 0) return this.getDimensions();

        Dimensions totalDimensions = new Dimensions(this.getDimensions());
        totalDimensions.incrementWidth(toolBar.getActiveItem().getDimensions().getWidth());
        return totalDimensions;
    }

    public Boolean targetWithinRange(){
        double dist = Math.sqrt(
                Math.pow((this.getPosition().getX() + this.getDimensions().getWidth()/2.0) - targetPosition.getX(), 2) +
                        Math.pow((this.getPosition().getY() + this.getDimensions().getHeight()/2.0) - targetPosition.getY(), 2));
        return dist <= stats.getRange();
    }

    public HeroStats getStats() {
        return stats;
    }

    public void setLevel(Level level) {
        this.stats.setLevel(level);
    }

    public void setHealth(int health) {
        this.stats.setHP(health);
    }

    public void setRange(double range) {
        this.stats.setRange(range);
    }

    public void setPower(int power) {
        this.stats.setPower(power);
    }

    public void setSpeed(int speed) {
        this.stats.setSpeed(speed);
    }

    public Toolbar getToolBar() {
        return toolBar;
    }

    public void setToolBar(Toolbar toolBar) {
        this.toolBar = toolBar;
    }

    public Position getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(Position targetPosition) {
        this.targetPosition = targetPosition;
    }

    public void addBuff(Buff buff) {
        int i = 0;
        for (; i < activeBuffs.size(); i++){
            if (buff.getStats().getDuration() < activeBuffs.get(i).getStats().getDuration()) break;
        }
        this.activeBuffs.add(i, buff);
    }

    public void removeBuff(Buff buff) {
        this.activeBuffs.remove(buff);
    }

    public List<Buff> getActiveBuffs() {
        return activeBuffs;
    }

    @Override
    protected void loadElement() {
        this.setDimensions(new Dimensions(8, 4));
    }
}
