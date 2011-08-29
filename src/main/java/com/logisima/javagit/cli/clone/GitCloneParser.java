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

import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;

/**
 * Implementation of the <code>IParser</code> interface in GitCloneParser class.
 */
public class GitCloneParser extends Parser {

    private int                  lineNum;
    private GitCloneResponseImpl response;
    private boolean              error = false;
    private List<Error>          errorList;

    public ICommandResponse getResponse() throws JavaGitException {
        return response;
    }

    public GitCloneParser() {
        lineNum = 0;
        response = new GitCloneResponseImpl();
    }

    public void parseLine(String line) {
        if (line == null || line.length() == 0) {
            return;
        }
        lineNum++;
        if (isError(line)) {
            error = true;
            errorList.add(new Error(lineNum, line));
        }
        else {
            // processLine(line);
        }
    }

    private boolean isError(String line) {
        if (line.trim().startsWith("warning") || line.trim().startsWith("error")) {
            if (errorList == null) {
                errorList = new ArrayList<Error>();
            }
            return true;
        }
        return false;
    }

    public void processExitCode(int code) {
    }

    /**
     * Class for storing error details from the &lt;git-add&gt; output.
     * 
     */
    private static class Error {

        final int    lineNum;
        final String errorStr;

        Error(int lineNum, String errorStr) {
            this.lineNum = lineNum;
            this.errorStr = errorStr;
        }

        public String getErrorString() {
            return errorStr;
        }
    }
}
