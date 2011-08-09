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
package edu.nyu.cs.javagit.cli;

import edu.nyu.cs.javagit.cli.add.GitAdd;
import edu.nyu.cs.javagit.cli.branch.GitBranch;
import edu.nyu.cs.javagit.cli.checkout.GitCheckout;
import edu.nyu.cs.javagit.cli.clone.GitClone;
import edu.nyu.cs.javagit.cli.commit.GitCommit;
import edu.nyu.cs.javagit.cli.init.GitInit;
import edu.nyu.cs.javagit.cli.log.GitLog;
import edu.nyu.cs.javagit.cli.mv.GitMv;
import edu.nyu.cs.javagit.cli.reset.GitReset;
import edu.nyu.cs.javagit.cli.rm.GitRm;
import edu.nyu.cs.javagit.cli.show.GitShow;
import edu.nyu.cs.javagit.cli.status.GitStatus;

/**
 * An interface to represent a git client type, such as a command-line client.
 */
public interface IClient {

    /**
     * Gets an instance of <code>IGitAdd</code>.
     * 
     * @return An instance of <code>IGitAdd</code>.
     */
    public GitAdd getGitAddInstance();

    /**
     * Gets an instance of <code>IGitCommit</code>.
     * 
     * @return An instance of <code>IGitCommit</code>.
     */
    public GitCommit getGitCommitInstance();

    /**
     * Gets an instance of <code>IGitLog</code>.
     * 
     * @return An instance of <code>IGitLog</code>.
     */
    public GitLog getGitLogInstance();

    /**
     * Gets an instance of <code>IGitMv</code>.
     * 
     * @return An instance of <code>IGitMv</code>.
     */
    public GitMv getGitMvInstance();

    /**
     * Gets an instance if <code>IGitReset</code>.
     * 
     * @return An instance of <code>IGitReset</code>
     */
    public GitReset getGitResetInstance();

    /**
     * Gets an instance of <code>IGitRm</code>.
     * 
     * @return An instance of <code>IGitRm</code>.
     */
    public GitRm getGitRmInstance();

    /**
     * Gets an instance of <code>IGitShow</code>.
     * 
     * @return An instance of <code>IGitShow</code>.
     */
    public GitShow getGitShowInstance();

    /**
     * Gets an instance of <code>IGitStatus</code>.
     * 
     * @return An instance of <code>IGitStatus</code>.
     */
    public GitStatus getGitStatusInstance();

    /**
     * Gets an instance of <code>IGitBranch</code>
     * 
     * @return An instance of <code>IGitBranch</code>
     */
    public GitBranch getGitBranchInstance();

    /**
     * Gets an instance of <code>IGitCheckout</code>
     * 
     * @return An instance of <code>IGitCheckout</code>
     */
    public GitCheckout getGitCheckoutInstance();

    /**
     * Gets an instance of <code>IGitInit</code>
     * 
     * @return An instance of <code>IGitInit</code>
     */
    public GitInit getGitInitInstance();

    /**
     * Gets an instance of <code>IGitClone</code>
     * 
     * @return An instance of <code>IGitClone</code>
     */
    public GitClone getGitCloneInstance();

}
