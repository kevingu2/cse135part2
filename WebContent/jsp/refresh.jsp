<%@page
    import="java.util.List"
    import="java.util.ArrayList"
    import="org.json.*"
    import="helpers.*"%>
<%
	int old_count=FindModifiedCellsHelper.getOldCount();
	int new_count=FindModifiedCellsHelper.getNewCount();
	//System.out.println("" + old_count + " compared to " + new_count);
	if(old_count != new_count)
	{
		FindModifiedCellsHelper.updateTempTables(old_count);
		ArrayList<Total> cells = FindModifiedCellsHelper.getModifiedTotals();
		JSONArray result = new JSONArray();
		for(int i = 0; i < cells.size(); i++)
		{
			Total t = cells.get(i);
			String key = "r" + t.getSid() + "_c" + t.getPid();
			Object[] pair = {key, t.getTotal()};
			result.put(pair);
			//JSONArray a = new JSONArray(Arrays.asList(array));
		}
		out.print(result);
	}
/*
	ArrayList<Total> cells = FindModifiedCellsHelper.getModifiedTotals();
	JSONArray result = new JSONArray();
	for(int i = 0; i < cells.size(); i++)
	{
		Total t = cells.get(i);
		String key = "r" + t.getSid() + "_c" + t.getPid();
		Object[] pair = {key, t.getTotal()};
		result.put(pair);
		//JSONArray a = new JSONArray(Arrays.asList(array));
	}
	out.print(result);
*/
%>