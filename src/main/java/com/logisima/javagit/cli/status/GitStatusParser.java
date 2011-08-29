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
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.object.GitFileSystemObject.Status;
import com.logisima.javagit.object.OutputErrorOrWarn;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitStatusParser extends Parser {

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

    private State             outputState;

    /**
     * The response.
     */
    private GitStatusResponse response;

    private File              inputFile = null;

    /**
     * The working directory for the command that was run.
     */
    private String            workingDirectory;

    /**
     * Constructor.
     * 
     * @param workingDirectory
     */
    public GitStatusParser(String workingDirectory) {
        super();
        this.workingDirectory = workingDirectory;
        response = new GitStatusResponse(workingDirectory);
    }

    @Override
    public void parseLine(String line) {
        // System.out.println(line);
        if (line == null || line.length() == 0) {
            return;
        }
        ++numLinesParsed;
        if (isError(line)) {
            return;
        }
        if (numLinesParsed == 1) {
            parseLineOne(line);
        }
        else {
            parseOtherLines(line);
        }
    }

    /**
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
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
            return true;
        }
        return false;
    }

    private void addNewFile(String filename) {
        File file = new File(workingDirectory + filename);
        response.getNewFilesToCommit().add(file);
        response.fileToStatus.put(file, Status.NEW_TO_COMMIT);
    }

    private void addDeletedFile(String filename) {
        File file = new File(workingDirectory + filename);
        switch (outputState) {
            case FILES_TO_COMMIT:
                response.getDeletedFilesToCommit().add(file);
                response.fileToStatus.put(file, Status.DELETED_TO_COMMIT);
                break;

            case NOT_UPDATED:
                response.getDeletedFilesNotUpdated().add(file);
                response.fileToStatus.put(file, Status.DELETED);
                break;
        }
    }

    private void addModifiedFile(String filename) {
        File file = new File(workingDirectory + filename);
        switch (outputState) {
            case FILES_TO_COMMIT:
                response.getModifiedFilesToCommit().add(file);
                response.fileToStatus.put(file, Status.MODIFIED_TO_COMMIT);
                break;

            case NOT_UPDATED:
                response.getModifiedFilesNotUpdated().add(file);
                response.fileToStatus.put(file, Status.MODIFIED);
                break;
        }
    }

    private void addRenamedFileToCommit(String renamedFile) {
        File file = new File(workingDirectory + renamedFile);
        response.getRenamedFilesToCommit().add(file);
        response.fileToStatus.put(file, Status.RENAMED_TO_COMMIT);
    }

    private void addUntrackedFile(String filename) {
        File file = new File(workingDirectory + filename);
        response.getUntrackedFiles().add(file);
        response.fileToStatus.put(file, Status.UNTRACKED);
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

    public GitStatusResponse getResponse() throws JavaGitException {
        if (this.errors.size() != 0) {
            throw new JavaGitException(438000, ExceptionMessageMap.getMessage("438000")
                    + " - git status error message: { " + this.getError() + " }");
        }
        return response;
    }
}
