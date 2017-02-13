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
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<div id="page-wrapper">
		<div class="row">
            <div class="col-lg-12">
                <h1 class="page-header">Petty Cash</h1>
            </div>
        </div>
		<div class="row" style="margin-top: 1%;">
			<div class="col-lg-12">
       			<label>Remaining Balance: Rp. 1.900.000,-</label>
                <button type="button" class="btn btn-primary pull-right">Balancing</button><br /><br />
	        </div>
		</div>
		<div style="border:1px solid black; padding: 1%; border-radius: 10px; background-color: #EFEFEF;">
			<div class="row">
		    	<div class="col-lg-12">
		                Category<br />
		                <select class="form-control" style="width:50%;">
		                    <option>1</option>
		                    <option>2</option>
		                </select><br />
		        </div>
		    </div>
		    <div class="row">
		    	<div class="col-lg-3">
		    		From<br />
		    		<input type="date" class="form-control" style="width:100%;">
		    	</div>
		    	<div class="col-lg-3">
		    		To<br />
		    		<input type="date" class="form-control" style="width:100%;">
		    	</div>
		    </div>
		    <div class="row">
		    	<div class="col-lg-6">
		    		<br />
		    		<button type="button" class="btn btn-primary">Filter</button>
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
</body>
</html>
