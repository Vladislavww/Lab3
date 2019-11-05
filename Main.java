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
		super("Табулирование многочлена на отрезке по схеме Горнера"); 
		this.coefficients = coefficients;
		setSize(WIDTH, HEIGHT); 
		Toolkit kit = Toolkit.getDefaultToolkit(); // Отцентрировать окно приложения на экране 
		setLocation((kit.getScreenSize().width - WIDTH)/2, (kit.getScreenSize().height - HEIGHT)/2);
		// Создать меню 
		JMenuBar menuBar = new JMenuBar();
		// Установить меню в качестве главного меню приложения
		setJMenuBar(menuBar);
		// Добавить в меню пункт меню "Файл" 
		JMenu fileMenu = new JMenu("Файл"); 
		menuBar.add(fileMenu); // Создать пункт меню "Таблица"
		JMenu tableMenu = new JMenu("Таблица");
		menuBar.add(tableMenu);
		JMenu refMenu = new JMenu("Справка");
		menuBar.add(refMenu);
		final ImageIcon icon = new ImageIcon("f1.png");
	Action saveToTextAction = new AbstractAction("Сохранить в текстовый файл") { 
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
	Action saveToGraphicsAction = new AbstractAction("Сохранить данные для построения графика") {
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
	// Создать новое действие по поиску значений многочлена 
	Action searchValueAction = new AbstractAction("Найти значение многочлена") { 
		public void actionPerformed(ActionEvent event) { 
			// Запросить пользователя ввести искомую строку 
			String value = JOptionPane.showInputDialog(Main.this, "Введите значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE);
			// Установить введенное значение в качестве иголки
			renderer.setNeedle(value); // Обновить таблицу 
			getContentPane().repaint();
		} 
	};
	Action searchInterval = new AbstractAction("Найти из диапазона"){
		public void actionPerformed(ActionEvent event) { 
			// Запросить пользователя ввести искомую строку 
			Double value = Double.parseDouble(JOptionPane.showInputDialog(Main.this, "Введите нижнее значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE));
			Double value1 = Double.parseDouble(JOptionPane.showInputDialog(Main.this, "Введите верхнее значение для поиска", "Поиск значения", JOptionPane.QUESTION_MESSAGE));
			// Установить введенное значение в качестве иголки
			renderer.setInterval(value, value1);
			getContentPane().repaint();
		} 
	};
	// Добавить действие в меню "Таблица" 
	
	searchValueMenuItem = tableMenu.add(searchValueAction);
	// По умолчанию пункт меню является недоступным (данных ещ? нет)
	searchValueMenuItem.setEnabled(false); 
	Action show_reference = new AbstractAction("О программе"){
		public void actionPerformed(ActionEvent event){
			JOptionPane.showMessageDialog(Main.this, "Программу сделал \n Яцков, 6 группа", "О программе", JOptionPane.INFORMATION_MESSAGE, icon);
		}
	};
	referenceMenuItem = refMenu.add(show_reference);
	referenceMenuItem.setEnabled(true);
	searchIntervalMenuItem = tableMenu.add(searchInterval);
	searchIntervalMenuItem.setEnabled(false);
	// Создать область с полями ввода для границ отрезка и шага 
	// Создать подпись для ввода левой границы отрезка 
	JLabel labelForFrom = new JLabel("X изменяется на интервале от:");
	// Создать текстовое поле для ввода значения длиной в 10 символов // со значением по умолчанию 0.0 
	textFieldFrom = new JTextField("0.0", 10); 
	// Установить максимальный размер равный предпочтительному, чтобы // предотвратить увеличение размера поля ввода 
	textFieldFrom.setMaximumSize(textFieldFrom.getPreferredSize()); // Создать подпись для ввода левой границы отрезка 
	JLabel labelForTo = new JLabel("до:"); // Создать текстовое поле для ввода значения длиной в 10 символов // со значением по умолчанию 1.0 
	textFieldTo = new JTextField("1.0", 10); // Установить максимальный размер равный предпочтительному, чтобы // предотвратить увеличение размера поля ввода 
	textFieldTo.setMaximumSize(textFieldTo.getPreferredSize()); // Создать подпись для ввода шага табулирования 
	JLabel labelForStep = new JLabel("с шагом:"); // Создать текстовое поле для ввода значения длиной в 10 символов // со значением по умолчанию 1.0
	textFieldStep = new JTextField("0.1", 10); // Установить максимальный размер равный предпочтительному, чтобы // предотвратить увеличение размера поля ввода
	textFieldStep.setMaximumSize(textFieldStep.getPreferredSize()); // Создать контейнер 1 типа "коробка с горизонтальной укладкой" 
	Box hboxRange = Box.createHorizontalBox(); // Задать для контейнера тип рамки "объ?мная" 
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
	// Создать кнопку "Вычислить" 
	JButton buttonCalc = new JButton("Вычислить"); 
	// Задать действие на нажатие "Вычислить" и привязать к кнопке 
	buttonCalc.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent ev){
		try { // Считать значения начала и конца отрезка, шага 
			Double from = Double.parseDouble(textFieldFrom.getText()); 
			Double to = Double.parseDouble(textFieldTo.getText()); 
			Double step = Double.parseDouble(textFieldStep.getText()); 
			// На основе считанных данных создать новый экземпляр модели таблицы 
			data = new GornerTableModel(from, to, step, Main.this.coefficients); 
			// Создать новый экземпляр таблицы 
			JTable table = new JTable(data); 
			// Установить в качестве визуализатора ячеек для класса Double разработанный визуализатор 
			table.setDefaultRenderer(Double.class, renderer); 
			// Установить размер строки таблицы в 30 пикселов 
			table.setRowHeight(30); 
			// Удалить все вложенные элементы из контейнера hBoxResult 
			hBoxResult.removeAll(); 
			// Добавить в hBoxResult таблицу, "об?рнутую" в панель с полосами прокрутки 
			hBoxResult.add(new JScrollPane(table)); 
			// Обновить область содержания главного окна 
			getContentPane().validate(); // Пометить ряд элементов меню как доступных
			saveToTextMenuItem.setEnabled(true); 
			saveToGraphicsMenuItem.setEnabled(true); 
			searchValueMenuItem.setEnabled(true);
			searchIntervalMenuItem.setEnabled(true);
		}
		catch (NumberFormatException ex) {
			JOptionPane.showMessageDialog(Main.this, "Ошибка в формате записи числа с плавающей точкой", "Ошибочный формат числа", JOptionPane.WARNING_MESSAGE);
		}
	}
	});	
	JButton buttonReset = new JButton("Очистить поля"); 
	// Задать действие на нажатие "Очистить поля" и привязать к кнопке 
	buttonReset.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent ev) { 
			// Установить в полях ввода значения по умолчанию 
			textFieldFrom.setText("0.0"); 
			textFieldTo.setText("1.0"); 
			textFieldStep.setText("0.1");
			hBoxResult.removeAll(); 
			// Добавить в контейнер пустую панель 
			hBoxResult.add(new JPanel()); 
			// Пометить элементы меню как недоступные
			saveToTextMenuItem.setEnabled(false); 
			saveToGraphicsMenuItem.setEnabled(false); 
			searchValueMenuItem.setEnabled(false);
			searchIntervalMenuItem.setEnabled(false);
			// Обновить область содержания главного окна 
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

	// Установить предпочтительный размер области равным удвоенному минимальному, чтобы при // компоновке окна область совсем не сдавили 
	hboxButtons.setPreferredSize(new Dimension(new Double(hboxButtons.getMaximumSize().getWidth()).intValue(), new Double(hboxButtons.getMinimumSize().getHeight()).intValue()*2)); 
	// Разместить контейнер с кнопками в нижней (южной) области граничной компоновки 
	getContentPane().add(hboxButtons, BorderLayout.SOUTH); 
	// Область для вывода результата пока что пустая 
	hBoxResult = Box.createHorizontalBox();
	hBoxResult.add(new JPanel()); // Установить контейнер hBoxResult в главной (центральной) области граничной компоновки 
	
	getContentPane().add(hBoxResult, BorderLayout.CENTER);
}

protected void saveToGraphicsFile(File selectedFile) { 
	try { 
		// Создать новый байтовый поток вывода, направленный в указанный файл 
		DataOutputStream out = new DataOutputStream(new FileOutputStream(selectedFile));
		// Записать в поток вывода попарно значение X в точке, значение многочлена в точке 
		for (int i = 0; i<data.getRowCount(); i++) { 
			out.writeDouble((Double)data.getValueAt(i,0)); 
			out.writeDouble((Double)data.getValueAt(i,1)); 
		} // Закрыть поток вывода 
		out.close(); 
	} 
	catch (Exception e) {
	}
}

protected void saveToTextFile(File selectedFile) { 
	try { 
		// Создать новый символьный поток вывода, направленный в указанный файл 
		PrintStream out = new PrintStream(selectedFile);
	out.println("Результаты табулирования многочлена по схеме Горнера"); 
	out.print("Многочлен: "); 
	for (int i=0; i<coefficients.length; i++) { 
		out.print(coefficients[i] + "*X^" + (coefficients.length-i-1)); 
		if (i!=coefficients.length-1){ 
			out.print(" + ");
		}
	}
		out.println(""); 
		out.println("Интервал от " + data.getFrom() + " до " + data.getTo() + " с шагом " + data.getStep()); 
		out.println("===================================================="); 
		// Записать в поток вывода значения в точках 
		for (int i = 0; i<data.getRowCount(); i++) {
			out.println("Значение в точке " + data.getValueAt(i,0) + " равно " + data.getValueAt(i,1)); }
		// Закрыть поток 
		out.close(); 

	}
	catch (FileNotFoundException e){		
	}
}
	
	
	
	
	public static void main(String[] args) {
		
		if (args.length==0) { 
			System.out.println("Невозможно табулировать многочлен, для которого не задано ни одного коэффициента!"); 
			System.exit(-1); 
		} 
		// Зарезервировать места в массиве коэффициентов столько, сколько аргументов командной строки 
		Double[] coefficients = new Double[args.length];
		
		int i = 0; 
		try { 
			// Перебрать аргументы, пытаясь преобразовать их в Double 
			for (String arg: args) { 
				coefficients[i++] = Double.parseDouble(arg); 
			} 
		} 
		catch (NumberFormatException ex) { 
			// Если преобразование невозможно - сообщить об ошибке и завершиться 
			System.out.println("Ошибка преобразования строки '" + args[i] + "' в число типа Double"); 
			System.exit(-2); 
		}
		// Создать экземпляр главного окна, передав ему коэффициенты 
		Main frame = new Main(coefficients); 
		// Задать действие, выполняемое при закрытии окна 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		frame.setVisible(true);
	}
}