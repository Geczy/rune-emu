package net.scapeemulator.game.model;

import net.scapeemulator.game.model.area.QuadArea;
import net.scapeemulator.game.model.grounditem.GroundItemList;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.model.npc.stateful.impl.NormalNPC;
import net.scapeemulator.game.model.object.GroundObjectList;
import net.scapeemulator.game.model.pathfinding.ObjectDataListener;
import net.scapeemulator.game.model.pathfinding.TraversalMap;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.net.game.GameSession;
import net.scapeemulator.game.task.TaskScheduler;
import net.scapeemulator.game.update.PlayerUpdater;
import net.scapeemulator.game.model.grounditem.GroundItemList.Type;

public final class World {

	public static final int MAX_PLAYERS = 2000;

	private static final World world = new World();

	public static World getWorld() {
		return world;
	}

	private final MobList<Player> players = new MobList<>(MAX_PLAYERS);
	private final MobList<NPC> npcs = new MobList<>(32000);
	private final TaskScheduler taskScheduler = new TaskScheduler();
	private final PlayerUpdater updater = new PlayerUpdater(this);
    private final GroundItemList groundItems = new GroundItemList(Type.WORLD);
    private final GroundObjectList groundObjects = new GroundObjectList();
    private final TraversalMap traversalMap = new TraversalMap();

	private World() {
        /* TODO: Is this in the correct place? */
        /* Add the object data listener for the traversal map */
		groundObjects.addListener(new ObjectDataListener(traversalMap));

        NormalNPC normalNPC = new NormalNPC(1);
        normalNPC.setPosition(new Position(3222, 3222));
        normalNPC.setBounds(new QuadArea(3200, 3200, 3300, 3300));
        npcs.add(normalNPC);
	}

	public MobList<Player> getPlayers() {
		return players;
	}

	public MobList<NPC> getNpcs() {
		return npcs;
	}

    public GroundItemList getGroundItems() {
        return groundItems;
    }

    public GroundObjectList getGroundObjects() {
        return groundObjects;
    }

    public TraversalMap getTraversalMap() {
        return traversalMap;
    }

	public TaskScheduler getTaskScheduler() {
		return taskScheduler;
	}

	public void tick() {
		for (Player player : players) {
			GameSession session = player.getSession();
			if (session != null)
				session.processMessageQueue();
		}

		taskScheduler.tick();
		updater.tick();
	}

	public Player getPlayerByName(String username) {
		for (Player player : players) {
			if (player.getUsername().equals(username))
				return player;
		}

		return null;
	}

}