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
package com.logisima.javagit.cli.mv;

import java.io.File;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.object.OutputErrorOrWarn;
import com.logisima.javagit.utilities.ExceptionMessageMap;

/**
 * Implementation of the <code>IParser</code> interface in GitMvParser class.
 */
public class GitMvParser extends Parser {

    /**
     * The response object for an mv operation.
     */
    private GitMvResponse response;

    public GitMvParser() {
        super();
        response = new GitMvResponse();
    }

    /**
     * Parses the line from the git-mv response text.
     * 
     * @param line The line of text to process.
     * 
     */
    public void parseLine(String line) {
        ++numLinesParsed;
        if (this.errors.size() != 0) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
            return;
        }
        if (line.startsWith("error") || line.startsWith("fatal")) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
        else {
            parseLineForSuccess(line);
        }
    }

    /**
     * Parses the line for successful execution.
     * 
     * @param line The line of text to process.
     */
    public void parseLineForSuccess(String line) {
        if (line.contains("Warning:")) {
            response.getMessage().concat(line);
        }
        if (line.contains("Adding") || line.contains("Changed")) {
            response.setDestination(new File(line.substring(11)));
        }
        if (line.contains("Deleting")) {
            response.setSource(new File(line.substring(11)));
        }
    }

    /**
     * Gets a <code>GitMvResponse</code> object containing the information from the git-mv response text parsed by this
     * IParser instance. It is expected that GitMv does not notify when a move was successful. This follows the response
     * that git-mv itself gives. If the move/rename fails for any reason, proper exception messages are generated and
     * thrown.
     * 
     * @return The <code>GitMvResponse</code> object containing the git-mv's response information. It is expected that
     *         GitMv does not notify when a move was successful. This follows the response that git-mv itself gives. If
     *         the move/rename fails for any reason, proper exception messages are generated and thrown.
     * @throws <code>JavaGitException</code> if there is an error executing git-mv.
     */
    public GitMvResponse getResponse() throws JavaGitException {
        if (this.errors.size() != 0) {
            throw new JavaGitException(424000, ExceptionMessageMap.getMessage("424000")
                    + "  The git-mv error message:  { " + this.getError() + " }");
        }
        return response;
    }
}
