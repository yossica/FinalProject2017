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
				<h1 class="page-header">Create Invoice Professional Service</h1>
			</div>
			
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">

					<div class="col-md-2"><label>Invoice Date</label></div>
					<div class="col-md-5">
						<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceDate"/>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">

					<div class="col-md-2"><label>Client</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.clientId" />
						<bean:write name="invoiceForm" property="invoiceBean.clientName"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">

					<div class="col-md-2"><label>Contract Service</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeId" />
						<bean:write name="invoiceForm" property="invoiceBean.invoiceTypeName"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">

					<div class="col-md-2"><label>Period</label></div>

					<div class="col-md-5">
						<bean:write name="invoiceForm" property="invoiceBean.periodMonth" format="#"/>
						<html:hidden name="invoiceForm" property="invoiceBean.periodMonth"/>
						 - 
						<bean:write name="invoiceForm" property="invoiceBean.periodYear" format="#"/>
						<html:hidden name="invoiceForm" property="invoiceBean.periodYear"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">

					<div class="col-md-2"><label>Tax</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.isGross" />
						<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="1">
							Include
							<html:hidden name="invoiceForm" property="invoiceBean.isGross" value="1"/>
						</logic:equal>
						<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="0">
							Exclude
							<html:hidden name="invoiceForm" property="invoiceBean.isGross" value="0"/>
						</logic:equal>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">

					<div class="col-md-2"><label>Invoice Notes</label></div>

					<div class="col-md-5">
						<html:text name="invoiceForm" property="invoiceBean.notes" />
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Outsource Data:</label></div>
				</div>
			</div>
			<div class="col-md-10" style="border:solid 1px gray;border-radius: 10px; background-color: #EFEFEF;">
				<div class="row">
					<div class="col-md-12">
						<table class="table table-hover">
							<tr>
								<th>Name</th>
								<th>Fee</th>
								<th>Total Work Days</th>
								<th>Man Days</th>
								<th>Notes</th>
							</tr>
							<tbody>
							<logic:notEmpty property="professionalServiceList" name="invoiceForm">
								<logic:iterate id="invoiceDetailPS" property="professionalServiceList"
									name="invoiceForm">
									<tr>
										<td>
											<bean:write property="employeeName" name="invoiceDetailPS" />
											<html:hidden property="employeeName" name="invoiceDetailPS" indexed="true"/>
										</td>
										<td>
											<bean:write property="fee" name="invoiceDetailPS" format="##,###.##"/>
											<html:hidden property="fee" name="invoiceDetailPS" indexed="true"/>
										</td>
										<td>
											<bean:write property="workDays" name="invoiceDetailPS" format="#,###"/>
											<html:hidden property="workDays" name="invoiceDetailPS" indexed="true"/>
										</td>
										<td>
											<html:text name="invoiceDetailPS" property="manDays" styleClass="form-control" indexed="true" value=""></html:text>
										</td>
										<td>
											<html:text name="invoiceDetailPS" property="notes" styleClass="form-control" indexed="true"></html:text>
										</td>
									</tr>
								</logic:iterate>
							</logic:notEmpty>
						</tbody>
						</table>

					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Back</button>
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('insertTransactionOutsource')">Save</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
