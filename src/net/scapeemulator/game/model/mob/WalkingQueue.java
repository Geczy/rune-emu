package net.scapeemulator.game.model.mob;

import net.scapeemulator.game.model.Position;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;

public final class WalkingQueue {
    
    private final static int AMOUNT_POINTS = 100;

    private final Mob mob;
    private final Deque<Position> points = new ArrayDeque<>();
    private boolean runningQueue;
    private boolean minimapFlagReset = false;
    private final Deque<Position> recentPoints = new LinkedList<>();

    public WalkingQueue(Mob mob) {
        this.mob = mob;
    }

    public void reset() {
        points.clear();
        runningQueue = false;
        minimapFlagReset = true;
    }

    public void addPoint(Position position) {
        points.add(position);
    }

    public void addFirstStep(Position position) {
        points.clear();
        runningQueue = false;
        addStepImpl(position, mob.getPosition());
    }

    public void addStep(Position position) {
        addStepImpl(position, points.peekLast());
    }

    private void addStepImpl(Position position, Position last) {
        System.out.println((position == null) + " " + (last == null) + " " + points.size());
        int deltaX = position.getX() - last.getX();
        int deltaY = position.getY() - last.getY();

        int max = Math.max(Math.abs(deltaX), Math.abs(deltaY));
        
        /* Bug fix? */
        if(max < 1) {
            max = 1;
        }

        for (int i = 0; i < max; i++) {
            if (deltaX < 0) {
                deltaX++;
            } else if (deltaX > 0) {
                deltaX--;
            }

            if (deltaY < 0) {
                deltaY++;
            } else if (deltaY > 0) {
                deltaY--;
            }

            points.add(new Position(position.getX() - deltaX, position.getY() - deltaY, position.getHeight()));
        }
    }

    public void tick() {
        Position position = mob.getPosition();

        Direction firstDirection = Direction.NONE;
        Direction secondDirection = Direction.NONE;

        Position next = points.poll();
        
        if (next != null) {
            Direction direction = Direction.between(position, next);
            boolean traversable = Direction.isTraversable(position, direction, mob.getSize());
            if (traversable) {
                firstDirection = direction;
                addRecentPoint(position);           
                position = next;               
                    

                if (runningQueue || mob.isRunning()) {
                    next = points.poll();
                    if (next != null) {
                        direction = Direction.between(position, next);
                        traversable = Direction.isTraversable(position, direction, mob.getSize());
                        if (traversable) {
                            secondDirection = direction;
                            addRecentPoint(position);
                            position = next;                           
                        }
                    }
                }
            }

            if (!traversable) {
                reset();
            }
        }

        mob.setDirections(firstDirection, secondDirection);
        mob.setPosition(position);
    }
    
    public void addRecentPoint(Position position) {
        if(recentPoints.size() >= AMOUNT_POINTS) {
            recentPoints.poll();
        } 
        recentPoints.addLast(position);
    }
    
    public Deque<Position> getRecentPoints() {
        return recentPoints;
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public boolean isRunningQueue() {
        return runningQueue;
    }

    public void setRunningQueue(boolean runningQueue) {
        this.runningQueue = runningQueue;
    }

    public boolean isMinimapFlagReset() {
        return minimapFlagReset;
    }

    public void setMinimapFlagReset(boolean minimapFlagReset) {
        this.minimapFlagReset = minimapFlagReset;
    }
}
