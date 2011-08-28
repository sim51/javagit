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
import com.logisima.javagit.cli.init.GitInit;
import com.logisima.javagit.test.utilities.FileUtilities;

public class GitStatusTest extends GitTestCase {

    File repositoryPath;

    @Before
    public void setUp() throws Exception {
        repositoryPath = FileUtilities.createTempDirectory("GitCommitTestRepo");
        GitInit gitInit = new GitInit();
        gitInit.init(repositoryPath, null);
    }

    /**
     * Testing "git status" just after a "git init".
     * 
     * @throws JavaGitException
     */
    @Test
    public void testOnInitRepo() throws JavaGitException {
        JavaGit git = new JavaGit(repositoryPath);
        GitStatusResponseImpl response = (GitStatusResponseImpl) git.status(null);
        gitStatusTestEquals(response, "master", 0, 0, 0, 0, 0, 0,
                "nothing to commit (create/copy files and use \"git add\" to track)");
    }

    /**
     * Testing "git status" on a basic repo.
     * 
     * @throws JavaGitException
     * @throws IOException
     */
    @Test
    public void testOnNormalRepo() throws JavaGitException, IOException {
        JavaGit git = new JavaGit(repositoryPath);

        // create repo structure
        File fileA = FileUtilities.createFile(repositoryPath, "fileB.txt", "This is file fileB.txt");
        File dirA = new File(repositoryPath.getAbsolutePath() + File.separator + "dirA");
        dirA.mkdirs();
        File fileB = FileUtilities.createFile(repositoryPath, "dirA/fileB.txt", "Sample Contents");
        File fileC = FileUtilities.createFile(repositoryPath, "dirA/fileC.txt", "Sample Contents");
        File fileD = FileUtilities.createFile(repositoryPath, "dirA/fileD.txt", "Sample Contents");

        // list of files to "git added"
        List<File> files = new ArrayList<File>();
        files.add(fileA);
        files.add(new File("dirA"));
        files.add(fileB);
        files.add(fileC);
        files.add(fileD);

        git.add(files, null);
        git.commit("Making a first test commit", null);

        // 1 file to commit & 1 file updated to commit
        File fileE = FileUtilities.createFile(repositoryPath, "dirA/fileE.txt", "Sample Contents");
        List<File> files2 = new ArrayList<File>();
        files2.add(fileE);
        git.add(files2, null);

        // 1 file modify but not updated
        FileUtilities.modifyFileContents(new File(repositoryPath.getAbsolutePath() + File.separator + "fileB.txt"),
                "test");

        // 1 file untracked
        File fileG = FileUtilities.createFile(repositoryPath, "dirA/fileG.txt", "Sample Contents");

        GitStatusResponseImpl response = (GitStatusResponseImpl) git.status(null);

        gitStatusTestEquals(response, "master", 1, 1, 0, 0, 0, 1, null);
    }

    @After
    public void tearDown() throws Exception {
        if (repositoryPath.exists()) {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryPath);
        }
    }
}
