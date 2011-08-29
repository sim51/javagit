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
import com.logisima.javagit.test.utilities.FileUtilities;

public class BranchingTest extends TestCase {

    private File        repositoryDirectory;
    private DotGit      dotGit;
    private WorkingTree workingTree;

    @Before
    public void setUp() throws JavaGitException, IOException {
        repositoryDirectory = FileUtilities.createTempDirectory("TestGitBranching_dir");
        GitInit gitInit = new GitInit();
        gitInit.init(repositoryDirectory, null);
        dotGit = DotGit.getInstance(repositoryDirectory);
        workingTree = WorkingTree.getInstance(repositoryDirectory);
    }

    /**
   * 
   */
    @Test
    public void testGitApiBranch() throws IOException, JavaGitException {
        // Create a file
        FileUtilities.createFile(repositoryDirectory, "file1.txt", "Some data");

        // check contents
        List<GitFileSystemObject> children = workingTree.getTree();
        assertEquals("Error. Expecting only one file.", 1, children.size());

        GitFileSystemObject file = children.get(0);
        file.commit("some comment");

        // now we can create new branch
        Ref createdBranch = dotGit.createBranch("branch1");

        Ref currentBranch = workingTree.getCurrentBranch();
        assertEquals("Error. Expecting master branch.", Ref.createBranchRef("master"), currentBranch);

        // iterate through branches
        boolean found = false;
        List<Ref> branches = dotGit.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            // check
            Ref branch = branches.get(i);
            if (branch.equals(createdBranch)) {
                found = true;
            }

            // test checkout
            if (!branch.equals(currentBranch)) {
                workingTree.checkout(branch);
                currentBranch = workingTree.getCurrentBranch();
                assertEquals("Error. Expecting new current branch.", branch, currentBranch);
            }
        }

        assertEquals("Error. Expecting created branch to appear.", true, found);

        // test branch rename
        String newName = new String("branch2");
        Ref renamedBranch = dotGit.renameBranch(Ref.createBranchRef("branch1"), newName, true);
        assertEquals("Error. Expecting renamed branch.", newName, renamedBranch.getName());

        found = false;
        branches = dotGit.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            Ref branch = branches.get(i);
            if (branch.equals(renamedBranch)) {
                found = true;
            }
        }
        assertEquals("Error. Expecting renamed branch to appear.", true, found);

        // test branch delete
        dotGit.deleteBranch(renamedBranch, true);
        found = false;
        branches = dotGit.getBranches();
        for (int i = 0; i < branches.size(); i++) {
            Ref branch = branches.get(i);
            if (branch.equals(renamedBranch)) {
                found = true;
            }
        }
        assertEquals("Error. Expecting deleted branch to disappear.", false, found);
    }

    @After
    public void tearDown() throws Exception {
        if (repositoryDirectory.exists()) {
            FileUtilities.removeDirectoryRecursivelyAndForcefully(repositoryDirectory);
        }
    }

}
