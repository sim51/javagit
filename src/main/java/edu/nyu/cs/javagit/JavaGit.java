package edu.nyu.cs.javagit;

import java.io.File;
import java.io.IOException;
import java.util.List;

import edu.nyu.cs.javagit.cli.ClientManager;
import edu.nyu.cs.javagit.cli.IClient;
import edu.nyu.cs.javagit.cli.add.GitAdd;
import edu.nyu.cs.javagit.cli.add.GitAddOptions;
import edu.nyu.cs.javagit.cli.add.GitAddResponse;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class JavaGit {

    private File repositoryPath;

    public JavaGit(File repositoryPath) throws JavaGitException {
        super();
        try {
            CheckUtilities.checkFileValidity(repositoryPath.getAbsoluteFile());
        } catch (IOException e) {
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
    public GitAddResponse add(File file, GitAddOptions options) throws IOException, JavaGitException {
        IClient client = ClientManager.getInstance().getPreferredClient();
        GitAdd gitAdd = client.getGitAddInstance();
        return gitAdd.add(repositoryPath, file, options);
    }

}
