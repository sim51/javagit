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
import com.logisima.javagit.cli.IParser;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitResetParser implements IParser {

    // TODO (jhl388): Create test case for this class.
    // TODO (jhl388): Finish implementing the GitResetParser.

    // The index of the start of the short SHA1 in the HEAD record. Result of the --hard option
    private final int            HEAD_RECORD_SHA1_START = 15;

    /*
     * The working directory path set for the command line. Used to generate the correct paths to the files needing
     * update.
     */
    private String               workingDirectoryPath;

    // Holding onto the error message to make part of an exception
    private StringBuffer         errorMsg               = null;

    // Track the number of lines parsed.
    private int                  numLinesParsed         = 0;

    // The response object for a reset.
    private GitResetResponseImpl response;

    /**
     * Constructor for <code>GitResetParser</code>
     * 
     * @param workingDirectoryPath The working directory path set for the command line.
     */
    public GitResetParser(String workingDirectoryPath) {
        this.workingDirectoryPath = workingDirectoryPath;
    }

    public void parseLine(String line) {

        // TODO (jhl388): handle error messages in a better manner.

        if (null != errorMsg) {
            ++numLinesParsed;
            errorMsg.append(", line" + numLinesParsed + "=[" + line + "]");
            return;
        }

        if (line.startsWith("HEAD ")) {
            // A record indicating the new HEAD commit resulting from using the --hard option.
            int sha1End = line.indexOf(' ', HEAD_RECORD_SHA1_START);
            Ref sha1 = Ref.createSha1Ref(line.substring(HEAD_RECORD_SHA1_START, sha1End));
            response = new GitResetResponseImpl(sha1, line.substring(sha1End + 1));
        }
        else if (numLinesParsed > 0 && response.getNewHeadSha1() != null) {
            // No line is expected after getting a HEAD record. Doing nothing for now. Must revisit.

            // TODO (jhl388): Figure out what to do if a line is received after a HEAD record.
        }
        else if (line.endsWith(": needs update")) {
            // A file needs update record.
            int lastColon = line.lastIndexOf(":");
            File f = new File(workingDirectoryPath + line.substring(0, lastColon));
            response.addFileToFilesNeedingUpdateList(f);
        }
        else if (numLinesParsed > 0) {
            errorMsg = new StringBuffer();
            errorMsg.append("Unexpected results.  line" + (numLinesParsed + 1) + "=[" + line + "]");
        }
        else {
            errorMsg = new StringBuffer();
            errorMsg.append("line1=[" + line + "]");
        }

        ++numLinesParsed;
    }

    public void processExitCode(int code) {
    }

    /**
     * Gets a <code>GitResetResponseImpl</code> object containing the information from the reset response text parsed by
     * this IParser instance.
     * 
     * @return The <code>GitResetResponseImpl</code> object containing the reset's response information.
     */
    public GitResetResponseImpl getResponse() throws JavaGitException {
        if (null != errorMsg) {
            throw new JavaGitException(432000, ExceptionMessageMap.getMessage("432000")
                    + "  The git-reset error message:  { " + errorMsg.toString() + " }");
        }
        return response;
    }

    /**
     * Gets the number of lines of response text parsed by this IParser.
     * 
     * @return The number of lines of response text parsed by this IParser.
     */
    public int getNumLinesParsed() {
        return numLinesParsed;
    }
}
