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
import gui.dialogs.ParametersJDialog;
import gui.mainEngine.DetailedTableModel;
import gui.mainEngine.Gui;
import gui.tableElements.tableConstructors.TableConstructionIDU;
import gui.tableElements.tableConstructors.TableConstructionPhases;
import gui.tableElements.tableConstructors.TableConstructionWithClusters;
import phaseAnalyzer.engine.PhaseAnalyzerMainEngine;
import tableClustering.clusterExtractor.engine.TableClusteringMainEngine;

public class TableListener extends JFrame implements ActionListener {

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
	
	public TableListener(Gui gui, JMenu mnTable) {
		this.gui = gui;
		this.mnTable = mnTable;
		
		ShowPLD();
		ShowPhasesPLD();
		ShowPhasesWithClustersPLD();
		DetailedLifeTimeTable();
	}
	
	public void ShowPLD() {
		JMenuItem mntmShowGeneralLifetimeIDU = new JMenuItem("Show PLD");
		mntmShowGeneralLifetimeIDU.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
	
	public void ShowPhasesPLD() {
		JMenuItem mntmShowGeneralLifetimePhasesPLD = new JMenuItem("Show Phases PLD");
		mntmShowGeneralLifetimePhasesPLD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
							gui.makeGeneralTablePhases();
							gui.fillPhasesTree();
						} else {
							JOptionPane.showMessageDialog(null, "Extract Phases first");
						}
					}
				} else {
					JOptionPane.showMessageDialog(null, "Please select a project first!");
				}
			}
		});
		mnTable.add(mntmShowGeneralLifetimePhasesPLD);
	}
	
	public void ShowPhasesWithClustersPLD() {
		JMenuItem mntmShowGeneralLifetimePhasesWithClustersPLD = new JMenuItem("Show Phases With Clusters PLD");
		mntmShowGeneralLifetimePhasesWithClustersPLD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
							gui.makeGeneralTablePhases();
							gui.fillClustersTree();
						}
						else{
							JOptionPane.showMessageDialog(null, "Extract Phases first");
						}
					}
				}
				else{
					JOptionPane.showMessageDialog(null, "Please select a project first!");
				}
			}
		});
		mnTable.add(mntmShowGeneralLifetimePhasesWithClustersPLD);
	}
	
	public void DetailedLifeTimeTable() {
		JMenuItem mntmShowLifetimeTable = new JMenuItem("Show Full Detailed LifeTime Table");
		mntmShowLifetimeTable.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					new DetailedTableModel(columns,rows,true,segmentSizeDetailedTable, LifeTimeTable, selectedColumn);
				} else {
					JOptionPane.showMessageDialog(null, "Select a Project first");
					return;
				}
			}
		});
		mnTable.add(mntmShowLifetimeTable);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
	}
}
