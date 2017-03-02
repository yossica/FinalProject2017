<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ page import="java.util.Calendar"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Solution</title>
<style>
/* Popup container - can be anything you want */
.popup {
	position: relative;
	display: inline-block;
	cursor: pointer;
	-webkit-user-select: none;
	-moz-user-select: none;
	-ms-user-select: none;
	user-select: none;
}

/* The actual popup */
.popup .popuptext {
	visibility: hidden;
	width: 200px;
	background-color: #337ab7;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 8px;
	position: absolute;
	z-index: 1;
	bottom: 125%;
	left: -200px;
	margin-left: 150px;
}

/* Popup arrow */
.popup .popuptext::after {
	content: "";
	position: absolute;
	top: 100%;
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: #555 transparent transparent transparent;
}

/* Toggle this class - hide and show the popup */
.popup .show {
	visibility: visible;
	-webkit-animation: fadeIn 1s;
	animation: fadeIn 1s;
}

/* Add animation (fade in the popup) */
@
-webkit-keyframes fadeIn {
	from {opacity: 0;
}

to {
	opacity: 1;
}

}
@
keyframes fadeIn {
	from {opacity: 0;
}

to {
	opacity: 1;
}
}
</style>
<script>
	function toggleFilter() {
		var filter = document.getElementById("filterForm");
		filter.style.display = filter.style.display === 'none' ? '' : 'none';
		if (filter.style.display == 'none') {
			document.getElementById("listInvoice").style.height = "495px";
		} else {
			document.getElementById("listInvoice").style.height = "265px";
		}
	}
	function filter() {
		var monthFrom = document.forms[1].monthFrom.value;
		var yearFrom = document.forms[1].yearFrom.value;
		var monthTo = document.forms[1].monthTo.value;
		var yearTo = document.forms[1].yearTo.value;

		if (monthFrom == "" && yearFrom == "" && monthTo == "" && yearTo == "") {
			flyToPage('filter');
		} else if (monthFrom != "" && yearFrom != "" && monthTo != ""
				&& yearTo != "") {
			if (monthFrom > monthTo && yearFrom == yearTo) {
				//document.getElementById("errorMessage").innerHTML = "Start month period must before end month period!";
				sweetAlert("Oops...",
						"Start month period must before end month period!",
						"error");
			} else if (yearFrom > yearTo) {
				//document.getElementById("errorMessage").innerHTML = "Start year period must before end year period!";
				sweetAlert("Oops...",
						"Start year period must before end year period!",
						"error");
			} else {
				flyToPage('filter');
			}
		} else {
			//document.getElementById("errorMessage").innerHTML = "Start month and year period and month and year period must be either both filled or emptied!";
			sweetAlert(
					"Oops...",
					"Start month and year period and month and year period must be either both filled or emptied!",
					"error");
		}
	}
	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function toggleNotes(idNumber) {
	    var popup = document.getElementById("myPopup"+idNumber);
	    popup.classList.toggle("show");
	}
	function flyToChangeStatus(invoiceNumber, statusId, idPaidDate) {
		document.forms[1].paidDate.value = null;
		if (statusId == 4) {
			swal(
					{
						title : "Are you sure?",
						text : "System will change this invoice status to 'Cancelled'",
						type : "warning",
						showCancelButton : true,
						confirmButtonColor : "#DD6B55",
						confirmButtonText : "Yes, Cancel this Invoice",
						cancelButtonText : "No, Cancel",
						closeOnConfirm : false,
						closeOnCancel : false
					},
					function(isConfirm) {
						if (isConfirm) {
							swal(
									{
										title : "Good job!",
										text : "Cancelled Success!",
										type : "success"
									},
									function() {
										setTimeout(
												function() {
													document.forms[1].invoiceNumber.value = invoiceNumber;
													document.forms[1].statusId.value = statusId;
													document.forms[1].paidDate.value = null;
													flyToPage("changeStatus");
												}, 10);
									});
							/* document.forms[1].invoiceNumber.value = invoiceNumber;
							document.forms[1].statusId.value = statusId;
							flyToPage("changeStatus"); */
						} else {
							swal("Cancelled", "Cancel 'Cancel Invoice'",
									"error");
						}
					});
		} else{
			if(statusId == 2){
				var paidDate = document.getElementById(idPaidDate).value;
				if (paidDate == ""){
					sweetAlert("Oops...",
							"Paid date must be filled!",
							"error");
					return;
					//error karena mau ubah jadi paid tapi gak di input tanggal pembayarannya
				}else{		
					document.forms[1].paidDate.value = paidDate;				
				}
			}
			swal(
					{
						title : "Are you sure?",
						text : "System will change current status into next status",
						type : "warning",
						showCancelButton : true,
						confirmButtonColor : "#DD6B55",
						confirmButtonText : "Yes, Change Status",
						cancelButtonText : "No, Cancel",
						closeOnConfirm : false,
						closeOnCancel : false
					},
					function(isConfirm) {
						if (isConfirm) {
							swal(
									{
										title : "Good job!",
										text : "Changed Status Success!",
										type : "success"
									},
									function() {
										setTimeout(
												function() {
													document.forms[1].invoiceNumber.value = invoiceNumber;
													document.forms[1].statusId.value = statusId;
													
													flyToPage("changeStatus");
												}, 10);
									});
							/* document.forms[1].invoiceNumber.value = invoiceNumber;
							document.forms[1].statusId.value = statusId;
							flyToPage("changeStatus"); */
						} else {
							swal("Cancelled", "Cancel Change Status", "error");
						}
					});
		}
	}
	function flyToDetail(transactionInvoiceHeaderId, clientId, statusId) {
		document.forms[1].transactionInvoiceHeaderId.value = transactionInvoiceHeaderId;
		document.forms[1].client.value = clientId;
		document.forms[1].statusId.value = statusId;
		flyToPage("detailInvoice");
	}
	function alertError() {
		var message = document.getElementById("err");
		if (message != null) {
			var messageValue = message.value;
			var strValue = messageValue.substring(0, 7);
			if (strValue == "Success") {
				//Success
				swal("Good job!", messageValue, "success");
			} else if (strValue == "Ooooops") {
				//Ooooops
				sweetAlert("Oops...", messageValue, "error");
			}
		}
	}
	window.onload = alertError;
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
		<html:hidden property="task" name="invoiceForm" />
		<html:hidden property="client" name="invoiceForm" />
		<html:hidden property="statusId" name="invoiceForm" />
		<html:hidden property="invoiceTypeId" name="invoiceForm" />
		<html:hidden property="paidDate" name="invoiceForm" />
		<%-- <html:hidden property="invoiceTipeId" name="invoiceForm"/> --%>
		<html:hidden property="transactionInvoiceHeaderId" name="invoiceForm" />
		<html:hidden property="invoiceNumber" name="invoiceForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Invoice List</h1>
					<div class="panel-body" style="padding-right: 0;">
						<div class="pull-right">
							<button id="filterButton" type="button" class="btn btn-primary"
								onclick="javascript:toggleFilter()">Toggle Filter</button>
							<button type="button" class="btn btn-primary"
								onclick="javascript:flyToPage('export')">Export To PDF</button>
							<button type="button" class="btn btn-primary"
								onclick="javascript:flyToPage('createInvoice')">Create</button>
						</div>
					</div>
					<div id="filterForm" class="col-lg-12"
						style="border: solid 2px gray; border-radius: 10px; background-color: #EFEFEF; display: none;">
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12">
								<div class="col-md-2">Client</div>
								<div class="col-md-10">
									<html:select property="clientId" name="invoiceForm"
										styleClass="form-control" styleId="basic">
										<html:option value="">Select</html:option>
										<html:optionsCollection name="invoiceForm"
											property="clientList" label="name" value="clientId" />
									</html:select>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2">From</div>
								<div class="col-md-2">
									<html:select property="monthFrom" name="invoiceForm"
										styleClass="form-control">
										<html:option value="">Select</html:option>
										<html:option value="1">January</html:option>
										<html:option value="2">February</html:option>
										<html:option value="3">March</html:option>
										<html:option value="4">April</html:option>
										<html:option value="5">May</html:option>
										<html:option value="6">June</html:option>
										<html:option value="7">July</html:option>
										<html:option value="8">August</html:option>
										<html:option value="9">September</html:option>
										<html:option value="10">October</html:option>
										<html:option value="11">November</html:option>
										<html:option value="12">December</html:option>
									</html:select>
								</div>
								<div class="col-md-2">
									<html:select property="yearFrom" name="invoiceForm"
										styleClass="form-control">
										<html:option value="">Select</html:option>
										<%
											int year = Calendar.getInstance().get(Calendar.YEAR);
													for (int i = 2000; i <= year; i++) {
										%>
										<html:option value='<%=i + ""%>'><%=i%></html:option>
										<%
											}
										%>
									</html:select>
								</div>
								<div class="col-md-1">To</div>
								<div class="col-md-2">
									<html:select property="monthTo" name="invoiceForm"
										styleClass="form-control">
										<html:option value="">Select</html:option>
										<html:option value="1">January</html:option>
										<html:option value="2">February</html:option>
										<html:option value="3">March</html:option>
										<html:option value="4">April</html:option>
										<html:option value="5">May</html:option>
										<html:option value="6">June</html:option>
										<html:option value="7">July</html:option>
										<html:option value="8">August</html:option>
										<html:option value="9">September</html:option>
										<html:option value="10">October</html:option>
										<html:option value="11">November</html:option>
										<html:option value="12">December</html:option>
									</html:select>
								</div>
								<div class="col-md-2">
									<html:select property="yearTo" name="invoiceForm"
										styleClass="form-control">
										<html:option value="">Select</html:option>
										<%
											int year = Calendar.getInstance().get(Calendar.YEAR);
													for (int i = 2000; i <= year; i++) {
										%>
										<html:option value='<%=i + ""%>'><%=i%></html:option>
										<%
											}
										%>
									</html:select>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2">Status</div>
								<div class="col-md-10">
									<html:select property="statusInvoiceId" name="invoiceForm"
										styleClass="form-control">
										<html:option value="">Select</html:option>
										<html:optionsCollection property="statusInvoiceList"
											label="name" value="statusInvoiceId" name="invoiceForm" />
									</html:select>
								</div>
							</div>
						</div>
						<div class="col-md-12" style="margin-top: 10px;">
							<div class="row">
								<div class="col-md-2">Service Type</div>
								<div class="col-md-10">
									<html:select property="invoiceTipeId" name="invoiceForm"
										styleClass="form-control">
										<html:option value="">Select</html:option>
										<html:optionsCollection property="invoiceTypeList"
											label="name" value="invoiceTypeId" name="invoiceForm" />
									</html:select>
								</div>
							</div>
						</div>
						<div class="col-md-12"
							style="margin-top: 10px; margin-bottom: 10px;">
							<button type="button" class="btn btn-primary"
								onclick="javascript:filter()">Filter</button>
							<span id="errorMessage" style="color: red">
						</div>
					</div>
				</div>
				<!-- /.col-lg-12 -->
			</div>
			<div class="panel-body">
				<div id="listInvoice" style="height: 495px;">
					<table class="table table-hover">
						<tr>
							<th>Invoice No.</th>
							<th>Client</th>
							<th>Period</th>
							<th>Service</th>
							<th>Invoice Date</th>
							<th>Status</th>
							<th>Action</th>
						</tr>
						<tbody>
							<logic:notEmpty name="invoiceForm" property="invoiceList">
								<logic:iterate id="inv" name="invoiceForm"
									property="invoiceList" indexId="indexInv">
									<tr>
										<td><bean:write name="inv" property="invoiceNumber" /></td>
										<td><bean:write name="inv" property="clientName" /></td>
										<td><bean:write name="inv" property="periodMonthName" />&nbsp;<bean:write
												name="inv" property="periodYear" format="#" /></td>
										<td><bean:write name="inv" property="invoiceTypeName" /></td>
										<td><bean:write name="inv" property="invoiceDate" /></td>
										<td><bean:write name="inv" property="statusInvoiceName" />
											<p>
												<logic:equal name="inv" property="statusInvoiceName"
													value="Paid"> on <bean:write name="inv"
														property="paidDate" />
											</p> </logic:equal></td>
										<td><input type="button" value="View"
											class="btn btn-primary"
											onclick="javascript:flyToDetail(
				                				'<bean:write name="inv" property="transactionInvoiceHeaderId" format="#"/>',
				                				'<bean:write name="inv" property="clientId" format="#"/>',
				                				'<bean:write name="inv" property="statusInvoiceId" format="#"/>'
				                		)" />
											<logic:equal name="inv" property="statusInvoiceName"
												value="Created">

												<input type="button" value="Change Status"
													class="btn btn-primary"
													onclick="javascript:flyToChangeStatus(
					                								'<bean:write name="inv" property="invoiceNumber"/>',
					                								'<bean:write name="inv" property="statusInvoiceId" format="#"/>',
					                								'0')">
												<input type="button" value="Cancel" class="btn btn-primary"
													onclick="javascript:flyToChangeStatus(
				                								'<bean:write name="inv" property="invoiceNumber"/>',
				                								'4',
				                								'0')">
											</logic:equal> <logic:equal name="inv" property="statusInvoiceName"
												value="Sent">
												<div class="popup">
													<span class="popuptext" id="myPopup${indexInv}">
														<div>Input Paid Date:</div>
														<div>
															<input type="date" id="paidDate${indexInv}"
																class="form-control-client" style="width: 100%;"
																value="<bean:write name="inv" property="paidDate" />">
														</div>
														<div>
															<input type="button" value="OK" class="btn btn-primary"
																onclick="javascript:flyToChangeStatus(
				                								'<bean:write name="inv" property="invoiceNumber"/>',
				                								'<bean:write name="inv" property="statusInvoiceId" format="#"/>',
				                								'paidDate${indexInv}')">
														</div>
													</span> <input type="button" value="Change Status"
														class="btn btn-primary"
														onclick="javascript:toggleNotes(${indexInv})">
												</div>
												<input type="button" value="Cancel" class="btn btn-primary"
													onclick="javascript:flyToChangeStatus(
				                								'<bean:write name="inv" property="invoiceNumber"/>',
				                								'4',
				                								'0')">
											</logic:equal></td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
							<logic:empty name="invoiceForm" property="invoiceList">
								<tr>
									<td colspan="7" align="center">There are no data based on
										this filter</td>
								</tr>
							</logic:empty>
						</tbody>
					</table>
				</div>
			</div>
		</div>
		<div class="col-md-4" style="color: red; overflow: auto;" id="message">
			<logic:notEmpty name="invoiceForm" property="messageList">
				<logic:iterate id="message" name="invoiceForm"
					property="messageList">
					<%-- <bean:write name="message" />  --%>
					<input type="hidden" id="err" value="<bean:write name="message" />">
				</logic:iterate>
			</logic:notEmpty>
		</div>
	</html:form>
	<script type="text/javascript">
$(document).ready(function() {
  $('#basic').selectpicker({
      liveSearch: true
    });
});
</script>
</body>
</html>