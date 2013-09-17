package net.scapeemulator.game.task;

public abstract class Task {

	private boolean running = true;
	private int delay;
	private int countdown;

	public Task(int delay, boolean immediate) {
		if (delay < 1)
			throw new IllegalArgumentException();

		this.delay = delay;
		this.countdown = immediate ? 1 : delay;
	}

	public abstract void execute();

    public void setDelay(int delay) {
        this.delay = delay;
    }

	public void tick() {
		if (--countdown == 0) {
			countdown = delay;
			execute();
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void stop() {
		running = false;
	}

}
