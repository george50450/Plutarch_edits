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
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import data.dataKeeper.DataManager;
import data.dataKeeper.GlobalDataKeeper;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;

public class LoadTest {

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
	
	@Test
	public void test() throws RecognitionException, IOException {
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
	    testGui = new Gui();
	    
	    for (int i=0; i<projects.length; i++) {
	    	String path = "filesHandler/inis/" + projects[i] + ".ini";
			dataManager = new DataManager(testGui,treeLabel ,tablesTree,sideMenu,tablesTreePanel,treeScrollPane, tabbedPane,zoomAreaTable,LifeTimeTable);
		    
			// Creating a File object that represents the disk file.
		    String filename = "NewData/NewLoad_" + projects[i] + ".txt";
		    PrintStream load = new PrintStream(new File(filename));
		    System.setOut(load);
			dataManager.importData(path);
		    
			System.setOut(console);
			
			File file1 = new File(filename);
			File file2 = new File("OldData/OldLoad_" + projects[i] + ".txt");
			Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
	    }
	    System.out.println("\n\nTEST finished");
	}
}
