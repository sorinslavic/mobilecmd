<%@ tag body-content="scriptless" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="path" required="true" %>

<spring:bind path="${path}">
	<c:if test="${not empty status.errorMessages}">
		<c:forEach var="error" items="${status.errorMessages}"> 
			<label class="error">
				<c:out value="${error}"/><br>
			</label>
		</c:forEach>
	</c:if>	
</spring:bind>
