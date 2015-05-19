package helpers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ListProductHelper {
	public static List<Product> listProductAlphabeticallyWithFilter(int category_id, int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Product> products = new ArrayList<Product>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Product>();
            }
            String query= "Select p.id, p.name, SUM(s.quantity*s.price) as total "
            		+ "From sales s, (Select id, name From products where cid = ? "
            		+ "Order by name limit ? offset ?) p Where p.id = s.pid "
            		+ "Group by p.id, p.name Order by p.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, category_id);
            stmt.setInt(2,limit);
            stmt.setInt(3, offset);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                products.add(new Product(id, name, total));
            }
            return products;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Product>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	public static List<Product> listProductAlphabeticallyWithNoFilter(int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Product> products = new ArrayList<Product>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Product>();
            }
            String query= "Select p.id, p.name, SUM(s.quantity*s.price) as total "
            		+ "From sales s, (Select id, name From products "
            		+ "Order by name limit ? offset ?) p "
            		+ "Where p.id = s.pid Group by p.id, p.name Order by p.name";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                Integer total=rs.getInt(3);
                products.add(new Product(id, name, total));
            }
            return products;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Product>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	//use when finding top k for all category
	public static List<Product> listProductsByTotalWithNoFilter(int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Product> products = new ArrayList<Product>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Product>();
            }
            String query= "Select r.name, p.pid, p.total From products r, (Select pid, SUM(price*quantity) as total From "
            		+ "sales Group by pid Order by total desc "
            		+ "limit ? offset ?) p Where p.pid = r.id Order by total desc";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            while (rs.next()) {
                String name = rs.getString(1);
                Integer id = rs.getInt(2);
                Integer total=rs.getInt(3);
                products.add(new Product(id, name,total));
            }
            return products;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Product>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	//use when finding top k for a specific category
	public static List<Product> listProductsByTotalWithFilter(int category_id, int limit, int offset) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Product> products = new ArrayList<Product>();
        try {
            try {
                conn = HelperUtils.connect();
            } catch (Exception e) {
                System.err.println("Internal Server Error. This shouldn't happen.");
                return new ArrayList<Product>();
            }
            String query= "Select r.name, p.pid, p.total From products r, (Select pid, SUM(price*quantity) as total From "
            		+ "sales Where pid IN (Select id From products where cid = ?) Group by pid Order by total desc "
            		+ "limit ? offset ?) p Where p.pid = r.id Order by total desc";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, category_id);
            stmt.setInt(2,limit);
            stmt.setInt(3, offset);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            while (rs.next()) {
                String name = rs.getString(1);
                Integer id = rs.getInt(2);
                Integer total=rs.getInt(3);
                products.add(new Product(id, name,total));
            }
            return products;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return new ArrayList<Product>();
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	//returns the total value of the product bought by the user
	public static int getUserProductTotal(int uid, int pid) {
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
            String query= "Select (quantity*price) as total From sales Where uid = ? and pid = ?";
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,uid);
            stmt.setInt(2, pid);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return 0;
        } finally {
            try {
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
	
	//returns the total value of the product bought by the state
	public static int getStateProductTotal(int state_id, int pid) {
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
            String query= "Select SUM(s.price*s.quantity) as total "
            		+ "from sales s, (select id from users where state = ? AND role = 'customer') u "
            		+ "where s.pid = ? and s.uid = u.id";
            
            stmt = conn.prepareStatement(query);
            stmt.setInt(1,state_id);
            stmt.setInt(2, pid);
            rs = stmt.executeQuery();
            System.out.println("Executed Query");
            if (rs.next()) {
                return rs.getInt(1);
            }
            return 0;
        } catch (Exception e) {
            System.err.println("Some error happened!<br/>" + e.getLocalizedMessage());
            return 0;
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
