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
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitAdd</code> interface.
 * 
 * TODO (gsd216) - to implement exception chaining.
 */
public class GitAdd implements IGitAdd {

    /**
     * Implementations of &lt;git-add&gt; with options and list of files provided.
     */
    public GitAddResponse add(File repositoryPath, GitAddOptions options, List<File> paths) throws JavaGitException,
            IOException {
        CheckUtilities.checkFileValidity(repositoryPath);
        GitAddParser parser = new GitAddParser();
        List<String> command = buildCommand(repositoryPath, options, paths);
        GitAddResponseImpl response = (GitAddResponseImpl) ProcessUtilities.runCommand(repositoryPath, command, parser);

        if (options != null) {
            addDryRun(options, response);
        }
        return (GitAddResponse) response;
    }

    /**
     * Adds a list of files with no GitAddOptions.
     */
    public GitAddResponse add(File repositoryPath, List<File> files) throws JavaGitException, IOException {
        GitAddOptions options = null;
        return add(repositoryPath, options, files);
    }

    /**
     * Adds one file to the index with no GitAddOptions.
     */
    public GitAddResponse add(File repositoryPath, File file) throws JavaGitException, IOException {
        List<File> filePaths = new ArrayList<File>();
        filePaths.add(file);
        GitAddOptions options = null;
        return add(repositoryPath, options, filePaths);
    }

    /**
     * Implementations of &lt;git-add&gt; with options and one file to be added to index.
     */
    public GitAddResponse add(File repositoryPath, GitAddOptions options, File file) throws JavaGitException,
            IOException {
        List<File> paths = new ArrayList<File>();
        paths.add(file);
        return add(repositoryPath, options, paths);
    }

    /**
     * Implementation of &lt;git-add&gt; dry run.
     */
    public GitAddResponse addDryRun(File repositoryPath, List<File> paths) throws JavaGitException, IOException {
        GitAddOptions options = new GitAddOptions();
        options.setDryRun(true);
        return add(repositoryPath, options, paths);
    }

    /**
     * Implementations of &lt;git-add&gt; in verbose mode.
     */
    public GitAddResponse addVerbose(File repositoryPath, List<File> paths) throws JavaGitException, IOException {
        GitAddOptions options = new GitAddOptions();
        options.setVerbose(true);
        return add(repositoryPath, options, paths);
    }

    /**
     * Implementations of &lt;git-add&gt; with force option set.
     */
    public GitAddResponse addWithForce(File repositoryPath, List<File> paths) throws JavaGitException, IOException {
        GitAddOptions options = new GitAddOptions();
        options.setForce(true);
        return add(repositoryPath, options, paths);
    }

    /**
     * if the dry run option was selected then set the flag in response.
     * 
     * @param options <code>GitAddOptions</code>
     * @param response <code>gitAddResponse</code>
     */
    private void addDryRun(GitAddOptions options, GitAddResponseImpl response) {
        if (options.dryRun()) {
            response.setDryRun(true);
        }
    }

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
