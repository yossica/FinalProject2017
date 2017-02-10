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
	<script type="text/javascript">
		function insert(){
			//validate
			flyToPage("insertDetail");
		}
		function deleteAdditionalTraining(desc,id){
			if(confirm("Are you sure to delete additional training "+desc+"?")){
				document.forms[1].transactionTrainingDetailId.value = id;
				flyToPage("deleteDetail");
			}
		}
		function flyToPage(task)
		{
			document.forms[1].task.value = task;
			document.forms[1].submit();
		}
	</script>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	
	<html:form action="/training" method="post">
		<html:hidden name="trainingForm" property="task"/>
		<html:hidden name="trainingForm" property="transactionTrainingDetailId"/> 
		<span>
			<logic:notEmpty name="trainingForm" property="messageList">
				<logic:iterate id="message" name="trainingForm" property="messageList">
					<bean:write name="message" /> 
				</logic:iterate>
			</logic:notEmpty>
		</span>
		<div id="page-wrapper">
		    <div class="row">
		        <div class="col-lg-12">
		            <h1 class="page-header">Add Additional Training</h1>
		            <div class="col-lg-10">
			            <div class="col-lg-9" style="border:solid 2px gray;margin-top:15px;">
			            	<div class="row">
				            	<div class="col-md-2">
				            	Client
				            	</div>
				            	<div class="col-md-1">
				            	:
				            	</div>
				            	<div class="col-md-8">
						            <html:select property="clientId" name="trainingForm" 
						            	styleClass="form-control" onchange="javascript:flyToPage('loadTrainingHeader')">
										<html:optionsCollection name="trainingForm" property="clientList" label="name" value="clientId"/>
									</html:select>
				            	</div>
	                        </div>
	                        <br/>
	                        <div class="row">
				            	<div class="col-md-2">
				            	Training
				            	</div>
				            	<div class="col-md-1">
				            	:
				            	</div>
				            	<div class="col-md-8">
				            		<html:select property="transactionTrainingHeaderId" name="trainingForm" 
				            			styleClass="form-control" onchange="javascript:flyToPage('loadTrainingDetail')">
				            			<logic:notEmpty name="trainingForm" property="trainingHeaderList">	
											<html:optionsCollection name="trainingForm" property="trainingHeaderList" label="description" value="transactionTrainingHeaderId"/>
										</logic:notEmpty>
									</html:select>
				            	</div>
	                        </div>
	                        <br/>
	                        <div class="row">
				            	<div class="col-md-2">
				            	Fee
				            	</div>
				            	<div class="col-md-1">
				            	:
				            	</div>
				            	<div class="col-md-8">
						            <html:text property="fee" name="trainingForm" styleClass="form-control" format="#"/>
				            	</div>
	                        </div>
	                        <br/>
	                        <div class="row">
				            	<div class="col-md-2">
				            	Notes
				            	</div>
				            	<div class="col-md-1">
				            	:
				            	</div>
				            	<div class="col-md-8">
						            <html:textarea property="description" name="trainingForm" styleClass="form-control" style="height:100px">
						            </html:textarea>
				            	</div>
	                        </div>
			            </div>
		            </div>	            
		            <div class="col-lg-11">
			            <button type="button" class="btn btn-primary" onclick="javascript:insert()">Add</button>
		            </div>
		            <div class="col-lg-10">
						<div class="table-responsive" style="overflow: auto;">
							<table class="table table-hover">
								<thead>
									<tr>
										<th>Additional Training Description</th>
										<th>Fee</th>
										<th>Action</th>
									</tr>
								</thead>
								<tbody>
									<logic:notEmpty property="trainingDetailList" name="trainingForm">
								        <logic:iterate id="trainingDetail" property="trainingDetailList" name="trainingForm">
								        	<tr>
												<td><bean:write name="trainingDetail" property="description"/></td>
												<td><bean:write name="trainingDetail" property="fee"/></td>
												<td><a href="#" onclick="javascript:deleteAdditionalTraining('<bean:write name="trainingDetail" property="description"/>',<bean:write name="trainingDetail" property="transactionTrainingDetailId" format="#"/>)">X</a></td>
											</tr>
								        </logic:iterate>
								    </logic:notEmpty>
									
								</tbody>
							</table>
						</div>
					</div>
		        </div>
		    </div>
	    </div>
    </html:form>
</body>
</html>