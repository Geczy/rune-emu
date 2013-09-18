package net.scapeemulator.game.dialogue;

/**
 * Written by Hadyn Richard
 */
public enum HeadAnimation {

    SAD(9760), SOBBING(9765), WORRIED(9770), SCARED(9775), FRIGHTENED(9780),
    STERN(9785), YELLING(9790), ANGRY(9795), DUNNO_0(9800),
    CALM(9805), CALMLY_TALKING(9810), QUIZITIVE(9815), DISBELIEF(9820), 
    DISAGREEMENT(9825), INTERESTED(9830), SINGING(9835), 
    PLEASED(9845), HAPPY(9850), ANNOYED(9855), DUNNO_1(9860), 
    POMPOUS(9864), DUNNO_2(9851), SHIFTY(9838);
    
    private int animationId;

    HeadAnimation(int animationId) {
        this.animationId = animationId;
    }
    
    public int getAnimationId() {
        return animationId;
    }
}
