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
<script type="text/javascript">
	function scrollBottom(){
		var objDiv = document.getElementById("listPettyCash");
	    objDiv.scrollTop = objDiv.scrollHeight;		
	}
	function toggleFilter() {
		var filter = document.getElementById("filterForm");
		filter.style.display = filter.style.display === 'none' ? '' : 'none';
		if (filter.style.display == 'none'){
			document.getElementById("listPettyCash").style.height = "400px";
		}else{
			document.getElementById("listPettyCash").style.height = "200px";
		}
	}
	
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
				//document.getElementById("errorMessage").innerHTML = "Start Date must be later than End Date!";
				sweetAlert("Oops...", "Start Date must be later than End Date!", "error");
			}else{
				flyToPage("filter");
			}
		}
		else{
			//document.getElementById("errorMessage").innerHTML = "Start Date and End Date must be either both filled or emptied!";
			sweetAlert("Oops...", "Start Date and End Date must be either both filled or emptied!", "error");
		}
	}
	function balance(){
		swal({
			  title: "Are you sure?",
			  text: "System will rebalance Petty Cash ",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Yes, Rebalance",
			  cancelButtonText: "No, Cancel Please!",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm){
			  if (isConfirm) {
				/* flyToPage("balancing"); */
				 swal({title: "Good job!",
					  text: "Transaction Success!",
					  type: "success"}
					 ,function(){
					  setTimeout(function(){
						  flyToPage("balancing");
						  }, 10);
						});   
			  } else {
			    swal("Cancelled", "Cancel Rebalance Cash in Bank", "error");
			  }
			});
	}
	function flyToPage(task){
		document.forms[1].task.value=task;
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
</head>
<body onload="scrollBottom()">
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
				<label>Remaining Balance: <bean:write name="pettyCashForm" property="remainingBalance" format="IDR #,###.##" /></label>
				<div class="pull-right">
					<button id="filterButton" type="button" class="btn btn-primary" onclick="javascript:toggleFilter()">Toggle Filter</button>
					<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('export')">Export to PDF</button>
					<button type="button" class="btn btn-primary" onclick="javascript:balance()">Balancing</button>
				</div>
				<br />
				<br />
			</div>
		</div>
		<div id="filterForm"  class="col-lg-12"
			style="border: solid 2px gray; border-radius: 10px; background-color: #EFEFEF; display: none;">
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-12" style="padding-right: 1%">
					<div class="col-md-2">Category</div>
					<div class="col-md-10">
						<html:select property="categoryId" name="pettyCashForm" styleClass="form-control-client" >
							<html:option value="">All</html:option>
							<html:optionsCollection property="cashFlowCategoryList" label="name" value="cashFlowCategoryId" name="pettyCashForm" />
						</html:select><br />
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">From</div>
					<div class="col-md-4">
						<input type="date" class="form-control" style="width: 100%;" name="filterStartDate" value="<bean:write property="filterStartDate" name="pettyCashForm" />"/>
					</div>
					<div class="col-md-2">To</div>
					<div class="col-md-4">
						<input type="date" class="form-control" style="width: 100%;" name="filterEndDate" value="<bean:write property="filterEndDate" name="pettyCashForm" />"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
				<button type="button" class="btn btn-primary" onclick="javascript:filter()">Filter</button>
				<span id="errorMessage" style="color:red"></span>
			</div>
		</div>
		<logic:notEmpty name="pettyCashForm"
						property="messageList">
						<logic:iterate id="message" name="pettyCashForm"
							property="messageList">
							<input type="hidden" id="err" value="<bean:write name="message" />">
						</logic:iterate>
					</logic:notEmpty>
		<div class="row">
			<div class="col-lg-12">
				<div class="panel-body">
					<div id="listPettyCash" class="table-responsive" style="height:400px;overflow: auto;">
						<table class="table table-hover">
							<thead>
								<tr>
									<th>Date</th>
									<th>Category</th>
									<th>Description</th>
									<th>Debit (IDR)</th>
									<th>Credit (IDR)</th>
									<th>Balance (IDR)</th>
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
												<td><bean:write property="amount" name="pettyCash" format="#,###.##"/></td>
												<td></td>
											</logic:equal>
											<logic:equal value="0" name="pettyCash" property="isDebit">
												<td></td>
												<td><bean:write property="amount" name="pettyCash" format="#,###.##"/></td>
											</logic:equal>
											<td><bean:write property="balance" name="pettyCash" format="#,###.##"/></td>
										</tr>
									</logic:iterate>
								</logic:notEmpty>
								<logic:empty property="transactionList" name="pettyCashForm">
									<tr>
										<td colspan="6" align="center">There are no data based on this filter</td>
									</tr>
								</logic:empty>
							</tbody>
						</table>
					</div>
					<div class="pull-right" style="margin-top: 15px;">
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
