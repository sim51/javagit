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
package com.logisima.javagit.cli.mv;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitMv</code> interface.
 */
public class GitMv {

    public GitMvResponse mv(File repoPath, GitMvOptions options, File source, File destination) throws JavaGitException {
        List<File> sources = new ArrayList<File>();
        sources.add(source);
        return mvProcess(repoPath, options, sources, destination);
    }

    public GitMvResponse mv(File repoPath, GitMvOptions options, List<File> sources, File destination)
            throws JavaGitException {
        return mvProcess(repoPath, options, sources, destination);
    }

    /**
     * Exec of git-mv command
     */
    public GitMvResponse mvProcess(File repoPath, GitMvOptions options, List<File> source, File destination)
            throws JavaGitException {

        List<String> commandLine = buildCommand(options, source, destination);
        GitMvParser parser = new GitMvParser();

        try {
            return (GitMvResponse) ProcessUtilities.runCommand(repoPath, commandLine, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    /**
     * Builds a list of command arguments to pass to <code>ProcessBuilder</code>.
     */
    protected List<String> buildCommand(GitMvOptions options, List<File> source, File destination) {
        List<String> cmd = new ArrayList<String>();

        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("mv");

        if (null != options) {
            if (options.isOptF()) {
                cmd.add("-f");
            }
            if (options.isOptK()) {
                cmd.add("-k");
            }
            if (options.isOptN()) {
                cmd.add("-n");
            }
        }
        for (File file : source) {
            cmd.add(file.getPath());
        }
        cmd.add(destination.getPath());
        return cmd;
    }

}
