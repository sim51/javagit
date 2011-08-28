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
package com.logisima.javagit.cli.commit;

import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Test;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.test.utilities.ParserTestUtilities;

public class GitCommitParserTest extends TestCase {

    private static void testEquals(GitCommitResponseImpl response, String shortComment, String shortHashName,
            int nbFilesChanged, int nbLinesDeleted, int nbLinesInserted, int nbAddedFiles, int nbCopiedFiles,
            int nbDeletedFiles, int nbRenamedFiles) {
        assertEquals(shortComment, response.getCommitShortComment());
        assertEquals(shortHashName, response.getCommitShortHashName().getName());
        assertEquals(nbFilesChanged, response.getFilesChanged());
        assertEquals(nbLinesDeleted, response.getLinesDeleted());
        assertEquals(nbLinesInserted, response.getLinesInserted());
        assertEquals(nbAddedFiles, response.addedFiles.size());
        assertEquals(nbCopiedFiles, response.copiedFiles.size());
        assertEquals(nbDeletedFiles, response.deletedFiles.size());
        assertEquals(nbRenamedFiles, response.renamedFiles.size());
    }

    @Test
    public void testGitCommitOuput1() throws IOException, JavaGitException {
        final String workingDirectory = "/tmp/";
        GitCommitParser parser = new GitCommitParser(workingDirectory);

        GitCommitResponseImpl response = (GitCommitResponseImpl) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/commit/GitCommitOutputTest1");

        // testing
        testEquals(response, "begin TU", "09c2765", 12, 16, 1011, 7, 0, 0, 2);
    }

    @Test
    public void testGitCommitOuput2() throws IOException, JavaGitException {
        final String workingDirectory = "/tmp/";
        GitCommitParser parser = new GitCommitParser(workingDirectory);

        GitCommitResponseImpl response = (GitCommitResponseImpl) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/commit/GitCommitOutputTest2");

        // testing
        testEquals(response, "my comment", "b8181b8", 0, 0, 0, 1, 0, 0, 0);
    }
}
