<html xmlns:th="https://www.thymeleaf.org">

<%@ page import="java.util.ArrayList" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<body>

	<form method="POST" enctype="multipart/form-data" action="/choose">
			<table>
				
				
				<c:forEach items="${body}" var="s">
				
				<label >${s}</label><input type="checkbox"  name="def" value="${s}"><Br>
				</c:forEach>
			<input type="hidden"
    		name="${_csrf.parameterName}"
    		value="${_csrf.token}"/>
				<tr><td></td><td><input type="submit" value="choose" /></td></tr>
			</table>
		</form>
		
</body>
</html>