<%@ include file="common/includes.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>MobileCMD : Files</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<link rel="shortcut icon" href="favicon.ico" type="image/x-icon" />
	<link rel="stylesheet" href="<c:url value="/styles/styles.css"/>" type="text/css" />
</head>

<body>
<div class="section">
	<div>
		<h1> Client files: ${computerName} </h1>
	</div>	
	<table class="grid" border="0" cellpadding="1" cellspacing="5">
    	<tr>
           	<th valign="top">System Path</th>
           	<th valign="top">Date Added</th>
           	<th valign="top">Type</th>
           	<th valign="top">Recursive Folder</th>
           	<th valign="top">Last Access Date</th>
		</tr>
		<c:forEach var="file" items="${clientFileList}">
			<tr>
				<td align="left">${file.path}</td>      	
            	<td align="center">${file.added}</td>
            	<td align="center">
            		<c:choose>
            			<c:when test="${file.folder}"> 
            				<img src="images/folder.png" width="16" height="16" alt="Folder" title="Folder" />
            			</c:when>
            			<c:otherwise>
            				<img src="images/file.png" width="16" height="16" alt="File" title="File" />
            			</c:otherwise> 
            		</c:choose>         		 
            	</td>
            	<td align="center"><c:if test="${file.recursive}"> <img src="images/checked.png" width="16" height="16"/> </c:if> </td>
            	<td align="center">${file.lastAccess}</td>
			</tr>
		</c:forEach>					
	</table>
</div>
</body>
</html>