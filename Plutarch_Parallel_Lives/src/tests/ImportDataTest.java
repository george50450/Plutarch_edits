package tests;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import org.antlr.v4.runtime.RecognitionException;
import org.junit.Assert;
import org.junit.Test;
import org.apache.commons.io.*;

import gui.listeners.FileActions;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import data.dataKeeper.DataManager;
import data.dataKeeper.GlobalDataKeeper;

public class ImportDataTest {
	Gui testGui;
	GlobalDataKeeper globalDataKeeper;
	private String[] projects = {"Atlas","biosql","coppermine","ensembl","mwiki","opencart","phpBB","typo3"};
	
	private JScrollPane treeScrollPane= new JScrollPane();
	private JLabel treeLabel;
	private JTree tablesTree=new JTree();
	private JPanel sideMenu=new JPanel();
	private JPanel tablesTreePanel=new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JvTable LifeTimeTable=null;
	private JvTable zoomAreaTable=null;
	private DataManager dataManager;
	private String halfPath = "NewData/NewGlobalDataKeeper_";
	
	@Test
	public void test() throws RecognitionException, IOException {
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
	    testGui = new Gui();
	    
	    for (int i=0; i<projects.length; i++) {
	    	String path = "filesHandler/inis/" + projects[i] + ".ini";
			dataManager = new DataManager(testGui,treeLabel ,tablesTree,sideMenu,tablesTreePanel,treeScrollPane, tabbedPane,zoomAreaTable,LifeTimeTable);
		    dataManager.importData(path);
		    
		    // Creating a File object that represents the disk file.
		    String filename = halfPath + projects[i] + ".txt";
		    PrintStream gui = new PrintStream(new File(filename));
		    // Assign to output stream
		    System.setOut(gui);
		    
		    globalDataKeeper = testGui.getGlobalDataKeeper();
		    
		    System.out.println(globalDataKeeper.getAllPPLSchemas());
		    System.out.println(globalDataKeeper.getAtomicChanges());
		    System.out.println(globalDataKeeper.getTmpTableChanges());
		    System.out.println(globalDataKeeper.getClusterCollectors());
 
		    System.setOut(console);
	    
		    File file1 = new File("OldData/OldGlobalDataKeeper_" + projects[i] + ".txt");
		    File file2 = new File(filename);
		    
		    Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
	    }
	    System.out.println("\n\nTEST finished");
	}
}
