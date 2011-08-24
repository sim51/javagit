package edu.nyu.cs.javagit.cli.reset;

import java.io.File;

import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.cli.IParser;
import edu.nyu.cs.javagit.object.Ref;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

public class GitResetParser implements IParser {

    // TODO (jhl388): Create test case for this class.
    // TODO (jhl388): Finish implementing the GitResetParser.

    // The index of the start of the short SHA1 in the HEAD record. Result of the --hard option
    private final int            HEAD_RECORD_SHA1_START = 15;

    /*
     * The working directory path set for the command line. Used to generate the correct paths to the files needing
     * update.
     */
    private String               workingDirectoryPath;

    // Holding onto the error message to make part of an exception
    private StringBuffer         errorMsg               = null;

    // Track the number of lines parsed.
    private int                  numLinesParsed         = 0;

    // The response object for a reset.
    private GitResetResponseImpl response;

    /**
     * Constructor for <code>GitResetParser</code>
     * 
     * @param workingDirectoryPath The working directory path set for the command line.
     */
    public GitResetParser(String workingDirectoryPath) {
        this.workingDirectoryPath = workingDirectoryPath;
    }

    public void parseLine(String line) {

        // TODO (jhl388): handle error messages in a better manner.

        if (null != errorMsg) {
            ++numLinesParsed;
            errorMsg.append(", line" + numLinesParsed + "=[" + line + "]");
            return;
        }

        if (line.startsWith("HEAD ")) {
            // A record indicating the new HEAD commit resulting from using the --hard option.
            int sha1End = line.indexOf(' ', HEAD_RECORD_SHA1_START);
            Ref sha1 = Ref.createSha1Ref(line.substring(HEAD_RECORD_SHA1_START, sha1End));
            response = new GitResetResponseImpl(sha1, line.substring(sha1End + 1));
        }
        else if (numLinesParsed > 0 && response.getNewHeadSha1() != null) {
            // No line is expected after getting a HEAD record. Doing nothing for now. Must revisit.

            // TODO (jhl388): Figure out what to do if a line is received after a HEAD record.
        }
        else if (line.endsWith(": needs update")) {
            // A file needs update record.
            int lastColon = line.lastIndexOf(":");
            File f = new File(workingDirectoryPath + line.substring(0, lastColon));
            response.addFileToFilesNeedingUpdateList(f);
        }
        else if (numLinesParsed > 0) {
            errorMsg = new StringBuffer();
            errorMsg.append("Unexpected results.  line" + (numLinesParsed + 1) + "=[" + line + "]");
        }
        else {
            errorMsg = new StringBuffer();
            errorMsg.append("line1=[" + line + "]");
        }

        ++numLinesParsed;
    }

    public void processExitCode(int code) {
    }

    /**
     * Gets a <code>GitResetResponseImpl</code> object containing the information from the reset response text parsed by
     * this IParser instance.
     * 
     * @return The <code>GitResetResponseImpl</code> object containing the reset's response information.
     */
    public GitResetResponseImpl getResponse() throws JavaGitException {
        if (null != errorMsg) {
            throw new JavaGitException(432000, ExceptionMessageMap.getMessage("432000")
                    + "  The git-reset error message:  { " + errorMsg.toString() + " }");
        }
        return response;
    }

    /**
     * Gets the number of lines of response text parsed by this IParser.
     * 
     * @return The number of lines of response text parsed by this IParser.
     */
    public int getNumLinesParsed() {
        return numLinesParsed;
    }
}
