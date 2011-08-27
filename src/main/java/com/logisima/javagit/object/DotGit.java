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
package com.logisima.javagit.object;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.branch.GitBranch;
import com.logisima.javagit.cli.branch.GitBranchOptions;
import com.logisima.javagit.cli.branch.GitBranchResponse;
import com.logisima.javagit.cli.log.GitLog;
import com.logisima.javagit.cli.log.GitLogOptions;
import com.logisima.javagit.utilities.CheckUtilities;

/**
 * The <code>DotGit</code> represents the .git directory.
 */
public final class DotGit {

    // This guy's a per-repository singleton, so we need a static place to store our instances.
    private static final Map<String, DotGit> INSTANCES = new HashMap<String, DotGit>();

    // The directory that contains the .git in question.
    private final File                       path;

    /*
     * The canonical pathname from this file. Store this here so that we don't need to continually hit the filesystem to
     * resolve it.
     */
    private final String                     canonicalPath;

    /**
     * The constructor. Private because this singleton-ish (per each repository) class is only available via the
     * getInstance method.
     * 
     * @param path The path to the directory containing the .git file in question.
     */
    private DotGit(File path, String canonicalPath) {
        // TODO (rs2705): Ensure that these arguments are valid (not null, not empty)
        this.path = path;
        this.canonicalPath = canonicalPath;
    }

    /**
     * Checks if there is a DotGit instance for a given path
     * 
     * @param path <code>File</code> object representing the path to the repository.
     * @return true if exits, false otherwise;
     */
    public static synchronized boolean existsInstance(File path) {
        String canonicalPath = "";

        try {
            canonicalPath = path.getCanonicalPath();
        } catch (IOException e) {
            // obviously, the answer is NO
            return false;
        }

        return INSTANCES.containsKey(canonicalPath);
    }

    /**
     * Static factory method for retrieving an instance of this class.
     * 
     * @param path <code>File</code> object representing the path to the repository.
     * @return The <code>DotGit</code> instance for this path
     */
    public static synchronized DotGit getInstance(File path) {
        DotGit dotGit;

        // TODO (rs2705): make sure that path is valid

        /*
         * We want to make sure we're dealing with the canonical path here, since there are multiple ways to refer to
         * the same dir with different strings.
         */
        String canonicalPath = "";

        try {
            canonicalPath = path.getCanonicalPath();
        } catch (Exception e) {
            /*
             * TODO (rs2705): Figure out which exception to throw here, and throw it - or should we simply let it
             * propogate up as-is?
             */

            // Temporary placeholder
            return null;
        }

        if (!(INSTANCES.containsKey(canonicalPath))) {
            dotGit = new DotGit(path, canonicalPath);
            INSTANCES.put(canonicalPath, dotGit);
        }
        else {
            dotGit = INSTANCES.get(canonicalPath);
        }

        return dotGit;
    }

    /**
     * Convenience method for retrieving an instance of the class using a <code>String</code> instead of a
     * <code>File</code>.
     * 
     * @param path <code>String</code> object representing the path to the repository.
     * @return The <code>DotGit</code> instance for this path
     */
    public static DotGit getInstance(String path) {
        // TODO (rs2705): make sure that path is valid
        return getInstance(new File(path));
    }

    /**
     * Since instances of this class are singletons, don't allow cloning.
     * 
     * @return None - always throws exception
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Clones the repository into new location
     * 
     * @param gitFrom The .git being cloned
     */
    public void gitClone(DotGit gitFrom) {
    }

    /**
     * Creates a new branch
     * 
     * @param name The name of the branch to create
     * 
     * @return The new branch
     */
    public Ref createBranch(String name) throws IOException, JavaGitException {
        Ref newBranch = Ref.createBranchRef(name);
        GitBranch gitBranch = new GitBranch();
        gitBranch.createBranch(path, new GitBranchOptions(), newBranch);
        return newBranch;
    }

    /**
     * Deletes a branch
     * 
     * @param branch The branch to delete
     * @param forceDelete True if force delete option -D should be used, false if -d should be used.
     * @throws IOException Thrown in case of I/O operation failure
     * @throws JavaGitException Thrown when there is an error executing git-branch.
     */
    public void deleteBranch(Ref branch, boolean forceDelete) throws IOException, JavaGitException {
        GitBranch gitBranch = new GitBranch();
        gitBranch.deleteBranch(path, forceDelete, false, branch);
        branch = null;
    }

    /**
     * Renames a branch
     * 
     * @param branchFrom The branch to rename
     * @param nameTo New branch name
     * @param forceRename True if force rename option -M should be used. False if -m should be used.
     * @return new <code>Ref</code> instance
     * @throws IOException Thrown in case of I/O operation failure
     * @throws JavaGitException Thrown when there is an error executing git-branch.
     */
    public Ref renameBranch(Ref branchFrom, String nameTo, boolean forceRename) throws IOException, JavaGitException {
        Ref newBranch = Ref.createBranchRef(nameTo);
        GitBranch gitBranch = new GitBranch();
        gitBranch.renameBranch(path, forceRename, branchFrom, newBranch);
        return newBranch;
    }

    /**
     * Gets a list of the branches in the repository.
     * 
     * @return The branches in the repository.
     */
    public Iterator<Ref> getBranches() throws IOException, JavaGitException {
        GitBranch gitBranch = new GitBranch();
        GitBranchOptions options = new GitBranchOptions();
        GitBranchResponse response = gitBranch.branch(path, options);
        return response.getBranchListIterator();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof DotGit)) {
            return false;
        }

        DotGit dotGit = (DotGit) obj;
        return CheckUtilities.checkObjectsEqual(canonicalPath, dotGit.canonicalPath);
    }

    /**
     * Gets the repository path represented by this repository object.
     * 
     * @return The path to the repository
     */
    public File getPath() {
        return path;
    }

    /**
     * Gets the working tree for this git repository
     * 
     * @return The working tree
     */
    public WorkingTree getWorkingTree() {
        return WorkingTree.getInstance(path);
    }

    @Override
    public int hashCode() {
        return canonicalPath.hashCode();
    }

    /**
     * Initializes Git repository
     */
    public void init() {
        // TODO (ma1683): Implement this method
        // GitInit.init();
    }

    /**
     * Merges development histories together
     * 
     * @param otherGit The other .git to merge with
     * @param m The merge options (strategy, message, etc)
     */
    public void merge(DotGit otherGit, MergeOptions m) {
    }

    /**
     * Merges development histories together
     * 
     * @param gitList The list of .git to merge with; implies octopus
     * @param m The merge options (strategy, message, etc)
     */
    public void merge(List<DotGit> gitList, MergeOptions m) {
    }

    /**
     * Fetch from git repository and merge with the current one
     * 
     * @param gitFrom The git repository being fetched from
     */
    public void pull(DotGit gitFrom) {
    }

    /**
     * Updates the remote git repository with the content of the current one
     * 
     * @param gitTo The .git being updated
     */
    public void push(DotGit gitTo) {
    }

    /**
     * Show commit logs
     * 
     * @return List of commits for the working directory
     * @throws IOException
     * @throws JavaGitException
     */
    public List<com.logisima.javagit.cli.log.GitLogResponse.Commit> getLog() throws JavaGitException, IOException {
        GitLog gitLog = new GitLog();
        return gitLog.log(this.getPath(), new GitLogOptions());
    }

    /**
     * 
     * @param options Options to the git log command
     * @return List of commits for the working directory
     * @throws JavaGitException
     * @throws IOException
     */
    public List<com.logisima.javagit.cli.log.GitLogResponse.Commit> getLog(GitLogOptions options)
            throws JavaGitException, IOException {
        GitLog gitLog = new GitLog();
        return gitLog.log(this.getPath(), options);
    }

}
