<%@ tag body-content="scriptless" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="path" required="true" %>

<spring:bind path="${path}">
	<c:if test="${not empty status.errorMessages}">
		<div class="widget errorBox">
			<div class="widgetIcon">
				<img src="images/errorIcon.png" alt="<fmt:message key="invalid.value"/>" />
			</div>
			<p class="widgetText">
				<c:forEach var="error" items="${status.errorMessages}"> 
		 			<c:out value="${error}"/><br>
	 			</c:forEach>
	 		</p>
		</div>
	</c:if>	
</spring:bind>
