package gui.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import data.dataKeeper.GlobalDataKeeper;
import gui.dialogs.ProjectInfoDialog;
import gui.mainEngine.Gui;

public class InfoListener implements ActionListener {
	
	private final Gui gui;
	private JMenu menuFile;
	private JMenuBar menuBar;
	private JMenu mnProject;
	private JMenuItem mntmInfo;
	
	private String currentProject;
	private String projectName;
	private String datasetTxt;
	private String inputCsv;
	private String transitionsFile;
	private GlobalDataKeeper globalDataKeeper;
	private String outputAssessment1;
	private String outputAssessment2;
	
	public InfoListener(Gui gui, JMenu mnFile,JMenuBar menuBar) {
		this.gui = gui;
		this.menuFile = mnFile;
		this.menuBar = menuBar;
		
		showInfo();
		Help();
	}
	
	public void Help() {
		JButton buttonHelp=new JButton("Help");
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				////
				getDataFromGui();
				///
				
				String message ="To open a project, you must select a .txt file that contains the names ONLY of " +
									"the SQL files of the dataset that you want to use."+"\n" +
									"The .txt file must have EXACTLY the same name with the folder " +
									"that contains the DDL Scripts of the dataset."+ "\n" +
									"Both .txt file and dataset folder must be in the same folder.";
				JOptionPane.showMessageDialog(null,message); 				
			}
		});
		
		buttonHelp.setBounds(900,900 , 80, 40);
		menuBar.add(buttonHelp);
	}
	

	public void showInfo() {
		mnProject = new JMenu("Project");
		menuBar.add(mnProject);
		
		mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				getDataFromGui();
				
				if(!(currentProject==null)){
					
					////
					getDataFromGui();
					///
					
					System.out.println("Project Name:"+projectName);
					System.out.println("Dataset txt:"+datasetTxt);
					System.out.println("Input Csv:"+inputCsv);
					System.out.println("Output Assessment1:"+outputAssessment1);
					System.out.println("Output Assessment2:"+outputAssessment2);
					System.out.println("Transitions File:"+ transitionsFile);
					
					System.out.println("Schemas:"+globalDataKeeper.getAllPPLSchemas().size());
					System.out.println("Transitions:"+globalDataKeeper.getAllPPLTransitions().size());
					System.out.println("Tables:"+globalDataKeeper.getAllPPLTables().size());
					
					
					ProjectInfoDialog infoDialog = new ProjectInfoDialog(projectName,datasetTxt,inputCsv,transitionsFile,globalDataKeeper.getAllPPLSchemas().size(),
							globalDataKeeper.getAllPPLTransitions().size(), globalDataKeeper.getAllPPLTables().size());
					
					infoDialog.setVisible(true);
				} else {
					JOptionPane.showMessageDialog(null, "Select a Project first");
					return;
				}	
			}
		});
		
		mnProject.add(mntmInfo);
	}
	
	
	public void getDataFromGui() {
		currentProject = gui.getCurrentProject();
		projectName = gui.getProjectName();
		datasetTxt = gui.getDatasetTxt();
		inputCsv = gui.getInputCsv();
		outputAssessment1 = gui.getOutputAssessment1();
		outputAssessment2 = gui.getOutputAssessment2();
		transitionsFile = gui.getTransitionsFile();
		globalDataKeeper = gui.getGlobalDataKeeper();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}