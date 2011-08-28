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
 *  along with JavGit. If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import com.logisima.javagit.cli.ClientManager;
import com.logisima.javagit.cli.IClient;
import com.logisima.javagit.cli.add.GitAdd;
import com.logisima.javagit.cli.add.GitAddOptions;
import com.logisima.javagit.cli.add.GitAddResponse;
import com.logisima.javagit.cli.branch.GitBranch;
import com.logisima.javagit.cli.branch.GitBranchOptions;
import com.logisima.javagit.cli.branch.GitBranchResponse;
import com.logisima.javagit.cli.checkout.GitCheckout;
import com.logisima.javagit.cli.checkout.GitCheckoutOptions;
import com.logisima.javagit.cli.checkout.GitCheckoutResponse;
import com.logisima.javagit.cli.clone.GitClone;
import com.logisima.javagit.cli.clone.GitCloneOptions;
import com.logisima.javagit.cli.clone.GitCloneResponse;
import com.logisima.javagit.cli.commit.GitCommit;
import com.logisima.javagit.cli.commit.GitCommitOptions;
import com.logisima.javagit.cli.commit.GitCommitResponse;
import com.logisima.javagit.cli.init.GitInitOptions;
import com.logisima.javagit.cli.init.GitInitResponse;
import com.logisima.javagit.cli.log.GitLog;
import com.logisima.javagit.cli.log.GitLogOptions;
import com.logisima.javagit.cli.log.GitLogResponse.Commit;
import com.logisima.javagit.cli.mv.GitMv;
import com.logisima.javagit.cli.mv.GitMvOptions;
import com.logisima.javagit.cli.mv.GitMvResponse;
import com.logisima.javagit.cli.reset.GitReset;
import com.logisima.javagit.cli.reset.GitResetOptions;
import com.logisima.javagit.cli.reset.GitResetResponse;
import com.logisima.javagit.cli.rm.GitRm;
import com.logisima.javagit.cli.rm.GitRmOptions;
import com.logisima.javagit.cli.rm.GitRmResponse;
import com.logisima.javagit.cli.show.GitShowOptions;
import com.logisima.javagit.cli.show.GitShowResponse;
import com.logisima.javagit.cli.status.GitStatus;
import com.logisima.javagit.cli.status.GitStatusOptions;
import com.logisima.javagit.cli.status.GitStatusResponse;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.object.Ref.RefType;
import com.logisima.javagit.utilities.CheckUtilities;

public class JavaGit {

    private static File repositoryPath;

    public JavaGit(File repositoryPath) throws JavaGitException {
        super();
        try {
            CheckUtilities.checkFileValidity(repositoryPath.getAbsoluteFile());
        } catch (JavaGitException e) {
            throw new JavaGitException(JavaGitException.REPOSITORY_ERROR, e.getMessage());
        }
        this.repositoryPath = repositoryPath;
    }

    public File getRepositoryPath() {
        return repositoryPath;
    }

    public void setRepositoryPath(File repositoryPath) {
        this.repositoryPath = repositoryPath;
    }

    // ~~~~ BEGIN GIT ADD

    /**
     * This command adds the current content of new or modified files to the index, thus staging that content for
     * inclusion in the next commit.
     */
    public GitAddResponse add(List<File> paths, GitAddOptions options) throws IOException, JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitAdd gitAdd = client.getGitAddInstance();
        return gitAdd.add(repositoryPath, paths, options);
    }

    /**
     * This command adds the current content of new or modified file to the index, thus staging that content for
     * inclusion in the next commit.
     */
    public GitAddResponse add(File file, GitAddOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitAdd gitAdd = client.getGitAddInstance();
        return gitAdd.add(repositoryPath, file, options);
    }

    // ~~~~ BEGIN GIT BRANCH

    /**
     * Perform git-branch with the specified options against the repository.
     */
    public GitBranchResponse branch(GitBranchOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(options, "options");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitBranch gitBranch = client.getGitBranchInstance();
        return gitBranch.branch(repositoryPath, options);
    }

    /**
     * This method deletes the specified branch using the -d command line option.
     */
    public GitBranchResponse deleteBranch(Ref branchName, boolean forceDelete, boolean remote) throws JavaGitException {
        CheckUtilities.checkNullArgument(branchName, "branch name");
        CheckUtilities.validateArgumentRefType(branchName, Ref.RefType.BRANCH, "branch name");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitBranch gitBranch = client.getGitBranchInstance();
        return gitBranch.deleteBranch(repositoryPath, forceDelete, remote, branchName);
    }

    /**
     * Creates a branch according to given option. Indicate to use the current working branch as the branch start point.
     */
    public GitBranchResponse createBranch(Ref branchName, GitBranchOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(repositoryPath, "repository path");
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(branchName, "branch name");
        CheckUtilities.validateArgumentRefType(branchName, Ref.RefType.BRANCH, "branch name");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitBranch gitBranch = client.getGitBranchInstance();
        return gitBranch.createBranch(repositoryPath, options, branchName);
    }

    // ~~~~ BEGIN GIT COMMIT

    /**
     * Commits staged changes into the specified repository. The specific files that are committed depends on the
     * options specified.
     */
    public GitCommitResponse commit(String message, GitCommitOptions options) throws IOException, JavaGitException {

        CheckUtilities.checkStringArgument(message, "message");
        CheckUtilities.checkNullArgument(options, "options");

        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.commit.GitCommit gitCommit = client.getGitCommitInstance();
        return gitCommit.commit(repositoryPath, options, message);
    }

    /**
     * Commits staged changes into the specified repository. The specific files that are committed depends on the
     * options and paths specified.
     */
    public GitCommitResponse commit(List<File> paths, String message, GitCommitOptions options) throws JavaGitException {
        CheckUtilities.checkStringArgument(message, "message");
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullListArgument(paths, "paths");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitCommit gitCommit = client.getGitCommitInstance();
        return gitCommit.commit(repositoryPath, options, message, paths);
    }

    // ~~~~ BEGIN GIT CHECKOUT

    /**
     * For checking out a branch or creating a new branch. The <new-branch> is the value of the option '-b' passed in
     * <code>GitCheckoutOptions</code> starting at <branch-name> provided as the last argument to the checkout method.
     **/
    public GitCheckoutResponse checkout(Ref branch, GitCheckoutOptions options) throws IOException, JavaGitException {
        CheckUtilities.validateArgumentRefType(branch, RefType.BRANCH, "Branch name");
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitCheckout gitCheckout = client.getGitCheckoutInstance();
        return gitCheckout.checkout(repositoryPath, options, branch);
    }

    // ~~~~ BEGIN GIT CLONE

    /**
     * Clones a git repository with specified options in default directory i.e. the directory name which the URL
     * contains.
     */
    public GitCloneResponse clone(URL repositoryUrl, GitCloneOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(repositoryUrl, "repository");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitClone gitClone = client.getGitCloneInstance();
        return gitClone.clone(repositoryPath, options, repositoryUrl);
    }

    // ~~~~ BEGIN GIT INIT

    /**
     * Git init.
     */
    public GitInitResponse init(GitInitOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.init.GitInit gitInit = client.getGitInitInstance();
        return gitInit.init(repositoryPath, options);
    }

    // ~~~~ BEGIN GIT LOG

    /**
     * Git log
     */
    public List<Commit> log(GitLogOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitLog gitLog = client.getGitLogInstance();
        return gitLog.log(repositoryPath, options);
    }

    /**
     * Git log files.
     */
    public List<Commit> log(List<File> files, GitLogOptions options) throws JavaGitException {
        CheckUtilities.checkNullListArgument(files, "files");
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitLog gitLog = client.getGitLogInstance();
        return gitLog.log(repositoryPath, options, files);
    }

    // ~~~~ BEGIN GIT MOVE

    /**
     * Moves the specified source file/symlink/directory to the destination file/symlink/directory. If destination is
     * non-existent then same as rename.
     */
    public GitMvResponse mv(File source, File destination, GitMvOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(source, "source");
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(destination, "destination");

        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.mv.GitMv gitMv = client.getGitMvInstance();
        return gitMv.mv(repositoryPath, options, source, destination);
    }

    /**
     * Moves the specified source files/symlinks/directories to the destination directory.
     */
    public GitMvResponse mv(List<File> sources, File destination, GitMvOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullListArgument(sources, "sources");
        CheckUtilities.checkDirectoryArgument(destination, "destination");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitMv gitMv = client.getGitMvInstance();
        return gitMv.mv(repositoryPath, options, sources, destination);
    }

    // ~~~~ BEGIN GIT REMOVE

    /**
     * Perform a reset on the repository.
     */
    public static GitResetResponse reset(GitResetOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(options, "options");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitReset gitReset = client.getGitResetInstance();
        return gitReset.gitReset(repositoryPath, options);
    }

    /**
     * Perform a reset on the repository.
     */
    public GitResetResponse reset(Ref commitName, List<File> files) throws JavaGitException {
        CheckUtilities.checkNullArgument(commitName, "options");
        CheckUtilities.checkNullListArgument(files, "files");
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitReset gitReset = client.getGitResetInstance();
        return gitReset.gitReset(repositoryPath, commitName, files);
    }

    /**
     * Perform a reset on the repository.
     */
    public static GitResetResponse reset(List<File> files) throws JavaGitException {
        CheckUtilities.checkNullListArgument(files, "files");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitReset gitReset = client.getGitResetInstance();
        return gitReset.gitReset(repositoryPath, files);
    }

    // ~~~~ BEGIN GIT REMOVE

    /**
     * Remove files relative to the path within the repository.
     */
    public GitRmResponse rm(File file, GitRmOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullArgument(file, "file");

        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.rm.GitRm GitRm = client.getGitRmInstance();
        return GitRm.rm(repositoryPath, options, file);
    }

    /**
     * Remove files relative to the path within the repository.
     */
    public GitRmResponse rm(List<File> files, GitRmOptions options) throws JavaGitException {
        CheckUtilities.checkNullArgument(options, "options");
        CheckUtilities.checkNullListArgument(files, "files");

        IClient client = ClientManager.getInstance().getPreferredClient();
        GitRm GitRm = client.getGitRmInstance();
        return GitRm.rm(repositoryPath, options, files);
    }

    // ~~~~ BEGIN GIT SHOW

    /**
     * Git show.
     */
    public GitShowResponse show(GitShowOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.show.GitShow gitShow = client.getGitShowInstance();
        return gitShow.show(repositoryPath, options);
    }

    /**
     * Git show on a file and a rev
     */
    public GitShowResponse show(File file, Ref revision, GitShowOptions options) throws JavaGitException {
        CheckUtilities.checkFileValidity(file);
        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.show.GitShow gitShow = client.getGitShowInstance();
        return gitShow.show(repositoryPath, options, file, revision);
    }

    /**
     * Git show on a file and a rev
     */
    public GitShowResponse show(Ref revision, GitShowOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        com.logisima.javagit.cli.show.GitShow gitShow = client.getGitShowInstance();
        return gitShow.show(repositoryPath, options, revision);
    }

    // ~~~~ BEGIN GIT STATUS

    /**
     * It returns a <code>GitStatusResponse</code> object that contains all the details of the output of
     * &lt;git-status&gt; command.
     */
    public GitStatusResponse status(List<File> paths, GitStatusOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitStatus gitStatus = client.getGitStatusInstance();
        return gitStatus.status(repositoryPath, options, paths);
    }

    /**
     * It returns a <code>GitStatusResonse</code> object that contains all the details of the output of
     * &lt;git-status&gt; command. It has no path passed as parameter to the method.
     */
    public GitStatusResponse status(GitStatusOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitStatus gitStatus = client.getGitStatusInstance();
        return gitStatus.status(repositoryPath, options);
    }

    /**
     * It returns a <code>GitStatusResonse</code> object that contains all the details of the output of
     * &lt;git-status&gt; command. Instead of passing a list of paths, this method takes a <code>String</code> argument
     * to a file-path.
     */
    public GitStatusResponse status(File file, GitStatusOptions options) throws JavaGitException {
        CheckUtilities.checkFileValidity(file);
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitStatus gitStatus = client.getGitStatusInstance();
        return gitStatus.status(repositoryPath, options, file);
    }

}
