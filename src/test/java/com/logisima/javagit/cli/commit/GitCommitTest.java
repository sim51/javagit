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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.logisima.javagit.JavaGit;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.GitTestCase;
import com.logisima.javagit.test.utilities.FileUtilities;

public class GitCommitTest extends GitTestCase {

    File repositoryPath;

    @Before
    protected void setUp() throws IOException, JavaGitException {
        repositoryPath = FileUtilities.createTempDirectory("GitCommitTestRepo");
        JavaGit git = new JavaGit(repositoryPath);
        git.init(null);
    }

    @Test
    public void testCommit() throws IOException, JavaGitException {
        File testFile = FileUtilities.createFile(repositoryPath, "fileA.txt", "Sameple Contents");
        JavaGit git = new JavaGit(repositoryPath);

        // Add a file to the repo
        List<File> filesToAdd = new ArrayList<File>();
        // filesToAdd.add(new File("fileA.txt"));
        filesToAdd.add(testFile);
        git.add(filesToAdd, null);

        // Call commit
        GitCommitResponse response = (GitCommitResponse) git.commit("Making a first test commit", null);

        gitCommitTestEquals(response, "Making a first test commit", null, 1, 0, 1, 1, 0, 0, 0);
    }

    @After
    protected void tearDown() throws JavaGitException {
        // delete repo dir
        FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryPath);
    }
}
