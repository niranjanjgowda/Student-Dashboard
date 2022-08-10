package timemanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JComboBox;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.naming.spi.DirStateFactory.Result;
import javax.swing.JButton;

public class insert_activity_gui extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	static insert_activity_gui frame;
	static String[] subjects;
	static String[] subjects_names;
	private JTextField textField_1;
	static Connection sql_connection_id = Sqlmanager.connect_withuserandpassword();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args,String[] argc) {
		subjects=args;
		subjects_names=argc;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new insert_activity_gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public insert_activity_gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Insert activity");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(154, 11, 112, 22);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Date of Submission     :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(10, 46, 142, 22);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Subject                      :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(10, 79, 152, 22);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Subject name             :");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_3.setBounds(10, 112, 142, 22);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(132, 228, 205, 22);
		contentPane.add(lblNewLabel_4);
		
		
		textField = new JTextField();
		textField.setToolTipText("Enter Submission date in format (DD-MM-YYYY)");
		textField.setBounds(164, 47, 220, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		textField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					if(date_check.takeindate(textField.getText())) {
						textField.setText("");
						lblNewLabel_4.setText("Enter a proper Date (DD-MM-YYYY)");
					}
					else
						lblNewLabel_4.setText("");
				
				
			}
		});
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setBounds(164, 115, 220, 24);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JComboBox<Object> comboBox = new JComboBox<Object>(subjects);
		comboBox.setBounds(164, 81, 220, 24);
		contentPane.add(comboBox);
		
		JLabel lblNewLabel_5 = new JLabel("Description                 :");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_5.setBounds(10, 145, 142, 22);
		contentPane.add(lblNewLabel_5);
		
		JTextArea textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBounds(164, 150, 220, 40);
		contentPane.add(textArea);
		
		JButton btnNewButton = new JButton(" < Back");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Delete_content_gui.main();
			}
		});
		btnNewButton.setBounds(10, 227, 112, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(date_check.takeindate(textField.getText())) {
					textField.setText("");
					lblNewLabel_4.setText("Enter a proper Date (DD-MM-YYYY)");
				}
				else {
					lblNewLabel_4.setText("");
					try {
						Statement query_processor = sql_connection_id.createStatement();
						ResultSet r;
						r = query_processor.executeQuery("select count(*) from activity where usn = '"+Login.USN+"'");
						if(r.next())
							query_processor.executeUpdate("insert into activity values('"+textField.getText()+"','"+comboBox.getSelectedItem()+"','"+textArea.getText()+"',"+(r.getInt(1)+1)+",'"+Login.USN+"')");
						query_processor.executeUpdate("commit");
						lblNewLabel_4.setText("Inserted Activity sucsessfully");
						textField.setText("");
						textField_1.setText("");
						textArea.setText("");
						} catch (SQLException e1) {
						// TODO Auto-generated catch block
							e1.printStackTrace();
							}
					}
				}		
			});
		btnNewButton_1.setBounds(340, 201, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_3 = new JButton("Logout");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
				frame.dispose();
			}
		});
		btnNewButton_3.setBounds(340, 227, 89, 23);
		contentPane.add(btnNewButton_3);
		
		JButton btnNewButton_2 = new JButton("< Main Menu");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Menu_gui.main(null);
			}
		});
		btnNewButton_2.setBounds(10, 201, 112, 23);
		contentPane.add(btnNewButton_2);
		
		comboBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				textField_1.setText(subjects_names[comboBox.getSelectedIndex()]+"");
			}
		});
		
		
		
		
	}
}
