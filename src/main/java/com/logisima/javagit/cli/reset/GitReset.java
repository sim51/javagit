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
package com.logisima.javagit.cli.reset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitReset</code> interface.
 */
public class GitReset {

    public GitResetResponse gitReset(File repository, GitResetOptions options) throws JavaGitException {
        try {
            return resetProcessor(repository, new GitResetOptions(), null);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    public GitResetResponse gitReset(File repository, Ref commitName, List<File> paths) throws JavaGitException {
        try {
            return resetProcessor(repository, new GitResetOptions(commitName), paths);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    public GitResetResponse gitReset(File repository, List<File> paths) throws JavaGitException {
        try {
            return resetProcessor(repository, new GitResetOptions(), paths);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
    }

    protected GitResetResponse resetProcessor(File repository, GitResetOptions options, List<File> paths)
            throws IOException, JavaGitException {
        List<String> commandLine = buildCommand(options, paths);
        GitResetParser parser = new GitResetParser(repository.getPath());

        return (GitResetResponse) ProcessUtilities.runCommand(repository, commandLine, parser);
    }

    protected List<String> buildCommand(GitResetOptions options, List<File> paths) {
        List<String> cmd = new ArrayList<String>();
        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("reset");

        if (null != options) {
            if (null == paths) {
                // Only include the reset type if there are no paths. -- jhl388 2008.07.04
                cmd.add(options.getResetType().toString());
            }

            if (options.isQuiet()) {
                cmd.add("-q");
            }

            cmd.add(options.getCommitName().toString());
        }

        if (null != paths) {
            cmd.add("--");
            for (File f : paths) {
                cmd.add(f.getPath());
            }
        }

        return cmd;
    }

}
