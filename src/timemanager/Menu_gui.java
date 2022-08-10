package timemanager;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.awt.event.ActionEvent;

public class Menu_gui extends JFrame {

	private JPanel contentPane;
	static Menu_gui frame;
	
	static Connection sql_connection_id = Sqlmanager.connect_withuserandpassword();
	public static String working_days[]= {"MONDAY","TUESDAY","WEDNESDAY","THURSDAY","FRIDAY"};
	static LocalDate l = LocalDate.now();
	static Calendar c = Calendar.getInstance();
	static DateTimeFormatter d = DateTimeFormatter.ofPattern("dd-MM-yyyy");
	static String todate = d.format(l);
	static DayOfWeek dayOfWeek = DayOfWeek.from(LocalDate.now());
	static String today = dayOfWeek.toString();
	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					if(frame==null)
					frame = new Menu_gui();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	static boolean ANotInB(String a,String[]b) {
		for(String i:b) {
			if(a.equals(i))
				return true;
		}
		return false;
	}

	/**
	 * Create the frame.
	 */
	public Menu_gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("STUDENT DASHBOARD");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(111, 21, 194, 48);
		contentPane.add(lblNewLabel);
		
		ButtonGroup bg=new ButtonGroup();   
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Timetable");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton.setBounds(152, 99, 129, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Activities");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton_1.setBounds(152, 125, 129, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Subjects");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		rdbtnNewRadioButton_2.setBounds(152, 151, 129, 23);
		contentPane.add(rdbtnNewRadioButton_2);
		
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1);
		bg.add(rdbtnNewRadioButton_2); 
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(152, 219, 142, 31);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Next >");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					frame.setVisible(false);
					try {
						timetable_final.main(todate,today,ANotInB(today, working_days));
						//Timetable_new.main(todate, today, ANotInB(today, working_days));
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
				else if(rdbtnNewRadioButton_1.isSelected()) {
					frame.setVisible(false);
					Delete_content_gui.main();
					
				}
				else if(rdbtnNewRadioButton_2.isSelected()) {
					frame.setVisible(false);
					Subjects.main(null);
				}
				else {
					lblNewLabel_1.setText("select a proper option");
				}
			}
		});
		btnNewButton.setBounds(312, 212, 89, 23);
		contentPane.add(btnNewButton);
		
		JLabel lblNewLabel_3 = new JLabel(todate);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_3.setBounds(353, 36, 81, 23);
		contentPane.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel(today);
		lblNewLabel_2.setBounds(353, 22, 81, 14);
		contentPane.add(lblNewLabel_2);
		
		JButton btnNewButton_1 = new JButton("Logout");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				Login.main(null);
			}
		});
		btnNewButton_1.setBounds(45, 212, 89, 23);
		contentPane.add(btnNewButton_1);
		
		ArrayList<ArrayList> a = Sqlmanager.sqlselect("select name from students where usn = '"+Login.USN+"'");
		
	}
}
