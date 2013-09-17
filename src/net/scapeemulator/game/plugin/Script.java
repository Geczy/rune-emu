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

package net.scapeemulator.game.plugin;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by Hadyn Richard
 */
public final class Script {

    /**
     * The name of the script.
     */
    private String name;

    /**
     * The source of the script.
     */
    private Reader source;

    /**
     * Constructs a new {@link Script};
     * @param file The file of the script to create.
     * @throws IOException An I/O exception was thrown while creating the script.
     */
    public Script(File file) throws IOException {
        this(file.getCanonicalPath(), new FileReader(file));
    }

    /**
     * Constructs a new {@link Script};
     * @param name      The name of the script.
     * @param source    The source of the script.
     */
    public Script(String name, Reader source) {
        this.name = name;
        this.source = source;
    }

    /**
     * Gets the source of the script.
     *
     * @return  The source.
     */
    public Reader getSource() {
        return source;
    }

    /**
     * Gets the name of the script.
     *
     * @return  The name of the script.
     */
    public String getName() {
        return name;
    }
}