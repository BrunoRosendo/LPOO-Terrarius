package Terrarius.Model.SkillTree.Skills;

import Terrarius.Model.Game.elements.hero.HeroStats;

public class SpeedSkill extends Skill {
    public SpeedSkill() {
        super("Speed", 10);
    }

    @Override
    public void applyEffect(HeroStats stats) {
        stats.setSpeed(stats.getSpeed() + 1);
    }
}