package report;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ReportTableModel extends AbstractTableModel {
	private final String[] columnNames = { "제목", "작성자", "받는이", "작성 날짜" };
	private List<Report> reports = new ArrayList<>();

	public void setReports(List<Report> reports) {
		this.reports = reports;
		fireTableDataChanged();
	}

	public Report getReport(int rowIndex) {
		return reports.get(rowIndex);
	}

	@Override
	public int getRowCount() {
		return reports.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columnNames[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Report report = reports.get(rowIndex);
		switch (columnIndex) {
		case 0:
			return report.getReportTitle();
		case 1:
			return report.getFromId();
		case 2:
			return report.getToId();
		case 3:
			return report.getReportDate().toString();
		default:
			return null;
		}
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public void setRowCount(int i) {
		// TODO Auto-generated method stub
		
	}

	public void addRow(Vector<Object> row) {
		// TODO Auto-generated method stub
		
	}
}
