package gui.tableManager;


import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTree;
import data.dataKeeper.GlobalDataKeeper;
import gui.mainEngine.Gui;
import gui.tableElements.commons.JvTable;
import gui.tableElements.tableConstructors.TableConstructionIDU;
import gui.tableElements.tableConstructors.TableConstructionWithClusters;
import gui.treeManager.FillTreeManager;
import phaseAnalyzer.engine.PhaseAnalyzerMainEngine;
import tableClustering.clusterExtractor.engine.TableClusteringMainEngine;

public class FillTableManager {
	
	private GlobalDataKeeper globalDataKeeper;
	private Gui gui;
	private String inputCsv,outputAssessment1,outputAssessment2;
	private Float timeWeight,changeWeight;
	private Boolean preProcessingTime,preProcessingChange;
	private Integer numberOfPhases,numberOfClusters;
	private Integer[] segmentSize;
	private String[] finalColumns=null;
	private String[][] finalRows=null;
	private JvTable LifeTimeTable=null;
	private Integer[] segmentSizeZoomArea=new Integer[4];
	private String[] finalColumnsZoomArea=null;
	private String[][] finalRowsZoomArea=null;
	private JTabbedPane tabbedPane;
	private JvTable zoomAreaTable;
	
	FillTreeManager fillTreeManager; 
	

	public FillTableManager(Gui gui, GlobalDataKeeper globalDataKeeper,JTabbedPane tabbedPane, JvTable zoomAreaTable,  JvTable LifeTimeTable, JLabel treeLabel,  JTree tablesTree, JPanel sideMenu, JPanel tablesTreePanel, JScrollPane treeScrollPane, String inputCsv, String outputAssessment1, String outputAssessment2)
	{
		this.gui = gui;
		this.globalDataKeeper = globalDataKeeper;
		this.tabbedPane = tabbedPane;
		this.inputCsv = inputCsv;
		this.outputAssessment1 = outputAssessment1;
		this.outputAssessment2 = outputAssessment2;
		fillTreeManager = new FillTreeManager(treeLabel,tablesTree,sideMenu,tablesTreePanel,treeScrollPane);
	}
	
	public void fillTable() {
		TableConstructionIDU table=new TableConstructionIDU(globalDataKeeper);
		final String[] columns=table.constructColumns();
		final String[][] rows=table.constructRows();
		segmentSizeZoomArea = table.getSegmentSize();

		finalColumnsZoomArea=columns;
		finalRowsZoomArea=rows;
		
		//These setters needed so values in gui be updated and zoomTablemodel etc can access them
		gui.setFinalColumnsZoomArea(finalColumnsZoomArea);
		gui.setFinalRowsZoomArea(finalRowsZoomArea);
		gui.setGlobalDataKeeper(globalDataKeeper);
		gui.setInputCsv(inputCsv);
		gui.setoutputAssessment1(outputAssessment1);
		gui.setoutputAssessment2(outputAssessment2);
		gui.setSegmentSizeZoomArea(segmentSizeZoomArea);
		
		
		
		tabbedPane.setSelectedIndex(0);
		ZoomTableModel zoomTableModel = new ZoomTableModel(gui);
		zoomAreaTable = zoomTableModel.getZoomAreaTable();
		gui.setZoomAreaTable(zoomAreaTable);
		
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
			
			///setters to gui to update finalRows and finalColumns
			gui.setFinalRows(finalRows);
			gui.setFinalColumns(finalColumns);
			gui.setSegmentSize(segmentSize);
			gui.setTabbedPane(tabbedPane);
		
			
			GeneralTablePhases generalTablePhases = new GeneralTablePhases(gui);
			LifeTimeTable = generalTablePhases.getLifeTimeTable();
			
			gui.setLifeTimeTable(LifeTimeTable);
			
			fillTreeManager.fillClustersTree(globalDataKeeper);
		}
		System.out.println("Schemas:"+globalDataKeeper.getAllPPLSchemas().size());
		System.out.println("Transitions:"+globalDataKeeper.getAllPPLTransitions().size());
		System.out.println("Tables:"+globalDataKeeper.getAllPPLTables().size());
	}
	
	
	public GlobalDataKeeper getGlobalDataKeeper() {
		return globalDataKeeper;
	}

	public JTabbedPane getTabbedPane() {
		return tabbedPane;
	}
	
	public JvTable getLifeTimeTable() {
		return LifeTimeTable;
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
	
	public String[][] getFinalRows(){
		return finalRows;
	}
	
	public String[] getFinalColumns() {
		return finalColumns;
	}
	
	public Integer[] getSegmentSize() {
		return segmentSize;
	}
	
	public JvTable getZoomAreaTable() {
		return zoomAreaTable;
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
