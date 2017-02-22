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
	function flyToDelete(id){
		document.forms[1].deleteIndex.value = id;
		flyToPage('deleteAdditionalFee');
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<html:hidden property="deleteIndex" name="invoiceForm" />
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<h1 class="page-header">Create Invoice Training</h1>
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
					<div class="col-md-2"><label>Payment</label></div>
					<div class="col-md-5">
						Settlement
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
						<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control"></html:textarea>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Training Name</label></div>
					<div class="col-md-5">
						<html:select name="invoiceForm" property="trainingBean.transactionTrainingHeaderId" styleClass="form-control" onchange="javascript:flyToPage('getTax')">
							<html:optionsCollection name="invoiceForm" label="description" value="transactionTrainingHeaderId" property="ongoingTrainingList"/>
						</html:select>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<table border="1">
						<tr>
							<td colspan="2">Add Additional Fee</td>
						</tr>
						<tr>
							<td>Description</td>
							<td><html:text name="invoiceForm" property="trainingDetailBean.description"></html:text></td>
						</tr>
						<tr>
							<td>Fee</td>
							<td><html:text name="invoiceForm" property="trainingDetailBean.fee"></html:text></td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('addAdditionalFee')">Save</button>
								<button type="button" class="btn btn-primary">Cancel</button>
							</td>
						</tr>
					</table>
				</div>
			</div>
			<button type="button" class="btn btn-primary">Add Additional Fee</button>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<table border="1">
						<tr>
							<th>Description</th>
							<th>Price</th>
							<th>Note</th>
							<th>Action</th>
						</tr>
						<logic:iterate id="trainingDetail" name="invoiceForm" property="detailTrainingList" indexId="indexDetail">
							<tr>
								<td><html:text name="trainingDetail" property="description" indexed="true"></html:text></td>
								<td><html:text name="trainingDetail" property="fee" indexed="true"></html:text></td>
								<td><html:text name="trainingDetail" property="note" indexed="true"></html:text></td>
								<td>
									<html:hidden name="trainingDetail" property="isSettlement" indexed="true"/>
									<logic:equal name="trainingDetail" property="isSettlement" value="0">
										<button type="button" class="btn btn-primary" onclick="javascript:flyToDelete('${indexDetail}')">Delete</button>
									</logic:equal>
								</td>
							</tr>
						</logic:iterate>
					</table>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Back</button>
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('insertTRST')">Save</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
