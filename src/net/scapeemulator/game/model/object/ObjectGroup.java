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

package net.scapeemulator.game.model.object;

/**
 * Created by Hadyn Richard
 */
public enum ObjectGroup {

    /**
     * Enumation for each group type.
     */
    WALL(0), WALL_DECORATION(1), GROUP_2(2), GROUP_3(3);

    /**
     * The array of object group ids for their type.
     */
    public static final int[] OBJECT_GROUPS = new int[] { 0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2,
                                                          2, 2, 2, 2, 2, 2, 2, 3 };

    /**
     * The group id that the type belongs to.
     */
    private final int id;
    
    ObjectGroup(int id) {
        this.id = id;
    }
    
    public static ObjectGroup forType(int type) {
        int id = OBJECT_GROUPS[type];
        for(ObjectGroup group : values()) {
            if(group.id == id) {
                return group;
            }
        }
        throw new RuntimeException();
    }
}