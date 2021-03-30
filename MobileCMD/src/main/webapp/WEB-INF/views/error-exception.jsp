<%@ include file="common/includes.jsp" %>
<%@ page isErrorPage="true" %> 
<%@ page import="java.io.*" %>

<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link href="styles/styles.css" rel="stylesheet" type="text/css" />
</head>
<body>
	<div class="section" style="width: 700px; margin: 200px auto;">
		<div class="sectionContent" align="center">
			<h3 class="errorTitle">Error</h3> <br />
			<p>
				<strong>There was an error processing the request. Please retry the last operation or contact the system administrator.</strong>
			</p>
		</div>
		<a href="home.htm"> Home </a>
	</div>
	<%
		out.println("<!--");
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		exception.printStackTrace(pw);
		out.print(sw);
		sw.close();
		pw.close();
		out.println("-->");
	%>		
</body>
</html>	

