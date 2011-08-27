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
package com.logisima.javagit.test.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.logisima.javagit.JavaGitConfiguration;
import com.logisima.javagit.JavaGitException;

/**
 * This class contains methods that implement some git commands that aren't coded into javagit yet but which are
 * required to test other commands already written.
 */
public class HelperGitCommands {

    /**
     * Initialize a git repository.
     * 
     * @param repoDirectory The root directory of the repository.
     * @throws IOException If IO errors happen.
     * @throws JavaGitException If errors happen while initializing the repo.
     */
    public static void initRepo(File repoDirectory) throws IOException, JavaGitException {
        List<String> cmdLine = new ArrayList<String>();

        cmdLine.add(JavaGitConfiguration.getGitCommand());
        cmdLine.add("init");

    }

}
