<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>View</title>
</head>
<body>
	Firstname: ${person.firstname}<br/>
	Surname: ${person.surname}<br/>
	Street: ${person.street}<br/>
	Zip: ${person.zip}<br/>
	City: ${person.city}<br/>
	Birthday: ${person.birthday}<br/>
	Height: ${person.height}<br/>
	<br/>
	<c:forEach items="${commChannels}" var="comm">
		Type: ${comm.type} // Data: ${comm.data}<br/>
	</c:forEach>
	<c:if test="${empty commChannels}">
		No communication channels defined.
	</c:if>
	<br/>
	<br/>
	<a href="${pageContext.servletContext.contextPath}/list">Back</a>
	<a href="${pageContext.servletContext.contextPath}/edit/${person.id}">Edit</a>
	<a href="${pageContext.servletContext.contextPath}/delete/${person.id}">Delete</a>
</body>
</html>
