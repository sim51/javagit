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

/**
 * Implementation of a <code>GitMvResponse</code>. This class adds functionality to set values in a
 * <code>GitMvResponse</code>.
 */
public class GitMvResponseImpl extends GitMvResponse {

    /**
     * Adds comments from each line of the message, if received, upon successful execution of the git-mv command, to the
     * message buffer.
     * 
     * @param comment The comment from each line of the message, if received, upon successful execution of the git-mv.
     */
    public void addComment(String comment) {
        message.append(comment);
    }

    /**
     * Sets the destination file/folder/symlink in response to the destination
     * 
     * @param destination The destination to set
     */
    public void setDestination(File destination) {
        this.destination = destination;
    }

    /**
     * Sets the source file/folder/symlink in response object to the source string.
     * 
     * @param source The source to set
     */
    public void setSource(File source) {
        this.source = source;
    }

}
