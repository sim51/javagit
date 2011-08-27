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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitStatus</code> interface.
 * 
 */
public class GitStatus {

    /**
     * Implementation of <code>IGitStatus</code> method for getting the status of a list of files
     * 
     */
    public GitStatusResponse status(File repositoryPath, GitStatusOptions options, List<File> paths)
            throws JavaGitException {
        List<String> command = buildCommandLine(options, paths);
        GitStatusParser parser = new GitStatusParser(repositoryPath.getPath() + File.separator);
        GitStatusResponse response;
        try {
            response = (GitStatusResponseImpl) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return response;
    }

    /**
     * Implementation of <code>IGitStatus</code> method for getting the status of a file.
     */
    public GitStatusResponse status(File repositoryPath, GitStatusOptions options, File file) throws JavaGitException {
        List<File> paths = new ArrayList<File>();
        paths.add(file);
        return status(repositoryPath, options, paths);
    }

    /**
     * Implementation of <code>IGitStatus</code> method with only options passed to &lt;git-status&gt; command.
     */
    public GitStatusResponse status(File repositoryPath, GitStatusOptions options) throws JavaGitException {
        List<File> paths = null;
        return status(repositoryPath, options, paths);
    }

    /**
     * Parses options provided by the <code>GitStatusOptions</code> object and adds them to the command.
     * 
     * @param options <code>GitStatusOptions</code> provided by &lt;gitclipse&gt;.
     * @param paths List of file paths.
     * @return command to be executed.
     */
    private List<String> buildCommandLine(GitStatusOptions options, List<File> paths) {
        List<String> command = new ArrayList<String>();

        command.add(JavaGitConfiguration.getGitCommand());
        command.add("status");

        if (options != null) {
            if (options.isOptAll()) {
                command.add("-a");
            }
            if (options.isOptQuiet()) {
                command.add("-q");
            }
            if (options.isOptVerbose()) {
                command.add("-v");
            }
            if (options.isOptSignOff()) {
                command.add("-s");
            }
            if (options.isOptEdit()) {
                command.add("-e");
            }
            if (options.isOptInclude()) {
                command.add("-i");
            }
            if (options.isOptOnly()) {
                command.add("-o");
            }
            if (options.isOptNoVerify()) {
                command.add("-n");
            }
            if (options.isOptUntrackedFiles()) {
                command.add("--untracked-files");
            }
            if (options.isOptAllowEmpty()) {
                command.add("--allow-empty");
            }
            if (!options.isOptReadFromLogFileNull()) {
                command.add("-F");
                command.add(options.getOptReadFromLogFile().getPath());
            }
            if (!options.isAuthorNull()) {
                command.add("--author");
                command.add(options.getAuthor());
            }
        }

        if (paths != null) {
            for (File file : paths) {
                command.add(file.getPath());
            }
        }
        return command;
    }

}
