package gui.tableManager;

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
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;

import data.dataKeeper.DescriptionGenerator;
import data.dataKeeper.GlobalDataKeeper;
import data.dataSorters.PldRowSorter;
import gui.mainEngine.Gui;
import gui.mainEngine.JItemsGenerator;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import gui.tableElements.tableRenderers.IDUHeaderTableRenderer;
import gui.tableElements.tableRenderers.IDUTableRenderer;

public class ZoomTableModel {
	
	public String[][] finalRowsZoomArea;
	public boolean showingPld;
	public JButton zoomInButton,zoomOutButton,showThisToPopup;
	public ArrayList<Integer> selectedRows;
	public MyTableModel zoomModel;
	public String[] finalColumnsZoomArea;
	public int rowHeight;
	public int columnWidth;
	public int wholeCol = -1;
	public Integer[] segmentSize;
	public int wholeColZoomArea;
	public JTextArea descriptionText;
	public int selectedColumnZoomArea;
	public ArrayList<String> selectedFromTree;
	public Integer[] segmentSizeZoomArea;
	public int[] selectedRowsFromMouse;
	public JvTable zoomAreaTable;
	public JScrollPane tmpScrollPaneZoomArea;
	public JPanel lifeTimePanel;
	
	private Gui gui;
	private GlobalDataKeeper globalDataKeeper;
	private JItemsGenerator jItemsHandler;
	
	public ZoomTableModel(Gui gui){
		this.gui = gui;
		this.globalDataKeeper = gui.getGlobalDataKeeper();
		this.finalRowsZoomArea = gui.getFinalRowsZoomArea();
		this.descriptionText = gui.getDescriptionText();
		this.showingPld = true;
		zoomInButton = gui.getZoomInButton();
		zoomOutButton = gui.getZoomOutButton();
		showThisToPopup = gui.getShowThisToPopup() ;
		finalColumnsZoomArea = gui.getFinalColumnsZoomArea();
		rowHeight = gui.getRowHeight();
		columnWidth = gui.getColumnWidth();
		wholeCol = gui.getWholeCol();
		segmentSize = gui.getSegmentSize();
		wholeColZoomArea = gui.getWholeColZoomArea();
		selectedColumnZoomArea = gui.getSelectedColumnZoomArea();
		selectedFromTree = gui.getSelectedFromTree();
		segmentSizeZoomArea = gui.getSegmentSizeZoomArea();
		selectedRowsFromMouse = gui.getselectedRowsFromMouse();
		lifeTimePanel = gui.getLifeTimePanel(); // Maybe from GeneralTableModel
		tmpScrollPaneZoomArea = gui.getTmpScrollPaneZoomArea();
		this.jItemsHandler = new JItemsGenerator();
		
		makeGeneralTableIDU();
	}
	
	
	public void makeGeneralTableIDU(){
		
		PldRowSorter sorter = new PldRowSorter(finalRowsZoomArea,globalDataKeeper);
		
		finalRowsZoomArea = sorter.sortRows();
	    
		showingPld=true;
		zoomInButton.setVisible(true);
		zoomOutButton.setVisible(true);
		
		showThisToPopup.setVisible(true);
		
		int numberOfColumns=finalRowsZoomArea[0].length;
		int numberOfRows=finalRowsZoomArea.length;
		
		selectedRows=new ArrayList<Integer>();
		
		String[][] rows=new String[numberOfRows][numberOfColumns];
		
		for(int i=0; i<numberOfRows; i++){
			
			rows[i][0]=finalRowsZoomArea[i][0];
			
		}
		
		zoomModel=new MyTableModel(finalColumnsZoomArea, rows);
		
		final JvTable generalTable=new JvTable(zoomModel);
		
		generalTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if(rowHeight<1){
			rowHeight=1;
		}
		if (columnWidth<1) {
			columnWidth=1;
		}
		
		for(int i=0; i<generalTable.getRowCount(); i++){
			generalTable.setRowHeight(i, rowHeight);	
		}

		generalTable.setShowGrid(false);
		generalTable.setIntercellSpacing(new Dimension(0, 0));
		
		
		for(int i=0; i<generalTable.getColumnCount(); i++){
			if(i==0){
				generalTable.getColumnModel().getColumn(0).setPreferredWidth(columnWidth);	
			} else {
				generalTable.getColumnModel().getColumn(i).setPreferredWidth(columnWidth);
			}
		}
		
		int start=-1;
		int end=-1;
		if (globalDataKeeper.getPhaseCollectors()!=null && wholeCol!=-1 && wholeCol!=0) {
			start=globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(wholeCol-1).getStartPos();
			end=globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(wholeCol-1).getEndPos();
		}


		if(wholeCol!=-1){
			for(int i=0; i<generalTable.getColumnCount(); i++){
				if(!(generalTable.getColumnName(i).equals("Table name"))){
					if(Integer.parseInt(generalTable.getColumnName(i))>=start && Integer.parseInt(generalTable.getColumnName(i))<=end){
			
						generalTable.getColumnModel().getColumn(i).setHeaderRenderer(new IDUHeaderTableRenderer());
					}
				}
			}
		}
		
		final IDUTableRenderer renderer = new IDUTableRenderer(gui,finalRowsZoomArea, globalDataKeeper, segmentSize);
		
		
		generalTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
		{ 
			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
		    {
		        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        
		        String tmpValue=finalRowsZoomArea[row][column];
		        String columnName=table.getColumnName(column);
		        Color fr=new Color(0,0,0);
		        
		        c.setForeground(fr);
		        setOpaque(true);
		      
		        DescriptionGenerator descriptionGenerator = new DescriptionGenerator(table, value,  isSelected, hasFocus, row,  column, gui);
		        if(column==wholeColZoomArea && wholeColZoomArea!=0){
		        	String description = descriptionGenerator.generateZoomTableModelDescription(tmpValue,wholeColZoomArea,selectedColumnZoomArea,globalDataKeeper,finalRowsZoomArea);
		        	
	        		descriptionText.setText(description);
		        	
		        	Color cl = new Color(255,69,0,100);

	        		c.setBackground(cl);
	        		
	        		return c;
		        } else if(selectedColumnZoomArea==0){
		    		
		        	if (isSelected){
		        		Color cl = new Color(255,69,0,100);
		        		c.setBackground(cl);
		        		
			        	String description = descriptionGenerator.generateZoomTableModelDescription(tmpValue,wholeColZoomArea,selectedColumnZoomArea,globalDataKeeper,finalRowsZoomArea);

		        		descriptionText.setText(description);
		        		
		        		return c;
		        	}
		        } else {
		        	if(selectedFromTree.contains(finalRowsZoomArea[row][0])){

		        		Color cl = new Color(255,69,0,100);
		        		c.setBackground(cl);
		        		
		        		return c;
		        	}
		        	
		        	if (isSelected && hasFocus){

		        		String description="";
		        		if(!table.getColumnName(column).contains("Table name")){
		        			description = descriptionGenerator.generateZoomTableModelDescription(tmpValue,wholeColZoomArea,selectedColumnZoomArea,globalDataKeeper,finalRowsZoomArea);
			        		
		        			descriptionText.setText(description);
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
	        		else if(numericValue> 0&& numericValue<=segmentSizeZoomArea[3]){
	        			
	        			insersionColor=new Color(176,226,255);
		        	}
	        		else if(numericValue>segmentSizeZoomArea[3] && numericValue<=2*segmentSizeZoomArea[3]){
	        			insersionColor=new Color(92,172,238);
	        		}
	        		else if(numericValue>2*segmentSizeZoomArea[3] && numericValue<=3*segmentSizeZoomArea[3]){
	        			
	        			insersionColor=new Color(28,134,238);
	        		}
	        		else{
	        			insersionColor=new Color(16,78,139);
	        		}
	        		c.setBackground(insersionColor);
		        	
		        	return c;
		        } catch(Exception e){

	        		if(tmpValue.equals("")){
	        			c.setBackground(Color.GRAY);
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
			         selectedColumnZoomArea = target.getSelectedColumn();
			         renderer.setSelCol(selectedColumnZoomArea);
			         target.getSelectedColumns();
			         
			         zoomAreaTable.repaint();
				}
			  }
		});
		
		generalTable.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseReleased(MouseEvent e) {
				
					if(SwingUtilities.isRightMouseButton(e)){
						System.out.println("Right Click");

						JTable target1 = (JTable)e.getSource();
						target1.getSelectedColumns();
						selectedRowsFromMouse=target1.getSelectedRows();
						System.out.println(target1.getSelectedColumns().length);
						System.out.println(target1.getSelectedRow());
						for(int rowsSelected=0; rowsSelected<selectedRowsFromMouse.length; rowsSelected++){
							System.out.println(generalTable.getValueAt(selectedRowsFromMouse[rowsSelected], 0));
						}
						final JPopupMenu popupMenu = new JPopupMenu();
				        JMenuItem showDetailsItem = new JMenuItem("Clear Selection");
				        showDetailsItem.addActionListener(new ActionListener() {

				            @Override
				            public void actionPerformed(ActionEvent e) {
				            	selectedFromTree=new ArrayList<String>();
				            	zoomAreaTable.repaint();
				            }
				        });
				        popupMenu.add(showDetailsItem);
				        popupMenu.show(generalTable, e.getX(),e.getY());	    
					}
			   }
		});
		
		
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        wholeColZoomArea = generalTable.columnAtPoint(e.getPoint());
		        renderer.setWholeCol(generalTable.columnAtPoint(e.getPoint()));
		        generalTable.repaint();
		    }
		});
		
		generalTable.getTableHeader().addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseReleased(MouseEvent e) {
		    	if(SwingUtilities.isRightMouseButton(e)){
					System.out.println("Right Click");
					
							final JPopupMenu popupMenu = new JPopupMenu();
					        JMenuItem showDetailsItem = new JMenuItem("Clear Column Selection");
					        showDetailsItem.addActionListener(new ActionListener() {

					            @Override
					            public void actionPerformed(ActionEvent e) {
					            	wholeColZoomArea=-1;
							        renderer.setWholeCol(wholeColZoomArea);

					            	generalTable.repaint();
					            }
					        });
					        popupMenu.add(showDetailsItem);
					        popupMenu.show(generalTable, e.getX(),e.getY());  
				}
		   } 
		});
		
		zoomAreaTable=generalTable;
		tmpScrollPaneZoomArea.setViewportView(zoomAreaTable);
		jItemsHandler.setJScrollPanePosition(tmpScrollPaneZoomArea, 300, 300, 950, 250);
		tmpScrollPaneZoomArea.setAlignmentX(0);
		tmpScrollPaneZoomArea.setAlignmentY(0);
		
        
		lifeTimePanel.setCursor(gui.getCursor());
		lifeTimePanel.add(tmpScrollPaneZoomArea);
	}
	
	public JvTable getZoomAreaTable() {
		return zoomAreaTable;
	}
	
}