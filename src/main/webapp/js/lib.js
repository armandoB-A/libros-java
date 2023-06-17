var username = new URL(location.href).searchParams.get("username");
var user;

$(document).ready(function(){
	
	
	$(function () {
		$('[data-toggle="tooltip"]').tooltip();
	});
	
	
	getUsuarios().then(function(){
		
		$("#inicio-btn").attr("href","home.html?username=" + username);
				
	})
	
});


async function getUsuarios(){
	
	await $.ajax({
			type:"GET",
			dataType:"html",
			url:"./ServletUsuarioPedir",
			data: $.param({
				username: username,
			}),
			success: function(result){
				let parsedResult = JSON.parse(result);
				
				if (parsedResult != false){
					user = parsedResult;
				}
				else{
					console.log("Error recuperando los datos del usuario");
				}
			}
		});
}
