package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import org.antlr.v4.runtime.RecognitionException;

import data.dataKeeper.DataManager;
import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;

public class FileActions  {

	private String project=null;
	public String fileName=null;
	public String projectName="";
	public String datasetTxt="";
	public String inputCsv="";
	public String outputAssessment1="";
	public String outputAssessment2="";
	public String transitionsFile="";
	
	private final Gui gui;
	private JMenu mnFile;
	
	
	private JLabel treeLabel;
	private JTree tablesTree;
	private JPanel sideMenu;
	private JPanel tablesTreePanel;
	private JScrollPane treeScrollPane;
	
	DataManager dataManager;
	
	public FileActions(Gui gui, JMenu mnFile,JLabel treeLabel,  JTree tablesTree, JPanel sideMenu, JPanel tablesTreePanel, JScrollPane treeScrollPane, JTabbedPane tabbedPane, JvTable zoomAreaTable, JvTable LifeTimeTable) {
		this.gui = gui;
		this.mnFile = mnFile;
		
		this.treeScrollPane = treeScrollPane;
		this.treeLabel = treeLabel;
		this.tablesTree = tablesTree;
		this.sideMenu = sideMenu;
		this.tablesTreePanel = tablesTreePanel;
		dataManager = new DataManager(gui,treeLabel ,tablesTree,sideMenu,tablesTreePanel,treeScrollPane,  tabbedPane,zoomAreaTable,LifeTimeTable);
	}
	
	public void createProjectAction() {
		CreateProjectJDialog createProjectDialog=new CreateProjectJDialog("","","","","","");
		createProjectDialog.setModal(true);
		createProjectDialog.setVisible(true);
		
		if(createProjectDialog.getConfirmation()){
			
			createProjectDialog.setVisible(false);
			
			File file = createProjectDialog.getFile();
            System.out.println(file.toString());
            project=file.getName();
            fileName=file.toString();
            System.out.println("!!"+project);
            
    		try {
    			dataManager.importData(fileName);
    		} catch (IOException e) {
    			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
    			return;
    		} catch (RecognitionException e) {
    			
    			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
    			return;
    		} 
		}
	}
	
		
	public void loadProjectAction() {
		File dir=new File("filesHandler/inis");
		JFileChooser fcOpen1 = new JFileChooser();
		fcOpen1.setCurrentDirectory(dir);
		int returnVal = fcOpen1.showDialog(null, "Open");
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fcOpen1.getSelectedFile();
            System.out.println(file.toString());
            project=file.getName();
            fileName=file.toString();
            System.out.println("!!"+project);
            
		} else {
			return;
		}
	
		try {
			dataManager.importData(fileName);
		} catch (RecognitionException e) {
			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editProjectAction() {
		File dir=new File("filesHandler/inis");
		JFileChooser fcOpen1 = new JFileChooser();
		fcOpen1.setCurrentDirectory(dir);
		int returnVal = fcOpen1.showDialog(gui, "Open");
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			
            File file = fcOpen1.getSelectedFile();
            System.out.println(file.toString());
            project=file.getName();
            fileName=file.toString();
            System.out.println("!!"+project);
          
            BufferedReader br;
			try {
				br = new BufferedReader(new FileReader(fileName));
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
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println(projectName);
			CreateProjectJDialog createProjectDialog=new CreateProjectJDialog(projectName,datasetTxt,inputCsv,outputAssessment1,outputAssessment2,transitionsFile);
		
			createProjectDialog.setModal(true);
			
			createProjectDialog.setVisible(true);
			
			if(createProjectDialog.getConfirmation()){
				
				createProjectDialog.setVisible(false);
				
				file = createProjectDialog.getFile();
	            System.out.println(file.toString());
	            project=file.getName();
	            fileName=file.toString();
	            System.out.println("!!"+project);
			
				try {
					dataManager.importData(fileName);
				} catch (IOException e2) {
					JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
					return;
				} catch (RecognitionException e2) {
					
					JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
					return;
				}
			}
		} else {
			return;
		}
	
	}


}
