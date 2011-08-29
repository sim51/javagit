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
package com.logisima.javagit.cli.checkout;

import java.io.File;
import java.util.Scanner;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.object.OutputErrorOrWarn;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ExceptionMessageMap;

/**
 * Parser class to parse the output generated by &lt;git-checkout&gt; and return a <code>GitCheckoutResponse</code>
 * object.
 */
public class GitCheckoutParser extends Parser {

    private GitCheckoutResponse response;

    /**
     * String pattern for matching files with modified, deleted, added words in the output.
     */
    private enum Pattern {
        MODIFIED("^M\\s+\\w+"), DELETED("^D\\s+\\w+"), ADDED("^A\\s+\\w+");

        String pattern;

        private Pattern(String pattern) {
            this.pattern = pattern;
        }

        public boolean matches(String line) {
            return line.matches(pattern);
        }
    }

    /**
     * Constructor.
     */
    public GitCheckoutParser() {
        super();
        response = new GitCheckoutResponse();
    }

    @Override
    public void parseLine(String line) {
        if (line == null || line.length() == 0) {
            return;
        }
        ++numLinesParsed;
        if (!isErrorLine(line)) {
            parseSwitchedToBranchLine(line);
            parseFilesInfo(line);
        }
    }

    private boolean isErrorLine(String line) {
        if (line.startsWith("error") || line.startsWith("fatal")) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
            return true;
        }
        return false;
    }

    public void parseSwitchedToBranchLine(String line) {
        if (line.startsWith("Switched to branch")) {
            getSwitchedToBranch(line);
        }
        else if (line.startsWith("Switched to a new branch")) {
            getSwitchedToNewBranch(line);
        }
    }

    private void getSwitchedToBranch(String line) {
        String branchName = extractBranchName(line);
        Ref branch = Ref.createBranchRef(branchName);
        response.setBranch(branch);
    }

    private void getSwitchedToNewBranch(String line) {
        String newBranchName = extractBranchName(line);
        Ref newBranch = Ref.createBranchRef(newBranchName);
        response.setNewBranch(newBranch);
    }

    private String extractBranchName(String line) {
        int startIndex = line.indexOf("'");
        int endIndex = line.indexOf("'", startIndex + 1);
        return line.substring(startIndex + 1, endIndex);
    }

    private void parseFilesInfo(String line) {
        if (Pattern.MODIFIED.matches(line)) {
            File file = new File(extractFileName(line));
            response.getModifiedFiles().add(file);
            return;
        }
        if (Pattern.DELETED.matches(line)) {
            File file = new File(extractFileName(line));
            response.getAddedFiles().add(file);
            return;
        }
        if (Pattern.ADDED.matches(line)) {
            File file = new File(extractFileName(line));
            response.getAddedFiles().add(file);
        }
    }

    private String extractFileName(String line) {
        String filename = null;
        Scanner scanner = new Scanner(line);
        while (scanner.hasNext()) {
            filename = scanner.next();
        }
        return filename;
    }

    public void processExitCode(int code) {
    }

    public GitCheckoutResponse getResponse() throws JavaGitException {
        if (errors.size() > 0) {
            throw new JavaGitException(406000, ExceptionMessageMap.getMessage("406000")
                    + " - git checkout error message: { " + getError() + " }");
        }
        return response;
    }

}