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
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with JavGit. If not, see <http://www.gnu.org/licenses/>.
 *  
 *  @author Benoît Simard
 *  @See https://github.com/sim51/javagit
 */
package com.logisima.javagit.test.utilities;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.logisima.javagit.JavaGitException;
import com.logisima.javagit.cli.Parser;
import com.logisima.javagit.cli.Response;
import com.logisima.javagit.utilities.ExceptionMessageMap;

public class ParserTestUtilities {

    /**
     * Method to get the <code>ICommandResponse</code> from a parser and a file. This method is only to test parser.
     * 
     * @param parser
     * @param outputGitCmd file that reprsesent a output of a git command.
     * @return <code>ICommandResponse</code>
     * @throws IOException
     * @throws JavaGitException
     */
    public static Response getGitResponse(Parser parser, String outputGitCmd) throws IOException, JavaGitException {
        BufferedReader br = new BufferedReader(new InputStreamReader(ParserTestUtilities.class.getClassLoader()
                .getResourceAsStream(outputGitCmd)));
        while (true) {
            String str;
            try {
                str = br.readLine();
                if (null == str) {
                    break;
                }
                parser.parseLine(str);
            } catch (IOException e) {
                IOException toThrow = new IOException(ExceptionMessageMap.getMessage("020101"));
                toThrow.initCause(e);
                throw toThrow;
            }
        }
        return parser.getResponse();
    }
}
