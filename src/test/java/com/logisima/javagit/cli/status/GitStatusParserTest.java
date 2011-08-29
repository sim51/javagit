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
package com.logisima.javagit.cli.status;

import java.io.IOException;

import org.junit.Test;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.GitTestCase;
import com.logisima.javagit.test.utilities.ParserTestUtilities;

public class GitStatusParserTest extends GitTestCase {

    @Test
    public void testGitStatusOuput1() throws IOException, JavaGitException {
        final String workingDirectory = "/tmp/";
        GitStatusParser parser = new GitStatusParser(workingDirectory);

        GitStatusResponse response = (GitStatusResponse) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/status/GitStatusOutputTest1");

        // testing
        gitStatusTestEquals(response, "mine", 1, 3, 2, 0, 0, 3);
    }

    @Test
    public void testGitStatusOuput2() throws IOException, JavaGitException {
        final String workingDirectory = "/tmp/";
        GitStatusParser parser = new GitStatusParser(workingDirectory);

        GitStatusResponse response = (GitStatusResponse) ParserTestUtilities.getGitResponse(parser,
                "com/logisima/javagit/cli/status/GitStatusOutputTest2");

        // testing
        gitStatusTestEquals(response, "master", 0, 0, 0, 0, 0, 0);
    }
}
