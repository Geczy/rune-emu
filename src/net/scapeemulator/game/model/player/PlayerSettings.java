package net.scapeemulator.game.model.player;

import net.scapeemulator.game.msg.impl.ConfigMessage;
import net.scapeemulator.game.msg.impl.ScriptMessage;

public final class PlayerSettings {

	private final Player player;
	private int attackStyle;
	private boolean autoRetaliating = true;
	private boolean running = false; /* TODO move to Player as it isn't saved */
	private boolean twoButtonMouse = true;
	private boolean chatFancy = true;
	private boolean privateChatSplit = false;
	private boolean acceptingAid = false;

	public PlayerSettings(Player player) {
		this.player = player;
	}

	public void setRunning(boolean running) {
		this.running = running;
		refreshRunning();
	}

	public boolean isRunning() {
		return running;
	}

	public void toggleRunning() {
		running = !running;
		refreshRunning();
	}

	public void setAttackStyle(int attackStyle) {
		this.attackStyle = attackStyle;
		refreshAttackStyle();
	}

	public int getAttackStyle() {
		return attackStyle;
	}

	public void toggleAutoRetaliating() {
		autoRetaliating = !autoRetaliating;
		refreshAutoRetaliating();
	}

	public void setAutoRetaliating(boolean autoRetaliating) {
		this.autoRetaliating = autoRetaliating;
		refreshAutoRetaliating();
	}

	public boolean isAutoRetaliating() {
		return autoRetaliating;
	}

	public void setTwoButtonMouse(boolean twoButtonMouse) {
		this.twoButtonMouse = twoButtonMouse;
		refreshTwoButtonMouse();
	}

	public void toggleTwoButtonMouse() {
		twoButtonMouse = !twoButtonMouse;
		refreshTwoButtonMouse();
	}

	public boolean isTwoButtonMouse() {
		return twoButtonMouse;
	}

	public void setChatFancy(boolean chatFancy) {
		this.chatFancy = chatFancy;
		refreshChatFancy();
	}

	public void toggleChatFancy() {
		chatFancy = !chatFancy;
		refreshChatFancy();
	}

	public boolean isChatFancy() {
		return chatFancy;
	}

	public void setPrivateChatSplit(boolean privateChatSplit) {
		this.privateChatSplit = privateChatSplit;
		refreshPrivateChatSplit();
	}

	public void togglePrivateChatSplit() {
		privateChatSplit = !privateChatSplit;
		refreshPrivateChatSplit();
	}

	public boolean isPrivateChatSplit() {
		return privateChatSplit;
	}

	public void setAcceptingAid(boolean acceptingAid) {
		this.acceptingAid = acceptingAid;
		refreshAcceptingAid();
	}

	public void toggleAcceptingAid() {
		acceptingAid = !acceptingAid;
		refreshAcceptingAid();
	}

	public boolean isAcceptingAid() {
		return acceptingAid;
	}

	public Player getPlayer() {
		return player;
	}

	public void refresh() {
		refreshRunning();
		refreshAttackStyle();
		refreshAutoRetaliating();
		refreshTwoButtonMouse();
		refreshChatFancy();
		refreshPrivateChatSplit();
		refreshAcceptingAid();
	}

	private void refreshRunning() {
		player.send(new ConfigMessage(173, running ? 1 : 0));
	}

	private void refreshAttackStyle() {
		player.send(new ConfigMessage(43, attackStyle));
	}

	private void refreshAutoRetaliating() {
		player.send(new ConfigMessage(172, autoRetaliating ? 0 : 1));
	}

	private void refreshTwoButtonMouse() {
		player.send(new ConfigMessage(170, twoButtonMouse ? 0 : 1));
	}

	private void refreshChatFancy() {
		player.send(new ConfigMessage(171, chatFancy ? 0 : 1));
	}

	private void refreshPrivateChatSplit() {
		player.send(new ConfigMessage(287, privateChatSplit ? 1 : 0));

		if (privateChatSplit) {
			player.send(new ScriptMessage(83, "")); // TODO check (Xeno uses type of s but blank params array?)
		}
	}

	private void refreshAcceptingAid() {
		player.send(new ConfigMessage(427, acceptingAid ? 1 : 0));
	}

}
