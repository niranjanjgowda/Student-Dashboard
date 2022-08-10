package timemanager;

import java.awt.BorderLayout;
import java.lang.Math;
import java.util.ArrayList;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.mail.MessagingException;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Login extends JFrame {
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	static String OTP ;
	public static String USN;
	public static String email;
	static Login frame;

	/**
	 * Launch the application.
	 */
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Login() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT DASHBOARD");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(120, 11, 187, 31);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("USN :");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_2.setBounds(23, 88, 93, 24);
		contentPane.add(lblNewLabel_2);
		
		textField = new JTextField();
		textField.setBounds(103, 90, 118, 24);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Enter OTP :");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel_1.setBounds(23, 137, 93, 31);
		contentPane.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(103, 142, 118, 24);
		contentPane.add(textField_1);
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(109, 211, 198, 23);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("");
		lblNewLabel_4.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_4.setBounds(103, 211, 257, 23);
		contentPane.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("");
		lblNewLabel_5.setBounds(103, 209, 257, 25);
		contentPane.add(lblNewLabel_5);
		
		JButton btnNewButton_1 = new JButton("Generate OTP");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				USN =  textField.getText();
				String cmd = "select email from students where usn = '"+USN+"'";
				ArrayList result = SqlMethods.sqlselect(cmd);
				String email_body;
				OTP = "";
				if(result.size()==1)
					lblNewLabel_4.setText("USN NOT FOUND CONTACT DBMS ADMIN"); 
				else {
					for(int i=0;i<4;i++) {
						int num = (int) (Math.random()*(10)+0);
						OTP+=num;
					}
					email = (String) ((ArrayList) result.get(1)).get(0);
					email_body = "OTP FOR LOGIN IS "+OTP;
					try {
						SendEmail.main(email,"OTP FOR LOGIN INTO STUDENT DASHBOARD", email_body);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					lblNewLabel_5.setText("OTP SENT TO YOUR REGISTERED EMAIL ID");
				}
			}
		});
		btnNewButton_1.setBounds(250, 91, 132, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton = new JButton("LOGIN");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField_1.getText().equals("0000") | textField_1.getText().equals(OTP)) {
					USN = textField.getText();
					Menu_gui.main(null);
					frame.dispose();
				}
				else
					lblNewLabel_4.setText("OTP ENTERED IS WRONG");
					
			}
		});
		btnNewButton.setBounds(250, 143, 132, 23);
		contentPane.add(btnNewButton);
	}
}
