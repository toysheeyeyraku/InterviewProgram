<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
	xmlns:th="https://www.thymeleaf.org"
	xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
<%@ page import="java.util.ArrayList"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<head>
<title>Spring Security Example</title>
</head>
<body>


	<c:forEach items="${data}" var="s">
		<form th:action="@{/get}" method="post" enctype="application/json">
			<input type="hidden" name="type" value="${s}" /> <input
				type="hidden" name="${_csrf.parameterName}
		value=" ${_csrf.token}"/>
			<div>
				<input type="submit" value="${s}" />
			</div>
		</form>

	</c:forEach>
	${question}



	<div>
		<div>
			<label> Comment <input id="commentInput" type="text"
				name="comment" />
			</label>
		</div>


		<button type="button" id="addComment">Add Comment</button>
	</div>




	<p>Respondent</p>
	<input type="text" id="Respondent" />
	<button type="button" id="end">End Interview</button>
	${warn}
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
	<script>
		$("#end").on("click", function() {
			if ($('#Respondent').val().length==0){
				return ;
			}
			
			$.ajax({
				type : "POST",
				url : 'http://localhost:8080/endinterview',
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				data:{"name": $('#Respondent').val()},
				async : false,
				success : function(data) {
					document.location.href = 'http://localhost:8080/';
				}
			});
		});
	</script>
	<script>
		$("#addComment").on("click", function() {

			$.ajax({
				type : "POST",
				url : 'http://localhost:8080/addComment',
				contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
				data : {"comment":$('#commentInput').val()},
				async : false,
				success : function(data) {
					$('#commentInput').val("");
					$('#noStored').text('Stored');
				}
			});
		});
	</script>
</body>
</html>