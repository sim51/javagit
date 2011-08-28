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
package com.logisima.javagit.cli.status;

import java.io.File;
import java.util.Scanner;
import java.util.StringTokenizer;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.IParser;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitStatusParser implements IParser {

    private enum State {
        FILES_TO_COMMIT, NOT_UPDATED, UNTRACKED_FILES
    }

    /**
     * Patterns for matching lines for deleted files, modified files, new files and empty lines.
     */
    public static enum Patterns {
        DELETED("^#\\s+deleted:\\s+.*"), MODIFIED("^#\\s+modified:\\s+.*"), NEW_FILE("^#\\s+new file:\\s+.*"), EMPTY_HASH_LINE(
                "^#\\s*$"), RENAMED("^#\\s+renamed:\\s+.*");

        String pattern;

        Patterns(String pattern) {
            this.pattern = pattern;
        }

        public boolean matches(String line) {
            return line.matches(this.pattern);
        }
    }

    private State                 outputState;
    private int                   lineNum;
    private GitStatusResponseImpl response;
    private File                  inputFile = null;

    // The working directory for the command that was run.
    private String                workingDirectory;

    public GitStatusParser(String workingDirectory) {
        this.workingDirectory = workingDirectory;
        lineNum = 0;
        response = new GitStatusResponseImpl(workingDirectory);
    }

    public GitStatusParser(String workingDirectory, File in) {
        this.workingDirectory = workingDirectory;
        inputFile = in;
        lineNum = 0;
        response = new GitStatusResponseImpl(workingDirectory);
    }

    public void parseLine(String line) {
        // System.out.println(line);
        if (line == null || line.length() == 0) {
            return;
        }
        ++lineNum;
        if (isError(line)) {
            return;
        }
        if (lineNum == 1) {
            parseLineOne(line);
        }
        else {
            parseOtherLines(line);
        }
    }

    /*
     * Seems like a valid ( non-error ) line 1 always start with a '#' and contains the branch name.
     */
    private void parseLineOne(String line) {
        if (!line.startsWith("#")) {
            return;
        }
        String branch = getBranch(line);
        if (branch != null) {
            String branchName = getBranch(line);
            response.setBranch(Ref.createBranchRef(branchName));
        }
    }

    private void parseOtherLines(String line) {
        if (!(line.charAt(0) == '#')) {
            response.setStatusOutputComment(line);
            return;
        }
        if (line.contains("Changes to be committed")) {
            outputState = State.FILES_TO_COMMIT;
            return;
        }
        else if (line.contains("Changed but not updated")) {
            outputState = State.NOT_UPDATED;
            return;
        }
        else {
            if (line.contains("Untracked files")) {
                outputState = State.UNTRACKED_FILES;
                return;
            }
        }
        if (ignoreOutput(line)) {
            return;
        }
        if (Patterns.DELETED.matches(line)) {
            String deletedFile = getFilename(line);
            if ((inputFile != null) && (!deletedFile.matches(inputFile.getName())))
                return;
            addDeletedFile(deletedFile);
            return;
        }
        if (Patterns.MODIFIED.matches(line)) {
            String modifiedFile = getFilename(line);
            if ((inputFile != null) && (!modifiedFile.matches(inputFile.getName())))
                return;
            addModifiedFile(modifiedFile);
            return;
        }
        if (Patterns.NEW_FILE.matches(line)) {
            String newFile = getFilename(line);
            if ((inputFile != null) && (!newFile.matches(inputFile.getName())))
                return;
            addNewFile(newFile);
            return;
        }
        if (outputState == State.UNTRACKED_FILES) {
            String untrackedFile = getFilename(line);
            if ((inputFile != null) && (!untrackedFile.matches(inputFile.getName())))
                return;
            addUntrackedFile(untrackedFile);
        }
        if (Patterns.RENAMED.matches(line)) {
            String renamedFile = getFilename(line);
            if ((inputFile != null) && (!renamedFile.matches(inputFile.getName())))
                return;
            addRenamedFileToCommit(renamedFile);
        }
    }

    private boolean isError(String line) {
        if (line.startsWith("fatal") || line.startsWith("Error") || line.startsWith("error")) {
            response.setError(lineNum, line);
            return true;
        }
        return false;
    }

    private void addNewFile(String filename) {
        response.addToNewFilesToCommit(new File(workingDirectory + filename));
    }

    private void addDeletedFile(String filename) {
        File file = new File(workingDirectory + filename);
        switch (outputState) {
            case FILES_TO_COMMIT:
                response.addToDeletedFilesToCommit(file);
                break;

            case NOT_UPDATED:
                response.addToDeletedFilesNotUpdated(file);
                break;
        }
    }

    private void addModifiedFile(String filename) {
        File file = new File(workingDirectory + filename);
        switch (outputState) {
            case FILES_TO_COMMIT:
                response.addToModifiedFilesToCommit(file);
                break;

            case NOT_UPDATED:
                response.addToModifiedFilesNotUpdated(file);
                break;
        }
    }

    private void addRenamedFileToCommit(String renamedFile) {
        response.addToRenamedFilesToCommit(new File(workingDirectory + renamedFile));
    }

    private void addUntrackedFile(String filename) {
        response.addToUntrackedFiles(new File(workingDirectory + filename));
    }

    private String getBranch(String line) {
        StringTokenizer st = new StringTokenizer(line);
        String last = null;
        while (st.hasMoreTokens()) {
            last = st.nextToken();
        }
        return last;
    }

    //

    public String getFilename(String line) {
        String filename = null;
        Scanner scanner = new Scanner(line);
        while (scanner.hasNext()) {
            filename = scanner.next();
        }
        return filename;
    }

    private boolean ignoreOutput(String line) {
        if (line.contains("(use \"git reset")) {
            return true;
        }
        if (line.contains("(use \"git add ")) {
            return true;
        }
        if (line.contains("(use \"git add/rm")) {
            return true;
        }
        if (Patterns.EMPTY_HASH_LINE.matches(line)) {
            return true;
        }
        return false;
    }

    public void processExitCode(int code) {
    }

    public GitStatusResponse getResponse() throws JavaGitException {
        if (response.errorState()) {
            throw new JavaGitException(438000, ExceptionMessageMap.getMessage("438000")
                    + " - git status error message: { " + response.getErrorMessage() + " }");
        }
        return response;
    }
}
