package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StateHelper {
	public static int getSize()
	{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try
		{
			try
			{
				conn = HelperUtils.connect();
			}
			catch (Exception e)
			{
				System.err.println("Internal Server Error. This shouldn't happen.");
				return 0;
			}
			
			String query = "SELECT Count(*) FROM states";
			stmt = conn.prepareStatement(query);
			rs = stmt.executeQuery();
			if(rs.next())
			{
				return rs.getInt(1);
			}
			else
			{
				throw new SQLException();
			}
		}
		catch (Exception e)
		{
			System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
			return 0;
		}
		finally 
		{
			try 
			{
                stmt.close();
                conn.close();
            } 
			catch (SQLException e) 
			{
                e.printStackTrace();
            }
        }
	}
	
	public static List<State> listStateAlphabeticallyWithNoFilter(int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<State> states = new ArrayList<State>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<State>();
            }
            String query= "SELECT ans.id, ans.name, SUM(ans.total) as total FROM "
            		+ "(SELECT s.id, s.name, COALESCE((sa.quantity*sa.price),0) as total "
            		+ "FROM ((SELECT st.id, st.name FROM States st ORDER BY st.name LIMIT ? OFFSET ?) s "
            		+ "LEFT OUTER JOIN Users u on s.id = u.state LEFT OUTER JOIN Sales sa on u.id = sa.uid)) ans "
            		+ "GROUP BY ans.name, ans.id ORDER BY ans.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                states.add(new State(id, name,total));
            }
            return states;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<State>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	public static List<State> listStateAlphabeticallyWithFilter(int c_id, int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<State> states = new ArrayList<State>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<State>();
            }
            String query= "SELECT ans.st_id, ans.st_name, SUM(ans.total) as total FROM "
            		+ "(SELECT su.st_id, su.st_name, COALESCE(sp.price*sp.quantity, 0) as total "
            		+ "FROM (((SELECT st.id as st_id, st.name as st_name FROM States st ORDER BY st.name LIMIT ? OFFSET ?) s "
            		+ "JOIN Users u on s.st_id = u.state)su "
            		+ "LEFT OUTER JOIN (Sales s JOIN "
            		+ "(SELECT id FROM Products p WHERE p.cid = ?) p on p.id = s.pid) sp on su.id=sp.uid)) ans "
            		+ "GROUP BY ans.st_name, ans.st_id ORDER BY ans.st_name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            stmt.setInt(3,c_id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                states.add(new State(id, name,total));
            }
            return states;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<State>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	//category_filter: sum up the total value of the customer purchase in a certain category
		//let category filter be null if you want to add up all category
		public static List<State> listStatesByTotalWithFilter(int category_filter,int limit, int offset) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        List<State> states = new ArrayList<State>();
	        try {
	            try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return new ArrayList<State>();
	            }
	            String query= "Select st.name, st.id, coalesce(r.total, 0) as total "
	            		+ "From states st left outer join (Select u.state, SUM(s.quantity*s.price) as total "
	            		+ "From sales s, users u, products p Where p.cid = ? AND s.uid = u.id AND s.pid = p.id "
	            		+ "Group by u.state Order by total desc  limit ? offset ?) r on r.state = st.id "
	            		+ "Order by total desc";
	    
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1,category_filter);
	            stmt.setInt(2,limit);
	            stmt.setInt(3,offset);
	            rs = stmt.executeQuery();
	            while (rs.next()) {
	                String name= rs.getString(1);
	                Integer id = rs.getInt(2);
	                Integer total=rs.getInt(3);
	                states.add(new State(id, name, total));
	            }
	            return states;
	        } catch (Exception e) {
	            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
	            return new ArrayList<State>();
	        } finally {
	            try {
	                stmt.close();
	                conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }
		public static List<State> listStatesByTotalWithNoFilter(int limit, int offset) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        List<State> states = new ArrayList<State>();
	        try {
	            try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return new ArrayList<State>();
	            }
	            String query= "Select st.name, st.id, coalesce(r.total, 0) as total "
	            		+ "From states st left outer join (Select u.state, SUM(s.quantity*s.price) as total "
	            		+ "From sales s, users u, products p where s.uid = u.id AND s.pid = p.id "
	            		+ "Group by u.state Order by total desc limit ? offset ?) r on r.state = st.id "
	            		+ "Order by total desc";
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1,limit);
	            stmt.setInt(2,offset);
	            rs = stmt.executeQuery();
	            while (rs.next()) {
	                String name= rs.getString(1);
	                Integer id = rs.getInt(2);
	                Integer total=rs.getInt(3);
	                states.add(new State(id, name, total));
	            }
	            return states;
	        } catch (Exception e) {
	            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
	            return new ArrayList<State>();
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

