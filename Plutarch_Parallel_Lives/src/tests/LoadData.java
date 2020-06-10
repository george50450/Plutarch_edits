package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JMenu;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

import gui.listeners.FileActions;
import gui.listeners.TestFileListener;
import gui.mainEngine.Gui;
import data.dataKeeper.GlobalDataKeeper;

public class LoadData {
	/*
	Gui testGui;
	GlobalDataKeeper globalDataKeeper;
	String[] projects = {"Atlas"};//,"biosql","coppermine","ensembl","mwiki","opencart","phpBB","typo3"};
	
	@Test
	public void test() throws RecognitionException, IOException {
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
		
	    testGui = new Gui();
	    
	    for (int i=0; i<projects.length; i++) {
	    	String path = "C:\\Users\\gtkal\\Desktop\\new_test_github\\Plutarch_edits-master\\Plutarch_Parallel_Lives\\filesHandler\\inis" + projects[i] + ".ini";
			
			TestFileListener testFileListener =new TestFileListener(testGui, projects[i] + ".ini");
			testFileListener.LoadProject();
		    
			// Creating a File object that represents the disk file.
		    String filename = "NewData\\NewGuiGlobalDataKeeper_" + projects[i] + ".txt";
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
		    String filename2 = "NewData\\\\NewGuiFileInfo_" + projects[i] + ".txt";
		    PrintStream gui2 = new PrintStream(new File(filename2));
		    // Assign gui to output stream
		    System.setOut(gui2);
		    
		    System.out.println(testGui.getProjectName());
		    System.out.println(testGui.getDatasetTxt());
		    System.out.println(testGui.getInputCsv());
		    System.out.println(testGui.getOutputAssessment1());
		    System.out.println(testGui.getOutputAssessment2());
		    System.out.println(testGui.getTransitionsFile());
		    
		    System.setOut(console);
		    
		    //FileUtils.contentEquals("OldData\\OldGuiGlobalDataKeeper_" + projects[i] + ".txt", "NewData\\NewGuiGlobalDataKeeper_" + projects[i] + ".txt");
	    
		    // Compare files
			BufferedReader reader1 = new BufferedReader(new FileReader("C:\\Users\\gtkal\\Desktop\\Plutarch_Parallel_Lives-master_Refactor\\OldData\\OldGuiGlobalDataKeeper_" + projects[i] + ".txt"));
			BufferedReader reader2 = new BufferedReader(new FileReader("C:\\Users\\gtkal\\Desktop\\Plutarch_Parallel_Lives-master_Refactor\\NewData\\NewGuiGlobalDataKeeper_" + projects[i] + ".txt"));
			
			String line1 = reader1.readLine();
	        String line2 = reader2.readLine();
	         
	        boolean areEqual = true;
	        int lineNum = 1;
	         
	        while (line1 != null || line2 != null) {
	            if(line1 == null || line2 == null) {
	                areEqual = false;
	                break;
	            } else if(! line1.equalsIgnoreCase(line2)) {
	                areEqual = false;
	                break;
	            }
	            
	            line1 = reader1.readLine();
	            line2 = reader2.readLine();
	            
	            lineNum++;
	        }
	        
	        if(areEqual) {
	            System.out.println("Two files have same content.");
	        } else {
	            System.out.println("Two files have different content. They differ at line: "+lineNum);
	            
	            System.out.println("File1 has: "+line1+" and File2 has: "+line2+" at line: "+lineNum);
	        }
	         
	        reader1.close();
	        reader2.close();
	        System.out.println("TEST finished");
	    }
	}*/
}
