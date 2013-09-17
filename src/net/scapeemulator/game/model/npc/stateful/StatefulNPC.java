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

package net.scapeemulator.game.model.npc.stateful;

import net.scapeemulator.game.model.npc.NPC;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Hadyn Richard
 */
public abstract class StatefulNPC<E extends Enum> extends NPC {

    /**
     * Something something can't use an EnumMap because you cant specify the generic class type.
     */
    private Map<E, StateHandler> handlers = new HashMap<>();

    public StatefulNPC(int type) {
        super(type);
    }
    
    public void bindHandler(E state, StateHandler handler) {
        handlers.put(state, handler);
    }

    @Override
    public void tick() {
        StateHandler handler = handlers.get(determineState());
        if(handler != null) {
            handler.handle(this);
        }
    }

    public abstract E determineState();

}
