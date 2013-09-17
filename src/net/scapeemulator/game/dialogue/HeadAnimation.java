package net.scapeemulator.game.dialogue;

/**
 * Written by Hadyn Richard
 */
public enum HeadAnimation {

    REALLY_SAD(9760), SAD(9765), DEPRESSED(9770), WORRIED(9775), SCARED(9780),
    MEAN_FACE(9785), YELLING(9790), ANGRY(9795), STERN(9800),
    CALM(9805), CALMLY_TALKING(9810), QUIZITIVE(9815), SNOBBY(9820), 
    SNOBBY_HEAD_MOVE(9825), CONFUSED(9830), DRUNK_HAPPY_TIRED(9835), 
    TALKING_ALOT(9845), HAPPY_TALKING(9850), BAD_ASS(9855), THINKING(9860), 
    COOL_YES(9864), LAUGH_EXCITED(9851), SECRELTY_TALKING(9838);
    
    private int animationId;

    HeadAnimation(int animationId) {
        this.animationId = animationId;
    }
    
    public int getAnimationId() {
        return animationId;
    }
}
