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
package com.logisima.javagit.cli.log;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.ProcessUtilities;

/**
 * Command-line implementation of the <code>IGitLog</code> interface.
 */
public class GitLog {

    /**
     * Implementations of git log.
     */
    public GitLogResponse log(File repositoryPath, GitLogOptions options) throws JavaGitException {
        GitLogParser parser = new GitLogParser();
        List<String> command = buildCommand(repositoryPath, options, null);
        GitLogResponse response;
        try {
            response = (GitLogResponse) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return response;
    }

    /**
     * Implementations of git log onf files.
     */
    public GitLogResponse log(File repositoryPath, GitLogOptions options, List<File> paths) throws JavaGitException {
        GitLogParser parser = new GitLogParser();
        List<String> command = buildCommand(repositoryPath, options, paths);
        GitLogResponse response;
        try {
            response = (GitLogResponse) ProcessUtilities.runCommand(repositoryPath, command, parser);
        } catch (IOException e) {
            throw new JavaGitException(JavaGitException.PROCESS_ERROR, e.getMessage());
        }
        return response;
    }

    /**
     * This function builds the git log commands with necessary options as specified by the user.
     */
    private List<String> buildCommand(File repositoryPath, GitLogOptions options, List<File> paths) {
        List<String> command = new ArrayList<String>();
        command.add(JavaGitConfiguration.getGitCommand());
        command.add("log");
        if (null != paths) {
            command.add("--");
            for (File f : paths) {
                command.add(f.getPath());
            }
        }
        if (options != null) {
            // General Options
            /**
             * Breaks rewrite changes in to pairs of delete and create.
             */
            if (options.isOptBreakRewriteChanges()) {
                command.add("-B");
            }
            /**
             * Detects renames
             */
            if (options.isOptDetectRenames()) {
                command.add("-M");
            }
            /**
             * Detects copies and renames, of original files
             */
            if (options.isOptFindCopies()) {
                command.add("-C");
            }
            /**
             * Detects copies and renames , very expensive operation.
             */
            if (options.isOptFindCopiesHarder()) {
                command.add("--find-copies-harder");
            }
            /**
             * List details about lines modified and files affected in a commit.
             */
            if (options.isOptFileDetails()) {
                command.add("--numstat");
            }

            /**
             * List all logs on the relative path.
             */
            if (options.isOptRelative()) {
                command.add("--relative=" + options.getOptRelativePath());
            }

            /**
             * List all logs since specified date.
             */
            if (options.isOptLimitCommitSince()) {
                command.add("--since=" + options.getOptLimitSince());
            }

            /**
             * List all logs after specified date.
             */
            if (options.isOptLimitCommitAfter()) {
                command.add("--after=" + options.getOptLimitAfter());
            }

            /**
             * List all logs after specified date.
             */
            if (options.isOptLimitCommitUntil()) {
                command.add("--until=" + options.getOptLimitUntil());
            }

            /**
             * List all logs before specified date.
             */
            if (options.isOptLimitCommitBefore()) {
                command.add("--before=" + options.getOptLimitBefore());
            }

            /**
             * List all logs by an author
             */
            if (options.isOptLimitAuthor()) {
                command.add("--author=" + options.getOptAuthor());
            }

            /**
             * List all logs by an author/committer header pattern.
             */
            if (options.isOptLimitCommitterPattern()) {
                command.add("--committer=" + options.getOptLimitPattern());
            }

            /**
             * List all logs by matching to a grep pattern.
             */
            if (options.isOptLimitGrep()) {
                command.add("--grep=" + options.getOptLimitGrepPattern().toString());
            }
            /**
             * Match regular expressions with out regard to letters case.
             */
            if (options.isOptLimitMatchIgnoreCase()) {
                command.add("-i");
            }

            /**
             * Match extended regular expressions.
             */
            if (options.isOptLimitEnableExtendedRegex()) {
                command.add("-E");
            }

            /**
             * Match patterns as fixed strings and not regular expressions.
             */
            if (options.isOptLimitMatchIgnoreCase()) {
                command.add("-F");
            }

            /**
             * Stop when a path dissapears from the tree.
             */
            if (options.isOptLimitRemoveEmpty()) {
                command.add("--remove-empty");
            }

            /**
             * Match parts of history irrelevant to the current path.
             */
            if (options.isOptLimitFullHistory()) {
                command.add("--full-history");
            }

            /**
             * Do not print commits with more than one merges.
             */
            if (options.isOptLimitNoMerges()) {
                command.add("--no-merges");
            }

            /**
             * Follow only first parent on seeing a merge.
             */
            if (options.isOptLimitFirstParent()) {
                command.add("--first-parent");
            }

            /**
             * Order commits topologically.
             */
            if (options.isOptOrderingTopological()) {
                command.add("--topo-order");
            }

            /**
             * Order commits in reverse
             */
            if (options.isOptOrderingReverse()) {
                command.add("--reverse");
            }

            /**
             * Limits the number of commits to retrieve.
             */
            if (options.isOptLimitCommitMax()) {
                command.add("-n");
                command.add(String.valueOf(options.getOptLimitMax()));
            }

            // Skips the specified number of commit messages
            if (options.isOptLimitCommitSkip()) {
                command.add("--skip=" + options.getOptLimitSkip());
            }
        }
        return command;
    }

}
