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

package net.scapeemulator.game.model;

/**
 * Created by Hadyn Richard
 */
public enum ExtendedOption {

    /* Each of the extended options */
    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE;

    ExtendedOption() {}

    /**
     * Converts the option to an integer.
     * @return The converted integer.
     */
    public int toInteger() {
        switch(this) {
            case ONE:
                return 0;
            case TWO:
                return 1;
            case THREE:
                return 2;
            case FOUR:
                return 3;
            case FIVE:
                return 4;
            case SIX:
                return 5;
            case SEVEN:
                return 6;
            case EIGHT:
                return 7;
            case NINE:
                return 8;
            default:
                throw new RuntimeException();
        }
    }
}
