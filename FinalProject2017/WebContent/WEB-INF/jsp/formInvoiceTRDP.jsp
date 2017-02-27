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
	function validate() {
		//validate
		var fee = document.getElementsByName("trainingFee")[0].value;
		var description = document
				.getElementsByName("trainingBean.description")[0].value;
		var startDate = document
				.getElementsByName("trainingBean.trainingStartDate")[0].value;
		var endDate = document
				.getElementsByName("trainingBean.trainingEndDate")[0].value;
		var invoiceDate = document.getElementsByName("invoiceBean.invoiceDate")[0].value;
		var doubleReg = /^([\d]+)(|.[\d]+)$/;
		var errorMessage = "";
		var flag = true;

		if (invoiceDate == "") {
			errorMessage = errorMessage + "Invoice Date must be filled! \n";
			flag = false;
		}

		if (description == "") {
			errorMessage = errorMessage + "Training Name must be filled! \n";
		}

		if (startDate == "") {
			errorMessage = errorMessage + "Start Date must be filled! \n";
			flag = false;
		}
		if (endDate == "") {
			errorMessage = errorMessage + "End Date must be filled! \n";
			flag = false;
		}
		if (flag) {
			var s = startDate.split("-");
			var sd = new Date(parseInt(s[0]), parseInt(s[1]) - 1,
					parseInt(s[2]));
			var e = endDate.split("-");
			var ed = new Date(parseInt(e[0]), parseInt(e[1]) - 1,
					parseInt(e[2]));
			if (sd > ed) {
				errorMessage = errorMessage
						+ "Start Date must be later than End Date! \n";
			}
		}

		if (fee == "") {
			errorMessage = errorMessage
					+ "Total Training Fee must be filled! \n";
		} else if (!doubleReg.test(fee)) {
			errorMessage = errorMessage
					+ "Total Training Fee must be number! \n";
		} else if (parseFloat(fee) <= 0) {
			errorMessage = errorMessage
					+ "Total Training Fee cannot be zero or negative! \n";
		}

		if (errorMessage.length != 0) {
			//document.getElementById("message").innerHTML = errorMessage;
			sweetAlert("Oops...", errorMessage, "error");
			return 0;
		} else {
			return 1;
		}
	}
	function insert() {
		validate();
		if (validate() == 1) {
			swal({
				title : "Are you sure?",
				text : "System will insert these data to Invoice",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#ef2300",
				confirmButtonText : "Yes, Insert",
				cancelButtonText : "No, Cancel Please!",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					flyToPage("insertTRDP");
				} else {
					swal("Cancelled", "Cancel Insert Transaction", "error");
				}
			});

		}
	}
	function update() {
		validate();
		if (validate() == 1) {
			swal({
				title : "Are you sure?",
				text : "System will update these data to Invoice",
				type : "warning",
				showCancelButton : true,
				confirmButtonColor : "#ef2300",
				confirmButtonText : "Yes, Update",
				cancelButtonText : "No, Cancel Please!",
				closeOnConfirm : false,
				closeOnCancel : false
			}, function(isConfirm) {
				if (isConfirm) {
					flyToPage("editInvoiceTRDP");
				} else {
					swal("Cancelled", "Cancel Update Transaction", "error");
				}
			});

		}
	}

	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
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
		<html:hidden property="transactionInvoiceHeaderId" name="invoiceForm" />
		<html:hidden property="invoiceBean.ppnPercentage" name="invoiceForm" />
		<html:hidden property="statusId" name="invoiceForm" />
		<html:hidden property="trainingBean.transactionTrainingHeaderId"
			name="invoiceForm" />

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<logic:equal name="invoiceForm" property="task"
						value="editInvoiceTRDP">
						<h1 class="page-header">Edit Invoice Training</h1>
					</logic:equal>
					<logic:notEqual name="invoiceForm" property="task"
						value="editInvoiceTRDP">
						<h1 class="page-header">Create Invoice Training</h1>
					</logic:notEqual>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-12" style="padding-right: 1%">
						<div class="col-md-3">
							<label>Invoice Date</label>
						</div>
						<div class="col-md-9">
							<logic:equal name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<input type="date" class="form-control" style="width: 100%;"
									name="invoiceBean.invoiceDate"
									value="<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />" />
							</logic:equal>
							<logic:notEqual name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<html:hidden name="invoiceForm"
									property="invoiceBean.invoiceDate" />
							: <bean:write name="invoiceForm"
									property="invoiceBean.invoiceDate" />
							</logic:notEqual>

						</div>
					</div>
				</div>
				<div class="row" style="margin-top: 10px;">
					<div class="col-md-12" style="padding-right: 1%">

						<div class="col-md-3">
							<label>Client</label>
						</div>
						<div class="col-md-9">
							<html:hidden name="invoiceForm" property="invoiceBean.clientId" />
							:
							<bean:write name="invoiceForm" property="invoiceBean.clientName" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Contract Service</label>
						</div>
						<div class="col-md-9">
							<html:hidden name="invoiceForm"
								property="invoiceBean.invoiceTypeId" />
							:
							<bean:write name="invoiceForm"
								property="invoiceBean.invoiceTypeName" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Payment</label>
						</div>
						<div class="col-md-9">: DP</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Tax</label>
						</div>
						<div class="col-md-9">

							<logic:equal name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<div class="col-md-4">
									<html:radio name="invoiceForm" property="invoiceBean.isGross"
										value="0" />
									&nbsp;Exclude &nbsp;
									<html:radio name="invoiceForm" property="invoiceBean.isGross"
										value="1" />
									&nbsp;Include
								</div>
							</logic:equal>

							<logic:notEqual name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<html:hidden name="invoiceForm" property="invoiceBean.isGross" />
								<logic:equal name="invoiceForm" property="invoiceBean.isGross"
									value="0">
								: Exclude
							</logic:equal>
								<logic:equal name="invoiceForm" property="invoiceBean.isGross"
									value="1">
								: Include
							</logic:equal>
							</logic:notEqual>
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Invoice Note</label>
						</div>
						<div class="col-md-8">
							<logic:equal name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<html:textarea name="invoiceForm" property="invoiceBean.notes"
									styleClass="form-control"></html:textarea>
							</logic:equal>
							<logic:notEqual name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<html:textarea name="invoiceForm" property="invoiceBean.notes"
									styleClass="form-control" readonly="true"></html:textarea>
							</logic:notEqual>
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Training Name</label>
						</div>
						<div class="col-md-9">
							<html:text name="invoiceForm" property="trainingBean.description"
								styleClass="form-control" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Start Date</label>
						</div>
						<div class="col-md-9">
							<input type="date" class="form-control" style="width: 100%;"
								name="trainingBean.trainingStartDate"
								value="<bean:write property="trainingBean.trainingStartDate" name="invoiceForm" />" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>End Date</label>
						</div>
						<div class="col-md-9">
							<input type="date" class="form-control" style="width: 100%;"
								name="trainingBean.trainingEndDate"
								value="<bean:write property="trainingBean.trainingEndDate" name="invoiceForm" />" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Total Training Fee</label>
						</div>
						<div class="col-md-9">
							<html:text name="invoiceForm" property="trainingFee"
								styleClass="form-control" />
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-3">
							<label>Notes</label>
						</div>
						<div class="col-md-9">
							<html:textarea name="invoiceForm" property="invoiceDetailNotes"
								styleClass="form-control"></html:textarea>
						</div>
					</div>
				</div>
				<div class="col-md-12" style="margin-top: 10px;">
					<div class="row">
						<div class="col-md-12"
							style="margin-top: 10px; margin-bottom: 10px;">

							<logic:equal name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<button type="button" class="btn btn-primary"
									onclick="javascript:flyToPage('detailInvoice')">Back</button>
								<button type="button" class="btn btn-primary"
									onclick="javascript:update()">Save</button>
							</logic:equal>
							<logic:notEqual name="invoiceForm" property="task"
								value="editInvoiceTRDP">
								<button type="button" class="btn btn-primary"
									onclick="javascript:flyToPage('createInvoice')">Back</button>
								<button type="button" class="btn btn-primary"
									onclick="javascript:insert()">Save</button>
							</logic:notEqual>


						</div>
					</div>
					<div class="col-md-7" id="message" style="color: red">
						<logic:notEmpty name="invoiceForm" property="messageList">
							<logic:iterate id="message" name="invoiceForm"
								property="messageList">
								<input type="hidden" id="err"
									value="<bean:write name="message" />">
							</logic:iterate>
						</logic:notEmpty>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>
