package com.lpoo.terrarius.viewer.skillTree;

import com.lpoo.terrarius.gui.GUI;
import com.lpoo.terrarius.model.game.Position;
import com.lpoo.terrarius.model.game.elements.Element;
import com.lpoo.terrarius.model.skillTree.SkillTree;
import com.lpoo.terrarius.model.skillTree.skills.Skill;
import com.lpoo.terrarius.viewer.image.ColoredImage;
import com.lpoo.terrarius.viewer.Viewer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import static com.lpoo.terrarius.utils.GameConstants.SCREEN_WIDTH;

public class SkillTreeViewer extends Viewer<SkillTree> {
    private ColoredImage image;
    int previousSelected;
    int previousUsedPoints;

    public SkillTreeViewer() {
        super();
        image = new ColoredImage();
        try {
            image.load("Images/SkillTree/SkillTree.txt");
        } catch (FileNotFoundException | URISyntaxException e) {
            e.printStackTrace();
            image = null;
        }
        previousSelected = -1;
        previousUsedPoints = -1;
    }

    @Override
    public void draw(GUI gui, SkillTree model) throws IOException {
        if (!this.needsUpdate(model)) return;
        previousSelected = model.getSelectedIndex();
        previousUsedPoints = model.getUsedPoints();

        gui.clear();

        String title = "Skill Tree";
        gui.drawString((SCREEN_WIDTH - title.length()) /2, 5, title, "#FFFFFF", "#000000");

        Position treePos = new Position(SkillTreeViewerConstants.START_POS_X, SkillTreeViewerConstants.START_POS_Y);
        this.image.draw(treePos, Element.Orientation.RIGHT, gui);

        this.drawSkillsLabels(treePos, gui, model);

        int yOffset = 5;
        String availablePointsString = "Available Points: " + model.getHeroStats().getCurrentPoints();
        int availablePointsX = SkillTreeViewerConstants.AVAILABLE_POINTS_X;
        int availablePointsY = SkillTreeViewerConstants.AVAILABLE_POINTS_Y;

        gui.drawString(availablePointsX, availablePointsY, availablePointsString, "#FFFFFF", "#000000");

        for(int i = 0; i < model.getHeroStats().getSkills().size(); i++) {
            Skill skill = model.getHeroStats().getSkills().get(i);
            String skillInfoPrefix = skill.getName() + ": ";
            String skillInfoHighlighted = Integer.toString(skill.getCurrLevel() );
            String skillInfoSuffix = " / " + Skill.getMaxLevel();
            String highlightColor = skill.getCurrLevel() > 0 ? "#00FF00" : "#FFFFFF";

            gui.drawString(availablePointsX, availablePointsY + (i + 1) * yOffset, skillInfoPrefix, "#FFFFFF", "#000000");
            gui.drawString(availablePointsX + skillInfoPrefix.length(), availablePointsY + (i + 1) * yOffset, skillInfoHighlighted, highlightColor, "#000000");
            gui.drawString(availablePointsX + skillInfoPrefix.length() + skillInfoHighlighted.length(), availablePointsY + (i + 1) * yOffset, skillInfoSuffix, "#FFFFFF", "#000000");
        }

        gui.refresh();
    }

    public boolean needsUpdate(SkillTree model) {
        return ( previousSelected != model.getSelectedIndex() || previousUsedPoints != model.getUsedPoints() );
    }

    public void drawSkillsLabels(Position startingPos, GUI gui, SkillTree model) {
        String skillLabel = "Skills";
        int textXPos = startingPos.getX() + SkillTreeViewerConstants.CENTER_CONTAINER_X - skillLabel.length() / 2;
        int textYPos = startingPos.getY() + SkillTreeViewerConstants.CENTER_CONTAINER_Y - 1;
        gui.drawString(textXPos, textYPos, skillLabel, "#FFFFFF", "#000000");

        for(int i = 0; i < model.getHeroStats().getSkills().size(); i++) {
            Skill currSkill = model.getHeroStats().getSkills().get(i);
            skillLabel = currSkill.getName();

            switch (i) {
                case 0:
                    textXPos = startingPos.getX() + SkillTreeViewerConstants.LEFT_OFFSET + (SkillTreeViewerConstants.SKILL_CONTAINER_WIDTH - skillLabel.length()) / 2;
                    textYPos = startingPos.getY() + SkillTreeViewerConstants.TOP_OFFSET + SkillTreeViewerConstants.SKILL_CONTAINER_HEIGHT / 2;
                    break;
                case 1:
                    textXPos = startingPos.getX() + this.image.getDimensions().getWidth() - SkillTreeViewerConstants.RIGHT_OFFSET - (SkillTreeViewerConstants.SKILL_CONTAINER_WIDTH + skillLabel.length()) / 2;
                    textYPos = startingPos.getY() + SkillTreeViewerConstants.TOP_OFFSET + SkillTreeViewerConstants.SKILL_CONTAINER_HEIGHT / 2;
                    break;
                case 2:
                    textXPos = startingPos.getX() + SkillTreeViewerConstants.LEFT_OFFSET + (SkillTreeViewerConstants.SKILL_CONTAINER_WIDTH - skillLabel.length()) / 2;
                    textYPos = startingPos.getY() + this.image.getDimensions().getHeight() - SkillTreeViewerConstants.BOTTOM_OFFSET - SkillTreeViewerConstants.SKILL_CONTAINER_HEIGHT / 2 - 2;
                    break;
                case 3:
                    textXPos = startingPos.getX() + this.image.getDimensions().getWidth() - SkillTreeViewerConstants.RIGHT_OFFSET - (SkillTreeViewerConstants.SKILL_CONTAINER_WIDTH + skillLabel.length()) / 2;
                    textYPos = startingPos.getY() + this.image.getDimensions().getHeight() - SkillTreeViewerConstants.BOTTOM_OFFSET - SkillTreeViewerConstants.SKILL_CONTAINER_HEIGHT / 2 - 2;
                    break;
                default:
                    return;
            }
            String charColor = "#FFFFFF";
            if (model.getSelectedIndex() == i) charColor = "#00FF00";

            gui.drawString(textXPos, textYPos, skillLabel, charColor, "#000000");

            int costPosY;
            if(i == 0 || i == 1) costPosY = textYPos - (SkillTreeViewerConstants.SKILL_CONTAINER_HEIGHT/2 + 3);
            else costPosY = textYPos + (SkillTreeViewerConstants.SKILL_CONTAINER_HEIGHT/2 + 3);

            String highlightColor = model.getHeroStats().getCurrentPoints() - currSkill.getUpgradeCost() >= 0 ? "#00FF00" : "#FF0000";

            String costInfo = "Cost: ";
            gui.drawString(textXPos, costPosY, costInfo, "#FFFFFF", "#000000");

            String costValue = Integer.toString(currSkill.getUpgradeCost());
            gui.drawString(textXPos + costInfo.length(), costPosY, costValue, highlightColor, "#000000");
        }
    }

    public ColoredImage getImage() {
        return image;
    }

    public void setImage(ColoredImage image) {
        this.image = image;
    }
}
