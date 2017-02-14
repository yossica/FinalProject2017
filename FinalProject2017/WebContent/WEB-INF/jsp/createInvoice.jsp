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
					<div class="col-md-2">Invoice Date</div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;" name="invoiceBean.invoiceDate">
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Client</div>
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
					<div class="col-md-2">Contract Services</div>
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
					<div class="col-md-2">Period</div>
					<div class="col-md-1">Month</div>
					<div class="col-md-4">
						<select class="form-control">
							<option selected disabled>Select</option>
                            <option>January</option>
                            <option>February</option>
                            <option>March</option>
                            <option>April</option>
                            <option>June</option>
                            <option>July</option>
                            <option>August</option>
                            <option>September</option>
                            <option>October</option>
                            <option>November</option>
                            <option>December</option>
						</select>
					</div>
					<div class="col-md-1">Year</div>
					<div class="col-md-4">
						<select class="form-control">
							<option selected disabled>Select</option>
							<%
                        		int year = Calendar.getInstance().get(Calendar.YEAR);
                        		for(int i=2000;i<=year;i++){
                        		%>
                        			<option><%= i %></option>
                        		<% 
                        		}
                        	%>
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
					<div class="col-md-2">Tax</div>
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
					<div class="col-md-2">Invoice Notes</div>
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
