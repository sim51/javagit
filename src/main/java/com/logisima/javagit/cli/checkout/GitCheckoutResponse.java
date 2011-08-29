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
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.cli.Response;
import com.logisima.javagit.object.Ref;

/**
 * Response data object for &lt;git-checkout&gt; command.
 */
public class GitCheckoutResponse extends Response {

    /**
     * List of files that have been modified but not committed.
     */
    protected final List<File> modifiedFiles;
    /**
     * List of files added by &lt;git-add&gt; but not committed.
     */
    protected final List<File> addedFiles;
    /**
     * List of files deleted but not committed.
     */
    protected final List<File> deletedFiles;

    /**
     * The new branch that is created and switched to by -b option provided by &lt;git-checkout&gt;.
     */
    protected Ref              newBranch;
    /**
     * The name of the branch to which &lt;git-checkout&gt; switches.
     */
    protected Ref              branch;

    /**
     * Constructor
     */
    protected GitCheckoutResponse() {
        modifiedFiles = new ArrayList<File>();
        addedFiles = new ArrayList<File>();
        deletedFiles = new ArrayList<File>();
    }

    /**
     * @return the newBranch
     */
    public Ref getNewBranch() {
        return newBranch;
    }

    /**
     * @param newBranch the newBranch to set
     */
    public void setNewBranch(Ref newBranch) {
        this.newBranch = newBranch;
    }

    /**
     * @return the branch
     */
    public Ref getBranch() {
        return branch;
    }

    /**
     * @param branch the branch to set
     */
    public void setBranch(Ref branch) {
        this.branch = branch;
    }

    /**
     * @return the modifiedFiles
     */
    public List<File> getModifiedFiles() {
        return modifiedFiles;
    }

    /**
     * @return the addedFiles
     */
    public List<File> getAddedFiles() {
        return addedFiles;
    }

    /**
     * @return the deletedFiles
     */
    public List<File> getDeletedFiles() {
        return deletedFiles;
    }

}
