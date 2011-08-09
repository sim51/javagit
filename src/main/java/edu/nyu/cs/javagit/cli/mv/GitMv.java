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
package edu.nyu.cs.javagit.cli.mv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitMv</code> interface.
 */
public class GitMv {

    public GitMvResponseImpl mv(File repoPath, GitMvOptions options, File source, File destination)
            throws JavaGitException {
        List<File> sources = new ArrayList<File>();
        sources.add(source);
        return mvProcess(repoPath, options, sources, destination);
    }

    public GitMvResponseImpl mv(File repoPath, GitMvOptions options, List<File> sources, File destination)
            throws JavaGitException {
        return mvProcess(repoPath, options, sources, destination);
    }

    /**
     * Exec of git-mv command
     */
    public GitMvResponseImpl mvProcess(File repoPath, GitMvOptions options, List<File> source, File destination)
            throws JavaGitException {

        List<String> commandLine = buildCommand(options, source, destination);
        GitMvParser parser = new GitMvParser();

        try {
            return (GitMvResponseImpl) ProcessUtilities.runCommand(repoPath, commandLine, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     */
    protected List<String> buildCommand(GitMvOptions options, List<File> source, File destination) {
        List<String> cmd = new ArrayList<String>();

        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("mv");

        if (null != options) {
            if (options.isOptF()) {
                cmd.add("-f");
            }
            if (options.isOptK()) {
                cmd.add("-k");
            }
            if (options.isOptN()) {
                cmd.add("-n");
            }
        }
        for (File file : source) {
            cmd.add(file.getPath());
        }
        cmd.add(destination.getPath());
        return cmd;
    }

}
