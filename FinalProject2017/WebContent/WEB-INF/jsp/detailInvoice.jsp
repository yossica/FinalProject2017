<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Invoice Detail</title>
<script>
	function flyToPage(task){
		document.forms[1].task.value = task;
		document.forms[1].submit();
	}
</script>
<title>Client</title>
</head>
<body>
	<jsp:include page="dashboard.jsp" />
	<html:form action="/invoice" method="post">
		<html:hidden property="task" name="invoiceForm" />
		<div id="page-wrapper">
			<div class="row">
				<div class="col-lg-12">
					<h1 class="page-header">Invoice Detail</h1>
					
					<div class="panel-body">
					
						<div class="col-lg-2">
							<strong>
								Invoice No : 
							</strong>
						</div>
						<div class="col-lg-4">
							<strong>
								<html:hidden name="invoiceForm" property="invoiceBean.transactionInvoiceHeaderId"/>
								<bean:write name="invoiceForm" property="invoiceBean.invoiceNumber"/>
							</strong>
						</div>
						<div class="col-lg-4" style="text-align:right;">
							<strong>
								Date :
							</strong> 
						</div>
						<div class="col-lg-2" style="text-align:right;">
							<strong>
								<bean:write name="invoiceForm" property="invoiceBean.invoiceDate"/>
							</strong>
						</div>
						<div class="col-lg-12">
						<div class="table-responsive">
							<table style="border:3px double black;margin-top:30px;margin-bottom:10px;" width=100% >
								<tr>
									<td style="padding-left:10px;padding-top:10px;">Name</td>
									<td style="padding-left:10px;padding-top:10px;">: <bean:write name="invoiceForm" property="clientBean.name"/></td>
									<td style="padding-left:10px;padding-top:10px;">City</td>
									<td style="padding-left:10px;padding-top:10px;">: <bean:write name="invoiceForm" property="clientBean.city"/></td>
								</tr>
								<tr>
									<td rowspan="3" style="padding-left:10px;">Address</td>
									<td rowspan="3" style="padding-left:10px;">: <bean:write name="invoiceForm" property="clientBean.address"/></td>
									<td style="padding-left:10px;padding-top:10px;">Telp</td>
									<td style="padding-left:10px;padding-top:10px;">: <bean:write name="invoiceForm" property="clientBean.phoneNumber"/></td>
								</tr>
								<tr>
									<td style="padding-left:10px;padding-top:10px;">Zip Code</td>
									<td style="padding-left:10px;padding-top:10px;">: <bean:write name="invoiceForm" property="clientBean.postalCode"/></td>
								</tr>
								<tr>
									<td style="padding-left:10px;padding-top:10px;">Fax</td>
									<td style="padding-left:10px;padding-top:10px;padding-bottom:10px;">: <bean:write name="invoiceForm" property="clientBean.faxNumber"/></td>
								</tr>
							</table>
						</div>
						</div>
						<br>
						<div class="col-lg-12">
						<div class="table-responsive">
							<table border="2" style="margin-top:30px;margin-bottom:10px;padding:100px;" width=100% >
								<tr>
									<th width=5% style="text-align:center;">No.</th>
									<th width=40% style="text-align:center;">Description</th>
									<th width=15% style="text-align:center;">Unit Price</th>
									<th width=15% style="text-align:center;">Total</th>
									<th width=25% style="text-align:center;">Notes</th>
								</tr>
								<logic:iterate id="inv" name="invoiceForm" property="invoiceDetailList">
									<tr height=40px>
										<td style="text-align:center;"><bean:write name="inv" property="numb" format="#"/></td>
										<td style="padding:10px;"><bean:write name="inv" property="description"/></td>
										<td style="padding:10px;"><bean:write name="inv" property="unitPrice" format="#,###.##"/></td>
										<td style="padding:10px;"><bean:write name="inv" property="totalFee" format="#,###.##"/></td>
										<td style="padding:10px;"><bean:write name="inv" property="notes"/></td>
									</tr>
								</logic:iterate>
								</table>
								<table border="0" style="margin-top:10px;margin-bottom:30px;padding:100px;" width=100%>
								<tr>
									<td width=60% style="text-align:right;">Total  :&nbsp;</td>
									<td width=40% style="padding:10px;"><bean:write name="invoiceForm" property="invoiceBean.totalNet" format="#,###.##"/></td>
								</tr>
								<tr>
									<td width=55% style="text-align:right;">PPN 10%  :&nbsp;</td>
									<td width=45% style="padding:10px;"><bean:write name="invoiceForm" property="invoiceBean.totalPpn" format="#,###.##"/></td>
								</tr>
								<tr>
									<td width=55% style="text-align:right;">Grand Total  :&nbsp;</td>
									<td width=45% style="padding:10px;"><bean:write name="invoiceForm" property="invoiceBean.totalGross" format="#,###.##"/></td>
								</tr>
							</table>
						</div>
						</div>
						<br>
						<div class="col-lg-12">
						<div class="table-responsive">
							<table style="margin-top:30px;margin-bottom:10px;" width=50% >
								<tr>
									<td class="col-lg-1">Note :</td>
									<td class="col-lg-5" style="padding:10px;"><bean:write name="invoiceForm" property="invoiceBean.notes"/></td>
								</tr>
							</table>
						</div>
						</div>
						<br>
						<div class="col-lg-12">
						<div class="table-responsive">
							<table style="border:3px double black;margin-top:30px;margin-bottom:10px;overflow: auto; max-height: 400px;" width=35% >
								<tr>
									<td style="padding:10px;">
										Nomor Rekening:
									</td>
								</tr>
								<tr>
									<td style="padding:10px;">
										<bean:write name="invoiceForm" property="note.value"/>
									</td>
								</tr>
							</table>
						<br>
						<div class="pull-right"><bean:write name="invoiceForm" property="sign.value"/></div>
						
						<div class="pull-left" style="margin-top:40px;margin-bottom:10px;">
							<input type="button" value="Back" class="btn btn-primary" onclick="javascript:flyToPage('invoice')">
							<logic:equal name="invoiceForm" property="statusId" value="1">
								<input type="button" value="Edit" class="btn btn-primary">
							</logic:equal>
							<logic:equal name="invoiceForm" property="statusId" value="2">
								<input type="button" value="Edit" class="btn btn-primary">
							</logic:equal>
							<input type="button" value="Export" class="btn btn-primary" onclick="javascript:flyToPage('exportDetail')">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
	</html:form>
</body>
</html>