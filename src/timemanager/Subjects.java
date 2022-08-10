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
import javax.swing.JTextField;

public class Subjects extends JFrame {

	private JPanel contentPane;
	static Subjects frame;
	static Connection sql_connection_id = Sqlmanager.connect_withuserandpassword();
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Subjects();
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
	public Subjects() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Subjects DashBoard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(105, 11, 220, 24);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(91, 56, 103, 20);
		contentPane.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(261, 56, 168, 20);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Add New Subject");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(5, 28, 114, 17);
		contentPane.add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Subject Code :");
		lblNewLabel_2.setBounds(9, 56, 89, 20);
		contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Sub Name :");
		lblNewLabel_3.setBounds(197, 59, 60, 14);
		contentPane.add(lblNewLabel_3);
		
		String cmd = "select sub_code,subject_name from subject where usn = '"+Login.USN+"'";
		ArrayList<ArrayList> data = SqlMethods.sqlselect(cmd);
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
	    DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
	    cellRenderer.setHorizontalAlignment(JLabel.LEFT);
	    table.getColumnModel().getColumn(0).setCellRenderer(cellRenderer);
	    table.getColumnModel().getColumn(1).setCellRenderer(cellRenderer);
	    ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer())
	    .setHorizontalAlignment(JLabel.LEFT);
	    
	    JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(5, 87, 424, 129);
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
		
		JButton btnNewButton_1 = new JButton("Delete");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int row = table.getSelectedRow();
				if(row!=-1) {
				String data[] = new String[column_names.length];
				StringJoiner s = new StringJoiner(" AND ");
				for(int i=0;i<column_names.length;i++) {
					s.add(column_names[i]+" = '"+table.getValueAt(row, i)+"'");
				}
				Connection c = Sqlmanager.connect_withuserandpassword();
				Statement s1;
				try {
					s1 = c.createStatement();
					String cmd = "delete from subject where "+s+"and usn = '"+Login.USN+"'";
					s1.executeUpdate(cmd);
					String cmd2 = "update subject set sl_no=sl_no-1 where usn = '"+Login.USN+"'";
					s1.executeUpdate(cmd2);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				Subjects.main(null);
			}
			}
		});
		btnNewButton_1.setBounds(241, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Insert");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(textField.getText()!=""&&textField_1.getText()!="") {
				try {
					Statement query_processor = sql_connection_id.createStatement();
					ArrayList<ArrayList> a = Sqlmanager.sqlselect("select count(sl_no) from subject where usn = '"+Login.USN+"'");
					int sub_count  = Integer.parseInt((String) a.get(1).get(0));
					query_processor.executeUpdate("insert into subject values('"+textField.getText()+"','"+textField_1.getText()+"',"+(sub_count+1)+",'"+Login.USN+"')");
					frame.dispose();
					Subjects.main(null);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			}
		});
		btnNewButton_2.setBounds(105, 227, 89, 23);
		contentPane.add(btnNewButton_2);
		
		JButton btnNewButton_3 = new JButton("Logout");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
				frame.dispose();
			}
		});
		btnNewButton_3.setBounds(340, 227, 89, 23);
		contentPane.add(btnNewButton_3);
		
	}
}
