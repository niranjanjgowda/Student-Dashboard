package DBMS_Manager;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringJoiner;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import timemanager.Delete_content_gui;
import timemanager.Login;
import timemanager.Sqlmanager;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import java.awt.Color;

public class timetable_final extends JFrame {

	private JPanel contentPane;
	static timetable_final frame;
	static Connection sql_connection_id = Sqlmanager.connect_withuserandpassword();
	private JTextField textField;
	static String pday;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	/**
	 * Launch the application.
	 */
	public static void main(String day) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					pday=day;
					frame = new timetable_final();
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
	public timetable_final() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Timetable DashBoard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(105, 2, 220, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Day   : ");
		lblNewLabel_1.setBounds(10, 40, 59, 14);
		contentPane.add(lblNewLabel_1);
		
		JComboBox comboBox = new JComboBox(timemanager.Menu_gui.working_days);
		comboBox.setBounds(68, 37, 126, 22);
		contentPane.add(comboBox);
		
		JButton btnNewButton_3 = new JButton("Show TimeTable");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable_final.main((String) comboBox.getSelectedItem());
				frame.dispose();
			}
		});
		btnNewButton_3.setBounds(227, 37, 197, 23);
		contentPane.add(btnNewButton_3);
		
		
		String cmd = "select P_ID,P_NAME,SUBJECT_NAME from timetable3,subject where day = '"+pday+"' and subject.SUB_CODE=timetable3.P_NAME ";
		ArrayList<ArrayList> data = Sqlmanager.sqlselect(cmd);
		String[] column_names = ((String) data.get(0).toString().subSequence(1,data.get(0).toString().length()-1)).split(", ");
		String[][] table_data = new String[data.size()-1][column_names.length];
		data.remove(0);
		for(int i=0;i<data.size();i++) {
			for(int j=0;j<column_names.length;j++) {
				table_data[i][j]=(String) data.get(i).get(j);
			}
		}
		
		DefaultTableModel tableModel = new DefaultTableModel(table_data,column_names) {
		    @Override
		    public boolean isCellEditable(int row, int column) {
		       return false;
		    }
		};
		
		JTable table = new JTable(table_data,column_names);
		table.setBounds(30, 40, 200, 300);
		table.setModel(tableModel);
		table.getColumnModel().getColumn(0).setPreferredWidth(3);
		table.getColumnModel().getColumn(1).setPreferredWidth(3);
	    table.getColumnModel().getColumn(2).setPreferredWidth(100);
	    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.LEFT);
	    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	    table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	    ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.LEFT);
	    
	    JScrollPane scrollPane = new JScrollPane(table);
        //scrollPane.setBounds(5, 94, 424, 122);
        scrollPane.setBounds(5, 76, 424, 97);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton(" < Back ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				frame.setVisible(false);
				Menu_gui.main(null);
			}
		});
		btnNewButton.setBounds(5, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_4 = new JButton("Logout");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DBA_Login.main(null);
				frame.dispose();
			}
		});
		btnNewButton_4.setBounds(340, 227, 89, 23);
		contentPane.add(btnNewButton_4);
		
		textField_1 = new JTextField();
		textField_1.setBounds(153, 196, 103, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(68, 196, 75, 20);
		contentPane.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(266, 196, 89, 20);
		contentPane.add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Period No.");
		lblNewLabel_2.setBounds(80, 179, 59, 14);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_2_1 = new JLabel("Day");
		lblNewLabel_2_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_2_1.setBounds(174, 179, 59, 14);
		contentPane.add(lblNewLabel_2_1);
		
		JLabel lblNewLabel_2_2 = new JLabel("Subject id");
		lblNewLabel_2_2.setBounds(279, 179, 59, 14);
		contentPane.add(lblNewLabel_2_2);
		
		JLabel lblNewLabel_3 = new JLabel("Add");
		lblNewLabel_3.setFont(new Font("Stencil", Font.PLAIN, 13));
		lblNewLabel_3.setForeground(Color.RED);
		lblNewLabel_3.setBounds(26, 191, 34, 32);
		contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton_1 = new JButton("Add");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Statement query_processor;
				try {
					query_processor = sql_connection_id.createStatement();
					query_processor.executeUpdate("insert into timetable3 values('"+textField_2.getText()+"','"+textField_1.getText()+"','"+textField_3.getText()+"','1','1NH20IS096')");
					query_processor.executeUpdate("commit");
					frame.dispose();
					timetable_final.main("MONDAY");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		btnNewButton_1.setBounds(365, 195, 59, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_11 = new JButton("Delete");
		btnNewButton_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row!=-1) {
				String data[] = new String[column_names.length-1];
				StringJoiner s = new StringJoiner(" AND ");
				for(int i=0;i<column_names.length-1;i++) {
					s.add(column_names[i]+" = '"+table.getValueAt(row, i)+"'");
				}
				Connection c = Sqlmanager.connect_withuserandpassword();
				Statement s1;
				try {
					s1 = c.createStatement();
					String cmd = "delete from timetable3 where "+s;
					s1.executeUpdate(cmd);
					String cmd2 = "update timetable3 set sl_no=sl_no-1";
					s1.executeUpdate(cmd2);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				timetable_final.main("MONDAY");
			}
			}
		});
		btnNewButton_11.setBounds(241, 227, 89, 23);
		contentPane.add(btnNewButton_11);
		
	}
}






