<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script src="../js/lib/jQuery.js"></script>
<script src="../js/registration.js"></script>
<link rel="stylesheet" href="../css/login.css">

<title>Account Registration</title>
</head>

<body class="new_body">
	<h1 style="color: Black; font-weight: 900; align: center;">Account</h1>
	<form action="javascript:clickSubmit();" autocomplete="off">
		<div class="new_main_div">
			<div>
				<ul class="tab">
					<li><a class="tablinks" id="customerReg"
						onclick="customer_form()">CUSTOMER</a></li>
					<li><a class="tablinks" id="vendorReg" onclick="vendor_form()">VENDOR</a></li>
				</ul>
				<div id="Content_div" align="center">
					<table align="center" class="tables" id="tables">
						<tr>
							<td><input type="text" class="reg_input"
								placeholder="User Name" id="userName" autofocus="autofocus"
								tabindex="1" value="" required></td>
							<td><input type="text" class="reg_input"
								placeholder="First Name" id="firstName" autofocus="autofocus"
								value="" tabindex="2"></td>
							<td><input class="reg_input" type="text"
								placeholder="Last Name" id="lastName" autofocus="autofocus"
								value="" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="text" title="Please enter a valid email id"
								class="reg_input" value="" placeholder="Email" id="email"
								autofocus="autofocus" tabindex="4" required="required"
								pattern="[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]{2,}\.[A-Za-z]{2,3}$">
							</td>
							<td><input type="text" class="reg_input"
								placeholder="Address" id="addressLine" autofocus="autofocus"
								tabindex="5" value=""></td>
							<td><input type="text" class="reg_input" placeholder="City"
								id="city" autofocus="autofocus" tabindex="6" value=""></td>
						<tr>
							<td><input class="reg_input" type="text"
								placeholder="Country" id="country" autofocus="autofocus"
								tabindex="7" value=""></td>
							<td><input class="reg_input" type="text"
								placeholder="Postal Code" id="zipCode" tabindex="8"
								autofocus="autofocus" required pattern="[0-9]{5,6}$" value=""
								title="Please enter only valid postalpin"></td>
							<td><input type="text" class="reg_input"
								placeholder="Phone Number" id="telephone" tabindex="9"
								autofocus="autofocus" required pattern="[0-9]{10,11}$"
								title="please enter valid number"></td>
						</tr>
						<tr>
							<td><input class="reg_input" type="password"
								placeholder="Password" id="password" tabindex="10"
								autofocus="autofocus" value="" required></td>
							<td><input class="reg_input" type="text" placeholder="State"
								id="state" name="state" tabindex="11" value=""
								autofocus="autofocus"></td>
						</tr>
					</table>
					<div id="vendor_extra">
						<table>
							<tr>
								<td><input type="text" id="companyName" class="reg_input"
									placeholder="Company Name" autofocus="autofocus" tabindex="12"></td>
								<td><input class="reg_input" id="companyNumber" type="text"
									placeholder="Company Number" autofocus="autofocus"
									tabindex="13"></td>
							</tr>
						</table>
					</div>
					<br>
					<div class="button_div">
						<input type="submit" id="submitUser" class="submit_button">
						Already have an account<a href="/ECommerce/myCart/loginValid">Sign
							in</a>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>