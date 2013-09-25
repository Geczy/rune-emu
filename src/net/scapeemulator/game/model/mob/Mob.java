package net.scapeemulator.game.model.mob;

import net.scapeemulator.game.model.*;
import net.scapeemulator.game.model.SpotAnimation;
import net.scapeemulator.game.model.pathfinding.Path;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.model.player.SkillSet;
import net.scapeemulator.game.task.Action;

public abstract class Mob extends Entity {

    protected int id;
    protected boolean teleporting;
    protected final WalkingQueue walkingQueue = new WalkingQueue(this);
    protected Direction firstDirection = Direction.NONE;
    protected Direction secondDirection = Direction.NONE;
    protected Direction mostRecentDirection = Direction.SOUTH;
    protected final SkillSet skillSet = new SkillSet();
    protected int size;
    protected Animation animation;
    protected SpotAnimation spotAnimation;
    protected Position turnToPosition;
    protected Mob currentTarget;
    protected int turnToTargetId = -1;
    protected Action<?> action;

    public Mob() {
        size = 1;
    }

    public int getId() {
        return id;
    }

    public void resetId() {
        this.id = 0;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public boolean isActive() {
        return id != 0;
    }

    public void startAction(Action<?> action) {
        if (this.action != null) {
            if (this.action.equals(action)) {
                return;
            }

            stopAction();
        }
        this.action = action;
        World.getWorld().getTaskScheduler().schedule(action);
    }

    public void stopAction() {
        if (action != null) {
            Action<?> oldAction = action;
            action = null;
            oldAction.stop();
        }
    }

    public boolean isTeleporting() {
        return teleporting;
    }

    public void teleport(Position position) {
        this.position = position;
        this.teleporting = true;
        this.walkingQueue.reset();
    }

    public WalkingQueue getWalkingQueue() {
        return walkingQueue;
    }

    public Direction getFirstDirection() {
        return firstDirection;
    }

    public Direction getSecondDirection() {
        return secondDirection;
    }

    public boolean notWalking() {
    	return firstDirection == Direction.NONE && secondDirection == Direction.NONE; 
    }
    public Direction getMostRecentDirection() {
        return mostRecentDirection;
    }

    public void setDirections(Direction firstDirection, Direction secondDirection) {
        this.firstDirection = firstDirection;
        this.secondDirection = secondDirection;

        if (secondDirection != Direction.NONE) {
            mostRecentDirection = secondDirection;
        } else if (firstDirection != Direction.NONE) {
            mostRecentDirection = firstDirection;
        }
    }

    public SkillSet getSkillSet() {
        return skillSet;
    }

    public Animation getAnimation() {
        return animation;
    }

    public SpotAnimation getSpotAnimation() {
        return spotAnimation;
    }

    public boolean isAnimationUpdated() {
        return animation != null;
    }

    public boolean isSpotAnimationUpdated() {
        return spotAnimation != null;
    }

    public void playAnimation(Animation animation) {
        this.animation = animation;
    }

    public void playSpotAnimation(SpotAnimation spotAnimation) {
        this.spotAnimation = spotAnimation;
    }

    public void turnToPosition(Position turnToPosition) {
        this.turnToPosition = turnToPosition;
    }

    public boolean isTurnToPositionUpdated() {
        return turnToPosition != null;
    }

    public Position getTurnToPosition() {
        return turnToPosition;
    }

    public void turnToTarget(Mob target) {
        turnToTargetId = getTargetId(target);
        currentTarget = target;
    }

    public Mob getTurnToTarget() {
        return currentTarget;
    }

    public void resetTurnToTarget() {
        turnToTargetId = 65535;
        currentTarget = null;
    }

    public boolean isTurnToTargetSet() {
        return currentTarget != null;
    }

    public boolean isTurnToTargetUpdated() {
        return turnToTargetId != -1;
    }

    public int getTurnToTargetId() {
        return turnToTargetId;
    }

    public void walkPath(Path path) {
        if (!path.isEmpty()) {
            walkingQueue.reset();
            for (Position point : path.getPoints()) {
                walkingQueue.addPoint(point);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (!(object instanceof Mob)) {
            return false;
        }

        Mob otherMob = (Mob) object;
        return getTargetId(otherMob) == getTargetId(this);
    }

    public void reset() {
        animation = null;
        spotAnimation = null;
        turnToPosition = null;
        teleporting = false;
        turnToTargetId = -1;
        walkingQueue.setMinimapFlagReset(false);
    }

    private static int getTargetId(Mob mob) {
        int val = mob.getId();
        if (mob instanceof Player) {
            val += 0x8000;
        }
        return val;
    }

    public abstract boolean isRunning();
}
