import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;

import javax.swing.JComboBox;
import javax.swing.JButton;


public class CheckDataBaseFrame {

	public  JFrame frame=new JFrame();
	public static JComboBox<String> combobox;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private static SqlConnect sql=new SqlConnect();
	public static Connection connect;

	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckDataBaseFrame window=new CheckDataBaseFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * @wbp.parser.entryPoint
	 */
	public CheckDataBaseFrame() {
		initialize();
		combobox.addItem("FishAppDB");
		combobox.addItem("FishAppDB_1");
		btnNewButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if((connect=sql.isConnect((String)combobox.getSelectedItem()))==null){
					JOptionPane.showMessageDialog(frame, "Ошибка подключения к базе", "Ошибка", JOptionPane.ERROR_MESSAGE);
					frame.setVisible(false);
					System.exit(0);
				}else{
					frame.setVisible(false);
					new MainFrame().getFrame().setVisible(true);
				}
				
			}
		});
		
		btnNewButton_1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				frame.setVisible(false);
				
			}
		});
		
	}

		private void initialize() {
		frame.setBounds(100, 100, 450, 212);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("<html>\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0438\u0437 \u0441\u043F\u0438\u0441\u043A\u0430<br> \u0431\u0430\u0437\u0443 \u0434\u043B\u044F \u043F\u043E\u0434\u043A\u043B\u044E\u0447\u0435\u043D\u0438\u044F</html>");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblNewLabel.setBounds(25, 32, 222, 40);
		frame.getContentPane().add(lblNewLabel);
		
		combobox = new JComboBox<String>();
		combobox.setBounds(315, 35, 93, 20);
		frame.getContentPane().add(combobox);
		
		btnNewButton = new JButton("\u041F\u043E\u0434\u043A\u043B\u044E\u0447\u0438\u0442\u044C\u0441\u044F");
		btnNewButton.setBounds(161, 140, 135, 23);
		frame.getContentPane().add(btnNewButton);
		
		btnNewButton_1 = new JButton("\u0412\u044B\u0439\u0442\u0438");
		btnNewButton_1.setBounds(319, 140, 89, 23);
		frame.getContentPane().add(btnNewButton_1);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
	}
}