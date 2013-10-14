package net.scapeemulator.game.dialogue;

import java.util.HashMap;
import java.util.Map;

import net.scapeemulator.game.model.player.InterfaceSet.Component;
import net.scapeemulator.game.model.player.Player;

/**
 * Written by Hadyn Richard
 */
public final class Dialogue {
    
    /**
     * The name constant for the starting dialogue stage.
     */
    public static final String START_STAGE = "start";
    
    private final Map<String, Stage> stages = new HashMap<>();
    
    public Dialogue() {}
    
    public void addStage(String name, Stage stage) {
        stages.put(name, stage);
    }
    
    public void setStartingStage(Stage stage) {
        addStage(START_STAGE, stage);
    }
    
    public DialogueContext displayTo(Player player) {
        DialogueContext context = new DialogueContext(player, this);
        Component chatbox = player.getInterfaceSet().getChatbox();
        chatbox.setListener(new DialogueContextListener(context));
        return context;
    }
    
    public Stage getStage(String name) {
        return stages.get(name);
    }
}
