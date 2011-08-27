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
package com.logisima.javagit.cli.add;

import java.io.File;

/**
 * Class implementing <code>GitAddResponse</code> for setting values in it.
 */
public class GitAddResponseImpl extends GitAddResponse {

    /**
     * Sets the value of no output flag.
     * 
     * @param noOutput true if there was no output generated. This is a helper flag that tells the consumer of
     *        <code>GitAddResponse</code> that there was no resulting output for executed &lt;git-add&gt; command.
     */
    public void setNoOutput(boolean noOutput) {
        this.noOutput = noOutput;
    }

    /**
     * Sets the flag if dry run flag need to be used
     * 
     * @param dryRun true if dry run should be used, otherwise false.
     */
    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }

    /**
     * Sets the non-error message generated in the output of the &lt;git-add&gt; command.
     * 
     * @param lineNumber line number at which the message appeared in output.
     * @param commentString message itself.
     */
    public void setComment(int lineNumber, String commentString) {
        ResponseString comment = new ResponseString(lineNumber, commentString);
        comments.add(comment);
    }

    /**
     * Adds a file and action to the list.
     * 
     * @param file File to be added to the <code>List</code>.
     */
    public void add(File file) {
        filePathsList.add(file);
    }

}
