package net.scapeemulator.game.task;

import net.scapeemulator.game.model.mob.Mob;
import net.scapeemulator.game.model.mob.action.FollowAction;

/**
 * Written by Hadyn Richard
 */
public abstract class MobInteractionAction<T extends Mob> extends Action<T> {
    
    private FollowAction followAction;
    private int distance;
    private boolean reached;
    protected Mob target;
    
    public MobInteractionAction(T mob, Mob target, int distance) {
        super(mob, 1, true);
        followAction = new FollowAction(mob, target);
        this.distance = distance;
        this.target = target;
        this.distance = distance;
        init();
    }
    
    private void init() {     
        mob.turnToTarget(target);
    }

    @Override
    public void execute() {
        if(!reached) {
            followAction.execute();
        }
        
        if(mob.getPosition().getDistance(target.getPosition()) <= distance) {
            executeAction(); // TODO: Delay?
            reached = true;
        }
    }
    
    public abstract void executeAction();
}
