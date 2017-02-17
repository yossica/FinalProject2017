<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function flyToSave() {
		document.forms[1].submit();
	}
</script>
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
		<html:hidden property="task" name="invoiceForm" />
		<html:hidden property="clientId" name="invoiceForm"/>
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Form Client</h1>
					<div class="panel-body">
						<div class="pull-left">Invoice No: <bean:write name="invoiceForm" property="invoiceBean.invoiceNumber"/> </div>
						<div class="pull-right">Date: <bean:write name="invoiceForm" property="invoiceBean.invoiceDate"/></div>
						<%-- <div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td>Invoice No: </td>
										<td><bean:write name="invoiceForm" property="invoiceBean.invoiceNumber"/></td>
									</tr>
									<tr>
										<td>Address</td>
										<td><html:text name="invoiceForm" property="address" /></td>
									</tr>
									<tr>
										<td>City</td>
										<td><html:text name="invoiceForm" property="city" /></td>
									</tr>
									<tr>
										<td>Phone</td>
										<td><html:text name="invoiceForm" property="phoneNumber" /></td>
									</tr>
									<tr>
										<td>Fax</td>
										<td><html:text name="invoiceForm" property="faxNumber" /></td>
									</tr>
									<tr>
										<td>Postal Code</td>
										<td><html:text name="invoiceForm" property="postalCode" /></td>
									</tr>
									<tr>
										<td>Client Status</td>
										<td><html:select name="invoiceForm" property="isEnabled">
												<html:option value="1">Enabled</html:option>
												<html:option value="0">Disabled</html:option>
												</html:select>
										</td>
									</tr>
								</tbody>
							</table>
						</div> --%>
						<!-- /.table-responsive -->
					</div>
					<table border="1">

					</table>
					<button type="button" class="btn btn-primary pull-right"
						onclick="javascript:flyToSave()">Save</button>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>