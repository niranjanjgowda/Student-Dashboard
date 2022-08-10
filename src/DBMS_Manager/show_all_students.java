package DBMS_Manager;

import java.awt.BorderLayout;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import timemanager.Delete_content_gui;
import timemanager.Login;
import timemanager.Menu_gui;
import timemanager.Sqlmanager;
import javax.swing.SwingConstants;

public class show_all_students extends JFrame {

	private JPanel contentPane;
	static show_all_students frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new show_all_students();
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
	public show_all_students() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Student Database");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(105, 11, 220, 24);
		contentPane.add(lblNewLabel);
		
		String cmd = "select * from students";
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
        scrollPane.setBounds(5, 40, 424, 176);
		contentPane.add(scrollPane);
		
		JButton btnNewButton = new JButton(" < Back ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e){
				frame.dispose();
				DBMS_Manager.Menu_gui.main(null);
			}
		});
		btnNewButton.setBounds(10, 227, 89, 23);
		contentPane.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				String data[] = new String[column_names.length];
				StringJoiner s = new StringJoiner(" AND ");
				for(int i=0;i<column_names.length;i++) {
					s.add(column_names[i]+" = '"+table.getValueAt(row, i)+"'");
				}
				System.out.println(s);
				Connection c = Sqlmanager.connect_withuserandpassword();
				Statement s1;
				try {
					s1 = c.createStatement();
					String cmd = "delete from students where "+s;
					s1.executeUpdate(cmd);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				show_all_students.main(null);
			}
		});
		btnNewButton_1.setBounds(340, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		
	}
}
