package Terrarius.Model.Game.items.tools;

import Terrarius.Utils.Dimensions;
import Terrarius.Model.Game.elements.hero.Hero;

public class Shovel extends Tool {
    public Shovel(Hero hero) {
        super(hero, new Dimensions(5, 3));
    }

    @Override
    public void updateStats() {
        int heroLevel = this.getHero().getStats().getCurrentLevel();
        this.setStats(new ToolStats(1 + heroLevel / 20, 10, 1));
    }

    @Override
    public String getComponentName() {
        return "Shovel";
    }
}
