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
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.object.OutputErrorOrWarn;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitRmParser extends Parser {

    /**
     * The response.
     */
    private GitRmResponse response;

    @Override
    public void parseLine(String line) {
        if (this.errors.size() != 0) {
            ++numLinesParsed;
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
            return;
        }
        if (line.startsWith("rm '")) {
            int locQuote = line.indexOf('\'');
            int locLastQuote = line.lastIndexOf('\'');
            response.getRemovedFiles().add(new File(line.substring(locQuote + 1, locLastQuote)));
        }
        else {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
    }

    @Override
    public GitRmResponse getResponse() throws JavaGitException {
        if (this.errors.size() != 0) {
            throw new JavaGitException(434000, ExceptionMessageMap.getMessage("434000") + "  { " + this.getError()
                    + " }");
        }
        return response;
    }
}
