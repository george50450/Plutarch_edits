package tests;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.RecognitionException;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import gui.dialogs.CreateProjectJDialog;

public class CreateTest {
	
	private CreateProjectJDialog createProjectDialog;
	
	@Test
	public void test() throws RecognitionException, IOException {
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
	
	    createProjectDialog = new CreateProjectJDialog("projectNameTest", "filesHandler/datasets/datasetTest.txt", "filesHandler/input/inputCsvTest.csv", "filesHandler/output/ass1Test.txt", "filesHandler/output/ass2Test.txt", "filesHandler/transitions/transXmlTest.xml");
	    createProjectDialog.getButton().doClick();	
	    File file1 = createProjectDialog.getFile();
	    
	    File file2 = new File("NewData/NewCreate.txt");
	    String str = "Project-name:projectNameTest\r\n" + 
	    			"Dataset-txt:filesHandler/datasets/datasetTest.txt\r\n" + 
	    			"Input-csv:filesHandler/input/inputCsvTest.csv\r\n" + 
	    			"Assessement1-output:filesHandler/output/ass1Test.txt\r\n" + 
	    			"Assessement2-output:filesHandler/output/ass2Test.txt\r\n" + 
	    			"Transition-xml:filesHandler/transitions/transXmlTest.xml";
	    BufferedWriter writer = new BufferedWriter(new FileWriter(file2));
	    writer.write(str);
	    writer.close();
    
	    System.setOut(console);
	    Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
	    System.out.println("\n\nTEST finished");
	}
	
}
