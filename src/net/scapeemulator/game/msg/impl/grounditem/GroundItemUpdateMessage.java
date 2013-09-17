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

package net.scapeemulator.game.msg.impl.grounditem;

import net.scapeemulator.game.model.Position;
import net.scapeemulator.game.msg.Message;

/**
 * Created by Hadyn Richard
 */
public final class GroundItemUpdateMessage extends Message {
    
    private final int itemId;
    private final int newAmount;
    private final Position position;
    private final int previousAmount;
    
    public GroundItemUpdateMessage(int itemId, int newAmount, Position position, int previousAmount) {
        this.itemId = itemId;
        this.newAmount = newAmount;
        this.position = position;
        this.previousAmount = previousAmount;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public int getAmount() {
        return newAmount;
    }
    
    public Position getPosition() {
        return position;
    }
    
    public int getPreviousAmount() {
        return previousAmount;
    }
}
