package task3;

import javax.swing.table.AbstractTableModel;

public class GornerTableModel extends AbstractTableModel{
	private Double[] coefficients;  //!!coefficients[0] is a divider
	private Double[] new_coef;
	private Double[] new_coef2;
	private Double from; 
	private Double to;
	private Double step; 
	
	private void calculate_new_coef(){
		new_coef[0] = coefficients[1];
		int size = coefficients.length;
		for(int i=2; i<size; i++){
			new_coef[i-1] = new_coef[i-2]*coefficients[0] + coefficients[i];
		}
	}
	private void calculate_new_coef2(){
		int size = coefficients.length;
		new_coef2[0] = coefficients[size-1];
		for(int i=2; i<size; i++){
			new_coef2[i-1] = new_coef2[i-2]*coefficients[size-1] + coefficients[size-i];
		}
	}
	public GornerTableModel(Double from, Double to, Double step, Double[] coefficients) { 
		this.from = from; 
		this.to = to;
		this.step = step;
		this.coefficients = coefficients;
		new_coef = new Double[coefficients.length];
		new_coef2 = new Double[coefficients.length];
		calculate_new_coef();
		calculate_new_coef2();
	} 
	public Double getFrom() { 
		return from; 
	} 
	public Double getTo() { 
		return to; 
	}
	public Double getStep() {
		return step; 
	}
	public int getColumnCount() { 
		// � ������ ������ ��� ������� 
		return 4; 
	} 
	public int getRowCount() {
		// ��������� ���������� ����� ����� ������� � ������ ������� // ������ �� ���� ������������� 
		return new Double(Math.ceil((to-from)/step)).intValue()+1; 
	}
	public Object getValueAt(int row, int col) { 
		// ��������� �������� X ��� ������_������� + ���*�����_������ 
		double x = from + step*row; 
		if (col==0) {
			// ���� ������������� �������� 1-�� �������, �� ��� X 
			return x; 
		} 
		if(col ==1){
			Double result = 0.0;
			int size = new_coef.length-1;
			for(int i=0; i<size; i++){
				result += new_coef[i]*Math.pow(x, i);
			}
			return result;
		}
		if(col==2){
			Double result = 0.0;
			int size = new_coef2.length-1;
			for(int i=0; i<size; i++){
				result += new_coef2[i]*Math.pow(x, i);
			}
			return result;
		}
		if(col==3){
			Double result = 0.0;
			result = (Double)getValueAt(row, 1) - (Double)this.getValueAt(col, 2);
			return result;
		}
		else{
			return 0;
		}
	}
	public String getColumnName(int col) { 
		switch (col) { 
		case 0: 
			// �������� 1-�� ������� 
			return "�������� X"; 
		default: 
			// �������� 2-�� ������� 
			return "�������� ����������"; 
		} 
	} 
	public Class<?> getColumnClass(int col) { 
		// � � 1-�� � �� 2-�� ������� ��������� �������� ���� Double 
		return Double.class; 
	}
}
	
		
