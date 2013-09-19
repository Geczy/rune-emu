package net.scapeemulator.game.dialogue;

/**
 * Written by Hadyn Richard
 */
public enum DialogueOption {
    
    OPTION_1, OPTION_2, OPTION_3, OPTION_4, OPTION_5,
    OPTION_6, OPTION_7, OPTION_8, OPTION_9, CONTINUE;
    
    public static DialogueOption forId(int id) {
        switch(id) {
            case 1:
                return OPTION_1;
            case 2:
                return OPTION_2;
            case 3:
                return OPTION_3;
            case 4:
                return OPTION_4;
            case 5:
                return OPTION_5;
            case 6:
                return OPTION_6;
            case 7:
                return OPTION_7;
            case 8:
                return OPTION_8;
            case 9:
                return OPTION_9;
        }
        throw new IllegalStateException();
    }
}
