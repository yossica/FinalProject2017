<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Holiday</title>
<script type="text/javascript">
	function deleteHoliday(holiday,id){
		if(confirm("Are you sure to delete "+holiday+"?")){
			document.forms[1].holidayId.value = id;
			flyToPage("delete");
		}		
	}
	function insertHoliday(){
		var csv = document.forms[1].holidayCsv.value;
		//validate
		var splitenter = csv.split("\n");
		var error = "";
		for(var i = 0 ; i < splitenter.length ; i++){
			if(!splitenter[i].includes(",")){
				error += "Row number "+ (i+1) + " must contain ,\n";
			}
			else {
				var splitcomma = splitenter[i].split(",");
				var isDateValid = true;
				if(!checkDate(splitcomma[0])){
					isDateValid = false;
				}
				if(splitcomma[1].length == 0){
					if(isDateValid){
						error += "Row number "+ (i+1) +" description must be filled\n";
					}
					else {
						error += "Row number "+ (i+1) +" date format (MM/dd/yyyy) is not valid, description must be filled\n";
					}
				}
				else {
					if(!isDateValid){
						error += "Row number "+ (i+1) +" date format (MM/dd/yyyy) is not valid\n";
					}
				}
			}
		}
		if(error.length != 0){
			document.getElementById("message").innerHTML = error;
			return;
		}
		if(confirm("Are you sure to insert these data?\n"+csv)){
			flyToPage("insert");
		}
	}
	function checkDate(dat){
		//MM/dd/yyyy -> 1900 - 2099
		var regex = /^(?:(0[1-9]|1[012])[\/](0[1-9]|[12][0-9]|3[01])[\/](19|20)[0-9]{2})$/;
	    return regex.test(dat);
	}
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
</script>
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<html:form action="/holiday" method="post">
		<html:hidden property="task" name="holidayForm"/>
		<html:hidden property="holidayId" name="holidayForm"/>
		<div id="page-wrapper">
		    <div class="row">
		        <div class="col-lg-12">
		            <h1 class="page-header">Holiday List</h1>
		            <div class="col-lg-11">
		            	<div class="col-md-8">
				            <html:textarea property="holidayCsv" name="holidayForm" styleClass="form-control" style="width:80%;height:150px"></html:textarea>
				            
				           	<br/>
				           	<div class="col-lg-12" style="padding:0px">
				           	<div class="col-md-2" style="padding:0px">
				           		<button type="button" class="btn btn-primary" onclick="javascript:insertHoliday()">Insert CSV</button>
				           	</div>
				           	<div class="col-md-8" style="margin-left:10px;float:left">
				           		Format: Date(MM/dd/yyyy),Description<br/>Example:<br/>01/01/2017,New Year<br/>12/31/2017,New Year's Eve
				           	</div>
				           	<div style="clear:both"></div>
				           	</div>
			  			</div>
			  			<div class="col-md-4" style="color:red;overflow: auto;" id="message">
			  				<logic:notEmpty name="holidayForm" property="messageList">
								<logic:iterate id="message" name="holidayForm" property="messageList">
									<bean:write name="message" /> 
								</logic:iterate>
							</logic:notEmpty>
			  			</div>
		            </div>
		            <div class="col-lg-10">
			            <div class="panel-body">
							<div class="table-responsive" style="height:200px;overflow:auto;">
							    <table class="table table-hover">
							        <thead>
							            <tr>
							                <th>Date</th>
							                <th>Description</th>
							                <th>Action</th>
							            </tr>
							        </thead>
							        <tbody>
							        	<logic:notEmpty property="holidayList" name="holidayForm">
							        		<logic:iterate id="holiday" property="holidayList" name="holidayForm">
							        			<tr>
									                <td><bean:write name="holiday" property="holidayDate"/></td>
									                <td><bean:write name="holiday" property="name" format="#"/></td>
									                <td><a href="#" onclick="javascript:deleteHoliday('<bean:write name="holiday" property="holidayDate"/>-<bean:write name="holiday" property="name"/>',<bean:write name="holiday" property="holidayId" format="#"/>)">X</a></td>
									            </tr>  
							        		</logic:iterate>
							        	</logic:notEmpty>
							                   
							         </tbody>
		                		</table>
		                	</div>
		        		</div>
		            </div>
		        </div>
		        <!-- /.col-lg-12 -->
		    </div>
	    </div>
    </html:form>
</body>
</html>
