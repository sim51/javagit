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
package com.logisima.javagit.cli.checkout;

import java.io.File;

import com.logisima.javagit.object.Ref;

/**
 * This class implements <code>GitCheckoutResponse</code> by setting values in <code>GitCheckoutResponse</code>.
 */
public class GitCheckoutResponseImpl extends GitCheckoutResponse {

    /**
     * Sets the new branch name that is created by &lt;git-checkout&gt using -b option
     * 
     * @param newBranch Name of the new branch created
     */
    public void setNewBranch(Ref newBranch) {
        this.newBranch = newBranch;
    }

    /**
     * Sets the branch to the branch, to which the &lt;git-checkout&gt switched the repository to. This branch should
     * already be existing in the repository. To create a new branch and switch to it, use the -b option while running
     * &lt;git-checkout&gt.
     * 
     * @param branch
     */
    public void setBranch(Ref branch) {
        this.branch = branch;
    }

    /**
     * Adds the modified file to the list of modifiedFiles. When a file is modified locally but has not been committed
     * to the repository and if we try to switch the branch to another branch, the &lt;git-checkout&gt fails and outputs
     * the list of modified files that are not yet committed unless -f option is used by &lt;git-checkout&gt.
     * 
     * @param file
     */
    public void addModifiedFile(File file) {
        modifiedFiles.add(file);
    }

    /**
     * Adds the newly added file to the list of addedFiles. A newly added file is the one that is added by
     * &lt;git-add&gt; command but had not been committed.
     * 
     * @param file
     */
    public void addAddedFile(File file) {
        addedFiles.add(file);
    }

    /**
     * Adds the locally deleted file to the list of deletedFiles. A locally deleted file is one that has been removed
     * but has not been removed from repository using &lt;git-rm&gt; command.
     * 
     * @param file
     */
    public void addDeletedFile(File file) {
        deletedFiles.add(file);
    }

}
