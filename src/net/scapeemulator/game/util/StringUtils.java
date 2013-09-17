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

package net.scapeemulator.game.util;

/**
 * Created by Hadyn Richard
 */
public final class StringUtils {

    public static String toCamelCase(String str) {
        String[] strings = str.toLowerCase().split("_");
        for (int i = 0; i < strings.length; i++){
            strings[i] = capitalize(strings[i]);
        }
        return join(strings);
    }
    
    public static String capitalize(String str) {
        String capitalized = "";
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if(i == 0) {
                c = Character.toUpperCase(c);
            }
            capitalized += c;
        }
        return capitalized;
    }
    
    public static String join(String[] strs) {
        String joined = "";
        for(String str : strs) {
            joined += str;
        }
        return joined;
    }

    private StringUtils() {}
}
