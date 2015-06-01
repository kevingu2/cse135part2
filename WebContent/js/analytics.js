/**
 * 
 */
function refresh()
{
	alert("Please add refresh code to analytics.js.\nThis button currently toggles the State names between red and black.");
	for(var i = 1; i <= 50; i++)
	{
		var row = document.getElementById('r' + i);
		if(row != null)
		{
			if(row.getElementsByTagName("th")[0].style["color"] != "red")
			{
				row.getElementsByTagName("th")[0].style["color"] = "red";
			}
			else
			{
				row.getElementsByTagName("th")[0].style["color"] = "black";
			}
		}
	}
}