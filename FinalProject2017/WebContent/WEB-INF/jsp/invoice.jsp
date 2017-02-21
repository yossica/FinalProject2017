<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<%@ page import="java.util.Calendar" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoice</title>
<script>
	function toggleFilter() {
		var filter = document.getElementById("filterForm");
		filter.style.display = filter.style.display === 'none' ? '' : 'none';
		if (filter.style.display == 'none'){
			document.getElementById("listInvoice").style.height = "400px";
		}else{
			document.getElementById("listInvoice").style.height = "200px";
		}
	}
	function filter() {
		var monthFrom = document.forms[1].monthFrom.value;
		var yearFrom = document.forms[1].yearFrom.value;
		var monthTo = document.forms[1].monthTo.value;
		var yearTo = document.forms[1].yearTo.value;
		
		if (monthFrom == "" && yearFrom == "" && monthTo == "" && yearTo == "") {
			flyToPage('filter');
		} else if (monthFrom != "" && yearFrom != "" && monthTo != "" && yearTo != "") {
			if (monthFrom>monthTo && yearFrom==yearTo) {
				document.getElementById("errorMessage").innerHTML = "Start month period must before end month period!";
			} else if (yearFrom>yearTo) {
				document.getElementById("errorMessage").innerHTML = "Start year period must before end year period!";
			} else {
				flyToPage('filter');
			}
		} else {
			document.getElementById("errorMessage").innerHTML = "Start month and year period and month and year period must be either both filled or emptied!";
		}
	}
	function flyToPage(task)
	{
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function flyToChangeStatus(invoiceNumber,statusId){
		if(statusId == 4){
			swal({
				  title: "Are you sure?",
				  text: "System will change this invoice status to 'Cancelled'",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "Yes, Cancel this Invoice",
				  cancelButtonText: "No, Cancel",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
						document.forms[1].invoiceNumber.value = invoiceNumber;
						document.forms[1].statusId.value = statusId;
						flyToPage("changeStatus");
				  } else {
				    swal("Cancelled", "Cancel 'Cancel Invoice'", "error");
				  }
				});
		}
		else{
			swal({
				  title: "Are you sure?",
				  text: "System will change current status into next status",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "Yes, Change Status",
				  cancelButtonText: "No, Cancel",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
						document.forms[1].invoiceNumber.value = invoiceNumber;
						document.forms[1].statusId.value = statusId;
						flyToPage("changeStatus");
				  } else {
				    swal("Cancelled", "Cancel Change Status", "error");
				  }
				});
		}
	}
	function flyToDetail(transactionInvoiceHeaderId, clientId, statusId){
		document.forms[1].transactionInvoiceHeaderId.value = transactionInvoiceHeaderId;
		document.forms[1].client.value = clientId;
		document.forms[1].statusId.value = statusId;
		flyToPage("detailInvoice");
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
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<html:hidden property="client" name="invoiceForm"/>
	<html:hidden property="statusId" name="invoiceForm"/>
	<html:hidden property="invoiceTypeId" name="invoiceForm"/>
	<html:hidden property="transactionInvoiceHeaderId" name="invoiceForm"/>
	<html:hidden property="invoiceNumber" name="invoiceForm"/>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Invoice List</h1>
	            <div class="panel-body" style="padding-right:0;">
		            <div class="pull-right">
		            	<button id="filterButton" type="button" class="btn btn-primary" onclick="javascript:toggleFilter()">Toggle Filter</button>
		           		<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('export')">Export To PDF</button>
			            <button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Create</button>
		            </div>
	            </div>
	            <div id="filterForm" class="col-lg-12" style="border:solid 2px gray;border-radius: 10px; background-color: #EFEFEF; display: none;">
	            	<div class="row" style="margin-top:10px;">
		            	<div class="col-md-12" style="padding-right:1%">
			            	<div class="col-md-2">
			            	Client
			            	</div>
			            	<div class="col-md-10">
				            	<html:select property="clientId" name="invoiceForm" styleClass="form-control-client">
									<html:option value="">Select</html:option>
									<html:optionsCollection name="invoiceForm" property="clientList" label="name" value="clientId"/>
								</html:select>
			            	</div>
			            </div>
                       </div>
                <div class="col-md-12" style="margin-top:10px;">
	            	<div class="row">
		            	<div class="col-md-2">
		            		From 
		            	</div>
		            	<div class="col-md-2">
		            		<html:select property="monthFrom" name="invoiceForm" styleClass="form-control">
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
	                        <html:select property="yearFrom" name="invoiceForm" styleClass="form-control">
	                        	<html:option value="">Select</html:option>
	                        	<%
	                        		int year = Calendar.getInstance().get(Calendar.YEAR);
	                        		for(int i=2000;i<=year;i++){
	                        		%>
	                        			<html:option value='<%= i+"" %>'><%= i %></html:option>
	                        		<% 
	                        		}
	                        	%>
	                        </html:select>
                        </div>
                        <div class="col-md-1">
                        	To
                        </div>
                        <div class="col-md-2">
	                        <html:select property="monthTo" name="invoiceForm" styleClass="form-control">
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
	                        <html:select property="yearTo" name="invoiceForm" styleClass="form-control">
	                        	<html:option value="">Select</html:option>
	                        	<%
	                        		int year = Calendar.getInstance().get(Calendar.YEAR);
	                        		for(int i=2000;i<=year;i++){
	                        		%>
	                        			<html:option value='<%= i+"" %>'><%= i %></html:option>
	                        		<% 
	                        		}
	                        	%>
	                        </html:select>
	                    </div>
	              	</div>
	              	</div>
	              	<div class="col-md-12" style="margin-top:10px;">   
	                    <div class="row">
		                    <div class="col-md-2">   
				            	Status
				            </div>
				            <div class="col-md-10">
		                        <html:select property="statusInvoiceId" name="invoiceForm" styleClass="form-control">
		                        	<html:option value="">Select</html:option>
		                        	<html:optionsCollection property="statusInvoiceList" label="name" value="statusInvoiceId" name="invoiceForm"/>
		                        </html:select>
			            	</div>
		            	</div>
        			</div>
        			<div class="col-md-12" style="margin-top:10px;margin-bottom:10px;">
        				<button type="button" class="btn btn-primary" onclick="javascript:filter()">Filter</button>
        				<!-- <span id="errorMessage" style="color:red"> -->
        			</div>
        			<div class="col-md-4" style="color: red;"
						id="message">
						<logic:notEmpty name="invoiceForm" property="messageList">
							<logic:iterate id="message" name="invoiceForm"
								property="messageList">
								<input type="hidden" id="err" value="<bean:write name="message" />">
							</logic:iterate>
						</logic:notEmpty>
					</div>
        		</div>
	        </div>
	        <!-- /.col-lg-12 -->
	    </div>
	    <div class="panel-body">
	        <div id="listInvoice" class="table-responsive" style="height:400px; overflow:auto;">
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
						<logic:iterate id="inv" name="invoiceForm" property="invoiceList">
					    	<tr>
			                	<td><bean:write name="inv" property="invoiceNumber"/></td>
			                	<td><bean:write name="inv" property="clientName"/></td>
			                	<td>
			                		<bean:write name="inv" property="periodMonthName"/>&nbsp;<bean:write name="inv" property="periodYear" format="#"/>
			                	</td>
			                	<td><bean:write name="inv" property="invoiceTypeName"/></td>
			                	<td><bean:write name="inv" property="invoiceDate"/></td>
			                	<td><bean:write name="inv" property="statusInvoiceName"/></td>
			                	<td>
			                		<input type="button" value="View" class="btn btn-primary" 
			                			onclick="javascript:flyToDetail(
			                				'<bean:write name="inv" property="transactionInvoiceHeaderId" format="#"/>',
			                				'<bean:write name="inv" property="clientId" format="#"/>',
			                				'<bean:write name="inv" property="statusInvoiceId" format="#"/>'
			                		)" />
			                		<logic:equal name="inv" property="statusInvoiceName" value="Created">
			                			<input type="button" value="Change Status" class="btn btn-primary" 
			                					onclick="javascript:flyToChangeStatus(
			                								'<bean:write name="inv" property="invoiceNumber"/>',
			                								'<bean:write name="inv" property="statusInvoiceId" format="#"/>')">
			                			<input type="button" value="Cancel" class="btn btn-primary" 
			                					onclick="javascript:flyToChangeStatus(
			                								'<bean:write name="inv" property="invoiceNumber"/>',
			                								'4')">
			                		</logic:equal>
			                		<logic:equal name="inv" property="statusInvoiceName" value="Sent">
			                			<input type="button" value="Change Status" class="btn btn-primary" 
			                					onclick="javascript:flyToChangeStatus(
			                								'<bean:write name="inv" property="invoiceNumber"/>',
			                								'<bean:write name="inv" property="statusInvoiceId" format="#"/>')">
			                			<input type="button" value="Cancel" class="btn btn-primary" 
			                					onclick="javascript:flyToChangeStatus(
			                								'<bean:write name="inv" property="invoiceNumber"/>',
			                								'4')">
			                		</logic:equal>
			                	</td>
	                		</tr>
	                	</logic:iterate>
	                </tbody>
	            </table>
	        </div>
    	</div>
    </div>
    </html:form>
</body>
</html>