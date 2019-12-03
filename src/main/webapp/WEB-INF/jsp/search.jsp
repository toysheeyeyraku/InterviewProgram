<html xmlns:th="https://www.thymeleaf.org">

<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<body>
	<div>
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



		<c:if test="${good!=null}">
			<button type="button" id="previous">Previous</button>
			<button type="button" id="next">Next</button>
		</c:if>
	</div>
	<label>Year</label>
	<input type="text" id="year">
	<label>Month</label>
	<input type="text" id="month">
	<label>Day</label>
	<input type="text" id="day">
	<button type="button" id="search">Search</button>
	<a href="http://localhost:8080">Main</a>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js">
		
	</script>
	<script>
		var current = ${current};
		$( document ).ready(function() {
			$("#year").val(${year});
			$("#month").val(${month});
			$("#day").val(${day});
		});
		
		$("#search").on("click",function() {
					console.log("POP");
					var d=$("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
					document.location.href = 'http://localhost:8080/search/'+d+"/0" ;
				});
		$("#next").on("click",function() {
					current += 1;
					console.log(current);
					var d=$("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
					document.location.href = 'http://localhost:8080/search/'+d+"/"+current ;
				});
		$("#previous").on("click",function() {
			current -= 1;
			console.log(current);
			if (current<0){
				current=0;
			}
			var d=$("#year").val()+"-"+$("#month").val()+"-"+$("#day").val();
			document.location.href = 'http://localhost:8080/search/'+d+"/"+current ;
		});
	</script>

</body>
</html>