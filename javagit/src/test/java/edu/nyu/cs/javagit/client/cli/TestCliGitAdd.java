package edu.nyu.cs.javagit.client.cli;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;

import edu.nyu.cs.javagit.api.JavaGitException;
import edu.nyu.cs.javagit.api.commands.GitAddOptions;
import edu.nyu.cs.javagit.api.commands.GitAddResponse;
import edu.nyu.cs.javagit.client.cli.CliGitAdd;

public class TestCliGitAdd extends TestCase {

  @Before
  public void setUp() throws Exception {
  }

  /**
   * Test for NullPointerException thrown if the repository path is null
   * 
   * @throws IOException
   */
  @Test
  public void testGitAddNullRepositoryPath() throws IOException, JavaGitException {
    try {
      CliGitAdd gitAdd = new CliGitAdd();
      GitAddOptions options = null;
      List<File> files = null;
      gitAdd.add(null, options, files);
    } catch (NullPointerException e) {
      return;
    }
    fail("Failed to throw NullPointerException");
  }

  /**
   * Test for testing IOException when the given repository path does not exist. This is different
   * <code>testGitAddNullRepositoryPath</code> where the repositoryPath is null.
   * 
   * @throws IOException
   * 
   * thrown if file paths or repository path provided is found or git command is not found.
   */
  @Test(expected = IOException.class)
  public void testGitAddIOExceptionThrown() throws IOException, JavaGitException {
    try {
      CliGitAdd gitAdd = new CliGitAdd();
      GitAddOptions options = null;
      List<File> fileNames = new ArrayList<File>();
      fileNames.add(new File("testFile"));
      gitAdd.add(new File("repo/path/should/not/exist"), options, fileNames);
    } catch (IOException e) {
      return;
    }
    fail("IOException not thrown");
  }

  /**
   * Test for parsing the a valid output generated after a couple of files are added to the
   * repository. The output starts with 'add'.
   */
  @Test
  public void testGitAddParserValidInput() {
    CliGitAdd gitAdd = new CliGitAdd();
    CliGitAdd.GitAddParser parser = gitAdd.new GitAddParser();
    parser.parseLine("add 'test-repository/eg9'");
    parser.parseLine("add 'test-repository/testdir/foo'");
    GitAddResponse response = parser.getResponse();
    assertEquals("No of lines in output does not match", 2, response.getFileListSize());
    assertEquals("File name does not match", "eg9", response.get(0).getName());
    assertEquals("File path provided when adding does not match.", "test-repository"
        + File.separator + "eg9", response.get(0).toString());
    assertEquals("File name does not match", "foo", response.get(1).getName());
    assertEquals("File path provided when adding does not match.", "test-repository"
        + File.separator + "testdir" + File.separator + "foo", response.get(1).toString());
  }

  /**
   * This is a test for adding a file to the git repository that is non-existing. Test verifies that
   * error condition is raised in the <code>GitAddResponse</code> and error string is saved.
   * <p>
   * e.g. git-add ./foobar001 fatal: pathspec 'test-repository/foobar001' did not match any files
   * <p>
   * In the above example the file foobar001 is file that is not physically existing inside the
   * repository and this test parsed the error message generated by the git-add command.
   */
  @Test
  public void testGitAddParserNonExistantFile() {
    CliGitAdd gitAdd = new CliGitAdd();
    CliGitAdd.GitAddParser parser = gitAdd.new GitAddParser();
    parser.parseLine("fatal: pathspec 'test-repository/foobar001' did not match any files");
    GitAddResponse response = parser.getResponse();
    assertTrue(response.containsError());
    assertEquals(response.getError(0).error(),
        "fatal: pathspec 'test-repository/foobar001' did not match any files");
  }

}