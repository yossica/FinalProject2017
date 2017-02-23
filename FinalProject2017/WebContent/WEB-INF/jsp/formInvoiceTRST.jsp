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
		/* document.forms[1].deleteIndex.value = id;
		flyToPage('deleteAdditionalFee'); */
		swal({
			  title: "Are you sure?",
			  text: "System will delete "+id+" data from additional",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#ef2300",
			  confirmButtonText: "Yes, Delete",
			  cancelButtonText: "No, Cancel Please!",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm){
			  if (isConfirm) {
				  document.forms[1].deleteIndex.value = id;
					flyToPage('deleteAdditionalFee');
			  } else {
			    swal("Cancelled", "Cancel Delete Additional", "error");
			  }
			});
	}
	function validate(){
		var tgl = document.getElementsByName("invoiceBean.invoiceDate")[0].value;
		
		if (tgl=="") {
			alert("tanggal harus diisi");
		} else {
			flyToPage('editInvoiceTRST');
		}
		
	}
	function flyToAdd(){
		var desc = document.getElementById('desc').value;
		var fee = document.getElementById('fee').value;
		
		if (desc==""||fee=="0.0"||fee=="") {
			alert("deskripsi dan fee harus diisi");
		} else if (isNaN(fee)) {
			alert("fee harus angka");
		} else {
			swal({
				  title: "Are you sure?",
				  text: "System will insert these data to master holiday",
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
					  flyToPage('addAdditionalFee');
				  } else {
				    swal("Cancelled", "Cancel Insert Master Holiday", "error");
				  }
				}); 
			/* flyToPage('addAdditionalFee'); */
		}
	}
	function alertError() {
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
	window.onload = alertError;
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
	<html:hidden property="task" name="invoiceForm"/>
	<html:hidden property="subTask" name="invoiceForm"/>
	<html:hidden property="deleteIndex" name="invoiceForm" />
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<logic:equal value="editInvoiceTRST" property="subTask" name="invoiceForm">
					<h1 class="page-header">Edit Invoice Training Settlement</h1>
					<html:hidden property="invoiceBean.transactionInvoiceHeaderId" name="invoiceForm"/>
				</logic:equal>
				<logic:notEqual value="editInvoiceTRST" property="subTask" name="invoiceForm">
					<h1 class="page-header">Create Invoice Training Settlement</h1>
				</logic:notEqual>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-12" style="padding-right: 1%">
					<div class="col-md-3"><label>Invoice Date</label></div>
					<div class="col-md-9">
						<logic:equal value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<input type="date" name="invoiceBean.invoiceDate" class="form-control" value="<bean:write property="invoiceBean.invoiceDate" name="invoiceForm"/>" styleId="tgl">
						</logic:equal>
						<logic:notEqual value="editInvoiceTRST" property="subTask" name="invoiceForm">							
							<html:hidden name="invoiceForm" property="invoiceBean.invoiceDate" />
							<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />
						</logic:notEqual>
					</div>
				</div>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-12" style="padding-right: 1%">
					<div class="col-md-3"><label>Client</label></div>
					<div class="col-md-9">
						<html:hidden name="invoiceForm" property="invoiceBean.clientId" />
						<html:hidden name="invoiceForm" property="invoiceBean.clientName"/>
						<bean:write name="invoiceForm" property="invoiceBean.clientName"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Contract Service</label></div>
					<div class="col-md-9">
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeId" />
						<html:hidden name="invoiceForm" property="invoiceBean.invoiceTypeName" />
						<bean:write name="invoiceForm" property="invoiceBean.invoiceTypeName"/>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Payment</label></div>
					<div class="col-md-9">
						Settlement
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Tax</label></div>
					<div class="col-md-9">
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
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Invoice Note</label></div>
					<div class="col-md-8">
						<logic:equal value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control-client"></html:textarea>
						</logic:equal>
						<logic:notEqual value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control" readonly="true"></html:textarea>
						</logic:notEqual>
					</div>
				</div>
			</div>
			<div class="col-md-12" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-3"><label>Training Name</label></div>
					<div class="col-md-8">
						<logic:equal value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<html:hidden property="trainingBean.transactionTrainingHeaderId" name="invoiceForm" />
							<bean:write property="trainingBean.description" name="invoiceForm"/>
						</logic:equal>
						<logic:notEqual value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<html:select name="invoiceForm" property="trainingBean.transactionTrainingHeaderId" styleClass="form-control" onchange="javascript:flyToPage('getTax')">
								<html:optionsCollection name="invoiceForm" label="description" value="transactionTrainingHeaderId" property="ongoingTrainingList"/>
							</html:select>
						</logic:notEqual>						
					</div>
				</div>
			</div>
			<div class="pull-right" style="margin-top: 30px;">
			<button type="button" class="btn btn-primary">Add Additional Fee</button>
			</div>
			<div class="col-md-12" style="border:solid 1px gray; border-radius: 10px; background-color: #EFEFEF; margin-top: 30px;">
				<div class="row">
					<div class="col-md-12">
						<table class="table table-hover">
						<thead>	
						<tr>
							<td colspan="2"><label>Add Additional Fee</label></td>
						</tr>
						</thead>
						<tbody>
						<tr>
							<td>Description</td>
							<td><html:text name="invoiceForm" property="trainingDetailBean.description" styleId="desc" styleClass="form-control"></html:text></td>
						</tr>
						<tr>
							<td>Fee</td>
							<td><html:text name="invoiceForm" property="trainingDetailBean.fee" styleId="fee" styleClass="form-control"></html:text></td>
						</tr>
						<tr>
							<td colspan="2">
								<button type="button" class="btn btn-primary" onclick="javascript:flyToAdd()">Save</button>
								<button type="button" class="btn btn-primary">Cancel</button>
							</td>
						</tr>
						</tbody>
					</table>
				</div>
			</div>
			</div>
			
			<div class="col-md-12" style="border:solid 1px gray; border-radius: 10px; background-color: #EFEFEF; margin-top: 30px;">
				<div class="row">
					<div class="col-md-12">
						<table class="table table-hover">
						<tr>
							<th>Description</th>
							<th>Price</th>
							<th>Note</th>
							<th>Action</th>
						</tr>
						<logic:iterate id="trainingDetail" name="invoiceForm" property="detailTrainingList" indexId="indexDetail">
							<tr>
								<td>
								<html:text name="trainingDetail" property="description" readonly="true" indexed="true" styleClass="form-control"></html:text>
				
								</td>
								<td><html:text name="trainingDetail" property="fee" readonly="true" indexed="true" styleClass="form-control"></html:text></td>
								<td><html:text name="trainingDetail" property="note" indexed="true" styleClass="form-control"></html:text ></td>
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
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
						<logic:equal value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('invoiceList')">Back</button>
							<button type="button" class="btn btn-primary" onclick="javascript:validate()">Save</button>
						</logic:equal>
						<logic:notEqual value="editInvoiceTRST" property="subTask" name="invoiceForm">
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Back</button>
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('insertTRST')">Save</button>
						</logic:notEqual>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="col-md-4" id="message">
				<logic:notEmpty name="invoiceForm" property="messageList">
			<logic:iterate id="message" name="invoiceForm" property="messageList">
				<input type="hidden" id="err" value="<bean:write name="message" />">
			</logic:iterate>
		</logic:notEmpty>
			</div>
	</html:form>
</body>
</html>
