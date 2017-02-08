<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Index</title>
	<link href="asset/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
    <link href="asset/metisMenu/metisMenu.css" rel="stylesheet">
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="asset/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
</head>
<body>
	<jsp:include page="dashboard.jsp"/>
	<div id="page-wrapper">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Monthly Notifications</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            
            <!-- Table 1 -->
            <div class="row">
                <div class="col-lg-6" style="width:1100px">
                	<div class="col-lg-2" style="width:350px">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            Listed
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                            <div class="table-responsive">
	                                <table class="table table-hover">
	                                    <tbody>
	                                        <tr>
	                                            <td>Client A</td>
	                                        </tr>
	                                        <tr>
	                                            <td>Client B</td>
	                                        </tr>
	                                        <tr>
	                                            <td>Client C</td>
	                                        </tr>
	                                    </tbody>
	                                </table>
	                            </div>
	                            <!-- /.table-responsive -->
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
                    <!-- /.panel -->
					</div>
					<div class="col-lg-2" style="width:350px">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            Created
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                            <div class="table-responsive">
	                                <table class="table table-hover">
	                                    <tbody>
	                                        <tr>
	                                            <td>Client A</td>
	                                        </tr>
	                                        <tr>
	                                            <td>Client B</td>
	                                        </tr>
	                                        <tr>
	                                            <td>Client C</td>
	                                        </tr>
	                                    </tbody>
	                                </table>
	                            </div>
	                            <!-- /.table-responsive -->
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
                    </div>
                    <!-- /.panel -->
                    <div class="col-lg-2" style="width:350px">
	                    <div class="panel panel-default">
	                        <div class="panel-heading">
	                            Unpaid
	                        </div>
	                        <!-- /.panel-heading -->
	                        <div class="panel-body">
	                            <div class="table-responsive">
	                                <table class="table table-hover">
	                                    <tbody>
	                                        <tr>
	                                            <td>Client A</td>
	                                        </tr>
	                                        <tr>
	                                            <td>Client B</td>
	                                        </tr>
	                                        <tr>
	                                            <td>Client C</td>
	                                        </tr>
	                                    </tbody>
	                                </table>
	                            </div>
	                            <!-- /.table-responsive -->
	                        </div>
	                        <!-- /.panel-body -->
	                    </div>
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
		</div>
	</div>
</body>
</html>