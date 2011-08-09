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
package edu.nyu.cs.javagit.cli.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitAdd</code> interface.
 * 
 */
public class GitAdd {

    /**
     * Adds a list of files with GitAddOptions.
     */
    public GitAddResponse add(File repositoryPath, List<File> paths, GitAddOptions options) throws JavaGitException {
        GitAddParser parser = new GitAddParser();
        List<String> command = buildCommand(repositoryPath, options, paths);
        GitAddResponseImpl response;
        try {
            response = (GitAddResponseImpl) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return (GitAddResponse) response;
    }

    /**
     * Adds one file to the index with GitAddOptions.
     */
    public GitAddResponse add(File repositoryPath, File file, GitAddOptions options) throws JavaGitException {
        List<File> paths = new ArrayList<File>();
        paths.add(file);
        return add(repositoryPath, paths, options);
    }

    /**
     * Constructor of the command line.
     */
    private List<String> buildCommand(File repositoryPath, GitAddOptions options, List<File> paths) {
        List<String> command = new ArrayList<String>();
        command.add(JavaGitConfiguration.getGitCommand());
        command.add("add");
        if (options != null) {
            if (options.dryRun()) {
                command.add("-n");
            }
            if (options.verbose()) {
                command.add("-v");
            }
            if (options.force()) {
                command.add("-f");
            }
            if (options.update()) {
                command.add("-u");
            }
            if (options.refresh()) {
                command.add("--refresh");
            }
            if (options.ignoreErrors()) {
                command.add("--ignore-errors");
            }
        }
        if (paths != null && paths.size() > 0) {
            for (File file : paths) {
                command.add(file.getPath());
            }
        }
        return command;
    }

}
