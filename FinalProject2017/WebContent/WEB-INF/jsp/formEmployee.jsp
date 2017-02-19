<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Master Employees</title>
<script>
	function flyToPage(task)
	{
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function flyToSave()
	{
		swal({
			  title: "Are you sure?",
			  text: "System will update these data to master employee",
			  type: "warning",
			  showCancelButton: true,
			  confirmButtonColor: "#DD6B55",
			  confirmButtonText: "Yes, Save",
			  cancelButtonText: "No, Cancel Please!",
			  closeOnConfirm: false,
			  closeOnCancel: false
			},
			function(isConfirm){
			  if (isConfirm) {
				  document.forms[1].submit();
			  } else {
			    swal("Cancelled", "Cancel Save To Master Employee", "error");
			  }
			});
		/* document.forms[1].submit(); */
	}
</script>
<title>Finance Solution</title>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/employee" method="post">
		<html:hidden property="task" name="employeeForm"/>
		<html:hidden property="employeeId" name="employeeForm"/>
		
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Form Employee</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Employee Name</label></td>
										<td><html:text property="name" name="employeeForm" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Email</label></td>
										<td><html:text property="email" name="employeeForm" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Employee Status</label></td>
										<td>
											<div class="col-lg-13" >
												<html:select property="isEnabled" name="employeeForm" styleClass="form-control-client">
													<html:option value="1">Enabled</html:option>
													<html:option value="0">Disabled</html:option>
												</html:select>
											</div>
										</td>
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
						<div class="col-md-12" style="padding-left:0;">
							<div class="pull-left">
								 <input type="button" value="Save" class="btn btn-primary" onclick="javascript:flyToSave()">
			                   	<input type="button" value="Cancel" class="btn btn-primary" onclick="javascript:flyToPage('employee')">
			                </div>
			            </div>
					</div>
				</div>
			</div>
		</div>
	
		
		
				
				
    </html:form>
</body>
</html>