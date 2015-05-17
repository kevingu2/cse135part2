<!-- Put your Project 2 code here -->
<%@page
    import="java.util.List"
    import="helpers.*"%>
<%
//example of using the methods
    System.out.println("analytics");
	List<State> states = StateHelper.listStatesByTotalWithNoFilter(20, 0);
	for(State state: states){
		System.out.println("id: "+state.getId()+"    name: "+state.getName()+"     total: "+state.getTotal());
	}
	List<Customer> customers=CustomerHelper.listCustomersByTotalWithNoFilter(20, 0);
	for(Customer customer: customers){
		System.out.println("id: "+customer.getId()+"    name: "+customer.getName()+"     total: "+customer.getTotal());
	}
	List<Product> products=ListProductHelper.listProductsByTotalWithNoFilter(20, 0);
	for(Product product: products){
		System.out.println("id: "+product.getId()+"    name: "+product.getName()+"     total: "+product.getTotal());
	}
	System.out.println("Get User product total: "+ListProductHelper.getUserProductTotal(3515,346));
	System.out.println("Get State product total: "+ListProductHelper.getUserProductTotal(3,346));
%>