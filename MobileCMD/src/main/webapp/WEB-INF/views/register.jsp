<%@ include file="common/includes.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MobileCMD : Register</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="<c:url value="/styles/styles.css"/>" type="text/css" />
</head>

<body>
<form method="post">
	<div class="section" style="width: 700px; margin: 200px auto;">
		<h1>Registration</h1>
				
		<div class="sectionContent">
			<table width="100%" border="0" cellpadding="0" cellspacing="2">
				<tr>
					<td>
				        <table width="100%" border="0" cellpadding="0" cellspacing="15">
							<tr height="25">
				            	<td width="30%" align="right"><strong>User name</strong></td>
				                <td>
				                	<input id="username" name="username" type="text" class="medium" value="${user.userName}"/>
				                </td>
				                <td>
					                <c:if test="${user.exception.label == 'User name'}">								
										<div class="error">
											<c:out value="${user.exception.message}" />
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
					                <c:if test="${user.exception.label == 'Password'}">								
										<div class="error">
											<c:out value="${user.exception.message}" />
										</div>									
									</c:if>
				                </td>
							</tr>
							<tr height="25">
				            	<td align="right"><strong>Validate Password</strong></td>
				                <td>
				                	<input id="password2" name="password2" type="password" class="medium"/>
				                </td>
				                <td>
					                <c:if test="${user.exception.label == 'Validate Password'}">								
										<div class="error">
											<c:out value="${user.exception.message}" />
										</div>									
									</c:if>
				                </td>
							</tr>
							<tr height="25">
				            	<td align="right"><strong>First Name</strong></td>
				                <td colspan="2">
				                	<input id="firstName" name="firstName" type="text" class="medium" value="${user.firstName}"/>
				                </td>
							</tr>
							<tr height="25">
				            	<td align="right"><strong>Last Name</strong></td>
				                <td colspan="2">
				                	<input id="lastName" name="lastName" type="text" class="medium" value="${user.lastName}"/>
				                </td>
							</tr>
							<c:if test="${user.invalidError}">
								<tr>
									<td></td>
									<td class="error" colspan="2">
										<c:out value="${user.error}" /> 
									</td>
								</tr>
							</c:if>
				            <tr>
				            	<td>&nbsp;</td>
				                <td width="10">
				                	<input type="button" id="actSave" onclick="javascript:document.forms[0].submit();" value="Register" />
				                	&nbsp;&nbsp;&nbsp;
				                	<a href="home.htm">Home</a><br/>
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
