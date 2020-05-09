package tests;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

import gui.mainEngine.Gui;
import data.dataKeeper.GlobalDataKeeper;

public class OldData {
	
	Gui testGui;
	GlobalDataKeeper globalDataKeeper;
	String[] projects = {"Atlas","biosql","coppermine","ensembl","mwiki","opencart","phpBB","typo3"};
	
	@Test
	public void test() throws RecognitionException, IOException {
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
		
	    testGui = new Gui();
	    
	    for (int i=0; i<projects.length; i++) {
	    	String path = "C:\\Users\\Toni\\Desktop\\Soft&Data_Evol\\Plutarch_Parallel_Lives-master\\filesHandler\\inis\\" + projects[i] + ".ini";
		    testGui.importData(path); 
		    
			// Creating a File object that represents the disk file.
		    String filename = "OldData\\OldGuiGlobalDataKeeper_" + projects[i] + ".txt";
		    PrintStream gui = new PrintStream(new File(filename));
		    // Assign gui to output stream
		    System.setOut(gui);
		    
		    globalDataKeeper = testGui.getGlobalDataKeeper();
		    
		    System.out.println(globalDataKeeper.getAllPPLSchemas());
		    System.out.println(globalDataKeeper.getAllPPLTables());
		    System.out.println(globalDataKeeper.getAtomicChanges());
		    System.out.println(globalDataKeeper.getTmpTableChanges());
		    System.out.println(globalDataKeeper.getAllPPLTransitions());
		    System.out.println(globalDataKeeper.getPhaseCollectors());
		    System.out.println(globalDataKeeper.getClusterCollectors());
		    
		    // Creating a File object that represents the disk file.
		    String filename2 = "OldData\\\\OldGuiFileInfo_" + projects[i] + ".txt";
		    PrintStream gui2 = new PrintStream(new File(filename2));
		    // Assign gui to output stream
		    System.setOut(gui2);
		    
		    System.out.println(testGui.getProjectName());
		    System.out.println(testGui.getDatasetTxt());
		    System.out.println(testGui.getInputCsv());
		    System.out.println(testGui.getOutputAssessment1());
		    System.out.println(testGui.getOutputAssessment2());
		    System.out.println(testGui.getTransitionsFile());
		    
		    //FileUtils.contentEquals(file1,file2);
	    }
	}
}
