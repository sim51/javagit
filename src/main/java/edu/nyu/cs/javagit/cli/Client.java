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
import edu.nyu.cs.javagit.cli.add.IGitAdd;
import edu.nyu.cs.javagit.cli.branch.GitBranch;
import edu.nyu.cs.javagit.cli.branch.IGitBranch;
import edu.nyu.cs.javagit.cli.checkout.GitCheckout;
import edu.nyu.cs.javagit.cli.checkout.IGitCheckout;
import edu.nyu.cs.javagit.cli.clone.GitClone;
import edu.nyu.cs.javagit.cli.clone.IGitClone;
import edu.nyu.cs.javagit.cli.commit.GitCommit;
import edu.nyu.cs.javagit.cli.commit.IGitCommit;
import edu.nyu.cs.javagit.cli.diff.GitDiff;
import edu.nyu.cs.javagit.cli.diff.IGitDiff;
import edu.nyu.cs.javagit.cli.grep.GitGrep;
import edu.nyu.cs.javagit.cli.grep.IGitGrep;
import edu.nyu.cs.javagit.cli.init.GitInit;
import edu.nyu.cs.javagit.cli.init.IGitInit;
import edu.nyu.cs.javagit.cli.log.GitLog;
import edu.nyu.cs.javagit.cli.log.IGitLog;
import edu.nyu.cs.javagit.cli.mv.GitMv;
import edu.nyu.cs.javagit.cli.mv.IGitMv;
import edu.nyu.cs.javagit.cli.reset.GitReset;
import edu.nyu.cs.javagit.cli.reset.IGitReset;
import edu.nyu.cs.javagit.cli.revert.GitRevert;
import edu.nyu.cs.javagit.cli.revert.IGitRevert;
import edu.nyu.cs.javagit.cli.rm.GitRm;
import edu.nyu.cs.javagit.cli.rm.IGitRm;
import edu.nyu.cs.javagit.cli.show.GitShow;
import edu.nyu.cs.javagit.cli.show.IGitShow;
import edu.nyu.cs.javagit.cli.status.GitStatus;
import edu.nyu.cs.javagit.cli.status.IGitStatus;

/**
 * Command-line implementation of the <code>IClient</code> interface.
 */
public class Client implements IClient {

    public IGitAdd getGitAddInstance() {
        return new GitAdd();
    }

    public IGitCommit getGitCommitInstance() {
        return new GitCommit();
    }

    public IGitDiff getGitDiffInstance() {
        return new GitDiff();
    }

    public IGitGrep getGitGrepInstance() {
        return new GitGrep();
    }

    public IGitLog getGitLogInstance() {
        return new GitLog();
    }

    public IGitMv getGitMvInstance() {
        return new GitMv();
    }

    public IGitReset getGitResetInstance() {
        return new GitReset();
    }

    public IGitRevert getGitRevertInstance() {
        return new GitRevert();
    }

    public IGitRm getGitRmInstance() {
        return new GitRm();
    }

    public IGitShow getGitShowInstance() {
        return new GitShow();
    }

    public IGitStatus getGitStatusInstance() {
        return new GitStatus();
    }

    public IGitBranch getGitBranchInstance() {
        return new GitBranch();
    }

    public IGitCheckout getGitCheckoutInstance() {
        return new GitCheckout();
    }

    public IGitInit getGitInitInstance() {
        return new GitInit();
    }

    public IGitClone getGitCloneInstance() {
        return new GitClone();
    }

}
