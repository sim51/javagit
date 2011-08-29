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
package com.logisima.javagit.cli.init;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.logisima.javagit.JavaGit;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.GitTestCase;
import com.logisima.javagit.test.utilities.FileUtilities;

public class GitInitTest extends GitTestCase {

    /**
     * Test for git init without options.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testGitInitWithoutOptions() throws IOException, JavaGitException {
        File repositoryPath = FileUtilities.createTempDirectory("GitCommitTestRepo");
        JavaGit git = new JavaGit(repositoryPath);

        GitInitResponse response = git.init(null);

        gitInitTestEquals(response, true, false);
    }

    /**
     * Test for git init on an intialize repository.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testGitInitReinitialise() throws IOException, JavaGitException {
        File repositoryPath = FileUtilities.createTempDirectory("GitCommitTestRepo");
        JavaGit git = new JavaGit(repositoryPath);

        git.init(null);
        GitInitResponse response = git.init(null);

        gitInitTestEquals(response, false, true);
    }

    /**
     * Test for git init with bare option.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testGitInitBare() throws IOException, JavaGitException {
        File repositoryPath = FileUtilities.createTempDirectory("GitCommitTestRepo");
        JavaGit git = new JavaGit(repositoryPath);

        GitInitOptions options = new GitInitOptions();
        options.setOptBare(true);
        GitInitResponse response = git.init(options);

        gitInitTestEquals(response, true, false);
    }

}
