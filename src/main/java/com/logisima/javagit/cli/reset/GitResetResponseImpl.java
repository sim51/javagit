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

import com.logisima.javagit.object.Ref;

/**
 * Implementation of a <code>GitResetResponse</code> abstract class. This class adds functionality to set values in a
 * <code>GitResetResponse</code>.
 */
public class GitResetResponseImpl extends GitResetResponse {

    // TODO (jhl388): Add test cases for this class.

    /**
     * Default constructor.
     */
    public GitResetResponseImpl() {
        super();
    }

    public GitResetResponseImpl(Ref newHeadSha1, String newHeadShortMessage) {
        super(newHeadSha1, newHeadShortMessage);
    }

    /**
     * Adds the file to the files needing update list.
     * 
     * @param file The file to add to the files needing update list.
     */
    public void addFileToFilesNeedingUpdateList(File file) {
        filesNeedingUpdate.add(file);
    }

}
