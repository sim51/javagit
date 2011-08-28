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
package com.logisima.javagit.cli.commit;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.cli.ICommandResponse;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.CheckUtilities;

/**
 * A response data object for the git-commit command.
 */
public class GitCommitResponse implements ICommandResponse {

    // The short hash name for the commit.
    protected Ref                      commitShortHashName;

    // The short comment for the commit.
    protected String                   commitShortComment = "";

    // Indicates how many files have changed in a commit.
    protected int                      filesChanged       = 0;

    // Indicates how many lines were inserted in a commit.
    protected int                      linesInserted      = 0;

    // Indicates how many lines were deleted in a commit.
    protected int                      linesDeleted       = 0;

    /**
     * The list of the files added to the repository in this commit. The file name is the relative path to the file from
     * the root of the repository tree.
     */
    protected List<AddedOrDeletedFile> addedFiles;

    /**
     * A list of new files that were copied from existing files already tracked in the repository. The file names are
     * relative paths to the files from the root of the repository tree.
     */
    protected List<CopiedOrMovedFile>  copiedFiles;

    /**
     * A list of the files deleted form the repository in this commit. The file name is the relative path to the file
     * from the root of the repository tree.
     */
    protected List<AddedOrDeletedFile> deletedFiles;

    /**
     * A list of files that were moved/renamed in this commit. The file name is the relative path to the file from the
     * root of the repository tree.
     */
    protected List<CopiedOrMovedFile>  renamedFiles;

    /**
     * Constructor.
     * 
     * @param shortHashName The short hash name
     * @param shortComment
     */
    public GitCommitResponse(Ref shortHashName, String shortComment) {
        this.commitShortHashName = shortHashName;
        this.commitShortComment = shortComment;

        addedFiles = new ArrayList<AddedOrDeletedFile>();
        copiedFiles = new ArrayList<CopiedOrMovedFile>();
        deletedFiles = new ArrayList<AddedOrDeletedFile>();
        renamedFiles = new ArrayList<CopiedOrMovedFile>();
    }

    // ~~~ GETTER

    public Ref getCommitShortHashName() {
        return commitShortHashName;
    }

    public String getCommitShortComment() {
        return commitShortComment;
    }

    public int getFilesChanged() {
        return filesChanged;
    }

    public int getLinesInserted() {
        return linesInserted;
    }

    public int getLinesDeleted() {
        return linesDeleted;
    }

    public List<AddedOrDeletedFile> getAddedFiles() {
        return addedFiles;
    }

    public List<CopiedOrMovedFile> getCopiedFiles() {
        return copiedFiles;
    }

    public List<AddedOrDeletedFile> getDeletedFiles() {
        return deletedFiles;
    }

    public List<CopiedOrMovedFile> getRenamedFiles() {
        return renamedFiles;
    }

    /**
     * Represents a file added to or deleted from the repository for a given commit.
     */
    public static class AddedOrDeletedFile {

        // The path to the file.
        private File   pathTofile;

        // The mode the file was added/deleted with.
        private String mode;

        /**
         * Constructor.
         * 
         * @param pathToFile The path to the file.
         * @param mode The mode the file was added/deleted with.
         */
        public AddedOrDeletedFile(File pathToFile, String mode) {
            this.pathTofile = pathToFile;
            this.mode = mode;
        }

        public boolean equals(Object o) {
            if (!(o instanceof AddedOrDeletedFile)) {
                return false;
            }

            AddedOrDeletedFile a = (AddedOrDeletedFile) o;

            if (!CheckUtilities.checkObjectsEqual(getPathTofile(), a.getPathTofile())) {
                return false;
            }

            if (!CheckUtilities.checkObjectsEqual(getMode(), a.getMode())) {
                return false;
            }

            return true;
        }

        /**
         * Gets the mode of the added/deleted file.
         * 
         * @return The mode of the added/deleted file.
         */
        public String getMode() {
            return mode;
        }

        /**
         * Gets the path to the file.
         * 
         * @return The path to the file.
         */
        public File getPathTofile() {
            return pathTofile;
        }

        public int hashCode() {
            return pathTofile.hashCode() + mode.hashCode();
        }
    }

    /**
     * Represents a file that was copied from an existing file already tracked in the repository or a tracked file that
     * was moved from one name/place to another.
     */
    public static class CopiedOrMovedFile {

        // The path to the file that is the source of the copied/moved file.
        private File sourceFilePath;

        // The path to the new file/location.
        private File destinationFilePath;

        // The percentage. (not sure how to read this yet, -- jhl388 2008.06.15)
        private int  percentage;

        /**
         * Constructor.
         * 
         * @param sourceFilePath The path to the source file.
         * @param destinationFilePath The path to the destination file.
         * @param percentage The percentage.
         */
        public CopiedOrMovedFile(File sourceFilePath, File destinationFilePath, int percentage) {
            this.sourceFilePath = sourceFilePath;
            this.destinationFilePath = destinationFilePath;
            this.percentage = percentage;
        }

        public boolean equals(Object o) {
            if (!(o instanceof CopiedOrMovedFile)) {
                return false;
            }

            CopiedOrMovedFile c = (CopiedOrMovedFile) o;

            if (!CheckUtilities.checkObjectsEqual(getSourceFilePath(), c.getSourceFilePath())) {
                return false;
            }

            if (!CheckUtilities.checkObjectsEqual(getDestinationFilePath(), c.getDestinationFilePath())) {
                return false;
            }

            if (getPercentage() != c.getPercentage()) {
                return false;
            }

            return true;
        }

        /**
         * Gets the path to the destination file.
         * 
         * @return The path to the destination file.
         */
        public File getDestinationFilePath() {
            return destinationFilePath;
        }

        /**
         * Gets the percentage.
         * 
         * @return The percentage.
         */
        public int getPercentage() {
            return percentage;
        }

        /**
         * Gets the path to the source file.
         * 
         * @return The path to the source file.
         */
        public File getSourceFilePath() {
            return sourceFilePath;
        }

        public int hashCode() {
            return sourceFilePath.hashCode() + destinationFilePath.hashCode() + percentage;
        }
    }

}
