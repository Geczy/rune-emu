package net.scapeemulator.game.task;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.mob.Mob;
import net.scapeemulator.game.task.Action;

/**
 * An @{link Action} which fires when a distance requirement is met.
 * @author Blake
 * @author Graham
 */
public abstract class DistancedAction<T extends Mob> extends Action<T> {

    /**
     * The position to distance check with.
     */
    protected final Position position;

    /**
     * The minimum distance before the action fires.
     */
    private final int distance;

    /**
     * The delay once the threshold is reached.
     */
    private final int delay;

    /**
     * A flag indicating if this action fires immediately after the threshold
     * is reached.
     */
    private final boolean immediate;

    /**
     * A flag indicating if the distance has been reached yet.
     */
    private boolean reached = false;

    /**
     * Creates a new DistancedAction.
     * @param delay The delay between executions once the distance threshold is
     * reached.
     * @param immediate Whether or not this action fires immediately after the
     * distance threshold is reached.
     * @param character The character.
     * @param position The position.
     * @param distance The distance.
     */
    public DistancedAction(int delay, boolean immediate, T character, Position position, int distance) {
        super(character, 1, true);
        this.position = position;
        this.distance = distance;
        this.delay = delay;
        this.immediate = immediate;
    }

    @Override
    public void execute() {
        if (reached) {
            // some actions (e.g. agility) will cause the player to move away again
            // so we don't check once the player got close enough once
            executeAction();
        } else  if (mob.getPosition().getDistance(position) <= distance) {
            reached = true;
            setDelay(delay);
            if (immediate) {
                executeAction();
            }
        }
    }

    /**
     * Executes the actual action. Called when the distance requirement is met.
     */
    public abstract void executeAction();

}