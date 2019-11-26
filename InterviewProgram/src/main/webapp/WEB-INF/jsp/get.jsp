<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="https://www.thymeleaf.org"
      xmlns:sec="https://www.thymeleaf.org/thymeleaf-extras-springsecurity3">
      <%@ page import="java.util.ArrayList" %>
    <head>
        <title>Spring Security Example </title>
    </head>
    <body>
        
       <%
       ArrayList<String> p=(ArrayList<String>) request.getAttribute("data");
       String shab = "<form th:action=\"@{/get}\" method=\"post\" enctype=\"application/json\">\r\n"
				+ "             <input type=\"hidden\" name=\"type\" value=\"%s\"/> \r\n" + "           \r\n"
				+ "            <input type=\"hidden\"\r\n" + "    		name=\"${_csrf.parameterName}\"\r\n"
				+ "    		value=\"${_csrf.token}\"/>\r\n"
				+ "            <div><input type=\"submit\" value=\"%s\"/></div>\r\n" + "        </form>";
		StringBuilder ans = new StringBuilder();
		if (p != null) {
			for (String s : p) {
				ans.append(String.format(shab, s, s));
			}
		}
		out.println(ans.toString());
       %>
        ${question}
       
        
        
         <div>
         <div><label> Comment <input id="commentInput" type="text" name="comment"/> </label></div>
            
           
            <button type="button" id="addComment" >Add Comment</button>
         </div>
       
        
       
        <button type="button" id="end">End Interview</button>
      ${warn} 
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
      <script>
      $( "#end" ).on( "click", function() {
    	  $.ajax({
    	        type: "POST",
    	        url: 'http://localhost:8080/endinterview',
    	        dataType: 'text',
    	        
    	        async: false,
    	        success: function (data) {
    	            document.location.href='http://localhost:8080/';
    	        }
    	    });
    	});
	</script>
	<script>
	
      $( "#addComment" ).on( "click", function() {
    	  
    	  $.ajax({
    	        type: "POST",
    	        url: 'http://localhost:8080/addComment',
    	        dataType: 'text',
    	        data : $('#commentInput').val(),
    	        async: false,
    	        success: function (data) {
    	        	$('#commentInput').val("");
    	        	$('#noStored').text('Stored');
    	        }
    	    });
    	});
	</script>
    </body>
</html>