package tests;

import static org.junit.Assert.*;

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

import data.dataKeeper.DataManager;
import data.dataKeeper.GlobalDataKeeper;
import gui.listeners.TableActions;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;

public class ShowPhasesWithClustersPLDTest {
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
			tableAction.showPhasesWithClustersPLD();

			// Creating a File object that represents the disk file.
		    String filename = "NewData/NewShowPhasesWithClustersPLDInfo_" + projects[i] + ".txt";
		    PrintStream gui;
			try {
				gui = new PrintStream(new File(filename));
				// Assign gui to output stream
			    System.setOut(gui);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			String[] finalColumns = tableAction.getFinalColumns();
			String[][] finalRows = tableAction.getFinalRows();
			
			int numberOfRows = finalRows.length;
			for (int k = 0; k < numberOfRows; k++) {
				for (int j = 0; j < finalRows[k].length; j++) {
					System.out.println(finalRows[k][j]);
				}
			}
			
			int numberOfCols = finalColumns.length;
			for (int p=0; p<numberOfCols; p++) {
				System.out.println(finalColumns[p]);
			}
			
			globalDataKeeper = tableAction.getGlobalDataKeeper();
			System.out.println(globalDataKeeper.getAllPPLSchemas());
			System.out.println(globalDataKeeper.getAtomicChanges());
			System.out.println(globalDataKeeper.getTmpTableChanges());
			System.out.println(globalDataKeeper.getClusterCollectors());
			
			
			System.setOut(console);
			
			File file1 = new File("OldData/OldShowPhasesWithClustersPLDInfo_" + projects[i] + ".txt");
		    File file2 = new File("NewData/NewShowPhasesWithClustersPLDInfo_" + projects[i] + ".txt");
		    
		    
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