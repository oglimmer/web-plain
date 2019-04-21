<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Edit Details</title>
</head>
<body>
	<form method="post">
		<input type="hidden" name="screen" value="2" />
		<input type="hidden" name="dataCache" value="${dataCache}" />
		<c:forEach items="${commChannels.communicationChannels}" var="comm">
			Type: <input name="type_${comm.id}" value="${comm.type}" />
			Data: <input name="data_${comm.id}" value="${comm.data}" /><br/>
		</c:forEach>
		<br/>
		<input type="submit" name="btnCancel" value="Cancel" /><br/>
		<input type="submit" name="btnBack" value="Back" /><br/>
		<input type="submit" name="btnAddRow" value="Add Row" /><br/>
		<input type="submit" name="btnSave" value="Save And Exit" /><br/>
	</form>
</body>
</html>
