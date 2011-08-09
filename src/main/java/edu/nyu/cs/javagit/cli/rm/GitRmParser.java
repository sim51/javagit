package edu.nyu.cs.javagit.cli.rm;

import java.io.File;

import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.cli.IParser;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

public class GitRmParser implements IParser {

    // Holding onto the error message to make part of an exception
    private StringBuffer      errorMsg       = null;

    // Track the number of lines parsed.
    private int               numLinesParsed = 0;

    // The response.
    private GitRmResponseImpl response       = new GitRmResponseImpl();

    public void parseLine(String line) {

        // TODO (jhl388): handle error messages in a better manner.

        if (null != errorMsg) {
            ++numLinesParsed;
            errorMsg.append(", line" + numLinesParsed + "=[" + line + "]");
            return;
        }

        if (line.startsWith("rm '")) {
            int locQuote = line.indexOf('\'');
            int locLastQuote = line.lastIndexOf('\'');
            response.addFileToRemovedFilesList(new File(line.substring(locQuote + 1, locLastQuote)));
        }
        else {
            errorMsg = new StringBuffer();
            errorMsg.append("line1=[" + line + "]");
        }
    }

    public void processExitCode(int code) {
    }

    public GitRmResponse getResponse() throws JavaGitException {
        if (null != errorMsg) {
            throw new JavaGitException(434000, ExceptionMessageMap.getMessage("434000") + "  { " + errorMsg.toString()
                    + " }");
        }
        return response;
    }
}
