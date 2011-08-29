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
package com.logisima.javagit.cli.clone;

import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.object.OutputErrorOrWarn;

/**
 * Implementation of the <code>IParser</code> interface in GitCloneParser class.
 */
public class GitCloneParser extends Parser {

    private GitCloneResponse response;

    /**
     * Constructor.
     */
    public GitCloneParser() {
        super();
        response = new GitCloneResponse();
    }

    @Override
    public GitCloneResponse getResponse() {
        return response;
    }

    @Override
    public void parseLine(String line) {
        if (line == null || line.length() == 0) {
            return;
        }
        numLinesParsed++;
        if (isError(line)) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
        else {
            // processLine(line);
        }
    }

    private boolean isError(String line) {
        if (line.trim().startsWith("warning") || line.trim().startsWith("error")) {
            return true;
        }
        return false;
    }

}
