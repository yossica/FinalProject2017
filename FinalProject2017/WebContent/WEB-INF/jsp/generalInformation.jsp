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
	function flyToPage(task) {
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}

	function flyToUpdate(key) {
		document.getElementsByName("generalInformationBean.key")[0].value = key;
		flyToPage("update");
	}
</script>
<title>General Information</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/generalInformation" method="post">
		<html:hidden property="task" name="generalInformationForm" />
		<html:hidden property="generalInformationBean.key" name="generalInformationForm" />

		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">General Information List</h1>
					<div class="panel-body">
						<div class="table-responsive"
							style="height: 450px; overflow: auto;">
							<table class="table table-hover">
								<tr>
									<th>Key</th>
									<th>Value</th>
									<th>Data Type</th>
									<th>Length</th>
									<th>Action</th>
								</tr>
								<tbody>
									<logic:iterate id="generalInformation"
										name="generalInformationForm"
										property="listGeneralInformation">
										<tr>
											<td><bean:write name="generalInformation" property="key" /></td>
											<td><bean:write name="generalInformation" property="value" /></td>
											<td><bean:write name="generalInformation" property="dataType" /></td>
											<td><bean:write name="generalInformation" property="length" format="#" /></td>
											<td><input type="button" value="Edit"
												class="btn btn-primary"
												onclick="javascript:flyToUpdate('<bean:write name="generalInformation" property="key"/>')"></td>
										</tr>
									</logic:iterate>
								</tbody>
							</table>
						</div>
						<!-- /.table-responsive -->
					</div>
					<table border="1">

					</table>
				</div>
			</div>
		</div>
	</html:form>
</body>
</html>