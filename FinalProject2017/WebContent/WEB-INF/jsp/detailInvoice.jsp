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
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
		<html:hidden property="task" name="invoiceForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Invoice Detail</h1>
					<div class="panel-body">
						<div>Invoice No: <bean:write name="invoiceForm" property="invoiceBean.invoiceNumber"/></div>
						<div>Date: <bean:write name="invoiceForm" property="invoiceBean.invoiceDate"/></div>
						<div>
							<table border="1">
								<tr>
									<td>Name</td>
									<td><bean:write name="invoiceForm" property="clientBean.name"/></td>
									<td>City</td>
									<td><bean:write name="invoiceForm" property="clientBean.city"/></td>
								</tr>
								<tr>
									<td rowspan="3">Address</td>
									<td rowspan="3"><bean:write name="invoiceForm" property="clientBean.address"/></td>
									<td>Telp</td>
									<td><bean:write name="invoiceForm" property="clientBean.phoneNumber"/></td>
								</tr>
								<tr>
									<td>Zip Code</td>
									<td><bean:write name="invoiceForm" property="clientBean.postalCode"/></td>
								</tr>
								<tr>
									<td>Fax</td>
									<td><bean:write name="invoiceForm" property="clientBean.faxNumber"/></td>
								</tr>
							</table>
						</div>
						<br>
						<div>
							<table border="1">
								<tr>
									<th>No.</th>
									<th>Description</th>
									<th>Unit Price</th>
									<th>Total</th>
									<th>Notes</th>
								</tr>
								<tr>
									<td>No</td>
									<td>Description</td>
									<td>Unit Price</td>
									<td>Total</td>
									<td>Notes</td>
								</tr>
								<tr>
									<td colspan="3">Total</td>
									<td colspan="2"><bean:write name="invoiceForm" property="invoiceBean.totalNet" format="#"/></td>
								</tr>
								<tr>
									<td colspan="3">PPN 10%</td>
									<td colspan="2"><bean:write name="invoiceForm" property="invoiceBean.ppnPercentage" format="#"/></td>
								</tr>
								<tr>
									<td colspan="3">Grand Total</td>
									<td colspan="2"><bean:write name="invoiceForm" property="invoiceBean.totalGross" format="#"/></td>
								</tr>
							</table>
						</div>
						<br>
						<div>
							<table border="1">
								<tr>
									<td>Note</td>
									<td>Note Content</td>
								</tr>
							</table>
						</div>
						<br>
						<div>Note Content</div>
						<br>
						<div>Signature</div>
						<input type="button" value="Back" class="btn btn-primary" onclick="javascript:flyToPage('invoice')">
						<logic:equal name="invoiceForm" property="statusId" value="1">
							<input type="button" value="Edit" class="btn btn-primary">
						</logic:equal>
						<logic:equal name="invoiceForm" property="statusId" value="2">
							<input type="button" value="Edit" class="btn btn-primary">
						</logic:equal>
						<input type="button" value="Export" class="btn btn-primary">
					</div>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>