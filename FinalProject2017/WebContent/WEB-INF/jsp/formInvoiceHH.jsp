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
		//Error Checking
		var error = false;
		var doubleReg = /^([\d]+)(|.[\d]+)$/;
		var errorMessage = "";
		
		if (task == 'insertHH' || task == 'editInvoiceHH'){
			var HHSize = document.getElementById('headHunterListSize').value;
			for(var i=0; i<HHSize; i++){
				if (document.getElementsByName('invoiceDetailHH['+i+'].description')[0].value == ''){
					/* alert('Error Deskripsi Kosong');
					error = true; */
					errorMessage = errorMessage + "Description must be filled! \n";
				}else if (document.getElementsByName('invoiceDetailHH['+i+'].fee')[0].value == ''){
					/* alert('Error Fee Kosong');
					error = true; */
					errorMessage = errorMessage + "Fee must be filled! \n";
				}else if (!doubleReg.test(document.getElementsByName('invoiceDetailHH['+i+'].fee')[0].value)){
					/* alert('Error Fee tidak sesuai format');
					error = true; */
					errorMessage = errorMessage + "Fee must be Number! \n";
				}
			}
		}
		if(errorMessage.length != 0){
			sweetAlert("Oops...", errorMessage, "error");
			//document.getElementById("message").innerHTML = errorMessage;
			return;
		}
		else{
			swal({
				  title: "Are you sure?",
				  text: "System will insert these data to Invoice Professional Service",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#ef2300",
				  confirmButtonText: "Yes, Insert",
				  cancelButtonText: "No, Cancel Please!",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
					  document.forms[1].task.value = task;
						document.forms[1].submit();
				  } else {
				    swal("Cancelled", "Cancel Insert Transaction", "error");
				  }
				});
			/* document.forms[1].task.value = task;
			document.forms[1].submit(); */
		}
	}
	function flyToDetail(transactionInvoiceHeaderId, clientId, statusId){
		document.forms[1].transactionInvoiceHeaderId.value = transactionInvoiceHeaderId;
		document.forms[1].client.value = clientId;
		document.forms[1].statusId.value = statusId;
		flyToPage("detailInvoice");
	}
	function deleteDetailHH(index, flyTo){
		document.forms[1].deleteIndex.value = index;
		flyToPage(flyTo);
	}
	function toggleNotes(idNumber) {
	    var popup = document.getElementById("myPopup"+idNumber);
	    popup.classList.toggle("show");
	}
	function onloadFunc() {
		var HHSize = document.getElementById('headHunterListSize').value;
		if (HHSize == 1){
			document.getElementById('deleteButton').style.display = 'none';
		}else {
			document.getElementById('deleteButton').style.display = 'table-row';
		}
		var message=document.getElementById("err");
		if(message!=null){
			var messageValue=message.value;
			
			var strValue = messageValue.substring(0, 7);
			if(strValue=="Success"){
				//Success
				swal("Good job!", messageValue, "success");
			}
			else if(strValue=="Ooooops"){
				//Ooooops
				sweetAlert("Oops...", messageValue, "error");
			}
		}
	}
	window.onload = onloadFunc;
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<html:hidden property="deleteIndex" name="invoiceForm" />
	<html:hidden property="invoiceBean.transactionInvoiceHeaderId" name="invoiceForm" />
	<html:hidden property="headHunterListSize" name="invoiceForm" styleId="headHunterListSize"/>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
					<h1 class="page-header">Create Invoice (Cont)</h1>
				</logic:equal>
				<logic:equal name="invoiceForm" property="task" value="editInvoice">
					<h1 class="page-header">Edit Invoice</h1>
				</logic:equal>
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
					<html:hidden name="invoiceForm" property="invoiceBean.clientName" />
					<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
					: 
					</logic:equal>
					<bean:write name="invoiceForm" property="invoiceBean.clientName"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Contract Service</label></div>
					<div class="col-md-6">
					<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeId" />
					<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeName" />
					<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
					: 
					</logic:equal>
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
						<table class="table table-hover" style="width: 95%;">
							<tr>
								<th>Description</th>
								<th>Fee</th>
								<th style="text-align:center;">Notes</th>
								<th style="text-align:center;">Action</th>
							</tr>
							<logic:iterate id="invoiceDetailHH" name="invoiceForm" property="headHunterList" indexId="indexHH">
							<tr>
								<td>
									<html:text name="invoiceDetailHH" property="description" styleClass="form-control description" indexed="true"></html:text>
								</td>
								<td>
									<html:text name="invoiceDetailHH" property="fee" styleClass="form-control fee" indexed="true"></html:text>
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
								<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
									<button id="deleteButton" type="button" class="btn btn-primary" style="margin-bottom: 1%;" 
									onclick="javascript:deleteDetailHH('${indexHH}','deleteDetailHH')"><i class="fa fa-times"></i></button>
								</logic:equal>
								<logic:equal name="invoiceForm" property="task" value="editInvoice">
									<button id="deleteButton" type="button" class="btn btn-primary" style="margin-bottom: 1%;" 
									onclick="javascript:deleteDetailHH('${indexHH}','deleteDetailHHonEditPage')"><i class="fa fa-times"></i></button>
								</logic:equal>
								</td>
							</tr>
							</logic:iterate>
						</table>
						<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
							<button type="button" class="btn btn-primary btn-circle" style="margin-bottom: 1%;" onclick="javascript:flyToPage('addDetailHH')"><i class="fa fa-plus"></i></button>
						</logic:equal>
						<logic:equal name="invoiceForm" property="task" value="editInvoice">
							<button type="button" class="btn btn-primary btn-circle" style="margin-bottom: 1%;" onclick="javascript:flyToPage('editDetailHH')"><i class="fa fa-plus"></i></button>
						</logic:equal>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
						<logic:equal name="invoiceForm" property="task" value="formInvoiceHH">
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Back</button>
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('insertHH')">Save</button>
						</logic:equal>
						<logic:equal name="invoiceForm" property="task" value="editInvoice">
							<html:hidden property="transactionInvoiceHeaderId" name="invoiceForm"/>
							<html:hidden property="client" name="invoiceForm"/>
							<html:hidden property="statusId" name="invoiceForm"/>
							<button type="button" class="btn btn-primary" onclick="javascript:flyToDetail(
                				'<bean:write name="invoiceForm" property="transactionInvoiceHeaderId" format="#"/>',
                				'<bean:write name="invoiceForm" property="clientId" format="#"/>',
                				'<bean:write name="invoiceForm" property="statusInvoiceId" format="#"/>'
	                		)">
	                		Back</button>
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('editInvoiceHH')">Save</button>
						</logic:equal>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-4" style="color:red;overflow: auto;" id="message">
  				<logic:notEmpty name="invoiceForm" property="messageList">
					<logic:iterate id="message" name="invoiceForm" property="messageList">
						<%-- <bean:write name="message" />  --%>
						<input type="hidden" id="err" value="<bean:write name="message" />">
					</logic:iterate>
				</logic:notEmpty>
			</div>
	</html:form>
	
</body>
</html>
