package Model.items.tools;

import Model.elements.Hero;

public class Axe extends Tool {
    public Axe(Hero hero) {
        super(hero);
    }

    @Override
    public void updateStats() {
        int heroLevel = this.hero.getLevel().getNumLevel();
        this.setStats(new ToolStats(1 + heroLevel / 5, 10 + heroLevel / 5, 2));
    }
}
