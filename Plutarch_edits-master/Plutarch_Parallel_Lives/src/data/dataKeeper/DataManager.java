package data.dataKeeper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import org.antlr.v4.runtime.RecognitionException;

import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import gui.tableManager.FillTableManager;
import gui.treeManager.FillTreeManager;

public class DataManager {
	
	private JLabel treeLabel;
	private JTree tablesTree;
	private JPanel sideMenu;
	private JPanel tablesTreePanel;
	private JScrollPane treeScrollPane;
	
	private String projectName;
	private String datasetTxt;
	private String inputCsv;
	private String outputAssessment1;
	private String outputAssessment2;
	
	private String transitionsFile;
	private String currentProject;
	
	private String project;
	
	GlobalDataKeeper globalDataKeeper;
	FillTreeManager fillTreeManager; 
	Gui gui;
	
	
	
	private JTabbedPane tabbedPane;
	private JvTable zoomAreaTable;
	private JvTable LifeTimeTable;
	
	public DataManager(Gui gui, JLabel treeLabel,  JTree tablesTree, JPanel sideMenu, JPanel tablesTreePanel, JScrollPane treeScrollPane,   JTabbedPane tabbedPane, JvTable zoomAreaTable, JvTable LifeTimeTable)
	{
		this.gui = gui;
		this.treeScrollPane = treeScrollPane;
		this.treeLabel = treeLabel;
		this.tablesTree = tablesTree;
		this.sideMenu = sideMenu;
		this.tablesTreePanel = tablesTreePanel;
		fillTreeManager = new FillTreeManager(treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
		
		this.tabbedPane = tabbedPane;
		this.zoomAreaTable = zoomAreaTable;
		this.LifeTimeTable = LifeTimeTable;
	}
	
	public void importData(String fileName) throws IOException, RecognitionException {
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line;
		
		while(true) {
			line = br.readLine();
			if (line == null) 
				break;
			if(line.contains("Project-name")){
				String[] projectNameTable=line.split(":");
				projectName=projectNameTable[1];
			}
			else if(line.contains("Dataset-txt")){
				String[] datasetTxtTable=line.split(":");
				datasetTxt=datasetTxtTable[1];
			}
			else if(line.contains("Input-csv")){
				String[] inputCsvTable=line.split(":");
				inputCsv=inputCsvTable[1];
			}
			else if(line.contains("Assessement1-output")){
				String[] outputAss1=line.split(":");
				outputAssessment1=outputAss1[1];
			}
			else if(line.contains("Assessement2-output")){
				String[] outputAss2=line.split(":");
				outputAssessment2=outputAss2[1];
			}
			else if(line.contains("Transition-xml")){
				String[] transitionXmlTable=line.split(":");
				transitionsFile=transitionXmlTable[1];
			}	
		};	
		
		br.close();
		
		System.out.println("Project Name:"+projectName);
		System.out.println("Dataset txt:"+datasetTxt);
		System.out.println("Input Csv:"+inputCsv);
		System.out.println("Output Assessment1:"+outputAssessment1);
		System.out.println("Output Assessment2:"+outputAssessment2);
		System.out.println("Transitions File:"+transitionsFile);

		globalDataKeeper=new GlobalDataKeeper(datasetTxt,transitionsFile);
		globalDataKeeper.setData();
		System.out.println(globalDataKeeper.getAllPPLTables().size());
		
        System.out.println(fileName);
        
        updateGuiFields();
        
       
        FillTableManager fillTableManager = new FillTableManager(gui,globalDataKeeper,tabbedPane, zoomAreaTable, LifeTimeTable, treeLabel,  tablesTree, sideMenu,  tablesTreePanel, treeScrollPane, inputCsv, outputAssessment1, outputAssessment2);
        
        fillTableManager.fillTable();
       
        fillTreeManager.fillTree(globalDataKeeper);

		currentProject=fileName;
	}
	
	public void updateGuiFields()
	{
		gui.setProjectName(projectName);
		gui.setDatasetTxt(datasetTxt);
		gui.setInputCsv(inputCsv);
		gui.setOutputAssessment1(outputAssessment1);
		gui.setOutputAssessment2(outputAssessment2);
		gui.setTransitionsFile(transitionsFile);
	}
}
