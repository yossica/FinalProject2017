<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Solution</title>
<script>
	function onchangeContractServices(){
		var e = document.getElementById("contractServices");
		var value = e.options[e.selectedIndex].value;
		if (value == 1){
			document.getElementById("period").style.display = "block";
			document.getElementById("payment").style.display = "none";
			document.getElementById("tax").style.display = "none";
		}else if (value == 2 || value == 4){
			document.getElementById("period").style.display = "none";
			document.getElementById("payment").style.display = "none";
			document.getElementById("tax").style.display = "block";
		}else if (value == 3){
			document.getElementById("period").style.display = "none";
			document.getElementById("payment").style.display = "block";
			document.getElementById("tax").style.display = "block";
		}
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Create Invoice</h1>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2">Invoice Date</div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;">
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Client</div>
					<div class="col-md-5">
						<select class="form-control">
							<option>1</option>
							<option>2</option>
						</select>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Contract Services</div>
					<div class="col-md-5">
						<select class="form-control" onchange="javascript:onchangeContractServices()" id="contractServices">
							<option selected disabled>Select</option>
							<option value="1">Professional Service</option>
							<option value="2">Head Hunter</option>
							<option value="3">Training</option>
							<option value="4">Other</option>
						</select>
					</div>
				</div>
			</div>
			<div id="period" class="col-md-10" style="margin-top: 10px; display: none;">
				<div class="row">
					<div class="col-md-2">Period</div>
					<div class="col-md-1">Month</div>
					<div class="col-md-4">
						<select class="form-control">
							<option>1</option>
							<option>2</option>
						</select>
					</div>
					<div class="col-md-1">Year</div>
					<div class="col-md-4">
						<select class="form-control">
							<option>1</option>
							<option>2</option>
						</select>
					</div>
				</div>
			</div>
			<div id="payment" class="col-md-10" style="margin-top: 10px; display: none;">
				<div class="row">
					<div class="col-md-2">Payment</div>
					<div class="col-md-5">
						<div class="radio">
							<label>
								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>DP
						    </label>
						    &nbsp;
						    <label>
								<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">Settlement
						    </label>
						</div>
					</div>
				</div>
			</div>
			<div id="tax" class="col-md-10" style="margin-top: 10px;  display: none;">
				<div class="row">
					<div class="col-md-2">Tax</div>
					<div class="col-md-5">
						<div class="radio">
							<label>
								<input type="radio" name="optionsRadios" id="optionsRadios1" value="option1" checked>Include
						    </label>
						    &nbsp;
						    <label>
								<input type="radio" name="optionsRadios" id="optionsRadios2" value="option2">Exclude
						    </label>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Invoice Notes</div>
					<div class="col-md-5">
						<textarea class="form-control" rows="3"></textarea>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
				<button type="button" class="btn btn-primary">Filter</button>
			</div>
		</div>
	</div>
</body>
</html>
