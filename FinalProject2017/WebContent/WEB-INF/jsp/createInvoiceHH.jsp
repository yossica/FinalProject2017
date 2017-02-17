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
					<div class="col-md-2"><label>Invoice Date</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceDate" />
						<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">

					<div class="col-md-2"><label>Client</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.clientId" />
						<html:hidden name="invoiceForm" property="invoiceBean.clientName"/>
						<bean:write name="invoiceForm" property="invoiceBean.clientName"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Contract Service</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeId" />
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeName" />
						<bean:write name="invoiceForm" property="invoiceBean.invoiceTypeName"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Period</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.periodMonth" />
						<html:hidden name="invoiceForm" property="invoiceBean.periodYear" />
						<bean:write name="invoiceForm" property="invoiceBean.periodMonth" format="#" />
						/
						<bean:write name="invoiceForm" property="invoiceBean.periodYear" format="#" />
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
						</logic:equal>
						<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="0">
							Exclude
						</logic:equal>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Invoice Note</label></div>
					<div class="col-md-5">
						<html:hidden name="invoiceForm" property="invoiceBean.notes" />
						<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control" disabled="true"></html:textarea>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="border:solid 1px gray; border-radius: 10px; background-color: #EFEFEF; margin-top: 1%;">
				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<table class="table table-hover">
							<tr>
								<th>Description</th>
								<th>Fee</th>
								<th>Notes</th>
								<th>Action</th>
							</tr>
							<logic:iterate id="invoiceDetail" name="invoiceForm" property="headHunterList">
							<tr>
								<td>
									<html:text name="invoiceDetail" property="description" styleClass="form-control" indexed="true"></html:text>
								</td>
								<td>
									<html:text name="invoiceDetail" property="fee" styleClass="form-control" indexed="true"></html:text>
								</td>
								<td>
									<html:text name="invoiceDetail" property="notes" styleClass="form-control" indexed="true"></html:text>
								</td>
								<td>
									Action Button Coming Soon
								</td>
							</tr>
							</logic:iterate>
						</table>
						<button type="button" class="btn btn-primary btn-circle" style="margin-bottom: 1%;" onclick="javascript:flyToPage('addDetailHH')"><i class="fa fa-plus"></i></button>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Back</button>
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('insert')">Save</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
