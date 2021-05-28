package Terrarius.Model.Game.elements;

import Terrarius.Model.Game.items.tools.Tool;
import Terrarius.Utils.Dimensions;
import Terrarius.Model.Game.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

public class Block extends Element {
    private int hp;
    private int hardness;
    private final String name;

    public Block(Position position, String name) throws FileNotFoundException, URISyntaxException {
        super(position, new Dimensions(4, 4));
        this.name = name;
        loadBlock();
    }

    public int getHP() {
        return this.hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHardness() {
        return hardness;
    }

    private void loadBlock() throws URISyntaxException, FileNotFoundException {
        URL resource = Tool.class.getResource("/assets/blocks/" + getComponentName() + ".txt");
        Scanner scanner = new Scanner(new File(resource.toURI()));

        this.hp = scanner.nextInt();
        this.hardness = scanner.nextInt();
    }

    @Override
    public String getComponentName() {
        return this.name;
    }
}
