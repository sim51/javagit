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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with JavGit. If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli.add;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitAdd</code> interface.
 * 
 */
public class GitAdd {

    /**
     * Adds a list of files with GitAddOptions.
     */
    public GitAddResponse add(File repositoryPath, List<File> paths, GitAddOptions options) throws JavaGitException {
        GitAddParser parser = new GitAddParser();
        List<String> command = buildCommand(repositoryPath, options, paths);
        GitAddResponseImpl response;
        try {
            response = (GitAddResponseImpl) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return (GitAddResponse) response;
    }

    /**
     * Adds one file to the index with GitAddOptions.
     */
    public GitAddResponse add(File repositoryPath, File file, GitAddOptions options) throws JavaGitException {
        List<File> paths = new ArrayList<File>();
        paths.add(file);
        return add(repositoryPath, paths, options);
    }

    /**
     * Constructor of the command line.
     */
    private List<String> buildCommand(File repositoryPath, GitAddOptions options, List<File> paths) {
        List<String> command = new ArrayList<String>();
        command.add(JavaGitConfiguration.getGitCommand());
        command.add("add");
        if (options != null) {
            if (options.dryRun()) {
                command.add("-n");
            }
            if (options.verbose()) {
                command.add("-v");
            }
            if (options.force()) {
                command.add("-f");
            }
            if (options.update()) {
                command.add("-u");
            }
            if (options.refresh()) {
                command.add("--refresh");
            }
            if (options.ignoreErrors()) {
                command.add("--ignore-errors");
            }
        }
        if (paths != null && paths.size() > 0) {
            for (File file : paths) {
                command.add(file.getPath());
            }
        }
        return command;
    }

}
