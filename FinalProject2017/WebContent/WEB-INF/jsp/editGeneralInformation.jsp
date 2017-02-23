<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Finance Solution</title>
<script>
	function flyToPage(task)
		{
			document.forms[1].task.value = task;
			document.forms[1].submit();
		}
	
	function flyToSave()
	{
		var value = document.getElementsByName("value")[0].value;
		var key = document.getElementsByName("key")[0].value;
		var type = document.getElementsByName("dataType")[0].value;
		var length = document.getElementsByName("length")[0].value;
		var phoneAndFaxRegEx = /^([+0])([\d-])*$/;
		var intOrDouble = /^[0-9]+([,.][0-9]+)?$/;
		
		var errorMessage = "";
		
		if (value == "")
		{
			errorMessage = errorMessage + "Value must be filled! \n";
		}
		
		if (value.length>length){
			errorMessage = errorMessage + "Length of value can't be more than "+length+" ! \n";
		} else if (type=="Integer" && !intOrDouble.test(value)) {
			errorMessage = errorMessage + key +" value must be numeric ! \n";
		} else if (key=="telp" && !phoneAndFaxRegEx.test(value) || key=="fax" && !phoneAndFaxRegEx.test(value)){
			errorMessage = errorMessage + key +" format is wrong! \n ex: 021-123456 \n";
		}
		
		if (errorMessage.length != 0) {
			sweetAlert("Oops...", errorMessage, "error");
			/* document.getElementById("message").innerHTML = errorMessage; */
			return;
		}
		else{
			swal({
				  title: "Are you sure?",
				  text: "System will update these data to General Information",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#ef2300",
				  confirmButtonText: "Yes, Update",
				  cancelButtonText: "No, Cancel Please!",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
					  swal({title: "Good job!",
						  text: "Save Success!",
						  type: "success"}
						 ,function(){
						  setTimeout(function(){
							  document.forms[1].submit();
							  }, 10);
							});		
			            	
				  } else {
				    swal("Cancelled", "Cancel Update General Information", "error");
				  }
				});
		}
	}
		/* if(confirm("Are you sure to update ?")){
			document.forms[1].submit();
		} */
</script>
<title>General Information</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/generalInformation" method="post">
	<html:hidden property="task" name="generalInformationForm"/>
	
	<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Edit General Information</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Key</label></td>
										<td><html:text name="generalInformationForm" property="key" readonly="true" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Value</label></td>
										<td><html:text name="generalInformationForm" property="value" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Data Type</label></td>
										<td><html:text name="generalInformationForm" property="dataType" readonly="true" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Length</label></td>
										<td><html:text name="generalInformationForm" property="length" readonly="true" styleClass="form-control-client"/></td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
						<div class="col-md-15" style="color: red;" id="message">
						<logic:notEmpty name="generalInformationForm" property="messageList">
							<logic:iterate id="message" name="generalInformationForm">
								<input type="hidden" id="err" value="<bean:write name="message" />">
							</logic:iterate>
						</logic:notEmpty>
					</div>
                        <div class="panel-body" style="padding-left:0;">
				            <div class="pull-left">
				            	<button type="button" class="btn btn-primary " onclick="javascript:flyToPage('generalInformation')">Cancel</button>	
				            	<button type="button" class="btn btn-primary" onclick="javascript:flyToSave()">Save</button>	            
				          	</div>
	            		</div>
					</div>
				</div>
			</div>
		</div>
    </html:form>
</body>
</html>