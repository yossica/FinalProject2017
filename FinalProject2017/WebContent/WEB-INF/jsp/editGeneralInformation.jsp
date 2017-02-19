<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function flyToPage(task)
		{
			document.forms[1].task.value = task;
			document.forms[1].submit();
		}
	
	function flyToSave()
	{
		//validasi belum sempurna
		var dType = document.forms[1].dataType.value;
		var k = document.forms[1].key.value;
		var val = document.forms[1].value.value;
		if(dType=="String"){
			if(k=="name" && val.length>50){
				alert("eror ni");
				
			}
		}
		if(dType=="Integer"){
			if(!val.match(number)){	
				if(k=="tax" && val.length>2){
					alert("Too Long");
				}
			}
		}
		else{
			swal({
				  title: "Are you sure?",
				  text: "System will update these data to general information",
				  type: "warning",
				  showCancelButton: true,
				  confirmButtonColor: "#DD6B55",
				  confirmButtonText: "Yes, Update",
				  cancelButtonText: "No, Cancel Please!",
				  closeOnConfirm: false,
				  closeOnCancel: false
				},
				function(isConfirm){
				  if (isConfirm) {
					  document.forms[1].submit();
				  } else {
				    swal("Cancelled", "Cancel Update General Information", "error");
				  }
				});
		}
		/* if(confirm("Are you sure to update ?")){
			document.forms[1].submit();
		} */
	}
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
						
                        <div class="panel-body" style="padding-left:0;">
				            <div class="pull-left">
				            	<button type="button" class="btn btn-primary" onclick="javascript:flyToSave()">Save</button>
					         	<button type="button" class="btn btn-primary " onclick="javascript:flyToPage('generalInformation')">Cancel</button>			            
				          	</div>
	            		</div>
					</div>
				</div>
			</div>
		</div>
    </html:form>
</body>
</html>