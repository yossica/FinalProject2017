<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Petty Cash</title>
<script type="text/javascript">
	function filter(){
		//validasi untuk masukin start date + end date (ga boleh salah satu aja)
		var filterStartDate = document.forms[1].filterStartDate.value;
		var filterEndDate = document.forms[1].filterEndDate.value;
		
		if(filterStartDate == "" && filterEndDate == ""){
			flyToPage("filter");
		}
		else if(filterStartDate != "" && filterEndDate != ""){
			var s = filterStartDate.split("-");
			var startDate = new Date(parseInt(s[0]),parseInt(s[1])-1,parseInt(s[2]));
			var e = filterEndDate.split("-");
			var endDate = new Date(parseInt(e[0]),parseInt(e[1])-1,parseInt(e[2]));
			if(startDate > endDate){
				document.getElementById("errorMessage").innerHTML = "Start Date must be later than End Date!";
			}else{
				flyToPage("filter");
			}
		}
		else{
			document.getElementById("errorMessage").innerHTML = "Start Date and End Date must be either both filled or emptied!";
		}
	}
	function balance(){
		if(confirm("Are you sure you want to balance petty cash?")){
			flyToPage("balancing");
		}
	}
	function flyToPage(task){
		document.forms[1].task.value=task;
		document.forms[1].submit();
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/pettyCash" method="post">
	<html:hidden property="task" name="pettyCashForm"/>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Petty Cash</h1>
			</div>
		</div>
		<div class="row" style="margin-top: 1%;">
			<div class="col-lg-12">
				<label>Remaining Balance: Rp.<bean:write name="pettyCashForm" property="remainingBalance" format="#" />,-</label>
				<div class="pull-right">
					<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('export')">Export to PDF</button>
					<button type="button" class="btn btn-primary" onclick="javascript:balance()">Balancing</button>
				</div>
				<br />
				<br />
			</div>
		</div>
		<div class="col-lg-12"
			style="border: solid 2px gray; border-radius: 10px; background-color: #EFEFEF;">
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-1">Category</div>
					<div class="col-md-11">
						<html:select property="categoryId" name="pettyCashForm" styleClass="form-control" style="width: 50%;">
							<html:option value="">All</html:option>
							<html:optionsCollection property="cashFlowCategoryList" label="name" value="cashFlowCategoryId" name="pettyCashForm" />
						</html:select><br />
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-1">From</div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;" name="filterStartDate" value="<bean:write property="filterStartDate" name="pettyCashForm" />"/>
					</div>
					<div class="col-md-1">To</div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;" name="filterEndDate" value="<bean:write property="filterEndDate" name="pettyCashForm" />"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
				<button type="button" class="btn btn-primary" onclick="javascript:filter()">Filter</button>
				<span id="errorMessage" style="color:red">
			</div>
		</div>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel-body">
					<div class="table-responsive" style="height:200px;overflow: auto;">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Date</th>
									<th>Category</th>
									<th>Description</th>
									<th>Debit</th>
									<th>Credit</th>
									<th>Balance</th>
								</tr>
							</thead>
							<tbody>
								<logic:notEmpty property="transactionList" name="pettyCashForm">
									<logic:iterate id="pettyCash" property="transactionList" name="pettyCashForm">
										<tr>
											<td><bean:write property="transactionDate" name="pettyCash"/></td>
											<td><bean:write property="cashFlowCategoryName" name="pettyCash"/></td>
											<td><bean:write property="description" name="pettyCash"/></td>
											<logic:equal value="1" name="pettyCash" property="isDebit">
												<td>Rp.<bean:write property="amount" name="pettyCash" format="#"/></td>
												<td></td>
											</logic:equal>
											<logic:equal value="0" name="pettyCash" property="isDebit">
												<td></td>
												<td>Rp.<bean:write property="amount" name="pettyCash" format="#"/></td>
											</logic:equal>
											<td>Rp.<bean:write property="balance" name="pettyCash" format="#"/></td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
							</tbody>
						</table>
					</div>
					<div class="pull-right">
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('debit')">Debit</button>
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('credit')">Credit</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
