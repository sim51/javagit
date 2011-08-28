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
package com.logisima.javagit.cli.status;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.test.utilities.ParserTestUtilities;

public class GitStatusParserTest extends TestCase {

    private static void testEquals(GitStatusResponseImpl response, String branch, int nbNewFilesToCommit,
            int nbModifiedFilesNotUpdated, int nbDeletedFilesNotUpdated, int nbModifiedFilesToCommit,
            int nbDeletedFilesToCommit, int nbUntrackedFiles) {
        assertEquals(branch, response.branch.getName());
        assertEquals(nbNewFilesToCommit, response.newFilesToCommit.size());
        assertEquals(nbModifiedFilesNotUpdated, response.modifiedFilesNotUpdated.size());
        assertEquals(nbDeletedFilesNotUpdated, response.deletedFilesNotUpdated.size());
        assertEquals(nbModifiedFilesToCommit, response.modifiedFilesToCommit.size());
        assertEquals(nbDeletedFilesToCommit, response.deletedFilesToCommit.size());
        assertEquals(nbUntrackedFiles, response.untrackedFiles.size());
    }

    @Test
    public void testGitStatusOuput1() throws IOException, JavaGitException {
        final String workingDirectory = "/tmp/";
        GitStatusParser parser = new GitStatusParser(workingDirectory);

        GitStatusResponseImpl response = (GitStatusResponseImpl) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/status/GitStatusOutputTest1");

        // testing
        testEquals(response, "mine", 1, 3, 2, 0, 0, 3);
    }
}
