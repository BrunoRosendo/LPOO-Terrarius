package Terrarius;

import Terrarius.GUI.GUI;
import Terrarius.GUI.LanternaGui;
import Terrarius.Model.Game.arena.Arena;
import Terrarius.Model.Game.elements.hero.HeroStats;
import Terrarius.Model.ItemShop.ItemShop;
import Terrarius.Model.Menu.Menu;
import Terrarius.Model.SkillTree.SkillTree;
import Terrarius.States.*;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;

import static Terrarius.Utils.GameConstants.MS_PER_UPDATE;

public class Terrarius {

    private final GUI gui;

    private State state;

    public static void main(String[] args) throws FontFormatException, IOException, URISyntaxException {
        new Terrarius(128, 74).start();
    }

    public Terrarius(int width, int height) throws FontFormatException, IOException, URISyntaxException {
        this.gui = new LanternaGui(width, height);
        this.state = this.createMenuState();
    }

    protected void start() throws IOException, URISyntaxException {
        long previous = System.currentTimeMillis();
        long lag = 0;

        while (true) {
            long current = System.currentTimeMillis();
            long elapsed = current - previous;
            previous = current;
            lag += elapsed;

            while (lag >= MS_PER_UPDATE) {
                state.readInput(this, gui);
                state.update(this);
                lag -= MS_PER_UPDATE;

                if (state == null) {
                    gui.close();
                    return;
                }
            }

            state.draw(gui);
        }
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public GUI getGui() {
        return gui;
    }

    public State createSkillTreeState() {
        HeroStats heroStats = ((Arena) this.state.getModel()).getHero().getStats();
        return new SkillTreeState(new SkillTree(heroStats), this.state);
    }

    public State createItemShopState() {
        return new ItemShopState(new ItemShop(((Arena) this.state.getModel()).getHero()), this.state);
    }

    public State createMenuState() {
        return new MenuState(new Menu());
    }

    public State createGameState() {
        return new GameState(new Arena());
    }
}

