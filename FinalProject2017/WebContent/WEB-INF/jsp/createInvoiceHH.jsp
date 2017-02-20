<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create Invoice</title>
<style>
/* Popup container */
.popup {
    position: relative;
    display: inline-block;
    cursor: pointer;
}

/* The actual popup (appears on top) */
.popup .popuptext {
    width: 160px;
    background-color: #555;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 8px 0;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: 50%;
    margin-left: -80px;
}

/* Popup arrow */
.popup .popuptext::after {
    content: "";
    position: absolute;
    top: 100%;
    left: 50%;
    margin-left: -5px;
    border-width: 5px;
    border-style: solid;
    border-color: #555 transparent transparent transparent;
}

/* Toggle this class when clicking on the popup container (hide and show the popup) */
.popup .show {
    visibility: visible;
}
</style>
<script>
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function deleteDetailHH(index){
		document.forms[1].deleteIndex.value = index;
		flyToPage('deleteDetailHH');
	}
	function showNotes(input){
		document.getElementById(input).style.display = "inline";
	}
	function closeNotes(input){
		document.getElementById(input).style.display = "none";
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
							<logic:iterate id="invoiceDetailHH" name="invoiceForm" property="headHunterList" indexId="indexHH">
							<tr>
								<td>
									<html:text name="invoiceDetailHH" property="description" styleClass="form-control" indexed="true"></html:text>
								</td>
								<td>
									<html:text name="invoiceDetailHH" property="fee" styleClass="form-control" indexed="true"></html:text>
								</td>
								<td>
									<button type="button" class="btn btn-primary" onclick="javascript:showNotes('notes${indexHH}')">Notes</button>
									<div id="notes${indexHH}" class="popup" style= "display:none">
										<span class="popuptext">
										Notes
											<html:textarea name="invoiceDetailHH" property="notes" styleClass="form-control" indexed="true"></html:textarea>
											<button type="button" class="btn btn-primary" onclick="javascript:closeNotes('notes${indexHH}')">Close</button>
										</span>
									</div>
								</td>
								<td>
									<button type="button" class="btn btn-primary btn-circle" style="margin-bottom: 1%;" onclick="javascript:deleteDetailHH('${indexHH}')"><i class="fa fa-minus"></i></button>
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
						<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('insertHH')">Save</button>
					</div>
				</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
