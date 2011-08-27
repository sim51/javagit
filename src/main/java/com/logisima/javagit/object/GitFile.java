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
package com.logisima.javagit.object;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.log.GitLog;
import com.logisima.javagit.cli.log.GitLogOptions;
import com.logisima.javagit.cli.status.GitStatus;
import com.logisima.javagit.cli.status.GitStatusResponse;

/**
 * <code>GitFile</code> a file object in a git working tree.
 */
public class GitFile extends GitFileSystemObject {

    /**
     * The constructor. Both arguments are required (i.e. cannot be null).
     * 
     * @param file underlying <code>java.io.File</code> object
     * @param workingTree The <code>WorkingTree</code> that this file falls under.
     * 
     */
    protected GitFile(File file, WorkingTree workingTree) throws JavaGitException {
        super(file, workingTree);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GitFile)) {
            return false;
        }

        GitFileSystemObject gitObj = (GitFileSystemObject) obj;
        return super.equals(gitObj);
    }

    /**
     * Show object's status in the working directory
     * 
     * @return Object's status in the working directory (untracked, changed but not updated, etc).
     */
    public Status getStatus() throws IOException, JavaGitException {
        GitStatus gitStatus = new GitStatus();
        // run git-status command
        GitStatusResponse response = gitStatus.status(workingTree.getPath(), null, relativePath);
        return response.getFileStatus(file);
    }

    /**
     * Show commit logs
     * 
     * @return List of commits for the working directory
     * @throws IOException
     * @throws JavaGitException
     */
    public List<com.logisima.javagit.cli.log.GitLogResponse.Commit> getLog() throws JavaGitException, IOException {
        GitLog gitLog = new GitLog();
        GitLogOptions options = new GitLogOptions();
        options.setOptRelative(true, this.relativePath.toString());
        return gitLog.log(this.file, null);
    }

    /**
     * 
     * @param options Options to the git log command
     * @return List of commits for the working directory
     * @throws JavaGitException
     * @throws IOException
     */
    public List<com.logisima.javagit.cli.log.GitLogResponse.Commit> getLog(GitLogOptions options)
            throws JavaGitException, IOException {
        GitLog gitLog = new GitLog();
        options.setOptRelative(true, this.relativePath.toString());
        return gitLog.log(this.file, options);
    }

}
