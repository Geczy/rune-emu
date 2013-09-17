package net.scapeemulator.game.model.player;

import net.scapeemulator.game.model.SpotAnimation;
import net.scapeemulator.game.model.player.inter.CloseOnInputComponentListener;
import net.scapeemulator.game.msg.impl.SkillMessage;
import net.scapeemulator.game.util.StringUtils;

public final class SkillMessageListener implements SkillListener {
    
    private static final SpotAnimation fireworks = new SpotAnimation(199, 0, 100);

    private final Player player;

    public SkillMessageListener(Player player) {
        this.player = player;
    }

    @Override
    public void skillChanged(SkillSet set, int skill) {
        int level = set.getCurrentLevel(skill);
        int experience = (int) set.getExperience(skill);
        player.send(new SkillMessage(skill, level, experience));
    }

    @Override
    public void skillLevelledUp(SkillSet set, int amount, int skill) {
        //TODO: If in combat, dont pop this up
        player.setInterfaceText(740, 0, "Congratulations! You've advanced a " + StringUtils.capitalize(Skill.getName(skill)) + " level!");
        player.setInterfaceText(740, 1, "You have reached level " + set.getMaximumLevel(skill) + "!");
        player.getStateSet().setBitState(4757, Skill.getConfigValue(skill));
        player.getInterfaceSet().openChatbox(740, new CloseOnInputComponentListener());
        
        int level = set.getMaximumLevel(skill);
        String msg = "You've just advanced " + (amount == 1 ? "a" : String.valueOf(amount)) + " "
                + StringUtils.capitalize(Skill.getName(skill)) + " level" + (amount == 1 ? "" : "s") + "! You have reached level " + level + ".";
        player.sendMessage(msg);
        player.getStateSet().setBitState(Skill.getFlashingIcon(skill), true);
        
        player.playSpotAnimation(fireworks);
    }

    @Override
    public void combatLevelledUp(SkillSet set, int combat) {
        player.sendMessage("Congratulations! You've just reached combat level " + combat + "!");
    }
}
