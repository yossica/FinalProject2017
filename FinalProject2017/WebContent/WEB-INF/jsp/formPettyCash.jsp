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
		var task = document.forms[1].task.value;
		var debit = document.forms[1].isDebit.value;

		//all balance checking in server
		//validate amount != alphabet
		
		if (confirm("Are you sure to insert these data to cash in bank transaction?")) {
			flyToPage();
		}
	}
	function cancel(){
		document.forms[1].task.value="pettyCash";
		flyToPage();
	}
	function flyToPage() {
		document.forms[1].submit();
	}
</script>
<title>Change Password</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />

	<html:form action="/pettyCash" method="post">
		<html:hidden name="pettyCashForm" property="task" />
		<html:hidden name="pettyCashForm" property="isDebit" />
		<span> <logic:notEmpty name="pettyCashForm"
				property="messageList">
				<logic:iterate id="message" name="pettyCashForm"
					property="messageList">
					<bean:write name="message" />
				</logic:iterate>
			</logic:notEmpty>
		</span>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Petty Cash Transaction</h1>

					<div class="col-lg-12">
						<div class="col-lg-11">
							<div class="row" style="margin-top: 10px;">
								<div class="col-md-2">Remaining Balance</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									Rp.
									<bean:write name="pettyCashForm" property="remainingBalance"
										format="#" />
								</div>
							</div>

							<div class="row" style="margin-top: 10px;">
								<div class="col-md-2">Transaction Date</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									<input type="date" style="form-control"
										name="transactionDate"
										value="<bean:write name="pettyCashForm" property="transactionDate"/>" />
								</div>
							</div>

							<br />
							<div class="row">
								<div class="col-md-2">Transaction Category</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">									
									<html:select property="cashFlowCategoryId"
										name="pettyCashForm" styleClass="form-control"
										style="width: 50%;">
										<html:optionsCollection property="cashFlowCategoryList"
											label="name" value="cashFlowCategoryId"
											name="pettyCashForm" />
									</html:select>									
									<br />
								</div>

							</div>
							<br />
							<div class="row">
								<div class="col-md-2">Amount</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									<html:text styleClass="form-control" name="pettyCashForm"
										property="amount" />
								</div>
							</div>
							<br />
							<div class="row">
								<div class="col-md-2">Description</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8" style="margin-bottom: 10px;">
									<html:textarea styleClass="form-control" name="pettyCashForm"
										property="description" />
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
						<logic:notEmpty name="pettyCashForm" property="messageList">
							<logic:iterate id="message" name="pettyCashForm"
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