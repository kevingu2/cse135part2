/**
 * 
 */
function validate(id, actionType)
{
	var name = document.getElementById("name").value;
	var role = document.getElementById("role").value;
	var age = document.getElementById("age").value;
	var state = document.getElementById("state").value;
	var error = false;

	if(name == '')
	{
		error = true;
		if(document.getElementById("badName") == null)
		{
			var badName = document.createElement('tr');
			badName.id = 'badName';
			badName.innerHTML = "<td></td><td><font color='red'>Name not provided</font></td>";
			var nameChild = document.getElementById('nameTag');
			nameChild.parentNode.insertBefore(badName,nameChild);
		}
	}
	else if(document.getElementById("badName") != null)
	{
		var child = document.getElementById("badName");
		child.parentNode.removeChild(child);
	}
	
	var xmlHttp;
	xmlHttp=new XMLHttpRequest();
	var responseHandler = function()
	{
		if(xmlHttp.readyState==4)
		{ 
		   if(xmlHttp.responseText.trim()=="true"){
				if(document.getElementById("existName") == null)
				{
					var badName = document.createElement('tr');
					badName.id = 'existName';
					badName.innerHTML = "<td></td><td><font color='red'>This user name is already taken</font></td>";
					var nameChild = document.getElementById('nameTag');
					nameChild.parentNode.insertBefore(badName,nameChild);
				}
		   }
		   else if(document.getElementById("existName") != null)
		   {
			   var child = document.getElementById("existName");
			   child.parentNode.removeChild(child);
				if(!error)
				{
					alert("Form filled successfully.")
				}
		   }
		}
	}

	xmlHttp.onreadystatechange = responseHandler ;
	xmlHttp.open("GET","existUser?name="+name,true);
	xmlHttp.send(null);
	
	if(role == '')
	{
		error = true;
		if(document.getElementById("badRole") == null)
		{
			var badRole = document.createElement('tr');
			badRole.id = 'badRole';
			badRole.innerHTML = "<td></td><td><font color='red'>Role not provided</font></td>";
			var roleChild = document.getElementById('roleTag');
			roleChild.parentNode.insertBefore(badRole,roleChild);
		}
	}
	else if(document.getElementById("badRole") != null)
	{
		var child = document.getElementById('badRole');
		child.parentNode.removeChild(child);
	}
	if(age == '')
	{
		error = true;
		if(document.getElementById("badAge") == null)
		{
			var badAge = document.createElement('tr');
			badAge.id = 'badAge';
			badAge.innerHTML = "<td></td><td><font color='red'>Age not provided</font></td>";
			var ageChild = document.getElementById('ageTag');
			ageChild.parentNode.insertBefore(badAge,ageChild);
		}
	}
	else if(document.getElementById("badAge") != null)
	{
		var child = document.getElementById('badAge');
		child.parentNode.removeChild(child);
	}
	if(state == '')
	{
		error = true;
		if(document.getElementById("badState") == null)
		{
			var badState = document.createElement('tr');
			badState.id = 'badState';
			badState.innerHTML = "<td></td><td><font color='red'>State not provided</font></td>";
			var stateChild = document.getElementById('stateTag');
			stateChild.parentNode.insertBefore(badState,stateChild);
		}
	}
	else if(document.getElementById("badState") != null)
	{
		var child = document.getElementById('badState');
		child.parentNode.removeChild(child);
	}
	
}