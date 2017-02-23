<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Solution</title>
<script>
	function getContractServices(){
		var e = document.getElementById("contractServices");
		var value = e.options[e.selectedIndex].value;
		return value;
	}
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function flyToNextPage(){
		var task;
		var error = false;
		
		//Check Error Pertama
		if (document.getElementById('invoiceDate').value == ''){
			error = true;
		}else if (document.getElementById('contractServices').value == 'Select'){
			error = true;
		}else if (document.getElementById('client').value == 'Select'){
			error = true;
		}
		
		if (getContractServices() == 1){
			task = 'formInvoicePS';
			//Error Checking Kedua
			if (document.getElementById('periodMonth').value == 'Select'){
				error = true;
			}else if (document.getElementById('periodYear').value == 'Select'){
				error = true;
			}
		}else if (getContractServices() == 2 || getContractServices() == 4){
			task = 'formInvoiceHH';
		}else if (getContractServices() == 3){
			var payment = document.querySelector('input[name = "paymentRadio"]:checked').value;
			if(payment == "option1"){
				task = 'createInvoiceTRDP';
			}else {
				task = 'createInvoiceTRST';
			}
		}
		
		//Alert kalau Error disini
		if (error){
			alert('Error ya...')
		}else {
			alert('Tidak Ada Error... Lanjut!');
			document.forms[1].task.value = task;
			document.forms[1].submit();
		}
	}
	function onchangeContractServices(){
		if (getContractServices() == 1){
			document.getElementById("period").style.display = "block";
			document.getElementById("payment").style.display = "none";
			document.getElementById("tax").style.display = "none";
		}else if (getContractServices() == 2 || getContractServices() == 4){
			document.getElementById("period").style.display = "none";
			document.getElementById("payment").style.display = "none";
			document.getElementById("tax").style.display = "block";
		}else if (getContractServices() == 3){
			document.getElementById("period").style.display = "none";
			document.getElementById("payment").style.display = "block";
			document.getElementById("tax").style.display = "block";
		}
	}
	function yyyymmdd(dateIn) {
	   var yyyy = dateIn.getFullYear();
	   var mm = dateIn.getMonth()+1; // getMonth() is zero-based
	   var dd  = dateIn.getDate();
	   return String(yyyy + '-' + mm + '-' + dd); // Leading zeros for mm and dd
	}
	function onloadFunc() {
		var today = new Date();
		onchangeContractServices();
		alertError();
	}
	function ifSettlement(){
		var payment = document.querySelector('input[name = "paymentRadio"]:checked').value;
		if (payment == "option2" && getContractServices() == 3) {
			document.getElementById("tax").style.display = "none";
		} else {
			document.getElementById("tax").style.display = "block";
		}
	}
	function alertError() {
		var message=document.getElementById("err");
		if(message!=null){
			var messageValue=message.value;
			var strValue = messageValue.substring(0, 7);
			if(strValue=="Success"){
				//Success
				swal("Good job!", messageValue, "success");
			}
			else if(strValue=="Ooooops"){
				//Ooooops
				sweetAlert("Oops...", messageValue, "error");
			}
		}
	}
	window.onload = onloadFunc;
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Create Invoice</h1>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-3"><label>Invoice Date</label></div>
					<div class="col-md-8">
						<input type="date" id="invoiceDate" class="form-control-client" style="width: 100%;" name="invoiceBean.invoiceDate" value="<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />">
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Client</label></div>
					<div class="col-md-8">
						<html:select property="invoiceBean.clientId" name="invoiceForm" style="width: 100%;" styleClass="form-control-client" styleId="client">
							<option selected disabled>Select</option>
							<html:optionsCollection name="invoiceForm" property="clientList" label="name" value="clientId"/>
						</html:select>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Contract Services</label></div>
					<div class="col-md-8">
						<html:select property="invoiceBean.invoiceTypeId" name="invoiceForm" styleClass="form-control-client" styleId="contractServices" onchange="javascript:onchangeContractServices()">
							<option selected disabled>Select</option>
							<html:optionsCollection name="invoiceForm" property="invoiceTypeList" label="name" value="invoiceTypeId"/>
						</html:select>
					</div>
				</div>
			</div>
			<logic:equal property="task" name="invoiceForm" value="createInvoice">
			<div id="period" class="col-md-10" style="margin-top: 10px; display: none;">
				<div class="row">
					<div class="col-md-3"><label>Period</label></div>
					<div class="col-md-1"><label>Month</label></div>
					<div class="col-md-3">

						<html:select name="invoiceForm" property="invoiceBean.periodMonth" styleClass="form-control">
							<html:option value="01">January</html:option>
							<html:option value="02">February</html:option>
							<html:option value="03">March</html:option>
							<html:option value="04">April</html:option>
							<html:option value="05">May</html:option>
							<html:option value="06">June</html:option>
							<html:option value="07">July</html:option>
							<html:option value="08">August</html:option>
							<html:option value="09">September</html:option>
							<html:option value="10">October</html:option>
							<html:option value="11">November</html:option>
							<html:option value="12">December</html:option>
						</html:select>
					</div>
					<div class="col-md-1"><label>Year</label></div>
					<div class="col-md-3">
						<html:select name="invoiceForm" property="invoiceBean.periodYear"
										styleClass="form-control-client">
							<html:option value="">Select All</html:option>
							<html:optionsCollection name="invoiceForm"
								property="optYear" value="value" label="label" />
						</html:select>
					</div>
				</div>
			</div>
			</logic:equal>
			<logic:equal property="task" name="invoiceForm" value="createInvoiceIndex">
			<div id="period" class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Period</label></div>
					<div class="col-md-1"><label>Month</label></div>
					<div class="col-md-3">
						<html:select name="invoiceForm" property="invoiceBean.periodMonth" styleClass="form-control">
							<html:option value="01">January</html:option>
							<html:option value="02">February</html:option>
							<html:option value="03">March</html:option>
							<html:option value="04">April</html:option>
							<html:option value="05">May</html:option>
							<html:option value="06">June</html:option>
							<html:option value="07">July</html:option>
							<html:option value="08">August</html:option>
							<html:option value="09">September</html:option>
							<html:option value="10">October</html:option>
							<html:option value="11">November</html:option>
							<html:option value="12">December</html:option>
						</html:select>
					</div>
					<div class="col-md-1"><label>Year</label></div>
					<div class="col-md-3">
						<html:select name="invoiceForm" property="invoiceBean.periodYear"
										styleClass="form-control-client">
							<html:option value="">Select All</html:option>
							<html:optionsCollection name="invoiceForm"
								property="optYear" value="value" label="label" />
						</html:select>
					</div>
				</div>
			</div>
			</logic:equal>
			<div id="payment" class="col-md-10" style="margin-top: 10px; display: none;">
				<div class="row">
					<div class="col-md-3"><label>Payment</label></div>
					<div class="col-md-5">
						<div class="radio">
							<label>
								<input type="radio" name="paymentRadio" id="paymentOption1" value="option1" onchange="javascript:ifSettlement()" checked>DP
						    </label>
						    &nbsp;
						    <label>
								<input type="radio" name="paymentRadio" id="paymentOption1" value="option2" onchange="javascript:ifSettlement()">Settlement
						    </label>
						</div>
					</div>
				</div>
			</div>
			<div id="tax" class="col-md-10" style="margin-top: 10px;  display: none;">
				<div class="row">
					<div class="col-md-3"><label>Tax</label></div>
					<div class="col-md-5">
						<div class="radio">
							<label>
								<html:radio name="invoiceForm" property="invoiceBean.isGross" value="0">Exclude</html:radio>
						    </label>
						    &nbsp;
						    <label>
						   		<html:radio name="invoiceForm" property="invoiceBean.isGross" value="1">Include</html:radio>
						    </label>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Invoice Notes</label></div>
					<div class="col-md-8">
						<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control"></html:textarea>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
				<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('invoice')">Back</button>
				<button type="button" class="btn btn-primary" onclick="javascript:flyToNextPage()">Next</button>
			</div>
			<div class="col-md-4" style="color:red;overflow: auto;" id="message">
  				<logic:notEmpty name="invoiceForm" property="messageList">
					<logic:iterate id="message" name="invoiceForm" property="messageList">
						<%-- <bean:write name="message" />  --%>
						<input type="hidden" id="err" value="<bean:write name="message" />">
					</logic:iterate>
				</logic:notEmpty>
			</div>
		</div>
	</div>
	
	
	</html:form>
</body>
</html>
