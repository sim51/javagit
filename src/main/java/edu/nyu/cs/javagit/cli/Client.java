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
 * Command-line implementation of the <code>IClient</code> interface.
 */
public class Client implements IClient {

    public GitAdd getGitAddInstance() {
        return new GitAdd();
    }

    public GitCommit getGitCommitInstance() {
        return new GitCommit();
    }

    public GitLog getGitLogInstance() {
        return new GitLog();
    }

    public GitMv getGitMvInstance() {
        return new GitMv();
    }

    public GitReset getGitResetInstance() {
        return new GitReset();
    }

    public GitRm getGitRmInstance() {
        return new GitRm();
    }

    public GitShow getGitShowInstance() {
        return new GitShow();
    }

    public GitStatus getGitStatusInstance() {
        return new GitStatus();
    }

    public GitBranch getGitBranchInstance() {
        return new GitBranch();
    }

    public GitCheckout getGitCheckoutInstance() {
        return new GitCheckout();
    }

    public GitInit getGitInitInstance() {
        return new GitInit();
    }

    public GitClone getGitCloneInstance() {
        return new GitClone();
    }

}
