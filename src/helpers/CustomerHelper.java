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
	public static List<Customer> listCustomersAlphabetically(int limit, int offset) {
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
            String query= "Select u.id, u.name, SUM(s.quantity*s.price) as total "
            		+ "From sales s, (Select id, name From users Where role = 'customer' "
            		+ "Order by name limit ? offset ?) u Where u.id = s.uid "
            		+ "Group by u.id, u.name Order by u.name";
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
            String query= "Select t.uid, u.name, t.total From Users u, "
            		+ "(Select uid, SUM(price*quantity) as total From sales s where s.pid  "
            		+ "in (Select id from Products where cid=?) Group by uid Order by total DESC  "
            		+ "limit ? offset ?) t Where u.id=t.uid Order by total DESC ";
            
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
	            String query= "Select t.uid, u.name, t.total From Users u, "
	            		+ "(Select uid, SUM(price*quantity) as total From sales s "
	            		+ "Group by uid Order by total DESC "
	            		+ "limit ? offset ?) t Where u.id=t.uid Order by total DESC ";
	            
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
