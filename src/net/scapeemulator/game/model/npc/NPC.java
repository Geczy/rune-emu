package net.scapeemulator.game.model.npc;

import net.scapeemulator.cache.def.NPCDefinition;
import net.scapeemulator.game.model.Area;
import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.model.area.QuadArea;
import net.scapeemulator.game.model.definition.NPCDefinitions;
import net.scapeemulator.game.model.mob.Mob;

public abstract class NPC extends Mob {

    private int type;
    private Area bounds;
    private NPCDefinition definition;

    public NPC(int type) {
        this.type = type;    
        init();
    }
    
    private void init() {
        definition = NPCDefinitions.forId(type);
        size = definition.getSize();
    }

    public abstract void tick();

    public void setPosition(Position position) {
        /* Initialize the bounds for the NPC if they haven't been already */
        if (bounds == null) {
            bounds = new QuadArea(position, position);
        }
        super.setPosition(position);
    }

    public void setBounds(Area bounds) {
        this.bounds = bounds;
    }

    public Area getBounds() {
        return bounds;
    }

    public int getType() {
        return type;
    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
