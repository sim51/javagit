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
package com.logisima.javagit;

/**
 * Base exception for git specific exceptions.
 */
public class JavaGitException extends Exception {

    public final static int   REPOSITORY_ERROR = 100;
    public final static int   FILE_ERROR       = 200;
    public final static int   PROCESS_ERROR    = 300;

    // as per the Java spec, this is a required field for <code>Serializable</code>
    private static final long serialVersionUID = 1402053559415331074L;
    // The code for an exception instance.
    private int               code;

    /**
     * Create an exception with a code and a message.
     * 
     * @param code The code for this exception.
     * @param message The message for this exception.
     */
    public JavaGitException(int code, String message) {
        super(message);
        this.code = code;
    }

    /**
     * Create an exception with a code, a message and a causal <code>Throwable</code>.
     * 
     * @param code The code for this exception.
     * @param message The message for this exception.
     * @param cause A <code>Throwable</code> that caused this exception.
     */
    public JavaGitException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    /**
     * Get the code this exception is thrown with.
     * 
     * @return The code for this exception.
     */
    public int getCode() {
        return code;
    }

}
