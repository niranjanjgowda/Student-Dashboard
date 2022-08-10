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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

public class Delete_content_gui extends JFrame {

	private JPanel contentPane;
	static Delete_content_gui frame; 
	static Connection sql_connection_id = Sqlmanager.connect_withuserandpassword();
	/**
	 * Launch the application.
	 */
	public static void main() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Delete_content_gui();
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
	public Delete_content_gui() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Activity DashBoard");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblNewLabel.setBounds(105, 11, 220, 24);
		contentPane.add(lblNewLabel);
		
		
		String cmd = "select SUB_DATE,SUBJECT_CODE,Description from activity,subject where subject.SUB_CODE=activity.SUBJECT_CODE AND activity.usn = '"+Login.USN+"' order by sub_date";
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
					String cmd = "delete from activity where "+s+"and usn = '"+Login.USN+"'";
					s1.executeUpdate(cmd);
					String cmd2 = "update activity set sl_no=sl_no-1 where usn = '"+Login.USN+"'";
					s1.executeUpdate(cmd2);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				frame.dispose();
				Delete_content_gui.main();
			}
			}
		});
		btnNewButton_1.setBounds(241, 227, 89, 23);
		contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_2 = new JButton("Insert");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ResultSet r3;
				try {
					Statement query_processor = sql_connection_id.createStatement();
					r3 = query_processor.executeQuery("select sub_code from subject where usn = '"+Login.USN+"' order by sl_no");
					String subjects = "";
					String subject_names = "";
					while(r3.next())
						subjects += r3.getObject(1)+"\n";
					r3 = query_processor.executeQuery("select subject_name from subject where usn = '"+Login.USN+"' order by sl_no");
					while(r3.next())
						subject_names += r3.getObject(1)+"\n";
					frame.dispose();
					insert_activity_gui.main(subjects.split("\n"),subject_names.split("\n"));
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
