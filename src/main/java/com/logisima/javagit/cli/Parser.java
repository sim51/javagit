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
 *  along with Javagit.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli;

import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.object.OutputErrorOrWarn;

/**
 * Abstract class for Git Parser.
 * 
 * @author bsimard
 * 
 */
public abstract class Parser {

    /**
     * Number of parsed lines
     */
    protected int                     numLinesParsed;
    /**
     * List of error by line from the output.
     */
    protected List<OutputErrorOrWarn> errors;

    /**
     * Abstract method to parse line by line the process output.
     * 
     * @param line
     */
    public abstract void parseLine(String line);

    /**
     * Abstract method to return the response.
     * 
     * @return
     * @throws JavaGitException
     */
    public abstract Response getResponse() throws JavaGitException;

    /**
     * Constructor.
     * 
     * @param numLinesParsed
     * @param errors
     */
    public Parser() {
        super();
        this.numLinesParsed = 0;
        this.errors = new ArrayList<OutputErrorOrWarn>();
    }

    /**
     * Retrieves all the errors in the error list and concatenate them together in one string.
     * 
     * @return
     */
    public String getError() {
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < errors.size(); i++) {
            buf.append("Line " + errors.get(i).getLine() + ". " + errors.get(i).getMessage());
            if (i != (errors.size() - 1)) {
                buf.append("\n");
            }
        }
        return buf.toString();
    }

    /**
     * Method to set the exist code of process into response object.
     * 
     * @param code
     * @throws JavaGitException
     */
    public void processExitCode(int code) throws JavaGitException {
        this.getResponse().processExitCode = code;
    }

}
