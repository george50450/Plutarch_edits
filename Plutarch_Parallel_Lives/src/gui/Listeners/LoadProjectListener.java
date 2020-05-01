package gui.Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.antlr.v4.runtime.RecognitionException;

import gui.mainEngine.Gui;

public class LoadProjectListener implements ActionListener{
	
	private String project=null;
	public String fileName=null;

	private final Gui gui;
	
	public LoadProjectListener(Gui gui)
	{
		this.gui = gui;
	}
	
	@Override
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
          

		}
		else{
			return;
		}
		
		
	
		try {
			
			//new ImportData(gui,fileName);
			gui.importData(fileName);
		
		} catch (RecognitionException e) {
			
			JOptionPane.showMessageDialog(null, "Something seems wrong with this file");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
