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
package com.logisima.javagit.object;

import java.io.File;
import java.io.IOException;
import java.util.List;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.init.GitInit;
import com.logisima.javagit.cli.status.GitStatusResponse;
import com.logisima.javagit.object.GitFileSystemObject.Status;
import com.logisima.javagit.test.utilities.FileUtilities;

public class GitFileSystemTest extends TestCase {

    private File        repositoryDirectory;
    private DotGit      dotGit;
    private WorkingTree workingTree;

    @Before
    public void setUp() throws JavaGitException, IOException {
        repositoryDirectory = FileUtilities.createTempDirectory("GitFileSystemTest_dir");
        GitInit gitInit = new GitInit();
        gitInit.init(repositoryDirectory, null);
        dotGit = DotGit.getInstance(repositoryDirectory);
        workingTree = WorkingTree.getInstance(repositoryDirectory);
    }

    /**
     * creates a single file and runs a series of tests on it
     */
    @Test
    public void testSingleGitFile() throws IOException, JavaGitException {
        // Create a file
        FileUtilities.createFile(repositoryDirectory, "abc.txt", "Some data");

        // check contents
        List<GitFileSystemObject> children = workingTree.getTree();
        assertEquals("Error. Expecting only one file.", 1, children.size());

        GitFileSystemObject currentFile = children.get(0);
        assertEquals("Error. Expecting instance of GitFile.", GitFile.class, currentFile.getClass());

        GitFile gitFile = (GitFile) currentFile;
        assertEquals("Error. Expecting UNTRACKED status for the single file.", Status.UNTRACKED, gitFile.getStatus());

        gitFile.add();
        assertEquals("Error. Expecting NEW_TO_COMMIT status for the single file.", Status.NEW_TO_COMMIT,
                gitFile.getStatus());

        workingTree.getFile(new File("x"));

        gitFile.commit("commit message");
        assertEquals("Error. Expecting IN_REPOSITORY status for the single file.", Status.IN_REPOSITORY,
                gitFile.getStatus());

        FileUtilities.modifyFileContents(gitFile.getFile(), "more data");
        assertEquals("Error. Expecting MODIFIED status for the single file.", Status.MODIFIED, gitFile.getStatus());

        gitFile.add();
        assertEquals("Error. Expecting MODIFIED_TO_COMMIT status for the single file.", Status.MODIFIED_TO_COMMIT,
                gitFile.getStatus());

        gitFile.commit("commit message");
        assertEquals("Error. Expecting IN_REPOSITORY status for the single file.", Status.IN_REPOSITORY,
                gitFile.getStatus());

    }

    @Test
    /**
     * Adds more file system objects
     */
    public void testGitFileSystem() throws IOException, JavaGitException {
        // Add another file
        FileUtilities.createFile(repositoryDirectory, "file1", "Some data");
        FileUtilities.createFile(repositoryDirectory, "file2", "Some data");

        // check contents
        List<GitFileSystemObject> children = workingTree.getTree();
        assertEquals("Error. Expecting 2 files.", 2, children.size());

        // attempt to commit (but without anything on the index)
        try {
            workingTree.commit("commit comment");
            fail("JavaGitException not thrown");
        } catch (JavaGitException e) {
        }

        // get children
        GitFile gitFile1 = (GitFile) children.get(0);
        GitFile gitFile2 = (GitFile) children.get(1);

        // both should be untracked
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile1.getStatus());
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile2.getStatus());

        // another way to see the same thing
        File file1 = new File("file1");
        File file2 = new File("file2");
        GitStatusResponse statusResponse = workingTree.getStatus();
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, statusResponse.getFileStatus(file1));
        assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, statusResponse.getFileStatus(file2));

        // stage one file
        gitFile1.add();

        // TODO (ma1683): check why the following tests fail on different system
        /*
         * //check status
         * 
         * assertEquals("Error. Expecting NEW_TO_COMMIT.", Status.NEW_TO_COMMIT, gitFile1.getStatus());
         * assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile2.getStatus());
         * 
         * //alternative way statusResponse = workingTree.getStatus(); assertEquals("Error. Expecting NEW_TO_COMMIT.",
         * Status.NEW_TO_COMMIT, statusResponse.getFileStatus(file1)); assertEquals("Error. Expecting UNTRACKED.",
         * Status.UNTRACKED, statusResponse.getFileStatus(file2));
         * 
         * //commit everything added to the index workingTree.commitAll("commit comment"); //check status
         * assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile1.getStatus());
         * assertEquals("Error. Expecting UNTRACKED.", Status.UNTRACKED, gitFile2.getStatus()); //alternative way
         * statusResponse = workingTree.getStatus(); assertEquals("Error. Expecting IN_REPOSITORY.",
         * Status.IN_REPOSITORY, statusResponse.getFileStatus(file1)); assertEquals("Error. Expecting UNTRACKED.",
         * Status.UNTRACKED, statusResponse.getFileStatus(file2));
         * 
         * //commit everything workingTree.addAndCommitAll("commit comment"); //check status
         * assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile1.getStatus());
         * assertEquals("Error. Expecting IN_REPOSITORY.", Status.IN_REPOSITORY, gitFile2.getStatus()); //alternative
         * way statusResponse = workingTree.getStatus(); assertEquals("Error. Expecting IN_REPOSITORY.",
         * Status.IN_REPOSITORY, statusResponse.getFileStatus(file1)); assertEquals("Error. Expecting IN_REPOSITORY.",
         * Status.IN_REPOSITORY, statusResponse.getFileStatus(file2));
         */
    }

    @After
    public void tearDown() throws Exception {
        if (repositoryDirectory.exists()) {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
        }
    }

}
