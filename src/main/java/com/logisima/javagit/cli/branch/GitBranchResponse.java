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

import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.cli.Response;
import com.logisima.javagit.object.BranchRecord;
import com.logisima.javagit.object.Ref;

/**
 * A response data object for the git-branch command.
 */
public class GitBranchResponse extends Response {

    /**
     * An enumeration of the types of response. In normal case a list of branches, otherwise some message such as
     * "Deleted branch".
     */
    public static enum responseType {
        BRANCH_LIST, MESSAGE, EMPTY
    }

    /**
     * The list of branches in the response of git-branch.
     */
    protected List<Ref>          branchList;

    /**
     * The list of branch records, in response of git-branch with verbose option.
     */
    protected List<BranchRecord> listOfBranchRecord;

    /**
     * String Buffer to store the message after execution of git-branch command.
     */
    protected StringBuffer       messages = new StringBuffer();

    /**
     * Variable to store the current branch.
     */
    protected Ref                currentBranch;

    /**
     * The type of this response.
     */
    protected responseType       responseType;

    /**
     * Constructor.
     */
    public GitBranchResponse() {
        branchList = new ArrayList<Ref>();
        listOfBranchRecord = new ArrayList<BranchRecord>();
    }

    /**
     * @return the branchList
     */
    public List<Ref> getBranchList() {
        return branchList;
    }

    /**
     * @param branchList the branchList to set
     */
    public void setBranchList(List<Ref> branchList) {
        this.branchList = branchList;
    }

    /**
     * @return the listOfBranchRecord
     */
    public List<BranchRecord> getListOfBranchRecord() {
        return listOfBranchRecord;
    }

    /**
     * @param listOfBranchRecord the listOfBranchRecord to set
     */
    public void setListOfBranchRecord(List<BranchRecord> listOfBranchRecord) {
        this.listOfBranchRecord = listOfBranchRecord;
    }

    /**
     * @return the messages
     */
    public StringBuffer getMessages() {
        return messages;
    }

    /**
     * @param messages the messages to set
     */
    public void setMessages(StringBuffer messages) {
        this.messages = messages;
    }

    /**
     * @return the currentBranch
     */
    public Ref getCurrentBranch() {
        return currentBranch;
    }

    /**
     * @param currentBranch the currentBranch to set
     */
    public void setCurrentBranch(Ref currentBranch) {
        this.currentBranch = currentBranch;
    }

    /**
     * @return the responseType
     */
    public responseType getResponseType() {
        return responseType;
    }

    /**
     * @param responseType the responseType to set
     */
    public void setResponseType(responseType responseType) {
        this.responseType = responseType;
    }

}
