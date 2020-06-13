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
import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;

public class EditTest {

	Gui testGui;
	GlobalDataKeeper globalDataKeeper;
	
	private JScrollPane treeScrollPane= new JScrollPane();
	private JLabel treeLabel;
	private JTree tablesTree=new JTree();
	private JPanel sideMenu=new JPanel();
	private JPanel tablesTreePanel=new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JvTable LifeTimeTable=null;
	private JvTable zoomAreaTable=null;
	private DataManager dataManager;
	private String file = "NewData/NewEdit.txt";
	private CreateProjectJDialog createProjectDialog;
	
	@Test
	public void test() throws RecognitionException, IOException {
		
		testGui = new Gui();
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out;
	    
		createProjectDialog = new CreateProjectJDialog("AtlasTest", "filesHandler/datasets/Atlas.txt", "filesHandler/input/atlas.csv", "filesHandler/output/atlas_Assessment1.txt", "filesHandler/output/atlas_Assessment2.txt", "filesHandler/transitions/atlasTransitions.xml");
	    createProjectDialog.getButton().doClick();	
		
	    String path = "filesHandler/inis/AtlasTest.ini";
		dataManager = new DataManager(testGui, treeLabel ,tablesTree,sideMenu,tablesTreePanel,treeScrollPane, tabbedPane,zoomAreaTable,LifeTimeTable);
		try {
	    	dataManager.importData(path);
	    } catch (RecognitionException e) {
	    	e.printStackTrace();
	    } catch (IOException e) {
	    	e.printStackTrace();
	    }
		
		PrintStream create = new PrintStream(new File(file));
		System.setOut(create);
		
	    System.out.println("Project-name:" + testGui.getProjectName());
	    System.out.println("Dataset-txt:" + testGui.getDatasetTxt());
	    System.out.println("Input-csv:" + testGui.getInputCsv());
	    System.out.println("Assessement1-output:" + testGui.getOutputAssessment1());
	    System.out.println("Assessement2-output:" + testGui.getOutputAssessment2());
	    System.out.println("Transition-xml:" + testGui.getTransitionsFile());
		
	    System.setOut(console);
	    File file1 = new File(path);
		File file2 = new File(file);
	    Assert.assertEquals(FileUtils.readLines(file1), FileUtils.readLines(file2));
		    
	    System.out.println("\n\nTEST finished");
	}
}
