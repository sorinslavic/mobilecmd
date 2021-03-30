<%@ include file="common/includes.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>Mobile CMD</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="<c:url value="/styles/styles.css"/>" type="text/css" />
</head>
<body>
<div class="regOk">
	${registrationOk}
</div>
<form method="post">
	<div class="section" style="width: 700px; margin: 200px auto;">
		<h1>Mobile CMD Server</h1> <br /> <br />
		<p>Use this following link to download the client app. 
		<a href="downloadClient.htm">Click here</a> </p>		
		
		<div class="sectionContent">
			<table width="100%" border="0" cellpadding="0" cellspacing="2">
				<tr>
					<td>
				        <table width="100%" border="0" cellpadding="0" cellspacing="15">
							<tr height="25">
				            	<td width="30%" align="right"><strong>User name</strong></td>
				                <td>
				                	<input id="username" name="username" type="text" class="medium" value="${username}"/>
				                </td>
				                <td>
					                <c:if test="${errorParam.label == 'User name'}">								
										<div class="error">
											<c:out value="${errorParam.message}" />
										</div>									
									</c:if>
				                </td>
							</tr>
				            <tr height="25">
				            	<td align="right"><strong>Password</strong></td>
				                <td>
				                	<input id="password" name="password" type="password" class="medium"/>
				                </td>
				                <td>
				                	<c:if test="${errorParam.label == 'Password'}">								
										<div class="error">
											<c:out value="${errorParam.message}" />
										</div>									
									</c:if>
				                </td>
							</tr>
							<c:if test="${not empty error}">
								<tr>
									<td></td>
									<td class="error" colspan="2">
										<c:out value="${error}" />
									</td>
								</tr>
							</c:if>
				            <tr>
				            	<td>&nbsp;</td>
				                <td width="10">
				                	<input type="button" id="actSave" onclick="javascript:document.forms[0].submit();" value="Login" />				                	
				                	&nbsp;&nbsp;&nbsp;
				                	<a href="register.htm">Register</a><br/>
				                </td>
				                <td> &nbsp; </td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
	</div>
</form>

</body>
</html>
