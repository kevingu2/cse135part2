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
            String query= "SELECT ans.id, ans.name, SUM(ans.total) AS total FROM "
            		+ "(SELECT u.id, u.name, COALESCE((s.quantity*s.price),0) as total "
            		+ "FROM ((SELECT u.id, u.name FROM users u ORDER BY u.name LIMIT ? OFFSET ?) u "
            		+ "LEFT OUTER JOIN Sales s on u.id = s.uid)) ans "
            		+ "GROUP BY ans.name, ans.id ORDER BY ans.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
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
            String query= "SELECT ans.id, ans.name, SUM(ans.total) AS total FROM "
            		+ "(SELECT u.id, u.name, COALESCE(sp.price*sp.quantity, 0) as total "
            		+ "FROM (SELECT u.id, u.name FROM users u ORDER BY u.name LIMIT ? OFFSET ?) u "
            		+ "LEFT OUTER JOIN "
            		+ "(Sales s JOIN (SELECT id FROM Products p WHERE p.cid =  ?) p ON s.pid = p.id) sp on u.id=sp.pid)ans "
            		+ "GROUP BY ans.name, ans.id ORDER BY ans.name";
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
            String query= "Select id,name, SUM(coalesce(price*quantity, 0)) "
            		+ "as total From ((Select u.id, u.name from users u)u "
            		+ "left outer join (Select uid, quantity, price from sales where pid in  "
            		+ "(Select id from Products where cid=?))sp on sp.uid=u.id) usp "
            		+ "Group by id, name Order by total DESC limit ? offset ?";    
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
	            String query= "Select id,name, SUM(coalesce(price*quantity, 0)) "
	            		+ "as total From ((Select u.id, u.name from users u)u "
	            		+ "left outer join (Select uid, quantity, price from sales)sp on sp.uid=u.id) usp "
	            		+ "Group by id, name Order by total DESC limit ? offset ?";
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
