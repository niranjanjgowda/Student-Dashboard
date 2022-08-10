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
import javax.swing.JComboBox;
import javax.swing.JTextField;

public class timetable_final extends JFrame {

	private JPanel contentPane;
	static String pdate;
	static String pday;
	static Boolean pworkingday;
	static timetable_final frame;
	static Connection sql_connection_id = Sqlmanager.connect_withuserandpassword();
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String date,String day,boolean working) {
		pdate = date;
		pday = day;
		pworkingday = working;
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
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
		
		JComboBox comboBox = new JComboBox(Menu_gui.working_days);
		comboBox.setBounds(68, 37, 126, 22);
		contentPane.add(comboBox);
		
		JButton btnNewButton_3 = new JButton("Show TimeTable");
		btnNewButton_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				timetable_final.main(pdate,(String) comboBox.getSelectedItem(),pworkingday);
				frame.dispose();
			}
		});
		btnNewButton_3.setBounds(227, 37, 197, 23);
		contentPane.add(btnNewButton_3);
		
		
		String cmd = "select P_ID,P_NAME,SUBJECT_NAME from timetable3,subject where day = '"+pday+"' and subject.SUB_CODE=timetable3.P_NAME ";
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
        //scrollPane.setBounds(5, 94, 424, 122);
        scrollPane.setBounds(5, 76, 424, 140);
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
		//contentPane.add(btnNewButton_1);
		
		JButton btnNewButton_4 = new JButton("Logout");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Login.main(null);
				frame.dispose();
			}
		});
		btnNewButton_4.setBounds(340, 227, 89, 23);
		contentPane.add(btnNewButton_4);
		
		textField = new JTextField();
		textField.setBounds(68, 65, 126, 20);
		//contentPane.add(textField);
		//textField.setColumns(10);
		
		//ArrayList<ArrayList> a = Sqlmanager.sqlselect("select sub_code from subject where usn = '"+USN+"'");
		//a.remove(0);
		//String pat = "], \\[";
		//String subject_names[] = (a.toString().substring(2, (a.toString().length()-2))).split(pat);
		
		//JComboBox comboBox_1 = new JComboBox(subject_names);
		//comboBox_1.setBounds(298, 65, 126, 20);
		//contentPane.add(comboBox_1);
		
		JLabel lblNewLabel_2 = new JLabel("Period no.");
		lblNewLabel_2.setBounds(10, 65, 58, 14);
		//contentPane.add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Subject  :");
		lblNewLabel_3.setBounds(227, 68, 58, 14);
		//contentPane.add(lblNewLabel_3);
		
		JButton btnNewButton_5 = new JButton("Add new subject");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Subjects.main(null);
				frame.dispose();
			}
		});
		btnNewButton_5.setBounds(227, 89, 197, 23);
		//contentPane.add(btnNewButton_5);
		
		JButton btnNewButton_2 = new JButton("Insert");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int pid_num  =  Integer.parseInt(textField.getText());
					//Statement query_processor = sql_connection_id.createStatement();
					//query_processor.executeUpdate("insert into timetable3 values('P"+Integer.parseInt(textField.getText())+"','"+"pday"+"','"+comboBox.getSelectedItem()+"',"+subject_names.length+",'"+USN+"')");
					//query_processor.executeUpdate("commit");
					timetable_final.main(pdate,(String) comboBox.getSelectedItem(),pworkingday);
					frame.dispose();
				//} catch (SQLException e1) {
					// TODO Auto-generated catch block
					//e1.printStackTrace();
				} catch (Exception e2) {
					System.out.println("OOPS");
					e2.printStackTrace();
				}
			}
		});
		btnNewButton_2.setBounds(105, 227, 89, 23);
		//contentPane.add(btnNewButton_2);
	}
}






