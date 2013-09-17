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

package net.scapeemulator.game.model.grounditem;

import net.scapeemulator.game.model.definition.ItemDefinitions;
import net.scapeemulator.game.model.grounditem.GroundItemList.Type;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.World;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.msg.impl.PlacementCoordsMessage;
import net.scapeemulator.game.msg.impl.grounditem.GroundItemCreateMessage;
import net.scapeemulator.game.msg.impl.grounditem.GroundItemRemoveMessage;
import net.scapeemulator.game.model.grounditem.GroundItemList.GroundItem;
import net.scapeemulator.game.msg.impl.grounditem.GroundItemUpdateMessage;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Created by Hadyn Richard
 */
public final class GroundItemSynchronizer extends GroundItemListener {

    /**
     * The player to synchronize the ground items for.
     */
    private final Player player;

    /**
     * The ids of all the ground items that the synchronized has handled.
     */
    private final Set<Integer> uids = new HashSet<>();

    /**
     * The list of ground objects to remove once the player reaches a certain height.
     */
    private final List<GroundItem> toRemove = new LinkedList<>();

    /**
     * Constructs a new {@link GroundItemSynchronizer};
     * @param player The player to synchronize the ground items for.
     */
    public GroundItemSynchronizer(Player player) {
        this.player = player;
    }

    @Override
    public void groundItemCreated(GroundItem groundItem, Type type) {
        Position position = groundItem.getPosition();
        boolean withinScene = player.getLastKnownRegion().isWithinScene(position);
        boolean sameHeight = player.getPosition().getHeight() == position.getHeight();
        if (withinScene && sameHeight && !uids.contains(groundItem.getUid())) {
            uids.add(groundItem.getUid());
            sendPlacementCoords(position);
            if(ItemDefinitions.forId(groundItem.getItemId()).isStackable()) {
                GroundItemList list = getAlternateList(type);
                if(list.contains(groundItem.getItemId(), groundItem.getPosition())) {
                    int prevAmount = list.get(groundItem.getItemId(), groundItem.getPosition()).getAmount();
                    int newAmount = groundItem.getAmount() + prevAmount;
                    player.send(new GroundItemUpdateMessage(groundItem.getItemId(), newAmount, position, prevAmount));
                    return;
                }
            }
            player.send(new GroundItemCreateMessage(groundItem.getItemId(), groundItem.getAmount(), position));
            return;
        }

        /* Remove the ground item from the added list if its not longer in the scene */
        if (!withinScene && uids.contains(groundItem.getUid())) {
            uids.remove(groundItem.getUid());
        }
    }

    @Override
    public void groundItemUpdated(GroundItemList.GroundItem groundItem, int previousAmount, Type type) {
        Position position = groundItem.getPosition();
        if(uids.contains(groundItem.getUid()) && player.getPosition().isWithinScene(position)) {
            sendPlacementCoords(position);
            player.send(new GroundItemUpdateMessage(groundItem.getItemId(), groundItem.getAmount(), groundItem.getPosition(), previousAmount));
        }
    }

    @Override
    public void groundItemRemoved(GroundItem groundItem, Type type) {
        Position position = groundItem.getPosition();

        boolean sameHeight = player.getPosition().getHeight() == position.getHeight();
        uids.remove(groundItem.getUid());
        if (sameHeight && player.getPosition().isWithinScene(position)) {
            sendPlacementCoords(position);
            if(ItemDefinitions.forId(groundItem.getItemId()).isStackable()) {
                GroundItemList list = getAlternateList(type);
                if(list.contains(groundItem.getItemId(), groundItem.getPosition())) {
                    int prevAmount = list.get(groundItem.getItemId(), groundItem.getPosition()).getAmount();
                    int newAmount = groundItem.getAmount() + prevAmount;
                    player.send(new GroundItemUpdateMessage(groundItem.getItemId(), newAmount, position, prevAmount));
                    return;
                }
            }
            player.send(new GroundItemRemoveMessage(groundItem.getItemId(), groundItem.getPosition()));
        }


        if (!sameHeight && player.getPosition().isWithinScene(position)) {
            toRemove.add(groundItem);
        }
    }

    /**
     * Purges the to remove list.
     */
    public void purge() {
        if (!toRemove.isEmpty()) {
            for (GroundItem object : toRemove) {
                boolean sameHeight = object.getPosition().getHeight() == player.getPosition().getHeight();
                Position position = object.getPosition();
                if (sameHeight && player.getPosition().isWithinScene(position)) {
                    sendPlacementCoords(position);
                    player.send(new GroundItemRemoveMessage(object.getItemId(), position));
                }
            }
            toRemove.clear();
        }
    }

    private void sendPlacementCoords(Position position) {
        int localX = position.getLocalX(player.getLastKnownRegion().getCentralRegionX()) & 0xfff8;
        int localY = position.getLocalY(player.getLastKnownRegion().getCentralRegionY()) & 0xfff8;
        player.send(new PlacementCoordsMessage(localX, localY));
    }
    
    private GroundItemList getAlternateList(Type type) {
        switch(type) {
           case LOCAL:
               return World.getWorld().getGroundItems();
           case WORLD:
               return player.getGroundItems();
           default:
               throw new RuntimeException();
        }
    }
}
