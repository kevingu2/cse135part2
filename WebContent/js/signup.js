/**
 * 
 */
function validateName()
{
	alert("Last Time function called");
	var xmlHttp;
  xmlHttp=new XMLHttpRequest();

  var responseHandler = function()
   {
    if(xmlHttp.readyState==4)
     { document.getElementById("existUser").innerHTML = "You last typed on " + xmlHttp.responseText; }
   }

  xmlHttp.onreadystatechange = responseHandler ;

  xmlHttp.open("GET","date",true);
  xmlHttp.send(null);
}
