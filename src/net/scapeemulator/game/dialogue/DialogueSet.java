package net.scapeemulator.game.dialogue;

import java.util.HashMap;
import java.util.Map;

/**
 * Written by Hadyn Richard
 */
public final class DialogueSet {

    private static final Map<String, Dialogue> dialogues = new HashMap<>();
    
    public static void add(String name, Dialogue dialogue) {
        dialogues.put(name, dialogue);
    }
    
    public static Dialogue get(String name) {
        return dialogues.get(name);
    }
}
