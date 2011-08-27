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
package com.logisima.javagit.cli.rm;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitRm</code> interface.
 */
public class GitRm {

    public GitRmResponse rm(File repository, GitRmOptions options, File path) throws JavaGitException {
        return processRm(repository, options, path, null);
    }

    public GitRmResponse rm(File repository, GitRmOptions options, List<File> paths) throws JavaGitException {
        return processRm(repository, options, null, paths);
    }

    /**
     * Processes an incoming <code>GitRm</code> request.
     */
    private GitRmResponse processRm(File repository, GitRmOptions options, File path, List<File> paths)
            throws JavaGitException {
        List<String> cmdline = buildCommandLine(options, path, paths);

        GitRmParser parser = new GitRmParser();
        try {
            return (GitRmResponse) ProcessUtilities.runCommand(repository, cmdline, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Builds the command line.
     */
    private List<String> buildCommandLine(GitRmOptions options, File path, List<File> paths) {
        List<String> cmdline = new ArrayList<String>();

        cmdline.add(JavaGitConfiguration.getGitCommand());
        cmdline.add("rm");

        if (null != options) {
            if (options.isOptCached()) {
                cmdline.add("--cached");
            }
            if (options.isOptF()) {
                cmdline.add("-f");
            }
            if (options.isOptN()) {
                cmdline.add("-n");
            }
            if (options.isOptQ()) {
                cmdline.add("-q");
            }
            if (options.isOptR()) {
                cmdline.add("-r");
            }
        }
        if (null != path) {
            cmdline.add(path.getPath());
        }
        else {
            for (File f : paths) {
                cmdline.add(f.getPath());
            }
        }
        return cmdline;
    }

}
