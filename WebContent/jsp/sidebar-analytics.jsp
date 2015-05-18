<%@page
    import="java.util.List"
    import="helpers.*"%>
    
<div class="panel panel-default">
	<div class="panel-body">
		<div class="bottom-nav">
            <h4> Options </h4>
            <% 	List<CategoryWithCount> categories = CategoriesHelper.listCategories(); 
            	String RowType = request.getParameter("RowType");
                String OrderBy = request.getParameter("OrderBy");
                String CategoryFilter = request.getParameter("CategoryFilter");
            %>
<% if(request.getParameter("Hide")==null){ %>
            <form action="analytics" method="post">
            <input type="hidden" name="Run" value="Run"> 
            <b>List By:</b>
            <select name="RowType" id="RowType">
            	<option value="Customers" <% if(RowType!=null && RowType.equals("Customers")){ %>selected<% } %>>Customers</option>
            	<option value="States" <% if(RowType!=null && RowType.equals("States")){ %>selected<% } %>>States</option>
            </select>
            <br>
            <b>Order By:</b>
            <select name="OrderBy" id="OrderBy">
            	<option value="Alphabetical" <% if(OrderBy!=null && OrderBy.equals("Alphabetical")){ %>selected<% } %>>Alphabetical</option>
            	<option value="Top-K" <% if(OrderBy!=null && OrderBy.equals("Top-K")){ %>selected<% } %>>Top-K</option>
            </select>
            <br>
            <b>Category Filter:</b>
            <select name="CategoryFilter" id="CategoryFilter">
            	<option value="All Categories">All Categories</option>
            	<%for(CategoryWithCount c : categories){ %>
            	<option value="<%= c.getId() %>" <% if(CategoryFilter!=null && CategoryFilter.equals(""+c.getId())){ %>selected<% } %>><%= c.getName() %></option>
            	<% } %>
            </select>
            <input type="submit" value="Run Query">
            </form>
<% }else{ %>
			<h5>Please reset this page to input another query.</h5>
			<form action="analytics" method="post">
			<input type="submit" value="Reset">
			</form>
<% } %>
            <!-- Put your part 2 code here -->
		</div>
	</div>
</div>