/**
 * 
 */
function refresh(CategoryFilter)
{
	//alert("Please add refresh code to analytics.js.\nThis button currently toggles the State names between red and black.");
	/*
	for(var i = 1; i <= 50; i++)
	{
		var row = document.getElementById('r' + i + 'font');
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
		if(row != null)
		{
			if(row.style["color"] != "red")
			{
				row.style["color"] = "red";
			}
			else
			{
				row.style["color"] = "black";
			}
		}
	}
	if(document.getElementById("c436font").style["color"] != "red")
	{
		document.getElementById("c436font").style["color"] = "red";
	}
	else
	{
		document.getElementById("c436font").style["color"] = "black"
	}
	*/
	
	// Get rid of all previous red text TODO
	var elementList = document.getElementsByClassName("refresh");
	for(var i = 0; i < elementList.length; i++)
	{
		elementList[i].style["color"] = "black";
	}
	
	var xmlHttp;
	xmlHttp=new XMLHttpRequest();
	var responseHandler = function()
	{
		if(xmlHttp.readyState==4)
		{
			var response = eval(xmlHttp.responseText);
			for(var i = 0; i < response.length; i++)
			{
				var idCell = response[i][0];
				var idc = idCell.split("_")[1];
				var idr = idCell.split("_")[0];
				var total = response[i][1];
				var cell = document.getElementById("" + idCell);
				var rowFont = document.getElementById("" + idr + "font");
				var rowNum = document.getElementById("" + idr + "num");
				var colFont = document.getElementById("" + idc + "font");
				var colNum = document.getElementById("" + idc + "num");
				//alert("cell: " + idCell + " total: " + total);
				if(cell != null)
				{
					cell.style["color"] = "red";
					cell.innerHTML = parseInt(cell.innerHTML) + parseInt(total);
					colFont.style["color"] = "red";
					colNum.innerHTML = parseInt(colNum.innerHTML) + parseInt(total);
				}
				if(cell != null || CategoryFilter == "All Categories")
				{
					rowFont.style["color"] = "red";
					rowNum.innerHTML = parseInt(rowNum.innerHTML) + parseInt(total);
				}
			}
		}
	}
		
	xmlHttp.onreadystatechange = responseHandler;
	xmlHttp.open("GET","refresh",true);
	xmlHttp.send(null);
}