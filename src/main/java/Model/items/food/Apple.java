package Model.items.food;

import Model.elements.Hero;

public class Apple extends Food {
    public Apple(Hero hero) {
        super(hero);
    }

    @Override
    public void updateStats(Hero hero) {
        int heroLevel = hero.getLevel().getNumLevel();
        int duration = Math.max(10 - heroLevel / 5, 1);
        this.setStats(new FoodStats(10, duration));
    }
}