/**
 *  This file is part of LogiSima (http://www.logisima.com).
 *
 *  JavGit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavGit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with LogiSima-Common.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.object;

/**
 * <code>MergeOpions</code> represents merge options
 * 
 */
public class MergeOptions {

    private final String message;
    private Strategy     strategy;

    public static enum Strategy {
        RESOLVE, RECURSIVE, OCTOPUS, OURS, SUBTREE
    }

    /**
     * The constructor.
     * 
     * @param s Merge strategy; see enum list
     */
    public MergeOptions(Strategy s) {
        this.strategy = s;
        // no message
        this.message = null;
    }

    /**
     * The constructor.
     * 
     * @param m Message for the commit
     * @param s Merge strategy; see enum list
     */
    public MergeOptions(Strategy s, String m) {
        this.strategy = s;
        this.message = m;
    }

    /**
     * Gets the commit message
     * 
     * @return The commit message
     */
    public String getMessage() {
        return message;
    }

    /**
     * Gets the merge strategy
     * 
     * @return The strategy for the merge (resolve, recursive, etc)
     */
    public Strategy getStrategy() {
        return strategy;
    }
}