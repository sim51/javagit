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
package com.logisima.javagit.cli.add;

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
import com.logisima.javagit.cli.status.GitStatusResponseImpl;
import com.logisima.javagit.test.utilities.FileUtilities;

public class GitAddTest extends GitTestCase {

    File repositoryPath;

    @Before
    public void setUp() throws Exception {
        repositoryPath = FileUtilities.createTempDirectory("GitCommitTestRepo");
        GitInit gitInit = new GitInit();
        gitInit.init(repositoryPath, null);
    }

    /**
     * Test for adding couple of files and a directory to the repository in verbose mode and then verifying that these
     * are in fact added by &lt;git add&gt; command.
     */
    @Test
    public void testAddingFilesToRepository() throws IOException, JavaGitException {
        // intialize JavaGit
        JavaGit git = new JavaGit(repositoryPath);

        // create repo structure
        File file1 = FileUtilities.createFile(repositoryPath, "fileB.txt", "This is file fileB.txt");
        File dirA = new File(repositoryPath.getAbsolutePath() + File.separator + "dirA");
        dirA.mkdirs();
        File file2 = FileUtilities.createFile(repositoryPath, "dirA/fileB.txt", "Sample Contents");

        // list of files to "git added"
        List<File> files = new ArrayList<File>();
        files.add(file1);
        files.add(new File("dirA"));
        files.add(file2);

        // git add verbose option
        GitAddOptions options = new GitAddOptions();
        options.setVerbose(true);

        GitAddResponseImpl response = (GitAddResponseImpl) git.add(files, options);

        // testing
        gitAddTestEquals(response, 2, 0);
    }

    /**
     * Test to add one file at a time to the repository with no options provided.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testAddingOneFileToRepository() throws IOException, JavaGitException {
        // intialize JavaGit
        JavaGit git = new JavaGit(repositoryPath);

        // create repo structure
        File file1 = FileUtilities.createFile(repositoryPath, "fileA.txt", "This is file fileA.txt");
        File dirA = new File(repositoryPath.getPath() + File.separator + "dirA");
        dirA.mkdirs();
        File file2 = FileUtilities.createFile(repositoryPath, "dirA" + File.separator + "fileB.txt",
                "Sample Contents fileB.txt");

        // perform "git add"
        git.add(file1, null);
        git.add(file1, null);
        git.add(new File("dirA"), null);
        git.add(file2, null);

        // check with "git status"
        GitStatusResponseImpl response = (GitStatusResponseImpl) git.status(null);
        gitStatusTestEquals(response, "master", 2, 0, 0, 0, 0, 0, null);
    }

    /**
     * Test for adding multiple files to add to repository with no options provided.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testAddingFilesToRepositoryWithNoOptions() throws IOException, JavaGitException {
        // intialize JavaGit
        JavaGit git = new JavaGit(repositoryPath);

        // create repo structure
        File file1 = FileUtilities.createFile(repositoryPath, "fileA.txt", "This is file fileA.txt");
        File dirA = new File(repositoryPath.getAbsolutePath() + File.separator + "dirA");
        dirA.mkdirs();
        File file2 = FileUtilities.createFile(repositoryPath, "dirA" + File.separator + "fileB.txt",
                "Sample Contents fileB.txt");

        // perform "git add"
        List<File> files = new ArrayList<File>();
        files.add(file1);
        files.add(new File("dirA"));
        files.add(file2);
        git.add(files, null);

        // check with "git status"
        GitStatusResponseImpl response = (GitStatusResponseImpl) git.status(null);
        gitStatusTestEquals(response, "master", 2, 0, 0, 0, 0, 0, null);
    }

    @After
    public void tearDown() throws Exception {
        if (repositoryPath.exists()) {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryPath);
        }
    }
}
