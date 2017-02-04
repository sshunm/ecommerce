/**
 * 
 */

var userType = "C";

var modifyProductFlag = sessionStorage.getItem( "modifyFlag" );

var modifyUserFlag = sessionStorage.getItem( "modifyUserFlag" );

var modifyProductId = sessionStorage.getItem( "modifyProductId" );

function registerProduct(){

	var urlProduct = "/ECommerce/myCart/insertProduct";

	if( modifyProductFlag == "true" ){
		urlProduct = "/ECommerce/myCart/modifyProduct";
	}else{
		modifyProductId = null;
	}

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : urlProduct,
		data : JSON.stringify({
			"productId" : modifyProductId,
			"productName" : $("#productName").val(),
			"modelNumber" : $("#modelNumber").val(),
			"os" : $("#os").val(),
			"hardDisk" : $("#hardDisk").val(),
			"processor" : $("#processor").val(),
			"screenSize" : $("#screenSize").val(),
			"available" : $("#available").val(),
			"ram" : $("#ram").val(),
			"price": $("#price").val()
		}),
		success : function(data) {
			alert(data);	
			window.location.href = "/ECommerce/myCart/dataTable"
		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		} 

	});



}

function cancelOperation(url){

	window.location.href = url;
}

function modifyUser(){	


	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/ECommerce/myCart/fetchUserDetails",
		success : function(data) {
			if( data.userType == "V"){
				vendor_form();
			}else{
				customer_form();
			}
			$("#userName").val(data.userName),
			$("#password").val(data.userPassword),
			$("#firstName").val(data.firstName),
			$("#lastName").val(data.lastName),
			$("#email").val(data.email),
			$("#addressLine").val(data.addressLine),
			$("#city").val(data.city),
			$("#state").val(data.state),
			$("#country").val(data.country),
			$("#telephone").val(data.telephone),
			$("#zipCode").val(data.zipCode),
			$("#companyName").val(data.companyName),
			$("#companyNumber").val(data.companyNumber)
			sessionStorage.setItem( "modifyUserFlag", false );
		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$(".loader").hide();
			sessionStorage.setItem( "modifyUserFlag", false );
		} 

	});

	$("#userName").prop("readonly", true);
	$("#password").prop("readonly", true);

}

function modifyProduct(){

	var tabArray = sessionStorage.getItem( "tableRow" ).split(",");	 

	for( var i = 0; i < tabArray.length; i++ ){

		if( tabArray[0] == modifyProductId ){
			$("#productName").val(tabArray[1]);
			$("#modelNumber").val(tabArray[2]);
			$("#os").val(tabArray[3]);
			$("#ram").val(tabArray[4]);
			$("#hardDisk").val(tabArray[5]);
			$("#processor").val(tabArray[6]);
			$("#screenSize").val(tabArray[7]);
			$("#available").val(tabArray[8]);
			$("#price").val(tabArray[9]);
			return false;
		}

	} 

}

function clickSubmit(){

	var url = "/ECommerce/myCart/insertUser";

	if( modifyUserFlag == "true" )
		url = "/ECommerce/myCart/modifyUser";

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : url,
		data : JSON.stringify({
			"userName" : $("#userName").val(),
			"userPassword" : $("#password").val(),
			"firstName" : $("#firstName").val(),
			"lastName" : $("#lastName").val(),
			"email" : $("#email").val(),
			"addressLine" : $("#addressLine").val(),
			"city" : $("#city").val(),
			"state" : $("#state").val(),
			"country" : $("#country").val(),
			"telephone" : $("#telephone").val(),
			"zipCode" : $("#zipCode").val(),
			"companyName" : $("#companyName").val(),
			"companyNumber" : $("#companyNumber").val(),
			"userType" : userType
		}),
		success : function(data) {

			alert(data);
			
			if( data == "User successfully added"){				
				window.location.href = "/ECommerce/myCart/loginValid";
			}			
		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$(".loader").hide();
		} 

	});
}

$(document).ready(function(){

	$(".reg_input").val("");

	$('#vendor_extra').hide();

	if( modifyProductFlag == "true" ) {		
		modifyProduct();		
	}

	if( modifyUserFlag == "true"){		
		$("#submitUser").after("<input type='button' id='cancelOp' class='submit_button' value='Cancel'>")
		modifyUser();
	}

	$("#cancelOp").click(function(){

		cancelOperation("/ECommerce/myCart/dataTable");
	})	

});

function vendor_form() {
	if( modifyUserFlag == "true") {
		$("#customerReg").prop("onclick", null);
	}
	$('#vendor_extra').show();
	$("#companyNumber").prop('required',true);
	$("#companyName").prop('required',true);
	userType = "V";
}


function customer_form() {
	if( modifyUserFlag == "true") {
		$("#vendorReg").prop("onclick", null);
	}
	$('#vendor_extra').hide();
	$("#companyNumber").prop('required',false);
	$("#companyName").prop('required',false);
	userType = "C";
}
