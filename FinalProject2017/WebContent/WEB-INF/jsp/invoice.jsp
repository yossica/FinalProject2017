<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoice</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/invoice" method="post"></html:form>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Invoice List</h1>
	            <div class="col-lg-11" style="float:right;">
		            <button type="button" class="btn btn-outline btn-success">Print</button>
		            <button type="button" class="btn btn-outline btn-success">Create</button>
	            </div>
	            <div class="col-lg-10">
		            <div class="col-lg-9" style="border:solid 2px gray;margin-top:65px;">
		            	<div class="row">
			            	<div class="col-md-1">
			            	Client
			            	</div>
			            	<div class="col-md-8">
				            <select class="form-control-client">
	                            <option>1</option>
	                            <option>2</option>
	                            <option>3</option>
	                            <option>4</option>
	                            <option>5</option>
	                        </select>
			            	</div>
		            	
                        </div>
		            	<br>Period
		            	<br>From 
		            	<select class="form-control">
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
                        <select class="form-control">
                            <option>1</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
                        To
                        <select class="form-control">
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
                        <select class="form-control">
                            <option>Select</option>
                            <option>2</option>
                            <option>3</option>
                            <option>4</option>
                            <option>5</option>
                        </select>
		            	<br>Status
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
	        <!-- /.col-lg-12 -->
	    </div>
    </div>
</body>
</html>