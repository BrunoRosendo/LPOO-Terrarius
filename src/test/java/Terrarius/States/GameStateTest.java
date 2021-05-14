package Terrarius.States;

import Terrarius.Controller.Game.GameController;
import Terrarius.GUI.GUI;
import Terrarius.Game;
import Terrarius.Model.arena.Arena;
import Terrarius.Viewer.Game.ArenaViewer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.Arrays;

public class GameStateTest {
    private State state;
    private GUI gui;
    private Arena arena;

    @BeforeEach
    public void setup() throws IOException {
        arena = Mockito.mock(Arena.class);
        state = new GameState(arena);

        gui = Mockito.mock(GUI.class);
        Mockito.when(gui.getNextActions()).thenReturn(Arrays.asList());
    }

    @Test
    public void arenaSpecifiers() {
        Assertions.assertTrue(state.getModel() instanceof Arena);
        Assertions.assertTrue(state.getController() instanceof GameController);
        Assertions.assertTrue(state.getViewer() instanceof ArenaViewer);
    }

    @Test
    public void guiReadInput() throws IOException {
        Game game = Mockito.mock(Game.class);
        state.readInput(game, gui);
        Mockito.verify(gui, Mockito.times(1)).getNextActions();
    }
}
