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
public enum ObjectType {

    /* Each of the types that have been identified */
    DIAGONAL_WALL(9), PROP(10), DIAGONAL_PROP(11),

    /* Each of the yet to be identified types, some arent really important */
    TYPE_0(0), TYPE_1(1), TYPE_2(2), TYPE_3(3), TYPE_4(4), TYPE_5(5), TYPE_6(6), TYPE_7(7), TYPE_8(8),
    TYPE_12(12), TYPE_13(13), TYPE_14(14), TYPE_15(15), TYPE_16(16), TYPE_17(17), TYPE_18(18), TYPE_19(19),
    TYPE_20(20), TYPE_21(21), TYPE_22(22);
    
    private final int id;

    ObjectType(int id) {
        this.id = id;
    }

    /**
     * Gets if the object type is apart of the wall group.
     */
    public boolean isWall() {
        return getObjectGroup() == ObjectGroup.WALL;
    }

    /**
     * Gets the type id.
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the object group this type belongs to.
     */
    public ObjectGroup getObjectGroup() {
        return ObjectGroup.forType(id);
    }

    /**
     * Gets the object type for the specified id.
     */
    public static ObjectType forId(int id) {
        for(ObjectType type : values()) {
            if(type.id == id) {
                return type;
            }
        }
        throw new RuntimeException("unknown object type for id: " + id);
    }
}
