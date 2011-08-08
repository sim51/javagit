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
package edu.nyu.cs.javagit.client;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.api.commands.GitShowOptions;
import edu.nyu.cs.javagit.api.commands.GitShowResponse;

/**
 * An interface to represent the git-show command.
 */
public interface IGitShow {

    /**
     * 
     * @param repositoryPath A <code>File</code> instance for the path to the repository root (the parent directory of
     *        the .git directory) or a sub-directory in the working tree of the repository to move/rename against. This
     *        argument must represent the absolute path to the desired directory as returned by the
     *        <code>File.getPath()</code> method. If null is passed, a <code>NullPointerException</code> will be thrown.
     * 
     * @param options Specify option used, see <code>GitShowOptions</code>
     * @param path The file we want to show
     * @param revision The revision we want
     * @return An object that represent a git show response. See <code>GitShowResponse</code>
     * @throws JavaGitException Thrown when there is an error executing git log.
     * @throws IOException Thrown when there is an error executing git log.
     */
    public GitShowResponse show(File repositoryPath, GitShowOptions options, File path, Ref revision)
            throws JavaGitException, IOException;

    /**
     * 
     * @param repositoryPath A <code>File</code> instance for the path to the repository root (the parent directory of
     *        the .git directory) or a sub-directory in the working tree of the repository to move/rename against. This
     *        argument must represent the absolute path to the desired directory as returned by the
     *        <code>File.getPath()</code> method. If null is passed, a <code>NullPointerException</code> will be thrown.
     * 
     * @param path The file we want to show
     * @param revision The revision we want
     * @return An object that represent a git show response. See <code>GitShowResponse</code>
     * @throws JavaGitException Thrown when there is an error executing git log.
     * @throws IOException Thrown when there is an error executing git log.
     */
    public GitShowResponse show(File repositoryPath, File path, Ref revision) throws JavaGitException, IOException;
}
