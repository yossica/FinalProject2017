<%-- Uncomment ketika mau buat redirect jika belum login
	 harus dicopas ke semua page (ga bisa taro dashboard :( --%>
<%
	if (session.getAttribute("username") == null
			|| "".equals(session.getAttribute("username")))
		response.sendRedirect("/FinalProject2017/login.do");
%>
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
<link href="asset/bootstrap/css/bootstrap.css" type="text/css"
	rel="stylesheet" media="all">
<link href="asset/metisMenu/metisMenu.css" rel="stylesheet">
<link href="dist/css/sb-admin-2.css" rel="stylesheet">
<link href="asset/font-awesome/css/font-awesome.css" rel="stylesheet"
	type="text/css">
<script>
	function flyToCreatePS(task, clientId, invoiceTypeId, periodMonth,
			periodYear) {
		document.forms[1].task.value = task;
		document.forms[1].clientId.value = clientId;
		document.forms[1].invoiceTypeId.value = invoiceTypeId;
		document.forms[1].periodMonth.value = periodMonth;
		document.forms[1].periodYear.value = periodYear;
		document.forms[1].submit();
	}
	function flyToCreateTr(task, clientId, invoiceTypeId, settlementInvoiceId) {
		document.forms[1].task.value = task;
		document.forms[1].clientId.value = clientId;
		document.forms[1].invoiceTypeId.value = invoiceTypeId;
		document.forms[1].settlementInvoiceId.value = settlementInvoiceId;
		document.forms[1].submit();
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
		<html:hidden property="task" name="invoiceForm" />
		<html:hidden property="clientId" name="invoiceForm" />
		<html:hidden property="invoiceTypeId" name="invoiceForm" />
		<html:hidden property="periodMonth" name="invoiceForm" />
		<html:hidden property="periodYear" name="invoiceForm" />
		<html:hidden property="settlementInvoiceId" name="invoiceForm" />
	</html:form>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Monthly Notifications</h1>
			</div>
		</div>
		<!-- Table 1 -->
		<div class="row">
			<div class="col-lg-6" style="width: 1200px">
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading" style="background-color:#ffc4c1/* #d5d9e0 */">Listed</div>
						<!-- /.panel-heading -->
						<div class="panel-body" style="height: 450px; overflow: auto;background-color:/* #ffc4c1 */ #ffd6d6 ;">
							<div class="table-responsive" >
								<table class="table table-hover">
									<tbody style="background-color:/* #ffc4c1 */#ffd6d6;">
										<logic:notEmpty name="indexForm"
											property="listedRemainderList">
											<logic:iterate id="list" name="indexForm"
												property="listedRemainderList">
												<tr>
													<td
														onclick="flyToCreatePS('createInvoiceIndex',
		                                        										 '<bean:write name="list" property="clientId" format="#"/>',
		                                        										 '<bean:write name="list" property="invoiceTypeId" format="#"/>',
		                                        										 '<bean:write name="list" property="periodMonth" format="#"/>',
		                                        										 '<bean:write name="list" property="periodYear" format="#"/>'
		                                        										)"
														style="cursor: pointer"><bean:write name="list"
															property="clientName" /> - Professional Service<br>
														Period: <bean:write name="list" property="periodMonth"
															format="#" />/<bean:write name="list"
															property="periodYear" format="#" />
														<p align="right">
															<bean:write name="list" property="invoiceDate" />
														</p></td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:notEmpty name="indexForm"
											property="listedTrainingRemainderList">
											<logic:iterate id="list" name="indexForm"
												property="listedTrainingRemainderList">
												<tr>
													<td
														onclick="flyToCreateTr('createInvoiceIndexTr',
		                                        									'<bean:write name="list" property="clientId" format="#"/>',
		                                        									3,
		                                        									'<bean:write name="list" property="settlementInvoiceId" format="#"/>'
		                                        								)"
														style="cursor: pointer"><bean:write name="list"
															property="clientName" /> - Training Settlement<br>
														Description : <bean:write name="list"
															property="description" /></td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="indexForm" property="listedRemainderList">
											<logic:empty name="indexForm"
												property="listedTrainingRemainderList">
												<tr>
													<td>Empty List Listed</td>
												</tr>
											</logic:empty>
										</logic:empty>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
					<!-- /.panel -->
				</div>
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading" style="background-color:#ffcb87">Created</div>
						<!-- /.panel-heading -->
						<div class="panel-body" style="height: 450px; overflow: auto; background-color:#ffdcaf">
							<div class="table-responsive">
								<table class="table table-hover">
									<tbody style="background-color:#ffdcaf; ">
										<logic:notEmpty name="indexForm"
											property="createdRemainderList">
											<logic:iterate id="list" name="indexForm"
												property="createdRemainderList">
												<tr>
													<td><bean:write name="list" property="clientName" /> -
														<bean:write name="list" property="invoiceTypeName" /><br>
														<bean:write name="list" property="invoiceNumber" />
														<p align="right">
															<bean:write name="list" property="invoiceDate" />
														</p></td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="indexForm" property="createdRemainderList">
											<tr>
												<td>Empty List</td>
											</tr>
										</logic:empty>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
				</div>
				<!-- /.panel -->
				<div class="col-lg-4">
					<div class="panel panel-default">
						<div class="panel-heading" style="background-color:#a5d3ff">Sent</div>
						<!-- /.panel-heading -->
						<div class="panel-body" style="height: 450px; overflow: auto;background-color:#e2edff;">
							<div class="table-responsive" >
								<table class="table table-hover">
									<tbody style="background-color:#e2edff">
										<logic:notEmpty name="indexForm"
											property="sentOutsourceRemainderList">
											<logic:iterate id="list" name="indexForm"
												property="sentOutsourceRemainderList">
												<tr>
													<td><bean:write name="list" property="clientName" /> -
														<bean:write name="list" property="invoiceTypeName" /><br>
														<bean:write name="list" property="invoiceNumber" />
														<p align="right">
															<bean:write name="list" property="invoiceDate" />
														</p></td>
												</tr>
											</logic:iterate>
										</logic:notEmpty>
										<logic:empty name="indexForm"
											property="sentOutsourceRemainderList">
											<tr>
												<td>Empty List</td>
											</tr>
										</logic:empty>
									</tbody>
								</table>
							</div>
							<!-- /.table-responsive -->
						</div>
						<!-- /.panel-body -->
					</div>
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-6 -->
		</div>
	</div>
</body>
</html>
