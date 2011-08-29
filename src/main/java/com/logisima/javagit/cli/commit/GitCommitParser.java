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
package com.logisima.javagit.cli.commit;

import java.io.File;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.cli.commit.GitCommitResponse.AddedOrDeletedFile;
import com.logisima.javagit.cli.commit.GitCommitResponse.CopiedOrMovedFile;
import com.logisima.javagit.object.OutputErrorOrWarn;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class GitCommitParser extends Parser {

    /**
     * The response object for a commit.
     */
    private GitCommitResponse response;

    // The working directory for the command that was run.
    private String            workingDirectory;

    public GitCommitParser(String workingDirectory) {
        super();
        this.response = new GitCommitResponse();
        this.workingDirectory = workingDirectory;
    }

    @Override
    public void parseLine(String line) {

        switch (numLinesParsed) {
            case 0:
                parseLineOne(line);
                break;
            case 1:
                parseLineTwo(line);
                break;
            default:
                parseAllOtherLines(line);
        }
        ++numLinesParsed;
    }

    /**
     * Parses the first line of the commit response text.
     * 
     * @param line The line of text to process.
     */
    private void parseLineOne(String line) {
        String shortHash;
        String shortComment;
        if (line.startsWith("[")) {
            String[] split = line.split(" ");
            // is it a root-commit
            if (split[1].startsWith("(") && split[1].endsWith(")")) {
                shortHash = split[2].substring(0, split[2].length() - 1);
            }
            else {
                shortHash = split[1].substring(0, split[1].length() - 1);
            }
            int locEndBrace = line.indexOf(']');
            shortComment = line.substring(locEndBrace + 2);
            response = new GitCommitResponse(Ref.createSha1Ref(shortHash), shortComment);
        }
        else {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
    }

    /**
     * Parse the second line of the commit response text.
     * 
     * @param line The line of text to process.
     */
    private void parseLineTwo(String line) {
        int nbChanged = 0;
        int nbInsertion = 0;
        int nbDeletion = 0;

        if (!line.startsWith("#")) {
            String[] split = line.split(",");
            nbChanged = Integer.valueOf(split[0].split(" ")[1]);
            nbInsertion = Integer.valueOf(split[1].split(" ")[1]);
            nbDeletion = Integer.valueOf(split[2].split(" ")[1]);
            response.filesChanged = nbChanged;
            response.linesInserted = nbInsertion;
            response.linesDeleted = nbDeletion;
        }
        else {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
    }

    /**
     * Parses the rest of the lines in the response from a successful commit.
     * 
     * @param line The line to parse.
     */
    private void parseAllOtherLines(String line) {
        if (line.startsWith(" create")) {
            parseAddDeleteLine(line, true);
        }
        else if (line.startsWith(" copy")) {
            parseCopyRenameLine(line, true);
        }
        else if (line.startsWith(" delete")) {
            parseAddDeleteLine(line, false);
        }
        else if (line.startsWith(" rename")) {
            parseCopyRenameLine(line, false);
        }
        else if (line.startsWith("#")) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, line));
        }
    }

    /**
     * Parses an add or delete line. The formats of the lines it parses are (without quotes):
     * <ul>
     * <li>
     * 
     * <pre>
     * &quot; create mode 100644 a/file/name.txt&quot;
     * </pre>
     * 
     * </li>
     * <li>
     * 
     * <pre>
     * &quot; delete mode 100644 a/file/name.txt&quot;
     * </pre>
     * 
     * </li>
     * </ul>
     * 
     * @param line The line of text to parse.
     * @param isAdd True if parsing an add (create) line, false if parsing a delete line.
     */
    private void parseAddDeleteLine(String line, boolean isAdd) {
        final int modeOffset = 13;
        final int endModeOffset = 19;
        final int startPathOffset = 20;
        String mode = line.substring(modeOffset, endModeOffset);
        String pathStr = line.substring(startPathOffset);
        File path = new File(workingDirectory + pathStr);
        if (isAdd) {
            response.getAddedFiles().add(new AddedOrDeletedFile(path, mode));
        }
        else {
            response.getDeletedFiles().add(new AddedOrDeletedFile(path, mode));
        }
    }

    /**
     * Parses a copy or rename line. The formats of the lines it parses are (without quotes):
     * <ul>
     * <li>
     * 
     * <pre>
     * &quot; copy path/to/{file.txt =&gt; newName.txt} (100%)&quot;
     * </pre>
     * 
     * </li>
     * <li>
     * 
     * <pre>
     * &quot; copy path/to/file.txt =&gt; different/path/newName.txt (5%)&quot;
     * </pre>
     * 
     * </li>
     * </ul>
     * 
     * @param line The line of text to parse.
     * @param isCopy True if parsing a copy line, false if parsing a rename line.
     */
    private void parseCopyRenameLine(String line, boolean isCopy) {
        final int PATH_START_COPY = 6;
        final int PATH_START_RENAME = 8;
        int pathStart = (isCopy ? PATH_START_COPY : PATH_START_RENAME);
        int openCurlyOffset = line.indexOf('{', pathStart);
        int openParenOffset = line.lastIndexOf('(');

        String fromPathStr = null;
        String toPathStr = null;

        if (-1 == openCurlyOffset) {
            int arrowOffset = line.indexOf("=>");
            fromPathStr = line.substring(pathStart, arrowOffset - 1);
            toPathStr = line.substring(arrowOffset + 3, openParenOffset - 1);
        }
        else {
            String base = line.substring(pathStart, openCurlyOffset);
            int arrowOffset = line.indexOf("=>", openCurlyOffset);
            fromPathStr = base + line.substring(openCurlyOffset + 1, arrowOffset - 1);
            int closeCurlyOffset = line.indexOf('}', arrowOffset + 3);
            toPathStr = base + line.substring(arrowOffset + 3, closeCurlyOffset);
        }

        File fromPath = new File(workingDirectory + fromPathStr);
        File toPath = new File(workingDirectory + toPathStr);

        int percentOffset = line.lastIndexOf('%');
        int percentage = 0;
        try {
            percentage = Integer.parseInt(line.substring(openParenOffset + 1, percentOffset));
        } catch (NumberFormatException e) {
            this.errors.add(new OutputErrorOrWarn(numLinesParsed, e.getMessage()));
        }

        if (isCopy) {
            response.getCopiedFiles().add(new CopiedOrMovedFile(fromPath, toPath, percentage));
        }
        else {
            response.getRenamedFiles().add(new CopiedOrMovedFile(fromPath, toPath, percentage));
        }
    }

    /**
     * Gets a <code>GitCommitResponse</code> object containing the information from the commit response text parsed by
     * this IParser instance.
     * 
     * @return The <code>GitCommitResponse</code> object containing the commit's response information.
     */
    public GitCommitResponse getResponse() throws JavaGitException {
        if (this.errors.size() != 0 && this.response.getProcessExitCode() != 0) {
            throw new JavaGitException(410000, ExceptionMessageMap.getMessage("410000")
                    + "  The git-commit error message:  { " + this.getError() + " }");
        }
        return response;
    }

}
