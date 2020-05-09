package gui.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.antlr.v4.runtime.RecognitionException;

import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;

public class FileListener implements ActionListener {

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
	
	public FileListener(Gui gui, JMenu mnFile) {
		this.gui = gui;
		this.mnFile = mnFile;
		
		File();
	}
	
	public void File() {
		
		JMenuItem mntmCreateProject = new JMenuItem("Create Project");
		mntmCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

				CreateProjectJDialog createProjectDialog=new CreateProjectJDialog("","","","","","");
				createProjectDialog.setModal(true);
				createProjectDialog.setVisible(true);
				
				if(createProjectDialog.getConfirmation()){
					
					createProjectDialog.setVisible(false);
					
					File file = createProjectDialog.getFile();
		            System.out.println(file.toString());
		            project=file.getName();
		            String fileName=file.toString();
		            System.out.println("!!"+project);
		            
		    		try {
		    			gui.importData(fileName);
		    		} catch (IOException e) {
		    			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
		    			return;
		    		} catch (RecognitionException e) {
		    			
		    			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
		    			return;
		    		} 
				}
			}
		});
		mnFile.add(mntmCreateProject);
		
		
		JMenuItem mntmLoadProject = new JMenuItem("Load Project");
		mntmLoadProject.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			
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
				gui.importData(fileName);
				
			} catch (RecognitionException e) {
				
				JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
				return;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		});
		mnFile.add(mntmLoadProject);
		
		JMenuItem mntmEditProject = new JMenuItem("Edit Project");
		mntmEditProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String fileName=null;
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
							gui.importData(fileName);
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
		});
		mnFile.add(mntmEditProject);
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}