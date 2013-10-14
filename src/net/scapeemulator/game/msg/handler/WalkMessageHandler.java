package net.scapeemulator.game.msg.handler;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.mob.WalkingQueue;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.MessageHandler;
import net.scapeemulator.game.msg.impl.WalkMessage;
import net.scapeemulator.game.msg.impl.WalkMessage.Step;

public final class WalkMessageHandler extends MessageHandler<WalkMessage> {

	@Override
	public void handle(Player player, WalkMessage message) {
		int z = player.getPosition().getHeight();

		WalkingQueue queue = player.getWalkingQueue();

		Step[] steps = message.getSteps();
		Position position = new Position(steps[0].getX(), steps[0].getY(), z);
		queue.addFirstStep(position);
		queue.setRunningQueue(message.isRunning()); // must be after first step which reset()s

		for (int i = 1; i < steps.length; i++) {
			position = new Position(steps[i].getX(), steps[i].getY(), z);
			queue.addStep(position);
		}

                player.resetTurnToTarget();
                player.getInterfaceSet().resetAll();
		player.stopAction();
	}

}
