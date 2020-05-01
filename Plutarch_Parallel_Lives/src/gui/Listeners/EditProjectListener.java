package gui.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.antlr.v4.runtime.RecognitionException;

import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;

public class EditProjectListener implements ActionListener{

	private final Gui gui;
	private String project=null;
	public String projectName="";
	public String datasetTxt="";
	public String inputCsv="";
	public String outputAssessment1="";
	public String outputAssessment2="";
	public String transitionsFile="";
	
	public EditProjectListener(Gui gui)
	{
		this.gui = gui;
	}

	
	@Override
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
			
		}
		else{
			return;
		}
		
	}
}
