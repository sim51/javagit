/*
 * ====================================================================
 * Copyright (c) 2008 JavaGit Project.  All rights reserved.
 *
 * This software is licensed using the GNU LGPL v2.1 license.  A copy
 * of the license is included with the distribution of this source
 * code in the LICENSE.txt file.  The text of the license can also
 * be obtained at:
 *
 *   http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 *
 * For more information on the JavaGit project, see:
 *
 *   http://www.javagit.com
 * ====================================================================
 */
package edu.nyu.cs.javagit.api.commands;

import java.io.File;
import java.io.IOException;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.Ref;
import edu.nyu.cs.javagit.client.ClientManager;
import edu.nyu.cs.javagit.client.IClient;
import edu.nyu.cs.javagit.client.IGitShow;
import edu.nyu.cs.javagit.utilities.CheckUtilities;

/**
 * <code>GitShow</code> provides an interface for show files or directory tree into git repository
 */
public final class GitShow {

    public GitShowResponse show(File repositoryPath, GitShowOptions options, File path, Ref revision)
            throws IOException, JavaGitException {
        CheckUtilities.checkFileValidity(repositoryPath.getAbsoluteFile());
        IClient client = ClientManager.getInstance().getPreferredClient();
        IGitShow gitShow = client.getGitShowInstance();
        return gitShow.show(repositoryPath, options, path, revision);
    }
}
