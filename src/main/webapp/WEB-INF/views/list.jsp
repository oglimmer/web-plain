<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>List</title>
</head>
<body>
	<form>
	<input type="hidden" name="sort" value="${sort}" />	
	<input type="hidden" name="sortOrder" value="${sortOrder}" />	
	<input type="hidden" name="page" value="${page}" />	
	<table>
		<tr>
			<th>Surname <input type="submit" name="sortSurname" value="<c:if test = "${sort == 'surname' and sortOrder == 'asc' }">V</c:if><c:if test = "${sort == 'surname' and sortOrder == 'desc' }">^</c:if><c:if test = "${sort != 'surname' }">&lt;&gt;</c:if>" style="border:0px" /></th>
			<th>Firstname  <input type="submit" name="sortFirstname" value="<c:if test = "${sort == 'firstname' and sortOrder == 'asc' }">V</c:if><c:if test = "${sort == 'firstname' and sortOrder == 'desc' }">^</c:if><c:if test = "${sort != 'firstname' }">&lt;&gt;</c:if>" style="border:0px" /></th>
			<th></th>
		</tr>	
		<tr>
			<th><input name="searchSurname" value="${searchSurname}" /></th>
			<th><input name="searchFirstname" value="${searchFirstname}"/></th>
			<th></th>
		</tr>	
		<c:forEach items="${allPersons}" var="person">
			<tr>
				<td>${person.surname}</td>
				<td>${person.firstname}</td>
				<td><a href="${pageContext.servletContext.contextPath}/view/${person.id}">Details</a></td>
	    	</tr>
		</c:forEach>
	</table>
	<input type="submit" value="Search" /> 
	Page: 
	<c:forEach var="pageNo" begin="${pageStart}" end="${pageEnd}">
		<input type="submit" name="btnPage" value="${pageNo}" style="border:0px;<c:if test = "${pageNo == page}">font-weight:bold</c:if>" />
	</c:forEach>
	</form>
	<a href="${pageContext.servletContext.contextPath}/edit/new">Create Entry</a>
</body>
</html>
