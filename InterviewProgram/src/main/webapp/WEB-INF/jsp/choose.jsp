<html xmlns:th="https://www.thymeleaf.org">

<%@ page import="java.util.ArrayList" %>

<body>

	<form method="POST" enctype="multipart/form-data" action="/choose">
			<table>
				
				<%
				ArrayList<String> list=(ArrayList<String>) request.getAttribute("body");
				StringBuilder body = new StringBuilder();
				String s = "<label >%s</label><input type=\"checkbox\"  name=\"def\" value=\"%s\"><Br>";
				for (String key : list) {
					body.append(String.format(s, key, key) + "\n");
				}
				 out.println(body.toString());
				%>
			<input type="hidden"
    		name="${_csrf.parameterName}"
    		value="${_csrf.token}"/>
				<tr><td></td><td><input type="submit" value="choose" /></td></tr>
			</table>
		</form>

</body>
</html>