package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerHelper {
	//type_ordering: name or total
	//limit: number of customers to get
	//offset: where to start
	public static List<Customer> listCustomersAlphabeticallyWithNoFilter(int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Customer> customers = new ArrayList<Customer>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Customer>();
            }//
            String query= "SELECT one.id, one.name, COALESCE(two.total, 0) as total "
            		+ "FROM (SELECT id, name FROM users ORDER BY name LIMIT ? OFFSET ?) one "
            		+ "LEFT OUTER JOIN "
            		+ "(SELECT u.id, SUM(s.quantity*s.price) as total "
            		+ "FROM users u, Sales s, Products p "
            		+ "WHERE u.id = s.uid AND s.pid = p.id "
            		+ "GROUP BY u.id) AS two "
            		+ "ON one.id = two.id ORDER BY one.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            stmt.setInt(3,limit);
            stmt.setInt(4, offset);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                customers.add(new Customer(name, id,total));
            }
            return customers;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Customer>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	public static List<Customer> listCustomersAlphabeticallyWithFilter(int c_id, int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Customer> customers = new ArrayList<Customer>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Customer>();
            }
            String query= "SELECT one.id, one.name, COALESCE(two.total, 0) as total "
            		+ "FROM (SELECT id, name FROM users ORDER BY name LIMIT ? OFFSET ?) one "
            		+ "LEFT OUTER JOIN "
            		+ "(SELECT u.id, SUM(s.quantity*s.price) as total "
            		+ "FROM users u, Sales s, Products p "
            		+ "WHERE u.id = s.uid AND s.pid = p.id AND p.cid = ? "
            		+ "GROUP BY u.id) AS two "
            		+ "ON one.id = two.id "
            		+ "ORDER BY one.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            stmt.setInt(3, c_id);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                customers.add(new Customer(name, id,total));
            }
            return customers;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Customer>();
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
	public static List<Customer> listCustomersByTotalWithFilter(int category_filter,int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Customer> customers = new ArrayList<Customer>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Customer>();
            }
            String query= "Select x.id, u.name, x.total "
            		+ "From users u, "
            		+ "(Select id, SUM(coalesce(price*quantity, 0)) "
            		+ "as total From (Select * from users u "
            		+ "left outer join (Select uid, quantity, price from sales where pid in  "
            		+ "(Select id from Products where cid=?))sp on sp.uid=u.id) usp "
            		+ "Group by id Order by total DESC limit ? offset ?)x "
            		+ "Where x.id=u.id order by x.total DESC";    
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,category_filter);
            stmt.setInt(2,limit);
            stmt.setInt(3,offset);
            rs = stmt.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                customers.add(new Customer(name, id, total));
            }
            return customers;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Customer>();
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
		public static List<Customer> listCustomersByTotalWithNoFilter(int limit, int offset) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        ResultSet rs = null;

	        List<Customer> customers = new ArrayList<Customer>();
	        try {
	            try {
	                conn = HelperUtils.connect();
	            } catch (Exception e) {
	                System.err.println("Internal Server Error. This shouldn't happen.");
	                return new ArrayList<Customer>();
	            }
	            String query= "Select x.id, u.name, x.total "
	            		+ "From users u, "
	            		+ "(Select id, SUM(coalesce(price*quantity, 0)) "
	            		+ "as total From (Select * from users u "
	            		+ "left outer join (Select uid, quantity, price from sales)sp on sp.uid=u.id) usp "
	            		+ "Group by id Order by total DESC	limit ? offset ?)x "
	            		+ "Where x.id=u.id order by x.total DESC";
	            stmt = conn.prepareStatement(query);
	            stmt.setInt(1,limit);
	            stmt.setInt(2,offset);
	            rs = stmt.executeQuery();
	            while (rs.next()) {
	                Integer id = rs.getInt(1);
	                String name = rs.getString(2);
	                Integer total=rs.getInt(3);
	                customers.add(new Customer(name, id, total));
	            }
	            return customers;
	        } catch (Exception e) {
	            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
	            return new ArrayList<Customer>();
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
