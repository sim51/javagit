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
package com.logisima.javagit.cli;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * <code>IParser</code> is an interface for parsing a git command line output stream. See how implementations of
 * <code>IParser</code> are used in <code>ProcessUtilities</code>.
 * 
 * @see ProcessUtilities
 */
public interface IParser {

    /**
     * Parses a line of output from a git command line output stream.
     * 
     * @param line The line to parse.
     */
    public void parseLine(String line);

    /**
     * Takes the process exit code so it can provide the most information possible to the user about how the process
     * ends.
     * 
     * @param code The exit code of the process that was run.
     */
    public void processExitCode(int code);

    /**
     * Gets the response for the command for which the parser is implemented.
     * 
     * @return The response for the command for which the parser is implemented.
     * @throws JavaGitException Thrown when there are problems creating the response, if there was an error running the
     *         command, ....
     * @throws
     */
    public ICommandResponse getResponse() throws JavaGitException;

}
