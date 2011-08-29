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

import com.logisima.javagit.cli.Response;

/**
 * A response data object for the git-mv command. For information about the contents of GitMvResponse instances returned
 * by a given method, please see the JavaDoc for the method in question.
 */
public class GitMvResponse extends Response {

    /**
     * Variable to store the source file/folder/symlink of the response.
     */
    protected File   source;

    /**
     * Variable to store the destination file/folder/symlink of the response.
     */
    protected File   destination;

    /**
     * String Buffer to store the comment message after execution of git-mv.
     */
    protected String message;

    /**
     * Constructor.
     */
    public GitMvResponse() {
        super();
    }

    /**
     * @return the source
     */
    public File getSource() {
        return source;
    }

    /**
     * @param source the source to set
     */
    public void setSource(File source) {
        this.source = source;
    }

    /**
     * @return the destination
     */
    public File getDestination() {
        return destination;
    }

    /**
     * @param destination the destination to set
     */
    public void setDestination(File destination) {
        this.destination = destination;
    }

    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
