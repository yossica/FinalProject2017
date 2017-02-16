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
	function flyToPage(task)
	{
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Invoice List</h1>
	            <div class="panel-body" style="padding-right:0;">
		            <div class="pull-right">
		           		<button type="button" class="btn btn-primary">Print</button>
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
        				<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('filter')">Filter</button>
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
			                	<td><bean:write name="inv" property="periodMonth" format="#"/>&nbsp;<bean:write name="inv" property="periodYear" format="#"/></td>
			                	<td><bean:write name="inv" property="invoiceTypeName"/></td>
			                	<td><bean:write name="inv" property="invoiceDate"/></td>
			                	<td><bean:write name="inv" property="statusInvoiceName"/></td>
			                	<td>
			                		<input type="button" value="View" class="btn btn-primary" onclick="javascript:flyToPage('detailInvoice')">
			                		<input type="button" value="Change Status" class="btn btn-primary">
			                	</td>
	                		</tr>
	                	</logic:iterate>
	                </tbody>
	            </table>
	        </div>
        <!-- /.table-responsive -->
    	</div>
    </div>
    </html:form>
</body>
</html>