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
package com.logisima.javagit.utilities;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

/**
 * Test class for edu.nyu.cs.javagit.utiltites.ExceptionMessageMap.
 */
public class ExceptionMessageMapTest extends TestCase {

    @Before
    protected void setUp() {
    }

    @Test
    public void testGetMessage() {
        assertGetMessageValid(null, "NO MESSAGE FOR ERROR CODE. { code=[null] }");
        assertGetMessageValid("", "NO MESSAGE FOR ERROR CODE. { code=[] }");
        assertGetMessageValid("0", "NO MESSAGE FOR ERROR CODE. { code=[0] }");
        assertGetMessageValid("000001", "000001: A String argument was not specified but is required.");
    }

    private void assertGetMessageValid(String code, String expectedMessage) {
        String actualMessage = ExceptionMessageMap.getMessage(code);
        assertEquals("Expected message was not received from ExcpetionMessageMap.getMessage()", expectedMessage,
                actualMessage);
    }

}
