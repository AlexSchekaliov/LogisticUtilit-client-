import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTabbedPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JTable;



public class FunctionFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JComboBox<String> combobox;
	private ResultSet res;
	private Statement st;
	private JButton button;
	private JTable table_1=new JTable();
	private JButton button_1;
	private JTable table=new JTable();
	private JTable table_2;
	private JButton button_2;
	private JButton btnNewButton;
	private static FileWriter fw;
	
	
	
	private void writetoFile(DefaultTableModel dtm,FileWriter fw){
		
		int ColC = dtm.getColumnCount(); //Определяем кол-во столбцов
		int ItemC = dtm.getRowCount();  //и элементов (строк)  
		StringBuilder sb;
		for (int i = 0; i < ItemC; i++) { //проходим все строки
			sb = new StringBuilder();
			for (int j = 0; j < ColC; j++) { //собираем одну строку из множества столбцов
				sb.append(dtm.getValueAt(i, j));
				if (j < ColC - 1) sb.append(','+"  ");
				if (j == ColC - 1) sb.append("\r\n");
			}
			
			try { //Пытаемся писать в файл
				fw.write(sb.toString()); //записывем собранную строку в файл
			}catch (IOException e) {
				e.printStackTrace();
			}
			
			sb = null;
		}
		try {
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public FunctionFrame() {
		
		initialize();
		addWindowListener(new WindowAdapter()	{
			 
			@Override
				public void windowClosing(WindowEvent e) {
						try {
							CheckDataBaseFrame.connect.close();
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}				
			});
		
		try {
			 st =CheckDataBaseFrame.connect.createStatement();
			res=st.executeQuery("Select r.Название from dbo.Рыбы r");
			while(res.next()){
				combobox.addItem(res.getString(1));
			}
		} catch (SQLException e) {
		
			e.printStackTrace();
		}finally{
			
				try {
					res.close();
					st.close();
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
		
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (CheckDataBaseFrame.connect!=null){
					
					try {
						CheckDataBaseFrame.connect.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				setVisible(false);
				new CheckDataBaseFrame().frame.setVisible(true);
				
			}
		});
		button_1.addActionListener(new ActionListener() {
			private CallableStatement callableStatement;
		
			
			public void actionPerformed(ActionEvent arg0) {
				try{
				callableStatement = CheckDataBaseFrame.connect.prepareCall(" { call InfoSostav(?) } ");
				callableStatement.setInt(1,Integer.parseInt(textField.getText()));
			ResultSet result=callableStatement.executeQuery();
			 // Получаю данные из БД
            Vector<Vector<String>> values = getDataFromDB(result);
            
            // "Шапка" - т.е. имена полей
            Vector<String> header = new Vector<String>();
            header.add("ФИО");
            header.add("Адрес проживания");
            header.add("Дата Рождения");
            header.add("Должность"); 
            // Помещаю в модель таблицы данные
            DefaultTableModel dtm_1 = (DefaultTableModel)table.getModel();
            // Сначала данные, потом шапка
            dtm_1.setDataVector(values, header);
            
			try {
				fw=new FileWriter("data.txt",true);
				fw.write("База данных: "+ CheckDataBaseFrame.combobox.getSelectedItem()+"\r\n");
				fw.write("Операция: "+button_1.getText()+"\r\n");
				fw.write("Аргумент операции: ID состава:"+ textField.getText()+"\r\n"+"\r\n");
				fw.write("ФИО"+"        "+"Адрес проживания"+"            "+"Дата Рождения"+"        "+"Должность"+"\r\n"+"\r\n");
				writetoFile(dtm_1,fw);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			    

				}catch(SQLException exc){
				    System.out.println("Что-то пошло не так");	
				 }
				}


			private Vector<Vector<String>> getDataFromDB(ResultSet result) {
				Vector<Vector<String>> resultVector = new Vector<Vector<String>>();
				String p1, p2, p3,p4;
		        
		        try {
					while(result.next())
					{
					    // Создаем новый список <ФИО, Адрес проживания,Дата рождения, Должность,>
					    Vector<String> element = new Vector<String>();
 
					    // Первой колонкой у нас объявлен ФИО
					    
						p1 = result.getString(2);
					    // Второй - Адрес Проживания
					    p2 = result.getString(3);
					 // Третий - Дата Рождения
					    p3 = result.getString(4);
					 // Должность - Дата Рождения   
					    p4 = result.getString(5);
					    // Добавляем по порядку
					    element.add(p1);
					    element.add(p2);
					    element.add(p3);
					    element.add(p4);
					    
					    // Присоединяем список к результату
					    resultVector.add(element);
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		        // Освобождаем все ресурсы:
		        try {
					result.close();
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return resultVector;
			}     		
						
		});
		button.addActionListener(new ActionListener() {
			private CallableStatement callableStatement;
		
			
			public void actionPerformed(ActionEvent arg0) {
				try{
				callableStatement = CheckDataBaseFrame.connect.prepareCall(" { call FindInfofromBank(?) } ");
				callableStatement.setString(1, (String)combobox.getSelectedItem());
				res=callableStatement.executeQuery();
				
				
				ResultSetMetaData resmeta=res.getMetaData();
				int column= resmeta.getColumnCount();
				Vector<String> column_name=new Vector<String>();
				Vector<String> data_rows=new Vector<String>();
				DefaultTableModel dtm= new DefaultTableModel();
				for(int i=1; i<=column; i++){
					column_name.addElement(resmeta.getColumnName(i));
					dtm.addColumn(resmeta.getColumnName(i));
				}
				
				
				
				while(res.next()){
					
					for(int j=1; j<=column; j++){
						data_rows.addElement(res.getString(j));
						
					}
					dtm.addRow(data_rows);
				}
				table_1.setModel(dtm);
				try {
					fw=new FileWriter("data.txt",true);
					fw.write("База данных: "+ CheckDataBaseFrame.combobox.getSelectedItem()+"\r\n");
					fw.write("Операция: "+button.getText()+"\r\n");
					fw.write("Аргумент операции: сорт рыбы: "+ combobox.getSelectedItem()+"\r\n"+"\r\n");
					fw.write("Название"+"        "+"Долгота"+"            "+"Широта"+"\r\n"+"\r\n");
					writetoFile(dtm,fw);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				}catch(SQLException exc){
					exc.getLocalizedMessage();
					}finally{
					try {
						callableStatement.close();
						res.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					
				}     		
			}		
		});
		button_2.addActionListener(new ActionListener() {
			private CallableStatement callableStatement;
		
			
			public void actionPerformed(ActionEvent arg0) {
				try{
				callableStatement = CheckDataBaseFrame.connect.prepareCall(" { call StatisticUlov } ");
				
			ResultSet result=callableStatement.executeQuery();
			 // Получаю данные из БД
            Vector<Vector<String>> values = getDataFromDB(result);
            
            // "Шапка" - т.е. имена полей
            Vector<String> header = new Vector<String>();
            header.add("ФИО");
            header.add("Должность");
            header.add("Название катера");
            header.add("Название банки");
            header.add("Вес улова");
            header.add("Название сорта");
            
            
            // Помещаю в модель таблицы данные
            DefaultTableModel dtm_2 = (DefaultTableModel)table_2.getModel();
            // Сначала данные, потом шапка
            dtm_2.setDataVector(values, header);
            try {
				fw=new FileWriter("data.txt",true);
				fw.write("База данных: "+ CheckDataBaseFrame.combobox.getSelectedItem()+"\r\n");
				fw.write("Операция: "+button_2.getText()+"\r\n");
				fw.write("Аргумент операции: null"+"\r\n");
				fw.write("ФИО"+"    "+"Должность"+"      "+"Название катера"+"      "+ "Название банки"+"     "+"Вес улова"+"      "+    "Название сорта"+"\r\n"+"\r\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			writetoFile(dtm_2,fw);

				}catch(SQLException exc){
				    System.out.println("Что-то пошло не так");	
				 }
				}


			private Vector<Vector<String>> getDataFromDB(ResultSet result) {
				Vector<Vector<String>> resultVector = new Vector<Vector<String>>();
				String p1, p2, p3,p4,p5,p6;
		        
		        try {
					while(result.next())
					{
					    // Создаем новый список <ФИО, Адрес проживания,Дата рождения, Должность,>
					    Vector<String> element = new Vector<String>();
 
					    // Первой колонкой у нас объявлен ФИО
					    
						p1 = result.getString(1);
					    // Второй - Должность
					    p2 = result.getString(2);
					 // Третий - Название катера
					    p3 = result.getString(3);
					 // Четвёртый - Название банки   
					    p4 = result.getString(4);
					 // Пятый - Вес улова
					    p5 = result.getString(5);
					 // Шестой - Название сорта   
					    p6 = result.getString(6);
					    // Добавляем по порядку
					    element.add(p1);
					    element.add(p2);
					    element.add(p3);
					    element.add(p4);
					    element.add(p5);
					    element.add(p6);
					    
					    // Присоединяем список к результату
					    resultVector.add(element);
					}
				} catch (SQLException e1) {
					
					e1.printStackTrace();
				}
		        // Освобождаем все ресурсы:
		        try {
					result.close();
					callableStatement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return resultVector;
			}     		
						
		});
		
		
	}
	
	

	private void initialize() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(800, 600);
		setLocationRelativeTo(null);
		getContentPane().setLayout(null);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 45, 764, 506);
		getContentPane().add(tabbedPane);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("<html><center>\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0441\u043E\u0441\u0442\u0430\u0432</center></html>", null, panel_1, null);
		panel_1.setLayout(null);
		
		JLabel lblId = new JLabel("<html>\u0423\u043A\u0430\u0436\u0438\u0442\u0435 ID \u0432\u0430\u0448\u0435\u0433\u043E \u0441\u043E\u0441\u0442\u0430\u0432\u0430 \u0438 \u0432\u0430\u043C \u0431\u0443\u0434\u0435\u0442 \u043F\u0440\u0435\u0434\u043E\u0441\u0442\u0430\u0432\u043B\u0435\u043D<br> \u0441\u043F\u0438\u0441\u043E\u043A \u0447\u0435\u043B\u043E\u0432\u0435\u043A \u0432 \u0432\u0430\u0448\u0435\u043C \u0441\u043E\u0441\u0442\u0430\u0432\u0435</html>");
		lblId.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblId.setBounds(10, 37, 461, 47);
		panel_1.add(lblId);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		table.setRowSelectionAllowed(false);
		table.setBounds(10, 139, 739, 47);
		panel_1.add(table);
		
		textField = new JTextField();
		textField.setBounds(512, 64, 86, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		button_1 = new JButton("<html><center>\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0441\u043E\u0441\u0442\u0430\u0432</center></html>");
		button_1.setVerticalAlignment(SwingConstants.TOP);
		button_1.setHorizontalAlignment(SwingConstants.LEFT);
		button_1.setBounds(663, 430, 86, 37);
		panel_1.add(button_1);
		
		JLabel label_4 = new JLabel("\u0424\u0438\u043E");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(70, 114, 102, 14);
		panel_1.add(label_4);
		
		JLabel label_5 = new JLabel("\u0410\u0434\u0440\u0435\u0441 \u043F\u0440\u043E\u0436\u0438\u0432\u0430\u043D\u0438\u044F");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(265, 114, 132, 14);
		panel_1.add(label_5);
		
		JLabel label_6 = new JLabel("\u0414\u0430\u0442\u0430 \u0440\u043E\u0436\u0434\u0435\u043D\u0438\u044F");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(458, 114, 117, 14);
		panel_1.add(label_6);
		
		JLabel label_7 = new JLabel("\u0414\u043E\u043B\u0436\u043D\u043E\u0441\u0442\u044C");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(625, 114, 86, 14);
		panel_1.add(label_7);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0430 \u0443\u043B\u043E\u0432\u0430", null, panel_2, null);
		panel_2.setLayout(null);
		
		 button_2 = new JButton("\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C \u0441\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0443");
		button_2.setBounds(578, 349, 156, 23);
		panel_2.add(button_2);
		
		JLabel label_8 = new JLabel("<html>\u0421\u0442\u0430\u0442\u0438\u0441\u0442\u0438\u043A\u0430 \u043F\u043E \u0443\u043B\u043E\u0432\u0430\u043C \u0434\u0440\u0443\u0433\u0438\u0445 \u043A\u043E\u043C\u0430\u043D\u0434<br> \u0431\u0443\u0434\u0435\u0442 \u043E\u0442\u043E\u0431\u0440\u0430\u0436\u0435\u043D\u0430 \u0432 \u0442\u0430\u0431\u043B\u0438\u0446\u0435</html>");
		label_8.setFont(new Font("Tahoma", Font.BOLD, 13));
		label_8.setBounds(10, 45, 366, 39);
		panel_2.add(label_8);
		
		table_2 = new JTable();
		table_2.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
			}
		));
		table_2.setBounds(10, 120, 739, 80);
		panel_2.add(table_2);
		
		JLabel label_9 = new JLabel("\u0424\u0438\u043E");
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(10, 95, 46, 14);
		panel_2.add(label_9);
		
		JLabel label_10 = new JLabel("\u0414\u043E\u043B\u0436\u043D\u043E\u0441\u0442\u044C");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setBounds(138, 95, 73, 14);
		panel_2.add(label_10);
		
		JLabel label_11 = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u043A\u0430\u0442\u0435\u0440\u0430");
		label_11.setHorizontalAlignment(SwingConstants.CENTER);
		label_11.setBounds(249, 95, 120, 14);
		panel_2.add(label_11);
		
		JLabel label_12 = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0431\u0430\u043D\u043A\u0438");
		label_12.setHorizontalAlignment(SwingConstants.CENTER);
		label_12.setBounds(379, 95, 94, 14);
		panel_2.add(label_12);
		
		JLabel label_13 = new JLabel("\u0423\u043B\u043E\u0432");
		label_13.setHorizontalAlignment(SwingConstants.CENTER);
		label_13.setBounds(522, 95, 46, 14);
		panel_2.add(label_13);
		
		JLabel label_14 = new JLabel("\u0421\u043E\u0440\u0442");
		label_14.setHorizontalAlignment(SwingConstants.CENTER);
		label_14.setBounds(688, 95, 46, 14);
		panel_2.add(label_14);
		
		JPanel panel = new JPanel();
		tabbedPane.addTab("\u041C\u0435\u0441\u0442\u0430 \u043B\u043E\u0432\u043B\u0438 \u0440\u044B\u0431\u044B", null, panel, null);
		panel.setLayout(null);
		
		combobox = new JComboBox<String>();
		combobox.setBounds(653, 46, 76, 20);
		panel.add(combobox);
		
		JLabel label = new JLabel("<html>\u0412\u044B\u0431\u0435\u0440\u0435\u0442\u0435 \u0438\u0437 \u0441\u043F\u0438\u0441\u043A\u0430 \u043D\u0430\u0437\u0432\u0430\u043D\u0438\u0435 \u0438\u043D\u0442\u0435\u0440\u0435\u0441\u0443\u044E\u0449\u0443\u044E \u0432\u0430\u0441 <br>\r\n\u0440\u044B\u0431\u044B \u0438 \u044F \u043F\u043E\u043A\u0430\u0436\u0443,\u0433\u0434\u0435 \u0435\u0451 \u043C\u043E\u0436\u043D\u043E \u043F\u043E\u0439\u043C\u0430\u0442\u044C.</html>");
		label.setFont(new Font("Tahoma", Font.BOLD, 13));
		label.setBounds(10, 25, 385, 44);
		panel.add(label);
		
		button = new JButton("<html><center>\u041F\u043E\u043A\u0430\u0437\u0430\u0442\u044C <br>\u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044E</center></html>");
		button.setFont(new Font("Tahoma", Font.ITALIC, 11));
		button.setBounds(621, 416, 108, 30);
		panel.add(button);
		
		table_1.setBounds(129, 104, 381, 48);
		panel.add(table_1);
		
		JLabel label_1 = new JLabel("\u041D\u0430\u0437\u0432\u0430\u043D\u0438\u0435");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(129, 80, 108, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("\u0414\u043E\u043B\u0433\u043E\u0442\u0430");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(247, 80, 102, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("\u0428\u0438\u0440\u043E\u0442\u0430");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(359, 79, 108, 14);
		panel.add(label_3);
		
		
		JLabel lblNewLabel = new JLabel("\u0412\u044B \u0432\u043E\u0448\u043B\u0438 \u043A\u0430\u043A:");
		lblNewLabel.setForeground(Color.BLUE);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel.setBounds(272, 20, 137, 26);
		getContentPane().add(lblNewLabel);
		JLabel lblNewLabel_1 = new JLabel(MainFrame.name);
		lblNewLabel_1.setBounds(400, 20, 333, 26);
		getContentPane().add(lblNewLabel_1);
		
		btnNewButton = new JButton("\u0412\u044B\u0431\u0440\u0430\u0442\u044C \u0434\u0440\u0443\u0433\u0443\u044E \u0431\u0430\u0437\u0443");
		btnNewButton.setBounds(10, 8, 169, 26);
		getContentPane().add(btnNewButton);
		setResizable(false);
		setVisible(true);
	}
}