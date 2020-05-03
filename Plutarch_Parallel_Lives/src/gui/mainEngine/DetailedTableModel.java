package gui.mainEngine;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.GroupLayout.Alignment;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;

import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;

public class DetailedTableModel extends JFrame {

	private static final long serialVersionUID = 1L;
	private MyTableModel detailedModel = null;
	private String[] columns;
	private String[][] rows;
	private final boolean levelized;
	
	private int selectedColumn=-1;
	private Integer[] segmentSizeDetailedTable=new Integer[3];
	private JvTable LifeTimeTable=null;
	private ArrayList<Integer> selectedRows=new ArrayList<Integer>();
	
	public DetailedTableModel(String[] columns , String[][] rows, boolean levelized,Integer[] segmentSizeDetailedTable, JvTable LifeTimeTable, int selectedColumn) {
		this.columns = columns;
		this.rows = rows;
		this.levelized = levelized;
		this.segmentSizeDetailedTable = segmentSizeDetailedTable;
		this.LifeTimeTable = LifeTimeTable;
		this.selectedColumn = selectedColumn;
		
		makeDetailedTable(this.columns,this.rows,this.levelized,this.segmentSizeDetailedTable);
	}
	
	public void makeDetailedTable(String[] columns , String[][] rows, final boolean levelized, final Integer[] segmentSizeDetailedTable){
		detailedModel = new MyTableModel(columns,rows);
		final JvTable tmpLifeTimeTable= new JvTable(detailedModel);
		tmpLifeTimeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		if(levelized==true){
			for(int i=0; i<tmpLifeTimeTable.getColumnCount(); i++){
				if(i==0){
					tmpLifeTimeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
					tmpLifeTimeTable.getColumnModel().getColumn(0).setMaxWidth(150);
					tmpLifeTimeTable.getColumnModel().getColumn(0).setMinWidth(150);
				} else {
					if(tmpLifeTimeTable.getColumnName(i).contains("v")){
						tmpLifeTimeTable.getColumnModel().getColumn(i).setPreferredWidth(100);
						tmpLifeTimeTable.getColumnModel().getColumn(i).setMaxWidth(100);
						tmpLifeTimeTable.getColumnModel().getColumn(i).setMinWidth(100);
					} else {
						tmpLifeTimeTable.getColumnModel().getColumn(i).setPreferredWidth(25);
						tmpLifeTimeTable.getColumnModel().getColumn(i).setMaxWidth(25);
						tmpLifeTimeTable.getColumnModel().getColumn(i).setMinWidth(25);
					}
				}
			}
		} else {
			for(int i=0; i<tmpLifeTimeTable.getColumnCount(); i++){
				if(i==0){
					tmpLifeTimeTable.getColumnModel().getColumn(0).setPreferredWidth(150);
					tmpLifeTimeTable.getColumnModel().getColumn(0).setMaxWidth(150);
					tmpLifeTimeTable.getColumnModel().getColumn(0).setMinWidth(150);
				} else {
					tmpLifeTimeTable.getColumnModel().getColumn(i).setPreferredWidth(20);
					tmpLifeTimeTable.getColumnModel().getColumn(i).setMaxWidth(20);
					tmpLifeTimeTable.getColumnModel().getColumn(i).setMinWidth(20);
				}
			}
		}
		
		tmpLifeTimeTable.setName("LifeTimeTable");
		
		tmpLifeTimeTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {

			private static final long serialVersionUID = 1L;

			@Override
		    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		        final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        
		        String tmpValue=(String) table.getValueAt(row, column);
		        String columnName=table.getColumnName(column);
		        Color fr=new Color(0,0,0);
		        c.setForeground(fr);
		        
		        if(selectedColumn==0){
		        	if (isSelected){
		        		Color cl = new Color(255,69,0, 100);
		        		c.setBackground(cl);
		        		
		        		return c;
		        	}
		        }
		        else{
		        	if (isSelected && hasFocus){
		        		c.setBackground(Color.YELLOW);
		        		return c;
			        }
		        }
		        
		        try{
		        	int numericValue=Integer.parseInt(tmpValue);
		        	Color insersionColor=null;
		        	
		        	if(columnName.equals("I")){
		        		if(numericValue==0){
		        			insersionColor=new Color(255,231,186);
		        		}
		        		else if(numericValue>0 && numericValue<=segmentSizeDetailedTable[0]){
		        			
		        			insersionColor=new Color(193,255,193);
			        	}
		        		else if(numericValue>segmentSizeDetailedTable[0] && numericValue<=2*segmentSizeDetailedTable[0]){
		        			insersionColor=new Color(84,255,159);
		        		}
		        		else if(numericValue>2*segmentSizeDetailedTable[0] && numericValue<=3*segmentSizeDetailedTable[0]){
		        			
		        			insersionColor=new Color(0,201,87);
		        		}
		        		else{
		        			insersionColor=new Color(0,100,0);
		        		}
		        		c.setBackground(insersionColor);
		        	}
		        	
		        	if(columnName.equals("U")){
		        		if(numericValue==0){
		        			insersionColor=new Color(255,231,186);
		        		}
		        		else if(numericValue>0 && numericValue<=segmentSizeDetailedTable[1]){
		        			
		        			insersionColor=new Color(176,226,255);
			        	}
		        		else if(numericValue>segmentSizeDetailedTable[1] && numericValue<=2*segmentSizeDetailedTable[1]){
		        			insersionColor=new Color(92,172,238);
		        		}
		        		else if(numericValue>2*segmentSizeDetailedTable[1] && numericValue<=3*segmentSizeDetailedTable[1]){
		        			
		        			insersionColor=new Color(28,134,238);
		        		}
		        		else{
		        			insersionColor=new Color(16,78,139);
		        		}
		        		c.setBackground(insersionColor);
		        	}
		        	if(columnName.equals("D")){
		        		if(numericValue==0){
		        			insersionColor=new Color(255,231,186);
		        		}
		        		else if(numericValue>0 && numericValue<=segmentSizeDetailedTable[2]){
		        			insersionColor=new Color(255,106,106);
			        	}
		        		else if(numericValue>segmentSizeDetailedTable[2] && numericValue<=2*segmentSizeDetailedTable[2]){
		        			insersionColor=new Color(255,0,0);
		        		}
		        		else if(numericValue>2*segmentSizeDetailedTable[2] && numericValue<=3*segmentSizeDetailedTable[2]){
		        			
		        			insersionColor=new Color(205,0,0);
		        		}
		        		else{
		        			insersionColor=new Color(139,0,0);
		        		}
		        		c.setBackground(insersionColor);
		        	}
		        	
		        	return c;
		        }
		        catch(Exception e){
		        		if(tmpValue.equals("")){
		        			c.setBackground(Color.black);
			        		return c; 
		        		} else {
		        			if(columnName.contains("v")){
		        				c.setBackground(Color.lightGray);
		        				if(levelized==false){
		        					setToolTipText(columnName);
		        				}
		        			} else {
		        				Color tableNameColor=new Color(205,175,149);
		        				c.setBackground(tableNameColor);
		        			}
			        		return c; 
		        		}	
		        }
		    }
		});
		
		tmpLifeTimeTable.setOpaque(true);
	    
		tmpLifeTimeTable.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	    tmpLifeTimeTable.getSelectionModel().addListSelectionListener(new RowListener());
	    tmpLifeTimeTable.getColumnModel().getSelectionModel().addListSelectionListener(new ColumnListener());

	    JScrollPane detailedScrollPane=new JScrollPane();
	    detailedScrollPane.setViewportView(tmpLifeTimeTable);
	    detailedScrollPane.setAlignmentX(0);
	    detailedScrollPane.setAlignmentY(0);
	    detailedScrollPane.setBounds(0,0,1280,650);
	    detailedScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	    detailedScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
       
	    detailedScrollPane.setCursor(getCursor());
	    
	    JDialog detailedDialog=new JDialog();
	    detailedDialog.setBounds(100, 100, 1300, 700);
	    
	    JPanel panelToAdd=new JPanel();
	    
	    GroupLayout gl_panel = new GroupLayout(panelToAdd);
	    gl_panel.setHorizontalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
		);
	    gl_panel.setVerticalGroup(
	    		gl_panel.createParallelGroup(Alignment.LEADING)
		);
		panelToAdd.setLayout(gl_panel);
	    
	    panelToAdd.add(detailedScrollPane);
	    detailedDialog.getContentPane().add(panelToAdd);
	    detailedDialog.setVisible(true);
	}
	
	private class RowListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
            int selectedRow=LifeTimeTable.getSelectedRow();
            selectedRows.add(selectedRow);
        }
    }
	
	private class ColumnListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent event) {
            if (event.getValueIsAdjusting()) {
                return;
            }
        }
    }
}