package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StateHelper {
	public static List<State> listStateAlphabetically(int limit, int offset) {
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
            String query= "Select st.id, st.name, SUM(s.quantity*s.price) as total "
            		+ "From users u, Sales s, (Select id, name From states "
            		+ "Order by name limit ? offset ?) st Where st.id = u.state And u.id = s.uid "
            		+ "Group by st.id, st.name Order by st.name";
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
	            String query= "Select st.name, r.state, r.total From states st, (Select u.state, SUM(s.quantity*s.price) as total "+ 
	            		"From sales s, users u, products p Where s.pid = p.id AND p.cid = ? AND s.uid = u.id AND role "+
	            		"= 'customer' Group by u.state Order by total desc limit ? offset ?) r Where r.state = st.id "
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
	            String query= "Select st.name, r.state, r.total From states st, (Select u.state, SUM(s.quantity*s.price) as total "+ 
	            		"From sales s, users u, products p Where s.pid = p.id AND s.uid = u.id AND role "+
	            		"= 'customer' Group by u.state Order by total desc limit ? offset ?) r Where r.state = st.id";
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

