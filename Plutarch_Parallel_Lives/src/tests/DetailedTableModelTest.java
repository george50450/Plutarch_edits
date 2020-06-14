package tests;

import java.io.File;
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
import org.junit.Assert;
import org.junit.Test;

import data.dataKeeper.DataManager;
import data.dataKeeper.GlobalDataKeeper;
import gui.listeners.TableActions;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;

public class DetailedTableModelTest {

	Gui testGui;
	GlobalDataKeeper globalDataKeeper;
	private String[] projects = {"Atlas","biosql","coppermine","ensembl","mwiki","opencart","phpBB","typo3"};
	
	private JScrollPane treeScrollPane= new JScrollPane();
	private JLabel treeLabel;
	private JTree tablesTree=new JTree();
	private JPanel sideMenu=new JPanel();
	private JPanel tablesTreePanel=new JPanel();
	private DataManager dataManager;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JvTable LifeTimeTable=null;
	private JvTable zoomAreaTable=null;
	private String halfPath = "NewData/New_Detailed_Table_Model_";
	final JMenu mnTable = new JMenu("Table");
	
	@Test
	public void test() throws RecognitionException, IOException {
		PrintStream console = System.out;
		testGui = new Gui();
		
		for (int i=0; i<projects.length; i++) {
	    	String path = "filesHandler/inis/" + projects[i] + ".ini";
			dataManager = new DataManager(testGui,treeLabel ,tablesTree,sideMenu,tablesTreePanel,treeScrollPane, tabbedPane,zoomAreaTable,LifeTimeTable);
		    try {
		    	dataManager.importData(path);
		    } catch (RecognitionException e) {
		    	e.printStackTrace();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    }
		    
		    TableActions tableActions = new TableActions(testGui,treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
	    	tableActions.showDetailedLifeTimeTable();
	    	
	    	// Creating a File object that represents the disk file.
		    String filename1 = halfPath + projects[i] + ".txt";
		    PrintStream detailedTableModelStream = new PrintStream(new File(filename1));
		    System.setOut(detailedTableModelStream);
		    
		    JvTable table = tableActions.getDetailedTableModel().getTmpLifeTimeTable();
		    for (int row = 0; row < table.getRowCount(); row++) {
		        for (int col = 0; col < table.getColumnCount(); col++) {
		        	System.out.print(table.getColumnName(col));
		        	System.out.print(": ");
		        	System.out.println(table.getValueAt(row, col));
		        }
		    }
		    
		    System.setOut(console);
		    
		    File file1 = new File(filename1);
		    File file2 = new File("OldData/OldDetailedTableModel_" + projects[i] + ".txt");
		    Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
		}
		System.out.println("\n\nTEST finished");
	}
}
