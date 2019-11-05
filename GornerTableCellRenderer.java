package task3;

import java.awt.Color; 
import java.awt.Component; 
import java.awt.FlowLayout; 
import java.text.DecimalFormat; 
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat; 
import javax.swing.JLabel; 
import javax.swing.JPanel;
import javax.swing.JTable; 
import javax.swing.table.TableCellRenderer;

public class GornerTableCellRenderer implements TableCellRenderer{
	private JPanel panel = new JPanel(); 
	private JLabel label = new JLabel(); 
	// ���� ������, ��������� ������������� ������� ��������� � needle // (�������). ����������� �������� ������ ������ � ����� ����, � ���� // ����� ���� - ������� 
	private String needle = null; 
	private Double low = null;
	private Double high = null;
	private DecimalFormat formatter = (DecimalFormat)NumberFormat.getInstance(); 
	
	public GornerTableCellRenderer() { 
		// ���������� ������ 5 ������ ����� ������� 
		formatter.setMaximumFractionDigits(5); 
		// �� ������������ ����������� (�.�. �� �������� ������ // �� ��������, �� ���������), �.�. ���������� ����� ��� "1000", // � �� "1 000" ��� "1,000" 
		formatter.setGroupingUsed(false); 
		// ���������� � �������� ����������� ������� ����� �����, � �� // �������. �� ���������, � ������������ ���������� // ������/�������� ������� ����� ���������� ������� 
		DecimalFormatSymbols dottedDouble = formatter.getDecimalFormatSymbols(); 
		dottedDouble.setDecimalSeparator('.');
		formatter.setDecimalFormatSymbols(dottedDouble); 
		// ���������� ������� ������ ������ 
		panel.add(label); 
		// ���������� ������������ ������� �� ������ ���� ������ 
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
	}
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) { 
		// ������������� double � ������ � ������� �������������� 
		String formattedDouble = formatter.format(value); 
		// ���������� ����� ������� ������ ���������� ������������� ����� 
		label.setText(formattedDouble);
		if((col+row)%2 == 0){
			panel.setBackground(Color.BLACK);
			panel.setForeground(Color.WHITE);
		}
		else{
			panel.setBackground(Color.WHITE);
		}
		if(col!=0 && needle!=null && needle.equals(formattedDouble)){ // ����� ������� = 1 (�.�. ������ �������) + ������ �� null // (������ ���-�� ����) + // �������� ������ ��������� �� ��������� ������ ������� - // �������� ������ ��� ������ � ������� ���� 
			panel.setBackground(Color.RED); 
		}
		if(col!=0 && low!=null && low.compareTo((Double)value) == -1 && high.compareTo((Double)value) == 1){
			panel.setBackground(Color.RED); 
		}
		return panel;
	}
	
	public void setNeedle(String needle) { 
		this.needle = needle; 
	}
	
	public void setInterval(Double low, Double high){ 
		this.low = low;
		this.high = high;
	}
}
	
	

