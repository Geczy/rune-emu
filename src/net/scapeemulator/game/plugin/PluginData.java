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

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Hadyn Richard
 */
public final class PluginData {

    /**
     * The list of scripts for the plugin.
     */
    private List<String> scripts = new LinkedList<>();

    /**
     * The list of dependencies for the plugin.
     */
    private List<String> dependencies = new LinkedList<>();

    /**
     * Constructs a new {@link PluginData};
     */
    public PluginData() {}

    /**
     * Adds a script to the scripts list.
     * @param file The name of the script.
     */
    public void addScript(String file) {
        scripts.add(file);
    }

    /**
     * Adds a dependency to the dependency list.
     * @param dependency The name of the dependency.
     */
    public void addDependency(String dependency) {
        scripts.add(dependency);
    }

    /**
     * Gets the list of scripts.
     * @return The scripts.
     */
    public List<String> getScripts() {
        return scripts;
    }

    /**
     * Gets the list of plugin dependencies.
     * @return The plugin dependencies.
     */
    public List<String> getDependencies() {
        return dependencies;
    }
}
