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
<script>
	function insert(){
		//validate
		var fee = document.getElementsByName("trainingFee")[0].value;
		var description = document.getElementsByName("trainingBean.description")[0].value;		
		var startDate = document.getElementsByName("trainingBean.trainingStartDate")[0].value;
		var endDate = document.getElementsByName("trainingBean.trainingEndDate")[0].value;		
		
		var doubleReg = /^([\d]+)(|.[\d]+)$/;
		var errorMessage = "";
		var flag = true;

		if(description == ""){
			errorMessage = errorMessage + "Training Name must be filled!<br/>";
		}
		
		if(startDate == ""){
			errorMessage = errorMessage + "Start Date must be filled! <br/>";
			flag = false;
		}
		if(endDate == ""){
			errorMessage = errorMessage + "End Date must be filled! <br/>";
			flag = false;
		}
		if(flag){
			var s = startDate.split("-");
			var sd = new Date(parseInt(s[0]),parseInt(s[1])-1,parseInt(s[2]));
			var e = endDate.split("-");
			var ed = new Date(parseInt(e[0]),parseInt(e[1])-1,parseInt(e[2]));
			if(sd > ed){
				errorMessage = errorMessage + "Start Date must be later than End Date! <br/>";
			}
		}
		
		if(fee == ""){
			errorMessage = errorMessage + "Total Training Fee must be filled!<br/>";
		}
		else if(!doubleReg.test(fee)){
			errorMessage = errorMessage + "Total Training Fee must be number!<br/>";
		}
		else if(parseFloat(fee) <= 0){
			errorMessage = errorMessage + "Total Training Fee cannot be zero or negative!<br/>";	
		}
		
		if(errorMessage.length != 0){
			document.getElementById("message").innerHTML = errorMessage;
			return;
		}
		else{		
			
			swal({
				  title: "Are you sure?",
				  text: "System will insert these data to Invoice",
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
			            	flyToPage("insertTRDP");
				  } else {
				    swal("Cancelled", "Cancel Insert Transaction", "error");
				  }
				});
			
		}			
	}
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
	<html:hidden property="client" name="invoiceForm"/>
	<html:hidden property="statusId" name="invoiceForm"/>
	<html:hidden property="transactionInvoiceHeaderId" name="invoiceForm"/>
	<div id="page-wrapper">
		<div class="row">
			<div class="col-lg-12">
				<logic:equal name="invoiceForm" property="task"
						value="saveedit">
					<h1 class="page-header">Edit Invoice Training</h1>
				</logic:equal>
				<logic:notEqual name="invoiceForm" property="task"
						value="saveedit">
					<h1 class="page-header">Create Invoice Training</h1>
				</logic:notEqual>
			</div>
			<div class="row" style="margin-top: 10px;">
				<div class="col-md-10" style="padding-right: 1%">
					<div class="col-md-2"><label>Invoice Date</label></div>
					<div class="col-md-5">
						<logic:equal name="invoiceForm" property="task"
								value="saveedit">
							<input type="date" class="form-control" style="width: 100%;" 
								name="invoiceBean.invoiceDate" 
								value="<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />"/>
						</logic:equal>
						<logic:notEqual name="invoiceForm" property="task"
								value="saveedit">
							<html:hidden name="invoiceForm" property="invoiceBean.invoiceDate" />
							<bean:write name="invoiceForm" property="invoiceBean.invoiceDate" />
						</logic:notEqual>
					
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
					<div class="col-md-2"><label>Payment</label></div>
					<div class="col-md-5">
						DP
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Tax</label></div>
					<div class="col-md-5">
						
						<logic:equal name="invoiceForm" property="task"
							value="saveedit">
							<div class="col-md-4">
							<html:radio name="invoiceForm"
								property="invoiceBean.isGross" value="0" />&nbsp;Exclude
							&nbsp;
							<html:radio name="invoiceForm"
								property="invoiceBean.isGross" value="1" />&nbsp;Include
							</div>
						</logic:equal>
					
						<logic:notEqual name="invoiceForm" property="task"
							value="saveedit">
							<html:hidden name="invoiceForm" property="invoiceBean.isGross" />
							<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="0">
								Exclude
							</logic:equal>
							<logic:equal name="invoiceForm" property="invoiceBean.isGross" value="1">
								Include
							</logic:equal>
						</logic:notEqual>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Invoice Note</label></div>
					<div class="col-md-5">
						<logic:equal name="invoiceForm" property="task"
								value="saveedit"><html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control" ></html:textarea>
						</logic:equal>
						<logic:notEqual name="invoiceForm" property="task"
								value="saveedit"><html:textarea name="invoiceForm" property="invoiceBean.notes" styleClass="form-control" readonly="true"></html:textarea>
						</logic:notEqual>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Training Name</label></div>
					<div class="col-md-5">
						<html:text name="invoiceForm" property="trainingBean.description" styleClass="form-control"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Start Date</label></div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;" name="trainingBean.trainingStartDate" value="<bean:write property="trainingBean.trainingStartDate" name="invoiceForm" />"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>End Date</label></div>
					<div class="col-md-5">
						<input type="date" class="form-control" style="width: 100%;" name="trainingBean.trainingEndDate" value="<bean:write property="trainingBean.trainingEndDate" name="invoiceForm" />"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Total Training Fee</label></div>
					<div class="col-md-5">
						<html:text name="invoiceForm" property="trainingFee" styleClass="form-control"/>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-2"><label>Notes</label></div>
					<div class="col-md-5">
						<html:textarea name="invoiceForm" property="invoiceDetailNotes" styleClass="form-control"></html:textarea>
					</div>
				</div>
			</div>
			<div class="col-md-10" style="margin-top: 10px;">
				<div class="row">
					<div class="col-md-12" style="margin-top: 10px; margin-bottom: 10px;">
						
						<logic:equal name="invoiceForm" property="task"
								value="saveedit">
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('detailInvoice')">Back</button>
							<button type="button" class="btn btn-primary" onclick="javascript:insert()">Save</button>
						</logic:equal>
						<logic:notEqual name="invoiceForm" property="task"
								value="saveedit">
							<button type="button" class="btn btn-primary" onclick="javascript:flyToPage('createInvoice')">Back</button>
							<button type="button" class="btn btn-primary" onclick="javascript:insert()">Save</button>
						</logic:notEqual>
					
						
					</div>
				</div>
				<div class="col-md-7" id="message" style="color:red">
	        		<logic:notEmpty name="invoiceForm" property="messageList">
						<logic:iterate id="message" name="invoiceForm" property="messageList">
							<bean:write name="message" /> 
						</logic:iterate>
					</logic:notEmpty>
	        	</div>
			</div>
		</div>
	</div>
	</html:form>
</body>
</html>
