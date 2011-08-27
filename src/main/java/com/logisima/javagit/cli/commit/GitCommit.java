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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.CheckUtilities;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitCommit</code> interface.
 */
public class GitCommit {

    public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message)
            throws JavaGitException {
        return commitProcessor(repository, options, message, null);
    }

    public GitCommitResponseImpl commit(File repository, GitCommitOptions options, String message, List<File> paths)
            throws JavaGitException {
        return commitProcessor(repository, options, message, paths);
    }

    /**
     * Processes the commit.
     */
    protected GitCommitResponseImpl commitProcessor(File repository, GitCommitOptions options, String message,
            List<File> paths) throws JavaGitException {
        CheckUtilities.checkNullArgument(repository, "repository");
        CheckUtilities.checkStringArgument(message, "message");

        List<String> commandLine = buildCommand(options, message, paths);
        GitCommitParser parser = new GitCommitParser(repository.getAbsolutePath());

        try {
            return (GitCommitResponseImpl) ProcessUtilities.runCommand(repository, commandLine, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     */
    protected List<String> buildCommand(GitCommitOptions options, String message, List<File> paths) {
        List<String> cmd = new ArrayList<String>();
        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("commit");

        if (null != options) {
            if (options.isOptAll()) {
                cmd.add("-a");
            }
            if (options.isOptInclude()) {
                cmd.add("-i");
            }
            if (options.isOptNoVerify()) {
                cmd.add("--no-verify");
            }
            if (options.isOptOnly()) {
                cmd.add("-o");
            }
            if (options.isOptSignoff()) {
                cmd.add("-s");
            }
            String author = options.getAuthor();
            if (null != author && author.length() > 0) {
                cmd.add("--author");
                cmd.add(options.getAuthor());
            }
        }

        cmd.add("-m");
        cmd.add(message);

        if (null != paths) {
            cmd.add("--");
            for (File f : paths) {
                cmd.add(f.getPath());
            }
        }

        return cmd;
    }

}
