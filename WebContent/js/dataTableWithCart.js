var table = null;

var deleteFlag = false;

var purchaseFlag = false;

var sessionArray = JSON.parse(sessionStorage.getItem("cartArray"));

var newCartArray = [];

var paymentType = "Cash on Delivery";

function deleteFromCart(className){

	deleteFlag = true;

	$.each(sessionArray, function( index, value ) {		
		if( ! ( value[0] == className ) ){			
			newCartArray.push(value);
		}		
	})	

	alert( "Product deleted from cart" );

	sessionStorage.setItem( "cartArray", JSON.stringify(newCartArray) );

	window.location.href = "/ECommerce/myCart/checkOutTable";
}

function purchaseProduct(){

	var productId = [];

	purchaseFlag = true;

	sessionArray = JSON.parse(sessionStorage.getItem("cartArray"));

	if( sessionArray != null && sessionArray.length > 0 ){

		$.each(sessionArray, function( index, value ) {
			productId.push(value[0]+":"+$('#'+value[0]).val());	
		})

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/ECommerce/myCart/orderConfirmation",
			data : JSON.stringify({"address" : $("#address").val() + "," +  $("#city").val() + "," +  $("#country").val() + "," +  $("#zipCode").val(),
				"phoneNumber" : $("#telephone").val(),
				"paymentType" : paymentType,
				"products" : productId }),
				success : function(dataURL) {					 
					window.location = "/ECommerce/myCart/downloadPdfFile?url=" + dataURL + "&fileType=pdf" ;
					newCartArray = [];
				},				
				error: function(XMLHttpRequest, textStatus, errorThrown) { 
					$(".loader").hide();
				}

		});


	}else{

		alert( " You got no products to buy in cart. Please choose a product.. ");
	}

}

$(document).ready(function() {


	$("#cartLogOutImg").click(function(){

		window.location.href="/ECommerce/myCart/logOut";
	})
	
	$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/ECommerce/myCart/fetchUserDetails",
			success : function(data) {		
				$("#address").val(data.addressLine);		
				$("#city").val(data.city);
				$("#country").val(data.country);
				$("#zipCode").val(data.zipCode);
				$("#telephone").val(data.telephone);
			},				
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
				$(".loader").hide();
			} 

		});


	$("input[type='radio']").change(function(){

		if( this.value == "Card" ){		
			$("#card_details").slideToggle("slow");
			$("#cardNumber").prop("required", true);
			$("#cardExpDate").prop("required", true);
			$("#cvv").prop("required", true);
			paymentType = "Credit card";
		}else if( this.value == "COD" ){
			$("#card_details").hide();
			$("#cardNumber").prop("required", false);		
			$("#cardExpDate").prop("required", false);
			$("#cvv").prop("required", false);
			paymentType = "Cash on Delivery";
		}
	})


	$("#cardExpDate").datepicker({minDate: '2'});

	if( sessionUserType == "A"){
		$("#purchase").hide();
		$("form").hide()
	}

	if( table != null ){		
		table.destroy();	
		$('#example').empty();
	} 

	if( sessionArray != null ){

		table = $('#example').DataTable( {
			data: sessionArray,			      
			columns: [
			          { title: "Product Id" },
			          { title: "Product Name" },
			          { title: "Model Number" },
			          { title: "OS" },
			          { title: "RAM" },
			          { title: "Hard Disk" },
			          { title: "Processor" },
			          { title: "Screen Size" },
			          { title: "No. of Items" },
			          { title: "Action" }

			          ]
		} );
	}else{

		table = $('#example').DataTable( {
			data: [],	
			columns: [
			          { title: "Product Id" },
			          { title: "Product Name" },
			          { title: "Model Number" },
			          { title: "OS" },
			          { title: "RAM" },
			          { title: "Hard Disk" },
			          { title: "Processor" },
			          { title: "Screen Size" },
			          { title: "No. of Items" },
			          { title: "Action" }

			          ]
		} );
	}


	$("#back").click(function(){

		if( deleteFlag || purchaseFlag ){			
			sessionStorage.setItem( "cartArray", JSON.stringify(newCartArray) );
		}else{
			sessionStorage.setItem( "cartArray", JSON.stringify(sessionArray) );
		}



		window.location.href = "/ECommerce/myCart/dataTable";


	})


});



