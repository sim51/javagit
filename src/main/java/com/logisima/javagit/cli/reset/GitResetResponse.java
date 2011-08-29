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
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.cli.Response;
import com.logisima.javagit.object.Ref;

/**
 * A response data object for the <code>git-reset</code> command.
 */
public class GitResetResponse extends Response {

    /**
     * The list of files left in a dirty state (different than what is in the new HEAD commit) in the working tree.
     */
    protected List<File> filesNeedingUpdate;

    /**
     * If the --hard option was given, this is the SHA1 of the new head.
     */
    protected Ref        newHeadSha1;

    /**
     * If the --hard option was given, this is the short message for the commit at the new head.
     */
    protected String     newHeadShortMessage;

    /**
     * Constructor.
     */
    protected GitResetResponse() {
        super();
        filesNeedingUpdate = new ArrayList<File>();
    }

    /**
     * Constructor that sets the SHA1 and short message of the new head commit.
     * 
     * @param newHeadSha1 The SHA1 of the new head commit.
     * @param newHeadShortMessage The short message of the new head commit.
     */
    protected GitResetResponse(Ref newHeadSha1, String newHeadShortMessage) {
        this.newHeadSha1 = newHeadSha1;
        this.newHeadShortMessage = newHeadShortMessage;
    }

    /**
     * @return the filesNeedingUpdate
     */
    public List<File> getFilesNeedingUpdate() {
        return filesNeedingUpdate;
    }

    /**
     * @param filesNeedingUpdate the filesNeedingUpdate to set
     */
    public void setFilesNeedingUpdate(List<File> filesNeedingUpdate) {
        this.filesNeedingUpdate = filesNeedingUpdate;
    }

    /**
     * @return the newHeadSha1
     */
    public Ref getNewHeadSha1() {
        return newHeadSha1;
    }

    /**
     * @param newHeadSha1 the newHeadSha1 to set
     */
    public void setNewHeadSha1(Ref newHeadSha1) {
        this.newHeadSha1 = newHeadSha1;
    }

    /**
     * @return the newHeadShortMessage
     */
    public String getNewHeadShortMessage() {
        return newHeadShortMessage;
    }

    /**
     * @param newHeadShortMessage the newHeadShortMessage to set
     */
    public void setNewHeadShortMessage(String newHeadShortMessage) {
        this.newHeadShortMessage = newHeadShortMessage;
    }

}
