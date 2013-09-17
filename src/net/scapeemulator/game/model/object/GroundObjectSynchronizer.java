/**
 * Copyright (c) 2012, Hadyn Richard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy 
 * of this software and associated documentation files (the "Software"), to deal 
 * in the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 */

package net.scapeemulator.game.model.object;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.object.GroundObjectList.GroundObject;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlacementCoordsMessage;
import net.scapeemulator.game.msg.impl.grounditem.GroundObjectRemoveMessage;
import net.scapeemulator.game.msg.impl.grounditem.GroundObjectUpdateMessage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Hadyn Richard
 */
public final class GroundObjectSynchronizer extends GroundObjectListenerAdapter {

    /**
     * The player to synchronize the object list for.
     */
    private final Player player;

    /**
     * The set of unique ids for objects that are within the players scene.
     */
    private final Set<Integer> uids = new HashSet<>();

    /**
     * The list of ground objects to remove once the player reaches a certain height.
     */
    private final List<GroundObject> toRemove = new LinkedList<>();

    public GroundObjectSynchronizer(Player player) {
        this.player = player;
    }

    @Override
    public void groundObjectAdded(GroundObject object) {
        Position position = object.getPosition();
        boolean withinScene = player.getLastKnownRegion().isWithinScene(position);
        boolean sameHeight = player.getPosition().getHeight() == position.getHeight();
        if (withinScene && sameHeight && !uids.contains(object.getUid())) {
            uids.add(object.getUid());
            sendPlacementCoords(position);
            player.send(new GroundObjectUpdateMessage(object.getPosition(), object.getId(), object.getType().getId(), object.getRotation()));
            return;
        }

        if (!withinScene && uids.contains(object.getUid())) {
            uids.remove(object.getUid());
        }
    }

    @Override
    public void groundObjectUpdated(GroundObject object) {
        Position position = object.getPosition();
        boolean sameHeight = player.getPosition().getHeight() == position.getHeight();
        if (sameHeight && player.getLastKnownRegion().isWithinScene(position)) {
            uids.add(object.getUid());
            sendPlacementCoords(position);
            player.send(new GroundObjectUpdateMessage(object.getPosition(), object.getId(), object.getType().getId(), object.getRotation()));
            return;
        }
    }


    @Override
    public void groundObjectAnimated(GroundObject object, int animationId) {
        //TODO
    }

    @Override
    public void groundObjectRemoved(GroundObject object) {
        Position position = object.getPosition();

        boolean sameHeight = player.getPosition().getHeight() == position.getHeight();
        uids.remove(object.getUid());
        if (sameHeight && player.getPosition().isWithinScene(position)) {
            sendPlacementCoords(position);
            player.send(new GroundObjectRemoveMessage(position, object.getType().getId(), object.getRotation()));
            return;
        }

        if (!sameHeight && player.getPosition().isWithinScene(position)) {
            toRemove.add(object);
        }
    }

    /**
     * Purges the to remove list.
     */
    public void purge() {
        if (!toRemove.isEmpty()) {
            for (GroundObject object : toRemove) {
                boolean sameHeight = object.getPosition().getHeight() == player.getPosition().getHeight();
                Position position = object.getPosition();
                if (sameHeight && player.getPosition().isWithinScene(position)) {
                    sendPlacementCoords(position);
                    player.send(new GroundObjectRemoveMessage(position, object.getType().getId(), object.getRotation()));
                }
            }
            toRemove.clear();
        }
    }

    /**
     * Sends a set of placement coords.
     */
    private void sendPlacementCoords(Position position) {
        int localX = position.getLocalX(player.getLastKnownRegion().getCentralRegionX()) & 0xf8;
        int localY = position.getLocalY(player.getLastKnownRegion().getCentralRegionY()) & 0xf8;
        player.send(new PlacementCoordsMessage(localX, localY));
    }
}
