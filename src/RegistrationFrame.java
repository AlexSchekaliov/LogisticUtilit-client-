import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;


public class RegistrationFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private  JFrame jframe;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	private Calendar c=Calendar.getInstance();
	private final String ClientInfoError="Ввод только русских букв. Ограничение на ввод:2-20 символов";
	private LinkedHashMap<JTextField, JLabel> map =new LinkedHashMap<JTextField, JLabel>();
	
	
	public RegistrationFrame() {
		initialize();
    btnNewButton.addActionListener(new ActionListener() {
			
			private CallableStatement callableStatement;
			private PreparedStatement preparedSt;
			private ResultSet res;

			@Override
			public void actionPerformed(ActionEvent e) {
				clearAllJlabel();
				map.put(textField, label_5);
				map.put(textField_1, label_6);
				map.put(textField_2, label_7);
				map.put(textField_3, label_8);
				map.put(textField_4, label_9);
				map.put(textField_5, label_10);
				map.put(textField_6, label_11);
				
				
				
				for (Entry<JTextField, JLabel> entry : map.entrySet()) {
					if(!methodConvertString((JTextField)entry.getKey())){
						methodConvertJLabel((JLabel)entry.getValue());
						return;
					}
				}
				
				  if(!validateDataClient(textField.getText())){
					  label_5.setText(ClientInfoError);
					  return;
				  }
				  if(!validateDataClient(textField_1.getText())){
					  label_6.setText(ClientInfoError);
					  return;
				  }
				  
				  if(!validateDataClient(textField_2.getText())){
					  label_7.setText(ClientInfoError);
					  return;
				  }
				  if(!validateDataOfBirth(textField_3.getText())){
					  label_8.setText("Дата введена некорректно");
					  return;
				  }
				  
				  if(!checkAge(textField_3.getText())){
					  label_8.setText("Мы не принимаем лица,возраст которых более 60 или менее 20");
					  return;
				  }
				  if(!validateAddress(textField_4.getText())){
						 label_9.setText("Некорректный ввод адреса!!");  
					  }
				  if(!validateLogin(textField_5.getText())){
					  label_10.setText("Ограничение 2-20 символов. Первый символ-буква, остальная часть-буквы и/или цыфры");
				     return;
				  }
				  
				  if(!validatePassword(textField_6.getText())){
					  label_11.setText("Пароль должен состоять из строчных и прописных латинских букв, цифр");
					  return;
				  }
				  
				try {
					preparedSt=CheckDataBaseFrame.connect.prepareStatement("SELECT [Id Моряка] FROM dbo.Моряк WHERE [Логин]=?");
					preparedSt.setString(1, textField_5.getText().trim());
					System.out.println(textField_5.getText().trim());
					res = preparedSt.executeQuery();
					if(res.next()){
						JOptionPane.showMessageDialog(jframe, "Пользователь с таким логином уже существует."+'\n'+"Пожалуйста,придумайте другой логин", 
		            			"Предупреждение", JOptionPane.WARNING_MESSAGE);
						return;
					}
					callableStatement=CheckDataBaseFrame.connect.prepareCall(" { call registration(?,?,?,?,?) } ");
					callableStatement.setString(1, formatClientData(textField.getText())+" "+formatClientData(textField_1.getText())+" "+formatClientData(textField_2.getText()));
		            callableStatement.setString(2, textField_3.getText().trim());
		            callableStatement.setString(3, textField_4.getText().trim());
		            callableStatement.setString(4, textField_5.getText().trim());
		            callableStatement.setString(5, textField_6.getText().trim());
		            callableStatement.executeUpdate();    	
				} catch (SQLException e1) {
					e1.printStackTrace();
					
				}finally{
					try {
						if(res!=null){
							res.close();
						}
						if(preparedSt!=null){
						preparedSt.close();
						}
						if(callableStatement!=null){
						callableStatement.close();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					
				}
				JOptionPane.showMessageDialog(jframe, "Кликните по кнопке 'Вернуться в окно входа' для авторизации", "Успешная регистрация", JOptionPane.INFORMATION_MESSAGE);
		  }
		
					});
		
        btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new MainFrame().getFrame().setVisible(true);
				jframe.setVisible(false);
			}
		});

	}
	private String formatClientData(String data){
		
		return data.substring(0,1).toUpperCase()+data.substring(1).toLowerCase().trim();
	}
	private boolean validatePassword(String text) {
		Pattern p = Pattern.compile("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?!.*\\s).*$");  
        Matcher m = p.matcher(text);
		return m.matches();
	}

	private boolean validateLogin(String text) {
		Pattern p = Pattern.compile("^[a-zA-Z][a-zA-Z0-9-_\\.]{1,20}$");  
        Matcher m = p.matcher(text);
		return m.matches();
	}

	private boolean validateAddress(String text) {
		Pattern p = Pattern.compile("^[а-яА-Я0-9., ]+$");  
        Matcher m = p.matcher(text);
		return m.matches();
	}

	private void methodConvertJLabel(JLabel label){
		label.setText("Поле обязательно для заполнения!!");
	}
	
	private boolean checkAge(String string){
		if((c.get(Calendar.YEAR)-Integer.parseInt(string.split("/")[2]))>60||(c.get(Calendar.YEAR)-Integer.parseInt(string.split("/")[2]))<20){
			return false;
		}
		
		return true;
	}
	
	private boolean methodConvertString(JTextField textfield){
		if(textfield.getText().equals("")){
		return false;
		}
		return true;
	}
	
	
	private boolean validateDataOfBirth(String dataofBirth){
		Pattern p = Pattern.compile("(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[012])/((19|20)\\d\\d)");  
        Matcher m = p.matcher(dataofBirth);
        if(!m.matches()){
        return false;
        }
        if(Integer.parseInt(dataofBirth.split("/")[2])>c.get(Calendar.YEAR)){
        	return false;
        }
        return true;
	}
	
	private void clearAllJlabel(){
		
		for (JLabel value : map.values()) {
		    if(!value.getText().equals("")){
		    	value.setText("");
		    }
		}
		map.clear();
	}

	private boolean validateDataClient(String text) {
		Pattern p = Pattern.compile("^[а-яА-Я]{5,20}+$");  
        Matcher m = p.matcher(text); 
        return m.matches();
	}

	private void initialize(){
		jframe= new JFrame("Окно регистрации");
		jframe.setAutoRequestFocus(true);
		jframe.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Dell\\workspace\\LogisticUtilit\\fish_2.jpg"));
		jframe.getContentPane().setLayout(null);
		textField = new JTextField();
		textField.setBounds(35, 47, 225, 20);
		jframe.getContentPane().add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("\u0412\u0430\u0448\u0430 \u0424\u0430\u043C\u0438\u043B\u0438\u044F");
		lblNewLabel.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblNewLabel.setBounds(35, 22, 145, 14);
		jframe.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("\u0412\u0430\u0448\u0435 \u0418\u043C\u044F");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		lblNewLabel_1.setBounds(35, 85, 107, 14);
		jframe.getContentPane().add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(35, 110, 225, 20);
		jframe.getContentPane().add(textField_1);
		
		JLabel label = new JLabel("\u0412\u0430\u0448\u0430 \u041E\u0442\u0447\u0435\u0441\u0442\u0432\u043E");
		label.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label.setBounds(35, 141, 107, 14);
		jframe.getContentPane().add(label);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(35, 166, 225, 20);
		jframe.getContentPane().add(textField_2);
		
		JLabel label_1 = new JLabel("\u0414\u0430\u0442\u0430 \u0420\u043E\u0436\u0434\u0435\u043D\u0438\u044F (\u0424\u043E\u0440\u043C\u0430\u0442 \u0432\u0432\u043E\u0434\u0430: \u0434\u0434/\u043C\u043C/\u0433\u0433\u0433\u0433)");
		label_1.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_1.setBounds(35, 197, 282, 14);
		jframe.getContentPane().add(label_1);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(35, 222, 225, 20);
		jframe.getContentPane().add(textField_3);
		
		JLabel label_2 = new JLabel("\u0410\u0434\u0440\u0435\u0441 \u041F\u0440\u043E\u0436\u0438\u0432\u0430\u043D\u0438\u044F");
		label_2.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_2.setBounds(35, 253, 145, 14);
		jframe.getContentPane().add(label_2);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(35, 278, 225, 20);
		jframe.getContentPane().add(textField_4);
		
		JLabel label_3 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043B\u043E\u0433\u0438\u043D");
		label_3.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_3.setBounds(35, 309, 145, 14);
		jframe.getContentPane().add(label_3);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(35, 334, 225, 20);
		jframe.getContentPane().add(textField_5);
		
		JLabel label_4 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u0430\u0440\u043E\u043B\u044C");
		label_4.setFont(new Font("Times New Roman", Font.ITALIC, 14));
		label_4.setBounds(35, 365, 145, 14);
		jframe.getContentPane().add(label_4);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(35, 390, 225, 20);
		jframe.getContentPane().add(textField_6);
		
		btnNewButton = new JButton("\u0417\u0430\u0440\u0435\u0433\u0438\u0441\u0442\u0440\u0438\u0440\u043E\u0432\u0430\u0442\u044C\u0441\u044F");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton.setBounds(289, 483, 155, 23);
		jframe.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("\u0412\u0435\u0440\u043D\u0443\u0442\u044C\u0441\u044F \u0432 \u043E\u043A\u043D\u043E \u0432\u0445\u043E\u0434\u0430");
		btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnNewButton_1.setBounds(475, 483, 168, 23);
		jframe.getContentPane().add(btnNewButton_1);
		
		label_5 = new JLabel("");
		label_5.setForeground(Color.RED);
		label_5.setBounds(270, 50, 404, 14);
		jframe.getContentPane().add(label_5);
		
		label_6 = new JLabel("");
		label_6.setForeground(Color.RED);
		label_6.setBounds(270, 110, 404, 14);
		jframe.getContentPane().add(label_6);
		
		label_7 = new JLabel("");
		label_7.setForeground(Color.RED);
		label_7.setBounds(270, 169, 404, 14);
		jframe.getContentPane().add(label_7);
		
		label_8 = new JLabel("");
		label_8.setForeground(Color.RED);
		label_8.setBounds(270, 222, 404, 14);
		jframe.getContentPane().add(label_8);
		
		label_9 = new JLabel("");
		label_9.setForeground(Color.RED);
		label_9.setBounds(270, 281, 404, 14);
		jframe.getContentPane().add(label_9);
		
		label_10 = new JLabel("");
		label_10.setFont(new Font("Tahoma", Font.BOLD, 10));
		label_10.setForeground(Color.RED);
		label_10.setBounds(270, 337, 504, 17);
		jframe.getContentPane().add(label_10);
		
		label_11 = new JLabel("");
		label_11.setForeground(Color.RED);
		label_11.setBounds(270, 396, 504, 14);
		jframe.getContentPane().add(label_11);
		jframe.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jframe.setSize(800, 600);
		jframe.setLocationRelativeTo(null);
		jframe.setResizable(false);
		jframe.setVisible(true);
		
	}
}