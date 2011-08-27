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

import com.logisima.javagit.cli.ICommandResponse;
import com.logisima.javagit.utilities.CheckUtilities;

/**
 * A response data object for the git-mv command. For information about the contents of GitMvResponse instances returned
 * by a given method, please see the JavaDoc for the method in question.
 */
public class GitMvResponse implements ICommandResponse {

    // Variable to store the source file/folder/symlink of the response.
    protected File         source;

    // Variable to store the destination file/folder/symlink of the response.
    protected File         destination;

    // String Buffer to store the comment message after execution of git-mv.
    protected StringBuffer message = new StringBuffer();

    /**
     * Gets the comments, if received, upon successful execution of the git-mv command, from the message buffer.
     * 
     * @return message The comments, if received, upon successful execution of the git-mv command, from the message
     *         buffer.
     */
    public String getComment() {
        return message.toString();
    }

    /**
     * Gets the destination file/folder
     * 
     * @return the destination
     */
    public File getDestination() {
        return destination;
    }

    /**
     * Gets the source file/folder/symlink
     * 
     * @return the source
     */
    public File getSource() {
        return source;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GitMvResponse)) {
            return false;
        }

        GitMvResponse g = (GitMvResponse) o;

        if (!CheckUtilities.checkObjectsEqual(getSource(), g.getSource())) {
            return false;
        }

        if (!CheckUtilities.checkObjectsEqual(getDestination(), g.getDestination())) {
            return false;
        }

        if (!CheckUtilities.checkObjectsEqual(getComment(), g.getComment())) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return source.hashCode() + destination.hashCode() + message.hashCode();
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        if (null != source) {
            buffer.append("Source: ");
            buffer.append(source.getName());
            buffer.append(" ");
        }

        if (null != destination) {
            buffer.append("Destination: ");
            buffer.append(destination.getName());
            buffer.append(" ");
        }

        if ((message.length() != 0)) {
            buffer.append("Message: ");
            buffer.append(message.toString());
        }
        return buffer.toString();
    }
}
