package data.dataKeeper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

import gui.tableElements.commons.JvTable;
import gui.tableElements.tableConstructors.TableConstructionAllSquaresIncluded;
import gui.mainEngine.DetailedTableModel;
import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.TableConstructionIDU;

public class TableListener extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	
	private String currentProject=null;
	private GlobalDataKeeper globalDataKeeper=null;
	private Integer[] segmentSizeDetailedTable=new Integer[3];
	private Integer[] segmentSizeZoomArea=new Integer[4];
	private String[] finalColumnsZoomArea=null;
	private String[][] finalRowsZoomArea=null;
	private JvTable LifeTimeTable=null;
	
	private final Gui gui;
	private JMenu mnTable;
	private JTabbedPane tabbedPane;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	
	public TableListener(Gui gui, JMenu mnTable, JTabbedPane tabbedPane, JButton zoomInButton, JButton zoomOutButton, JvTable LifeTimeTable) {
		this.gui = gui;
		this.tabbedPane = tabbedPane;
		this.mnTable = mnTable;
		this.zoomInButton = zoomInButton;
		this.zoomOutButton = zoomOutButton;
		this.LifeTimeTable = LifeTimeTable;
		
		Table();
	}
	
	public void Table() {
	
		JMenuItem mntmShowLifetimeTable = new JMenuItem("Show Full Detailed LifeTime Table");
		mntmShowLifetimeTable.addActionListener(new ActionListener() {
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
				} else {
					JOptionPane.showMessageDialog(null, "Select a Project first");
					return;
				}
			}
		});
		mnTable.add(mntmShowLifetimeTable);
		
		JMenuItem mntmShowGeneralLifetimeIDU = new JMenuItem("Show PLD");
		mntmShowGeneralLifetimeIDU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				currentProject = gui.getCurrentProject();
				globalDataKeeper = gui.getGlobalDataKeeper();
				
				if(!(currentProject==null)){
					//zoomInButton.setVisible(true);
					//zoomOutButton.setVisible(true);
					TableConstructionIDU table=new TableConstructionIDU(globalDataKeeper);
					final String[] columns=table.constructColumns();
					final String[][] rows=table.constructRows();
					segmentSizeZoomArea = table.getSegmentSize();
					System.out.println("Schemas: "+globalDataKeeper.getAllPPLSchemas().size());
					System.out.println("C: "+columns.length+" R: "+rows.length);
	
					finalColumnsZoomArea=columns;
					finalRowsZoomArea=rows;
					tabbedPane.setSelectedIndex(0);
					gui.makeGeneralTableIDU();
					gui.fillTree();
				} else {
					JOptionPane.showMessageDialog(null, "Select a Project first");
					return;
				}
			}
		});
		mnTable.add(mntmShowGeneralLifetimeIDU);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
