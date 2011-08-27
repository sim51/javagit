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

/**
 * Test cases for our <code>DotGit</code> class.
 */
public class DotGitTest extends TestCase {

    // We want two different strings that represent the same place in the filesystem.
    private static final String TEST_DIRNAME           = "testdir";
    private static final String TEST_DIRNAME_ALTERNATE = "testdir/../testdir";

    // Make this one totally different than the first two.
    private static final String TEST_DIRNAME_2         = "testdir2";

    @Test
    public void testDotGitEquality() {
        /*
         * First we test two <code>DotGit</code> objects that should be equal, using both
         * <code>File</code>/<code>String</code> getInstance methods
         */
        runEqualityTests(TEST_DIRNAME, TEST_DIRNAME_ALTERNATE, true, true);
        runEqualityTests(TEST_DIRNAME, TEST_DIRNAME_ALTERNATE, false, true);

        // Then we test two that should not be equal
        runEqualityTests(TEST_DIRNAME, TEST_DIRNAME_2, true, false);
        runEqualityTests(TEST_DIRNAME, TEST_DIRNAME_2, false, false);
    }

    @Test
    public void testDotGitWorkingTreeValidity() {
        DotGit dotGit = DotGit.getInstance(TEST_DIRNAME);

        // Check that the <code>DotGit</code> path matches its <code>WorkingTree</code> path
        assertEquals(dotGit.getPath(), dotGit.getWorkingTree().getPath());

        // Also test equality not through <code>DotGit</code> but using getInstance
        assertEquals(dotGit.getWorkingTree(), WorkingTree.getInstance(TEST_DIRNAME));

        // Also check that things are equal a layer deeper
        assertEquals(dotGit, dotGit.getWorkingTree().getDotGit());
    }

    private static void runEqualityTests(String path1, String path2, boolean accessViaFileObjects, boolean testEquality) {
        DotGit dotgit1, dotgit2;

        if (accessViaFileObjects) {
            dotgit1 = DotGit.getInstance(new File(path1));
            dotgit2 = DotGit.getInstance(new File(path2));
        }
        else {
            dotgit1 = DotGit.getInstance(path1);
            dotgit2 = DotGit.getInstance(path2);
        }

        if (testEquality) {
            assertEquals(dotgit1, dotgit2);
            assertEquals(dotgit1.hashCode(), dotgit2.hashCode());
            assertEquals(dotgit1.getWorkingTree(), dotgit2.getWorkingTree());
        }
        else {
            assertNotEquals(dotgit1, dotgit2);
            assertNotEquals(dotgit1.hashCode(), dotgit2.hashCode());
            assertNotEquals(dotgit1.getWorkingTree(), dotgit2.getWorkingTree());
        }
    }

    private static void assertNotEquals(Object obj1, Object obj2) {
        if (obj1.equals(obj2)) {
            fail("Objects were equal - expected unequal");
        }
        if (obj2.equals(obj1)) {
            fail("Objects were equal - expected unequal");
        }
    }
}
