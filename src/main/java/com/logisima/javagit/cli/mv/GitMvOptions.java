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

/**
 * A class to manage options to the <code>GitMv</code> command.
 */
public class GitMvOptions {

    // boolean variable to set or reset -f option.
    private boolean optF = false;

    // boolean variable to set or reset -n option.
    private boolean optN = false;

    // boolean variable to set or reset -k option.
    private boolean optK = false;

    /**
     * Is the -f option set?
     * 
     * @return True if the -f option is set, false if it is not set.
     */
    public boolean isOptF() {
        return optF;
    }

    /**
     * Set the force option.
     * 
     * @param optF True to set the -f option, false to reset it.
     */
    public void setOptF(boolean optF) {
        this.optF = optF;
    }

    /**
     * Is the -k option set?
     * 
     * @return True if the -k option is set, false if it is not set.
     */
    public boolean isOptK() {
        return optK;
    }

    /**
     * Set the quiet option.
     * 
     * @param optK True to set the -k option, false to reset it.
     */
    public void setOptK(boolean optK) {
        this.optK = optK;
    }

    /**
     * Is the -n option set?
     * 
     * @return True if the -n option is set, false if it is not set.
     */
    public boolean isOptN() {
        return optN;
    }

    /**
     * Set the dry-run option.
     * 
     * @param optN True to set the -n option, false to reset it.
     */
    public void setOptN(boolean optN) {
        this.optN = optN;
    }
}
