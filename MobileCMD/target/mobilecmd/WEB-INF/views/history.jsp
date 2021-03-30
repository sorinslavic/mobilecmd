<%@ include file="common/includes.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MobileCMD : Activity</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	
	<script type="text/javascript" src="<c:url value="/resources/fancybox/lib/jquery-1.4.min.js"/>"></script>
	<script type="text/javascript" src="<c:url value="/resources/fancybox/source/jquery.fancybox.pack.js"/>" ></script>
	
	<link rel="stylesheet" href="<c:url value="/resources/fancybox/source/jquery.fancybox.css"/>" type="text/css" media="screen" />	
	<link rel="stylesheet" href="<c:url value="/styles/styles.css"/>" type="text/css" />
	
	<script type="text/javascript">
	$(document).ready(function() {		
		$("#openFiles").fancybox({
			'transitionIn'	:	'elastic',
			'transitionOut'	:	'elastic',
			'speedIn'		:	300, 
			'speedOut'		:	200, 
			'overlayShow'	:	false,
			'width' 		: 	800,
			'height' 		: 	300,
			'autoScale' 	: 	false,
			'type' 			: 	'iframe'
		});
		
	});
	
	</script>
</head>

<body>
<div class="section" style="width: 700px; margin: 200px auto;">
	<%@ include file="common/user.jsp" %>
	<div>
		<h1> Devices </h1>
	</div>
	<table class="grid" border="0" cellpadding="1" cellspacing="5">
		<tr>
	    	<th valign="top">Device ID</th>
	        <th valign="top">Device Type</th>
	        <th valign="top">Device Name</th>
	        <th valign="top">Phone Number</th>
	        <th valign="top">Last Access Time</th>		                
		</tr>
		<c:forEach var="device" items="${deviceList}">
			<tr>
				<td align="center">${device.deviceImei}</td>
	           	<td align="center">${device.deviceType}</td>
	           	<td align="center">${device.deviceName}</td>
	           	<td align="center">${device.phoneNumber}</td>
	           	<td align="center">${device.lastAccess}</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<div>
		<h1> Desktop clients </h1>
	</div>	
	<table class="grid" border="0" cellpadding="1" cellspacing="5">
    	<tr>
           	<th valign="top">IP Address</th>
           	<th valign="top">Computer Name</th>
           	<th valign="top">Date installed</th>
           	<th valign="top">Last Access Time</th>
           	<th valign="top">Commands</th>
           	<th valign="top">Files</th>	
		</tr>
		<c:forEach var="client" items="${clientList}">
			<tr>
				<td align="center">${client.ipAddress}</td>
            	<td align="center">${client.computerName}</td>
            	<td align="center">${client.installed}</td>
            	<td align="center">${client.lastAccess}</td>
            	<td align="center">
            		<c:if test="${client.allowCommands}"> 
            			<img src="images/checked.png" width="16" height="16"/> 
            		</c:if> 
            	</td>
            	<td align="center">
            		<a id="openFiles" class="iframe" href="clientFiles.htm?clientId=${client.clientId}"> 
            			<img src="images/popup.png" width="16" height="16" alt="Open Files" title="Open Files" />
            		</a>
            	</td>			            	
			</tr>
		</c:forEach>					
	</table>
	<br />
	<%@ include file="common/clock.jsp" %> 
</div>

</body>
</html>
