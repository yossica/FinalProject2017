<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta name="description" content="">
	<meta name="author" content="">
	<!-- Bootstrap Core CSS -->
    <link href="asset/bootstrap/css/bootstrap.css" type="text/css" rel="stylesheet" media="all">
    <link href="asset/metisMenu/metisMenu.css" rel="stylesheet">
    <link href="dist/css/sb-admin-2.css" rel="stylesheet">
    <!-- Custom Fonts -->
    <link href="asset/font-awesome/css/font-awesome.css" rel="stylesheet" type="text/css">
    
<script>
	function flyToPageIndex(taskIndex)
	{
		document.forms[0].taskIndex.value = taskIndex;
		document.forms[0].submit();
	}
</script>
<title>Finance Solution</title>
</head>
<body>
<html:form action="/index" method="post">
	<html:hidden property="taskIndex" name="indexForm"/>
	<div id="wrapper">

        <!-- Navigation -->
        <nav class="navbar navbar-default navbar-static-top" role="navigation" style="margin-bottom: 0">
            <div class="navbar-header">
            	
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#" onclick="javascript:flyToPageIndex('index')"><img src="asset/image/ace-logo.png" height="50px"/> Finance Solution Ver. 1.0</a>
            </div>
            <!-- /.navbar-header -->

            <ul class="nav navbar-top-links navbar-right">
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                        <i class="fa fa-user fa-fw"></i> <i class="fa fa-caret-down"></i>
                    </a>
                    <ul class="dropdown-menu dropdown-user">
                        <li><a href="#" onclick="javascript:flyToPageIndex('changePassword')"><i class="fa fa-user fa-fw"></i> Change Password</a>
                        </li>
                        <li class="divider"></li>
                        <li><a href="#" onclick="javascript:flyToPageIndex('logout')"><i class="fa fa-sign-out fa-fw"></i> Logout</a>
                        </li>
                    </ul>
                    <!-- /.dropdown-user -->
                </li>
                <!-- /.dropdown -->
            </ul>
            <!-- /.navbar-top-links -->

            <div class="navbar-default sidebar" role="navigation">
                <div class="sidebar-nav navbar-collapse">
                    <ul class="nav" id="side-menu">
                        <li>
                            <a href="#" onclick="javascript:flyToPageIndex('index')"><i class="fa fa-home fa-fw"></i> Home</a>
                        </li>
                        <li>
                            <a href="#" onclick="javascript:flyToPageIndex('invoice')"><i class="fa fa-files-o fa-fw"></i> Invoice</a>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-money fa-fw"></i> Finance<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#" onclick="javascript:flyToPageIndex('financeSummary')">Finance Summary</a>
                                </li>
                                <li>
                                    <a href="#" onclick="javascript:flyToPageIndex('cashInBank')">Cash in Bank</a>
                                </li>
                                <li>
                                    <a href="#" onclick="javascript:flyToPageIndex('pettyCash')">Petty Cash</a>
                                </li> 
                            </ul>
                        </li>
                        <li>
                            <a href="#"><i class="fa fa-wrench fa-fw"></i>Manager<span class="fa arrow"></span></a>
                            <ul class="nav nav-second-level">
                                <li>
                                    <a href="#">Profesional Services</a>
                                </li>
                                <li>
                                    <a href="#">Clients</a>
                                </li>
                                <li>
                                    <a href="#" onclick="javascript:flyToPage('employee')">Employees</a>
                                </li>
                                <li>
                                    <a href="#">Additional Training</a>
                                </li>
                                <li>
                                    <a href="#" onclick="javascript:flyToPage('generalInformation')">General Information</a>
                                </li>
                                <li>
                                    <a href="holiday.do">Holidays</a>
                                </li>
                            </ul>
                            <!-- /.nav-second-level -->
                        </li>
                    </ul>
                </div>
                <!-- /.sidebar-collapse -->
            </div>
            <!-- /.navbar-static-side -->
        </nav>
    </div>
    <script src="asset/jquery/jquery.min.js"></script>
	<script src="asset/bootstrap/js/bootstrap.min.js"></script>
	<script src="asset/metisMenu/metisMenu.min.js"></script>
	<script src="dist/js/sb-admin-2.js"></script>
</html:form>
</body>
</html>