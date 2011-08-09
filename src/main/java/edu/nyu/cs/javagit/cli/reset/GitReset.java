/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.cli.reset;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.nyu.cs.javagit.JavaGitConfiguration;
import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.api.object.Ref;
import edu.nyu.cs.javagit.utilities.ProcessUtilities;

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

    protected GitResetResponseImpl resetProcessor(File repository, GitResetOptions options, List<File> paths)
            throws IOException, JavaGitException {
        List<String> commandLine = buildCommand(options, paths);
        GitResetParser parser = new GitResetParser(repository.getPath());

        return (GitResetResponseImpl) ProcessUtilities.runCommand(repository, commandLine, parser);
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
