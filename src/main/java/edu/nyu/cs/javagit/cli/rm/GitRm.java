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
package edu.nyu.cs.javagit.cli.rm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitRm</code> interface.
 */
public class GitRm {

    public GitRmResponse rm(File repository, GitRmOptions options, File path) throws JavaGitException {
        return processRm(repository, options, path, null);
    }

    public GitRmResponse rm(File repository, GitRmOptions options, List<File> paths) throws JavaGitException {
        return processRm(repository, options, null, paths);
    }

    /**
     * Processes an incoming <code>GitRm</code> request.
     */
    private GitRmResponse processRm(File repository, GitRmOptions options, File path, List<File> paths)
            throws JavaGitException {
        List<String> cmdline = buildCommandLine(options, path, paths);

        GitRmParser parser = new GitRmParser();
        try {
            return (GitRmResponse) ProcessUtilities.runCommand(repository, cmdline, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Builds the command line.
     */
    private List<String> buildCommandLine(GitRmOptions options, File path, List<File> paths) {
        List<String> cmdline = new ArrayList<String>();

        cmdline.add(JavaGitConfiguration.getGitCommand());
        cmdline.add("rm");

        if (null != options) {
            if (options.isOptCached()) {
                cmdline.add("--cached");
            }
            if (options.isOptF()) {
                cmdline.add("-f");
            }
            if (options.isOptN()) {
                cmdline.add("-n");
            }
            if (options.isOptQ()) {
                cmdline.add("-q");
            }
            if (options.isOptR()) {
                cmdline.add("-r");
            }
        }
        if (null != path) {
            cmdline.add(path.getPath());
        }
        else {
            for (File f : paths) {
                cmdline.add(f.getPath());
            }
        }
        return cmdline;
    }

}
