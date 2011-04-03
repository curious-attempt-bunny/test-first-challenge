package com.curiousattemptbunny.testfirstchallenge;

import javax.swing.table.AbstractTableModel;

public class SheetTableModel extends AbstractTableModel {

	private final Sheet sheet;

	public SheetTableModel(Sheet sheet) {
		this.sheet = sheet;
	}
	
	public int getRowCount() {
		return 100;
	}

	public int getColumnCount() {
		return 50;
	}

	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return (rowIndex+1).toString()
		}
		return sheet.get(getCellName(rowIndex, columnIndex));
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		sheet.put(getCellName(rowIndex, columnIndex), aValue);
		fireTableDataChanged();
	}
	
	String getCellName(int rowIndex, int columnIndex) {
		getColumnName(columnIndex)+(rowIndex+1)
	}
	
	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "";
		}
		
		if (column <= 26) {
			return String.valueOf((char)64+column)
		}
		
		return String.valueOf((char)64+(column / 26)) + String.valueOf((char)64+(column%26)) 
	}
	
	String getLiteralValueAt(int rowIndex, columnIndex) {
		return sheet.getLiteral(getCellName(rowIndex, columnIndex));
	}

}
