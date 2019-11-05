package task3;

import java.awt.BorderLayout; 
import java.awt.Color; 
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box; 
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu; 
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel; 
import javax.swing.JScrollPane;
import javax.swing.JTable; 
import javax.swing.JTextField;

public class Main extends JFrame {
	private static final int WIDTH = 700;
	private static final int HEIGHT = 500;
	private static final String String = null;
	private Double[] coefficients; 
	private JFileChooser fileChooser = null;
	private JMenuItem saveToTextMenuItem;
	private JMenuItem saveToGraphicsMenuItem; 
	private JMenuItem searchValueMenuItem;
	private JMenuItem searchIntervalMenuItem;
	private JMenuItem referenceMenuItem;
	private JTextField textFieldFrom; 
	private JTextField textFieldTo;
	private JTextField textFieldStep;
	private Box hBoxResult;
	private GornerTableCellRenderer renderer = new GornerTableCellRenderer();
	private GornerTableModel data; 
	//
	public Main(Double[] coefficients) {
		super("������������� ���������� �� ������� �� ����� �������"); 
		this.coefficients = coefficients;
		setSize(WIDTH, HEIGHT); 
		Toolkit kit = Toolkit.getDefaultToolkit(); // �������������� ���� ���������� �� ������ 
		setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
		// ������� ���� 
		JMenuBar menuBar = new JMenuBar();
		// ���������� ���� � �������� �������� ���� ����������
		setJMenuBar(menuBar);
		// �������� � ���� ����� ���� "����" 
		JMenu fileMenu = new JMenu("����"); 
		menuBar.add(fileMenu); // ������� ����� ���� "�������"
		JMenu tableMenu = new JMenu("�������");
		menuBar.add(tableMenu);
		JMenu refMenu = new JMenu("�������");
		menuBar.add(refMenu);
		final ImageIcon icon = new ImageIcon("f1.png");
	Action saveToTextAction = new AbstractAction("��������� � ��������� ����") { 
		public void actionPerformed(ActionEvent event) {
			if (fileChooser==null) { 
				fileChooser = new JFileChooser(); 
				fileChooser.setCurrentDirectory(new File("."));
			}
			if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION){ 
				saveToTextFile(fileChooser.getSelectedFile());
			}
		}
	};
	saveToTextMenuItem = fileMenu.add(saveToTextAction);
	saveToTextMenuItem.setEnabled(false);
	Action saveToGraphicsAction = new AbstractAction("��������� ������ ��� ���������� �������") {
		public void actionPerformed(ActionEvent event) { 
			if (fileChooser==null) { 
				fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(".")); }
			if (fileChooser.showSaveDialog(Main.this) == JFileChooser.APPROVE_OPTION);{
				saveToGraphicsFile( fileChooser.getSelectedFile());
			}
		}
	};
	saveToGraphicsMenuItem = fileMenu.add(saveToGraphicsAction);
	saveToGraphicsMenuItem.setEnabled(false);	
	// ������� ����� �������� �� ������ �������� ���������� 
	Action searchValueAction = new AbstractAction("����� �������� ����������") { 
		public void actionPerformed(ActionEvent event) { 
			// ��������� ������������ ������ ������� ������ 
			String value = JOptionPane.showInputDialog(Main.this, "������� �������� ��� ������", "����� ��������", JOptionPane.QUESTION_MESSAGE);
			// ���������� ��������� �������� � �������� ������
			renderer.setNeedle(value); // �������� ������� 
			getContentPane().repaint();
		} 
	};
	Action searchInterval = new AbstractAction("����� �� ���������"){
		public void actionPerformed(ActionEvent event) { 
			// ��������� ������������ ������ ������� ������ 
			Double value = Double.parseDouble(JOptionPane.showInputDialog(Main.this, "������� ������ �������� ��� ������", "����� ��������", JOptionPane.QUESTION_MESSAGE));
			Double value1 = Double.parseDouble(JOptionPane.showInputDialog(Main.this, "������� ������� �������� ��� ������", "����� ��������", JOptionPane.QUESTION_MESSAGE));
			// ���������� ��������� �������� � �������� ������
			renderer.setInterval(value, value1);
			getContentPane().repaint();
		} 
	};
	// �������� �������� � ���� "�������" 
	
	searchValueMenuItem = tableMenu.add(searchValueAction);
	// �� ��������� ����� ���� �������� ����������� (������ ��? ���)
	searchValueMenuItem.setEnabled(false); 
	Action show_reference = new AbstractAction("� ���������"){
		public void actionPerformed(ActionEvent event){
			JOptionPane.showMessageDialog(Main.this, "��������� ������ \n �����, 6 ������", "� ���������", JOptionPane.INFORMATION_MESSAGE, icon);
		}
	};
	referenceMenuItem = refMenu.add(show_reference);
	referenceMenuItem.setEnabled(true);
	searchIntervalMenuItem = tableMenu.add(searchInterval);
	searchIntervalMenuItem.setEnabled(false);
	// ������� ������� � ������ ����� ��� ������ ������� � ���� 
	// ������� ������� ��� ����� ����� ������� ������� 
	JLabel labelForFrom = new JLabel("X ���������� �� ��������� ��:");
	// ������� ��������� ���� ��� ����� �������� ������ � 10 �������� // �� ��������� �� ��������� 0.0 
	textFieldFrom = new JTextField("0.0", 10); 
	// ���������� ������������ ������ ������ �����������������, ����� // ������������� ���������� ������� ���� ����� 
	textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize()); // ������� ������� ��� ����� ����� ������� ������� 
	JLabel labelForTo = new JLabel("��:"); // ������� ��������� ���� ��� ����� �������� ������ � 10 �������� // �� ��������� �� ��������� 1.0 
	textFieldTo = new JTextField("1.0", 10); // ���������� ������������ ������ ������ �����������������, ����� // ������������� ���������� ������� ���� ����� 
	textFieldTo.setMaximumSize(textFieldTo.getPreferredSize()); // ������� ������� ��� ����� ���� ������������� 
	JLabel labelForStep = new JLabel("� �����:"); // ������� ��������� ���� ��� ����� �������� ������ � 10 �������� // �� ��������� �� ��������� 1.0
	textFieldStep = new JTextField("0.1", 10); // ���������� ������������ ������ ������ �����������������, ����� // ������������� ���������� ������� ���� �����
	textFieldStep.setMaximumSize(textFieldStep.getPreferredSize()); // ������� ��������� 1 ���� "������� � �������������� ��������" 
	Box hboxRange = Box.createHorizontalBox(); // ������ ��� ���������� ��� ����� "���?����" 
	hboxRange.setBorder(BorderFactory.createBevelBorder(1)); 
	hboxRange.add(Box.createHorizontalGlue());
	hboxRange.add(labelForFrom); 
	hboxRange.add(Box.createHorizontalStrut(10)); 
	hboxRange.add(textFieldFrom); 
	hboxRange.add(Box.createHorizontalStrut(20)); 
	hboxRange.add(labelForTo); 
	hboxRange.add(Box.createHorizontalStrut(10)); 
	hboxRange.add(textFieldTo); 
	hboxRange.add(Box.createHorizontalStrut(20)); 
	hboxRange.add(labelForStep); 
	hboxRange.add(Box.createHorizontalStrut(10)); 
	hboxRange.add(textFieldStep); 
	hboxRange.add(Box.createHorizontalGlue()); 
	hboxRange.setPreferredSize(new Dimension(new Double(hboxRange.getMaximumSize().getWidth()).intValue(), new Double(hboxRange.getMinimumSize().getHeight()).intValue()*2));
	getContentPane().add(hboxRange, BorderLayout.NORTH); 
	// ������� ������ "���������" 
	JButton buttonCalc = new JButton("���������"); 
	// ������ �������� �� ������� "���������" � ��������� � ������ 
	buttonCalc.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ev){
		try { // ������� �������� ������ � ����� �������, ���� 
			Double from = Double.parseDouble(textFieldFrom.getText()); 
			Double to = Double.parseDouble(textFieldTo.getText()); 
			Double step = Double.parseDouble(textFieldStep.getText()); 
			// �� ������ ��������� ������ ������� ����� ��������� ������ ������� 
			data = new GornerTableModel(from, to, step, Main.this.coefficients); 
			// ������� ����� ��������� ������� 
			JTable table = new JTable(data); 
			// ���������� � �������� ������������� ����� ��� ������ Double ������������� ������������ 
			table.setDefaultRenderer(Double.class, renderer); 
			// ���������� ������ ������ ������� � 30 �������� 
			table.setRowHeight(30); 
			// ������� ��� ��������� �������� �� ���������� hBoxResult 
			hBoxResult.removeAll(); 
			// �������� � hBoxResult �������, "��?������" � ������ � �������� ��������� 
			hBoxResult.add(new JScrollPane(table)); 
			// �������� ������� ���������� �������� ���� 
			getContentPane().validate(); // �������� ��� ��������� ���� ��� ���������
			saveToTextMenuItem.setEnabled(true); 
			saveToGraphicsMenuItem.setEnabled(true); 
			searchValueMenuItem.setEnabled(true);
			searchIntervalMenuItem.setEnabled(true);
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(Main.this, "������ � ������� ������ ����� � ��������� ������", "��������� ������ �����", JOptionPane.WARNING_MESSAGE);
		}
	}
	});	
	JButton buttonReset = new JButton("�������� ����"); 
	// ������ �������� �� ������� "�������� ����" � ��������� � ������ 
	buttonReset.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ev) { 
			// ���������� � ����� ����� �������� �� ��������� 
			textFieldFrom.setText("0.0"); 
			textFieldTo.setText("1.0"); 
			textFieldStep.setText("0.1");
			hBoxResult.removeAll(); 
			// �������� � ��������� ������ ������ 
			hBoxResult.add(new JPanel()); 
			// �������� �������� ���� ��� �����������
			saveToTextMenuItem.setEnabled(false); 
			saveToGraphicsMenuItem.setEnabled(false); 
			searchValueMenuItem.setEnabled(false);
			searchIntervalMenuItem.setEnabled(false);
			// �������� ������� ���������� �������� ���� 
			getContentPane().validate();
		}
	});
	Box hboxButtons = Box.createHorizontalBox();
	hboxButtons.setBorder(BorderFactory.createBevelBorder(1));
	hboxButtons.add(Box.createHorizontalGlue());
	hboxButtons.add(buttonCalc);
	hboxButtons.add(Box.createHorizontalStrut(30)); 
	hboxButtons.add(buttonReset);
	hboxButtons.add(Box.createHorizontalGlue()); 

	// ���������� ���������������� ������ ������� ������ ���������� ������������, ����� ��� // ���������� ���� ������� ������ �� ������� 
	hboxButtons.setPreferredSize(new Dimension(new Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2)); 
	// ���������� ��������� � �������� � ������ (�����) ������� ��������� ���������� 
	getContentPane().add(hboxButtons, BorderLayout.SOUTH); 
	// ������� ��� ������ ���������� ���� ��� ������ 
	hBoxResult = Box.createHorizontalBox();
	hBoxResult.add(new JPanel()); // ���������� ��������� hBoxResult � ������� (�����������) ������� ��������� ���������� 
	
	getContentPane().add(hBoxResult, BorderLayout.CENTER);
}

protected void saveToGraphicsFile(File selectedFile) { 
	try { 
		// ������� ����� �������� ����� ������, ������������ � ��������� ���� 
		DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
		// �������� � ����� ������ ������� �������� X � �����, �������� ���������� � ����� 
		for (int i = 0; i<data.getRowCount(); i++) { 
			out.writeDouble((Double)data.getValueAt(i,0)); 
			out.writeDouble((Double)data.getValueAt(i,1)); 
		} // ������� ����� ������ 
		out.close(); 
	} 
	catch (Exception e) {
	}
}

protected void saveToTextFile(File selectedFile) { 
	try { 
		// ������� ����� ���������� ����� ������, ������������ � ��������� ���� 
		PrintStream out = new PrintStream(selectedFile);
	out.println("���������� ������������� ���������� �� ����� �������"); 
	out.print("���������: "); 
	for (int i=0; i<coefficients.length; i++) { 
		out.print(coefficients[i] + "*X^" + (coefficients.length-i-1)); 
		if (i!=coefficients.length-1){ 
			out.print(" + ");
		}
	}
		out.println(""); 
		out.println("�������� �� " + data.getFrom() + " �� " + data.getTo() + " � ����� " + data.getStep()); 
		out.println("===================================================="); 
		// �������� � ����� ������ �������� � ������ 
		for (int i = 0; i<data.getRowCount(); i++) {
			out.println("�������� � ����� " + data.getValueAt(i,0) + " ����� " + data.getValueAt(i,1)); }
		// ������� ����� 
		out.close(); 

	}
	catch (FileNotFoundException e){		
	}
}
	
	
	
	
	public static void main(String[] args) {
		
		if (args.length==0) { 
			System.out.println("���������� ������������ ���������, ��� �������� �� ������ �� ������ ������������!"); 
			System.exit(-1); 
		} 
		// ��������������� ����� � ������� ������������� �������, ������� ���������� ��������� ������ 
		Double[] coefficients = new Double[args.length];
		
		int i = 0; 
		try { 
			// ��������� ���������, ������� ������������� �� � Double 
			for (String arg: args) { 
				coefficients[i++] = Double.parseDouble(arg); 
			} 
		} 
		catch (NumberFormatException ex) { 
			// ���� �������������� ���������� - �������� �� ������ � ����������� 
			System.out.println("������ �������������� ������ '" + args[i] + "' � ����� ���� Double"); 
			System.exit(-2); 
		}
		// ������� ��������� �������� ����, ������� ��� ������������ 
		Main frame = new Main(coefficients); 
		// ������ ��������, ����������� ��� �������� ���� 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
}