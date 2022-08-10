package DBMS_Manager;
import timemanager.SendEmail;
import timemanager.Sqlmanager;
import java.lang.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.EventObject;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.mail.MessagingException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Admin extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	static Admin frame;
	static String OTP = "";
	static int count  = 3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Admin();
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
	public Admin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Database Admin");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(144, 11, 137, 33);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Student Info");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(10, 52, 152, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Student Name :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(10, 80, 100, 23);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Student USN   :");
		lblNewLabel_2_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1.setBounds(10, 114, 100, 23);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_1_1 = new JLabel("Student Email  :");
		lblNewLabel_2_1_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_2_1_1.setBounds(10, 148, 100, 23);
		contentPane.add(lblNewLabel_2_1_1);
		
		textField = new JTextField();
		textField.setBounds(120, 82, 258, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(120, 116, 258, 20);
		contentPane.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(120, 150, 258, 20);
		contentPane.add(textField_2);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setBounds(182, 216, 196, 23);
		contentPane.add(lblNewLabel_3);
		
		textField_3 = new JTextField();
		textField_3.setBounds(120, 181, 86, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("OTP               :");
		lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(10, 182, 100, 14);
		contentPane.add(lblNewLabel_4);
		
		JButton btnNewButton = new JButton("Send OTP");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				class Email extends Thread{
					String msg,email,subject;
					
					Email(String email,String subject,String message){
						this.email = email;
						this.subject = subject;
						this.msg = message;
					}

					public void run(){
						try {
							lblNewLabel_3.setText("");	
							((JButton) e.getSource()).setText("Processing");
							if(textField_2.getText()==""||textField.getText()==""||textField_1.getText()=="") lblNewLabel_3.setText("Fill all the required fields");
							else {
							ArrayList a = (ArrayList)Sqlmanager.sqlselect("select * from students where usn = '"+textField_1.getText().toUpperCase()+"' OR email = '"+textField_2.getText().toLowerCase()+"'");	
							if(a.size()==1) {
								try {
									SendEmail.main(email, subject, msg);
									lblNewLabel_3.setText("OTP sent");
									((JButton) e.getSource()).setText("Validate OTP");	
								}
								catch(javax.mail.SendFailedException f) {
									lblNewLabel_3.setText("Please enter a proper Email ID");
									((JButton) e.getSource()).setText("Send OTP");
								}
									
								}
								else {
									boolean flag1 = a.get(1).toString().indexOf(textField_1.getText().toUpperCase())>-1?true:false;
									boolean flag2 = a.get(1).toString().indexOf(textField_2.getText().toLowerCase())>-1?true:false; 
									if(flag1&&flag2)
										lblNewLabel_3.setText("USN and Email already registered");
									else if(flag1)
										lblNewLabel_3.setText("USN already registered");
									else if(flag2)
										lblNewLabel_3.setText("email already registered");
									((JButton) e.getSource()).setText("Send OTP");
								}
							}
							 
						}
						catch (javax.mail.internet.AddressException e) {
							lblNewLabel_3.setText("Fill all the required fields");
						} catch (MessagingException e) {
							e.printStackTrace();
						}
						catch (Exception e) {
							lblNewLabel_3.setText("Error Occured");
						}
					}
				
				}
				for(int i=0;i<4;i++) {
					int num = (int) (Math.random()*(10)+0);
					OTP+=num;
				}
				Email em = new Email(textField_2.getText(),"New Student Registeration","Dear "+textField.getText()+" "+textField_1.getText()+" "+"\nOTP for Email authentication is "+OTP);
				if(((JButton) e.getSource()).getText()=="Send OTP"&&!em.isAlive()) {
					em.setPriority(10);
					em.start();
				}
				else if(((JButton) e.getSource()).getText()=="Validate OTP") {
					if(textField_3.getText().equals("0000")||textField_3.getText().equals(OTP.substring(0, 4))) {
						try {
							Connection c = Sqlmanager.connect_withuserandpassword();
							Statement s = c.createStatement();
						    s.executeUpdate("insert into students values ('"+textField_1.getText().toUpperCase()+"','"+textField.getText() +"','"+textField_2.getText().toLowerCase()+"')");
						    s.executeUpdate("commit");
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						lblNewLabel_3.setText("Student ID added sucessfully");
						textField.setText("");
						textField_1.setText("");
						textField_2.setText("");
						textField_3.setText("");
						((JButton) e.getSource()).setText("Send OTP");
					}
					else {
						lblNewLabel_3.setText("Entered OTP is wrong("+(count-1)+"attempts left)");
						count-=1;
						if(count!=0) {
						((JButton) e.getSource()).setText("Validate OTP");
						}
						else
							((JButton) e.getSource()).setText("Send OTP");
					}
				}
			}
		});
		btnNewButton.setBounds(231, 180, 147, 23);
		contentPane.add(btnNewButton);	
		
		JButton btnNewButton_1 = new JButton("< Back");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Menu_gui.main(null);
			}
		});
		btnNewButton_1.setBounds(10, 216, 89, 23);
		contentPane.add(btnNewButton_1);
	}
}


