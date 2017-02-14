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
	    	<%-- <bean:write name="invoiceForm" property="task"/>
	    	<bean:write name="invoiceForm" property="message"/> --%>
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
									<option selected disabled>Select</option>
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
			            	<select class="form-control" name="monthFrom">
	                            <option>Select</option>
	                            <option>January</option>
	                            <option>February</option>
	                            <option>March</option>
	                            <option>April</option>
	                            <option>June</option>
	                            <option>July</option>
	                            <option>August</option>
	                            <option>September</option>
	                            <option>October</option>
	                            <option>November</option>
	                            <option>December</option>
	                        </select>
	                     </div>
	                     <div class="col-md-2">
	                        <select class="form-control" name="yearFrom">
	                        	<option>Select</option>
	                        	<%
	                        		int year = Calendar.getInstance().get(Calendar.YEAR);
	                        		for(int i=2000;i<=year;i++){
	                        		%>
	                        			<option><%= i %></option>
	                        		<% 
	                        		}
	                        	%>
	                        </select>
                        </div>
                        <div class="col-md-1">
                        	To
                        </div>
                        <div class="col-md-3">
	                        <select class="form-control" name="monthTo">
	                            <option>Select</option>
	                            <option>January</option>
	                            <option>February</option>
	                            <option>March</option>
	                            <option>April</option>
	                            <option>June</option>
	                            <option>July</option>
	                            <option>August</option>
	                            <option>September</option>
	                            <option>October</option>
	                            <option>November</option>
	                            <option>December</option>
	                        </select>
	                    </div>
	                    <div class="col-md-2">
	                        <select class="form-control" name="yearTo">
	                            <option>Select</option>
	                        	<%
	                        		year = Calendar.getInstance().get(Calendar.YEAR);
	                        		for(int i=2000;i<=year;i++){
	                        		%>
	                        			<option><%= i %></option>
	                        		<% 
	                        		}
	                        	%>
	                        </select>
	                    </div>
	              	</div>
	              	</div>
	              	<div class="col-md-10" style="margin-top:10px;">   
	                    <div class="row">
		                    <div class="col-md-1">   
				            	Status
				            </div>
				            <div class="col-md-3">  
				            	<select class="form-control">
		                            <option>Select</option>
		                            <option>Created</option>
		                            <option>Sent</option>
		                            <option>Paid</option>
		                            <option>Canceled</option>
		                        </select>
			            	</div>
		            	</div>
        			</div>
        			<div class="col-md-12" style="margin-top:10px;margin-bottom:10px;">
        				<button type="button" class="btn btn-primary">Filter</button>
        			</div>
        		</div>
	        </div>
	        <!-- /.col-lg-12 -->
	    </div>
	    <div class="panel-body">
	        <div class="table-responsive">
	        	<table class="table table-hover">
	            	<tr>
				   		<th>Client</th>
				   		<th>Period</th>
				   		<th>Service</th>
				   		<th>Invoice Date</th>
				   		<th>Status</th>
				   		<th>Action</th>
				   	</tr>
	                <tbody>
	                <tr>
	                	<td>Client A</td>
	                	<td>Jan 2016</td>
	                	<td>Professional Service</td>
	                	<td>02/02/16</td>
	                	<td>Created</td>
	                	<td><input type="button" value="View" class="btn btn-primary">
	                		<input type="button" value="Change Status" class="btn btn-primary"></td>
	                </tr>
	                </tbody>
	            </table>
	        </div>
        <!-- /.table-responsive -->
    	</div>
    </div>
    </html:form>
</body>
</html>