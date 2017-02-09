<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Petty Cash</title>
	<!-- <script type="text/javascript">
	    $(function (){
	        $('#datetimepicker1').datetimepicker();
	        $('#datetimepicker2').datetimepicker();
	    });
	</script> -->
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<div id="page-wrapper">
		<div class="row" style="margin-top: 1%;">
			<div class="col-lg-12">
	        	<div class="panel panel-default">
	        		<div class="panel-heading">
	        			<label>Cash: Rp. 1.900.000,-</label>
                    	<button type="button" class="btn btn-primary pull-right">Balancing</button><br /><br />
	        		</div>
	        	</div>
	        </div>
		</div>
		<div class="row"">
	    	<div class="col-lg-12">
	        	<div class="panel panel-default">
	        		<div class="panel-heading">
                    	<div class="form-group" style="width:30%;">
                            Category
                            <select class="form-control">
                                <option>1</option>
                                <option>2</option>
                            </select>
                            From
                            <div class='input-group date' id="datetimepicker1">
			                    <input type='text' class="form-control" />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
                            To
                            <div class='input-group date' id="datetimepicker2">
			                    <input type='text' class="form-control" />
			                    <span class="input-group-addon">
			                        <span class="glyphicon glyphicon-calendar"></span>
			                    </span>
			                </div>
                        </div>
                    </div>
					<div class="panel-body">
						<div class="table-responsive" style="overflow: auto;">
						    <table class="table table-hover">
						        <thead>
						            <tr>
						                <th>Date</th>
						                <th>Category</th>
						                <th>Description</th>
						                <th>Debit</th>
						                <th>Credit</th>
						                <th>Balance</th>
						            </tr>
						        </thead>
						        <tbody>
						            <tr>
						                <td>08 FEB 2017</td>
						                <td>Stationery</td>
						                <td>Spidol Snowman WhiteBoard</td>
						                <td>Rp. 100.000</td>
						                <td></td>
						                <td>Rp. 1.900.000</td>
						            </tr>         
						         </tbody>
	                		</table>
	                	</div>
	                	<div class="pull-right">
	                		<button type="button" class="btn btn-primary">Debit</button>
	                		<button type="button" class="btn btn-primary">Credit</button>
	                	</div>
	        		</div>
	        	</div>
	    	</div>
		</div>
	</div>
</body>
</html>