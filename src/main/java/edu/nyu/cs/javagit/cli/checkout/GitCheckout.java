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
package edu.nyu.cs.javagit.cli.checkout;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.api.object.Ref;
import edu.nyu.cs.javagit.api.object.Ref.RefType;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitCheckout</code> interface.
 */
public class GitCheckout {

    /**
     * Git checkout with options and base branch information provided to &lt;git-checkout&gt; command.
     */
    public GitCheckoutResponse checkout(File repositoryPath, GitCheckoutOptions options, Ref ref)
            throws JavaGitException {
        CheckUtilities.checkFileValidity(repositoryPath);
        checkRefAgainstRefType(ref, RefType.HEAD);
        List<String> command = buildCommand(options, ref);
        GitCheckoutParser parser = new GitCheckoutParser();
        GitCheckoutResponse response;
        try {
            response = (GitCheckoutResponse) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return response;
    }

    /**
     * Checks out a list of files from repository, no checkout options provided.
     */
    public GitCheckoutResponse checkout(File repositoryPath, List<File> paths) throws JavaGitException, IOException {
        CheckUtilities.checkFileValidity(repositoryPath);
        CheckUtilities.checkNullListArgument(paths, "list of file paths");
        GitCheckoutParser parser = new GitCheckoutParser();
        List<String> command = buildCommand(null, null, paths);
        GitCheckoutResponse response = (GitCheckoutResponse) ProcessUtilities.runCommand(repositoryPath, command,
                parser);
        return response;
    }

    /**
     * Checks out a list of file from repository, with &lt;tree-ish&gt; options provided.
     */
    public GitCheckoutResponse checkout(File repositoryPath, GitCheckoutOptions options, Ref ref, List<File> paths)
            throws JavaGitException, IOException {
        CheckUtilities.checkFileValidity(repositoryPath);
        if (ref != null && ref.getRefType() == RefType.HEAD) {
            throw new IllegalArgumentException("Invalid ref type passed as argument to checkout");
        }
        GitCheckoutParser parser = new GitCheckoutParser();
        List<String> command = buildCommand(options, ref, paths);
        return (GitCheckoutResponse) ProcessUtilities.runCommand(repositoryPath, command, parser);
    }

    /**
     * This is just a test method for verifying that a given ref is not of refType provided as one of the parameters.
     * 
     * @param ref This could be branch, sha1 etc.
     * @param refType This is the <code>RefType</code> which the ref should not match.
     */
    private void checkRefAgainstRefType(Ref ref, RefType refType) {
        if (ref != null && ref.getRefType() == refType) {
            throw new IllegalArgumentException("Invalid ref type passed as argument to checkout");
        }
    }

    /**
     * builds a &lt;git-checkout&gt; command in sth <code>List<String></code> format.
     * 
     * @param options <code>GitCheckoutOptions</code> options passed to the &lt;git-checkout&gt; command.
     * @param treeIsh either a branch type or sha1 type object
     * @param paths List of files that are to be checked out
     * @return
     * @throws JavaGitException
     */
    private List<String> buildCommand(GitCheckoutOptions options, Ref treeIsh, List<File> paths)
            throws JavaGitException {
        List<String> command = new ArrayList<String>();
        command.add(JavaGitConfiguration.getGitCommand());
        command.add("checkout");
        // Process options
        if (options != null) {
            processOptions(command, options);
        }
        // Process tree-ish
        if (treeIsh != null) {
            command.add(treeIsh.getName());
        }
        // return if no file-paths are provided
        if (paths == null) {
            return command;
        }
        command.add("--");
        for (File file : paths) {
            command.add(file.getName());
        }
        return command;
    }

    private List<String> buildCommand(GitCheckoutOptions options, Ref branch) throws JavaGitException {
        List<String> command = new ArrayList<String>();
        command.add(JavaGitConfiguration.getGitCommand());
        command.add("checkout");
        if (options != null) {
            processOptions(command, options);
        }
        if (branch != null && branch.getName().length() > 0) {
            command.add(branch.getName());
        }
        return command;
    }

    private void processOptions(List<String> command, GitCheckoutOptions options) throws JavaGitException {
        if (options.optQ()) {
            command.add("-q");
        }
        if (options.optF()) {
            command.add("-f");
        }
        // --track and --no-track options are valid only with -b option
        Ref newBranch;
        if ((newBranch = options.getOptB()) != null) {
            if (options.optNoTrack() && options.optTrack()) {
                throw new JavaGitException(120, "Both --notrack and --track options are set");
            }
            if (options.optTrack()) {
                command.add("--track");
            }
            if (options.optNoTrack()) {
                command.add("--no-track");
            }
            command.add("-b");
            command.add(newBranch.getName());
        }
        if (options.optL()) {
            command.add("-l");
        }
        if (options.optM()) {
            command.add("-m");
        }
    }

}
