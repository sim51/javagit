/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.cli.clone;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitClone</code> interface.
 */
public class GitClone {

    public GitCloneResponseImpl clone(File repositoryPath, GitCloneOptions options, URL repository)
            throws JavaGitException {
        GitCloneResponseImpl response;
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
    public GitCloneResponseImpl cloneProcess(File repositoryPath, GitCloneOptions options, URL repository)
            throws IOException, JavaGitException {
        List<String> commandLine = buildCommand(options, repository, repositoryPath);
        GitCloneParser parser = new GitCloneParser();

        return (GitCloneResponseImpl) ProcessUtilities.runCommand(repositoryPath, commandLine, parser);
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
