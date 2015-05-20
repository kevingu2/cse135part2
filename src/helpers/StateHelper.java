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
            String query= "SELECT one.id, one.name, COALESCE(two.total, 0) as total "
            		+ "FROM (SELECT id, name FROM states ORDER BY name LIMIT ? OFFSET ?) one "
            		+ "LEFT OUTER JOIN "
            		+ "(SELECT u.state, SUM(s.quantity*s.price) as total "
            		+ "FROM users u, Sales s, Products p "
            		+ "WHERE u.id = s.uid AND p.id = s.pid "
            		+ "GROUP BY u.state) AS two "
            		+ "ON one.id = two.state "
            		+ "ORDER BY one.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
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
            String query= "SELECT one.id, one.name, COALESCE(two.total, 0) as total "
            		+ "FROM (SELECT id, name FROM states ORDER BY name LIMIT ? OFFSET ?) one "
            		+ "LEFT OUTER JOIN "
            		+ "(SELECT u.state, SUM(s.quantity*s.price) as total "
            		+ "FROM users u, Sales s, Products p "
            		+ "WHERE p.cid = ? AND u.id = s.uid AND p.id = s.pid "
            		+ "GROUP BY u.state) AS two "
            		+ "ON one.id = two.state "
            		+ "ORDER BY one.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            stmt.setInt(3,c_id);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
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
	            		+ "Group by u.state Order by total desc) r on r.state = st.id Order by total desc limit ? offset ?";
	    
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
	            		+ "Group by u.state Order by total desc) r on r.state = st.id Order by total desc limit ? offset ?";
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

