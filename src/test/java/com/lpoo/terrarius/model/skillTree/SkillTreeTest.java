package com.lpoo.terrarius.model.skillTree;

import com.lpoo.terrarius.model.game.Level;
import com.lpoo.terrarius.model.game.elements.hero.HeroStats;
import com.lpoo.terrarius.model.skillTree.skills.HealthSkill;
import com.lpoo.terrarius.model.skillTree.skills.Skill;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class SkillTreeTest {

    SkillTree skillTree;
    HeroStats heroStats;

    @BeforeEach
    public void setUp(){

        heroStats = new HeroStats();

        skillTree = new SkillTree(heroStats);

    }

    @Test
    public void upgradeSkillLimitedPoints(){

        heroStats.setLevel(new Level(3, 1));

        Skill skill = Mockito.mock(Skill.class);

        Mockito.when(skill.getUpgradeCost()).thenReturn(10);
        Mockito.when(skill.upgrade()).thenReturn(true);
        Mockito.when(skill.getCurrLevel()).thenReturn(1);

        skillTree.upgradeSkill(skill);
        skillTree.upgradeSkill(skill);
        skillTree.upgradeSkill(skill);
        skillTree.upgradeSkill(skill);

        Mockito.verify(skill, Mockito.times(3)).upgrade();
    }

    @Test
    public void upgradeSkillMax(){

        heroStats.setLevel(new Level(10000, 1));

        Skill skill = new HealthSkill();

        for (int i = 0; i<100; i++)
            skillTree.upgradeSkill(skill);

        Assertions.assertEquals(skill.getCurrLevel(), Skill.getMaxLevel());
    }

}
