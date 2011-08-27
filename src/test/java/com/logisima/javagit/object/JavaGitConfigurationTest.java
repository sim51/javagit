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
package com.logisima.javagit.object;

import java.io.File;

import junit.framework.TestCase;

import org.junit.Test;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.test.utilities.FileUtilities;

/**
 * Test cases for our <code>WorkingTree</code> class.
 */
public class JavaGitConfigurationTest extends TestCase {

    @Test
    public void testSettingGitPathExceptions() {
        String pathString;
        File pathFile;

        // Attempting to set a null path should generate a NullPointerException.
        pathString = null;
        try {
            JavaGitConfiguration.setGitPath(pathString);
            fail("Invalid path - this should never be reached.");
        } catch (NullPointerException e) {
            // This is fine.
        } catch (Exception e) {
            fail("Null path (String) generated wrong kind of exception: " + e.getClass().getName());
        }

        // Attempting to set an empty path as a String should generate an IllegalArgumentException.
        pathString = "";
        try {
            JavaGitConfiguration.setGitPath(pathString);
            fail("Invalid path - this should never be reached.");
        } catch (IllegalArgumentException e) {
            // This is fine.
        } catch (Exception e) {
            fail("Empty path (String) generated wrong kind of exception: " + e.getClass().getName());
        }

        // Attempting to set an empty path as a File should generate an IOException.
        pathFile = new File("");
        try {
            JavaGitConfiguration.setGitPath(pathFile);
            fail("Invalid path - this should never be reached.");
        } catch (JavaGitException e) {
            // This is fine.
        } catch (Exception e) {
            fail("Empty path (String) generated wrong kind of exception: " + e.getClass().getName());
        }

        // Let's create a temp file and try to set that as the path. Should throw a JavaGitException.
        try {
            pathFile = File.createTempFile("xyz", null);
            JavaGitConfiguration.setGitPath(pathFile);
            fail("Invalid path - this should never be reached.");
        } catch (JavaGitException e) {
            assertEquals(e.getCode(), 020002);
            pathFile.delete();
        } catch (Exception e) {
            fail("Non-directory path (File) generated wrong kind of exception: " + e.getClass().getName());
        }

        /*
         * Let's create a temp dir and try to set that as the path. It won't contain git, so it should throw a
         * JavaGitException.
         */
        try {
            pathFile = FileUtilities.createTempDirectory("abc");
            JavaGitConfiguration.setGitPath(pathFile);
            fail("Invalid path - this should never be reached.");
        } catch (JavaGitException e) {
            assertEquals(e.getCode(), 100002);
            pathFile.delete();
        } catch (Exception e) {
            fail("Temp path not containing git generated wrong kind of exception: " + e.getClass().getName());
        }

        /*
         * Set the path using null as the File argument. This is valid - it's saying: wipe out whatever was set before
         * (via previous calls to setGitPath) and just look on the PATH for git.
         */
        pathFile = null;
        try {
            JavaGitConfiguration.setGitPath(pathFile);
        } catch (Exception e) {
            fail("Null path (File) generated exception: " + e.getClass().getName());
        }

        // Grab the version and see if we can get it exception-free.
        String version = "";
        try {
            version = JavaGitConfiguration.getGitVersion();
        } catch (Exception e) {
            fail("Couldn't get git version string - saw \"" + version + "\" and got exception: "
                    + e.getClass().getName());
        }
    }
}
