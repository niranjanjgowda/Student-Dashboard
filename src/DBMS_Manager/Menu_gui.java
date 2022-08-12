package DBMS_Manager;

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

	public Menu_gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Database Adminstrator");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(120, 11, 187, 31);
		contentPane.add(lblNewLabel);
		
		ButtonGroup bg=new ButtonGroup();   
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Add Student");
		rdbtnNewRadioButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnNewRadioButton.setBounds(148, 96, 129, 23);
		contentPane.add(rdbtnNewRadioButton);
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Show all Students");
		rdbtnNewRadioButton_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnNewRadioButton_1.setBounds(148, 122, 129, 23);
		contentPane.add(rdbtnNewRadioButton_1);
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Timetable");
		rdbtnNewRadioButton_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rdbtnNewRadioButton_2.setBounds(148, 148, 109, 23);
		contentPane.add(rdbtnNewRadioButton_2);
		
		bg.add(rdbtnNewRadioButton);
		bg.add(rdbtnNewRadioButton_1); 
		bg.add(rdbtnNewRadioButton_2);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setBounds(74, 204, 142, 31);
		contentPane.add(lblNewLabel_1);
		
		JButton btnNewButton = new JButton("Next >");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(rdbtnNewRadioButton.isSelected()) {
					frame.dispose();
					Admin.main(null);
				}
				else if(rdbtnNewRadioButton_1.isSelected()) {
					frame.dispose();
					show_all_students.main(null);
					
				}
				else if(rdbtnNewRadioButton_2.isSelected()) {
					frame.dispose();
					timetable_final.main("MONDAY");
					
				}
				else {
					lblNewLabel_1.setText("select a proper option");
				}
			}
		});
		btnNewButton.setBounds(312, 212, 89, 23);
		contentPane.add(btnNewButton);
	}
}
