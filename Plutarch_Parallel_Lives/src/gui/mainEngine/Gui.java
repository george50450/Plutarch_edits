package gui.mainEngine;

import gui.dialogs.EnlargeTable;
import gui.listeners.FileActions;
import gui.listeners.InfoActions;
import gui.listeners.TableActions;
import gui.tableElements.commons.JvTable;
import gui.tableElements.commons.MyTableModel;
import gui.tableElements.tableConstructors.TableConstructionZoomArea;
import data.dataKeeper.DescriptionGenerator;
import data.dataKeeper.GlobalDataKeeper;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import javax.swing.table.DefaultTableCellRenderer;


public class Gui extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JPanel lifeTimePanel = new JPanel();
	private JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
	private MyTableModel zoomModel = null;
	private JvTable LifeTimeTable=null;
	private JvTable zoomAreaTable=null;
	private JScrollPane treeScrollPane= new JScrollPane();
	private JScrollPane tmpScrollPaneZoomArea =new JScrollPane();
	private GlobalDataKeeper globalDataKeeper=null;
	private String[] finalColumns=null;
	private String[][] finalRows=null;
	private String[] finalColumnsZoomArea=null;
	private String[][] finalRowsZoomArea=null;
	private String[] firstLevelUndoColumnsZoomArea=null;
	private String[][] firstLevelUndoRowsZoomArea=null;
	private String currentProject=null;
	private JItemsGenerator jItemsGenerator = new JItemsGenerator();
	private Integer[] segmentSize=new Integer[4];
	private Integer[] segmentSizeZoomArea=new Integer[4];
	private String projectName="";
	private String datasetTxt="";
	private String inputCsv="";
	private String outputAssessment1="";
	private String outputAssessment2="";
	private String transitionsFile="";
	private ArrayList<String> selectedFromTree=new ArrayList<String>();
	private JTree tablesTree=new JTree();
	private JPanel sideMenu= jItemsGenerator.createJPanel();
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
	protected JButton buttonHelp;

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
		
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		
		JMenuItem mntmCreateProject = new JMenuItem("Create Project");
		mntmCreateProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileActions fileAction = new FileActions(Gui.this,treeLabel,tablesTree, sideMenu, tablesTreePanel, treeScrollPane,    tabbedPane, zoomAreaTable,LifeTimeTable); 
				fileAction.createProjectAction();	            
			}
		});
		mnFile.add(mntmCreateProject);
		

		JMenuItem mntmLoadProject = new JMenuItem("Load Project");
		mntmLoadProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileActions fileAction = new FileActions(Gui.this,treeLabel,tablesTree, sideMenu, tablesTreePanel, treeScrollPane,    tabbedPane, zoomAreaTable,LifeTimeTable); 
				fileAction.loadProjectAction();		
			}
		});
		mnFile.add(mntmLoadProject);
		
		
		JMenuItem mntmEditProject = new JMenuItem("Edit Project");
		mntmEditProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FileActions fileAction = new FileActions(Gui.this,treeLabel,tablesTree, sideMenu, tablesTreePanel, treeScrollPane,    tabbedPane, zoomAreaTable,LifeTimeTable); 
				fileAction.editProjectAction();
				
			}
		});
		mnFile.add(mntmEditProject);
		

		final JMenu mnTable = new JMenu("Table");
		menuBar.add(mnTable);
		

		JMenuItem mntmShowGeneralLifetimeIDU = new JMenuItem("Show PLD");
		mntmShowGeneralLifetimeIDU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TableActions tableAction = new TableActions(Gui.this,treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
				tableAction.showPLD();
			}
		});
		mnTable.add(mntmShowGeneralLifetimeIDU);	
		
	
		JMenuItem mntmShowGeneralLifetimePhasesPLD = new JMenuItem("Show Phases PLD");
		mntmShowGeneralLifetimePhasesPLD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TableActions tableAction = new TableActions(Gui.this,treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
				tableAction.showPhasesPLD();
				
			}
		});
		mnTable.add(mntmShowGeneralLifetimePhasesPLD);
		
		
		JMenuItem mntmShowGeneralLifetimePhasesWithClustersPLD = new JMenuItem("Show Phases With Clusters PLD");
		mntmShowGeneralLifetimePhasesWithClustersPLD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				TableActions tableAction = new TableActions(Gui.this,treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
				tableAction.showPhasesWithClustersPLD();
			}
		});
		
		mnTable.add(mntmShowGeneralLifetimePhasesWithClustersPLD);
		
		
		JMenuItem mntmShowLifetimeTable = new JMenuItem("Show Full Detailed LifeTime Table");
		mntmShowLifetimeTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				TableActions tableAction = new TableActions(Gui.this,treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
				tableAction.showDetailedLifeTimeTable();
			}
		});
		mnTable.add(mntmShowLifetimeTable);
		
		
		tablesTreePanel.setBounds(10, 400, 260, 180);
		tablesTreePanel.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout gl_tablesTreePanel = new GroupLayout(tablesTreePanel);
		gl_tablesTreePanel.setHorizontalGroup(gl_tablesTreePanel.createParallelGroup(Alignment.LEADING));
		gl_tablesTreePanel.setVerticalGroup(gl_tablesTreePanel.createParallelGroup(Alignment.LEADING));
		
		tablesTreePanel.setLayout(gl_tablesTreePanel);
		
		
		treeLabel= jItemsGenerator.createJLabel("", "Tree", 10, 370, 260, 40, Color.WHITE);
	
		
		descriptionPanel.setBounds(10, 190, 260, 180);
		descriptionPanel.setBackground(Color.LIGHT_GRAY);
		
		GroupLayout gl_descriptionPanel = new GroupLayout(descriptionPanel);
		gl_descriptionPanel.setHorizontalGroup(gl_descriptionPanel.createParallelGroup(Alignment.LEADING));
		gl_descriptionPanel.setVerticalGroup(gl_descriptionPanel.createParallelGroup(Alignment.LEADING));
		
		descriptionPanel.setLayout(gl_descriptionPanel);
		
		
		descriptionText= jItemsGenerator.createTextArea(" ", 5, 5, 250, 170, Color.BLACK, Color.LIGHT_GRAY);
		
		descriptionPanel.add(descriptionText);
		
		
		descriptionLabel= jItemsGenerator.createJLabel("", "Description", 10, 160, 260, 40, Color.WHITE);
		
		sideMenu.add(treeLabel);
		sideMenu.add(tablesTreePanel);
		
		sideMenu.add(descriptionLabel);
		sideMenu.add(descriptionPanel);

		lifeTimePanel.add(sideMenu);
		
		
		final InfoActions infoAction = new InfoActions(this,mnFile, menuBar);
	
		
		buttonHelp=new JButton("Help");
		buttonHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				infoAction.Help();			
			}
		});
		buttonHelp.setBounds(900,900 , 80, 40);
		menuBar.add(buttonHelp);
		
	
		mnProject = new JMenu("Project");
		menuBar.add(mnProject);
		
		
		mntmInfo = new JMenuItem("Info");
		mntmInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				infoAction.showInfo();
			}
		});
		mnProject.add(mntmInfo);
		
		buttonHelp.setBounds(900,900 , 80, 40);
		menuBar.add(buttonHelp);
		
		
		contentPane = new JPanel();
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 1474, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING).addComponent(tabbedPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 771, Short.MAX_VALUE)
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
		
		
		generalTableLabel= jItemsGenerator.createJLabel("", "Parallel Lives Diagram", 300, 0, 150, 30, Color.BLACK);
		
		zoomAreaLabel=jItemsGenerator.createJLabel("", "<HTML>Z<br>o<br>o<br>m<br><br>A<br>r<br>e<br>a</HTML>", 1255, 325, 15, 300, Color.BLACK);
		
		
		zoomInButton = jItemsGenerator.createJButton("Zoom In", 1000, 560, 100, 30);
		
		
		zoomInButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
				rowHeight=rowHeight+2;
				columnWidth=columnWidth+1;
				zoomAreaTable.setZoom(rowHeight,columnWidth);
				
			}
		});
		
		zoomOutButton = jItemsGenerator.createJButton("Zoom Out", 1110, 560, 100, 30);
		
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
					makeZoomAreaTableForCluster(finalColumnsZoomArea, finalRowsZoomArea);
				}
				
			}
		});
		
		undoButton.setVisible(false);
		
		uniformlyDistributedButton = jItemsGenerator.createJButton("Same Width", 980, 0, 120, 30);
		
		uniformlyDistributedButton.addMouseListener(new MouseAdapter() {
			@Override
			   public void mouseClicked(MouseEvent e) {
			    LifeTimeTable.uniformlyDistributed(1);
			  } 
		});
		
		uniformlyDistributedButton.setVisible(false);
		
		notUniformlyDistributedButton = jItemsGenerator.createJButton("Over Time", 1100, 0, 120, 30);
		
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


	public void makeZoomAreaTable(final String[] finalColumnsZoomArea, final String[][] finalRowsZoomArea) {
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
		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        
		        
		        
		        String tmpValue=finalRowsZoomArea[row][column];
		        String columnName=table.getColumnName(column);
		        Color fr=new Color(0,0,0);
		        c.setForeground(fr);
		        
		  
		        
		        DescriptionGenerator descriptionGenerator = new DescriptionGenerator(table, value,  isSelected, hasFocus, row,  column, rowsZoom, Gui.this);
		        
		        
		        if(column==wholeColZoomArea){
		        	
			        	String description= descriptionGenerator.generateMakeZoomAreaTableDescription();
			        		    		
			        	descriptionText.setText(description);
			        	Color cl = new Color(255,69,0,100);
		        		c.setBackground(cl);
		        		
		        		return c;
			        }
			        else if(selectedColumnZoomArea==0){
			        	if (isSelected){
			        		String description= descriptionGenerator.generateMakeZoomAreaTableDescription();
			        		descriptionText.setText(description);
			        		
			        		Color cl = new Color(255,69,0,100);

			        		c.setBackground(cl);
			        		return c;
			        	}
			        }
			        else{
			        	if (isSelected && hasFocus){
				        		String description = "";
			        		
			        			description= descriptionGenerator.generateMakeZoomAreaTableDescription();
				        		descriptionText.setText(description);
			        		}
			        		
			        		Color cl = new Color(255,69,0,100);

			        		c.setBackground(cl);
			        		return c;
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

	public void makeZoomAreaTableForCluster(final String[] finalColumnsZoomArea, final String[][] finalRowsZoomArea) {
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
		        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		        
		        
		        
		        String tmpValue=finalRowsZoomArea[row][column];
		        String columnName=table.getColumnName(column);
		        Color fr=new Color(0,0,0);
		        c.setForeground(fr);
		        
		        DescriptionGenerator descriptionGenerator = new DescriptionGenerator(table, value,  isSelected, hasFocus, row,  column, rowsZoom, Gui.this);
		    
		        
		        if(column==wholeColZoomArea && wholeColZoomArea!=0){
		        	
		            String description= descriptionGenerator.generateMakeZoomAreaTableForClusterDescription();
		        	descriptionText.setText(description);
		        	
		        	Color cl = new Color(255,69,0,100);
	
	        		c.setBackground(cl);
	        		return c;
		        }
		        else if(selectedColumnZoomArea==0){
		        	if (isSelected){
		        		
		        		String description =descriptionGenerator.generateMakeZoomAreaTableForClusterDescription();
		        		descriptionText.setText(description);
		        		Color cl = new Color(255,69,0,100);
		        		
		        		c.setBackground(cl);
		        		return c;
		        	}
		        }
		        else{
		        	
		        	if (isSelected && hasFocus){
		        		if(!table.getColumnName(column).contains("Table name")){
		        		    String description= descriptionGenerator.generateMakeZoomAreaTableForClusterDescription();
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
		makeZoomAreaTable(finalColumnsZoomArea,finalRowsZoomArea);
	}

	
	
	// Gui Setters
	
	public void setFinalRowsZoomArea(String[][] finalRowsZoomArea)
	{
		this.finalRowsZoomArea = finalRowsZoomArea;
	}
	
	public void setFinalColumnsZoomArea(String[] finalColumnsZoomArea)
	{
		this.finalColumnsZoomArea = finalColumnsZoomArea;
	}
	
	public void setGlobalDataKeeper(GlobalDataKeeper globalDataKeeper)
	{
		this.globalDataKeeper = globalDataKeeper;
	}
	
	public void setInputCsv(String inputCsv)
	{
		this.inputCsv = inputCsv;
	}
	
	public void setoutputAssessment1(String outputAssessment1)
	{
		this.outputAssessment1 = outputAssessment1;
	}
	
	public void setoutputAssessment2(String outputAssessment1)
	{
		this.outputAssessment2 = outputAssessment2;
	}
	
	public void setFinalRows(String[][] finalRows)
	{
		this.finalRows = finalRows;
	}
	
	public void setFinalColumns(String[] finalColumns)
	{
		this.finalColumns = finalColumns;
	}
	
	
	public void setSegmentSizeZoomArea(Integer[] segmentSizeZoomArea)
	{
		this.segmentSizeZoomArea = segmentSizeZoomArea;
	}
	
	public void setLifeTimeTable(JvTable LifeTimeTable)
	{
		this.LifeTimeTable = LifeTimeTable;
	}
	
	public void setSegmentSize(Integer[] segmentSize)
	{
		this.segmentSize = segmentSize;
	}
	
	public void setTabbedPane(JTabbedPane tabbedPane)
	{
		this.tabbedPane = tabbedPane;
	}
	
	public void setProjectName(String projectName)
	{
		this.currentProject = projectName;
		this.projectName = projectName;
	}
	public void setDatasetTxt(String datasetTxt)
	{
		this.datasetTxt = datasetTxt;
	}
	
	public void setOutputAssessment1(String outputAssessment1)
	{
		this.outputAssessment1 = outputAssessment1;
	}
	
	public void setOutputAssessment2(String outputAssessment2)
	{
		this.outputAssessment2 = outputAssessment2;
	}
	
	public void setTransitionsFile(String transitionsFile)
	{
		this.transitionsFile = transitionsFile;
	}
	
	public void setZoomAreaTable(JvTable zoomAreaTable)
	{	
		this.zoomAreaTable = zoomAreaTable;
	}
	
	
	//Gui getters
	
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
