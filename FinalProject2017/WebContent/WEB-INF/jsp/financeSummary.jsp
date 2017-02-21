<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Summary</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<div id="page-wrapper">
	    <div class="row">
	        <div class="col-lg-12">
	            <h1 class="page-header">Expense Summary</h1>
	           
	            <div class="col-lg-12">
	            	<div class="col-md-6" style="padding:10px;"><strong>
	            		Current Date: <%= new java.util.Date() %>
	            		</strong>
	            	</div>
	            </div>
	            <div class="col-lg-12">
	            	<div class="col-md-6">Cash in Bank Balance: <bean:write name="indexForm" property="cashInBankBalance" format="IDR #,###.##"/></div>
	            	<div class="col-md-6" style="text-align:right">Petty Cash Balance: <bean:write name="indexForm" property="pettyCashBalance" format="IDR #,###.##"/></div>
	            </div>
	            <div class="col-lg-12">
		            <div class="panel-body">
						<div class="table-responsive" style="height:400px;overflow:auto;">
						    <table class="table table-hover">
						        <thead>
						            <tr>
						                <th>Period</th>
						                <th>Cash In Bank Expense</th>
						                <th>Petty Cash Expense</th>
						            </tr>
						        </thead>
						        <tbody>
						        	<logic:notEmpty property="financeSummaryList" name="indexForm">
						        		<logic:iterate id="financeSummary" property="financeSummaryList" name="indexForm">
						        			<tr>
								                <td><bean:write name="financeSummary" property="period"/></td>
								                <td><bean:write name="financeSummary" property="cashInBankExpense" format="#,###.##"/></td>
								                <td><bean:write name="financeSummary" property="pettyCashExpense" format="#,###.##"/></td>
								            </tr>  
						        		</logic:iterate>
						        	</logic:notEmpty>						                   
						         </tbody>
	                		</table>
	                	</div>
	        		</div>
	            </div>
	        </div>
	    </div>
    </div>
</body>
</html>