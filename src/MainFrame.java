import javax.swing.JFrame;
import javax.swing.JButton;

import java.awt.Color;

import javax.swing.JLabel;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Font;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.SwingConstants;


public class MainFrame extends JFrame {


	private static final long serialVersionUID = 1L;
	private JFrame frame;
	private JTextField textField_1;
	private JTextField textField;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private CallableStatement callableStatement;
	private ResultSet result;
	public static String name;
	private JButton btnNewButton_2;
    

	

	
	public MainFrame() {
		initialize();
		frame.addWindowListener(new WindowAdapter()	{
			
		@Override
			public void windowClosing(WindowEvent e) {
				if(CheckDataBaseFrame.connect!=null){
					try {
						CheckDataBaseFrame.connect.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}				
			}
		});
		
		btnNewButton.addActionListener(new ActionListener() {
			
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				switch(textField_1.getText()){
				
				case "капитан":
					frame.setVisible(false);
					new FunctionFrame();
					break;
				case "матрос":
					frame.setVisible(false);
					new RegistrationFrame();
					break;
					default:
						textField_1.setText("не вышло");
				}
				
				/*if(textField.getText().equals("") ||textField.getText().equals("")){
					JOptionPane.showMessageDialog(frame, "Введите Логин или пароль!!", "Предупреждение", JOptionPane.WARNING_MESSAGE);
					return;
				}
				try {
					callableStatement=CheckDataBaseFrame.connect.prepareCall(" { call autorization(?,?) } ");
					callableStatement.setString(1, textField_1.getText());
					callableStatement.setString(2, textField.getText());
					result = callableStatement.executeQuery();
					result.next();
					name=result.getString(1);
					JOptionPane.showMessageDialog(frame, "Здравствуйте "+name, 
							"Окно приветствия",JOptionPane.NO_OPTION);


				} catch (SQLException e1) {
					JOptionPane.showMessageDialog(frame, "Логин или пароль введены некорректно."+'\n'+"Пожалуйста,повторите попытку или зарегистрируйтесь.", 
							"Предупреждение", JOptionPane.WARNING_MESSAGE);
					cleanTextField();
					return;		
				}finally{
					try {
						callableStatement.close();
						result.close();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}

				}
				frame.setVisible(false);
				new FunctionFrame();
						}
*/		
			}
				});
		
        btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.setVisible(false);
				new RegistrationFrame();
				
				
			}
		});
        
        btnNewButton_2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					CheckDataBaseFrame.connect.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.setVisible(false);
				new CheckDataBaseFrame().frame.setVisible(true);
				
				
				
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Окно входа");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage("C:\\Users\\Dell\\workspace\\LogisticUtilit\\fish_2.jpg"));
		frame.setBounds(100, 100, 624, 440);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setBounds(0, 0, 794, 572);
		frame.getContentPane().add(layeredPane);
		
		btnNewButton = new JButton("\u0410\u0432\u0442\u043E\u0440\u0438\u0437\u0430\u0446\u0438\u044F");
		btnNewButton.setBounds(508, 148, 111, 23);
		layeredPane.add(btnNewButton,new Integer(2));
		
		btnNewButton_1 = new JButton("\u0420\u0435\u0433\u0438\u0441\u0442\u0440\u0430\u0446\u0438\u044F");
		btnNewButton_1.setBounds(645, 148, 110, 23);
		layeredPane.add(btnNewButton_1,new Integer(2));
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\Dell\\workspace\\LogisticUtilit\\fish_2.jpg"));
		lblNewLabel.setBounds(0, 0, 794, 571);
		layeredPane.add(lblNewLabel);
		
		textField_1 = new JTextField();
		textField_1.setBounds(614, 42, 141, 20);
		layeredPane.add(textField_1,new Integer(2));
		textField_1.setColumns(10);
		
		textField = new JTextField();
		textField.setBounds(616, 100, 139, 20);
		layeredPane.add(textField,new Integer(2));
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043B\u043E\u0433\u0438\u043D");
		lblNewLabel_1.setBackground(Color.WHITE);
		lblNewLabel_1.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
		lblNewLabel_1.setForeground(Color.DARK_GRAY);
		lblNewLabel_1.setBounds(614, 17, 141, 14);
		layeredPane.add(lblNewLabel_1, new Integer(2));
		
		JLabel lblNewLabel_2 = new JLabel("\u0412\u0432\u0435\u0434\u0438\u0442\u0435 \u043F\u0430\u0440\u043E\u043B\u044C");
		lblNewLabel_2.setBackground(Color.WHITE);
		lblNewLabel_2.setForeground(Color.BLACK);
		lblNewLabel_2.setHorizontalAlignment(SwingConstants.LEFT);
		lblNewLabel_2.setFont(new Font("Comic Sans MS", Font.ITALIC, 12));
		lblNewLabel_2.setBounds(614, 73, 141, 14);
		layeredPane.add(lblNewLabel_2, new Integer(2));
		
		btnNewButton_2 = new JButton("\u0412\u044B\u0431\u0440\u0430\u0442\u044C \u0434\u0440\u0443\u0433\u0443\u044E \u0431\u0430\u0437\u0443");
		btnNewButton_2.setBounds(555, 214, 170, 23);
		layeredPane.add(btnNewButton_2,new Integer(2));
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	public  JFrame getFrame(){
		
		return frame;
	}
	private void cleanTextField(){
		textField.setText(null);
    	textField_1.setText(null);
	}
}
