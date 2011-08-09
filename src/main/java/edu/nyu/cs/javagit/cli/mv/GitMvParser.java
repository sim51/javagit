package edu.nyu.cs.javagit.cli.mv;

import java.io.File;

import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.cli.IParser;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Implementation of the <code>IParser</code> interface in GitMvParser class.
 */
public class GitMvParser implements IParser {

    // The response object for an mv operation.
    private GitMvResponseImpl response       = null;

    // While handling the error cases this buffer will have the error messages.
    private StringBuffer      errorMessage   = null;

    // Track the number of lines parsed.
    private int               numLinesParsed = 0;

    /**
     * Parses the line from the git-mv response text.
     * 
     * @param line The line of text to process.
     * 
     */
    public void parseLine(String line) {
        ++numLinesParsed;
        if (null != errorMessage) {
            errorMessage.append(", line" + numLinesParsed + "=[" + line + "]");
            return;
        }
        if (line.startsWith("error") || line.startsWith("fatal")) {
            if (null == errorMessage) {
                errorMessage = new StringBuffer();
            }
            errorMessage.append("line1=[" + line + "]");
        }
        else {
            if (null == response) {
                response = new GitMvResponseImpl();
            }
            // This is to parse the output when -n or -f options were given
            parseLineForSuccess(line);
        }
    }

    /**
     * Parses the line for successful execution.
     * 
     * @param line The line of text to process.
     */
    public void parseLineForSuccess(String line) {
        if (line.contains("Warning:")) {
            response.addComment(line);
        }
        if (line.contains("Adding") || line.contains("Changed")) {
            response.setDestination(new File(line.substring(11)));
        }
        if (line.contains("Deleting")) {
            response.setSource(new File(line.substring(11)));
        }
    }

    public void processExitCode(int code) {
    }

    /**
     * Gets a <code>GitMvResponse</code> object containing the information from the git-mv response text parsed by this
     * IParser instance. It is expected that GitMv does not notify when a move was successful. This follows the response
     * that git-mv itself gives. If the move/rename fails for any reason, proper exception messages are generated and
     * thrown.
     * 
     * @return The <code>GitMvResponse</code> object containing the git-mv's response information. It is expected that
     *         GitMv does not notify when a move was successful. This follows the response that git-mv itself gives. If
     *         the move/rename fails for any reason, proper exception messages are generated and thrown.
     * @throws <code>JavaGitException</code> if there is an error executing git-mv.
     */
    public GitMvResponse getResponse() throws JavaGitException {
        if (null != errorMessage) {
            throw new JavaGitException(424000, ExceptionMessageMap.getMessage("424000")
                    + "  The git-mv error message:  { " + errorMessage.toString() + " }");
        }
        return response;
    }
}
