package gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;

import data.dataKeeper.GlobalDataKeeper;
import gui.tableElements.tableConstructors.TableConstructionAllSquaresIncluded;
import gui.tableElements.tableConstructors.TableConstructionIDU;
import gui.tableElements.tableConstructors.TableConstructionPhases;
import gui.tableElements.tableConstructors.TableConstructionWithClusters;
import gui.tableManager.DetailedTableModel;
import gui.tableManager.GeneralTablePhases;
import gui.tableManager.ZoomTableModel;
import gui.treeManager.FillTreeManager;
import gui.tableElements.commons.JvTable;
import gui.dialogs.ParametersJDialog;
import gui.mainEngine.Gui;
import phaseAnalyzer.engine.PhaseAnalyzerMainEngine;
import tableClustering.clusterExtractor.engine.TableClusteringMainEngine;

public class TableActions {

	private static final long serialVersionUID = 1L;
	
	private GlobalDataKeeper globalDataKeeper=null;
	private final Gui gui;
	private JMenu mnTable;
	private JTabbedPane tabbedPane;
	
	private String currentProject=null;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private Integer[] segmentSizeZoomArea=new Integer[4];
	private String[][] finalRowsZoomArea=null;
	private String[] finalColumnsZoomArea=null;
	
	private int wholeCol=-1;
	private Float timeWeight=null;
	private Float changeWeight=null;
	private Boolean preProcessingTime=null;
	private Boolean preProcessingChange=null;
	private Integer numberOfPhases=null;
	private Integer[] segmentSize=new Integer[4];
	private String[] finalColumns=null;
	private String[][] finalRows=null;
	private String inputCsv="";
	private String outputAssessment1="";
	private String outputAssessment2="";
	
	private Integer numberOfClusters=null;
	private Double birthWeight=null;
	private Double deathWeight=null;
	private Double changeWeightCl=null;
	
	private int selectedColumn=-1;
	private Integer[] segmentSizeDetailedTable=new Integer[3];
	private JvTable LifeTimeTable=null;
	private JScrollPane treeScrollPane= new JScrollPane();
	private JLabel treeLabel;
	private JTree tablesTree=new JTree();
	private JPanel sideMenu=new JPanel();
	private JPanel tablesTreePanel=new JPanel();
	
	// Test
	private JMenuItem mntmShowGeneralLifetimeIDU;
	
	private JMenuItem mntmShowGeneralLifetimePhasesPLD;
	
	private JMenuItem mntmShowGeneralLifetimePhasesWithClustersPLD;
	
	private JMenuItem mntmShowLifetimeTable;
	private DetailedTableModel detailedTableModel;
	
	public TableActions(Gui gui, JMenu mnTable,JLabel treeLabel,  JTree tablesTree, JPanel sideMenu, JPanel tablesTreePanel, JScrollPane treeScrollPane) {
		this.gui = gui;
		this.mnTable = mnTable;
		this.treeScrollPane = treeScrollPane;
		this.treeLabel = treeLabel;
		this.tablesTree = tablesTree;
		this.sideMenu = sideMenu;
		this.tablesTreePanel = tablesTreePanel;
	}
	
	public void showPLD() {
				currentProject = gui.getCurrentProject();
				globalDataKeeper = gui.getGlobalDataKeeper();
				zoomInButton = gui.getZoomInButton();
				zoomOutButton = gui.getZoomOutButton();
				tabbedPane = gui.getTabbedPane();
				
				if(!(currentProject==null)){
					zoomInButton.setVisible(true);
					zoomOutButton.setVisible(true);
					TableConstructionIDU table=new TableConstructionIDU(globalDataKeeper);
					final String[] columns=table.constructColumns();
					final String[][] rows=table.constructRows();
					segmentSizeZoomArea = table.getSegmentSize();
					System.out.println("Schemas: "+globalDataKeeper.getAllPPLSchemas().size());
					System.out.println("C: "+columns.length+" R: "+rows.length);
	
					finalColumnsZoomArea=columns;
					finalRowsZoomArea=rows;
					tabbedPane.setSelectedIndex(0);
					new ZoomTableModel(gui);
					FillTreeManager fillTreeManager = new FillTreeManager(treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
			        fillTreeManager.fillTree(globalDataKeeper);
				} else {
					JOptionPane.showMessageDialog(null, "Select a Project first");
					return;
				}
	
	}
	
	public void showPhasesPLD() {
		
			currentProject = gui.getCurrentProject();
			inputCsv= gui.getInputCsv();
			outputAssessment1 = gui.getOutputAssessment1();
			outputAssessment2 = gui.getOutputAssessment2();
			globalDataKeeper = gui.getGlobalDataKeeper();
			tabbedPane = gui.getTabbedPane();
			
			if(!(currentProject==null)){
				wholeCol=-1;
				ParametersJDialog jD = new ParametersJDialog(false);
				
				jD.setModal(true);

				jD.setVisible(true);
				
				if(jD.getConfirmation()){
		            timeWeight = jD.getTimeWeight();
		            changeWeight = jD.getChangeWeight();
		            preProcessingTime = jD.getPreProcessingTime();
		            preProcessingChange = jD.getPreProcessingChange();
				    numberOfPhases = jD.getNumberOfPhases();
		            
		            System.out.println(timeWeight+" "+changeWeight);
		            
					PhaseAnalyzerMainEngine mainEngine = new PhaseAnalyzerMainEngine(inputCsv,outputAssessment1,outputAssessment2,timeWeight,changeWeight,preProcessingTime,preProcessingChange);

					mainEngine.parseInput();		
					System.out.println("\n\n\n");
					mainEngine.extractPhases(numberOfPhases);
					
					mainEngine.connectTransitionsWithPhases(globalDataKeeper);
					globalDataKeeper.setPhaseCollectors(mainEngine.getPhaseCollectors());
					
					if(globalDataKeeper.getPhaseCollectors().size()!=0){
						TableConstructionPhases table=new TableConstructionPhases(globalDataKeeper);
						final String[] columns=table.constructColumns();
						final String[][] rows=table.constructRows();
						segmentSize=table.getSegmentSize();
						System.out.println("Schemas: "+globalDataKeeper.getAllPPLSchemas().size());
						System.out.println("C: "+columns.length+" R: "+rows.length);
	
						finalColumns=columns;
						finalRows=rows;
						tabbedPane.setSelectedIndex(0);
						new GeneralTablePhases(gui);
						FillTreeManager fillTreeManager = new FillTreeManager(treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
				        fillTreeManager.fillPhasesTree(globalDataKeeper);
					} else {
						JOptionPane.showMessageDialog(null, "Extract Phases first");
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Please select a project first!");
			}
	}
	
	public void showPhasesWithClustersPLD() {
		
		wholeCol=-1;
		currentProject = gui.getCurrentProject();
		inputCsv= gui.getInputCsv();
		outputAssessment1 = gui.getOutputAssessment1();
		outputAssessment2 = gui.getOutputAssessment2();
		globalDataKeeper = gui.getGlobalDataKeeper();
		tabbedPane = gui.getTabbedPane();
		
		if(!(currentProject==null)){
			
			ParametersJDialog jD=new ParametersJDialog(true);
			
			jD.setModal(true);
			
			jD.setVisible(true);
			
			if(jD.getConfirmation()){
			
	            timeWeight = jD.getTimeWeight();
	            changeWeight = jD.getChangeWeight();
	            preProcessingTime = jD.getPreProcessingTime();
	            preProcessingChange = jD.getPreProcessingChange();
			    numberOfPhases = jD.getNumberOfPhases();
			    numberOfClusters = jD.getNumberOfClusters();
			    birthWeight=jD.geBirthWeight();
			    deathWeight=jD.getDeathWeight();
			    changeWeightCl=jD.getChangeWeightCluster();
	            
	            System.out.println(timeWeight+" "+changeWeight);
	            
				PhaseAnalyzerMainEngine mainEngine = new PhaseAnalyzerMainEngine(inputCsv,outputAssessment1,outputAssessment2,timeWeight,changeWeight,preProcessingTime,preProcessingChange);
				
				mainEngine.parseInput();		
				System.out.println("\n\n\n");
				mainEngine.extractPhases(numberOfPhases);
				
				mainEngine.connectTransitionsWithPhases(globalDataKeeper);
				globalDataKeeper.setPhaseCollectors(mainEngine.getPhaseCollectors());
				TableClusteringMainEngine mainEngine2 = new TableClusteringMainEngine(globalDataKeeper,birthWeight,deathWeight,changeWeightCl);
				mainEngine2.extractClusters(numberOfClusters);
				globalDataKeeper.setClusterCollectors(mainEngine2.getClusterCollectors());
				mainEngine2.print();
				
				if(globalDataKeeper.getPhaseCollectors().size()!=0){
					TableConstructionWithClusters table=new TableConstructionWithClusters(globalDataKeeper);
					final String[] columns=table.constructColumns();
					final String[][] rows=table.constructRows();
					segmentSize=table.getSegmentSize();
					System.out.println("Schemas: "+globalDataKeeper.getAllPPLSchemas().size());
					System.out.println("C: "+columns.length+" R: "+rows.length);

					finalColumns=columns;
					finalRows=rows;
					tabbedPane.setSelectedIndex(0);
					new GeneralTablePhases(gui);
					FillTreeManager fillTreeManager = new FillTreeManager(treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
			        fillTreeManager.fillClustersTree(globalDataKeeper);
				} else {
					JOptionPane.showMessageDialog(null, "Extract Phases first");
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Please select a project first!");
		}
	}
	
	
	public void showDetailedLifeTimeTable() {
		currentProject = gui.getCurrentProject();
		LifeTimeTable = gui.getLifeTimeTable();
		selectedColumn = gui.getSelectedColumn();
		globalDataKeeper = gui.getGlobalDataKeeper();
		tabbedPane = gui.getTabbedPane();
		
		if(!(currentProject==null)){
			TableConstructionAllSquaresIncluded table=new TableConstructionAllSquaresIncluded(globalDataKeeper);
			final String[] columns=table.constructColumns();
			final String[][] rows=table.constructRows();
			segmentSizeDetailedTable=table.getSegmentSize();
			tabbedPane.setSelectedIndex(0);
			detailedTableModel = new DetailedTableModel(columns,rows,true,segmentSizeDetailedTable, LifeTimeTable, selectedColumn);
		} else {
			JOptionPane.showMessageDialog(null, "Select a Project first");
			return;
		}
	}


	
	// For test reasons
	public JMenuItem getMntmShowGeneralLifetimeIDU() {
		return mntmShowGeneralLifetimeIDU;
	}
	
	public JMenuItem getMntmShowGeneralLifetimePhasesPLD() {
		return mntmShowGeneralLifetimePhasesPLD;
	}
	
	public JMenuItem getMntmShowGeneralLifetimePhasesWithClustersPLD() {
		return mntmShowGeneralLifetimePhasesWithClustersPLD;
	}
	
	public JMenuItem getMntmShowLifetimeTable() {
		return mntmShowLifetimeTable;
	}
	
	public DetailedTableModel getDetailedTableModel() {
		return detailedTableModel;
	}
}