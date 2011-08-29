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
