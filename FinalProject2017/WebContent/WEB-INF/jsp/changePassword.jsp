<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Change Password</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>

	<html:form action="/user" method="post"></html:form>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Change Password</h1>
	            <div class="col-lg-11">
		            <button type="button" class="btn btn-primary ">Cancel</button>
		            <button type="button" class="btn btn-primary ">Save</button>
	            </div>
	            <div class="col-lg-10">
		            <div class="col-lg-9" style="border:solid 2px gray;margin-top:15px;">
		            	<div class="row">
			            	<div class="col-md-2">
			            	Username
			            	</div>
			            	<div class="col-md-1">
			            	:
			            	</div>
			            	<div class="col-md-8">
					            username diambil dari db terus di readonly
			            	</div>
                        </div>
                        <br/>
                        <div class="row">
			            	<div class="col-md-2">
			            	Password
			            	</div>
			            	<div class="col-md-1">
			            	:
			            	</div>
			            	<div class="col-md-8">
					            <input type="password" />
			            	</div>
                        </div>
                        <br/>
                        <div class="row">
			            	<div class="col-md-2">
			            	New Password
			            	</div>
			            	<div class="col-md-1">
			            	:
			            	</div>
			            	<div class="col-md-8">
					            <input type="password" />
			            	</div>
                        </div>
                        <br/>
                        <div class="row">
			            	<div class="col-md-2">
			            	Confirm New Password
			            	</div>
			            	<div class="col-md-1">
			            	:
			            	</div>
			            	<div class="col-md-8">
					            <input type="password" />
			            	</div>
                        </div>
                        
		            	
		            </div>
	            </div>
	        </div>
	        <!-- /.col-lg-12 -->
	    </div>
    </div>
</body>
</html>