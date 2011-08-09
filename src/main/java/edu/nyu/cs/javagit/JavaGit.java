package edu.nyu.cs.javagit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.api.object.Ref;
import edu.nyu.cs.javagit.cli.ClientManager;
import edu.nyu.cs.javagit.cli.IClient;
import edu.nyu.cs.javagit.cli.add.GitAdd;
import edu.nyu.cs.javagit.cli.add.GitAddOptions;
import edu.nyu.cs.javagit.cli.add.GitAddResponse;
import edu.nyu.cs.javagit.cli.commit.GitCommit;
import edu.nyu.cs.javagit.cli.commit.GitCommitOptions;
import edu.nyu.cs.javagit.cli.commit.GitCommitResponse;
import edu.nyu.cs.javagit.cli.init.GitInitOptions;
import edu.nyu.cs.javagit.cli.init.GitInitResponse;
import edu.nyu.cs.javagit.cli.log.GitLog;
import edu.nyu.cs.javagit.cli.log.GitLogOptions;
import edu.nyu.cs.javagit.cli.log.GitLogResponse.Commit;
import edu.nyu.cs.javagit.cli.mv.GitMv;
import edu.nyu.cs.javagit.cli.mv.GitMvOptions;
import edu.nyu.cs.javagit.cli.mv.GitMvResponse;
import edu.nyu.cs.javagit.cli.reset.GitReset;
import edu.nyu.cs.javagit.cli.reset.GitResetOptions;
import edu.nyu.cs.javagit.cli.reset.GitResetResponse;
import edu.nyu.cs.javagit.cli.rm.GitRm;
import edu.nyu.cs.javagit.cli.rm.GitRmOptions;
import edu.nyu.cs.javagit.cli.rm.GitRmResponse;
import edu.nyu.cs.javagit.cli.show.GitShowOptions;
import edu.nyu.cs.javagit.cli.show.GitShowResponse;
import edu.nyu.cs.javagit.cli.status.GitStatus;
import edu.nyu.cs.javagit.cli.status.GitStatusOptions;
import edu.nyu.cs.javagit.cli.status.GitStatusResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

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

    // ~~~~ BEGIN GIT COMMIT

    /**
     * Commits staged changes into the specified repository. The specific files that are committed depends on the
     * options specified.
     */
    public GitCommitResponse commit(String message, GitCommitOptions options) throws IOException, JavaGitException {

        CheckUtilities.checkStringArgument(message, "message");
        CheckUtilities.checkNullArgument(options, "options");

        IClient client = ClientManager.getInstance().getPreferredClient();
        edu.nyu.cs.javagit.cli.commit.GitCommit gitCommit = client.getGitCommitInstance();
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

    // ~~~~ BEGIN GIT INIT

    /**
     * Git init.
     */
    public GitInitResponse init(GitInitOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        edu.nyu.cs.javagit.cli.init.GitInit gitInit = client.getGitInitInstance();
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
        edu.nyu.cs.javagit.cli.mv.GitMv gitMv = client.getGitMvInstance();
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
        edu.nyu.cs.javagit.cli.rm.GitRm GitRm = client.getGitRmInstance();
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
        edu.nyu.cs.javagit.cli.show.GitShow gitShow = client.getGitShowInstance();
        return gitShow.show(repositoryPath, options);
    }

    /**
     * Git show on a file and a rev
     */
    public GitShowResponse show(File file, Ref revision, GitShowOptions options) throws JavaGitException {
        CheckUtilities.checkFileValidity(file);
        IClient client = ClientManager.getInstance().getPreferredClient();
        edu.nyu.cs.javagit.cli.show.GitShow gitShow = client.getGitShowInstance();
        return gitShow.show(repositoryPath, options, file, revision);
    }

    /**
     * Git show on a file and a rev
     */
    public GitShowResponse show(Ref revision, GitShowOptions options) throws JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        edu.nyu.cs.javagit.cli.show.GitShow gitShow = client.getGitShowInstance();
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
