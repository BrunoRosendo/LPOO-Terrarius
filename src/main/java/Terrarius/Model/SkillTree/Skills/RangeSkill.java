package Terrarius.Model.SkillTree.Skills;

import Terrarius.Model.Game.elements.hero.HeroStats;

public class RangeSkill extends Skill {
    public RangeSkill() {
        super("Range", 5);
    }

    @Override
    public void applyEffect(HeroStats stats) {
        // Temporary since we don't have defense stat
        stats.setRange(stats.getRange() + 1);
    }
}