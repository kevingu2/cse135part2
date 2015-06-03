package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class FindModifiedCellsHelper {
	//returns the total value of the product bought by the state
		public static int getOldCount() {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        try {
	            try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return 0;
	            }
	            String query= "SELECT counter FROM Counter;";
	            
	            stmt = conn.prepareStatement(query);
	            rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("counter");
	            }
	            return 0;
	        } catch (Exception e) {
	            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
	            return 0;
	        } finally {
	            try {
	            	if(stmt != null)
	            	{
	            		stmt.close();
	            	}
	            	if(conn != null)
	            	{
	            		conn.close();
	            	}
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
		}
		public static int getNewCount() {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        try {
	            try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return 0;
	            }
	            String query= "select count(*) from sales;";
	            
	            stmt = conn.prepareStatement(query);
	            rs = stmt.executeQuery();
	            if (rs.next()) {
	                return rs.getInt("count");
	            }
	            return 0;
	        } catch (Exception e) {
	            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
	            return 0;
	        } finally {
	            try {
	            	if(stmt != null)
	            	{
	            		stmt.close();
	            	}
	            	if(conn != null)
	            	{
	            		conn.close();
	            	}
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
		}
		//pass in the old size of the table
		public static void updateTempTables(int count) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;
	        try {
	            try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	            }
	            String query= "TRUNCATE TABLE Modified_Sales; "
	            		+ "INSERT INTO Modified_Sales(uid, pid, quantity, price)  (SELECT uid, pid, quantity, price FROM sales WHERE id >?);"
	            		+ "UPDATE TopK_States SET total = total + r.sum "
	            		+ "FROM (SELECT u.state as u_state, p.cid as p_cid, (s.quantity*s.price) AS sum "
	            		+ "FROM Modified_Sales s, users u, products p "
	            		+ "WHERE s.uid = u.id AND p.id = s.pid) as r "
	            		+ "WHERE sid = r.u_state and cid = r.p_cid; "
	            		+ "UPDATE TopK_States "
	            		+ "SET total = total + r.sum "
	            		+ "FROM (SELECT u.state as u_state, (s.quantity*s.price) AS sum "
	            		+ "FROM Modified_Sales s, users u, products p "
	            		+ "WHERE s.uid = u.id AND p.id = s.pid) as r "
	            		+ "WHERE sid = r.u_state and cid = -1; "
	            		+ "UPDATE TopK_Products "
	            		+ "SET total = total + r.sum "
	            		+ "FROM (SELECT p.id as p_id, p.cid as p_cid, (s.quantity*s.price) AS sum "
	            		+ "FROM Modified_Sales s, products p "
	            		+ "WHERE p.id = s.pid) as r "
	            		+ "WHERE pid = r.p_id and cid = r.p_cid; "
	            		+ "UPDATE TopK_Products "
	            		+ "SET total = total + r.sum "
	            		+ "FROM (SELECT p.id as p_id, (s.quantity*s.price) AS sum "
	            		+ "FROM Modified_Sales s, products p "
	            		+ "WHERE p.id = s.pid) as r "
	            		+ "WHERE pid = r.p_id and cid = -1; "
	            		+ "UPDATE StatesxProducts "
	            		+ "SET total = total + r.sum "
	            		+ "FROM (SELECT u.state as u_state, p.id as p_id, (s.quantity*s.price) AS sum "
	            		+ "FROM Modified_Sales s, users u, products p "
	            		+ "WHERE s.uid = u.id AND p.id = s.pid) as r "
	            		+ "WHERE sid = r.u_state and pid = r.p_id; "
	            		+ "UPDATE Counter SET counter = (SELECT COUNT(*) FROM sales);";
	            
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1,count);
	            stmt.executeUpdate();
	        } catch (Exception e) {
	            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
	        } finally {
	            try {
	            	if(stmt != null)
	            	{
	            		stmt.close();
	            	}
	            	if(conn != null)
	            	{
	            		conn.close();
	            	}
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
		}
		//Get all the totals for each cell with no filter, HashMap key is sid_pid, separated by the underscore _
				public static ArrayList<Total> getModifiedTotals(){
					Connection conn = null;
			        PreparedStatement stmt = null;
			        ResultSet rs = null;

			        ArrayList<Total> totals= new ArrayList<Total>();
			        try {
			            try {
			                conn = HelperUtils.connect();
			            } catch (Exception e) {
			                System.err.println("Internal Server Error. This shouldn't happen.");
			                return totals;
			            }
			            String query= "SELECT u.state as sid, ms.pid, SUM(ms.quantity*ms.price) as total "
			            		+ "FROM Modified_Sales ms, users u WHERE ms.uid = u.id GROUP BY u.state, ms.pid";
			            stmt = conn.prepareStatement(query);
			            rs = stmt.executeQuery();
			            while (rs.next()) {
			                Integer sid = rs.getInt("sid");
			                Integer pid = rs.getInt("pid");
			                Integer total=rs.getInt("total");
			                totals.add(new Total(total, pid, sid));
			            }
			            return totals;
			        } catch (Exception e) {
			            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
			            return new ArrayList<Total>();
			        } finally {
			            try {
			                stmt.close();
			                conn.close();
			            } catch (SQLException e) {
			                e.printStackTrace();
			            }
			        }
				}
}
