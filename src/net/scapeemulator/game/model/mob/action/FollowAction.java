package net.scapeemulator.game.model.mob.action;

import java.util.Deque;
import java.util.Iterator;
import java.util.List;

import net.scapeemulator.game.model.Area;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.area.QuadArea;
import net.scapeemulator.game.model.mob.Direction;
import net.scapeemulator.game.model.mob.Mob;
import net.scapeemulator.game.model.pathfinding.AStarPathFinder;
import net.scapeemulator.game.model.pathfinding.Path;
import net.scapeemulator.game.model.pathfinding.PathFinder;
import net.scapeemulator.game.task.Action;

/**
 * Written by Hadyn Richard
 */
public final class FollowAction extends Action<Mob> {
    
    private final Mob target;
    
    private State state = State.INIT;
    private Position lastAddedPosition;
    private Path path = new Path();
    
    public enum State {
        INIT, COPY_TARGET_QUEUE
    }
    
    public FollowAction(Mob mob, Mob target) {
        super(mob, 1, true);
        this.target = target;
    }

    @Override
    public void execute() {
        
        /* Stop the action if the target is teleporting */
        if(target.isTeleporting()) {
            stop();
            return;
        }
        
        Deque<Position> recentPoints = target.getWalkingQueue().getRecentPoints();
        
        if(state == State.INIT) {
            
            mob.getWalkingQueue().reset();
            
            Position position = target.getPosition();
            if(!recentPoints.isEmpty()) {
                position = recentPoints.peekLast();
            }
                  
            if(!mob.getPosition().equals(position)) {
            
                /* Find the path to the position */
                PathFinder pathFinder = new AStarPathFinder();
                path = pathFinder.find(mob, position.getX(), position.getY());
                lastAddedPosition = position;
            }
            
            mob.turnToTarget(target);
                
            state = State.COPY_TARGET_QUEUE;
        }
        
        if(state == State.COPY_TARGET_QUEUE) {          
            
            if(!recentPoints.isEmpty()) {
                Iterator<Position> iterator = recentPoints.descendingIterator();
                while(iterator.hasNext()) {
                    Position position = iterator.next();
                    if(lastAddedPosition != null && position.equals(lastAddedPosition)) {
                       break; 
                    }
                    path.addLast(position);
                }
                lastAddedPosition = recentPoints.peekLast();
            /* If the target hasnt recently walked, and they are at the same point */
            } else if(mob.getPosition().equals(target.getPosition())) {
                List<Position> nearbyTiles = Direction.getNearbyTraversableTiles(mob.getPosition(), mob.getSize());
                if(!nearbyTiles.isEmpty()) {
                    path.addLast(nearbyTiles.get(0));
                }
            }                 
        }
        
        Position position = path.peek();
            
        if(position == null || !isValid(position)) {
            return;
        }

        /* Add the path point to the walking queue */
        mob.getWalkingQueue().addPoint(position);

        /* Remove the point from the path */
        path.poll();
              
    }
    
    /**
     * Gets if the mob is near to the target so that steps do not need to be appended.
     */
    private boolean isValid(Position position) {
        int targetX = target.getPosition().getX();
        int targetY = target.getPosition().getY();
        
        /* The inclusive area is the area that the mob has to be within to be near the target */
        Area area = new QuadArea(targetX - mob.getSize() + 1, targetY - mob.getSize() + 1, 
                                 targetX + target.getSize() - 1, targetY + target.getSize() - 1);
        
        if(area.withinArea(position, mob.getSize())) {
            return false;
        }
        
        return true;
    }
}
