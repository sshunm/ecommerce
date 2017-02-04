
var table;

function cancelOrder(className){

	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/ECommerce/myCart/cancelOrder?orderId=" + className,
		success : function(dataSet) {

			alert(dataSet);
			
			window.location.href = "/ECommerce/myCart/viewOrders";


		},				
		error: function(XMLHttpRequest, textStatus, errorThrown) {
		} 

	});


}

function dataTableCall(dataSet){

		table = $('#example').DataTable( {
			data: dataSet,
			columns: [
			          { title: "Order Number" },
			          { title: "Product Name" },
			          { title: "Order Date" },
			          { title: "Delivery Date" },
			          { title: "Price"},
			          { title: "No. of Items"},
			          { title: "Order Status" },	
			          { title: "Action" }					               					                  
			          ]
		} );
}



$(document).ready(function() {

	if( sessionUserType == "A"){
		$(".adminPrivilege").show();
	}

	$("#viewOrderslogOutImg").click(function(){

		window.location.href="/ECommerce/myCart/logOut";
	})

	$("#export").click(function(){

		var userName = "";
		var companyName = "";

		if( $('input[name="userType"]:checked').val() == "C" ){
			userName = $("#searchText").val();
		}else if( $('input[name="userType"]:checked').val() == "V" ){
			companyName = $("#searchText").val();
		}

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/ECommerce/myCart/exportReport",
			data : JSON.stringify({
				"userName" : userName,
				"companyName" : companyName
			}),
			success : function(dataURL) {					 
				window.location = "/ECommerce/myCart/downloadPdfFile?url=" + dataURL + "&fileType=pdf" ;
			},				
			error: function(XMLHttpRequest, textStatus, errorThrown) { 
				$(".loader").hide();
			} 

		});

	});

	$("#submitAdminSearch").click(function(){

		$(".loader").show();

		var userName = "";
		var companyName = "";

		if( $('input[name="userType"]:checked').val() == "C" ){
			userName = $("#searchText").val();
		}else if( $('input[name="userType"]:checked').val() == "V" ){
			companyName = $("#searchText").val();
		}

		$.ajax({
			type : "POST",
			contentType : "application/json",
			url : "/ECommerce/myCart/generateReport",
			data : JSON.stringify({
				"userName" : userName,
				"companyName" : companyName
			}),	
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







	});

	dataTableCall([]);

	$(".loader").show();


	$.ajax({
		type : "GET",
		contentType : "application/json",
		url : "/ECommerce/myCart/viewOrders",
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


	$("#backToSearch").click(function(){

		window.location.href = "/ECommerce/myCart/dataTable";


	})

})




