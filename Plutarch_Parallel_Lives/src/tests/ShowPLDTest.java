package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import org.antlr.v4.runtime.RecognitionException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import gui.listeners.TableActions;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import data.dataKeeper.DataManager;
import data.dataKeeper.GlobalDataKeeper;
import static org.junit.Assert.*;


public class ShowPLDTest {
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
	final JMenu mnTable = new JMenu("Table");

	@Test
	public void test() {
		
	    PrintStream console = System.out;
	    testGui = new Gui();
	    
	    for (int i=0; i<projects.length; i++) {
	    	String path = "filesHandler/inis/" + projects[i] + ".ini";
	    	dataManager = new DataManager(testGui,treeLabel ,tablesTree,sideMenu,tablesTreePanel,treeScrollPane, tabbedPane,zoomAreaTable,LifeTimeTable);
		    try {
				dataManager.importData(path);
			} catch (RecognitionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    
		    
		    globalDataKeeper = testGui.getGlobalDataKeeper();
		    
		    TableActions tableAction = new TableActions(testGui,mnTable,treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
			tableAction.showPLD();
			
			// Creating a File object that represents the disk file.
		    String filename = "NewData/NewShowPLDInfo_" + projects[i] + ".txt";
		    PrintStream gui;
			try {
				gui = new PrintStream(new File(filename));
				// Assign gui to output stream
			    System.setOut(gui);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] finalColumnsZoomArea = tableAction.getFinalColumnsZoomArea();
			String[][] finalRowsZoomArea = tableAction.getFinalRowsZoomArea();
						
			int numberofRows = finalRowsZoomArea.length;
			int numberofColumns = finalColumnsZoomArea.length;
			
			for (int p=0; p<numberofColumns; p++) {
				System.out.println(finalColumnsZoomArea[p]);
			}
			
			System.out.println("Number of Rows: " + numberofRows);
			System.out.println("Number of Columns: " + numberofColumns);
			
			System.setOut(console);
			
			
			File file1 = new File("NewData/NewShowPLDInfo_" + projects[i] + ".txt");
		    File file2 = new File("OldData/OldShowPLDInfo_" + projects[i] + ".txt");
		    
		 
		    try {
		    	assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		    		    
	    }
	    
	    System.out.println("\n\nTEST finished");
	   
	}

}
