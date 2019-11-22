<html xmlns:th="https://www.thymeleaf.org">
<body>

	<form method="POST" enctype="multipart/form-data" action="/choose">
			<table>
				${body}
				
			<input type="hidden"
    		name="${_csrf.parameterName}"
    		value="${_csrf.token}"/>
				<tr><td></td><td><input type="submit" value="choose" /></td></tr>
			</table>
		</form>

</body>
</html>