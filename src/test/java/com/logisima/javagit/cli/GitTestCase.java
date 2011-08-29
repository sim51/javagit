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
 *  along with JavGit. If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli;

import junit.framework.TestCase;

import com.logisima.javagit.cli.add.GitAddResponse;
import com.logisima.javagit.cli.clone.GitCloneResponse;
import com.logisima.javagit.cli.commit.GitCommitResponse;
import com.logisima.javagit.cli.init.GitInitResponse;
import com.logisima.javagit.cli.status.GitStatusResponse;

public class GitTestCase extends TestCase {

    /**
     * Method that do all "assertEquals" on the <code>GitAddResponse</code>.
     * 
     * @param response the <code>GitAddResponse</code> to test.
     * @param nbAddedFile expected number of added files.
     * @param nbComment expected number of comment.
     */
    protected static void gitAddTestEquals(GitAddResponse response, int nbAddedFile, int nbComment) {
        assertEquals(nbAddedFile, response.getFilesAdded().size());
        assertEquals(nbComment, response.getComments().size());
    }

    /**
     * Method that do all "assertEquals" on the <code>GitCloneResponse</code>.
     * 
     * @param response the <code>GitCloneResponse</code> to test.
     */
    protected static void gitCloneTestEquals(GitCloneResponse response) {
        assertNotNull(response);

    }

    /**
     * Method that do all "assertEquals" on the <code>GitCommitResponse</code>.
     * 
     * @param response the <code>GitCommitResponse</code> to test.
     * @param shortComment expected short comment.
     * @param shortHashName expected short hashName.
     * @param nbFilesChanged expected number of changed files.
     * @param nbLinesDeleted expected number of deleted lines.
     * @param nbLinesInserted expected number of inserted lines.
     * @param nbAddedFiles expected number of added files.
     * @param nbCopiedFiles expected number of copied files.
     * @param nbDeletedFiles expected number of deleted files.
     * @param nbRenamedFiles expected number of renamed files.
     */
    protected static void gitCommitTestEquals(GitCommitResponse response, String shortComment, String shortHashName,
            int nbFilesChanged, int nbLinesDeleted, int nbLinesInserted, int nbAddedFiles, int nbCopiedFiles,
            int nbDeletedFiles, int nbRenamedFiles) {
        assertEquals(shortComment, response.getCommitShortComment());
        if (shortHashName != null) {
            assertEquals(shortHashName, response.getCommitShortHashName().getName());
        }
        assertEquals(nbFilesChanged, response.getFilesChanged());
        assertEquals(nbLinesDeleted, response.getLinesDeleted());
        assertEquals(nbLinesInserted, response.getLinesInserted());
        assertEquals(nbAddedFiles, response.getAddedFiles().size());
        assertEquals(nbCopiedFiles, response.getCopiedFiles().size());
        assertEquals(nbDeletedFiles, response.getDeletedFiles().size());
        assertEquals(nbRenamedFiles, response.getRenamedFiles().size());
    }

    /**
     * Method that do all "assertEquals" on the <code>GitInitResponse</code>.
     * 
     * @param response the <code>GitInitResponse</code> to test.
     * @param initialize expected initialize boolean.
     * @param reinitialize expected reinitialize boolean.
     */
    protected static void gitInitTestEquals(GitInitResponse response, boolean initialize, boolean reinitialize) {
        assertEquals(initialize, response.isInitialized());
        assertEquals(reinitialize, response.isReinitialized());
    }

    /**
     * Method that do all "assertEquals" on the <code>GitStatusResponse</code>.
     * 
     * @param response the <code>GitStatusResponse</code> to test.
     * @param branch expected branch name
     * @param nbNewFilesToCommit expected number of new file to commit.
     * @param nbModifiedFilesNotUpdated expected number of modified files and not updated.
     * @param nbDeletedFilesNotUpdated expected number of deleted files and not updated.
     * @param nbModifiedFilesToCommit expected number of modified files to commit.
     * @param nbDeletedFilesToCommit expected number of deleted files to commit.
     * @param nbUntrackedFiles expected number of untracked files.
     */
    protected static void gitStatusTestEquals(GitStatusResponse response, String branch, int nbNewFilesToCommit,
            int nbModifiedFilesNotUpdated, int nbDeletedFilesNotUpdated, int nbModifiedFilesToCommit,
            int nbDeletedFilesToCommit, int nbUntrackedFiles, String message) {
        assertEquals(branch, response.getBranch().getName());
        assertEquals(nbNewFilesToCommit, response.getNewFilesToCommit().size());
        assertEquals(nbModifiedFilesNotUpdated, response.getModifiedFilesNotUpdated().size());
        assertEquals(nbDeletedFilesNotUpdated, response.getDeletedFilesNotUpdated().size());
        assertEquals(nbModifiedFilesToCommit, response.getModifiedFilesToCommit().size());
        assertEquals(nbDeletedFilesToCommit, response.getDeletedFilesToCommit().size());
        assertEquals(nbUntrackedFiles, response.getUntrackedFiles().size());
        assertEquals(message, response.getMessage());
    }
}
