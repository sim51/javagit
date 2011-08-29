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
package com.logisima.javagit.cli.log;

import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.cli.Response;

/**
 * A response data object for the git log command.
 */
public class GitLogResponse extends Response {

    private List<Commit>     commitList;

    // local variables used to store parsed data until it is pushed into the object
    private String           sha;
    private List<String>     mergeDetails;
    private String           dateString;
    private String           message;
    private List<CommitFile> files;
    private String           author;

    /**
     * Constructor.
     */
    public GitLogResponse() {
        super();
        commitList = new ArrayList<Commit>();
    }

    /**
     * @return the commitList
     */
    public List<Commit> getCommitList() {
        return commitList;
    }

    /**
     * @param commitList the commitList to set
     */
    public void setCommitList(List<Commit> commitList) {
        this.commitList = commitList;
    }

    /**
     * @return the sha
     */
    public String getSha() {
        return sha;
    }

    /**
     * @param sha the sha to set
     */
    public void setSha(String sha) {
        this.sha = sha;
    }

    /**
     * @return the mergeDetails
     */
    public List<String> getMergeDetails() {
        return mergeDetails;
    }

    /**
     * @param mergeDetails the mergeDetails to set
     */
    public void setMergeDetails(List<String> mergeDetails) {
        this.mergeDetails = mergeDetails;
    }

    /**
     * @return the dateString
     */
    public String getDateString() {
        return dateString;
    }

    /**
     * @param dateString the dateString to set
     */
    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the files
     */
    public List<CommitFile> getFiles() {
        return files;
    }

    /**
     * @param files the files to set
     */
    public void setFiles(List<CommitFile> files) {
        this.files = files;
    }

    /**
     * @return the author
     */
    public String getAuthor() {
        return author;
    }

    /**
     * @param author the author to set
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 
     * A data structure which holds information about each commit.
     * 
     */
    public static class Commit {

        String           sha           = null;
        List<String>     mergeDetails  = null;
        String           author        = null;
        String           date          = null;

        String           message       = null;

        // Additional Commit details
        List<CommitFile> files         = null;

        int              filesChanged  = 0;
        int              linesInserted = 0;
        int              linesDeleted  = 0;

        /**
         * Constructor for creating a commit data structure.
         * 
         * @param sha The SHA hash for a particular commit instance.
         * @param mergeDetails The Merge details for a particular commit instance. Pass null is commit is not a merge
         * @param author The Author for a particular commit instance.
         * @param date The Date of a particular commit instance.
         * @param message The Message for a particular commit instance.
         * @param files The list of files affected by a particular commit instance.
         */
        public Commit(String sha, List<String> mergeDetails, String author, String date, String message,
                List<CommitFile> files) {
            super();
            this.sha = sha;
            this.mergeDetails = mergeDetails;
            this.author = author;
            this.date = date;
            this.message = message;
            this.files = files;
            setLinesInsertionsDeletions();
            setFilesChanged();
        }

        /**
         * 
         * @return This returns the SHA for each commit.
         */
        public String getSha() {
            return sha;
        }

        /**
         * 
         * @return This returns the merge details for each commit. If the commit was not a merge it returns null.
         */
        public List<String> getMergeDetails() {
            return mergeDetails;
        }

        /**
         * 
         * @return This return the name of the author of the commit.
         */
        public String getAuthor() {
            return author;
        }

        /**
         * 
         * @return This return the Date object for a particular commmit.
         */
        public String getDateString() {
            return date;
        }

        /**
         * 
         * @return This returns the message of a commit.
         */
        public String getMessage() {
            return message;
        }

        /**
         * This returns the list of files affected by a particular commit.
         */
        public List<CommitFile> getFiles() {
            return files;
        }

        /**
         * 
         * @param sha sets the SHA for a commit.
         */
        public void setSha(String sha) {
            this.sha = sha;
        }

        /**
         * 
         * @param mergeDetails This set the merge details for a particular commit.
         */
        public void setMergeDetails(List<String> mergeDetails) {
            this.mergeDetails = mergeDetails;
        }

        /**
         * 
         * @param author This sets the Author of a particular commit.
         */
        public void setAuthor(String author) {
            this.author = author;
        }

        /**
         * 
         * @param date This sets the Date object for a particular commit.
         */
        public void setDateString(String date) {
            this.date = date;
        }

        /**
         * 
         * @param message This sets the Message for a particular commit.
         */
        public void setMessage(String message) {
            this.message = message;
        }

        /**
         * 
         * @param files This sets the list of files affected by a particular commit.
         */
        public void setFiles(List<CommitFile> files) {
            this.files = files;
        }

        /**
         * This methods calculated the total number of files affected by a particular commit.
         */
        void setFilesChanged() {
            if (this.files != null) {
                this.filesChanged = this.files.size();
            }
            else {
                this.filesChanged = 0;
            }

        }

        /**
         * This calculated the Total number of lines Added and Deleted for a particular commit.
         */
        void setLinesInsertionsDeletions() {
            if (this.files != null) {
                for (int i = 0; i < this.files.size(); i++) {
                    this.linesInserted += files.get(i).linesAdded;
                    this.linesDeleted += files.get(i).linesDeleted;
                }

            }
            else {
                this.linesInserted = 0;
                this.linesDeleted = 0;
            }
        }

        /**
         * 
         * @return The number of lines deleted for a paticular commit.
         */
        public int getLinesDeleted() {
            return this.linesDeleted;
        }

        /**
         * 
         * @return @return The number of lines inserted for a particular commit.
         */
        public int getLinesInserted() {
            return this.linesInserted;
        }

        /**
         * 
         * @return The number of files changed in a particular commit.
         */
        public int getFilesChanged() {
            return this.filesChanged;
        }
    }

    /**
     * 
     * This class hold information about a file affected by a commit
     * 
     */
    public static class CommitFile {

        String filename     = null;
        int    linesAdded   = 0;
        int    linesDeleted = 0;

        public CommitFile(String name, int linesAdded, int linesDeleted) {
            super();
            this.filename = name;
            this.linesAdded = linesAdded;
            this.linesDeleted = linesDeleted;
        }

        public String getName() {
            return filename;
        }

        public int getLinesAdded() {
            return linesAdded;
        }

        public int getLinesDeleted() {
            return linesDeleted;
        }
    }

}
