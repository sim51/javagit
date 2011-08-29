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
import java.util.List;

import com.logisima.javagit.cli.Response;

/**
 * <code>GitRmResponse</code> holds the response information returned by the <code>GitRm</code> class.
 */
public class GitRmResponse extends Response {

    /**
     * The list of removed files.
     */
    private List<File> removedFiles = new ArrayList<File>();

    /**
     * Constructor.
     */
    public GitRmResponse() {
        super();
        removedFiles = new ArrayList<File>();
    }

    /**
     * @return the removedFiles
     */
    public List<File> getRemovedFiles() {
        return removedFiles;
    }

    /**
     * @param removedFiles the removedFiles to set
     */
    public void setRemovedFiles(List<File> removedFiles) {
        this.removedFiles = removedFiles;
    }

}
