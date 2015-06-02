<!-- Put your Project 2 code here -->
<jsp:include page="/html/head.html" />
<%@page
    import="java.util.List"
    import="helpers.*"%>
<%
//example of using the methods
/*
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
*/
%>
<%
	// Constants for number of rows and columns on the page
	long start = System.currentTimeMillis();
	int rowNum = 50;
	int colNum = 50;

	// Request Parameters
	String RowType = "States";//request.getParameter("RowType");
	String OrderBy = "Top-K"; //request.getParameter("OrderBy");
	String CategoryFilter = request.getParameter("CategoryFilter");
	String poffset = request.getParameter("poffset");
	String roffset = request.getParameter("roffset");
	if(poffset==null){ poffset = "0"; }
	if(roffset==null){ roffset = "0"; }
	String log = "\nCreating report with the following parameters:\n" +
				 "Row Type: " + RowType + "\n" +
				 "Order By: " + OrderBy + "\n" +
				 "Category Filter ID: " + CategoryFilter + "\n" +
				 "poffset: " + poffset + "\n" +
				 "roffset: " + roffset + "\n";
	System.out.println(log);
%>
<%	
	List<Product> Products;
	
	if(OrderBy.equals("Alphabetical")) //Alphabetical
	{
		if(CategoryFilter.equals("All Categories")) //No Filter
		{
			Products = ListProductHelper.listProductAlphabeticallyWithNoFilter(colNum, Integer.parseInt(poffset));
		}
		else //Filter
		{
			Products = ListProductHelper.listProductAlphabeticallyWithFilter(Integer.parseInt(CategoryFilter), colNum, Integer.parseInt(poffset));
		}
		
		if(RowType.equals("Customers"))
		{
			List<Customer> Rows;
			if(CategoryFilter.equals("All Categories")) //No Filter
			{
				Rows = CustomerHelper.listCustomersAlphabeticallyWithNoFilter(rowNum, Integer.parseInt(roffset));
			}
			else //Filter
			{
				Rows = CustomerHelper.listCustomersAlphabeticallyWithFilter(Integer.parseInt(CategoryFilter),rowNum, Integer.parseInt(roffset));
			}
%>
			<%if(Rows.size()>=rowNum){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset) %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset)+rowNum %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= rowNum %> <%= RowType %>">
				</form>
			<%}%>
			<%if(Products.size()>=colNum){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset)+colNum %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset) %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= colNum %> Products">
				</form>
			<%}%>

	

		<!-- Customer Alphabetical Report -->
		<table class="table table-striped" align="center" border="1">
		<thead>
			<tr>
				<th></th>
				<td align="center" colspan="<%= Products.size() %>"><B>Products</B></td>
			</tr>
			<tr>
				<th><%= RowType %>(Below)</th>
				<% for(int i = 0; i < Products.size(); i++){ %>
				<td align="center"><B><%= Products.get(i).getName() %>(<%= Products.get(i).getTotal() %>)</B></th>
				<% } %>
			</tr>
		</thead>
		<tbody>
			<% for(int i = 0; i < Rows.size(); i++){ 
				Customer c = Rows.get(i); %>
				<tr>
					<th><%= c.getName() %>(<%= c.getTotal() %>)</th>
					<% for(int j = 0; j < Products.size(); j++){
						Product p = Products.get(j); %>
						<td align="center"><%= ListProductHelper.getUserProductTotal(c.getId(), p.getId()) %></td>
					<% } %>
				</tr>
			<% } %>
		</tbody>	
		</table>
		<!-- End of Customer Alphabetical Report -->
<%
		}
		else
		{
			List<State> Rows;
			if(CategoryFilter.equals("All Categories")) //No Filter
			{
				Rows = StateHelper.listStateAlphabeticallyWithNoFilter(rowNum, Integer.parseInt(roffset));
			}
			else //Filter
			{
				Rows = StateHelper.listStateAlphabeticallyWithFilter(Integer.parseInt(CategoryFilter),rowNum, Integer.parseInt(roffset));
			}
%>
			<%if(Rows.size()>=rowNum){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset) %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset)+rowNum %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= rowNum %> <%= RowType %>">
				</form>
			<%}%>
			<%if(Products.size()>=colNum){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset)+colNum %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset) %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= colNum %> Products">
				</form>
			<%}%>

		<!-- State Alphabetical Report -->
		<table class="table table-striped" align="center" border="1">
		<thead>
			<tr>
				<th></th>
				<td align="center" colspan="<%= Products.size() %>"><B>Products</B></td>
			</tr>
			<tr>
				<th><%= RowType %>(Below)</th>
				<% for(int i = 0; i < Products.size(); i++){ %>
				<td align="center"><B><%= Products.get(i).getName() %>(<%= Products.get(i).getTotal() %>)</B></th>
				<% } %>
			</tr>
		</thead>
		<tbody>
			<% for(int i = 0; i < Rows.size(); i++){ 
				State s = Rows.get(i); %>
				<tr>
					<th><%= s.getName() %>(<%= s.getTotal() %>)</th>
					<% for(int j = 0; j < Products.size(); j++){
						Product p = Products.get(j); %>
						<td align="center"><%= ListProductHelper.getStateProductTotal(s.getId(), p.getId()) %></td>
					<% } %>
				</tr>
			<% } %>
		</tbody>	
		</table>
		<!-- End of State Alphabetical Report -->
<%
		}
		System.out.println("Time To Generate Report: " + (System.currentTimeMillis() - start) + " ms");
	}
	else //Top-K
	{
		if(CategoryFilter.equals("All Categories")) //No Filter
		{
			Products = ListProductHelper.listProductsByTotalWithNoFilter(colNum, Integer.parseInt(poffset));
		}
		else //Filter
		{
			Products = ListProductHelper.listProductsByTotalWithFilter(Integer.parseInt(CategoryFilter), colNum, Integer.parseInt(poffset));
		}
		
		if(RowType.equals("Customers"))
		{
			List<Customer> Rows;
			if(CategoryFilter.equals("All Categories")) //No Filter
			{
				Rows = CustomerHelper.listCustomersByTotalWithNoFilter(rowNum, Integer.parseInt(roffset));
			}
			else //Filter
			{
				Rows = CustomerHelper.listCustomersByTotalWithFilter(Integer.parseInt(CategoryFilter), rowNum, Integer.parseInt(roffset));
			}
%>
			<%if(Rows.size()>=rowNum){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset) %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset)+rowNum %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= rowNum %> <%= RowType %>">
				</form>
			<%}%>
			<%if(Products.size()>=colNum){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset)+colNum %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset) %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= colNum %> Products">
				</form>
			<%}%>
		<!-- Customer Top-K Report -->
		<table class="table table-striped" align="center" border="1">
		<thead>
			<tr>
				<th></th>
				<td align="center" colspan="<%= Products.size() %>"><B>Products</B></td>
			</tr>
			<tr>
				<th><%= RowType %>(Below)</th>
				<% for(int i = 0; i < Products.size(); i++){ %>
				<td align="center"><B><%= Products.get(i).getName() %>(<%= Products.get(i).getTotal() %>)</B></th>
				<% } %>
			</tr>
		</thead>
		<tbody>
			<% for(int i = 0; i < Rows.size(); i++){ 
				Customer c = Rows.get(i); %>
				<tr>
					<th><%= c.getName() %>(<%= c.getTotal() %>)</th>
					<% for(int j = 0; j < Products.size(); j++){
						Product p = Products.get(j); %>
						<td align="center"><%= ListProductHelper.getUserProductTotal(c.getId(), p.getId()) %></td>
					<% } %>
				</tr>
			<% } %>
		</tbody>	
		</table>
		<!-- End of Customer Top-K Report -->
<!-------------------------------------PART 3 CODE STARTS HERE------------------------------------------>
<%
		}
		else
		{
			List<State> Rows;
			if(CategoryFilter.equals("All Categories")) //No Filter
			{
				Rows = StateHelper.listStatesByTotalWithNoFilter(rowNum, Integer.parseInt(roffset));
			}
			else //Filter
			{
				Rows = StateHelper.listStatesByTotalWithFilter(Integer.parseInt(CategoryFilter), rowNum, Integer.parseInt(roffset));
			}
%>
			<%if(false/*Rows.size()>=rowNum*/){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset) %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset)+rowNum %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= rowNum %> <%= RowType %>">
				</form>
			<%}%>
			<%if(false /*Products.size()>=colNum*/){ %>
				<form action="analytics" method="post">
					<input type="hidden" name="RowType" value="<%= RowType %>">
					<input type="hidden" name="OrderBy" value="<%= OrderBy %>">
					<input type="hidden" name="CategoryFilter" value="<%= CategoryFilter %>">
					<input type="hidden" name="poffset" value="<%= Integer.parseInt(poffset)+colNum %>">
					<input type="hidden" name="roffset" value="<%= Integer.parseInt(roffset) %>">
					<input type="hidden" name="Hide" value="Hide">
					<input type="hidden" name="Run" value="Run">
					<input type="submit" value="Next <%= colNum %> Products">
				</form>
			<%}%>
			
		<!-- Refresh Button Below -->
		<input type="button" onClick="refresh();" value="Refresh Table">
		<br>
		<br>
		<!-- Refresh Button Above -->
		
		<!-- State Top-K Report -->
		<table class="table table-striped" align="center" border="1">
		<thead>
			<tr>
				<th></th>
				<td align="center" colspan="<%= Products.size() %>"><B>Products</B></td>
			</tr>
			<tr>
				<th><%= RowType %>(Below)</th>
				<% for(int i = 0; i < Products.size(); i++){ %>
				<td align="center" id="c<%= Products.get(i).getId() %>"><B><%= Products.get(i).getName() %>(<font id="c<%= Products.get(i).getId()%>font" style="color:'black'"><div id="c<%= Products.get(i).getId()%>num"><%= Products.get(i).getTotal() %></div></font>)</B></th>
				<% } %>
			</tr>
		</thead>
		<tbody>
			<% for(int i = 0; i < Rows.size(); i++){ 
				State s = Rows.get(i); %>
				<tr id="r<%= s.getId()%>">
					<th><%= s.getName() %>(<font id="r<%= s.getId()%>font" style="color:'black'"><div id="r<%= s.getId()%>num"><%= s.getTotal() %></div></font>)</th>
					<% for(int j = 0; j < Products.size(); j++){
						Product p = Products.get(j); %>
						<td style="color:'black'" align="center" id="r<%= s.getId()%>_c<%= p.getId()%>"><%= ListProductHelper.getStateProductTotal(s.getId(), p.getId()) %></td>
					<% System.out.println(s.getId() + "_" + p.getId());} %>
				</tr>
			<% } %>
		</tbody>	
		</table>
		<!-- End of State Top-K Report -->
<%
		}
		System.out.println("Time To Generate Report: " + (System.currentTimeMillis() - start) + " ms");
	}
%>
