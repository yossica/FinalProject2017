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
/* Popup container - can be anything you want */
.popup {
    position: relative;
    display: inline-block;
    cursor: pointer;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
}

/* The actual popup */
.popup .popuptext {
    visibility: hidden;
    width: 500px;
    background-color: #337ab7;
    color: #fff;
    text-align: center;
    border-radius: 6px;
    padding: 8px;
    position: absolute;
    z-index: 1;
    bottom: 125%;
    left: -140px;
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

/* Toggle this class - hide and show the popup */
.popup .show {
    visibility: visible;
    -webkit-animation: fadeIn 1s;
    animation: fadeIn 1s;
}

/* Add animation (fade in the popup) */
@-webkit-keyframes fadeIn {
    from {opacity: 0;} 
    to {opacity: 1;}
}

@keyframes fadeIn {
    from {opacity: 0;}
    to {opacity:1 ;}
}
</style>
<script>
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	/* function flyToDetail(transactionInvoiceHeaderId, clientId){
		document.forms[1].transactionInvoiceHeaderId.value = transactionInvoiceHeaderId;
		document.forms[1].client.value = clientId;
		flyToPage("detailInvoice");
	} */
	function deleteDetailHH(index){
		document.forms[1].deleteIndex.value = index;
		flyToPage('deleteDetailHH');
	}
	function toggleNotes(idNumber) {
	    var popup = document.getElementById("myPopup"+idNumber);
	    popup.classList.toggle("show");
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
				<h1 class="page-header">Create Invoice Head Hunter</h1>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-12" style="padding-right: 1%">
					<div class="col-md-2"><label>Invoice Date</label></div>
					<div class="col-md-6">
					<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceDate" />
						: <bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />
					</logic:equal>
					<logic:equal name="invoiceForm" property="task" value="editInvoice">
						<input type="date" id="invoiceDate" class="form-control-client" style="width: 100%;" name="invoiceBean.invoiceDate" value="<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />">
					</logic:equal>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-12" style="padding-right: 1%">
					<div class="col-md-2"><label>Client</label></div>
					<div class="col-md-6">
					<html:hidden name="invoiceForm" property="invoiceBean.clientId" />
					<bean:write name="invoiceForm" property="invoiceBean.clientName"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Contract Service</label></div>
					<div class="col-md-6">
					<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeId" />
					<bean:write name="invoiceForm" property="invoiceBean.invoiceTypeName"/>
					</div>
				</div>
			</div>
			<html:hidden name="invoiceForm" property="invoiceBean.periodMonth" />
			<html:hidden name="invoiceForm" property="invoiceBean.periodYear" />
			<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Period</label></div>
					<div class="col-md-6">
						: <bean:write name="invoiceForm" property="invoiceBean.periodMonth" format="#" />
						/
						<bean:write name="invoiceForm" property="invoiceBean.periodYear" format="#" />
					</div>
				</div>
			</div>
			</logic:equal>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Tax</label></div>
					<div class="col-md-6">
					<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
						<html:hidden name="invoiceForm" property="invoiceBean.isGross" />
						: 
						<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="1">
							Include
						</logic:equal>
						<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="0">
							Exclude
						</logic:equal>
					</logic:equal>
					<logic:equal name="invoiceForm" property="task" value="editInvoice">
						<div class="radio">
							<label>
								<html:radio name="invoiceForm" property="invoiceBean.isGross" value="0">Exclude</html:radio>
						    </label>
						    &nbsp;
						    <label>
						   		<html:radio name="invoiceForm" property="invoiceBean.isGross" value="1">Include</html:radio>
						    </label>
						</div>
					</logic:equal>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Invoice Note</label></div>
					<div class="col-md-5">
					<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
						<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control" readonly="true" ></html:textarea>
					</logic:equal>
					<logic:equal name="invoiceForm" property="task" value="editInvoice">
						<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control" rows="3"></html:textarea>
					</logic:equal>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="border:solid 1px gray; border-radius: 10px; background-color: #EFEFEF; margin-top: 30px;">
				<div class="row">
					<div class="col-md-12" style="text-align: center;">
						<table class="table table-hover">
							<tr>
								<th>Description</th>
								<th>Fee</th>
								<th style="text-align:center;">Notes</th>
								<th style="text-align:center;">Action</th>
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
									<div class="popup">
										<span class="popuptext" id="myPopup${indexHH}">
											<html:textarea name="invoiceDetailHH" property="notes" styleClass="form-control" indexed="true" rows="7"></html:textarea>
										</span>
										<button type="button" class="btn btn-primary" onclick="javascript:toggleNotes(${indexHH})">Notes</button>
									</div>
								</td>
								<td>
									<button type="button" class="btn btn-primary" style="margin-bottom: 1%;" onclick="javascript:deleteDetailHH('${indexHH}')"><i class="fa fa-times"></i></button>
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
