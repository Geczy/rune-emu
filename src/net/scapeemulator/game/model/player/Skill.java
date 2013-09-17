package net.scapeemulator.game.model.player;

public final class Skill {

    public static final String[] SKILL_NAMES = new String[]{
        "attack", "defence", "strength", "hitpoints", "ranged", "prayer",
        "magic", "cooking", "woodcutting", "fletching", "fishing", "firemaking",
        "crafting", "smithing", "mining", "herblore", "agility", "thieving",
        "slayer", "farming", "runecrafting", "hunter", "construction", "summoning"
    };
    public static final int[] FLASHING_ICONS = new int[]{
        4732, 4734, 4733, 4738, 4735, 4736, 4737, 4747, 4749, 4743, 4746, 4748, 4742,
        4745, 4744, 4740, 4739, 4741, 4751, 4752, 4750, 4754, 4753, 4755
    };
    public static final int[] CONFIGS = new int[]{
        1, 5, 2, 6, 3, 7, 4, 16, 18, 19, 15, 17, 11, 14, 13, 9,
        8, 10, 20, 21, 12, 23, 22, 24
    };
    public static final int ATTACK = 0;
    public static final int DEFENCE = 1;
    public static final int STRENGTH = 2;
    public static final int HITPOINTS = 3;
    public static final int RANGED = 4;
    public static final int PRAYER = 5;
    public static final int MAGIC = 6;
    public static final int COOKING = 7;
    public static final int WOODCUTTING = 8;
    public static final int FLETCHING = 9;
    public static final int FISHING = 10;
    public static final int FIREMAKING = 11;
    public static final int CRAFTING = 12;
    public static final int SMITHING = 13;
    public static final int MINING = 14;
    public static final int HERBLORE = 15;
    public static final int AGILITY = 16;
    public static final int THIEVING = 17;
    public static final int SLAYER = 18;
    public static final int FARMING = 19;
    public static final int RUNECRAFTING = 20;
    public static final int HUNTER = 21;
    public static final int CONSTRUCTION = 22;
    public static final int SUMMONING = 23;
    public static final int AMOUNT_SKILLS = 24;

    public static int getFlashingIcon(int id) {
        return FLASHING_ICONS[id];
    }

    public static int getConfigValue(int id) {
        return CONFIGS[id];
    }

    public static String getName(int id) {
        return SKILL_NAMES[id];
    }

    public static boolean isCombatSkill(int id) {
        switch (id) {
            case ATTACK:
            case DEFENCE:
            case STRENGTH:
            case HITPOINTS:
            case RANGED:
            case PRAYER:
            case MAGIC:
            case SUMMONING:
                return true;
        }

        return false;
    }

    private Skill() {
        /* empty */
    }
}
