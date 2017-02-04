<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Registration Form Design</title>

<script src="../js/lib/jQuery.js"></script>

<script src="../js/lib/JQuery-dataTable.js"></script>

<script>var sessionUserType = "${userType}";</script>

<script src="../js/dataTableWithValidation.js"></script>

<link rel="stylesheet" href="../css/dataTableWithValidation.css" />

<link rel="stylesheet" href="../css/lib/JQuery-dataTable-UI.css">

<link rel="stylesheet" href="../css/loader.css" media="screen"
	type="text/css" />

</head>

<body>

	<div class="loader"></div>

	<table>


		<tr>

			<td>

				<div id="search-form">
					<div class="form-container" id="formContainer">
						<input id="searchString" type="text" class="search-field" value=""
							placeholder="Search product name" />
						<div class="submit-container">
							<input id="submitSearch" type="submit" value="" class="submit" />
						</div>
					</div>
				</div>

			</td>

			<td><input type="button" id="addProduct" class="submit_button"
				value="Add product"></td>
			<td><input type="button" id="favouriteList"
				class="submit_button" value="Wish List"></td>
			<td><input type="button" id="checkOut" class="submit_button"
				value="Check Out"></td>
			<td><input type="button" id="viewOrders" class="submit_button"
				value="View Orders"></td>
			<td><input type="button" id="manageProfile"
				class="submit_button" value="Manage Profile"></td>

			<td><img id="logOutImg" src="../css/images/logout.png"></td>

		</tr>

	</table>

	<br>
	<br>


	<div id="tableDiv">
		<table id="example"></table>
	</div>

</body>
</html>
