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
	function insert(){
		//validate
		flyToPage();
	}
	function flyToPage(task)
	{
		document.forms[1].submit();
	}
</script>
<title>Change Password</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />

	<html:form action="/cashInBank" method="post">
		<html:hidden name="cashInBankForm" property="task" />
		<html:hidden name="cashInBankForm" property="isDebit" />
		<html:hidden name="cashInBankForm" property="remainingBalance" />
		<span> <logic:notEmpty name="cashInBankForm"
				property="messageList">
				<logic:iterate id="message" name="cashInBankForm"
					property="messageList">
					<bean:write name="message" />
				</logic:iterate>
			</logic:notEmpty>
		</span>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Cash in Bank Transaction</h1>

					<div class="col-lg-12">
						<div class="col-lg-11">
							<div class="row" style="margin-top: 10px;">
								<div class="col-md-2">Remaining Balance</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									Rp.<bean:write name="cashInBankForm" property="remainingBalance" format="#" />
								</div>
							</div>
							
							<div class="row" style="margin-top: 10px;">
								<div class="col-md-2">Transaction Date</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									<input type="date" styleClass="form-control" name="cashInBankForm" property="transactionDate"
										value="<bean:write name="cashInBankForm" property="transactionDate"/>" />
								</div>
							</div>

							<br />
							<div class="row">
								<div class="col-md-2">Transaction Category</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									<logic:equal value="saveBalance" property="task" name="cashInBankForm">
										<html:select property="cashFlowCategoryId" name="cashInBankForm" styleClass="form-control" style="width: 50%;" readonly="true">
											<html:optionsCollection property="cashFlowCategoryList" label="name" value="cashFlowCategoryId" name="cashInBankForm" />
										</html:select>
									</logic:equal>
									<logic:notEqual value="saveBalance" property="task" name="cashInBankForm">
										<html:select property="cashFlowCategoryId" name="cashInBankForm" styleClass="form-control" style="width: 50%;">
											<html:optionsCollection property="cashFlowCategoryList" label="name" value="cashFlowCategoryId" name="cashInBankForm" />
										</html:select>
									</logic:notEqual>
									<br />
								</div>

							</div>
							<br />
							<div class="row">
								<div class="col-md-2">Amount</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8">
									<html:text styleClass="form-control" name="cashInBankForm" property="amount" />
								</div>
							</div>
							<br />
							<div class="row">
								<div class="col-md-2">Description</div>
								<div class="col-md-1">:</div>
								<div class="col-md-8" style="margin-bottom: 10px;">
									<html:textarea name="cashInBankForm" property="description" />
								</div>
							</div>
							<div class="panel-body" style="padding-left: 0;">
								<div class="pull-left">
									<button type="button" class="btn btn-primary ">Cancel</button>
									<button type="button" class="btn btn-primary "
										onclick="javascript:insert()">Save</button>

								</div>
							</div>

						</div>
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>