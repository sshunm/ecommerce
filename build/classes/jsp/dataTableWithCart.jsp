<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Check out</title>

<script src="../js/lib/jQuery.js"></script>

<script src="../js/lib/JQuery-dataTable.js"></script>

<script src="../js/lib/JQuery-UI.js"></script>

<script>
	var sessionUserType = "${userType}";
</script>

<script src="../js/dataTableWithCart.js"></script>

<link rel="stylesheet" href="../css/lib/JQuery-css.css">

<link rel="stylesheet" href="../css/lib/JQuery-dataTable-UI.css">

<link rel="stylesheet" href="../css/loader.css">

<link rel="stylesheet" href="../css/checkout.css">

</head>

<body>

	<div class="loader"></div>

	<table>


		<tr>
			<td><input type="button" id="back" class="submit_button"
				value="Back to products"></td>
			<td><img id="cartLogOutImg" src="../css/images/logout.png">

			</td>

		</tr>

	</table>


	<br>
	<br>
	<br>

	<div id="tableDiv">
		<table id="example"></table>
	</div>

	<br>
	<br>

	<form action="javascript:purchaseProduct();">

		<div id="Address">Shipping address</div>
		<div id="panel">
			<br /> <input type="text" class="reg_input" placeholder="Address"
				size="50px" id="address" autofocus="autofocus" tabindex="1" required>
			<br /> <br /> <input type="text" class="reg_input" placeholder="City"
				id="city" autofocus="autofocus" tabindex="2" required>
			&nbsp&nbsp&nbsp <input class="reg_input" type="text"
				placeholder="Country" id="country" autofocus="autofocus"
				tabindex="3" required> <br /> <br /> <input
				class="reg_input" type="text" placeholder="Postal Code" id="zipCode"
				tabindex="4" autofocus="autofocus" required pattern="[0-9]{5,6}$"
				title="Please enter only valid postalpin"> &nbsp&nbsp&nbsp <input
				type="text" class="reg_input" placeholder="Phone Number"
				id="telephone" tabindex="5" autofocus="autofocus" required
				pattern="[0-9]{10,11}$" title="please enter valid number">
		</div>
		<br>
		<br>
		<div id="Payment">Payment</div>
		<div id="panel1">
			<input type="radio" checked name="payment" value="COD">Cash
			on Delivery &nbsp&nbsp<input type="radio" name="payment" value="Card">Credit
			card
			<div id="card_details">
				<br /> <input type="text" placeholder="Credit card number"
					id="cardNumber" size="27px" tabindex="1" pattern="[0-9]{16}$"
					title="Please enter valid card number" maxlength="16"> <br />
				<br /> <input id="cardExpDate" placeholder="Card expiry date"
					name="mydate" />&nbsp&nbsp&nbsp&nbsp <input type="text"
					placeholder="cvv" id="cvv" size="5px"
					title="Please enter valid cvv number" pattern="[0-9]{3}$"
					tabindex="2" maxlength="3">
			</div>
		</div>

		<input type="submit" width=50px id="purchase"
			class="submit_button purchase" value="Purchase">

	</form>

</body>
</html>