/**
 *  This file is part of LogiSima (http://www.logisima.com).
 *
 *  JavGit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavGit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with LogiSima-Common.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli.client;

import com.logisima.javagit.cli.add.GitAdd;
import com.logisima.javagit.cli.branch.GitBranch;
import com.logisima.javagit.cli.checkout.GitCheckout;
import com.logisima.javagit.cli.clone.GitClone;
import com.logisima.javagit.cli.commit.GitCommit;
import com.logisima.javagit.cli.init.GitInit;
import com.logisima.javagit.cli.log.GitLog;
import com.logisima.javagit.cli.mv.GitMv;
import com.logisima.javagit.cli.reset.GitReset;
import com.logisima.javagit.cli.rm.GitRm;
import com.logisima.javagit.cli.show.GitShow;
import com.logisima.javagit.cli.status.GitStatus;

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
