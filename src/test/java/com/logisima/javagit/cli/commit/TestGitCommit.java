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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.add.GitAdd;
import com.logisima.javagit.cli.init.GitInit;
import com.logisima.javagit.test.utilities.FileUtilities;

/**
 * Implements test cases for for GitCommit.
 */
public class TestGitCommit extends TestCase {

    private File repoDirectory;

    @Before
    protected void setUp() throws IOException, JavaGitException {
        repoDirectory = FileUtilities.createTempDirectory("GitCommitTestRepo");
        GitInit gitInit = new GitInit();
        gitInit.init(repoDirectory, null);
    }

    @After
    protected void tearDown() throws JavaGitException {
        // delete repo dir
        FileUtilities.removeDirectoryRecursivelyAndForcefully(repoDirectory);
    }

    @Test
    public void testCommit() throws IOException, JavaGitException {
        File testFile = FileUtilities.createFile(repoDirectory, "fileA.txt", "Sameple Contents");

        // Add a file to the repo
        List<File> filesToAdd = new ArrayList<File>();
        // filesToAdd.add(new File("fileA.txt"));
        filesToAdd.add(testFile);
        GitAdd gitAdd = new GitAdd();
        gitAdd.add(repoDirectory, filesToAdd, null);

        // Call commit
        GitCommit gitCommit = new GitCommit();
        GitCommitResponse resp = gitCommit.commit(repoDirectory, "Making a first test commit", null);
        resp.getFilesChanged();
        assertEquals("Short comment not as expected", resp.getCommitShortComment(), "Making a first test commit");
        assertEquals("", resp.getFilesChanged(), 1);
        assertEquals("", resp.getLinesDeleted(), 0);
        assertEquals("", resp.getLinesInserted(), 1);
    }

}
