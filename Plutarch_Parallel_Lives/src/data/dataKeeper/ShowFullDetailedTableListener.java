package data.dataKeeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import gui.tableElements.commons.JvTable;
import gui.tableElements.tableConstructors.TableConstructionAllSquaresIncluded;
import gui.mainEngine.DetailedTableModel;
import gui.mainEngine.Gui;

public class ShowFullDetailedTableListener extends JFrame implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String currentProject=null;
	private GlobalDataKeeper globalDataKeeper=null;
	private Integer[] segmentSizeDetailedTable=new Integer[3];
	private JTabbedPane tabbedPane;
	private JvTable LifeTimeTable=null;
	
	private final Gui gui;
	
	public ShowFullDetailedTableListener(Gui gui, JTabbedPane tabbedPane, JvTable LifeTimeTable) {
		this.gui = gui;
		this.tabbedPane = tabbedPane;
		this.LifeTimeTable = LifeTimeTable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		currentProject = gui.getCurrentProject();
		globalDataKeeper = gui.getGlobalDataKeeper();
		
		if(!(currentProject==null)){
			TableConstructionAllSquaresIncluded table=new TableConstructionAllSquaresIncluded(globalDataKeeper);
			final String[] columns=table.constructColumns();
			final String[][] rows=table.constructRows();
			segmentSizeDetailedTable=table.getSegmentSize();
			tabbedPane.setSelectedIndex(0);
			new DetailedTableModel(columns,rows,true,segmentSizeDetailedTable, LifeTimeTable);
		}
		else{
			JOptionPane.showMessageDialog(null, "Select a Project first");
			return;
		}
	}

}
