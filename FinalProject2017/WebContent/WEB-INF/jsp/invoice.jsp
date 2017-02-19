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
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<html:hidden property="statusId" name="invoiceForm"/>
	<html:hidden property="invoiceNumber" name="invoiceForm"/>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Invoice List</h1>
	            <div class="panel-body" style="padding-right:0;">
		            <div class="pull-right">
		           		<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('export')">Export To PDF</button>
			            <button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Create</button>
		            </div>
	            </div>
	            <div class="col-lg-12" style="border:solid 2px gray;border-radius: 10px; background-color: #EFEFEF;">
	            	<div class="row" style="margin-top:10px;">
		            	<div class="col-md-10" style="padding-right:1%">
			            	<div class="col-md-1">
			            	Client
			            	</div>
			            	<div class="col-md-11">
				            	<html:select property="clientId" name="invoiceForm" styleClass="form-control-client">
									<html:option value="">Select</html:option>
									<html:optionsCollection name="invoiceForm" property="clientList" label="name" value="clientId"/>
								</html:select>
			            	</div>
			            </div>
                       </div>
                <div class="col-md-10" style="margin-top:10px;">
	            	<div class="row">
		            	<div class="col-md-1">
		            		From 
		            	</div>
		            	<div class="col-md-3">
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
                        <div class="col-md-3">
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
	              	<div class="col-md-10" style="margin-top:10px;">   
	                    <div class="row">
		                    <div class="col-md-1">   
				            	Status
				            </div>
				            <div class="col-md-3">
		                        <html:select property="statusInvoiceId" name="invoiceForm" styleClass="form-control">
		                        	<html:option value="">Select</html:option>
		                        	<html:optionsCollection property="statusInvoiceList" label="name" value="statusInvoiceId" name="invoiceForm"/>
		                        </html:select>
			            	</div>
		            	</div>
        			</div>
        			<div class="col-md-12" style="margin-top:10px;margin-bottom:10px;">
        				<button type="button" class="btn btn-primary" onclick="javascript:filter()">Filter</button>
        				<span id="errorMessage" style="color:red">
        			</div>
        		</div>
	        </div>
	        <!-- /.col-lg-12 -->
	    </div>
	    <div class="panel-body">
	        <div class="table-responsive">
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

			                		<input type="button" value="View" class="btn btn-primary" onclick="javascript:flyToPage('detailInvoice')">
			                		<logic:equal name="inv" property="statusInvoiceName" value="Created">
			                			<input type="button" value="Change Status" class="btn btn-primary" 
			                					onclick="javascript:flyToChangeStatus(
			                								'<bean:write name="inv" property="invoiceNumber"/>',
			                								'<bean:write name="inv" property="statusInvoiceId" format="#"/>')">
			                		</logic:equal>
			                		<logic:equal name="inv" property="statusInvoiceName" value="Sent">
			                			<input type="button" value="Change Status" class="btn btn-primary" 
			                					onclick="javascript:flyToChangeStatus(
			                								'<bean:write name="inv" property="invoiceNumber"/>',
			                								'<bean:write name="inv" property="statusInvoiceId" format="#"/>')">
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