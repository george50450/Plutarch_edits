package gui.mainEngine;

import gui.Listeners.FileListener;
import gui.Listeners.InfoListener;
import gui.Listeners.TableListener;
//try to extract relationship beetween gui and pplSchema and pplTransition
import gui.dialogs.EnlargeTable;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import gui.tableElements.tableConstructors.PldConstruction;
import gui.tableElements.tableConstructors.TableConstructionClusterTablesPhasesZoomA;
import gui.tableElements.tableConstructors.TableConstructionIDU;
import gui.tableElements.tableConstructors.TableConstructionWithClusters;
import gui.tableElements.tableConstructors.TableConstructionZoomArea;
import gui.tableElements.tableRenderers.IDUHeaderTableRenderer;
import gui.tableElements.tableRenderers.IDUTableRenderer;
import gui.treeElements.TreeConstructionGeneral;
import gui.treeElements.TreeConstructionPhases;
import gui.treeElements.TreeConstructionPhasesWithClusters;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.TreePath;

import org.antlr.v4.runtime.RecognitionException;

import phaseAnalyzer.engine.PhaseAnalyzerMainEngine;
import tableClustering.clusterExtractor.engine.TableClusteringMainEngine;
import tableClustering.clusterValidator.engine.ClusterValidatorMainEngine;
import data.dataKeeper.GlobalDataKeeper;
import data.dataSorters.PldRowSorter;

public class Gui extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel lifeTimePanel = new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	
	private MyTableModel generalModel = null;
	private MyTableModel zoomModel = null;

	private JvTable LifeTimeTable=null;
	private JvTable zoomAreaTable=null;
	
	
	private JScrollPane tmpScrollPane =new JScrollPane();
	private JScrollPane treeScrollPane= new JScrollPane();
	private JScrollPane tmpScrollPaneZoomArea =new JScrollPane();
	
	
	private ArrayList<Integer> selectedRows=new ArrayList<Integer>();
	private GlobalDataKeeper globalDataKeeper=null;

	
	private String[] finalColumns=null;
	private String[][] finalRows=null;
	
	private String[] finalColumnsZoomArea=null;
	private String[][] finalRowsZoomArea=null;
	private String[] firstLevelUndoColumnsZoomArea=null;
	private String[][] firstLevelUndoRowsZoomArea=null;
	private String currentProject=null;
	private String project=null;
	
	
	private Integer[] segmentSize=new Integer[4];
	private Integer[] segmentSizeZoomArea=new Integer[4];
	private Integer[] segmentSizeDetailedTable=new Integer[3];

	
	private Float timeWeight=null;
	private Float changeWeight=null;
	private Double birthWeight=null;
	private Double deathWeight=null;
	private Double changeWeightCl=null;

	private Integer numberOfPhases=null;
	private Integer numberOfClusters=null;
	private Boolean preProcessingTime=null;
	private Boolean preProcessingChange=null;
	
	private String projectName="";
	private String datasetTxt="";
	private String inputCsv="";
	private String outputAssessment1="";
	private String outputAssessment2="";
	private String transitionsFile="";
	private ArrayList<String> selectedFromTree=new ArrayList<String>();
	
	private JTree tablesTree=new JTree();
	private JPanel sideMenu=new JPanel();
	private JPanel tablesTreePanel=new JPanel();
	private JPanel descriptionPanel=new JPanel();
	private JLabel treeLabel;
	private JLabel generalTableLabel;
	private JLabel zoomAreaLabel;
	private JLabel descriptionLabel;
	private JTextArea descriptionText;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton uniformlyDistributedButton;
	private JButton notUniformlyDistributedButton;
	private JButton showThisToPopup;


	private int[] selectedRowsFromMouse;
	private int selectedColumn=-1;
	private int selectedColumnZoomArea=-1;
	private int wholeCol=-1;
	private int wholeColZoomArea=-1;
	
	private int rowHeight=1;
	private int columnWidth=1;

	private ArrayList<String> tablesSelected = new ArrayList<String>();

	private boolean showingPld=false;
	
	private JButton undoButton;
	private JMenu mnProject;
	private JMenuItem mntmInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Gui frame = new Gui();
					frame.setVisible(true);
				} catch (Exception e) {
					//return;
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setResizable(false);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		// ======================================================================
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		new FileListener(this, mnFile);
		
		// ======================================================================
		JMenu mnTable = new JMenu("Table");
		menuBar.add(mnTable);
		
		new TableListener(this,mnTable);
		
		sideMenu.setBounds(0, 0, 280, 600);
		sideMenu.setBackground(Color.DARK_GRAY);
		
		GroupLayout gl_sideMenu = new GroupLayout(sideMenu);
		gl_sideMenu.setHorizontalGroup(
				gl_sideMenu.createParallelGroup(Alignment.LEADING)
		);
		gl_sideMenu.setVerticalGroup(
				gl_sideMenu.createParallelGroup(Alignment.LEADING)
		);
		
		sideMenu.setLayout(gl_sideMenu);
		
		tablesTreePanel.setBounds(10, 400, 260, 180);
		tablesTreePanel.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout gl_tablesTreePanel = new GroupLayout(tablesTreePanel);
		gl_tablesTreePanel.setHorizontalGroup(
				gl_tablesTreePanel.createParallelGroup(Alignment.LEADING)
		);
		gl_tablesTreePanel.setVerticalGroup(
				gl_tablesTreePanel.createParallelGroup(Alignment.LEADING)
		);
		
		tablesTreePanel.setLayout(gl_tablesTreePanel);
		
		treeLabel=new JLabel();
		treeLabel.setBounds(10, 370, 260, 40);
		treeLabel.setForeground(Color.WHITE);
		treeLabel.setText("Tree");
		
		descriptionPanel.setBounds(10, 190, 260, 180);
		descriptionPanel.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout gl_descriptionPanel = new GroupLayout(descriptionPanel);
		gl_descriptionPanel.setHorizontalGroup(
				gl_descriptionPanel.createParallelGroup(Alignment.LEADING)
		);
		gl_descriptionPanel.setVerticalGroup(
				gl_descriptionPanel.createParallelGroup(Alignment.LEADING)
		);
		
		descriptionPanel.setLayout(gl_descriptionPanel);
		
		descriptionText=new JTextArea();
		descriptionText.setBounds(5, 5, 250, 170);
		descriptionText.setForeground(Color.BLACK);
		descriptionText.setText("");
		descriptionText.setBackground(Color.LIGHT_GRAY);
		
		descriptionPanel.add(descriptionText);
		
		
		descriptionLabel=new JLabel();
		descriptionLabel.setBounds(10, 160, 260, 40);
		descriptionLabel.setForeground(Color.WHITE);
		descriptionLabel.setText("Description");
		
		sideMenu.add(treeLabel);
		sideMenu.add(tablesTreePanel);
		
		sideMenu.add(descriptionLabel);
		sideMenu.add(descriptionPanel);

		lifeTimePanel.add(sideMenu);
		
		// ======================================================================
		new InfoListener(this,mnFile, menuBar);
		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1474, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
		);
		
		
		tabbedPane.addTab("LifeTime Table", null, lifeTimePanel, null);
		
		GroupLayout gl_lifeTimePanel = new GroupLayout(lifeTimePanel);
		gl_lifeTimePanel.setHorizontalGroup(
			gl_lifeTimePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 1469, Short.MAX_VALUE)
		);
		gl_lifeTimePanel.setVerticalGroup(
			gl_lifeTimePanel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 743, Short.MAX_VALUE)
		);
		lifeTimePanel.setLayout(gl_lifeTimePanel);
		
		
		generalTableLabel=new JLabel("Parallel Lives Diagram");
		generalTableLabel.setBounds(300, 0, 150, 30);
		generalTableLabel.setForeground(Color.BLACK);
		
		zoomAreaLabel=new JLabel();
		zoomAreaLabel.setText("<HTML>Z<br>o<br>o<br>m<br><br>A<br>r<br>e<br>a</HTML>");
		zoomAreaLabel.setBounds(1255, 325, 15, 300);
		zoomAreaLabel.setForeground(Color.BLACK);
		
		zoomInButton = new JButton("Zoom In");
		zoomInButton.setBounds(1000, 560, 100, 30);
		
		
		
		zoomInButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
				rowHeight=rowHeight+2;
				columnWidth=columnWidth+1;
				zoomAreaTable.setZoom(rowHeight,columnWidth);
				
			}
		});
		
		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.setBounds(1110, 560, 100, 30);
		
		zoomOutButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
				rowHeight=rowHeight-2;
				columnWidth=columnWidth-1;
				if(rowHeight<1){
					rowHeight=1;
				}
				if (columnWidth<1) {
					columnWidth=1;
				}
				zoomAreaTable.setZoom(rowHeight,columnWidth);
				
			}
		});
		
		zoomInButton.setVisible(false);
		zoomOutButton.setVisible(false);
		
		
		showThisToPopup = new JButton("Enlarge");
		showThisToPopup.setBounds(800, 560, 100, 30);
		
		showThisToPopup.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
				
				EnlargeTable showEnlargmentPopup= new EnlargeTable(finalRowsZoomArea,finalColumnsZoomArea,segmentSizeZoomArea);
				showEnlargmentPopup.setBounds(100, 100, 1300, 700);
				
				showEnlargmentPopup.setVisible(true);
				
				
			}
		});
		
		showThisToPopup.setVisible(false);
		
		
		undoButton = new JButton("Undo");
		undoButton.setBounds(680, 560, 100, 30);
		
		undoButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
				if (firstLevelUndoColumnsZoomArea!=null) {
					finalColumnsZoomArea=firstLevelUndoColumnsZoomArea;
					finalRowsZoomArea=firstLevelUndoRowsZoomArea;
					makeZoomAreaTableForCluster();
				}
				
			}
		});
		
		undoButton.setVisible(false);
		
		
		uniformlyDistributedButton = new JButton("Same Width"); 
		uniformlyDistributedButton.setBounds(980, 0, 120, 30);
		
		uniformlyDistributedButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
			    LifeTimeTable.uniformlyDistributed(1);
			  } 
		});
		
		uniformlyDistributedButton.setVisible(false);
		
		notUniformlyDistributedButton = new JButton("Over Time"); 
		notUniformlyDistributedButton.setBounds(1100, 0, 120, 30);
		
		notUniformlyDistributedButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
			    LifeTimeTable.notUniformlyDistributed(globalDataKeeper);
			    
			  } 
		});
		
		notUniformlyDistributedButton.setVisible(false);
		
		lifeTimePanel.add(zoomInButton);
		lifeTimePanel.add(undoButton);
		lifeTimePanel.add(zoomOutButton);
		lifeTimePanel.add(uniformlyDistributedButton);
		lifeTimePanel.add(notUniformlyDistributedButton);
		lifeTimePanel.add(showThisToPopup);

		lifeTimePanel.add(zoomAreaLabel);
		
		lifeTimePanel.add(generalTableLabel);
		
		contentPane.setLayout(gl_contentPane);
		
		pack();
		setBounds(30, 30, 1300, 700);
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
	tabbedPane.setSelectedIndex(0);
	makeZoomAreaTable();
}

public void makeZoomAreaTable() {
	showingPld=false;
	int numberOfColumns=finalRowsZoomArea[0].length;
	int numberOfRows=finalRowsZoomArea.length;
	
	final String[][] rowsZoom=new String[numberOfRows][numberOfColumns];
	
	for(int i=0; i<numberOfRows; i++){	
		rowsZoom[i][0]=finalRowsZoomArea[i][0];	
	}
	
	zoomModel=new MyTableModel(finalColumnsZoomArea, rowsZoom);
	
	final JvTable zoomTable=new JvTable(zoomModel);
	
	zoomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
	zoomTable.setShowGrid(false);
	zoomTable.setIntercellSpacing(new Dimension(0, 0));
	
	
	for(int i=0; i<zoomTable.getColumnCount(); i++){
		if(i==0){
			zoomTable.getColumnModel().getColumn(0).setPreferredWidth(150);
			
		}
		else{
			
			zoomTable.getColumnModel().getColumn(i).setPreferredWidth(20);
			zoomTable.getColumnModel().getColumn(i).setMaxWidth(20);
			zoomTable.getColumnModel().getColumn(i).setMinWidth(20);
		}
	}
	
	zoomTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
	        
	        if(column==wholeColZoomArea){
	        	
	        	String description="Transition ID:"+table.getColumnName(column)+"\n";
	        	description=description+"Old Version Name:"+globalDataKeeper.getAllPPLTransitions().
        				get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
        		description=description+"New Version Name:"+globalDataKeeper.getAllPPLTransitions().
        				get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n";		        		
        		
    			description=description+"Transition Changes:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterChangesForOneTr(rowsZoom)+"\n";
    			description=description+"Additions:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterAdditionsForOneTr(rowsZoom)+"\n";
    			description=description+"Deletions:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterDeletionsForOneTr(rowsZoom)+"\n";
    			description=description+"Updates:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterUpdatesForOneTr(rowsZoom)+"\n";

    		
	        	
	        	descriptionText.setText(description);
	        	Color cl = new Color(255,69,0,100);
        		c.setBackground(cl);
        		
        		return c;
	        }
	        else if(selectedColumnZoomArea==0){
	        	if (isSelected){
	        		String description="Table:"+finalRowsZoomArea[row][0]+"\n";
	        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
	        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
	        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
	        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
	        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).
	        				getTotalChangesForOnePhase(Integer.parseInt(table.getColumnName(1)), Integer.parseInt(table.getColumnName(table.getColumnCount()-1)))+"\n";
	        		descriptionText.setText(description);
	        		
	        		Color cl = new Color(255,69,0,100);

	        		c.setBackground(cl);
	        		return c;
	        	}
	        }
	        else{
	        	if (isSelected && hasFocus){
		        	
	        		String description="";
	        		if(!table.getColumnName(column).contains("Table name")){
		        		description="Table:"+finalRowsZoomArea[row][0]+"\n";
		        		
		        		description=description+"Old Version Name:"+globalDataKeeper.getAllPPLTransitions().
		        				get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
		        		description=description+"New Version Name:"+globalDataKeeper.getAllPPLTransitions().
		        				get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n";		        		
		        		if(globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).
		        				getTableChanges().getTableAtChForOneTransition(Integer.parseInt(table.getColumnName(column)))!=null){
		        			description=description+"Transition Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).
		        				getTableChanges().getTableAtChForOneTransition(Integer.parseInt(table.getColumnName(column))).size()+"\n";
		        			description=description+"Additions:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).
		        					getNumberOfAdditionsForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
		        			description=description+"Deletions:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).
		        					getNumberOfDeletionsForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
		        			description=description+"Updates:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).
		        					getNumberOfUpdatesForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
		        			
		        		}
		        		else{
		        			description=description+"Transition Changes:0"+"\n";
		        			description=description+"Additions:0"+"\n";
		        			description=description+"Deletions:0"+"\n";
		        			description=description+"Updates:0"+"\n";
		        			
		        		}
		        		
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
        			insersionColor=new Color(0,100,0);
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
	        }
	        catch(Exception e){
	        		

	        	
        		if(tmpValue.equals("")){
        			c.setBackground(Color.DARK_GRAY);
        			return c; 
        		}
        		else{
        			if(columnName.contains("v")){
        				c.setBackground(Color.lightGray);
        				setToolTipText(columnName);
        			}
        			else{
        				Color tableNameColor=new Color(205,175,149);
        				c.setBackground(tableNameColor);
        			}
	        		return c; 
        		}
	        		
	        		
	        }
	    }
	});
	
	zoomTable.addMouseListener(new MouseAdapter() {
		@Override
		   public void mouseClicked(MouseEvent e) {
			
			if (e.getClickCount() == 1) {
				JTable target = (JTable)e.getSource();
		         
		         selectedRowsFromMouse = target.getSelectedRows();
		         selectedColumnZoomArea = target.getSelectedColumn();
		         zoomAreaTable.repaint();
			}
		    
		   }
	});
	
	zoomTable.addMouseListener(new MouseAdapter() {
		@Override
		   public void mouseReleased(MouseEvent e) {
			
				if(SwingUtilities.isRightMouseButton(e)){
					System.out.println("Right Click");

					JTable target1 = (JTable)e.getSource();
					selectedColumnZoomArea=target1.getSelectedColumn();
					selectedRowsFromMouse=target1.getSelectedRows();
					System.out.println(target1.getSelectedColumn());
					System.out.println(target1.getSelectedRow());
					final ArrayList<String> tablesSelected = new ArrayList<String>();
					for(int rowsSelected=0; rowsSelected<selectedRowsFromMouse.length; rowsSelected++){
						tablesSelected.add((String) zoomTable.getValueAt(selectedRowsFromMouse[rowsSelected], 0));
						System.out.println(tablesSelected.get(rowsSelected));
					}
				}
		   }
	});
	
	// listener
	zoomTable.getTableHeader().addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	wholeColZoomArea = zoomTable.columnAtPoint(e.getPoint());
	        String name = zoomTable.getColumnName(wholeColZoomArea);
	        System.out.println("Column index selected " + wholeCol + " " + name);
	        zoomTable.repaint();
	    }
	});
	
	zoomTable.getTableHeader().addMouseListener(new MouseAdapter() {
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
				            	zoomTable.repaint();
				            }
				        });
				        popupMenu.add(showDetailsItem);
				        popupMenu.show(zoomTable, e.getX(),e.getY());
				    
			}
		
	   }
	    
	});
	
	
	zoomAreaTable=zoomTable;
	
	tmpScrollPaneZoomArea.setViewportView(zoomAreaTable);
	tmpScrollPaneZoomArea.setAlignmentX(0);
	tmpScrollPaneZoomArea.setAlignmentY(0);
	tmpScrollPaneZoomArea.setBounds(300,300,950,250);
	tmpScrollPaneZoomArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	tmpScrollPaneZoomArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	

	lifeTimePanel.setCursor(getCursor());
	lifeTimePanel.add(tmpScrollPaneZoomArea);
}

public void makeZoomAreaTableForCluster() {
	showingPld=false;
	int numberOfColumns=finalRowsZoomArea[0].length;
	int numberOfRows=finalRowsZoomArea.length;
	undoButton.setVisible(true);
	
	final String[][] rowsZoom=new String[numberOfRows][numberOfColumns];
	
	for(int i=0; i<numberOfRows; i++){
		
		rowsZoom[i][0]=finalRowsZoomArea[i][0];
		
	}
	
	zoomModel=new MyTableModel(finalColumnsZoomArea, rowsZoom);
	
	final JvTable zoomTable=new JvTable(zoomModel);
	
	zoomTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	
	
	zoomTable.setShowGrid(false);
	zoomTable.setIntercellSpacing(new Dimension(0, 0));
	

	
	for(int i=0; i<zoomTable.getColumnCount(); i++){
		if(i==0){
			zoomTable.getColumnModel().getColumn(0).setPreferredWidth(150);
			
		}
		else{
			
			zoomTable.getColumnModel().getColumn(i).setPreferredWidth(10);
			zoomTable.getColumnModel().getColumn(i).setMaxWidth(10);
			zoomTable.getColumnModel().getColumn(i).setMinWidth(70);
		}
	}
	
	zoomTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
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
	        
	        if(column==wholeColZoomArea && wholeColZoomArea!=0){
	        	
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
	        else if(selectedColumnZoomArea==0){
	        	if (isSelected){
	        		
	        		
		        		String description="Table:"+finalRowsZoomArea[row][0]+"\n";
		        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
		        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
		        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
		        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
		        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTotalChanges()+"\n";
		        		descriptionText.setText(description);

	        	
	        		
	        		Color cl = new Color(255,69,0,100);
	        		
	        		c.setBackground(cl);
	        		return c;
	        	}
	        }
	        else{
	        	
	        	
	        	if (isSelected && hasFocus){
		        	
	        		String description="";
	        		if(!table.getColumnName(column).contains("Table name")){
	        			
		        		
	        			description="Transition "+table.getColumnName(column)+"\n";
		        		
	        			description=description+"Old Version:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
		        		description=description+"New Version:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n\n";
		
	        			//description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
		        				//get(column-1).getStartPos()+"\n";
		        		//description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().
		        			//	get(column-1).getEndPos()+"\n\n";
	        			description=description+"Table:"+finalRowsZoomArea[row][0]+"\n";
		        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
		        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
		        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
		        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
		        		description=description+"Total Changes For This Phase:"+tmpValue+"\n";
		        		
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
        			insersionColor=new Color(0,100,0);
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
	        }
	        catch(Exception e){
	        		

	        	
        		if(tmpValue.equals("")){
        			c.setBackground(Color.DARK_GRAY);
        			return c; 
        		}
        		else{
        			if(columnName.contains("v")){
        				c.setBackground(Color.lightGray);
        				setToolTipText(columnName);
        			}
        			else{
        				Color tableNameColor=new Color(205,175,149);
        				c.setBackground(tableNameColor);
        			}
	        		return c; 
        		}
	        		
	        		
	        }
	    }
	});
	
	zoomTable.addMouseListener(new MouseAdapter() {
		@Override
		   public void mouseClicked(MouseEvent e) {
			
			if (e.getClickCount() == 1) {
				JTable target = (JTable)e.getSource();
		         
		         selectedRowsFromMouse = target.getSelectedRows();
		         selectedColumnZoomArea = target.getSelectedColumn();
		         zoomAreaTable.repaint();
			}
		   
		   }
	});
	
	zoomTable.addMouseListener(new MouseAdapter() {
		@Override
		   public void mouseReleased(MouseEvent e) {
			
				if(SwingUtilities.isRightMouseButton(e)){
					System.out.println("Right Click");

						JTable target1 = (JTable)e.getSource();
						selectedColumnZoomArea=target1.getSelectedColumn();
						selectedRowsFromMouse=target1.getSelectedRows();
						System.out.println(target1.getSelectedColumn());
						System.out.println(target1.getSelectedRow());
						
						tablesSelected = new ArrayList<String>();

						for(int rowsSelected=0; rowsSelected<selectedRowsFromMouse.length; rowsSelected++){
							tablesSelected.add((String) zoomTable.getValueAt(selectedRowsFromMouse[rowsSelected], 0));
							System.out.println(tablesSelected.get(rowsSelected));
						}
		            	if (zoomTable.getColumnName(selectedColumnZoomArea).contains("Phase")) {

							final JPopupMenu popupMenu = new JPopupMenu();
					        JMenuItem showDetailsItem = new JMenuItem("Show Details");
					        showDetailsItem.addActionListener(new ActionListener() {

					            @Override
					            public void actionPerformed(ActionEvent e) {
				            		firstLevelUndoColumnsZoomArea=finalColumnsZoomArea;
					            	firstLevelUndoRowsZoomArea=finalRowsZoomArea;
				            		showSelectionToZoomArea(selectedColumnZoomArea);
									
					            	
					            }
					        });
					        popupMenu.add(showDetailsItem);
					        popupMenu.show(zoomTable, e.getX(),e.getY());
		            	}
					        
					
				}
			
		   }
	});
	
	// listener
	zoomTable.getTableHeader().addMouseListener(new MouseAdapter() {
	    @Override
	    public void mouseClicked(MouseEvent e) {
	    	wholeColZoomArea = zoomTable.columnAtPoint(e.getPoint());
	        String name = zoomTable.getColumnName(wholeColZoomArea);
	        System.out.println("Column index selected " + wholeCol + " " + name);
	        zoomTable.repaint();
	    }
	});
	
	zoomTable.getTableHeader().addMouseListener(new MouseAdapter() {
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
				            	zoomTable.repaint();
				            }
				        });
				        popupMenu.add(showDetailsItem);
				        popupMenu.show(zoomTable, e.getX(),e.getY());
				    
			}
		
	   }
	    
	});
	
	zoomAreaTable=zoomTable;
	
	tmpScrollPaneZoomArea.setViewportView(zoomAreaTable);
	tmpScrollPaneZoomArea.setAlignmentX(0);
	tmpScrollPaneZoomArea.setAlignmentY(0);
	tmpScrollPaneZoomArea.setBounds(300,300,950,250);
	tmpScrollPaneZoomArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	tmpScrollPaneZoomArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	

	lifeTimePanel.setCursor(getCursor());
	lifeTimePanel.add(tmpScrollPaneZoomArea);
	}
	
	public void importData(String fileName) throws IOException, RecognitionException {
		
		BufferedReader br = new BufferedReader(new FileReader(fileName));
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
		
		System.out.println("Project Name:"+projectName);
		System.out.println("Dataset txt:"+datasetTxt);
		System.out.println("Input Csv:"+inputCsv);
		System.out.println("Output Assessment1:"+outputAssessment1);
		System.out.println("Output Assessment2:"+outputAssessment2);
		System.out.println("Transitions File:"+transitionsFile);

		globalDataKeeper=new GlobalDataKeeper(datasetTxt,transitionsFile);
		globalDataKeeper.setData();
		System.out.println(globalDataKeeper.getAllPPLTables().size());
		
        System.out.println(fileName);

        fillTable();
        fillTree();

		currentProject=fileName;
	}
	
	public void fillTable() {
		TableConstructionIDU table=new TableConstructionIDU(globalDataKeeper);
		final String[] columns=table.constructColumns();
		final String[][] rows=table.constructRows();
		segmentSizeZoomArea = table.getSegmentSize();

		finalColumnsZoomArea=columns;
		finalRowsZoomArea=rows;
		tabbedPane.setSelectedIndex(0);
		ZoomTableModel zoomTableModel = new ZoomTableModel(this);
		zoomAreaTable = zoomTableModel.getZoomAreaTable();
		
		timeWeight = (float)0.5;
        changeWeight = (float)0.5;
        preProcessingTime = false;
        preProcessingChange = false;
        if(globalDataKeeper.getAllPPLTransitions().size()<56){
        	numberOfPhases=40;
        }
        else{
        	numberOfPhases = 56;
        }
	    numberOfClusters =14;
        
        System.out.println(timeWeight+" "+changeWeight);
        
		PhaseAnalyzerMainEngine mainEngine = new PhaseAnalyzerMainEngine(inputCsv,outputAssessment1,outputAssessment2,timeWeight,changeWeight,preProcessingTime,preProcessingChange);

		Double b=new Double(0.3);
		Double d=new Double(0.3);
		Double c=new Double(0.3);
			
		mainEngine.parseInput();		
		System.out.println("\n\n\n");
		mainEngine.extractPhases(numberOfPhases);
		
		mainEngine.connectTransitionsWithPhases(globalDataKeeper);
		globalDataKeeper.setPhaseCollectors(mainEngine.getPhaseCollectors());
		TableClusteringMainEngine mainEngine2 = new TableClusteringMainEngine(globalDataKeeper,b,d,c);
		mainEngine2.extractClusters(numberOfClusters);
		globalDataKeeper.setClusterCollectors(mainEngine2.getClusterCollectors());
		mainEngine2.print();
		
		if(globalDataKeeper.getPhaseCollectors().size()!=0){
			TableConstructionWithClusters tableP=new TableConstructionWithClusters(globalDataKeeper);
			final String[] columnsP=tableP.constructColumns();
			final String[][] rowsP=tableP.constructRows();
			segmentSize=tableP.getSegmentSize();
			finalColumns=columnsP;
			finalRows=rowsP;
			tabbedPane.setSelectedIndex(0);
			GeneralTablePhases generalTablePhases = new GeneralTablePhases(this);
			LifeTimeTable = generalTablePhases.getLifeTimeTable();
			fillClustersTree();
		}
		System.out.println("Schemas:"+globalDataKeeper.getAllPPLSchemas().size());
		System.out.println("Transitions:"+globalDataKeeper.getAllPPLTransitions().size());
		System.out.println("Tables:"+globalDataKeeper.getAllPPLTables().size());
	}
	
	public void optimize() throws IOException{
		
		String lalaString="Birth Weight:"+"\tDeath Weight:"+"\tChange Weight:"+"\tTotal Cohesion:"+"\tTotal Separation:"+"\n";
		int counter=0;
		for(double wb=0.0; wb<=1.0; wb=wb+0.01){
			
			for(double wd=(1.0-wb); wd>=0.0; wd=wd-0.01){
				
					double wc=1.0-(wb+wd);
					TableClusteringMainEngine mainEngine2 = new TableClusteringMainEngine(globalDataKeeper,wb,wd,wc);
					mainEngine2.extractClusters(numberOfClusters);
					globalDataKeeper.setClusterCollectors(mainEngine2.getClusterCollectors());
					
					ClusterValidatorMainEngine lala = new ClusterValidatorMainEngine(globalDataKeeper);
					lala.run();
					
					lalaString=lalaString+wb+"\t"+wd+"\t"+wc
							+"\t"+lala.getTotalCohesion()+"\t"+lala.getTotalSeparation()+"\t"+(wb+wd+wc)+"\n";
			
					counter++;
					System.err.println(counter);
			}
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter("lala.csv");
			
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(lalaString);
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(lalaString);
		
	}
	
	public void getExternalValidityReport() throws IOException{
		
		String lalaString="Birth Weight:"+"\tDeath Weight:"+"\tChange Weight:"+"\n";
		int counter=0;
		
		TableClusteringMainEngine mainEngine2 = new TableClusteringMainEngine(globalDataKeeper,0.333,0.333,0.333);
		mainEngine2.extractClusters(4);
		globalDataKeeper.setClusterCollectors(mainEngine2.getClusterCollectors());
		
		ClusterValidatorMainEngine lala = new ClusterValidatorMainEngine(globalDataKeeper);
		lala.run();
		
		lalaString=lalaString+"\n"+"0.333"+"\t"+"0.333"+"\t"+"0.333"
				+"\n"+lala.getExternalEvaluationReport();
		
		for(double wb=0.0; wb<=1.0; wb=wb+0.5){
			
			for(double wd=(1.0-wb); wd>=0.0; wd=wd-0.5){
				
					double wc=1.0-(wb+wd);
					mainEngine2 = new TableClusteringMainEngine(globalDataKeeper,wb,wd,wc);
					mainEngine2.extractClusters(4);
					globalDataKeeper.setClusterCollectors(mainEngine2.getClusterCollectors());
					
					lala = new ClusterValidatorMainEngine(globalDataKeeper);
					lala.run();
					
					lalaString=lalaString+"\n"+wb+"\t"+wd+"\t"+wc
							+"\n"+lala.getExternalEvaluationReport();
			
					counter++;
					System.err.println(counter);	
			}
		}
		
		FileWriter fw;
		try {
			fw = new FileWriter("lala.csv");
			
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(lalaString);
			bw.close();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(lalaString);
	}
	
	
	public void fillTree(){
		
		 TreeConstructionGeneral tc=new TreeConstructionGeneral(globalDataKeeper);
		 tablesTree=new JTree();
		 tablesTree=tc.constructTree();
		 tablesTree.addTreeSelectionListener(new TreeSelectionListener () {
			    public void valueChanged(TreeSelectionEvent ae) { 
			    	TreePath selection = ae.getPath();
			    	selectedFromTree.add(selection.getLastPathComponent().toString());
			    	System.out.println(selection.getLastPathComponent().toString()+" is selected");
			    	
			    }
		 });
		 
		 tablesTree.addMouseListener(new MouseAdapter() {
				@Override
				   public void mouseReleased(MouseEvent e) {
					
						if(SwingUtilities.isRightMouseButton(e)){
							System.out.println("Right Click Tree");
								
									final JPopupMenu popupMenu = new JPopupMenu();
							        JMenuItem showDetailsItem = new JMenuItem("Show This into the Table");
							        showDetailsItem.addActionListener(new ActionListener() {
		
							            @Override
							            public void actionPerformed(ActionEvent e) {
							          
							                LifeTimeTable.repaint();
							            }
							        });
							        popupMenu.add(showDetailsItem);
							        popupMenu.show(tablesTree, e.getX(),e.getY());
						}
				   }
			});
		 
		 treeScrollPane.setViewportView(tablesTree);
		 
		 treeScrollPane.setBounds(5, 5, 250, 170);
		 treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 treeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 tablesTreePanel.add(treeScrollPane);
		 
		 treeLabel.setText("General Tree");

		 sideMenu.revalidate();
		 sideMenu.repaint();
	}
	
	public void fillPhasesTree(){
		
		 TreeConstructionPhases tc=new TreeConstructionPhases(globalDataKeeper);
		 tablesTree=tc.constructTree();
		 
		 tablesTree.addTreeSelectionListener(new TreeSelectionListener () {
			    public void valueChanged(TreeSelectionEvent ae) { 
			    	TreePath selection = ae.getPath();
			    	selectedFromTree.add(selection.getLastPathComponent().toString());
			    	System.out.println(selection.getLastPathComponent().toString()+" is selected");	
			    }
		 });
		 
		 tablesTree.addMouseListener(new MouseAdapter() {
				@Override
				   public void mouseReleased(MouseEvent e) {
					
						if(SwingUtilities.isRightMouseButton(e)){
							System.out.println("Right Click Tree");
							
							final JPopupMenu popupMenu = new JPopupMenu();
					        JMenuItem showDetailsItem = new JMenuItem("Show This into the Table");
					        showDetailsItem.addActionListener(new ActionListener() {

					            @Override
					            public void actionPerformed(ActionEvent e) {
					          
					                LifeTimeTable.repaint();
					            }
					        });
					        popupMenu.add(showDetailsItem);
					        popupMenu.show(tablesTree, e.getX(),e.getY());
						}
				   }
			});
		 
		 treeScrollPane.setViewportView(tablesTree);
		 treeScrollPane.setBounds(5, 5, 250, 170);
		 treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 treeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 tablesTreePanel.add(treeScrollPane);
		 
		 treeLabel.setText("Phases Tree");

		 sideMenu.revalidate();
		 sideMenu.repaint();
	}
	
	public void fillClustersTree(){
		
		 TreeConstructionPhasesWithClusters tc=new TreeConstructionPhasesWithClusters(globalDataKeeper);
		 tablesTree=tc.constructTree();
		 
		 tablesTree.addTreeSelectionListener(new TreeSelectionListener () {
			    public void valueChanged(TreeSelectionEvent ae) { 
			    	TreePath selection = ae.getPath();
			    	selectedFromTree.add(selection.getLastPathComponent().toString());
			    	System.out.println(selection.getLastPathComponent().toString()+" is selected");
			    	
			    }
		 });
		 
		 tablesTree.addMouseListener(new MouseAdapter() {
				@Override
				   public void mouseReleased(MouseEvent e) {
					
						if(SwingUtilities.isRightMouseButton(e)){
							System.out.println("Right Click Tree");
							
							final JPopupMenu popupMenu = new JPopupMenu();
					        JMenuItem showDetailsItem = new JMenuItem("Show This into the Table");
					        showDetailsItem.addActionListener(new ActionListener() {

					            @Override
					            public void actionPerformed(ActionEvent e) {
					          
					                LifeTimeTable.repaint();
					            }
					        });
					        popupMenu.add(showDetailsItem);
					        popupMenu.show(tablesTree, e.getX(),e.getY());        	
						}
				   }
			});
		 treeScrollPane.setViewportView(tablesTree);
		 
		 treeScrollPane.setBounds(5, 5, 250, 170);
		 treeScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		 treeScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		 tablesTreePanel.add(treeScrollPane);

		 treeLabel.setText("Clusters Tree");

		 sideMenu.revalidate();
		 sideMenu.repaint();
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
	}
	
	public void setDescription(String descr){
		descriptionText.setText(descr);
	}

	public GlobalDataKeeper getGlobalDataKeeper() {
		return globalDataKeeper;
	}	
	
	public String getCurrentProject() {
		return currentProject;
	}

	public int getSelectedColumn() {
		return selectedColumn;
	}
	
	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public JvTable getLifeTimeTable() {
		return LifeTimeTable;
	}
	
	public JButton getZoomInButton() {
		return zoomInButton;
	}
	
	public JButton getZoomOutButton() {
		return zoomOutButton;
	}
	
	public String getInputCsv() {
		return inputCsv;
	}
	
	public String getOutputAssessment1() {
		return outputAssessment1;
	}
	
	public String getOutputAssessment2() {
		return outputAssessment2;
	}
	
	public String getTransitionsFile() {
		return transitionsFile;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public String getDatasetTxt() {
		return datasetTxt;
	}
	
	// ==========================================
	// 2
	public JButton getUniformlyDistributedButton() {
		return uniformlyDistributedButton;
	}
	
	public JButton getNotUniformlyDistributedButton() {
		return notUniformlyDistributedButton;
	}
	
	public String[][] getFinalRows(){
		return finalRows;
	}
	
	public String[] getFinalColumns() {
		return finalColumns;
	}
	
	public int getWholeCol() {
		return wholeCol;
	}
	
	public JTextArea getDescriptionText() {
		return descriptionText;
	}
	
	public ArrayList<String> getSelectedFromTree() {
		return selectedFromTree;
	}
	
	public Integer[] getSegmentSize() {
		return segmentSize;
	}
	
	public boolean getShowingPld() {
		return showingPld;
	}
	
	public JPanel getLifeTimePanel() {
		return lifeTimePanel;
	}
	
	public int getRowHeight()
	{
		return rowHeight;
	}
	
	public int getColumnWidth()
	{
		return columnWidth;
	}
	
	public int getWholeColZoomArea()
	{
		return wholeColZoomArea;
	}
	
	public int getSelectedColumnZoomArea()
	{
		return selectedColumnZoomArea;
	}

	public int[] getselectedRowsFromMouse()
	{
		return selectedRowsFromMouse;
	}
	
	public JButton getShowThisToPopup() {
		return showThisToPopup;
	}
	
	public JvTable getZoomAreaTable() {
		return zoomAreaTable;
	}
	
	public JScrollPane getTmpScrollPaneZoomArea() {
		return tmpScrollPaneZoomArea;
	}
	
	public String[] getFinalColumnsZoomArea() {
		return finalColumnsZoomArea;
	}
	
	public Integer[] getSegmentSizeZoomArea() {
		return segmentSizeZoomArea;
	}
	
	public String[][] getFinalRowsZoomArea() {
		return finalRowsZoomArea;
	}
}
