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
package com.logisima.javagit.cli.init;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.utilities.CheckUtilities;
import com.logisima.javagit.utilities.ProcessUtilities;

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
