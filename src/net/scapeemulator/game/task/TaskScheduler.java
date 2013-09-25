package net.scapeemulator.game.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class TaskScheduler {

	private final List<Task> tasks = new ArrayList<>();
	private final List<Task> taskQueue = new ArrayList<>();

	public void schedule(Task task) {
		taskQueue.add(task);
	}

	public void tick() {
		for (Task task : taskQueue) {
			tasks.add(task);
		}
		for (Iterator<Task> it = tasks.iterator(); it.hasNext();) {
			Task task = it.next();
			if (!task.isRunning()) {
				it.remove();
				continue;
			}
			task.tick();
		}
	}

}
