$(document).ready(function() {

	$("#register").click(function() {
		window.location.href = "/ECommerce/myCart/newUser"	
	});

	$("#submit").click(function() {

		if( $("#userName").val() == "" ){

			alert("Enter Username");
		}else if( $("#password").val() == "" ){

			alert("Enter password");
		}else{

			$(".loader").show();
			$.ajax({
				type : "POST",
				contentType : "application/json",
				url : "/ECommerce/myCart/checkUserWithValidation",
				data : JSON.stringify({
					"userName" : $("#userName").val(),
					"userPassword" : $("#password").val()
				}),
				success : function(data) {

					$(".loader").hide();

					if( data == 1 ){

						window.location.href = "/ECommerce/myCart/dataTable";

					}else if( data == 0 ){

						alert("Invalid Credentials");

					}else if( data == -1 ){

						alert("User doesn't exist. Please register");

					}
				},				
				error: function(XMLHttpRequest, textStatus, errorThrown) { 
					$(".loader").hide();
				} 

			});

		}

	});


});