package timemanager;

import java.sql.*;
import java.util.ArrayList;

import com.mysql.cj.jdbc.exceptions.SQLExceptionsMapping;

public class Sqlmanager {
	public static Connection connect_withuserandpassword() {
		try {
			Connection c  = SqlMethods.connect("yourdbausername","yourpassword");
			return c;
		}
		catch(Exception e) {
			System.out.println(e);
		}
		return null;
	}
	
	static Connection connect(String username,String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6504474", username,password);
			return c;
		} catch (Exception e) {
				System.out.println(e.toString());
		}
		return null;
	}
	
	static Connection connectl() {
		try {
			String username = "system";
			String password = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", username,password);
			return c;
		} catch (Exception e) {
				System.out.println(e.toString());
		}
		return null;
	}
	
	static void sqlselect(Connection c,String table_name) {
		try {
			Statement s = c.createStatement();
			String cmd = "select * from "+table_name;
			ResultSet r = s.executeQuery(cmd);
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			for(int i=1;i<=col_num;i++)
				System.out.print(m.getColumnName(i)+"   ");
			System.out.println();
			while(r.next()) {
				for(int i=1;i<=col_num;i++) {
					System.out.print(r.getObject(i) +"   ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ArrayList sqlselect(String cmd) {
		ArrayList<ArrayList> out = new ArrayList<ArrayList>();
		ArrayList<String> column_names = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		Connection c = Sqlmanager.connect_withuserandpassword();
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery(cmd);
			try {
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			for(int i=1;i<=col_num;i++)
				column_names.add(m.getColumnName(i)+"");
			out.add(column_names);
			while(r.next()) {
				ArrayList<String> inner_out = new ArrayList<String>();
				for(int i=1;i<=col_num;i++) {
					inner_out.add(r.getObject(i)+"");
				}
				out.add(inner_out);
			}
			}catch (SQLException e) {
			}
			return out;
		}
		catch (Exception e) {
				System.out.println(e);
		}
		return null;
	}
	
	static String sqlselect(ResultSet r) {
		try {
			String out = "";
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			for(int i=1;i<=col_num;i++)
				out+=m.getColumnName(i)+"   ";
				//System.out.print(m.getColumnName(i)+"   ");
			out+="\n";
			//System.out.println();	
			while(r.next()) {
				for(int i=1;i<=col_num;i++) {
					out+=r.getObject(i)+"  ";
					//System.out.print(r.getObject(i) +"   ");
				}
				out+="\n";
				//System.out.println();
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	static String[][] sqlselect(ResultSet r,int rows) {
		try {
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			String out[][] = new String[rows+1][col_num];
			for(int i=1;i<=col_num;i++)
				out[0][i-1]=m.getColumnName(i);
			while(r.next()) {
				for(int j=1;j<=rows;j++) {
				for(int i=1;i<=col_num;i++) {
					out[j][i-1]=r.getObject(i)+"";
				}
				}
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
}

class SqlMethods{
	
	static Connection connect(String username,String password) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:mysql://sql6.freesqldatabase.com:3306/sql6504474", username,password);
			return c;
		} catch (Exception e) {
				System.out.println(e.toString());
		}
		return null;
	}
	
	static Connection connectl() {
		try {
			String username = "system";
			String password = "tiger";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection c = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", username,password);
			return c;
		} catch (Exception e) {
				System.out.println(e.toString());
		}
		return null;
	}
	
	static void sqlselect(Connection c,String table_name) {
		try {
			Statement s = c.createStatement();
			String cmd = "select * from "+table_name;
			ResultSet r = s.executeQuery(cmd);
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			for(int i=1;i<=col_num;i++)
				System.out.print(m.getColumnName(i)+"   ");
			System.out.println();
			while(r.next()) {
				for(int i=1;i<=col_num;i++) {
					System.out.print(r.getObject(i) +"   ");
				}
				System.out.println();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	static ArrayList sqlselect(String cmd) {
		ArrayList<ArrayList> out = new ArrayList<ArrayList>();
		ArrayList<String> column_names = new ArrayList<String>();
		@SuppressWarnings("rawtypes")
		Connection c = Sqlmanager.connect_withuserandpassword();
		try {
			Statement s = c.createStatement();
			ResultSet r = s.executeQuery(cmd);
			try {
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			for(int i=1;i<=col_num;i++)
				column_names.add(m.getColumnName(i)+"");
			out.add(column_names);
			while(r.next()) {
				ArrayList<String> inner_out = new ArrayList<String>();
				for(int i=1;i<=col_num;i++) {
					inner_out.add(r.getObject(i)+"");
				}
				out.add(inner_out);
			}
			}catch (SQLException e) {
			}
			return out;
		}
		catch (Exception e) {
				System.out.println(e);
		}
		return null;
	}
	
	static String sqlselect(ResultSet r) {
		try {
			String out = "";
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			for(int i=1;i<=col_num;i++)
				out+=m.getColumnName(i)+"   ";
				//System.out.print(m.getColumnName(i)+"   ");
			out+="\n";
			//System.out.println();	
			while(r.next()) {
				for(int i=1;i<=col_num;i++) {
					out+=r.getObject(i)+"  ";
					//System.out.print(r.getObject(i) +"   ");
				}
				out+="\n";
				//System.out.println();
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
	
	static String[][] sqlselect(ResultSet r,int rows) {
		try {
			ResultSetMetaData m = r.getMetaData();
			int col_num = m.getColumnCount();
			String out[][] = new String[rows+1][col_num];
			for(int i=1;i<=col_num;i++)
				out[0][i-1]=m.getColumnName(i);
			while(r.next()) {
				for(int j=1;j<=rows;j++) {
				for(int i=1;i<=col_num;i++) {
					out[j][i-1]=r.getObject(i)+"";
				}
				}
			}
			return out;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;	
	}
}