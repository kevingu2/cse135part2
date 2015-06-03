<!-- Put your Project 2 code here -->
<jsp:include page="/html/head.html" />
<%@page
    import="java.util.List"
    import="java.util.ArrayList"
    import="java.util.HashMap"
    import="helpers.*"%>
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
	//TODO: ADD COUNT HIDDEN FIELD
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
///////////////////////////////////////////////// PART 3: STATES TOP-K /////////////////////////////////////////////////
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
<%
HashMap<String, Integer> totals;
if(CategoryFilter.equals("All Categories"))
{
	totals = ListProductHelper.stateProductTotalForTopKWithNoFilter(50, 0);
}
else
{
	totals = ListProductHelper.stateProductTotalForTopKWithFilter(Integer.parseInt(CategoryFilter), 50, 0);
}
/*
//example of using cell query with no filter run with all category
/*HashMap<String, Integer> totalsNoFilter=ListProductHelper.stateProductTotalForTopKWithNoFilter(50, 0);
for(int j = 0; j < Rows.size(); j++){ 
	State s = Rows.get(j);
	Integer sid=s.getId();
	for(int i = 0; i < Products.size(); i++){
		Product p=Products.get(i);
		Integer pid=p.getId();
		Integer total= totalsNoFilter.get(sid.toString()+"_"+pid.toString());
		System.out.println("sid: "+sid.toString()+"     pid: "+pid.toString()+"       total: "+total.toString());
	}
}*/
//example of using cell query with filter run with category as 248
/*HashMap<String, Integer> totalsWithFilter=ListProductHelper.stateProductTotalForTopKWithFilter(248, 50, 0);
for(int j = 0; j < Rows.size(); j++){ 
	State s = Rows.get(j);
	Integer sid=s.getId();
	for(int i = 0; i < Products.size(); i++){
		Product p=Products.get(i);
		Integer pid=p.getId();
		Integer total= totalsWithFilter.get(sid.toString()+"_"+pid.toString());
		if(total==null){
			System.out.println("sid: "+sid.toString()+"     pid: "+pid.toString()+"       total: "+0);
		}else{
			System.out.println("sid: "+sid.toString()+"     pid: "+pid.toString()+"       total: "+total.toString());
		}
	}
}*/

int old_count=FindModifiedCellsHelper.getOldCount();
int new_count=FindModifiedCellsHelper.getNewCount();
//System.out.println("new count:"+new_count);
//if old count and new count is the same, don't do anything
//if different Run updateTempTables
//Call when you click run query or refresh button,
FindModifiedCellsHelper.updateTempTables(old_count);
/*
ArrayList<Total> m_cells=FindModifiedCellsHelper.getModifiedTotals();
for(int i=0 ; i<m_cells.size();i++){
	Total total=m_cells.get(i);
	System.out.println("sid: "+total.getSid().toString()+"     pid: "+total.getPid().toString()+"       total: "+total.getTotal().toString());
}
*/

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
		<input type="button" onClick="refresh('<%= CategoryFilter %>');" value="Refresh Table">
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
				<td align="center" id="c<%= Products.get(i).getId() %>"><B><%= Products.get(i).getName() %>(<font id="c<%= Products.get(i).getId()%>font" class="refresh" style="color:'black'"><font id="c<%= Products.get(i).getId()%>num"><%= Products.get(i).getTotal() %></font></font>)</B></th>
				<% } %>
			</tr>
		</thead> 
		<tbody>
			<% for(int i = 0; i < Rows.size(); i++){ 
				State s = Rows.get(i); %>
				<tr id="r<%= s.getId()%>">
					<th><%= s.getName() %>(<font id="r<%= s.getId()%>font" class="refresh" style="color:'black'"><font id="r<%= s.getId()%>num"><%= s.getTotal() %></font></font>)</th>
					<% for(int j = 0; j < Products.size(); j++){
						Product p = Products.get(j); %>
						<td style="color:'black'" class="refresh" align="center" id="r<%= s.getId()%>_c<%= p.getId()%>"><%= totals.get("" + s.getId() + "_" + p.getId())/*ListProductHelper.getStateProductTotal(s.getId(), p.getId())*/ %></td>
					<% } %>
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
