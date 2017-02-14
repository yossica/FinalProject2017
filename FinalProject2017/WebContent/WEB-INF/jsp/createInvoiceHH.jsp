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
<script>
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Create Invoice (Cont)</h1>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2">Invoice Date</div>
					<div class="col-md-5">
						<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2">Client</div>
					<div class="col-md-5">
						<bean:write name="invoiceForm" property="invoiceBean.clientId" format="#"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Contract Service</div>
					<div class="col-md-5">
						<bean:write name="invoiceForm" property="invoiceBean.invoiceTypeId" format="#"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Period</div>
					<div class="col-md-5">
						Bulan Tahun pembuatan
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2">Tax</div>
					<div class="col-md-5">
						<bean:write name="invoiceForm" property="invoiceBean.isGross" format="#"/>
					</div>
				</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
