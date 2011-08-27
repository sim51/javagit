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
package com.logisima.javagit.cli.rm;

import java.io.File;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.IParser;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitRmParser implements IParser {

    // Holding onto the error message to make part of an exception
    private StringBuffer      errorMsg       = null;

    // Track the number of lines parsed.
    private int               numLinesParsed = 0;

    // The response.
    private GitRmResponseImpl response       = new GitRmResponseImpl();

    public void parseLine(String line) {

        // TODO (jhl388): handle error messages in a better manner.

        if (null != errorMsg) {
            ++numLinesParsed;
            errorMsg.append(", line" + numLinesParsed + "=[" + line + "]");
            return;
        }

        if (line.startsWith("rm '")) {
            int locQuote = line.indexOf('\'');
            int locLastQuote = line.lastIndexOf('\'');
            response.addFileToRemovedFilesList(new File(line.substring(locQuote + 1, locLastQuote)));
        }
        else {
            errorMsg = new StringBuffer();
            errorMsg.append("line1=[" + line + "]");
        }
    }

    public void processExitCode(int code) {
    }

    public GitRmResponse getResponse() throws JavaGitException {
        if (null != errorMsg) {
            throw new JavaGitException(434000, ExceptionMessageMap.getMessage("434000") + "  { " + errorMsg.toString()
                    + " }");
        }
        return response;
    }
}
