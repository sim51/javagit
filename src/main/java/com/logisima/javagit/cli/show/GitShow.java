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
package com.logisima.javagit.cli.show;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.object.Ref;
import com.logisima.javagit.utilities.CheckUtilities;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitShow</code> interface.
 */
public class GitShow {

    public GitShowResponse show(File repositoryPath, GitShowOptions options) throws JavaGitException {
        return show(repositoryPath, options, null, null);
    }

    public GitShowResponse show(File repositoryPath, GitShowOptions options, Ref revision) throws JavaGitException {
        return show(repositoryPath, options, null, revision);
    }

    public GitShowResponse show(File repositoryPath, GitShowOptions options, File path, Ref revision)
            throws JavaGitException {
        CheckUtilities.checkNullArgument(repositoryPath, "repository");
        CheckUtilities.validateArgumentRefType(revision, Ref.RefType.SHA1, "revision");

        GitShowParser parser = new GitShowParser();
        List<String> command = buildCommand(repositoryPath, options, path, revision);
        try {
            GitShowResponse response = (GitShowResponse) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        // TODO besim
        return null;
    }

    /**
     * Constructor of the command line.
     */
    protected List<String> buildCommand(File repositoryPath, GitShowOptions options, File file, Ref revision) {

        List<String> cmd = new ArrayList<String>();
        cmd.add(JavaGitConfiguration.getGitCommand());
        cmd.add("show");

        if (file != null && revision != null) {
            cmd.add(revision.getName() + ":" + file.getPath());
        }
        else {
            if (revision != null) {
                cmd.add(revision.getName());
            }
        }

        if (null != options) {
            // TODO there is no options
        }

        return cmd;
    }
}
