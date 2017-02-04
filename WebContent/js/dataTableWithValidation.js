var array;

var modifyFlag = false;

var modifyProductId = null;

var cartArray = [];

var table;

function dataTableCall(dataSet){
	
	table = $('#example').DataTable( {
		data: dataSet,
		columns: [
		          { title: "Product Id" },
		          { title: "Product Name" },
		          { title: "Model Number" },
		          { title: "OS" },
		          { title: "RAM" },
		          { title: "Hard Disk" },
		          { title: "Processor" },
		          { title: "Screen Size" },
		          { title: "Available" },
		          { title: "Price" },
		          { title: "Action" }

		          ]
	} );
}


function productListAjaxCall(){
	
	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/ECommerce/myCart/getProductList?searchParam=" + $("#searchString").val(),
		success : function(dataSet) {

			$(".loader").hide();

			if( dataSet != null ){											

				array = dataSet;

				table.destroy();

				$('#example').empty();

				if( dataSet != null ){

					dataTableCall(dataSet);
					
				}else{

					dataTableCall([]);
				}
			}


		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$(".loader").hide();
		} 

	});
}

function callFunction(title){

	$.each(array, function( index, value ) {
		
		var selectOptionStart = "<select id='" + value[0] + "' style='margin-left:20px;'>";
		var selectOptionEnd = "</select>";

		if( value[0] == title ){
			var selectString = selectOptionStart;
			for( i = 1; i <= value[8]; i++){
				selectString = selectString + "<option value='" + i + "'>" + i  + "</option>";
			}
			selectString = selectString + selectOptionEnd;
			value[9] = "<button title='" + value[0] + "' class='submit_button top' onclick='deleteFromCart(this.title);'>Delete from cart</button>";
			value[8] = selectString;
			cartArray.push(value);
			alert(" Product added to cart. ");
		}
	});

}


function deleteProduct(title){
	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ECommerce/myCart/deleteProduct",
		data : JSON.stringify({
			"productId" : title
		}),
		success : function(data) {	
			alert(data);
		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$(".loader").hide();
		} 

	});

}

function callFavourite(title){

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ECommerce/myCart/insertFavourite",
		data : JSON.stringify({
			"productId" : title
		}),
		success : function(data) {	
			alert(data);		
		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$(".loader").hide();
		} 

	});

}

function deleteFavourite(className){

	$.ajax({
		type : "POST",
		contentType : "application/json",
		url : "/ECommerce/myCart/deleteFavourite",
		data : JSON.stringify({
			"productId" : className
		}),
		success : function(data) {	
			alert(data);		
		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) { 
			$(".loader").hide();
		} 

	});

}


function modifyProduct(title){

	modifyFlag =  true;

	sessionStorage.setItem( "modifyFlag", modifyFlag );

	sessionStorage.setItem( "modifyProductId", title );

	sessionStorage.setItem("tableRow", array);

	window.location.href = "/ECommerce/myCart/addRegister";		

	modifyFlag =  false;

	modifyProductId = null;

}

$(document).ready(function() {

	if( sessionUserType == "C"){
		$("#addProduct").hide();		
	}else if( sessionUserType == "V" ){
		$("#favouriteList").hide();
		$("#checkOut").hide();
		$("#addProduct").show();
		$("#search-form").hide();
	}else if( sessionUserType == "A" ){
		$("#addProduct").hide();
	}

	$("#logOutImg").click(function(){

		window.location.href="/ECommerce/myCart/logOut";
	})

	var prevCart = JSON.parse(sessionStorage.getItem("cartArray"));

	if( prevCart != null && prevCart.length > 0 ){

		cartArray = prevCart;

	}

	dataTableCall([]);

	$("#addProduct").click(function(){	

		sessionStorage.setItem( "modifyFlag", modifyFlag );

		sessionStorage.setItem( "modifyProductId", modifyProductId );

		window.location.href = "/ECommerce/myCart/addRegister";		

		modifyFlag =  false;

		modifyProductId = null;
	})

	$("#checkOut").click(function(){

		sessionStorage.setItem( "cartArray", JSON.stringify(cartArray) );
		window.location.href = "/ECommerce/myCart/checkOutTable";

	})

	$("#manageProfile").click(function(){

		sessionStorage.setItem( "modifyUserFlag", true );
		window.location.href = "/ECommerce/myCart/newUser";
	})


	$("#favouriteList").click(function(){

		$(".loader").show();

		$.ajax({
			type : "GET",
			contentType : "application/json",
			url : "/ECommerce/myCart/getFavouriteList",
			success : function(dataSet) {

				$(".loader").hide();

				if( dataSet != null ){

					array = dataSet;

					table.destroy();

					$('#example').empty();

					if( dataSet != null ){

						dataTableCall(dataSet);
					}else{

						dataTableCall([]);
					}
				}


			},				
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
				$(".loader").hide();
			} 

		});

	})

	$("#viewOrders").click(function(){

		window.location.href="/ECommerce/myCart/viewOrdersPage";

	})


	$("#submitSearch").click(function() {		

		$(".loader").show();

		productListAjaxCall();
	});	



	$(".loader").show();


	productListAjaxCall();

})




