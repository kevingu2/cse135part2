/**
 * 
 */
function refresh(count)
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
	/*
	for(var i = 0; i < redList.length; i++)
	{
		redList[i].style["color"] = "red";
	}
	*/
	
	var xmlHttp;
	xmlHttp=new XMLHttpRequest();
	var responseHandler = function()
	{
		if(xmlHttp.readyState==4)
		{
			if(xmlHttp.responseText.trim()=="Hello World!")
			{
				alert("Hello World!");
			}
		}
	}
	
	xmlHttp.onreadystatechange = responseHandler;
	xmlHttp.open("GET","refresh",true);
	xmlHttp.send(null);
}