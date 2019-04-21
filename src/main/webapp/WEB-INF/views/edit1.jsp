<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Edit</title>
</head>
<body>
	<form method="post">
		<input type="hidden" name="screen" value="1" />
		<input type="hidden" name="dataCache" value="${dataCache}" />
		
		Firstname: <input name="firstname" value="${edit.firstname}" />
		<c:if test="${not empty error_firstname}">${error_firstname}</c:if>
		<br/>
		
		Surname: <input name="surname" value="${edit.surname}" />
		<c:if test="${not empty error_surname}">${error_surname}</c:if>
		<br/>
		
		Street: <input name="street" value="${edit.street}" /><br/>
		
		Zip: <input name="zip" value="${edit.zip}" /><br/>
		
		City: <input name="city" value="${edit.city}" /><br/>
		
		Birthday: <input name="birthday" value="${edit.birthday}" />
		<c:if test="${not empty error_birthday}">${error_birthday}</c:if>
		<br/>
		
		Height: <input name="height" value="${edit.height}" />
		<c:if test="${not empty error_height}">${error_height}</c:if>
		<br/>
		
		<input type="submit" name="btnCancel" value="Cancel" /><br/>
		<input type="submit" name="btnSaveExit" value="Save and Exit" /><br/>
		<input type="submit" name="btnContDetails" value="Cont' to Details" /><br/>
	</form>
</body>
</html>
