<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<title>View Orders</title>

<script src="../js/lib/jQuery.js"></script>

<script src="../js/lib/JQuery-dataTable.js"></script>

<script>
	var sessionUserType = "${userType}";
</script>

<script src="../js/viewOrders.js"></script>

<link rel="stylesheet" href="../css/dataTableWithValidation.css" />

<link rel="stylesheet" href="../css/lib/JQuery-dataTable-UI.css">

<link rel="stylesheet" href="../css/loader.css">

</head>

<body>

	<div class="loader"></div>

	<table>

		<tr>

			<td>

				<div id="search-form" class="adminPrivilege">
					<div class="form-container" id="formContainer">
						<input id="searchText" type="text" class="search-field" value=""
							placeholder="Search for customer or vendor" />
						<div class="submit-container">
							<input id="submitAdminSearch" type="submit" value=""
								class="submit" />
						</div>
					</div>
				</div>

			</td>

			<td>

				<div class="adminPrivilege">
					<input type="radio" name="userType" value="C" checked>Customer<br>
					<input type="radio" name="userType" value="V">Vendor
				</div>

			</td>

			<td><input type="button" id="export" class="submit_button"
				value="Export"></td>
			<td><input type="button" id="backToSearch" class="submit_button"
				value="Back to products"></td>
			<td><img id="viewOrderslogOutImg" src="../css/images/logout.png"
				title="Log out"></td>

		</tr>

	</table>



	<br>
	<br>

	<div id="tableDiv">
		<table id="example"></table>
	</div>

</body>
</html>
