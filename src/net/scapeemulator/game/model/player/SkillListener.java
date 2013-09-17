package net.scapeemulator.game.model.player;

public interface SkillListener {

	public void skillChanged(SkillSet set, int skill);

	public void skillLevelledUp(SkillSet set, int amount, int skill);
    
    public void combatLevelledUp(SkillSet set, int combat);

}
