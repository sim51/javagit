package edu.nyu.cs.javagit.cli.init;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.utilities.CheckUtilities;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

public class GitInit {

    public GitInitResponse init(File repoDirectory, GitInitOptions options) throws JavaGitException {
        CheckUtilities.checkFileValidity(repoDirectory);
        GitInitParser parser = new GitInitParser();
        List<String> command = buildCommand(repoDirectory, options);
        GitInitResponse response;
        try {
            response = (GitInitResponse) ProcessUtilities.runCommand(repoDirectory, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        if (response.containsError()) {
            throw new JavaGitException(418001, "Git Init error");
        }
        return response;
    }

    /*
     * Build the command to be executed using the Git Init method
     */
    private List<String> buildCommand(File repoDirectory, GitInitOptions options) {
        List<String> command = new ArrayList<String>();
        command.add(JavaGitConfiguration.getGitCommand());
        command.add("init");
        if (options != null) {

            if (options.isOptBare()) {
                command.add("--bare");
            }
            if (options.isOptTemplate()) {
                command.add("--template=" + options.getOptTemplateDirecory());
            }
            if (options.isOptSharedUmask()) {
                command.add("--shared=");
            }
            if (options.isOptSharedGroup()) {
                command.add("--shared=" + options.getOptSharedGroupName());
            }
            if (options.isOptSharedAll()) {
                command.add("--shared=");
            }
            if (options.isOptSharedOctal()) {
                command.add("--shared=" + options.getOptSharedOctalValue());
            }
        }
        return command;
    }

}
