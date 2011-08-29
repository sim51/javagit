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
package com.logisima.javagit.cli.reset;

import java.io.File;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.object.OutputErrorOrWarn;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitResetParser extends Parser {

    /**
     * The index of the start of the short SHA1 in the HEAD record. Result of the --hard option
     */
    private final int        HEAD_RECORD_SHA1_START = 15;

    /**
     * The working directory path set for the command line. Used to generate the correct paths to the files needing
     * update.
     */
    private String           workingDirectoryPath;

    /**
     * The response object for a reset.
     */
    private GitResetResponse response;

    /**
     * Constructor for <code>GitResetParser</code>
     * 
     * @param workingDirectoryPath The working directory path set for the command line.
     */
    public GitResetParser(String workingDirectoryPath) {
        super();
        workingDirectoryPath = workingDirectoryPath;
    }

    public void parseLine(String line) {
        if (this.errors.size() != 0) {
            ++numLinesParsed;
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
            return;
        }

        if (line.startsWith("HEAD ")) {
            // A record indicating the new HEAD commit resulting from using the --hard option.
            int sha1End = line.indexOf(' ', HEAD_RECORD_SHA1_START);
            Ref sha1 = Ref.createSha1Ref(line.substring(HEAD_RECORD_SHA1_START, sha1End));
            response = new GitResetResponse(sha1, line.substring(sha1End + 1));
        }
        else if (numLinesParsed > 0 && response.getNewHeadSha1() != null) {
            // No line is expected after getting a HEAD record. Doing nothing for now. Must revisit.
        }
        else if (line.endsWith(": needs update")) {
            // A file needs update record.
            int lastColon = line.lastIndexOf(":");
            File f = new File(workingDirectoryPath + line.substring(0, lastColon));
            response.getFilesNeedingUpdate().add(f);
        }
        else if (numLinesParsed > 0) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
        else {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }

        ++numLinesParsed;
    }

    /**
     * Gets a <code>GitResetResponseImpl</code> object containing the information from the reset response text parsed by
     * this IParser instance.
     * 
     * @return The <code>GitResetResponseImpl</code> object containing the reset's response information.
     */
    public GitResetResponse getResponse() throws JavaGitException {
        if (this.errors.size() != 0) {
            throw new JavaGitException(432000, ExceptionMessageMap.getMessage("432000")
                    + "  The git-reset error message:  { " + this.getError() + " }");
        }
        return response;
    }

}
