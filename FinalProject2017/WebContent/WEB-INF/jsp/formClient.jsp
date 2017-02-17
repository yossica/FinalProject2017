<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
	function flyToSave() {
		document.forms[1].submit();
	}
</script>
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/client" method="post">
		<html:hidden property="task" name="clientForm" />
		<html:hidden property="clientId" name="clientForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Form Client</h1>
					<div class="panel-body">
						<div class="table-responsive">
							<table class="table table-hover">
								<tbody>
									<tr>
										<td><label>Client Name</label></td>
										<td><html:text name="clientForm" property="name" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Address</label></td>
										<td><html:text name="clientForm" property="address" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>City</label></td>
										<td><html:text name="clientForm" property="city" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Phone</label></td>
										<td><html:text name="clientForm" property="phoneNumber" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Fax</label></td>
										<td><html:text name="clientForm" property="faxNumber" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Postal Code</label></td>
										<td><html:text name="clientForm" property="postalCode" styleClass="form-control-client"/></td>
									</tr>
									<tr>
										<td><label>Client Status</label></td>
										<td>
											<div class="col-lg-13" >
											<html:select name="clientForm" property="isEnabled" styleClass="form-control-client">
												<html:option value="1">Enabled</html:option>
												<html:option value="0">Disabled</html:option>
<<<<<<< HEAD
											</html:select>
											</div>
										</td>
										
=======
											</html:select></td>
									</tr>
									<tr>
										<td><button type="button" class="btn btn-primary"
												onclick="javascript:flyToPage('client')">Cancel</button></td>
										<td><button type="button"
												class="btn btn-primary pull-right"
												onclick="javascript:flyToSave()">Save</button></td>
>>>>>>> refs/remotes/origin/master
									</tr>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
<<<<<<< HEAD
					
					<div class="col-md-12" style="padding-right: 1%;" >
					<button type="button" class="btn btn-primary"
						onclick="javascript:flyToSave()" style="margin-bottom: 5%;">Save</button>
					<button type="button" class="btn btn-primary"
						style="margin-bottom: 5%;">Cancel</button>
					</div>
=======
					<table border="1">

					</table>
>>>>>>> refs/remotes/origin/master
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>