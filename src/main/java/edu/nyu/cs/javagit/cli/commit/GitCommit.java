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
package edu.nyu.cs.javagit.cli.commit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitCommit</code> interface.
 */
public class GitCommit {

    public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message)
            throws JavaGitException {
        return commitProcessor(repository, options, message, null);
    }

    public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message, List<File> paths)
            throws JavaGitException {
        return commitProcessor(repository, options, message, paths);
    }

    /**
     * Processes the commit.
     */
    protected GitCommitResponseImpl commitProcessor(File repository, GitCommitOptions options, String message,
            List<File> paths) throws JavaGitException {
        CheckUtilities.checkNullArgument(repository, "repository");
        CheckUtilities.checkStringArgument(message, "message");

        List<String> commandLine = buildCommand(options, message, paths);
        GitCommitParser parser = new GitCommitParser(repository.getAbsolutePath());

        try {
            return (GitCommitResponseImpl) ProcessUtilities.runCommand(repository, commandLine, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     */
    protected List<String> buildCommand(GitCommitOptions options, String message, List<File> paths) {
        List<String> cmd = new ArrayList<String>();
        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("commit");

        if (null != options) {
            if (options.isOptAll()) {
                cmd.add("-a");
            }
            if (options.isOptInclude()) {
                cmd.add("-i");
            }
            if (options.isOptNoVerify()) {
                cmd.add("--no-verify");
            }
            if (options.isOptOnly()) {
                cmd.add("-o");
            }
            if (options.isOptSignoff()) {
                cmd.add("-s");
            }
            String author = options.getAuthor();
            if (null != author && author.length() > 0) {
                cmd.add("--author");
                cmd.add(options.getAuthor());
            }
        }

        cmd.add("-m");
        cmd.add(message);

        if (null != paths) {
            cmd.add("--");
            for (File f : paths) {
                cmd.add(f.getPath());
            }
        }

        return cmd;
    }

}
