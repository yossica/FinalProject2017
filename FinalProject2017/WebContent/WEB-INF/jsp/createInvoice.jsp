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
<title>Create Invoice</title>
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
		if (getContractServices() == 1){
			task = 'createInvoicePS';
		}else if (getContractServices() == 2 || getContractServices() == 4){
			task = 'createInvoiceHH';
		}else if (getContractServices() == 3){
			task = 'createInvoiceTR';
		}
		document.forms[1].task.value = task;
		document.forms[1].submit();
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

	function onloadFunc() {
		onchangeContractServices();
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
			<div class="col-md-4" style="color:red;overflow: auto;" id="message">
			  				<logic:notEmpty name="invoiceForm" property="messageList">
								<logic:iterate id="message" name="invoiceForm" property="messageList">
									<bean:write name="message" /> 
								</logic:iterate>
							</logic:notEmpty>
			  			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2"><label>Invoice Date</label></div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;" name="invoiceBean.invoiceDate" value="<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />">
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Client</label></div>
					<div class="col-md-5">
						<html:select property="invoiceBean.clientId" name="invoiceForm" styleClass="form-control-client">
							<option selected disabled>Select</option>
							<html:optionsCollection name="invoiceForm" property="clientList" label="name" value="clientId"/>
						</html:select>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Contract Services</label></div>
					<div class="col-md-5">
						<html:select property="invoiceBean.invoiceTypeId" name="invoiceForm" styleClass="form-control-client" styleId="contractServices" onchange="javascript:onchangeContractServices()">
							<option selected disabled>Select</option>
							<html:optionsCollection name="invoiceForm" property="invoiceTypeList" label="name" value="invoiceTypeId"/>
						</html:select>
					</div>
				</div>
			</div>
			<div id="period" class="col-md-10" style="margin-top: 10px; display: none;">
				<div class="row">
					<div class="col-md-2"><label>Period</label></div>
					<div class="col-md-1"><label>Month</label></div>
					<div class="col-md-4">
						<select class="form-control" name="invoiceBean.periodMonth">
							<option selected disabled>Select</option>
                            <option value="01">January</option>
                            <option value="02">February</option>
                            <option value="03">March</option>
                            <option value="04">April</option>
                            <option value="05">May</option>
                            <option value="06">June</option>
                            <option value="07">July</option>
                            <option value="08">August</option>
                            <option value="09">September</option>
                            <option value="10">October</option>
                            <option value="11">November</option>
                            <option value="11">December</option>
						</select>
					</div>
					<div class="col-md-1"><label>Year</label></div>
					<div class="col-md-4">
						<select class="form-control" name="invoiceBean.periodYear">
							<option selected>Select</option>
							<%
                        		int year = Calendar.getInstance().get(Calendar.YEAR);
                        		for(int i=2000;i<=year;i++){
                        		%>
                        			<option value="<%= i %>"><%= i %></option>
                        		<% 
                        		}
                        	%>
						</select>
					</div>
				</div>
			</div>
			<div id="payment" class="col-md-10" style="margin-top: 10px; display: none;">
				<div class="row">
					<div class="col-md-2"><label>Payment</label></div>
					<div class="col-md-5">
						<div class="radio">
							<label>
								<input type="radio" name="paymentRadio" id="paymentOption1" value="option1" checked>DP
						    </label>
						    &nbsp;
						    <label>
								<input type="radio" name="paymentRadio" id="paymentOption1" value="option2">Settlement
						    </label>
						</div>
					</div>
				</div>
			</div>
			<div id="tax" class="col-md-10" style="margin-top: 10px;  display: none;">
				<div class="row">
					<div class="col-md-2"><label>Tax</label></div>
					<div class="col-md-5">
						<div class="radio">
							<label>
								<html:radio name="invoiceForm" property="invoiceBean.isGross" value="1">Include</html:radio>
						    </label>
						    &nbsp;
						    <label>
						    	<html:radio name="invoiceForm" property="invoiceBean.isGross" value="0">Exclude</html:radio>
						    </label>
						</div>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Invoice Notes</label></div>
					<div class="col-md-5">
						<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control"></html:textarea>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
				<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('invoice')">Back</button>
				<button type="button" class="btn btn-primary" onclick="javascript:flyToNextPage()">Next</button>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
