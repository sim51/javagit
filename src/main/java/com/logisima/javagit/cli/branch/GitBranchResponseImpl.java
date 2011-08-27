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
package com.logisima.javagit.cli.branch;

import com.logisima.javagit.object.Ref;

/**
 * Implementation of a <code>GitBranchResponse</code>. This class adds functionality to set values in a
 * <code>GitBranchResponse</code>.
 */
public class GitBranchResponseImpl extends GitBranchResponse {

    /**
     * Constructor.
     */
    public GitBranchResponseImpl() {
        super();
    }

    /**
     * Add the branch displayed by git-branch command into the list of branches.
     * 
     * @return true after the file gets added.
     */
    public boolean addIntoBranchList(Ref branchName) {
        return branchList.add(branchName);
    }

    /**
     * Add the record displayed by git-branch command with -v option into the list of records.
     * 
     * @return True after the record gets added.
     */
    public boolean addIntoListOfBranchRecord(BranchRecord record) {
        return listOfBranchRecord.add(record);
    }

    /**
     * Sets a message about the git-branch operation that was run.
     * 
     * @param message A message about the git-branch operation that was run.
     */
    public void addMessages(String message) {
        messages.append(message);
    }

    /**
     * Sets the current branch from the list of branches displayed by git-branch operation.
     * 
     * @param currentBranch The current branch from the list of branches displayed by git-branch operation.
     */
    public void setCurrentBranch(Ref currentBranch) {
        this.currentBranch = currentBranch;
    }

    /**
     * Sets the type of the response.
     * 
     * @param responseType The responseType to set to one of the three types.
     */
    public void setResponseType(responseType responseType) {
        this.responseType = responseType;
    }
}
