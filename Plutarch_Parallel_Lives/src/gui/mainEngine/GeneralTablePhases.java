package gui.mainEngine;

/* move it out... showSelectionToZoomArea after added to makeZoomAreaTableForCluster
 * extract makeGeneralTableIDU() after... to be used from all...
 * */

import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import gui.tableElements.tableConstructors.PldConstruction;
import gui.tableElements.tableConstructors.TableConstructionClusterTablesPhasesZoomA;
import gui.tableElements.tableConstructors.TableConstructionIDU;
import gui.tableElements.tableConstructors.TableConstructionWithClusters;
import gui.tableElements.tableConstructors.TableConstructionZoomArea;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import data.dataKeeper.GlobalDataKeeper;

public class GeneralTablePhases {

	private static final long serialVersionUID = 1L;
	private MyTableModel generalModel = null;
	private ArrayList<Integer> selectedRows=new ArrayList<Integer>();
	private String[][] finalRows=null;
	private String[] finalColumns=null;
	private int wholeCol=-1;
	private int selectedColumn=-1;
	private ArrayList<String> selectedFromTree=new ArrayList<String>();
	private Integer[] segmentSize=new Integer[4];
	private int[] selectedRowsFromMouse;
	private ArrayList<String> tablesSelected = new ArrayList<String>(); // maybe needs get...
	private boolean showingPld=false;
	private JScrollPane tmpScrollPane =new JScrollPane();
	private JPanel lifeTimePanel = new JPanel();
	private String[] finalColumnsZoomArea=null;
	private String[][] finalRowsZoomArea=null;
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private JTextArea descriptionText;
	
	private JvTable LifeTimeTable=null;
	private JButton uniformlyDistributedButton;
	private JButton notUniformlyDistributedButton;
	private Integer[] segmentSizeZoomArea=new Integer[4];
	
	Gui gui;
	GlobalDataKeeper globalDataKeeper;
	
	public GeneralTablePhases(Gui gui) {
		this.gui = gui;
		this.globalDataKeeper = gui.getGlobalDataKeeper();
		this.descriptionText = gui.getDescriptionText();
		
		this.selectedFromTree = gui.getSelectedFromTree();
		this.wholeCol = gui.getWholeCol();
		this.segmentSize = gui.getSegmentSize();
		this.showingPld= gui.getShowingPld();
		this.finalRows = gui.getFinalRows();
		this.finalColumns = gui.getFinalColumns();

		uniformlyDistributedButton = gui.getUniformlyDistributedButton();
		uniformlyDistributedButton.setVisible(true);
		
		notUniformlyDistributedButton = gui.getNotUniformlyDistributedButton();
		notUniformlyDistributedButton.setVisible(true);
		
		makeGeneralTablePhases();
	}
	
	public void makeGeneralTablePhases() {
		int numberOfColumns=finalRows[0].length;
		int numberOfRows=finalRows.length;
		
		selectedRows=new ArrayList<Integer>();
		
		String[][] rows=new String[numberOfRows][numberOfColumns];
		
		for(int i=0; i<numberOfRows; i++){
			rows[i][0]=finalRows[i][0];
		}
		
		generalModel=new MyTableModel(finalColumns, rows);
		
		final JvTable generalTable=new JvTable(generalModel);
		
		generalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		generalTable.setShowGrid(false);
		generalTable.setIntercellSpacing(new Dimension(0, 0));
		
		for(int i=0; i<generalTable.getColumnCount(); i++){
			if(i==0){
				generalTable.getColumnModel().getColumn(0).setPreferredWidth(86);
				
			} else {
				generalTable.getColumnModel().getColumn(i).setPreferredWidth(1);
			}
		}
		
		generalTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{
		    
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        	        
		        String tmpValue=finalRows[row][column];
		        String columnName=table.getColumnName(column);
		        Color fr=new Color(0,0,0);
		        c.setForeground(fr);
		        
		        if(column==wholeCol && wholeCol!=0){
		        	
		        	String description=table.getColumnName(column)+"\n";
		          	description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
	        				get(column-1).getStartPos()+"\n";
	        		description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
	        				get(column-1).getEndPos()+"\n";
	        		description=description+"Total Changes For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
	        				get(column-1).getTotalUpdates()+"\n";
	        		description=description+"Additions For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
	        				get(column-1).getTotalAdditionsOfPhase()+"\n";
	        		description=description+"Deletions For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
	        				get(column-1).getTotalDeletionsOfPhase()+"\n";
	        		description=description+"Updates For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
	        				get(column-1).getTotalUpdatesOfPhase()+"\n";

	        		descriptionText.setText(description);
	        		
		        	Color cl = new Color(255,69,0,100);

	        		c.setBackground(cl);
	        		return c;
		        }
		        else if(selectedColumn==0){
		        	if (isSelected){
		        		
		        		if(finalRows[row][0].contains("Cluster")){
			        		String description="Cluster:"+finalRows[row][0]+"\n";
			        		description=description+"Birth Version Name:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getBirthSqlFile()+"\n";
			        		description=description+"Birth Version ID:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getBirth()+"\n";
			        		description=description+"Death Version Name:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getDeathSqlFile()+"\n";
			        		description=description+"Death Version ID:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getDeath()+"\n";
			        		description=description+"Tables:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getNamesOfTables().size()+"\n";
			        		description=description+"Total Changes:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getTotalChanges()+"\n";
			       
			        		descriptionText.setText(description);
		        		}
		        		else{
			        		String description="Table:"+finalRows[row][0]+"\n";
			        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirth()+"\n";
			        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirthVersionID()+"\n";
			        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeath()+"\n";
			        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeathVersionID()+"\n";
			        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getTotalChanges()+"\n";

			        		descriptionText.setText(description);
		        		}
		        		
		        		Color cl = new Color(255,69,0,100);
		        		
		        		c.setBackground(cl);
		        		return c;
		        	}
		        }
		        else{
		        	
		        	if(selectedFromTree.contains(finalRows[row][0])){

		        		Color cl = new Color(255,69,0,100);
		        		
		        		c.setBackground(cl);
		        		
		        		return c;
		        	}
		        	
		        	if (isSelected && hasFocus){
			        	
		        		if(!table.getColumnName(column).contains("Table name")){
		        			
			        		if(finalRows[row][0].contains("Cluster")){

				        		String description=finalRows[row][0]+"\n";
				        		description=description+"Tables:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getNamesOfTables().size()+"\n\n";

				        		description=description+table.getColumnName(column)+"\n";
				        		description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
				        				get(column-1).getStartPos()+"\n";
				        		description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
				        				get(column-1).getEndPos()+"\n\n";
				        		description=description+"Total Changes For This Phase:"+tmpValue+"\n";

				        		descriptionText.setText(description);
			        		}
			        		else{
			        			String description=table.getColumnName(column)+"\n";
				        		description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
				        				get(column-1).getStartPos()+"\n";
				        		description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
				        				get(column-1).getEndPos()+"\n\n";
			        			description=description+"Table:"+finalRows[row][0]+"\n";
				        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirth()+"\n";
				        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirthVersionID()+"\n";
				        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeath()+"\n";
				        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeathVersionID()+"\n";
				        		description=description+"Total Changes For This Phase:"+tmpValue+"\n";

				        		descriptionText.setText(description);
			        		}

		        		}
		        		
		        		Color cl = new Color(255,69,0,100);
		        		
		        		c.setBackground(cl);
		        		return c;
			        }
		        }
		        try{
		        	int numericValue=Integer.parseInt(tmpValue);
		        	Color insersionColor=null;
					setToolTipText(Integer.toString(numericValue));

		        	
	        		if(numericValue==0){
	        			insersionColor=new Color(154,205,50,200);
	        		}
	        		else if(numericValue> 0&& numericValue<=segmentSize[3]){
	        			
	        			insersionColor=new Color(176,226,255);
		        	}
	        		else if(numericValue>segmentSize[3] && numericValue<=2*segmentSize[3]){
	        			insersionColor=new Color(92,172,238);
	        		}
	        		else if(numericValue>2*segmentSize[3] && numericValue<=3*segmentSize[3]){
	        			
	        			insersionColor=new Color(28,134,238);
	        		}
	        		else{
	        			insersionColor=new Color(16,78,139);
	        		}
	        		c.setBackground(insersionColor);
		        	
		        	return c;
		        }
		        catch(Exception e){
		        		   	
	        		if(tmpValue.equals("")){
	        			c.setBackground(Color.gray);
	        			return c; 
	        		} else {
	        			if(columnName.contains("v")){
	        				c.setBackground(Color.lightGray);
	        				setToolTipText(columnName);
	        			} else {
	        				Color tableNameColor=new Color(205,175,149);
	        				c.setBackground(tableNameColor);
	        			}
		        		return c; 
	        		}	
		        }
		    }
		});
		
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
				
				if (e.getClickCount() == 1) {
					JTable target = (JTable)e.getSource();
			         selectedRowsFromMouse = target.getSelectedRows();
			         selectedColumn = target.getSelectedColumn();
			         LifeTimeTable.repaint();
				}
			   }
		});
		
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseReleased(MouseEvent e) {
					if(e.getButton() == MouseEvent.BUTTON3){
						System.out.println("Right Click");

						JTable target1 = (JTable)e.getSource();
						selectedColumn=target1.getSelectedColumn();
						selectedRowsFromMouse=new int[target1.getSelectedRows().length];
						selectedRowsFromMouse=target1.getSelectedRows();
						
						final String sSelectedRow = (String) generalTable.getValueAt(target1.getSelectedRow(),0);
						tablesSelected = new ArrayList<String>();

						for(int rowsSelected=0; rowsSelected<selectedRowsFromMouse.length; rowsSelected++){
							tablesSelected.add((String) generalTable.getValueAt(selectedRowsFromMouse[rowsSelected], 0));
						}
						
						JPopupMenu popupMenu = new JPopupMenu();
				        JMenuItem showDetailsItem = new JMenuItem("Show Details for the selection");
				        showDetailsItem.addActionListener(new ActionListener() {

				            @Override
				            public void actionPerformed(ActionEvent le) {
				            	if(sSelectedRow.contains("Cluster ")){
				            		showClusterSelectionToZoomArea(selectedColumn,sSelectedRow);

				            	} else {
				            		showSelectionToZoomArea(selectedColumn);
				            	}
				            }
				        });
				        popupMenu.add(showDetailsItem);
				        JMenuItem clearSelectionItem = new JMenuItem("Clear Selection");
				        clearSelectionItem.addActionListener(new ActionListener() {

				            @Override
				            public void actionPerformed(ActionEvent le) {
				            	
				            	selectedFromTree=new ArrayList<String>();
				            	LifeTimeTable.repaint();
				            }
				        });
				        popupMenu.add(clearSelectionItem);
				        popupMenu.show(generalTable, e.getX(),e.getY());      
					}
			   }
		});
		
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        wholeCol = generalTable.columnAtPoint(e.getPoint());
		        String name = generalTable.getColumnName(wholeCol);
		        System.out.println("Column index selected " + wholeCol + " " + name);
		        generalTable.repaint();
		        if (showingPld) {
			        new ZoomTableModel(gui);
				}
		    }
		});
		
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	if(SwingUtilities.isRightMouseButton(e)){
					System.out.println("Right Click");
					
							final JPopupMenu popupMenu = new JPopupMenu();
					        JMenuItem clearColumnSelectionItem = new JMenuItem("Clear Column Selection");
					        clearColumnSelectionItem.addActionListener(new ActionListener() {

					            @Override
					            public void actionPerformed(ActionEvent e) {
					            	wholeCol=-1;
					            	generalTable.repaint();
					            	if(showingPld){
					            	     new ZoomTableModel(gui);
					            	}
					            }
					        });
					        popupMenu.add(clearColumnSelectionItem);
					        JMenuItem showDetailsItem = new JMenuItem("Show Details for this Phase");
					        showDetailsItem.addActionListener(new ActionListener() {

					            @Override
					            public void actionPerformed(ActionEvent e) {
									String sSelectedRow=finalRows[0][0];
									System.out.println("?"+sSelectedRow);
					            	tablesSelected=new ArrayList<String>();
					            	for(int i=0; i<finalRows.length; i++)
					            		tablesSelected.add((String) generalTable.getValueAt(i, 0));

					            	if(!sSelectedRow.contains("Cluster ")){	
					            		showSelectionToZoomArea(wholeCol);	
					            	} else {
					            		showClusterSelectionToZoomArea(wholeCol, "");
					            	}
					            }
					        });
					        popupMenu.add(showDetailsItem);
					        popupMenu.show(generalTable, e.getX(),e.getY());    
				}
		   }
		});
		
		LifeTimeTable=generalTable;
		
		tmpScrollPane.setViewportView(LifeTimeTable);
		tmpScrollPane.setAlignmentX(0);
		tmpScrollPane.setAlignmentY(0);
	    tmpScrollPane.setBounds(300,30,950,265);
	    tmpScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    tmpScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    
	    lifeTimePanel = gui.getLifeTimePanel();
		lifeTimePanel.setCursor(gui.getCursor());
		lifeTimePanel.add(tmpScrollPane);
	}
	
	private void showClusterSelectionToZoomArea(int selectedColumn,String selectedCluster){

		ArrayList<String> tablesOfCluster=new ArrayList<String>();
		for(int i=0; i <tablesSelected.size(); i++){
			String[] selectedClusterSplit= tablesSelected.get(i).split(" ");
			int cluster=Integer.parseInt(selectedClusterSplit[1]);
			ArrayList<String> namesOfTables=globalDataKeeper.getClusterCollectors().get(0).getClusters().get(cluster).getNamesOfTables();
			for(int j=0; j<namesOfTables.size(); j++){
				tablesOfCluster.add(namesOfTables.get(j));
			}
			System.out.println(tablesSelected.get(i));
		}
		
		PldConstruction table;
		if (selectedColumn==0) {
			table= new TableConstructionClusterTablesPhasesZoomA(globalDataKeeper,tablesOfCluster);
		}
		else{
			table= new TableConstructionZoomArea(globalDataKeeper,tablesOfCluster,selectedColumn);
		}
		final String[] columns=table.constructColumns();
		final String[][] rows=table.constructRows();
		segmentSizeZoomArea = table.getSegmentSize();
		System.out.println("Schemas: "+globalDataKeeper.getAllPPLSchemas().size());
		System.out.println("C: "+columns.length+" R: "+rows.length);

		finalColumnsZoomArea=columns;
		finalRowsZoomArea=rows;
		tabbedPane = gui.getTabbedPane();
		tabbedPane.setSelectedIndex(0);
		gui.makeZoomAreaTableForCluster(); // extract, need it in gui also
	}
	
	private void showSelectionToZoomArea(int selectedColumn){
		
		TableConstructionZoomArea table=new TableConstructionZoomArea(globalDataKeeper,tablesSelected,selectedColumn);
		final String[] columns=table.constructColumns();
		final String[][] rows=table.constructRows();
		segmentSizeZoomArea = table.getSegmentSize();

		System.out.println("Schemas: "+globalDataKeeper.getAllPPLSchemas().size());
		System.out.println("C: "+columns.length+" R: "+rows.length);

		finalColumnsZoomArea=columns;
		finalRowsZoomArea=rows;
		tabbedPane = gui.getTabbedPane();
		tabbedPane.setSelectedIndex(0);
		gui.makeZoomAreaTable(); // extract it only here is used
	}
	
	public JvTable getLifeTimeTable() {
		return LifeTimeTable;
	}
}