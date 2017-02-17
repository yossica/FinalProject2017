<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function insert() {
		//validate
		//all balance checking in server
		//validate amount != alphabet
		var transactionDate = document.getElementsByName("cashInBankBean.transactionDate")[0].value;
		var amount = document.getElementsByName("cashInBankBean.amount")[0].value;
		var description = document.getElementsByName("cashInBankBean.description")[0].value;
		var doubleReg =  /^[\d]+(.[\d]+)$/;
		var errorMessage = ""; 
		
		if(transactionDate == ""){
			errorMessage = errorMessage + "Transaction date must be filled!<br/>";
		}
		
		if(amount == ""){
			errorMessage = errorMessage + "Amount must be filled!<br/>";
		}
		else if(!doubleReg.test(amount)){
			errorMessage = errorMessage + "Amount must be number!<br/>";
		}
		else if(parseFloat(amount) <= 0){
			errorMessage = errorMessage + "Amount cannot be zero or negative!<br/>";	
		}
		if(description == ""){
			errorMessage = errorMessage + "Description must be filled!<br/>";
		}
		
		if(errorMessage.length != 0){
			document.getElementById("message").innerHTML = errorMessage;
			return;
		}
		else{
			swal({
				  title: "Are you sure?",
				  text: "System will insert these data to cash in bank transaction",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "Yes, Insert",
				  cancelButtonText: "No, Cancel Please!",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
					  flyToPage();
				  } else {
				    swal("Cancelled", "Cancel Insert Transaction", "error");
				  }
				});
		}
	}
	function cancel(){
		document.forms[1].task.value="cashInBank";
		flyToPage();
	}
	function flyToPage() {
		document.forms[1].submit();
	}
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
				</div>
				<div class="col-lg-12">
					<div class="col-lg-12" style="padding-left:10px">
						<div class="col-lg-10">
							<div class="row" style="margin-top: 10px;">
								<div class="col-md-3"><label>Remaining Balance</label></div>
								<div class="col-md-5">
									Rp.
									<bean:write name="cashInBankForm" property="remainingBalance"
										format="#,###.00" />
								</div>
							</div>

							<div class="row" style="margin-top: 10px;">
								<div class="col-md-3"><label>Transaction Date</label></div>
								<div class="col-md-5">
									<input type="date" class="form-control"
										name="cashInBankBean.transactionDate"
										value="<bean:write name="cashInBankForm" property="cashInBankBean.transactionDate"/>" />
								</div>
							</div>

							<br />
							<div class="row">
								<div class="col-md-3"><label>Transaction Category</label></div>
								<div class="col-md-5">
									<logic:equal value="saveTransfer" property="task"
										name="cashInBankForm">
										Transfer to Petty Cash
									</logic:equal>
									<logic:notEqual value="saveTransfer" property="task"
										name="cashInBankForm">
										<html:select property="cashInBankBean.cashFlowCategoryId"
											name="cashInBankForm" styleClass="form-control">
											<html:optionsCollection property="cashFlowCategoryList"
												label="name" value="cashFlowCategoryId"
												name="cashInBankForm" />
										</html:select>
									</logic:notEqual>
									<br />
								</div>

							</div>
							<br />
							<div class="row">
								<div class="col-md-3"><label>Amount</label></div>
								<div class="col-md-5">
									<html:text styleClass="form-control" name="cashInBankForm"
										property="cashInBankBean.amount" />
								</div>
							</div>
							<br />
							<div class="row">
								<div class="col-md-3"><label>Description</label></div>
								<div class="col-md-5" style="margin-bottom: 10px;">
									<html:textarea styleClass="form-control" name="cashInBankForm"
										property="cashInBankBean.description" />
								</div>
							</div>
							<div class="panel-body" style="padding-left: 0;">
								<div class="pull-left">
									<button type="button" class="btn btn-primary " onclick="javascript:cancel()">Cancel</button>
									<button type="button" class="btn btn-primary "
										onclick="javascript:insert()">Save</button>

								</div>
							</div>

						</div>
					</div>
					
					<div class="col-md-4" style="color: red;"
						id="message">
						<logic:notEmpty name="cashInBankForm" property="messageList">
							<logic:iterate id="message" name="cashInBankForm"
								property="messageList">
								<bean:write name="message" />
							</logic:iterate>
						</logic:notEmpty>
					</div>
				</div>

			</div>
		</div>
	</html:form>
</body>
</html>