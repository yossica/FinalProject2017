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
		var transactionDate = document.getElementsByName("pettyCashBean.transactionDate")[0].value;
		var amount = document.getElementsByName("pettyCashBean.amount")[0].value;
		var description = document.getElementsByName("pettyCashBean.description")[0].value;
		var doubleReg = /^([\d]+)(|.[\d]+)$/;
		var errorMessage = ""; 
		
		if(transactionDate == ""){
			errorMessage = errorMessage + "Transaction date must be filled! \n";
		}
		
		if(amount == ""){
			errorMessage+="Amount must be filled! \n";
		}
		else if(!doubleReg.test(amount)){
			errorMessage+="Amount must be number! \n";
		}
		else if(parseFloat(amount) <= 0){
			errorMessage+="Amount cannot be zero or negative! \n";	
		}
		if(description == ""){
			errorMessage+="Description must be filled! \n";
		}
		
		if(errorMessage.length != 0){
			sweetAlert("Oops...", errorMessage, "error");
			return;
		}
		else {
			swal({
				  title: "Are you sure?",
				  text: "System will insert these data to cash in bank transaction",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#ef2300",
				  confirmButtonText: "Yes, Insert",
				  cancelButtonText: "No, Cancel Please!",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
					 /*  swal({title: "Good job!",
						  text: "Transaction Success!",
						  type: "success"}
						 ,function(){
						  setTimeout(function(){
							  flyToPage();
							  }, 10);
							});  */
					  flyToPage();
				  } else {
				    swal("Cancelled", "Cancel Insert Transaction", "error");
				  }
				});
		}
	}
	function cancel(){
		document.forms[1].task.value="pettyCash";
		flyToPage();
	}
	function flyToPage() {
		document.forms[1].submit();
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
	window.onload = alertError;
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />

	<html:form action="/pettyCash" method="post">
		<html:hidden name="pettyCashForm" property="task" />
		<html:hidden name="pettyCashForm" property="pettyCashBean.isDebit" />
		
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Petty Cash Transaction</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Remaining Balance</label></td>
										<td>
											<bean:write name="pettyCashForm" property="remainingBalance"
										format="IDR #,###.##" /></td>
									</tr>
									<tr>
										<td><label>Transaction Date</label></td>
										
										<td><input type="date" class="form-control-client"
										name="pettyCashBean.transactionDate"
										value="<bean:write name="pettyCashForm" property="pettyCashBean.transactionDate"/>" />
										</td>
									</tr>
									<tr>
										<td><label>Transaction Category</label></td>
										<td>
											<html:select property="pettyCashBean.cashFlowCategoryId" 
										name="pettyCashForm" styleClass="form-control-client">
										<html:optionsCollection property="cashFlowCategoryList"
											label="name" value="cashFlowCategoryId"
											name="pettyCashForm" />
									</html:select>
										</td>
									</tr>
									<tr>
										<td><label>Amount</label></td>
										<td><html:text styleClass="form-control-client" name="pettyCashForm"
										property="pettyCashBean.amount" /></td>
									</tr>
									<tr>
										<td><label>Description</label></td>
										<td><html:textarea styleClass="form-control" name="pettyCashForm"
										property="pettyCashBean.description" /></td>
									</tr>
									
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>	
					<div class="col-md-12" style="padding-right: 1%;" >
						<button type="button" class="btn btn-primary" onclick="javascript:insert()">Save</button>
						<button type="button" class="btn btn-primary" onclick="javascript:cancel()">Cancel</button>	
					</div>
			
		
		
					
					<div class="col-md-4" style="color: red;"
						id="message">
						<logic:notEmpty name="pettyCashForm" property="messageList">
							<logic:iterate id="message" name="pettyCashForm"
								property="messageList">
								<input type="hidden" id="err" value="<bean:write name="message" />">
							</logic:iterate>
						</logic:notEmpty>
					</div>
				</div>
			</div>
		</div>
		
		
	</html:form>
</body>
</html>