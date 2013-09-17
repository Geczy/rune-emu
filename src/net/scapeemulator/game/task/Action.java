package net.scapeemulator.game.task;

import net.scapeemulator.game.model.mob.Mob;

public abstract class Action<T extends Mob> extends Task {

	protected final T mob;

	public Action(T mob, int delay, boolean immediate) {
		super(delay, immediate);
		this.mob = mob;
	}

	public T getMob() {
		return mob;
	}

	@Override
	public void stop() {
		super.stop();
		mob.stopAction();
	}

}
