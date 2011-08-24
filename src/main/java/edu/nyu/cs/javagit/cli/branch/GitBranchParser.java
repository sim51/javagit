package edu.nyu.cs.javagit.cli.branch;

import java.util.StringTokenizer;

import edu.nyu.cs.javagit.JavaGitException;
import edu.nyu.cs.javagit.cli.IParser;
import edu.nyu.cs.javagit.cli.branch.GitBranchResponse.BranchRecord;
import edu.nyu.cs.javagit.cli.branch.GitBranchResponse.responseType;
import edu.nyu.cs.javagit.object.Ref;
import edu.nyu.cs.javagit.utilities.ExceptionMessageMap;

/**
 * Implementation of the <code>IParser</code> interface in GitBranchParser class.
 */
public class GitBranchParser implements IParser {

    // The response object for a branch operation.
    private GitBranchResponseImpl response;

    // While handling the error cases this buffer will have the error messages.
    private StringBuffer          errorMessage   = null;

    // Track the number of lines parsed.
    private int                   numLinesParsed = 0;

    /**
     * Parses the line from the git-branch response text.
     * 
     * @param line The line of text to process.
     */
    public void parseLine(String line) {
        ++numLinesParsed;
        if (null != errorMessage) {
            errorMessage.append(", line" + numLinesParsed + "=[" + line + "]");
            return;
        }
        if (line.contains("fatal:") || line.contains("error:")) {
            if (null == errorMessage) {
                errorMessage = new StringBuffer();
            }
            errorMessage.append("line1=[" + line + "]");
        }
        else {
            if (null == response) {
                response = new GitBranchResponseImpl();
            }

            if (line.startsWith("Deleted branch")) {
                int indexOfBranch = line.indexOf("branch");
                String branchName = line.substring(indexOfBranch + 7, line.length() - 1);
                response.setResponseType(responseType.MESSAGE);
                if (1 == numLinesParsed) {
                    response.addMessages(line.substring(0, indexOfBranch + 6));
                }
                response.addIntoBranchList(Ref.createBranchRef(branchName));
            }
            else if (null == line) {
                response.setResponseType(responseType.EMPTY);
            }
            else {
                handleBranchDisplay(line);
            }
        }
    }

    /**
     * Parses the output of git-branch with different options and without any argumen.
     * 
     * @param line The line of text to be parsed.
     */
    public void handleBranchDisplay(String line) {
        String nextWord;
        boolean isCurrentBranch = false;
        StringTokenizer st = new StringTokenizer(line);

        nextWord = st.nextToken();
        response.setResponseType(responseType.BRANCH_LIST);

        if ('*' == nextWord.charAt(0)) {
            isCurrentBranch = true;
            nextWord = st.nextToken();
            response.setCurrentBranch(Ref.createBranchRef(nextWord));
        }
        response.addIntoBranchList(Ref.createBranchRef(nextWord));

        if (st.hasMoreTokens()) {
            Ref branch = Ref.createBranchRef(nextWord);
            nextWord = st.nextToken();
            Ref sha1 = Ref.createSha1Ref(nextWord);
            int indexOfSha = line.indexOf(nextWord);
            String comment = line.substring(indexOfSha + nextWord.length() + 1);
            BranchRecord record = new BranchRecord(branch, sha1, comment, isCurrentBranch);
            response.addIntoListOfBranchRecord(record);
        }
    }

    public void processExitCode(int code) {
    }

    /**
     * Throws appropriate <code>JavaGitException</code> for an error case or returns the <code>GitBranchResponse</code>
     * object to the upper layer.
     * 
     * @return GitBranchResponse object.
     * @throws JavaGitException Thrown when there is an error executing git-branch.
     */
    public GitBranchResponse getResponse() throws JavaGitException {
        if (null != errorMessage) {
            throw new JavaGitException(404000, ExceptionMessageMap.getMessage("404000")
                    + "  The git-branch error message:  { " + errorMessage.toString() + " }");
        }
        return response;
    }
}
