package com.lpoo.terrarius.controller.game;

import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.arena.Arena;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.model.game.elements.enemies.Enemy;
import com.lpoo.terrarius.model.game.elements.hero.Hero;

import java.util.HashMap;
import java.util.Map;

import static com.lpoo.terrarius.utils.GameConstants.*;

public class EnemyController {

    private final Arena arena;
    private Map<Enemy, Integer> fallingVelocities;
    private Map<Enemy, Integer> gravityFrameCounters;

    public EnemyController(Arena arena) {
        this.arena = arena;
        this.updateEnemies();
    }

    public void moveEnemies() {
        for (Enemy enemy : arena.getEnemies()) {
            if (!canFollowHero(enemy, arena.getHero()))
                continue;

            Position enemyPos = enemy.getPosition();
            Position heroPos = arena.getHero().getPosition();

            if (enemyPos.getX() < heroPos.getX()) {
                moveEnemy(enemy, enemyPos.getRight());
                enemy.setOrientation(Element.Orientation.RIGHT);
            }
            else if (enemyPos.getX() > heroPos.getX()) {
                moveEnemy(enemy, enemyPos.getLeft());
                enemy.setOrientation(Element.Orientation.LEFT);
            }
        }
    }

    public void fallEnemies() {
        for (Enemy enemy : arena.getEnemies()) {
            Position lastPos = enemy.getPosition();
            for (int i = 0; i < fallingVelocities.get(enemy); ++i)
                moveEnemy(enemy, enemy.getPosition().getDown());

            if (lastPos.equals(enemy.getPosition())) {
                fallingVelocities.replace(enemy, 1);
                gravityFrameCounters.replace(enemy, 0);
                continue;
            }

            gravityFrameCounters.replace(enemy, gravityFrameCounters.get(enemy) + 1);
            if (gravityFrameCounters.get(enemy) % FRAMES_PER_APPLY_GRAVITY == 0)
                fallingVelocities.replace(enemy, fallingVelocities.get(enemy) + GRAVITY);
        }
    }

    private void moveEnemy(Enemy enemy, Position position) {
        if (position.getX() < 0
                || position.getX() + enemy.getDimensions().getWidth() >= SCREEN_WIDTH
                || position.getY() < 0
                || position.getY() + enemy.getDimensions().getHeight() > SCREEN_HEIGHT - TOOLBAR_HEIGHT - 3)
            return;

        if ( (!arena.collidesWithBlocks(position, enemy.getDimensions())) && (!hasEnemy(enemy, position)) ) {
            enemy.setPosition(position);
        }
    }

    public void damageHero(){
        for (Enemy enemy : this.arena.getEnemies()){
            if (Position.checkElementsCollision(enemy.getPosition(), enemy.getDimensions(),
                    this.arena.getHero().getPosition(), this.arena.getHero().getDimensions()))
                arena.getHero().setHealth(arena.getHero().getStats().getHp() - enemy.getStats().getPower());
        }
    }

    public void updateEnemies() {
        fallingVelocities = new HashMap<>();
        gravityFrameCounters = new HashMap<>();

        for (Enemy enemy : arena.getEnemies()) {
            fallingVelocities.put(enemy, 1);
            gravityFrameCounters.put(enemy, 0);
        }
    }

    private boolean hasEnemy(Enemy enemy, Position position) {
        for (Enemy currEnemy : arena.getEnemies()) {
            if (currEnemy.equals(enemy)) continue;
            if (Position.checkElementsCollision(position, enemy.getDimensions(), currEnemy.getPosition(), currEnemy.getDimensions()))
                return true;
        }
        return false;
    }

    private boolean canFollowHero(Enemy enemy, Hero hero) {
        return Math.abs(enemy.getPosition().getX() - hero.getPosition().getX()) <= enemy.getStats().getViewDistance()
                && Math.abs(enemy.getPosition().getY() - hero.getPosition().getY()) <=
                    Math.max(hero.getDimensions().getHeight(), enemy.getDimensions().getHeight());
    }
}
