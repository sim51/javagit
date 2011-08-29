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
package com.logisima.javagit.cli.clone;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitClone</code> interface.
 */
public class GitClone {

    public GitCloneResponse clone(File repositoryPath, GitCloneOptions options, URL repository) throws JavaGitException {
        GitCloneResponse response;
        try {
            response = cloneProcess(repositoryPath, options, repository);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return response;
    }

    /**
     * Process the git-clone command, to make a clone of the git repository.
     */
    public GitCloneResponse cloneProcess(File repositoryPath, GitCloneOptions options, URL repository)
            throws IOException, JavaGitException {
        List<String> commandLine = buildCommand(options, repository, repositoryPath);
        GitCloneParser parser = new GitCloneParser();

        return (GitCloneResponse) ProcessUtilities.runCommand(repositoryPath, commandLine, parser);
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     */
    protected List<String> buildCommand(GitCloneOptions options, URL repositoryUrl, File repositoryPath) {
        List<String> cmd = new ArrayList<String>();

        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("clone");
        cmd.add(repositoryUrl.toString());
        cmd.add(repositoryPath.getPath());
        return cmd;
    }

}
