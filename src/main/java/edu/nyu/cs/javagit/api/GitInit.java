package edu.nyu.cs.javagit.api;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.cli.ClientManager;
import edu.nyu.cs.javagit.cli.IClient;
import edu.nyu.cs.javagit.cli.init.GitInitOptions;
import edu.nyu.cs.javagit.cli.init.GitInitResponse;
import edu.nyu.cs.javagit.cli.init.IGitInit;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

public class GitInit {

    public GitInitResponse init(File repositoryPath, GitInitOptions options) throws JavaGitException, IOException {
        CheckUtilities.checkNullArgument(repositoryPath, "repository");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitInit gitInit = client.getGitInitInstance();
        return gitInit.init(repositoryPath, options);
    }

    public GitInitResponse init(File repositoryPath) throws JavaGitException, IOException {
        CheckUtilities.checkNullArgument(repositoryPath, "repository");

        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitInit gitInit = client.getGitInitInstance();
        return gitInit.init(repositoryPath);
    }

}
