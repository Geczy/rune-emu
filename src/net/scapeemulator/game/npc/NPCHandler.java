package net.scapeemulator.game.npc;

import net.scapeemulator.game.model.Option;
import net.scapeemulator.game.model.npc.NPC;
import net.scapeemulator.game.model.player.Player;
import net.scapeemulator.game.util.HandlerContext;

/**
 * Written by Hadyn Richard
 */
public abstract class NPCHandler {

    /**
     * The option that the NPC handler will be bound to.
     */
    private final Option option;

    /**
     * Constructs a new {@link NPCHandler};
     * @param option The option that the NPC handler will be bound to.
     */
    public NPCHandler(Option option) {
        this.option = option;
    }

    /**
     * Gets the option that this handler will be for.
     * @return The option.
     */
    public Option getOption() {
        return option;
    }

    public abstract void handle(Player player, NPC npc, String option, HandlerContext context);
}