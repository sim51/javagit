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
package com.logisima.javagit.cli.rm;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.logisima.javagit.cli.ICommandResponse;
import com.logisima.javagit.utilities.CheckUtilities;

/**
 * <code>GitRmResponse</code> holds the response information returned by the <code>GitRm</code> class.
 */
public abstract class GitRmResponse implements ICommandResponse {

    // The list of removed files.
    protected List<File> removedFiles = new ArrayList<File>();

    /**
     * Gets the file at the specified index from the removed file list.
     * 
     * @param index The index of the file to get. It must fall in the range:
     *        <code>0 &lt;= index &lt; getRemovedFilesSize()</code>.
     * @return The file at the specified index.
     */
    public File getRemovedFile(int index) {
        CheckUtilities.checkIntIndexInListRange(removedFiles, index);
        return removedFiles.get(index);
    }

    /**
     * Gets an <code>Iterator</code> over the list of removed files.
     * 
     * @return An <code>Iterator<code> over the list of removed files.
     */
    public Iterator<File> getRemovedFilesIterator() {
        return (new ArrayList<File>(removedFiles)).iterator();
    }

    /**
     * Gets the number of removed files (provided that the quiet option was not used).
     * 
     * @return The number of removed files. If the quiet option was used, zero (0) will be returned.
     */
    public int getRemovedFilesSize() {
        return removedFiles.size();
    }

}
