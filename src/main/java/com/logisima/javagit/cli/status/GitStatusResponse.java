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
package com.logisima.javagit.cli.status;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Response;
import com.logisima.javagit.object.GitFileSystemObject;
import com.logisima.javagit.object.GitFileSystemObject.Status;
import com.logisima.javagit.object.Ref;

/**
 * A response data object for &lt;git-status&gt; command
 */
public class GitStatusResponse extends Response {

    /**
     * List of new files that will be added next time &lt;git-commit&gt; is executed.
     */
    private final List<File>                        newFilesToCommit;

    /**
     * List of files that will be deleted next time &lt;git-commit&gt; is executed.
     */
    protected final List<File>                      deletedFilesToCommit;

    /**
     * List of files that are modified and will be committed to git repository next time &lt;git-commit&gt; is executed.
     */
    protected final List<File>                      modifiedFilesToCommit;

    /**
     * List of files that are in git-repository and have been deleted locally.
     */
    protected final List<File>                      deletedFilesNotUpdated;

    /**
     * List of files that are in git-repository and have been modified locally but not yet added to git by
     * &lt;git-add&gt; command.
     */
    protected final List<File>                      modifiedFilesNotUpdated;

    /**
     * List of files that are in git-repository and have been renamed to some other filename.
     */
    protected final List<File>                      renamedFilesToCommit;

    /**
     * List of new files that have been created locally and have not been added to repository by &lt;git-add&gt; command
     * yet.
     */
    protected final List<File>                      untrackedFiles;

    /**
     * List of files that have been renamed
     */
    protected final List<File>                      renamedFiles;

    /**
     * Name of the branch to which HEAD is pointing to.
     */
    protected Ref                                   branch;

    /**
     * <code>File</code> file representing repository
     */
    protected File                                  repository;

    /**
     * Mapping of file to its status
     */
    protected Map<File, GitFileSystemObject.Status> fileToStatus;

    /**
     * Constructor.
     * 
     * @param repositoryPath The repository path.
     */
    public GitStatusResponse(String repositoryPath) {
        this.repository = new File(repositoryPath);
        fileToStatus = new HashMap<File, GitFileSystemObject.Status>();
        newFilesToCommit = new ArrayList<File>();
        deletedFilesToCommit = new ArrayList<File>();
        modifiedFilesToCommit = new ArrayList<File>();
        deletedFilesNotUpdated = new ArrayList<File>();
        modifiedFilesNotUpdated = new ArrayList<File>();
        renamedFilesToCommit = new ArrayList<File>();
        untrackedFiles = new ArrayList<File>();
        renamedFiles = new ArrayList<File>();
    }

    /**
     * 
     * @param file <code>File</code> object
     * @return status of the file
     * @throws JavaGitException if file is not part of the repository
     */
    public Status getFileStatus(File file) throws JavaGitException {
        // get command argument file (relative to repository path); this will perform basic path check
        File relativeFile = GitFileSystemObject.getRelativePath(file, repository);

        File argumentFile = new File(repository.getPath() + File.separator + relativeFile.getPath());
        // first check the map
        if (fileToStatus.containsKey(argumentFile)) {
            return fileToStatus.get(argumentFile);
        }

        // default
        return Status.IN_REPOSITORY;
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
     * @return the repository
     */
    public File getRepository() {
        return repository;
    }

    /**
     * @param repository the repository to set
     */
    public void setRepository(File repository) {
        this.repository = repository;
    }

    /**
     * @return the fileToStatus
     */
    public Map<File, GitFileSystemObject.Status> getFileToStatus() {
        return fileToStatus;
    }

    /**
     * @param fileToStatus the fileToStatus to set
     */
    public void setFileToStatus(Map<File, GitFileSystemObject.Status> fileToStatus) {
        this.fileToStatus = fileToStatus;
    }

    /**
     * @return the newFilesToCommit
     */
    public List<File> getNewFilesToCommit() {
        return newFilesToCommit;
    }

    /**
     * @return the deletedFilesToCommit
     */
    public List<File> getDeletedFilesToCommit() {
        return deletedFilesToCommit;
    }

    /**
     * @return the modifiedFilesToCommit
     */
    public List<File> getModifiedFilesToCommit() {
        return modifiedFilesToCommit;
    }

    /**
     * @return the deletedFilesNotUpdated
     */
    public List<File> getDeletedFilesNotUpdated() {
        return deletedFilesNotUpdated;
    }

    /**
     * @return the modifiedFilesNotUpdated
     */
    public List<File> getModifiedFilesNotUpdated() {
        return modifiedFilesNotUpdated;
    }

    /**
     * @return the renamedFilesToCommit
     */
    public List<File> getRenamedFilesToCommit() {
        return renamedFilesToCommit;
    }

    /**
     * @return the untrackedFiles
     */
    public List<File> getUntrackedFiles() {
        return untrackedFiles;
    }

    /**
     * @return the renamedFiles
     */
    public List<File> getRenamedFiles() {
        return renamedFiles;
    }

}
