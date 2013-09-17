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

package net.scapeemulator.game.model.player;

import net.scapeemulator.cache.def.VarbitDefinition;
import net.scapeemulator.game.model.definition.VarbitDefinitions;
import net.scapeemulator.game.msg.impl.ConfigMessage;

/**
 * Created by Hadyn Richard
 */
public final class StateSet {
    
    private final Player player;
    private final int[] stateValues = new int[2500];
    
    public StateSet(Player player) {
        this.player = player;
    }

    private void check(int id) {
        if(id < 0 || id >= stateValues.length) {
            throw new ArrayIndexOutOfBoundsException(id);
        }
    }

    public void setState(int id, int value) {
        check(id);
        stateValues[id] = value;
        player.send(new ConfigMessage(id, value));
    }
    
    public int getState(int id) {
        check(id);
        return stateValues[id];
    }

    public void setBitState(int id, boolean bool) {
        setBitState(id, bool ? 1 : 0);
    }

    public void setBitState(int id, int value) {
        VarbitDefinition definition = VarbitDefinitions.forId(id);

        int lowBit = definition.getLowBit();
        int highBit = definition.getHighBit();
        int mask = (1 << highBit - lowBit + 1) - 1;

        setState(definition.getState(), (value & mask) << definition.getLowBit() | getState(definition.getState()) & ~(mask << definition.getLowBit()));
    }
    
    public boolean isBitStateActive(int id) {
        return isBitStateActive(id, 0x1);
    }
    
    public boolean isBitStateActive(int id, int flag) {
        return (getBitState(id) & flag) != 0;
    }
    
    public int getBitState(int id) {
        VarbitDefinition definition = VarbitDefinitions.forId(id);

        int lowBit = definition.getLowBit();
        int highBit = definition.getHighBit();
        int mask = (1 << highBit - lowBit + 1) - 1;

        return getState(definition.getState()) >> definition.getLowBit() & mask;
    }
}
