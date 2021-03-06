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

/**
 * A class to manage passing arguments to the <code>GitRm</code> command. <code>GitRm</code> deletes the indicated
 * files/folders from the git repository. <br>
 * <br>
 * 
 * Note: the --ignore-unmatch option is not included becuase it does not make sense in this setting.
 */
public class GitRmOptions {

    // TODO (jhl388): Write test cases for this class.

    /*
     * The --cached option. Unstages and removes paths only from the index while leaving the working tree files alone.
     */
    private boolean optCached = false;

    // The -f option. Overrides the up-to-date check.
    private boolean optF      = false;

    /*
     * The -n (--dry-run) option. Doesn't actually remove anything, it just shows what the command would do.
     */
    private boolean optN      = false;

    // The -q (--quiet) option. Suppresses output.
    private boolean optQ      = false;

    /*
     * The -r option. Recursively remove a directory and its descendant files/directories from the repository.
     */
    private boolean optR      = false;

    /**
     * Construct a <code>GitResetOptions</code> instance with the default values.
     */
    public GitRmOptions() {
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof GitRmOptions)) {
            return false;
        }

        GitRmOptions rmOpts = (GitRmOptions) o;

        if (rmOpts.isOptCached() != optCached) {
            return false;
        }

        if (rmOpts.isOptF() != optF) {
            return false;
        }

        if (rmOpts.isOptN() != optN) {
            return false;
        }

        if (rmOpts.isOptQ() != optQ) {
            return false;
        }

        if (rmOpts.isOptR() != optR) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int ret = (optCached) ? 1 : 0;
        ret <<= 1;
        ret += (optF) ? 1 : 0;
        ret <<= 1;
        ret += (optN) ? 1 : 0;
        ret <<= 1;
        ret += (optQ) ? 1 : 0;
        ret <<= 1;
        ret += (optR) ? 1 : 0;
        return ret;
    }

    /**
     * Indicates if rm should just unstage and remove paths only from the index while leaving the working tree files
     * alone.
     * 
     * @return True indicates that rm should just unstage and remove paths only from the index while leaving the working
     *         tree files alone. False indicates rm should also effect the working tree files (normal operation).
     */
    public boolean isOptCached() {
        return optCached;
    }

    /**
     * Indicates whether or not to override the up-to-date check.
     * 
     * @return True indicates to override the up-to-date check. False indicates not to override the up-to-date check.
     */
    public boolean isOptF() {
        return optF;
    }

    /**
     * Indicates if this should be a dry-run; i.e. run GitRm without making any changes on the disk.
     * 
     * @return True if rm should run without making changes to the disk. False if rm should save its changes to disk
     *         (normal operation).
     */
    public boolean isOptN() {
        return optN;
    }

    /**
     * Is the rm to perform quietly?
     * 
     * @return True if the rm is to perform quietly. False if the rm is to perform as normal.
     */
    public boolean isOptQ() {
        return optQ;
    }

    /**
     * Should rm run recursively down the specified working tree paths removing all files from the repository?
     * 
     * @return True indicates to recursively remove all files from the repository for all paths specified to GitRm.
     *         False indicates to only remove the exact files specified to GitRm.
     */
    public boolean isOptR() {
        return optR;
    }

    /**
     * Sets whether rm should unstage and remove the paths from the index without effecting the working tree files or if
     * rm should also effect the working tree files.
     * 
     * @param optCached True indicates rm should unstage and remove the paths from the index without effecting the
     *        working tree files. False indicates rm should also effect the working tree files.
     */
    public void setOptCached(boolean optCached) {
        this.optCached = optCached;
    }

    /**
     * Set whether rm should override the up-to-date check.
     * 
     * @param optF True indicates to override the up-to-date check. False indicates not to override the up-to-date
     *        check.
     */
    public void setOptF(boolean optF) {
        this.optF = optF;
    }

    /**
     * Sets whether rm should save save its changes to disk.
     * 
     * @param optN True if rm should run without making changes to the disk. False if rm should save its changes to disk
     *        (normal operation).
     */
    public void setOptN(boolean optN) {
        this.optN = optN;
    }

    /**
     * Set the rm to perform quietly or with normal progress information.
     * 
     * @param optQ True if the rm is to perform quietly. False if the rm is to perform as normal.
     */
    public void setOptQ(boolean optQ) {
        this.optQ = optQ;
    }

    /**
     * Set whether rm should recursively delete all files under the specified paths.
     * 
     * @param optR True indicates to recursively remove all files from the repository for all paths specified to GitRm.
     *        False indicates to only remove the exact files specified to GitRm.
     */
    public void setOptR(boolean optR) {
        this.optR = optR;
    }

    @Override
    public String toString() {
        StringBuffer buf = new StringBuffer();

        if (optCached) {
            buf.append("--cached ");
        }

        if (optF) {
            buf.append("-f ");
        }

        if (optN) {
            buf.append("-n ");
        }

        if (optQ) {
            buf.append("-q ");
        }

        if (optR) {
            buf.append("-r ");
        }

        return buf.toString();
    }

}
