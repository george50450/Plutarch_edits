package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Test;

import gui.mainEngine.Gui;
import data.dataKeeper.GlobalDataKeeper;

import static org.junit.Assert.*;

import org.junit.Test;

public class OldData {

	Gui testGui;
	GlobalDataKeeper globalDataKeeper;
	String[] projects = {"Atlas","biosql","coppermine","ensembl","mwiki","opencart","phpBB","typo3"};
	
	@Test
	public void test() throws RecognitionException, IOException{
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
		
	    testGui = new Gui();
	    
	    for (int i=0; i<projects.length; i++) {
	    
	    	String path = "C:\\Users\\gtkal\\git\\Plutarch_Parallel_Lives\\filesHandler\\inis\\" + projects[i] + ".ini";
		    testGui.importData(path); //C:\Users\gtkal\git\Plutarch_Parallel_Lives\filesHandler\inis
		    
			// Creating a File object that represents the disk file.
		    String filename = "OldGui_" + projects[i] + ".txt";
		    PrintStream gui = new PrintStream(new File(filename));
		    // Assign o to output stream
		    System.setOut(gui);
		    
		    globalDataKeeper = testGui.getGlobalDataKeeper();
		    
		    System.out.println(globalDataKeeper.getAllPPLSchemas());
		    System.out.println(globalDataKeeper.getAllPPLTables());
		    System.out.println(globalDataKeeper.getAtomicChanges());
		    //System.out.println(Gui.globalDataKeeper.getAllTableChanges());
		    System.out.println(globalDataKeeper.getTmpTableChanges());
		    System.out.println(globalDataKeeper.getAllPPLTransitions());
		    System.out.println(globalDataKeeper.getPhaseCollectors());
		    System.out.println(globalDataKeeper.getClusterCollectors());
	    }
	    
	    
	}
}
