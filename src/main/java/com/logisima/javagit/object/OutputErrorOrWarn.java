/**
 *  This file is part of LogiSima (http://www.logisima.com).
 *
 *  JavaGit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavaGit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with JavaGit.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.object;

/**
 * Class for storing error details from the &lt;git-add&gt; output.
 */
public class OutputErrorOrWarn {

    private int    line;
    private String message;

    /**
     * Constructor.
     * 
     * @param lineNum line number of the output git response.
     * @param errorStr
     */
    public OutputErrorOrWarn(int line, String message) {
        this.line = line;
        this.message = message;
    }

    /**
     * Getter for line.
     * 
     * @return
     */
    public int getLine() {
        return line;
    }

    /**
     * Getter for message.
     * 
     * @return
     */
    public String getMessage() {
        return message;
    }

}
