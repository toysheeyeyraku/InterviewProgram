<html xmlns:th="https://www.thymeleaf.org">

<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<c:forEach items="${data}" var="s">

		<label>Respondent: ${s.respondent}</label>
		<br />
		<label>Time: ${s.date}</label>
		<br />
		<label>Questions with comments:</label>
		<c:forEach items="${s.questionsWithComments}" var="x">
			<br />
			${x}
			
		</c:forEach>
		<br />
		<br />
	</c:forEach>
	<button type="button" id="previous">Previous</button>
	<button type="button" id="next">Next</button>
	<a href="http://localhost:8080">Main</a>
	<a href="http://localhost:8080/search">Search</a>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js">
		
	</script>
	<script>
		var current = ${current};
		$("#next")
				.on(
						"click",
						function() {
							current += 1;
							console.log(current);
							document.location.href = 'http://localhost:8080/interviews/'+ current;
						});
		$("#previous")
		.on(
				"click",
				function() {
					current -= 1;
					console.log(current);
					if (current<0){
						current=0;
					}
					document.location.href = 'http://localhost:8080/interviews/'+ current;
				});
	</script>

</body>
</html>