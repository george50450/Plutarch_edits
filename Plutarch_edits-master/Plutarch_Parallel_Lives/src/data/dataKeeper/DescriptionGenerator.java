package data.dataKeeper;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;

import javax.swing.JTable;
import javax.swing.JTextArea;

import gui.mainEngine.Gui;

public class DescriptionGenerator {
	
	private JTable table;
	private Object value;
	private boolean isSelected;
	private boolean hasFocus;
	private int row;
	private int column;
	private String[][] rowsZoom;
	private int wholeColZoomArea;
	private int wholeCol;
	private int selectedColumnZoomArea;
	private GlobalDataKeeper globalDataKeeper; 
	private Gui gui;
	private String[][] finalRowsZoomArea;
	private int selectedColumn=-1;
	private String[][] finalRows;
	private ArrayList<String> selectedFromTree=new ArrayList<String>();
	
	public DescriptionGenerator(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column, String[][] rowsZoom, Gui gui)
	{
		this.gui = gui;
		this.table = table;
		this.value = value;
		this.isSelected = isSelected;
		this.hasFocus = hasFocus;
		this.row = row;
		this.column = column;
		this.rowsZoom = rowsZoom;
		this.wholeColZoomArea = gui.getWholeColZoomArea();
		this.wholeCol = gui.getWholeCol();
		this.globalDataKeeper = gui.getGlobalDataKeeper();
		this.selectedColumnZoomArea = gui.getSelectedColumnZoomArea();
		this.finalRowsZoomArea = gui.getFinalRowsZoomArea();
		this.finalRows = gui.getFinalRows();
		this.selectedFromTree = gui.getSelectedFromTree();
	}
	
	public DescriptionGenerator(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column, Gui gui)
	{
		this.gui = gui;
		this.table = table;
		this.value = value;
		this.isSelected = isSelected;
		this.hasFocus = hasFocus;
		this.row = row;
		this.column = column;
	}
	
	public String generateMakeZoomAreaTableDescription()
	{
		
		 if(column==wholeColZoomArea){
	        	
		 	String description="Transition ID:"+table.getColumnName(column)+"\n";
        	description=description+"Old Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
        	description=description+"New Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n";		        			
 			description=description+"Transition Changes:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterChangesForOneTr(rowsZoom)+"\n";
 			description=description+"Additions:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterAdditionsForOneTr(rowsZoom)+"\n";
 			description=description+"Deletions:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterDeletionsForOneTr(rowsZoom)+"\n";
 			description=description+"Updates:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfClusterUpdatesForOneTr(rowsZoom)+"\n";

 		
	 		return description;
	 		}
	        else if(selectedColumnZoomArea==0){
	        	if (isSelected){
	        		String description="Table:"+finalRowsZoomArea[row][0]+"\n";
	        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
	        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
	        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
	        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
	        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTotalChangesForOnePhase(Integer.parseInt(table.getColumnName(1)), Integer.parseInt(table.getColumnName(table.getColumnCount()-1)))+"\n";
	        		
	        		return description;
	        	}
	        }
	        else{
	        	if (isSelected && hasFocus){
		        	
	        		String description="";
	        		if(!table.getColumnName(column).contains("Table name")){
		        		description="Table:"+finalRowsZoomArea[row][0]+"\n";
		        		description=description+"Old Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
		        		description=description+"New Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n";		        		
		        		if(globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTableChanges().getTableAtChForOneTransition(Integer.parseInt(table.getColumnName(column)))!=null){
		        			description=description+"Transition Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTableChanges().getTableAtChForOneTransition(Integer.parseInt(table.getColumnName(column))).size()+"\n";
		        			description=description+"Additions:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getNumberOfAdditionsForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
		        			description=description+"Deletions:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getNumberOfDeletionsForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
		        			description=description+"Updates:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getNumberOfUpdatesForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
		        			
		        		}
		        		else{
		        			description=description+"Transition Changes:0"+"\n";
		        			description=description+"Additions:0"+"\n";
		        			description=description+"Deletions:0"+"\n";
		        			description=description+"Updates:0"+"\n";
		        		}
		        		
		        		return description;
	        		}
		        }
	        	
	        }
		 return null;
	
	}
	
	
	public String generateMakeZoomAreaTableForClusterDescription()
	{
		String tmpValue=finalRowsZoomArea[row][column];
		if(column==wholeColZoomArea && wholeColZoomArea!=0){
        	
        	String description=table.getColumnName(column)+"\n";
          	description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getStartPos()+"\n";
    		description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getEndPos()+"\n";
    		description=description+"Total Changes For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalUpdates()+"\n";
    		description=description+"Additions For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalAdditionsOfPhase()+"\n";
    		description=description+"Deletions For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalDeletionsOfPhase()+"\n";
    		description=description+"Updates For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalUpdatesOfPhase()+"\n";
        	
    		
        	return description;
       
        }
        else if(selectedColumnZoomArea==0){
        	if (isSelected){
        		String description="Table:"+finalRowsZoomArea[row][0]+"\n";
        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTotalChanges()+"\n";
        		
        		return description;
        	}
        }
        else{
        	
        	
        	if (isSelected && hasFocus){
	        	
        		String description="";
        		if(!table.getColumnName(column).contains("Table name")){
        			description="Transition "+table.getColumnName(column)+"\n";
        			description=description+"Old Version:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
	        		description=description+"New Version:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n\n";
        			description=description+"Table:"+finalRowsZoomArea[row][0]+"\n";
	        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
	        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
	        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
	        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
	        		description=description+"Total Changes For This Phase:"+tmpValue+"\n";
	        		
	        		return description;

        		}
	        }
        	
        }
		return null;

	}
	
	public String generateMakeGeneralTablePhasesDescription(String tmpValue,String[][] finalRows, GlobalDataKeeper globalDataKeeper,int wholeCol, int selectedColumn)
	{		 
		 if(column==wholeCol && wholeCol!=0){
			 String description=table.getColumnName(column)+"\n";
	         description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getStartPos()+"\n";
	         description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getEndPos()+"\n";
	         description=description+"Total Changes For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalUpdates()+"\n";
     		 description=description+"Additions For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalAdditionsOfPhase()+"\n";
     		 description=description+"Deletions For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalDeletionsOfPhase()+"\n";
     		 description=description+"Updates For This Phase:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getTotalUpdatesOfPhase()+"\n";
	        	
     		 return description;
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
	        		
	        		return description;
	        	}
	        	else{
	        		String description="Table:"+finalRows[row][0]+"\n";
	        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirth()+"\n";
	        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirthVersionID()+"\n";
	        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeath()+"\n";
	        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeathVersionID()+"\n";
	        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getTotalChanges()+"\n";
	        		
	        		return description;
	        	}

	        }
	     }
	     else{
	    	 if (isSelected && hasFocus){
        		if(!table.getColumnName(column).contains("Table name")){
        			if(finalRows[row][0].contains("Cluster")){

		        		String description=finalRows[row][0]+"\n";
		        		description=description+"Tables:"+globalDataKeeper.getClusterCollectors().get(0).getClusters().get(row).getNamesOfTables().size()+"\n\n";
		        		description=description+table.getColumnName(column)+"\n";
		        		description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getStartPos()+"\n";
		        		description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getEndPos()+"\n\n";
		        		description=description+"Total Changes For This Phase:"+tmpValue+"\n";
		        		
		        		return description;
		        		
	        		}
	        		else{
	        			String description=table.getColumnName(column)+"\n";
		        		description=description+"First Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getStartPos()+"\n";
		        		description=description+"Last Transition ID:"+globalDataKeeper.getPhaseCollectors().get(0).getPhases().get(column-1).getEndPos()+"\n\n";
	        			description=description+"Table:"+finalRows[row][0]+"\n";
		        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirth()+"\n";
		        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getBirthVersionID()+"\n";
		        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeath()+"\n";
		        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRows[row][0]).getDeathVersionID()+"\n";
		        		description=description+"Total Changes For This Phase:"+tmpValue+"\n";
		        		
		        		return description;
		        		
	        		}
        		}		
		    }	
	     }
		 return null;
	}

	
	public String generateZoomTableModelDescription(String tmpValue, int wholeColZoomArea, int selectedColumnZoomArea,GlobalDataKeeper globalDataKeeper,String [][] finalRowsZoomArea)
	{
		
		if(column==wholeColZoomArea && wholeColZoomArea!=0){
        	
        	String description="Transition ID:"+table.getColumnName(column)+"\n";
        	description=description+"Old Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
    		description=description+"New Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n";		        		 		
			description=description+"Transition Changes:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfChangesForOneTr()+"\n";
			description=description+"Additions:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfAdditionsForOneTr()+"\n";
			description=description+"Deletions:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfDeletionsForOneTr()+"\n";
			description=description+"Updates:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNumberOfUpdatesForOneTr()+"\n";

			
    		return description;
        }
        else if(selectedColumnZoomArea==0){
        	if (isSelected){
        		String description="Table:"+finalRowsZoomArea[row][0]+"\n";
        		description=description+"Birth Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirth()+"\n";
        		description=description+"Birth Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getBirthVersionID()+"\n";
        		description=description+"Death Version Name:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeath()+"\n";
        		description=description+"Death Version ID:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getDeathVersionID()+"\n";
        		description=description+"Total Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTotalChanges()+"\n";

        		return description;
        	}
        }
        else{
      	
        	if (isSelected && hasFocus){

        		String description="";
        		if(!table.getColumnName(column).contains("Table name")){
	        		description="Table:"+finalRowsZoomArea[row][0]+"\n";
	        		
	        		description=description+"Old Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getOldVersionName()+"\n";
	        		description=description+"New Version Name:"+globalDataKeeper.getAllPPLTransitions().get(Integer.parseInt(table.getColumnName(column))).getNewVersionName()+"\n";		        		
	        		if(globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTableChanges().getTableAtChForOneTransition(Integer.parseInt(table.getColumnName(column)))!=null){
	        			description=description+"Transition Changes:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getTableChanges().getTableAtChForOneTransition(Integer.parseInt(table.getColumnName(column))).size()+"\n";
	        			description=description+"Additions:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getNumberOfAdditionsForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
	        			description=description+"Deletions:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getNumberOfDeletionsForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
	        			description=description+"Updates:"+globalDataKeeper.getAllPPLTables().get(finalRowsZoomArea[row][0]).getNumberOfUpdatesForOneTr(Integer.parseInt(table.getColumnName(column)))+"\n";
	        		}
	        		else{
	        			description=description+"Transition Changes:0"+"\n";
	        			description=description+"Additions:0"+"\n";
	        			description=description+"Deletions:0"+"\n";
	        			description=description+"Updates:0"+"\n";
	        		}
	        		
	        		return description;
        		}
	        }
        }
		return null;
	}
	
}
