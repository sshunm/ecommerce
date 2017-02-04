<html>
<head>
<title>Product Registration</title>
<script src="../js/lib/jQuery.js"></script>
<script src="../js/registration.js"></script>
<link rel="stylesheet" href="../css/login.css">
</head>

<body>

	<h1 style="color: Black; font-weight: 900; text-align: center;">Adding
		Product</h1>
	<form action="javascript:registerProduct();">
		<div class="new_main_div productReg">
			<div id="Content_div" align="center">
				<br> <br>
				<table align="center" class="tables" id="tables">
					<tr>
						<td><input type="text" class="reg_input"
							placeholder="Product Name" id="productName" autofocus="autofocus"
							tabindex="1" required></td>
						<td><input type="text" class="reg_input"
							placeholder="Model Number" id="modelNumber" autofocus="autofocus"
							tabindex="2" required></td>
						<td><input class="reg_input" type="text"
							placeholder="processor" id="processor" autofocus="autofocus"
							tabindex="3" required></td>
					</tr>
					<tr>
						<td><input type="text" class="reg_input" placeholder="RAM"
							id="ram" autofocus="autofocus" tabindex="4" required></td>
						<td><input type="text" class="reg_input"
							placeholder="Hard Disk" id="hardDisk" autofocus="autofocus"
							tabindex="5" required></td>
						<td><input type="text" class="reg_input"
							placeholder="Availability" id="available"
							autofocus="autofocus" tabindex="6" required pattern="[0-9]{1,4}$"></td>
					<tr>
						<td><input class="reg_input" type="text"
							placeholder="Screen Size" id="screenSize" autofocus="autofocus"
							tabindex="7" required></td>
						<td><input type="text" class="reg_input" placeholder="os"
							id="os" tabindex="9" autofocus="autofocus" required></td>
						<td><input type="text" class="reg_input" placeholder="Prize"
							id="price" tabindex="10" autofocus="autofocus" required
							title="please enter valid prize"></td>
					</tr>
				</table>
				<br>
				<div class="button_div">
					<input type="submit" id="submit" class="submit_button"
						value="Save Product"> <input type="button" id="cancelOp"
						class="submit_button" value="Cancel">
				</div>
			</div>
		</div>
	</form>
</body>
</html>