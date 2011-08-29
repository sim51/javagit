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
 *  along with JavGit. If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.cli.init;

import java.io.IOException;

import org.junit.Test;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.GitTestCase;
import com.logisima.javagit.test.utilities.ParserTestUtilities;

public class GitInitParserTest extends GitTestCase {

    /**
     * Testing GitInitParser with the follow output file : com/logisima/javagit/cli/init/GitInitOutputTest1.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testGitAddOuput1() throws IOException, JavaGitException {
        GitInitParser parser = new GitInitParser();

        GitInitResponse response = (GitInitResponse) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/init/GitInitOutputTest1");

        // testing
        gitInitTestEquals(response, true, false);
    }

    /**
     * Testing GitInitParser with the follow output file : com/logisima/javagit/cli/init/GitInitOutputTest1.
     * 
     * @throws IOException
     * @throws JavaGitException
     */
    @Test
    public void testGitAddOuput2() throws IOException, JavaGitException {
        GitInitParser parser = new GitInitParser();

        GitInitResponse response = (GitInitResponse) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/init/GitInitOutputTest2");

        // testing
        gitInitTestEquals(response, false, true);
    }
}
