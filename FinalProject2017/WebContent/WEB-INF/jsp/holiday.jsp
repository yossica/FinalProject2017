<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Holiday</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/holiday" method="post"></html:form>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Holiday List</h1>
	            <div class="col-lg-11">
	            	<div class="col-md-8">
			            <html:textarea property="holidayCsv" name="holidayForm" style="width:80%;height:200px"></html:textarea>
			            
			           	<br/><button type="button" class="btn btn-primary">Insert CSV</button>
		  			</div>
		  			<div class="col-md-4" style="color:red;overflow: auto;" id="errorMessage">
		  				error messages here
		  			</div>
	            </div>
	            <div class="col-lg-10">
		            <div class="panel-body">
						<div class="table-responsive" style="overflow: auto;">
						    <table class="table table-hover">
						        <thead>
						            <tr>
						                <th>Date</th>
						                <th>Description</th>
						                <th>Action</th>
						            </tr>
						        </thead>
						        <tbody>
						            <tr>
						                <td>15 FEB 2017</td>
						                <td>Pilkada DKI Jakarta</td>
						                <td><a href="#" onclick="">X</a></td>
						            </tr>         
						         </tbody>
	                		</table>
	                	</div>
	        		</div>
	            </div>
	        </div>
	        <!-- /.col-lg-12 -->
	    </div>
    </div>
</body>
</html>