package com.logisima.javagit.object;

import com.logisima.javagit.utilities.CheckUtilities;

/**
 * A record containing branch, its head SHA1, and the comment of the commit on head. A list of this object is returned
 * as a part of <code>BranchResponse</code> object when verbose option is set.
 */
public class BranchRecord {

    private Ref     branch;

    // The SHA Refs of a branch in the response of git-branch with -v option.
    private Ref     sha1;

    // String Buffer to store the comment after execution of git-branch command with -v option.
    private String  comment;

    // Variable to store the current branch.
    private boolean isCurrentBranch;

    public BranchRecord(Ref branch, Ref sha1, String comment, boolean isCurrentBranch) {
        this.branch = branch;
        this.sha1 = sha1;
        this.comment = comment;
        this.isCurrentBranch = isCurrentBranch;
    }

    public boolean equals(Object o) {
        if (!(o instanceof BranchRecord)) {
            return false;
        }

        BranchRecord c = (BranchRecord) o;

        if (!CheckUtilities.checkObjectsEqual(getBranch(), c.getBranch())) {
            return false;
        }

        if (!CheckUtilities.checkObjectsEqual(getSha1(), c.getSha1())) {

            return false;
        }

        if (!CheckUtilities.checkObjectsEqual(getComment(), c.getComment())) {
            return false;
        }

        if (isCurrentBranch() != c.isCurrentBranch()) {
            return false;
        }

        return true;
    }

    /**
     * Gets the branch from the record.
     * 
     * @return The branch from the record.
     */
    public Ref getBranch() {
        return branch;
    }

    /**
     * Gets the SHA1 from the record.
     * 
     * @return The SHA1 from the record.
     */
    public Ref getSha1() {
        return sha1;
    }

    /**
     * Gets the comment of the last commit on a branch or the last commit on the branch it has originated from.
     * Displayed when git-branch is run with -v option.
     * 
     * @return The comment of the recent commit on a branch.
     */
    public String getComment() {
        return comment;
    }

    public int hashCode() {
        return branch.hashCode() + sha1.hashCode() + comment.hashCode();
    }

    /**
     * Gets the current branch from the list of branches displayed by git-branch operation.
     * 
     * @return The current branch from the list of branches displayed by git-branch operation.
     */
    public boolean isCurrentBranch() {
        return isCurrentBranch;
    }
}