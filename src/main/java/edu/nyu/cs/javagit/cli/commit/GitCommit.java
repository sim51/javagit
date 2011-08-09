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

    public GitCommitResponseImpl commitAll(File repository, String message) throws IOException, JavaGitException {
        GitCommitOptions options = new GitCommitOptions();
        options.setOptAll(true);
        return commitProcessor(repository, options, message, null);
    }

    public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message) throws IOException,
            JavaGitException {
        return commitProcessor(repository, options, message, null);
    }

    public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message, List<File> paths)
            throws IOException, JavaGitException {
        return commitProcessor(repository, options, message, paths);
    }

    public GitCommitResponseImpl commit(File repository, String message) throws IOException, JavaGitException {
        return commitProcessor(repository, null, message, null);
    }

    public GitCommitResponseImpl commitOnly(File repository, String message, List<File> paths) throws IOException,
            JavaGitException {
        GitCommitOptions options = new GitCommitOptions();
        options.setOptOnly(true);
        return commitProcessor(repository, options, message, paths);
    }

    /**
     * Processes the commit.
     * 
     * @param repository A <code>File</code> instance for the path to the repository root (the parent directory of the
     *        .git directory) or a sub-directory in the working tree of the repository to commit against. This argument
     *        must represent the absolute path to the desired directory as returned by the <code>File.getPath()</code>
     *        method. If null is passed, a <code>NullPointerException</code> will be thrown.
     * @param options The options to commit with.
     * @param message The message to attach to the commit. A non-zero length argument is required for this parameter,
     *        otherwise a <code>NullPointerException</code> or <code>IllegalArgumentException</code> will be thrown.
     * @param paths A list of folders and/or files to commit. The paths specified in this list must all be relative to
     *        the path specified in the <code>repository</code> parameter as returned by <code>File.getPath()</code>.
     * @return The results from the commit.
     * @exception IOException There are many reasons for which an <code>IOException</code> may be thrown. Examples
     *            include:
     *            <ul>
     *            <li>a directory doesn't exist</li>
     *            <li>access to a file is denied</li>
     *            <li>a command is not found on the PATH</li>
     *            </ul>
     * @exception JavaGitException Thrown when there is an error making the commit.
     */
    protected GitCommitResponseImpl commitProcessor(File repository, GitCommitOptions options, String message,
            List<File> paths) throws IOException, JavaGitException {
        CheckUtilities.checkNullArgument(repository, "repository");
        CheckUtilities.checkStringArgument(message, "message");

        List<String> commandLine = buildCommand(options, message, paths);
        GitCommitParser parser = new GitCommitParser(repository.getAbsolutePath());

        return (GitCommitResponseImpl) ProcessUtilities.runCommand(repository, commandLine, parser);
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     * 
     * @param options The options to include on the command line.
     * @param message The message for the commit.
     * @return A list of the individual arguments to pass to <code>ProcessBuilder</code>.
     */
    protected List<String> buildCommand(GitCommitOptions options, String message, List<File> paths) {

        // TODO (jhl388): Add a unit test for this method (CliGitCommit.buildCommand()).

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
