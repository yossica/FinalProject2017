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
	function insert() {
		//validate
		//all balance checking in server
		//validate amount != alphabet
		var transactionDate = document
				.getElementsByName("cashInBankBean.transactionDate")[0].value;
		var amount = document.getElementsByName("cashInBankBean.amount")[0].value;
		var description = document
				.getElementsByName("cashInBankBean.description")[0].value;
		var doubleReg = /^([\d]+)(|.[\d]+)$/;
		var errorMessage = "";

		if (transactionDate == "") {
			errorMessage = errorMessage + "Transaction date must be filled! \n";
		}

		if (amount == "") {
			errorMessage = errorMessage + "Amount must be filled! \n";
		} else if (!doubleReg.test(amount)) {
			errorMessage = errorMessage + "Amount must be number! \n";
		} else if (parseFloat(amount) <= 0) {
			errorMessage = errorMessage
					+ "Amount cannot be zero or negative! \n";
		}
		if (description == "") {
			errorMessage = errorMessage + "Description must be filled! \n";
		}

		if (errorMessage.length != 0) {
			sweetAlert("Oops...", errorMessage, "error");
			return;
		} else {
			swal(
					{
						title : "Are you sure?",
						text : "System will insert these data to cash in bank transaction",
						type : "warning",
						showCancelButton : true,
						confirmButtonColor : "#ef2300",
						confirmButtonText : "Yes, Insert",
						cancelButtonText : "No, Cancel Please!",
						closeOnConfirm : false,
						closeOnCancel : false
					}, function(isConfirm) {
						if (isConfirm) {
							/* swal({title: "Good job!",
							  text: "Transaction Success!",
							  type: "success"}
							 ,function(){
							  setTimeout(function(){
								  flyToPage();
								  }, 10);
								});  */
							flyToPage();
						} else {
							swal("Cancelled", "Cancel Insert Transaction",
									"error");
						}
					});
		}
	}
	function cancel() {
		document.forms[1].task.value = "cashInBank";
		flyToPage();
	}
	function flyToPage() {

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
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/cashInBank" method="post">
		<html:hidden name="cashInBankForm" property="task" />
		<html:hidden name="cashInBankForm" property="cashInBankBean.isDebit" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Cash in Bank Transaction</h1>
					<div class="panel-body">
						<div>
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Remaining Balance</label></td>
										<td><bean:write name="cashInBankForm"
												property="remainingBalance" format="IDR #,###.##" /></td>
									</tr>
									<logic:equal value="saveTransfer" property="task"
										name="cashInBankForm">
										<tr>
											<td><label>Current Petty Cash Balance</label></td>
											<td><bean:write name="cashInBankForm"
													property="pettyCashBalance" format="IDR #,###.##" /></td>
										</tr>
									</logic:equal>
									<tr>
										<td><label>Transaction Date</label></td>
										<td><input type="date" class="form-control-client"
											name="cashInBankBean.transactionDate"
											value="<bean:write name="cashInBankForm" property="cashInBankBean.transactionDate"/>" /></td>
									</tr>
									<tr>
										<td><label>Transaction Category</label></td>
										<td><logic:equal value="saveTransfer" property="task"
												name="cashInBankForm">
												Transfer to Petty Cash
											</logic:equal> <logic:notEqual value="saveTransfer" property="task"
												name="cashInBankForm">
												<html:select property="cashInBankBean.cashFlowCategoryId"
													name="cashInBankForm" styleClass="form-control" styleId="basic">
													<html:optionsCollection property="cashFlowCategoryList"
														label="name" value="cashFlowCategoryId"
														name="cashInBankForm" />
												</html:select>
											</logic:notEqual></td>
									</tr>
									<tr>
										<td><label>Amount</label></td>
										<td><html:text styleClass="form-control-client"
												name="cashInBankForm" property="cashInBankBean.amount" /></td>
									</tr>
									<tr>
										<td><label>Description</label></td>
										<td><html:textarea styleClass="form-control"
												name="cashInBankForm" property="cashInBankBean.description" /></td>
									</tr>

								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
					<div class="col-md-12" style="padding-right: 1%;">
						<button type="button" class="btn btn-primary "
							onclick="javascript:cancel()">Cancel</button>
						<button type="button" class="btn btn-primary "
							onclick="javascript:insert()">Save</button>
					</div>




					<div class="col-md-4" style="color: red;" id="message">
						<logic:notEmpty name="cashInBankForm" property="messageList">
							<logic:iterate id="message" name="cashInBankForm"
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
<script type="text/javascript">
$(document).ready(function() {
  $('#basic').selectpicker({
      liveSearch: true
    });});
</script>
</body>
</html>