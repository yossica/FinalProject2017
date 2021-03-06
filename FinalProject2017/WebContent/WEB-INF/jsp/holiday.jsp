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
<script type="text/javascript">
	function toggleFilter() {
		var filter = document.getElementById("filterForm");
		filter.style.display = filter.style.display === 'none' ? '' : 'none';
		var insert = document.getElementById("insertForm");
		insert.style.display = 'none';
	}
	function toggleInsert() {
		var insert = document.getElementById("insertForm");
		insert.style.display = insert.style.display === 'none' ? '' : 'none';
		var filter = document.getElementById("filterForm");
		filter.style.display = 'none';
	}
	function deleteHoliday(holiday,id){
		swal({
			  title: "Are you sure?",
			  text: "System will delete "+holiday+" data from master holiday",
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
		            	document.forms[1].holidayId.value = id;
		    			flyToPage("delete");
			  } else {
			    swal("Cancelled", "Cancel Delete Master Holiday", "error");
			  }
			});	
	}
	function insertHoliday(){
		var csv = document.forms[1].holidayCsv.value;
		//validate
		var splitenter = csv.split("\n");
		var error = "";
		/* document.getElementById("message").style.display="block"; */
		for(var i = 0 ; i < splitenter.length ; i++){
			if(!splitenter[i].includes(",")){
				error += "Row number "+ (i+1) + " must contain comma (,) \n";
			}
			else {
				var splitcomma = splitenter[i].split(",");
				var isDateValid = true;
				if(!checkDate(splitcomma[0])){
					isDateValid = false;
				}
				if(splitcomma[1].length == 0){
					if(isDateValid){
						error += "Row number "+ (i+1) +" description must be filled \n";
					}
					else {
						error += "Row number "+ (i+1) +" date format (MM/dd/yyyy) is not valid, description must be filled \n";
					}
				}
				else {
					if(!isDateValid){
						error += "Row number "+ (i+1) +" date format (MM/dd/yyyy) is not valid \n";
					}
				}
			}
		}
		if(error.length != 0){
			sweetAlert("Oops...", error, "error");
			return;
		}
		else{
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
			            	flyToPage("insert");
				  } else {
				    swal("Cancelled", "Cancel Insert Master Holiday", "error");
				  }
				}); 
		}
	
	}
	function checkDate(dat){
		//MM/dd/yyyy
		var regex = /(?=\d)^(?:(?!(?:10\D(?:0?[5-9]|1[0-4])\D(?:1582))|(?:0?9\D(?:0?[3-9]|1[0-3])\D(?:1752)))((?:0?[13578]|1[02])|(?:0?[469]|11)(?!\/31)(?!-31)(?!\.31)|(?:02(?=.?(?:(?:29.(?!000[04]|(?:(?:1[^0-6]|[2468][^048]|[3579][^26])00))(?:(?:(?:\d\d)(?:[02468][048]|[13579][26])(?!\x20BC))|(?:00(?:42|3[0369]|2[147]|1[258]|09)\x20BC))))))|(?:02(?=.(?:(?:\d\D)|(?:[01]\d)|(?:2[0-8])))))([\/])(0[1-9]|[12]\d|3[01])\2(?!0000)((?=(?:00(?:4[0-5]|[0-3]?\d)\x20BC)|(?:\d{4}(?!\x20BC)))\d{4}(?:\x20BC)?)(?:$|(?=\x20\d)\x20))?((?:(?:0?[1-9]|1[012])(?::[0-5]\d){0,2}(?:\x20[aApP][mM]))|(?:[01]\d|2[0-3])(?::[0-5]\d){1,2})?$/;
	    return regex.test(dat);
	}
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
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
			else{
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
	<html:form action="/holiday" method="post">
		<html:hidden property="task" name="holidayForm" />
		<html:hidden property="holidayId" name="holidayForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Holiday List</h1>
					<div class="row" style="margin-top: 1%;">
						<div class="col-lg-12">
							<div class="pull-right">
								<button id="filterButton" type="button" class="btn btn-primary"
									onclick="javascript:toggleFilter()">Toggle Filter</button>
								<button id="insertButton" type="button" class="btn btn-primary"
									onclick="javascript:toggleInsert()">Insert</button>
							</div>
							<br /> <br />
						</div>
					</div>
					<div id="insertForm" class="col-lg-9" style="display: none">
						<div class="col-md-12">
							<html:textarea property="holidayCsv" name="holidayForm"
								styleClass="form-control" style="width:80%;height:150px"></html:textarea>

							<br />
							<div class="col-lg-12" style="padding: 0px">
								<div class="col-md-2" style="padding: 0px; margin-top: 10px;">
									<button type="button" class="btn btn-primary"
										onclick="javascript:insertHoliday()">Save CSV</button>
								</div>
								<div class="col-md-8" style="margin-left: 10px; float: left">
									Format: Date(MM/dd/yyyy),Description<br />Example:<br />01/01/2017,New
									Year<br />12/31/2017,New Year's Eve
								</div>
								<div style="clear: both"></div>
							</div>
						</div>
					</div>
					<logic:notEmpty name="holidayForm" property="messageList">
						<logic:iterate id="message" name="holidayForm"
							property="messageList">
							<input type="hidden" id="err"
								value="<bean:write name="message" />">
						</logic:iterate>
					</logic:notEmpty>
					<div id="filterForm" class="col-lg-12"
						style="border: solid 2px gray; border-radius: 10px; background-color: #EFEFEF; display: none;">
						<div class="row" style="margin-top: 10px;">
							<div class="col-md-12" style="padding-right: 1%">
								<div class="col-md-2">Year</div>
								<div class="col-md-10">
									<html:select property="filterYear" name="holidayForm"
										styleClass="form-control-client">
										<html:option value="">All</html:option>
										<html:optionsCollection name="holidayForm" property="yearList"
											value="value" label="label" />
									</html:select>
									<br />
								</div>
							</div>
						</div>
						<div class="col-md-12"
							style="margin-top: 10px; margin-bottom: 10px;">
							<button type="button" class="btn btn-primary"
								onclick="javascript:flyToPage('filter')">Filter</button>
						</div>
					</div>
					<div class="col-lg-12">
						<div class="panel-body">
							<div id="listHoliday" class="table-responsive"
								style="height: 400px; overflow: auto;">
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
											<logic:iterate id="holiday" property="holidayList"
												name="holidayForm">
												<tr>
													<td><bean:write name="holiday" property="holidayDate" /></td>
													<td><bean:write name="holiday" property="name"
															format="#" /></td>
													<td><button type="button" class="btn btn-primary"
															style="margin-bottom: 1%;"
															onclick='javascript:deleteHoliday("<bean:write name="holiday" property="holidayDate"/>-<bean:write name="holiday" property="name"/>",<bean:write name="holiday" property="holidayId" format="#"/>)'>Delete</button></td>
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
