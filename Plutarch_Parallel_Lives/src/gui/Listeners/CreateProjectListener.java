package gui.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.antlr.v4.runtime.RecognitionException;

import gui.dialogs.CreateProjectJDialog;
import gui.mainEngine.Gui;

public class CreateProjectListener implements ActionListener {
	
	//private JMenuItem mntmCreateProject;
	private String project=null;
	public String fileName="";
	private final Gui gui;
	
	public CreateProjectListener(Gui gui)
	{
		this.gui = gui;
	}
	

	@Override
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
	

}
