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
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.log.GitLog;
import com.logisima.javagit.cli.log.GitLogOptions;

/**
 * <code>GitDirectory</code> represents a directory object in a git working tree.
 */
public class GitDirectory extends GitFileSystemObject {

    /**
     * The constructor. Both arguments are required (i.e. cannot be null).
     * 
     * @param dir The underlying {@link java.io.File} object that we want to augment with git functionality.
     * @param workingTree The <code>WorkingTree</code> that this directory falls under.
     * 
     */
    protected GitDirectory(File dir, WorkingTree workingTree) throws JavaGitException {
        super(dir, workingTree);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GitDirectory)) {
            return false;
        }

        GitFileSystemObject gitObj = (GitFileSystemObject) obj;
        return super.equals(gitObj);
    }

    /**
     * Gets the children of this directory.
     * 
     * @return The children of this directory.
     */
    public List<GitFileSystemObject> getChildren() throws IOException, JavaGitException {
        List<GitFileSystemObject> children = new ArrayList<GitFileSystemObject>();

        // get all of the file system objects currently located under this directory
        for (File memberFile : file.listFiles()) {
            // check if this file is hidden also some times the .git and
            // other unix hidden directories are not hidden in Windows
            if (memberFile.isHidden() || memberFile.getName().startsWith(".")) {
                // ignore (could be .git directory)
                continue;
            }

            // now, just check for the type of the filesystem object
            if (memberFile.isDirectory()) {
                children.add(new GitDirectory(memberFile, workingTree));
            }
            else {
                children.add(new GitFile(memberFile, workingTree));
            }
        }

        return children;
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
        return gitLog.log(this.file, options);
    }
}
