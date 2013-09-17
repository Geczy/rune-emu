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

package net.scapeemulator.game.msg.impl.item;

import net.scapeemulator.game.msg.Message;

/**
 * Created by Hadyn Richard
 */
public class ItemOnObjectMessage extends Message {
    
    private final int itemId, slot, widgetHash, objectId, x, y;

    public ItemOnObjectMessage(int itemId, int slot, int widgetHash, int objectId, int x, int y) {
        this.itemId = itemId;
        this.slot = slot;
        this.widgetHash = widgetHash;
        this.objectId = objectId;
        this.x = x;
        this.y = y;
    }
    
    public int getItemId() {
        return itemId;
    }
    
    public int getSlot() {
        return slot;
    }
    
    public int getWidgetHash() {
        return widgetHash;
    }
    
    public int getObjectId() {
        return objectId;
    }
    
    public int getX() {
        return x;
    }
    
    public int getY() {
        return y;
    }
}
