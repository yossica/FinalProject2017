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
			var fee = document.getElementsByName("trainingDetailBean.fee")[0].value;
			var description = document.getElementsByName("trainingDetailBean.description")[0].value;
			var doubleReg = /^[\d]+(.[\d]+)$/;
			var errorMessage = ""; 
			
			if(fee == ""){
				errorMessage = errorMessage + "Fee must be filled!<br/>";
			}
			else if(!doubleReg.test(fee)){
				errorMessage = errorMessage + "Fee must be number!<br/>";
			}
			else if(parseFloat(fee) <= 0){
				errorMessage = errorMessage + "Fee cannot be zero or negative!<br/>";	
			}
			if(description == ""){
				errorMessage = errorMessage + "Description must be filled!<br/>";
			}
			
			if(errorMessage.length != 0){
				document.getElementById("message").innerHTML = errorMessage;
				return;
			}
			else{		
				var clientList = document.getElementsByName("clientId")[0];
				var clientName = clientList.options[clientList.selectedIndex].text;
				var trainingList = document.getElementsByName("trainingDetailBean.transactionTrainingHeaderId")[0];
				var trainingName = trainingList.options[trainingList.selectedIndex].text;
				if (confirm("Are you sure to insert these data as additional training fee for "+clientName+"-"+trainingName+"?")) {
					flyToPage("insertDetail");
				}
			}			
		}
		function deleteAdditionalTraining(desc,id){
			if(confirm("Are you sure to delete additional training fee "+desc+"?")){
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
	            </div>
	        </div>
	        <div class="col-lg-12" style="border:solid 2px gray;border-radius: 10px; background-color: #EFEFEF;">
           		<div class="row" style="margin-top:10px;">
            		<div class="col-md-10" style="padding-right:1%">
            			<div class="col-md-1">
		            		Client
		            	</div>
		            	<div class="col-md-8">
				            <html:select property="clientId" name="trainingForm" 
				            	styleClass="form-control" onchange="javascript:flyToPage('loadTrainingHeader')">
				            	<logic:notEmpty name="trainingForm" property="clientList">
									<html:optionsCollection name="trainingForm" property="clientList" label="name" value="clientId"/>
								</logic:notEmpty>
							</html:select>
		            	</div>
            		</div>
            	</div>
            	<div class="row" style="margin-top:10px;">
            		<div class="col-md-10" style="padding-right:1%">
            			<div class="col-md-1">
            				Training
            			</div>
            			<div class="col-md-8">
		            		<html:select property="trainingDetailBean.transactionTrainingHeaderId" name="trainingForm" 
		            			styleClass="form-control" onchange="javascript:flyToPage('loadTrainingDetail')">
		            			<logic:notEmpty name="trainingForm" property="trainingHeaderList">
									<html:optionsCollection name="trainingForm" property="trainingHeaderList" label="description" value="transactionTrainingHeaderId"/>
								</logic:notEmpty>
							</html:select>
				        </div>
            		</div>
            	</div>
            	<div class="row" style="margin-top:10px;">
            		<div class="col-md-10" style="padding-right:1%">
            			<div class="col-md-1">
            				Fee
            			</div>
            			<div class="col-md-8">
		            		<html:text property="trainingDetailBean.fee" name="trainingForm" styleClass="form-control"/>
				        </div>
            		</div>
            	</div>
            	<div class="row" style="margin-top:10px;margin-bottom:10px;">
            		<div class="col-md-10" style="padding-right:1%">
            			<div class="col-md-1">
            				Notes
            			</div>
            			<div class="col-md-8">
		            		 <html:textarea property="trainingDetailBean.description" name="trainingForm" styleClass="form-control" style="height:100px">
						     </html:textarea>
				        </div>
            		</div>
            	</div>
            	<div class="row" style="margin-top:10px;margin-bottom:10px;">
            		<div class="col-md-10" style="padding-right:1%">
            			<div class="col-md-2">
			        		<button type="button" class="btn btn-primary" onclick="javascript:insert()">Add</button>
			        	</div>
			        	<div class="col-md-1"></div>
			        	<div class="col-md-7" id="message">
			        		<logic:notEmpty name="trainingForm" property="messageList">
								<logic:iterate id="message" name="trainingForm" property="messageList">
									<bean:write name="message" /> 
								</logic:iterate>
							</logic:notEmpty>
			        	</div>
            		</div>
            	</div>
            </div>
            <div class="row">
		    	<div class="col-lg-12">
		    		<div class="panel-body">
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
												<td><bean:write name="trainingDetail" property="fee" format="#,###.##"/></td>
												<logic:equal value="0" property="isSettlement" name="trainingDetail">
													<td><a href="#" onclick="javascript:deleteAdditionalTraining('<bean:write name="trainingDetail" property="description"/>',<bean:write name="trainingDetail" property="transactionTrainingDetailId" format="#"/>)">X</a></td>
												</logic:equal>
												<logic:notEqual value="0" property="isSettlement" name="trainingDetail">
													<td></td>
												</logic:notEqual>
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