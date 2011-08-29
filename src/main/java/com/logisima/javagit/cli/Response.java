/**
 *  This file is part of LogiSima (http://www.logisima.com).
 *
 *  JavaGit is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  JavaGit is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Javagit.  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli;

import java.util.List;

/**
 * Abstract class for Git Response.
 * 
 * @author bsimard
 * 
 */
public abstract class Response {

    /**
     * Git command line generated
     */
    protected List<String> command;
    /**
     * Exit code of git process
     */
    protected int          processExitCode;
    /**
     * Standard output of gi process
     */
    protected String       processOutput;

    /**
     * @return the processExitCode
     */
    public int getProcessExitCode() {
        return processExitCode;
    }

    /**
     * @return the processOutput
     */
    public String getProcessOutput() {
        return processOutput;
    }

    /**
     * @return the string command
     */
    public String getCommand() {
        String cmd = new String();
        for (int i = 0; i < command.size(); i++) {
            cmd += command.get(i);
            cmd += " ";
        }
        return cmd.trim();
    }

}
